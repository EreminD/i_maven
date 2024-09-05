package com.inzhenerka.compiler;

import com.inzhenerka.config.Configuration;
import com.inzhenerka.config.ConfigurationProvider;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MegaToolCompilerExternal implements MegaToolCompiler {

        //compile
        //javac -cp ./src/ -d ./compiled/ ./src/**/*.java

        //package
        //jar --create --file programm.jar --main-class ru.my.test.App -C ./compiled .

        public void run(String sourceDirName, String targetDirName) throws IOException {
            Configuration configuration = ConfigurationProvider.load();
            configuration.getDst();
            configuration.getSrc();
            // Получаем инструмент компилятора
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            // Задаем путь к папке с исходниками
            File sourceDir = new File(sourceDirName);
            File outputDir = new File(targetDirName);
            // Создаем целевую директорию, если она не существует
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }

            // Компилируем файлы
            if (compiler != null) {
                // Обходим все .java файлы в sourceDir
                try {
        //                String[] options = new String[]{"-d", outputDir.getAbsolutePath(), sourceDir.toString()};
        //                int result = compiler.run(null, null, null, options);
                    Files.walk(sourceDir.toPath())
                            .filter(path -> path.toString().endsWith(".java"))
                            .forEach(filePath -> {
                                // Получаем относительный путь
                                Path originalPath = sourceDir.toPath().relativize(filePath);

                                Path absolut = originalPath.toAbsolutePath();
                                // Базовый путь, который нужно удалить
                                Path basePath = Paths.get("src/main/java");
                                Path classPath = Paths.get("classes");

                                // Удаляем базовый путь с помощью relativize
                                Path relativePath = basePath.relativize(originalPath);
                                Path classResultPath = classPath.resolve(relativePath);
                                String className = classResultPath.toString().replace(File.separator, ".").replace(".java", "");

                                // Полный путь для сохранения *.class файлов
                                File targetFile = new File(outputDir, classResultPath.toString().replace(".java", ".class"));

                                // Создаем директории для скомпилированного файла
                                targetFile.getParentFile().mkdirs();

                                // Компилируем файл
                                String[] options = new String[] {"-d", outputDir.getAbsolutePath(), filePath.toString()};
                                int result = compiler.run(null, null, null, options);
                                if (result == 0) {
                                    System.out.println("Компиляция успешна: " + filePath);
                                } else {
                                    System.err.println("Ошибка компиляции: " + filePath);
                                }
                            });

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                System.err.println("Компилятор не найден. Убедитесь, что вы используете JDK.");

            }
    }
    

}
