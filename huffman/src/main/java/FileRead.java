import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileRead {
    public static Map<Integer, Integer> getBinaryFrequency(String fileName) {
        Map<Integer, Integer> binaryFrequency = new HashMap<>();


        try (DataInputStream inputStream = new DataInputStream(new FileInputStream(fileName))) {
            while (inputStream.available() > 0) {
                byte value = inputStream.readByte();
                String binaryString = String.format("%8s", Integer.toBinaryString(value & 0xFF)).replace(' ', '0');
                for (char c : binaryString.toCharArray()) {
                    int binaryValue = Character.getNumericValue(c);
                    binaryFrequency.put(binaryValue, binaryFrequency.getOrDefault(binaryValue, 0) + 1);
                }
            }
        } catch (IOException e) {
            return null;
        }

        return binaryFrequency;
    }
}

