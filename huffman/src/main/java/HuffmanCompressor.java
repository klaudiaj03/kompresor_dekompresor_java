import java.io.*;


public class HuffmanCompressor {


    public static String runC(String inputFilePath, String outputFilePath, String compressionMode) throws InterruptedException, IOException {
        if ("huffman".equals(compressionMode)) {
            String programPath = "C:\\Users\\Klaudia\\Desktop\\Huffman JAVA\\2023L_JIMP2_proj_git_gr1\\huffman\\huffman.exe";
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
        } else if ("huffman v2".equals(compressionMode)) {
            String programPath = "huffv2.exe";
            String[] command = {programPath, "-r", inputFilePath, "-s", outputFilePath, "-t", "tree2.txt", "-c x"};
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
            return null;
        }
        return null;
    }
}