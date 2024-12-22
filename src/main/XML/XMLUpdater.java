package main.XML;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.net.URI;
import java.nio.charset.StandardCharsets;

/*
 * Adds correct path to Actions/Exec/Arguments. Shall be run from createTask.bat
 */
public class XMLUpdater {

    private static void convertEncoding(File inputFile, File outputFile, java.nio.charset.Charset fromCharset, java.nio.charset.Charset toCharset) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), fromCharset));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), toCharset))) {

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    public static void changeRefresherLocation(File xml_file, String correctPath, int argumentNumber) throws Exception {

        // The docBuilder below needs to read this file saved with the utf-16BE encoding, but WTM has
        // to read the file saved with the utf-8 encoding, so we make a tmp file, which is the same as
        // the xml_file, but saved with utf16-BE encoding, parse it and then save the changes to the
        // original utf-8 encoded file, so that WTM can read it.
        // ---
        // DEV NOTE: I hate this encoding stuff, I lost like 5h figuring this out, but it works now :)
        File tempUtf16File = new File(xml_file.getParent(), "temp_utf16.xml");
        convertEncoding(xml_file, tempUtf16File, StandardCharsets.UTF_8, StandardCharsets.UTF_16BE);

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        
        // we parse the tmp UTF-16BE encoded file
        Document xml_doc = docBuilder.parse(tempUtf16File);

        // we change the <Arguments> tag
        Element arguments = (Element) xml_doc.getElementsByTagName("Arguments").item(0);

        String newTextContent = ("/c " + '\"' + correctPath + '\"' + " " + argumentNumber).replace("%20", " ");
        arguments.setTextContent(newTextContent);

        // we save the changes into the original UTF-8 encoded xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty("omit-xml-declaration", "yes");
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(xml_file), StandardCharsets.UTF_8)) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-16\"?>\n"); // Add UTF-16 declaration manually
            transformer.transform(new DOMSource(xml_doc), new StreamResult(writer));
        }

        tempUtf16File.delete(); // we remove the tmp file
    }
    
    public static void main(String[] args) {

        if (args.length < 2) {

            throw new RuntimeException("Incorrect Usage. Correct Usage: java XMLUpdater <correctFilePath> <pathToXmls>");
        }
        
        // we get and fix the path
        String correctPath = args[0];
        correctPath = correctPath.replace("\\\\", "/");
        correctPath = correctPath.replace(" ", "%20");

        // System.out.println();
        // System.out.println(correctPath);
        // System.out.println();

        try {

            // System.out.println();
            // System.out.println(new URI(correctPath + "/DTRN_Refresher_Daily.xml"));
            // System.out.println(new URI(correctPath + "/DTRN_Refresher_Weekly.xml"));
            // System.out.println();

            // we change the <Arguments> tag
            changeRefresherLocation(new File(new URI("file:///" + correctPath + "/DTRN_Refresher_Daily.xml")), correctPath + "/refresher.bat", 1);
            changeRefresherLocation(new File(new URI("file:///" + correctPath + "/DTRN_Refresher_Weekly.xml")), correctPath + "/refresher.bat", 2);

        } catch (Exception e) { e.printStackTrace(); }
    }
}
