import java.io.*;
import java.util.Map;


public class BitFrequency {

    private int frequencyOf0Huffman;
    private int frequencyOf1Huffman;

    public void generateStats(String inputFilePath, String outputFilePath, String Mode) throws IOException, InterruptedException {
        if (inputFilePath.endsWith(".txt")) {
            CompressionMode.runC(inputFilePath, outputFilePath, Mode);
            Map < Integer, Integer > bitsMap = FileRead.getBinaryFrequency(outputFilePath);
            frequencyOf0Huffman = bitsMap != null ? bitsMap.get(0) : 0;
            frequencyOf1Huffman = bitsMap != null ? bitsMap.get(1) : 0;
        } else if (inputFilePath.endsWith(".bin")) {
            HuffmanDecompressor.decompress(inputFilePath, outputFilePath);
            Map < Integer, Integer > bitsMap = FileRead.getBinaryFrequency(inputFilePath);
            frequencyOf0Huffman = bitsMap != null ? bitsMap.get(0) : 0;
            frequencyOf1Huffman = bitsMap != null ? bitsMap.get(1) : 0;
        }
    }
    public int getFrequencyOf0Huffman() {
        return frequencyOf0Huffman;
    }

    public int getFrequencyOf1Huffman() {
        return frequencyOf1Huffman;
    }

}
