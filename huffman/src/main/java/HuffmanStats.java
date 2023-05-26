import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class HuffmanStats {

    private int frequencyOf0Huffman;
    private int frequencyOf1Huffman;
    private int inputFileSize;
    private int outputFileSize;
    private int treeFileSizeHuffman;
    private String freqAll;


    public void generateStats(String inputFilePath) throws IOException, InterruptedException {
        String[] compressionModes = {
                "huffman",
                "huffman v2"
        };
        for (String compressionMode: compressionModes) {
            String outputFilePath = "temp.bin";
            String treeFilePath = "tree.txt";
            HuffmanCompressor.run(inputFilePath, outputFilePath, compressionMode);
            HashMap < Integer, Integer > frequencyMap = getFrequencyMap(inputFilePath);
            HashMap < Integer, Integer > freqMap = getFrequencyMap(treeFilePath);
            frequencyOf0Huffman = freqMap.get(0) != null ? frequencyMap.get(0) : 0;
            frequencyOf1Huffman = freqMap.get(1) != null ? frequencyMap.get(1) : 0;
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