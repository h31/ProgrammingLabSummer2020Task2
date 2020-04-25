package ru.nikiens.packrle.util;

import java.nio.file.FileSystems;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.PathMatcher;

public class ExtensionUtil {
    private String extension;

    public ExtensionUtil(String extension) {
        this.extension = extension;
    }

    public boolean hasExtension(Path path) {
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**?." + getExtension());
        return matcher.matches(path);
    }

    public Path truncate(Path path) {
        if (!hasExtension(path)) {
            throw new InvalidPathException(path.toString(),
                    String.format("Path must contain %s extension", getExtension()));
        }

        String fileName = path.getFileName().toString().substring(0,
                path.getFileName().toString().length() - getExtension().length() - 1);
        return path.resolveSibling(fileName);
    }

    public Path append(Path path) {
        return path.resolveSibling(path.getFileName() + "." + getExtension());
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
