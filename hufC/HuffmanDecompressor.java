package hufC;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class HuffmanDecompressor {

    public static void main(String[] args) {
        Map<String, Character> asciiMap = getAsciiMap("tree.txt");
        String input = "wynikC.bin";
        String output = "wynikD.txt";
        decompress(input, output, asciiMap);
    }

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

    public static void decompress(String inputFilename, String outputFilename, Map<String, Character> asciiMap) {
        try (DataInputStream inputFile = new DataInputStream(new FileInputStream(new File(inputFilename)));
             BufferedWriter outputFile = new BufferedWriter(new FileWriter(outputFilename))) {

            int cut = inputFile.read();

            int remainingBytes = inputFile.available();
            byte[] data = new byte[remainingBytes];
            inputFile.readFully(data);
            remainingBytes--;

            StringBuilder binaryString = new StringBuilder();
            for (int i = 0; i < remainingBytes; i++) {
                byte currentByte = data[i];
                for (int j = 0; j < 8; j++) {
                    boolean bitSet = ((currentByte >> (7 - j) & 1) == 1);
                    binaryString.append(bitSet ? '1' : '0');
                }
            }

            byte lastByte = data[remainingBytes];
            int maskedByte = (lastByte & 0xFF);
            String end = String.format("%8s", Integer.toBinaryString(maskedByte)).replace(' ', '0');
            end = end.substring(0, end.length() - cut);
            binaryString.append(end);

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

