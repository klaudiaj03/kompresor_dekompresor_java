import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ReadTree {

    public static Map<String, Character> getAsciiMap(String fileName) {
        Map<String, Character> asciiMap = new HashMap<>();
        try (BufferedReader buffer = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = buffer.readLine()) != null) {
                String[] parts = line.split(" ");
                int asciiCode = Integer.parseInt(parts[0]);
                String binaryCode = parts[1];
                char asciiChar = (char) asciiCode;
                asciiMap.put(binaryCode, asciiChar);
            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
        return asciiMap;
    }

    public static Map<Integer, String> getAsciiCodeMap(String filePath) throws IOException {
        Map<Integer, String> codeMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                if (parts.length == 2) {
                    int asciiCode = 0;
                    String code = "";

                    if (!parts[0].isEmpty()) {
                        asciiCode = Integer.parseInt(parts[0]);
                    }
                    if (!parts[1].isEmpty()) {
                        code = parts[1];
                    }

                    codeMap.put(asciiCode, code);
                }
            }
        }
        return codeMap;
    }
}
