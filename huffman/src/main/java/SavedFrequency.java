import java.io.*;
import java.util.HashMap;

public class SavedFrequency {
        private String freqAll;

        public void generateStats(String inputFilePath, String outputFilePath, String Mode) throws IOException, InterruptedException {

            if (inputFilePath.endsWith(".txt")) {
                HuffmanCompressor.runC(inputFilePath, outputFilePath, Mode);


                HashMap< Integer, Integer > frequencyMap = DataSave.getFrequencyMap(inputFilePath);
                StringBuilder freqAllBuilder = new StringBuilder();
                for (int key: frequencyMap.keySet()) {
                    String freq = (char) key + ":" + frequencyMap.get(key);
                    freqAllBuilder.append(freq).append("\n ");
                }
                if (freqAllBuilder.length() > 0) {
                    freqAllBuilder.setLength(freqAllBuilder.length() - 2);
                }
                freqAll = freqAllBuilder.toString();
            } else if (inputFilePath.endsWith(".bin")) {
                HuffmanDecompressor.decompress(inputFilePath, outputFilePath);
                HashMap < Integer, Integer > frequencyMap = DataSave.getFrequencyMap(outputFilePath);
                StringBuilder freqAllBuilder = new StringBuilder();
                for (int key: frequencyMap.keySet()) {
                    String freq = (char) key + ":" + frequencyMap.get(key);
                    freqAllBuilder.append(freq).append("\n ");
                }
                if (freqAllBuilder.length() > 0) {
                    freqAllBuilder.setLength(freqAllBuilder.length() - 2);
                }
                freqAll = freqAllBuilder.toString();
            }
        }
        public String getFreqAll() {
            return freqAll;
        }
    }
