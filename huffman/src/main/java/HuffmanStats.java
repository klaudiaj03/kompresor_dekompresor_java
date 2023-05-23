import java.io.IOException;
import java.util.HashMap;

public class HuffmanStats {
    private HuffmanCompressor compress = new HuffmanCompressor();
    private int frequencyOf0Huffman;
    private int frequencyOf1Huffman;
    private int inputFileSize;
    private int outputFileSize;
    private int treeFileSizeHuffman;
    private String freqAll;


    public void generateStats(String inputFilePath) throws IOException, InterruptedException {
        String[] compressionModes = {"huffman", "huffman v2"};
        for (String compressionMode : compressionModes) {
            String programPath;
            if (compressionMode.equals("huffman")) {
                programPath = "C:\\Users\\Klaudia\\Desktop\\huffman\\testy\\kod\\untitled1\\src\\hufC\\huffman.exe";
            } else if (compressionMode.equals("huffman v2")) {
                programPath = "C:\\Users\\Klaudia\\Desktop\\huffman\\testy\\kod\\untitled1\\src\\hufC\\ich.exe";
            } else {
                throw new IllegalArgumentException("Nieznany tryb kompresji: " + compressionMode);
            }
            String outputFilePath = "temp.bin";
            String treeFilePath="tree.txt";
            compress.run(inputFilePath, outputFilePath, compressionMode);
            HashMap<Integer, Integer> frequencyMap = compress.getFrequencyMap(inputFilePath);
            HashMap<Integer, Integer> freqMap = compress.getFrequencyMap(treeFilePath);
            if (compressionMode.equals("huffman")) {
                frequencyOf0Huffman = freqMap.get(0) != null ? frequencyMap.get(0) : 0;
                frequencyOf1Huffman = freqMap.get(1) != null ? frequencyMap.get(1) : 0;
                inputFileSize = compress.getFileSize(inputFilePath);
                outputFileSize = compress.getFileSize(outputFilePath);
                treeFileSizeHuffman = compress.getFileSize(treeFilePath);
            } else if (compressionMode.equals("huffman v2")) {
                frequencyOf0Huffman = frequencyMap.get(0) != null ? frequencyMap.get(0) : 0;
                frequencyOf1Huffman = frequencyMap.get(1) != null ? frequencyMap.get(1) : 0;
                inputFileSize = compress.getFileSize(inputFilePath);
                outputFileSize = compress.getFileSize(outputFilePath);
                treeFileSizeHuffman = compress.getFileSize("tree.txt");
            }
            StringBuilder freqAllBuilder = new StringBuilder();
            for (int key : frequencyMap.keySet()) {
                String freq = String.valueOf((char) key) + ":" + frequencyMap.get(key);
                freqAllBuilder.append(freq).append("\n ");
            }
            if (freqAllBuilder.length() > 0) {
                freqAllBuilder.setLength(freqAllBuilder.length() - 2);
            }
            freqAll = freqAllBuilder.toString();
        }
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