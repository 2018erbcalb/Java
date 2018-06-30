
/**
 * This document is not owned by me
 * It is the base class in which I an utilizing to export arrayLists to CSV
 * I in no way claim responsibility for writing this class
 * Simply like any import class statement, I am using its function without 
 * claiming ownership
 * I found the writeLine code on the following website
 * https://www.mkyong.com/java/how-to-export-data-to-csv-file-java/
 * I found the readFile code on the following website
 * https://stackoverflow.com/questions/33034833/converting-csv-file-into-2d-array
 * Although I have tweaked the code slightly to match my specific needs
 */

import java.io.*;
import java.util.*;
public class CSVUtils {
    private static final char DEFAULT_SEPARATOR = ',';
    private static String followCVSformat(String value) {
        String result = value;
        if (result.contains("\"")) {
            result = result.replace("\"", "\"\"");
        }
        return result;
    }
    public static void writeLine(Writer w, List<String> values) throws IOException {
        boolean first = true;
        char separators;
        separators = DEFAULT_SEPARATOR;
        char customQuote = ' ';
        StringBuilder sb = new StringBuilder();
        for (String value : values) {
            if (!first) {
                sb.append(separators);
            }
            if (customQuote == ' ') {
                sb.append(followCVSformat(value));
            } else {
                sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
            }
            first = false;
        }
        sb.append("\n");
        w.append(sb.toString());
    }
    public static String[][] readFile(String fileName) throws java.io.IOException{
        String fName = fileName;
        String thisLine;
        FileInputStream fis = new FileInputStream(fName);
        DataInputStream myInput = new DataInputStream(fis);
        List<String[]> lines = new ArrayList<String[]>();
        while ((thisLine = myInput.readLine()) != null) {
             lines.add(thisLine.split(","));
        }
        String[][] arr = new String[lines.size()][0];
        lines.toArray(arr);
        return arr;
    }
}