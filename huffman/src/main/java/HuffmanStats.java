import java.io.*;
import java.util.HashMap;
import java.util.Map;



public class HuffmanStats {

    private int frequencyOf0Huffman;
    private int frequencyOf1Huffman;
    private int inputFileSize;
    private int outputFileSize;
    private int treeFileSizeHuffman;
    private String freqAll;


    public void generateStats(String inputFilePath, String Mode) throws IOException, InterruptedException {

        if (inputFilePath.endsWith(".txt")) {
            String outputFilePath = "temp.bin";
            String treeFilePath = "tree.txt";
            HuffmanCompressor.runC(inputFilePath, outputFilePath, Mode);


                HashMap < Integer, Integer > frequencyMap = getFrequencyMap(inputFilePath);
                Map < Integer, Integer > bitsMap = getBinaryFrequency(outputFilePath);
                frequencyOf0Huffman = bitsMap != null ? bitsMap.get(0) : 0;
                frequencyOf1Huffman = bitsMap != null ? bitsMap.get(1) : 0;
                inputFileSize = getFileSize(inputFilePath);
                outputFileSize = getFileSize(outputFilePath);
                treeFileSizeHuffman = getFileSize(treeFilePath);
                StringBuilder freqAllBuilder = new StringBuilder();
                for (int key: frequencyMap.keySet()) {
                    String freq = (char) key + ":" + frequencyMap.get(key);
                    freqAllBuilder.append(freq).append("\n ");
                }
                if (freqAllBuilder.length() > 0) {
                    freqAllBuilder.setLength(freqAllBuilder.length() - 2);
                }
                freqAll = freqAllBuilder.toString();
        } else if (inputFilePath.endsWith(".bin")) {
                String outputFilePath = "temp.txt";
                String treeFilePath = "tree.txt";
                HuffmanDecompressor.decompress(inputFilePath, outputFilePath);
                HashMap < Integer, Integer > frequencyMap = getFrequencyMap(outputFilePath);
                Map < Integer, Integer > bitsMap = getBinaryFrequency(inputFilePath);
                frequencyOf0Huffman = bitsMap != null ? bitsMap.get(0) : 0;
                frequencyOf1Huffman = bitsMap != null ? bitsMap.get(1) : 0;
                inputFileSize = getFileSize(inputFilePath);
                outputFileSize = getFileSize(outputFilePath);
                treeFileSizeHuffman = getFileSize(treeFilePath);
                StringBuilder freqAllBuilder = new StringBuilder();
                for (int key: frequencyMap.keySet()) {
                    String freq = (char) key + ":" + frequencyMap.get(key);
                    freqAllBuilder.append(freq).append("\n ");
                }
                if (freqAllBuilder.length() > 0) {
                    freqAllBuilder.setLength(freqAllBuilder.length() - 2);
                }
                freqAll = freqAllBuilder.toString();
        }
    }

    public static HashMap<Integer, Integer> getFrequencyMap(String inputFilePath) throws IOException {
        HashMap<Integer, Integer> frequencyMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
            int c;
            while ((c = reader.read()) != -1) {
                Integer character = c;
                frequencyMap.put(character, frequencyMap.getOrDefault(character, 0) + 1);
            }
        }
        return frequencyMap;
    }

    public static Map<Integer, Integer> getBinaryFrequency(String fileName) {
        Map<Integer, Integer> binaryFrequency = new HashMap<>();

        try (DataInputStream inputStream = new DataInputStream(new FileInputStream(fileName))) {
            while (inputStream.available() > 0) {
                byte value = inputStream.readByte();
                String binaryString = String.format("%8s", Integer.toBinaryString(value & 0xFF)).replace(' ', '0');
                for (char c : binaryString.toCharArray()) {
                    int binaryValue = Character.getNumericValue(c);
                    binaryFrequency.put(binaryValue, binaryFrequency.getOrDefault(binaryValue, 0) + 1);
                }
            }
        } catch (IOException e) {
            System.err.println("Wystąpił błąd podczas odczytu pliku: " + e.getMessage());
            return null;
        }

        return binaryFrequency;
    }

    public static int getFileSize(String filePath) {
        File file = new File(filePath);
        return (int) file.length();
    }

    public int getFrequencyOf0Huffman() {
        return frequencyOf0Huffman;
    }

    public int getFrequencyOf1Huffman() {
        return frequencyOf1Huffman;
    }

    public int getInputFileSize() {
        return inputFileSize;
    }

    public int getOutputFileSize() {
        return outputFileSize;
    }

    public int getTreeFileSizeHuffman() {
        return treeFileSizeHuffman;
    }

    public String getFreqAll() {
        return freqAll;
    }
}