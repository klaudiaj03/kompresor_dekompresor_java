import java.io.*;
import java.util.HashMap;

public class DataSave {
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
}