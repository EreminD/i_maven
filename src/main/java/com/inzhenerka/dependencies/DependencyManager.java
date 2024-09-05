package com.inzhenerka.dependencies;

import com.inzhenerka.config.Configuration;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.stream.Collectors;

public class DependencyManager {

    public static void load(Configuration conf) throws IOException {
        String repository = conf.getRepositories().get(0);

        List<String> urlsForRepo = conf.getDependencies().stream()
                .map(dep -> getRepoUrl(repository, dep.getGroupId(), dep.getArtifactId(), dep.getVersion()))
                .peek(System.out::println)
                .collect(Collectors.toList());

        loadFiles(urlsForRepo, ".");

    }

    public static String getRepoUrl(String repo, String groupId, String artifactId, String version) {
        return String.format("%s/%s/%s/%s/%s-%s.jar", repo, groupId.replace(".", "/"), artifactId, version, artifactId, version);
    }

    public static void downloadFile(String fileURL, String saveDir) throws IOException {
        URL url = new URL(fileURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();

        // Проверяем, успешен ли ответ сервера
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String fileName = "";
            String disposition = httpConn.getHeaderField("Content-Disposition");
            String contentType = httpConn.getContentType();
            int contentLength = httpConn.getContentLength();

            if (disposition != null) {
                // Извлекаем имя файла из заголовка Content-Disposition
                int index = disposition.indexOf("filename=");
                if (index > 0) {
                    fileName = disposition.substring(index + 10, disposition.length() - 1);
                }
            } else {
                // Извлекаем имя файла из URL
                fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1);
            }

            System.out.println("Content-Type = " + contentType);
            System.out.println("Content-Disposition = " + disposition);
            System.out.println("Content-Length = " + contentLength);
            System.out.println("fileName = " + fileName);

            // Открываем входной поток для чтения данных из URL
            InputStream inputStream = httpConn.getInputStream();
            String saveFilePath = saveDir + java.io.File.separator + fileName;

            // Открываем выходной поток для записи данных в файл
            FileOutputStream outputStream = new FileOutputStream(saveFilePath);

            int bytesRead = -1;
            byte[] buffer = new byte[4096];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.close();
            inputStream.close();

            System.out.println("File downloaded: " + saveFilePath);
        } else {
            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
        }
        httpConn.disconnect();
    }

    public static void loadFiles(List<String> filesURLs, String saveDir) throws IOException {
        filesURLs.forEach(fileURL -> {
            try {
                downloadFile(fileURL, saveDir);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }
}