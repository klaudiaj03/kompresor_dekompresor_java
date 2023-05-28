import java.io.*;
import java.util.Map;

public class BitWriter {
    public static void decompress(String binaryString, String outputFilename) {
        Map<String, Character> asciiMap = ReadTree.getAsciiMap("tree.txt");
        try (BufferedWriter outputFile = new BufferedWriter(new FileWriter(outputFilename))) {
            StringBuilder result = new StringBuilder();
            int j = 0;
            while (j < binaryString.length()) {
                StringBuilder code = new StringBuilder();
                code.append(binaryString.charAt(j));
                while (asciiMap.get(code.toString()) == null) {
                    j++;
                    code.append(binaryString.charAt(j));
                }
                result.append(asciiMap.get(code.toString()));
                j++;
            }
            outputFile.write(result.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
