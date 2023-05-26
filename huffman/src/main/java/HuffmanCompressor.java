import java.io.*;


public class HuffmanCompressor {


    public static String runC(String inputFilePath, String outputFilePath, String compressionMode) throws InterruptedException, IOException {
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
            String programPath = "huffv2.exe";
            String[] command = {programPath, "-r", inputFilePath, "-s", outputFilePath, "-t", "tree2.txt", "-c x"};
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
            return null;
        }
        return null;
    }
}