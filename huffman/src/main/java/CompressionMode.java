import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;


public class CompressionMode {


    public static String runC(String inputFilePath, String outputFilePath, String compressionMode) throws InterruptedException, IOException {
        if (inputFilePath == null || !inputFilePath.toLowerCase().endsWith(".txt")) {
            return "Błędny plik wejściowy. Oczekiwano pliku o rozszerzeniu .txt.";
        }

        if (outputFilePath == null || !outputFilePath.toLowerCase().endsWith(".bin")) {
            return "Błędny plik wyjściowy. Oczekiwano pliku o rozszerzeniu .bin.";
        }

        if (!Files.exists(Paths.get(inputFilePath))) {
            return "Plik wejściowy nie istnieje.";
        }
        if ("huffman".equals(compressionMode)) {
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
        } else if ("huffman v2".equals(compressionMode)) {
            String programPath = "huffv2.exe";
            String[] command = {programPath, "-r", inputFilePath, "-s", outputFilePath, "-t", "tree2.txt", "-c x"};
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
            return null;
        }
        return "Nieznany tryb kompresji";
    }
}