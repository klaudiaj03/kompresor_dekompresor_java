import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class HuffmanCompressor {

    interface CompressionStrategy {
        String compress(String inputFilePath, String outputFilePath) throws IOException, InterruptedException;
    }

    static class HuffmanCompressionStrategy implements CompressionStrategy {
        @Override
        public String compress(String inputFilePath, String outputFilePath) throws IOException, InterruptedException {
            String programPath = "huffman.exe";
            String[] command = {programPath, "-i", inputFilePath, "-o", outputFilePath, "-c"};
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
            process.waitFor();
            return result.toString();
        }
    }

    static class HuffmanV2CompressionStrategy implements CompressionStrategy {
        @Override
        public String compress(String inputFilePath, String outputFilePath) throws IOException, InterruptedException {
            String programPath = "huffv2.exe";
            String treeFilePath= "tree.txt";
            String[] command = {programPath, "-r", inputFilePath, "-s", outputFilePath, "-t", treeFilePath, "-c x"};
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
            process.waitFor();
            return result.toString();
        }
    }

    public static String run(String inputFilePath, String outputFilePath, String compressionMode) throws IOException, InterruptedException {
        CompressionStrategy strategy = getCompressionStrategy(compressionMode);
        if (strategy != null) {
            return strategy.compress(inputFilePath, outputFilePath);
        } else {
            throw new IllegalArgumentException("Nieznany tryb kompresji: " + compressionMode);
        }
    }

    private static CompressionStrategy getCompressionStrategy(String compressionMode) {
        Map<String, CompressionStrategy> strategies = new HashMap<>();
        strategies.put("huffman", new HuffmanCompressionStrategy());
        strategies.put("huffman v2", new HuffmanV2CompressionStrategy());
        return strategies.get(compressionMode);
    }
}
