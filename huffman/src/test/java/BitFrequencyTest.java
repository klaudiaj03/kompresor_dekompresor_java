import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.IOException;

public class BitFrequencyTest {

    @Test
    public void testGenerateStatsForNonExistingFile() throws IOException, InterruptedException {
        //given
        BitFrequency bitFrequency = new BitFrequency();
        String inputFilePath = "testFiles/nonexistingfile.txt";
        String outputFilePath = "testFiles/result.txt";
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