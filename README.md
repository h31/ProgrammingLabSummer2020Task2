# ciphxor  


[![Build Status](https://travis-ci.org/shomov/ciphxor.svg?branch=master)](https://travis-ci.org/shomov/ciphxor)
[![codecov](https://codecov.io/gh/shomov/ciphxor/branch/master/graph/badge.svg)](https://codecov.io/gh/shomov/ciphxor)


Данная программа представляет собой реализацию консольного приложения, реализующее шифрование посредством алгоритма "исключающее или", иными словами "xor".
Поддерживает два режима работы: шифрация и дешифрация. Аргумент, указывающий режим, пишется в самом начале:
- `-с`		шифрация
- `-d`		дешифрация

Затем после указания метода в шестнадцатиричном формате указывается ключ шифровки.
Файлы, зашифрованные приложением, имеют специальное расширение .crp.
Вывод файла после обработки также имеет два метода: вывод в директорию входящего файла(по умолчанию) и вывод в указанную пользователем директорию с присвоением пользовательского имени. Для работы в пользовательском режиме указывается флаг `-o`, а затем следует путь до директории назначения с указанием имени выходного файла.

Пример использования:
`-c FE544D /home/mikhail/testFolder/text.txt -o /tmp/test-encode.txt.crp`

Также данное приложение ведёт логирование. Лог сохраняется в /tmp/ciphxor.log


------------

Приложение является практической работой, выполненной в рамках курса "Технологии программирования".

Шомов Михаил  
mikle@shomov.spb.ru
