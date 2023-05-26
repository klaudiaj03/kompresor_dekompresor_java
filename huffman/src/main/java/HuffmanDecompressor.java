import java.io.*;
import java.util.Map;

public class HuffmanDecompressor {

    public static void runD(String inputFilePath, String outputFilePath, String decompressionMode) throws IOException, InterruptedException {
        if (decompressionMode.equals("huffman")) {
            decompress(inputFilePath, outputFilePath);
        } else if (decompressionMode.equals("huffman v2")) {
            String programPath = "huffv2.exe";
            String[] command = {programPath, "-r", inputFilePath, "-s", outputFilePath, "-t", "tree2.txt", "-d x"};
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();

        }
    }

    public static void decompress(String inputFilename, String outputFilename) {
        Map<String, Character> asciiMap = AsciiMapBuilder.getAsciiMap("tree.txt");
        try (DataInputStream inputFile = new DataInputStream(new FileInputStream(inputFilename));
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

