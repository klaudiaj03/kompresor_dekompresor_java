import java.io.*;
import java.util.HashMap;


public class HuffmanCompressor {


    public static String run(String inputFilePath, String outputFilePath, String compressionMode) throws IOException, InterruptedException {
        if (compressionMode.equals("huffman")) {
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
        } else if (compressionMode.equals("huffman v2")) {
            String programPath = "C:\\Users\\Klaudia\\Desktop\\huffman\\testy\\kod\\untitled1\\src\\hufC\\ich.exe";
            programPath = "huffv2.exe";
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
        } else {
            throw new IllegalArgumentException("Nieznany tryb kompresji: " + compressionMode);
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

}