package net.bqc.aoc.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class IOUtils {

    public static String readResourceFile(String resourcePath) {
        try {
            ClassLoader classLoader = IOUtils.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(resourcePath);
            return readFromInputStream(inputStream);
        }
        catch (IOException e) {
            return "";
        }
    }

    public static void writeFile(String path, String content) {
        try {
            Files.write(Paths.get(path), content.getBytes());
        }
        catch (IOException ignored) {}
    }

    private static String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }
}
