import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class BitFrequencyTest {

    @Test
    public void testGenerateStatsForNonExistingFile() throws IOException, InterruptedException {
        //given
        BitFrequency bitFrequency = new BitFrequency();
        String inputFilePath = "nonexistingfile.txt";
        String outputFilePath = "output.txt";
        String mode = "mode";

        //when
        bitFrequency.generateStats(inputFilePath, outputFilePath, mode);

       //then
        Assertions.assertEquals(0, bitFrequency.getFrequencyOf0Huffman());
        Assertions.assertEquals(0, bitFrequency.getFrequencyOf1Huffman());
    }
    @Test
    public void testInitialFrequencies() {
        BitFrequency bitFrequency = new BitFrequency();
        Assertions.assertEquals(0, bitFrequency.getFrequencyOf0Huffman());
        Assertions.assertEquals(0, bitFrequency.getFrequencyOf1Huffman());
    }

}