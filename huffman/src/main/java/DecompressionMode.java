import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DecompressionMode {
    public static String runD(String inputFilePath, String outputFilePath, String decompressionMode) throws IOException, InterruptedException {
        if (inputFilePath == null || !inputFilePath.toLowerCase().endsWith(".bin")) {
            return "Błędny plik wejściowy. Oczekiwano pliku o rozszerzeniu .bin.";
        }

        if (outputFilePath == null || !outputFilePath.toLowerCase().endsWith(".txt")) {
            return "Błędny plik wyjściowy. Oczekiwano pliku o rozszerzeniu .txt.";
        }

        if (!Files.exists(Paths.get(inputFilePath))) {
            return "Plik wejściowy nie istnieje.";
        }

        if ("huffman".equals(decompressionMode)) {
            if (!Files.exists(Paths.get("tree.txt"))) {
                return "Plik tree.txt nie istnieje. Należy najpierw przeprowadzić kompresję w trybie huffman.";
            } else {
                HuffmanDecompressor.decompress(inputFilePath, outputFilePath);
                return "Dekompresja zakończona sukcesem.";
            }
        } else if ("huffman v2".equals(decompressionMode)) {
            if (!Files.exists(Paths.get("tree2.txt"))) {
                return "Plik tree.txt nie istnieje. Należy najpierw przeprowadzić kompresję w trybie huffman v2.";
            } else {
                String programPath = "huffv2.exe";
                String[] command = {programPath, "-r", inputFilePath, "-s", outputFilePath, "-t", "tree2.txt", "-d x"};
                Process process = Runtime.getRuntime().exec(command);
                process.waitFor();
                return "Dekompresja zakończona sukcesem.";
            }
        }

        return "Nieznany tryb dekompresji.";
    }
}
