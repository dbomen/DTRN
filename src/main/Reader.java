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

    public String readJson(String fileName) throws IOException {

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            StringBuilder json = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {

                json.append(line);
            }

            return json.toString();
        }
        catch (IOException e) {

            throw new IOException("[READER]" + e.getMessage());
        }
    }
}