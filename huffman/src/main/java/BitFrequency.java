import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class BitFrequency {

    private int frequencyOf0Huffman;
    private int frequencyOf1Huffman;

    public void generateStats(String inputFilePath, String Mode) throws IOException, InterruptedException {
        if (inputFilePath.endsWith(".txt")) {
            String outputFilePath = "temp.bin";
            HuffmanCompressor.runC(inputFilePath, outputFilePath, Mode);
            Map < Integer, Integer > bitsMap = FileRead.getBinaryFrequency(outputFilePath);
            frequencyOf0Huffman = bitsMap != null ? bitsMap.get(0) : 0;
            frequencyOf1Huffman = bitsMap != null ? bitsMap.get(1) : 0;
        } else if (inputFilePath.endsWith(".bin")) {
            String outputFilePath = "temp.txt";
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
