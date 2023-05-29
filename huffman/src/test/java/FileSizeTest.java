import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSizeTest {

    @Test
    public void testGenerateStats_withTxtInputFile_calculatesCorrectFileSizes() throws IOException, InterruptedException {
        // Arrange
        String inputFilePath = "testFiles/test2.txt";
        String outputFilePath = "testFiles/test2.bin";
        String mode = "huffman";

        FileSize fileSize = new FileSize();

        // Act
        fileSize.generateStats(inputFilePath, outputFilePath, mode);

        // Assert
        int expectedInputFileSize = 12;  // Replace with expected input file size in bytes
        int expectedOutputFileSize = 4;  // Replace with expected output file size in bytes
        int expectedTreeFileSize = 28;  // Replace with expected tree file size in bytes

        Assertions.assertEquals(expectedInputFileSize, fileSize.getInputFileSize());
        Assertions.assertEquals(expectedOutputFileSize, fileSize.getOutputFileSize());
        Assertions.assertEquals(expectedTreeFileSize, fileSize.getTreeFileSizeHuffman());
    }

    @Test
    public void testGenerateStats_withBinInputFile_calculatesCorrectFileSizes() throws IOException, InterruptedException {
        // Arrange
        String inputFilePath = "testFiles/test2.bin";
        String outputFilePath = "testFiles/test2.txt";
        String mode = "huffman";

        FileSize fileSize = new FileSize();

        // Act
        fileSize.generateStats(inputFilePath, outputFilePath, mode);

        // Assert
        int expectedInputFileSize = 4;  // Replace with expected input file size in bytes
        int expectedOutputFileSize = 12;  // Replace with expected output file size in bytes
        int expectedTreeFileSize = 28;  // Replace with expected tree file size in bytes

        Assertions.assertEquals(expectedInputFileSize, fileSize.getInputFileSize());
        Assertions.assertEquals(expectedOutputFileSize, fileSize.getOutputFileSize());
        Assertions.assertEquals(expectedTreeFileSize, fileSize.getTreeFileSizeHuffman());
        String filePath = "tree.txt";
        Path path = Paths.get(filePath);
        Files.delete(path);
    }
}
