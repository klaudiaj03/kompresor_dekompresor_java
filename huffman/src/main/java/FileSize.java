import java.io.*;
import java.util.HashMap;
import java.util.Map;



public class FileSize {

    private int inputFileSize;
    private int outputFileSize;
    private int treeFileSizeHuffman;

    public void generateStats(String inputFilePath, String Mode) throws IOException, InterruptedException {

        if (inputFilePath.endsWith(".txt")) {
            String outputFilePath = "temp.bin";
            String treeFilePath = "tree.txt";
            HuffmanCompressor.runC(inputFilePath, outputFilePath, Mode);

            HashMap < Integer, Integer > frequencyMap = DataSave.getFrequencyMap(inputFilePath);
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
        } else if (inputFilePath.endsWith(".bin")) {
            String outputFilePath = "temp.txt";
            String treeFilePath = "tree.txt";
            HuffmanDecompressor.decompress(inputFilePath, outputFilePath);
            HashMap < Integer, Integer > frequencyMap = DataSave.getFrequencyMap(outputFilePath);
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
        }
    }
    public static int getFileSize(String filePath) {
        File file = new File(filePath);
        return (int) file.length();
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

}