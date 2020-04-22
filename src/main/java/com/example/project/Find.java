package com.example.project;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Find {
   public static void main(String[] args) throws IOException, ConcurrentModificationException {
      ArrayList<String> keys = new ArrayList<>(); //список для поиска имён директории и файла
      Collections.addAll(keys, args);
      boolean d = false; // содержится ли ключ -d
      boolean r = false; // содержится ли ключ -r
      String dirName = ""; // Имя указанной директориий

      for (int i = 0; i < args.length; i++) {
         if (args[i].equals("-r")) {
            r = true;
            keys.remove(args[i]);
         }
         if (args[i].equals("-d")) {
            d = true;
            dirName = args[i + 1];
            boolean foundDir = false; // существует ли указанная директори
            keys.remove(dirName);
            keys.remove(args[i]);
            if (!isDir(dirName)) {
                for (int j = i + 2; j < args.length; j++) {
                    dirName += " " + args[j];
                    keys.remove(args[j]);
                    if (isDir(dirName)) {
                        foundDir = true;
                        break;
                    }
                }
            } else foundDir = true;
            if (!foundDir ) throw new FileNotFoundException("Directory wasn't found.");
            if (keys.isEmpty()) throw  new FileNotFoundException("Enter file name.");
         }
      }

      String name = keys.get(0);

      if (keys.size() > 1) {
          for (int i = 1; i < keys.size(); i++) {
          name += " " + keys.get(i);
          }
      }

      if (!d && !r) { // Поиск в текущей директории
         File file = new File(name);
         if (file.exists()) {
            System.out.println("File " + name + " was found");
            System.out.println("Absolute path: " + file.getAbsolutePath());
         }
      }

      if (!d && r) { // Поиск в поддиректориях текущей директории
         File defaultDir = new File(System.getProperty("user.dir")); // директория по умолчанию
         find(defaultDir, name);

      }

      File directory = new File(dirName);

      if (d && !r) { // Поиск в указанной директории
          for (File files: directory.listFiles()) {
              if (files.getName().equals(name)) {
                  System.out.println("File " + name + " was found");
                  System.out.println("Absolute path: " + files.getAbsolutePath());
              }
          }
         }

      if (d && r) { // Поиск в поддиректориях указанной директории
         find(directory, name);
      }
   }

   public static void find(File dirName, String fileName) { // Поиск в поддиректориях
       File[] dirList = dirName.listFiles();
       assert dirList != null;
       for (File file : dirList) {
           if (file.isFile()) {
               if (file.getName().equals(fileName)) {
                   System.out.println("File " + file.getName() + " was found");
                   System.out.println("Absolute path: " + file.getAbsolutePath());
               }
           } else if (file.isDirectory()) find(file, fileName);
       }
   }

   public static boolean isDir(String directoryName) { // Проверка существования указанной директории
      File dir = new File(directoryName);
      return dir.isDirectory() && dir.exists();
   }
}
