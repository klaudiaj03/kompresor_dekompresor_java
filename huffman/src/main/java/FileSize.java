import java.io.*;



public class FileSize {

    private int inputFileSize;
    private int outputFileSize;
    private int treeFileSizeHuffman;

    public void generateStats(String inputFilePath, String outputFilePath, String Mode) throws IOException, InterruptedException {

        if (inputFilePath.endsWith(".txt")) {
            String treeFilePath = "tree.txt";
            HuffmanCompressor.runC(inputFilePath, outputFilePath, Mode);
            inputFileSize = getFileSize(inputFilePath);
            outputFileSize = getFileSize(outputFilePath);
            treeFileSizeHuffman = getFileSize(treeFilePath);
        } else if (inputFilePath.endsWith(".bin")) {
            String treeFilePath = "tree.txt";
            HuffmanDecompressor.decompress(inputFilePath, outputFilePath);
            inputFileSize = getFileSize(inputFilePath);
            outputFileSize = getFileSize(outputFilePath);
            treeFileSizeHuffman = getFileSize(treeFilePath);
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