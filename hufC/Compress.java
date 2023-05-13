package hufC;
import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class Compress {


        public static void main(String[] args) throws IOException, InterruptedException {
            HuffmanGUI huffmanGUI = new HuffmanGUI();
            String programPath = "C:\\Users\\Klaudia\\Desktop\\huffman\\testy\\kod\\untitled1\\src\\hufC\\huffman.exe"; // ścieżka do programu w C
            String inputFilePath = "C:\\Users\\Klaudia\\Desktop\\huffman\\testy\\kod\\untitled1\\src\\hufC\\test.txt";
            String outputFilePath = "C:\\Users\\Klaudia\\Desktop\\huffman\\testy\\kod\\untitled1\\src\\hufC\\wynik.bin";
        }
    public static String run(String inputFilePath, String outputFilePath, String compressionMode) throws IOException, InterruptedException {
        if (compressionMode.equals("huffman")) {
            String programPath = "C:\\Users\\Klaudia\\Desktop\\huffman\\testy\\kod\\untitled1\\src\\hufC\\huffman.exe";
            String[] command = {programPath, "-i", inputFilePath, "-o", outputFilePath, "-c"}; // argumenty dla programu w C
            Process process = Runtime.getRuntime().exec(command); // uruchomienie programu w C
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) { // odczyt wyników programu w C i zapisanie ich do zmiennej result
                result.append(line).append("\n");
            }
            process.waitFor(); // oczekiwanie na zakończenie programu w C
            return result.toString(); // zwrócenie wyniku działania programu w C
        } else if (compressionMode.equals("huffman v2")) {
            String programPath = "C:\\Users\\Klaudia\\Desktop\\huffman\\testy\\kod\\untitled1\\src\\hufC\\ich.exe";
            programPath = "C:\\Users\\Klaudia\\Desktop\\huffman\\testy\\kod\\untitled1\\src\\hufC\\ich.exe";
            String treeFilePath= "C:\\Users\\Klaudia\\Desktop\\huffman\\testy\\kod\\untitled1\\tree.txt";
            String[] command = {programPath, "-r", inputFilePath, "-s", outputFilePath, "-t", treeFilePath, "-c x"}; // argumenty dla programu w C
            Process process = Runtime.getRuntime().exec(command); // uruchomienie programu w C
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) { // odczyt wyników programu w C i zapisanie ich do zmiennej result
                result.append(line).append("\n");
            }
            process.waitFor(); // oczekiwanie na zakończenie programu w C
            return result.toString(); // zwrócenie wyniku działania programu w C
        } else {
            throw new IllegalArgumentException("Nieznany tryb kompresji: " + compressionMode); // rzucenie wyjątku, gdy wybrany tryb jest nieznany
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




