package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {

    public void write(String fileName, String content) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {

            writer.write(content);
        }
        catch (IOException e) {

            System.out.println("[WRITER]" + e.getMessage());
        }
    }
}