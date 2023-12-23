package net.bqc.aoc;

import net.bqc.aoc.utils.IOUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class DayGenerator {

    private String year;
    private String day;

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
    }

    private void generateInputFiles() {

        try {
            // Download the first sample input
            Document problemDoc = getConnection(String.format("https://adventofcode.com/%s/day/%s", year, day)).get();
            Elements sampleInputs = problemDoc.select("pre > code");

            assert sampleInputs.size() > 0;
            String sampleInput = sampleInputs.get(0).text();

            IOUtils.writeFile(String.format("src/test/resources/year%s/Day%s_SampleInput.txt", year, day), sampleInput);

            // Download the input
            Connection con = getConnection(String.format("https://adventofcode.com/%s/day/%s/input", year, day));
            byte[] input = con.execute().bodyAsBytes();
            FileOutputStream out = (new FileOutputStream(new File(String.format("src/test/resources/year%s/Day%s_Input.txt", year, day))));
            out.write(input);
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection(String url) {
        String[] cookie = ((String)this.config.get("aoc_cookie")).split("=");
        Connection con = Jsoup.connect(url);
        con.cookie(cookie[0], cookie[1]);
        return con;
    }

    public void generateCodeAndTestFiles() {
        String code = IOUtils.readResourceFile("templates/DayXY.java");
        String test = IOUtils.readResourceFile("templates/DayXYTest.java");

        code = code.replace("ABCD", year).replace("XY", day);
        test = test.replace("ABCD", year).replace("XY", day);

        IOUtils.writeFile(String.format("src/main/java/net/bqc/aoc/year%s/Day%s.java", year, day), code);
        IOUtils.writeFile(String.format("src/test/java/net/bqc/aoc/year%s/Day%sTest.java", year, day), test);
    }

    public static void main(String[] args) {
        new DayGenerator("2023", "23");
    }
}
