import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DecompressionModeTest {


    @Test
    public void testRunD_withInvalidInputFileExtension_returnsErrorMessage() throws IOException, InterruptedException {
        // Arrange
        String inputFilePath = "testFiles/test2.txt";
        String outputFilePath = "testFiles/result.txt";
        String decompressionMode = "huffman";

        // Act
        String result = DecompressionMode.runD(inputFilePath, outputFilePath, decompressionMode);

        // Assert
        String expectedErrorMessage = "Błędny plik wejściowy. Oczekiwano pliku o rozszerzeniu .bin.";
        Assertions.assertEquals(expectedErrorMessage, result);
    }

    @Test
    public void testRunD_withInvalidOutputFileExtension_returnsErrorMessage() throws IOException, InterruptedException {
        // Arrange
        String inputFilePath = "testFiles/test2.bin";
        String outputFilePath = "testFiles/result.bin";
        String decompressionMode = "huffman";

        // Act
        String result = DecompressionMode.runD(inputFilePath, outputFilePath, decompressionMode);

        // Assert
        String expectedErrorMessage = "Błędny plik wyjściowy. Oczekiwano pliku o rozszerzeniu .txt.";
        Assertions.assertEquals(expectedErrorMessage, result);
    }

    @Test
    public void testRunD_withNonExistingInputFile_returnsErrorMessage() throws IOException, InterruptedException {
        // Arrange
        String inputFilePath = "testFiles/non_existing.bin";
        String outputFilePath = "testFiles/result.txt";
        String decompressionMode = "huffman";

        // Act
        String result = DecompressionMode.runD(inputFilePath, outputFilePath, decompressionMode);

        // Assert
        String expectedErrorMessage = "Plik wejściowy nie istnieje.";
        Assertions.assertEquals(expectedErrorMessage, result);
    }

    @Test
    public void testRunD_withMissingTreeFileInHuffmanMode_returnsErrorMessage() throws IOException, InterruptedException {
        // Arrange
        String inputFilePath = "testFiles/test2.bin";
        String outputFilePath = "testFiles/result.txt";
        String decompressionMode = "huffman";

        String filePath = "tree.txt";
        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            Files.delete(path);}

        // Act
        String result = DecompressionMode.runD(inputFilePath, outputFilePath, decompressionMode);

        // Assert
        String expectedErrorMessage = "Plik tree.txt nie istnieje. Należy najpierw przeprowadzić kompresję w trybie huffman.";
        Assertions.assertEquals(expectedErrorMessage, result);
    }

    @Test
    public void testRunD_withMissingTreeFileInHuffmanV2Mode_returnsErrorMessage() throws IOException, InterruptedException {
        // Arrange
        String inputFilePath = "testFiles/test2.bin";
        String outputFilePath = "testFiles/result.txt";
        String decompressionMode = "huffman v2";

        // Act
        String result = DecompressionMode.runD(inputFilePath, outputFilePath, decompressionMode);

        // Assert
        String expectedErrorMessage = "Plik tree.txt nie istnieje. Należy najpierw przeprowadzić kompresję w trybie huffman v2.";
        Assertions.assertEquals(expectedErrorMessage, result);
    }

    @Test
    public void testRunD_withUnknownDecompressionMode_returnsErrorMessage() throws IOException, InterruptedException {
        // Arrange
        String inputFilePath = "testFiles/test2.bin";
        String outputFilePath = "testFiles/result.txt";
        String decompressionMode = "unknown";

        // Act
        String result = DecompressionMode.runD(inputFilePath, outputFilePath, decompressionMode);

        // Assert
        String expectedErrorMessage = "Nieznany tryb dekompresji.";
        Assertions.assertEquals(expectedErrorMessage, result);
    }
}
