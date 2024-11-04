package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Reader {

    public String read(String fileName) throws IOException {

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            return reader.readLine();
        }
        catch (IOException e) {

            throw new IOException("[READER]" + e.getMessage());
        }
    }
}