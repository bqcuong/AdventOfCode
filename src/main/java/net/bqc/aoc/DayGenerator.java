package net.bqc.aoc;

import net.bqc.aoc.utils.IOUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class DayGenerator {

    private String year;
    private String day;
    private String dayTaskName;

    Properties config = new Properties();

    public DayGenerator(String year, String day) {
        this.year = year;
        this.day = day;

        try {
            this.config.load(DayGenerator.class.getClassLoader().getResourceAsStream("config.properties"));
        }
        catch (IOException ignored) {}

        this.generateCodeAndTestFiles();
        this.generateInputFiles();
        this.updateReadMe();
    }

    private void generateInputFiles() {
        try {
            // Download the first sample input
            String dayInURL = day.startsWith("0") ? day.substring(1) : day;
            Document problemDoc = getConnection(String.format("https://adventofcode.com/%s/day/%s", year, dayInURL)).get();
            dayTaskName = Objects.requireNonNull(problemDoc.selectFirst(".day-desc > h2")).text();
            dayTaskName = dayTaskName.substring(dayTaskName.indexOf(":") + 1).replace("---", "").trim();
            String sampleInput = Objects.requireNonNull(problemDoc.selectFirst("pre > code")).text();

            IOUtils.writeFile(String.format("src/test/resources/year%s/Day%s_SampleInput.txt", year, day), sampleInput);

            // Download the input
            Connection con = getConnection(String.format("https://adventofcode.com/%s/day/%s/input", year, dayInURL));
            byte[] input = con.execute().bodyAsBytes();
            FileOutputStream out = (new FileOutputStream(String.format("src/test/resources/year%s/Day%s_Input.txt", year, day)));
            out.write(input);
            out.close();

            System.out.printf("Generate boilerplate code for Day %s: %s\n", day, dayTaskName);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateCodeAndTestFiles() {
        String code = IOUtils.readResourceFile("templates/DayXY.java");
        String test = IOUtils.readResourceFile("templates/DayXYTest.java");

        code = code.replace("ABCD", year).replace("XY", day);
        test = test.replace("ABCD", year).replace("XY", day);

        IOUtils.writeFile(String.format("src/main/java/net/bqc/aoc/year%s/Day%s.java", year, day), code);
        IOUtils.writeFile(String.format("src/test/java/net/bqc/aoc/year%s/Day%sTest.java", year, day), test);
    }

    public void updateReadMe() {
        try {
            Path path = Paths.get("README.md");
            List<String> content = Files.readAllLines(path);
            int line = 0;
            while (!content.get(line++).trim().endsWith(year));
            while (line < content.size()) {
                String[] parts = content.get(line).split("\\|");
                if (parts[1].trim().equals(day)) {
                    parts[2] = parts[2].replace("[]", "[" + dayTaskName + "]");
                    content.set(line, String.join("|", parts));
                    break;
                }
                line++;
            }
            Files.write(path, String.join("\n", content).getBytes());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection(String url) {
        String[] cookie = ((String)this.config.get("aoc_cookie")).split("=");
        Connection con = Jsoup.connect(url);
        con.cookie(cookie[0], cookie[1]);
        return con;
    }

    public static void main(String[] args) {
        new DayGenerator("2024", "08");
    }
}
