import java.io.*;
import java.util.Map;

public class BitReader {

    public static void decompress(String inputFilename, String outputFilename) {
        Map<String, Character> asciiMap = ReadTree.getAsciiMap("tree.txt");
        try (DataInputStream inputFile = new DataInputStream(new FileInputStream(inputFilename))) {

            int cut = inputFile.read();

            int remainingBytes = inputFile.available();
            byte[] data = new byte[remainingBytes];
            inputFile.readFully(data);
            remainingBytes--;

            StringBuilder binaryString = new StringBuilder();
            for (int i = 0; i < remainingBytes; i++) {
                byte currentByte = data[i];
                for (int j = 0; j < 8; j++) {
                    boolean bitSet = ((currentByte >> (7 - j) & 1) == 1);
                    binaryString.append(bitSet ? '1' : '0');
                }
            }

            byte lastByte = data[remainingBytes];
            int maskedByte = (lastByte & 0xFF);
            String end = String.format("%8s", Integer.toBinaryString(maskedByte)).replace(' ', '0');
            end = end.substring(0, end.length() - cut);
            binaryString.append(end);
            BitWriter.decompress(binaryString.toString(), outputFilename);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
