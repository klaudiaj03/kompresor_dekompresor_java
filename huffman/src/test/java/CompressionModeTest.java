import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CompressionModeTest {

    @Test
    public void testRunC_withValidInputFileAndOutputFile_returnsOutput() throws InterruptedException, IOException {
        // Arrange
        String inputFilePath = "testFiles/test2.txt";
        String outputFilePath = "testFiles/test2.bin";
        String compressionMode = "huffman";

        // Act
        String result = CompressionMode.runC(inputFilePath, outputFilePath, compressionMode);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertNotEquals("Kompresja zakończona powodzeniem.", result);
        String filePath = "tree.txt";
        Path path = Paths.get(filePath);
        Files.delete(path);
    }

    @Test
    public void testRunC_withInvalidInputFileExtension_returnsErrorMessage() throws InterruptedException, IOException {
        // Arrange
        String inputFilePath = "testFiles/input.csv";
        String outputFilePath = "testFiles/result.bin";
        String compressionMode = "huffman";

        // Act
        String result = CompressionMode.runC(inputFilePath, outputFilePath, compressionMode);

        // Assert
        String expectedErrorMessage = "Błędny plik wejściowy. Oczekiwano pliku o rozszerzeniu .txt.";
        Assertions.assertEquals(expectedErrorMessage, result);
    }

    @Test
    public void testRunC_withInvalidOutputFileExtension_returnsErrorMessage() throws InterruptedException, IOException {
        // Arrange
        String inputFilePath = "testFiles/test2.txt";
        String outputFilePath = "testFiles/result.txt";
        String compressionMode = "huffman";

        // Act
        String result = CompressionMode.runC(inputFilePath, outputFilePath, compressionMode);

        // Assert
        String expectedErrorMessage = "Błędny plik wyjściowy. Oczekiwano pliku o rozszerzeniu .bin.";
        Assertions.assertEquals(expectedErrorMessage, result);
    }

    @Test
    public void testRunC_withNonExistingInputFile_returnsErrorMessage() throws InterruptedException, IOException {
        // Arrange
        String inputFilePath = "testFiles/non_existing.txt";
        String outputFilePath = "testFiles/result.bin";
        String compressionMode = "huffman";

        // Act
        String result = CompressionMode.runC(inputFilePath, outputFilePath, compressionMode);

        // Assert
        String expectedErrorMessage = "Plik wejściowy nie istnieje.";
        Assertions.assertEquals(expectedErrorMessage, result);
    }

    @Test
    public void testRunC_withUnknownCompressionMode_returnsErrorMessage() throws InterruptedException, IOException {
        // Arrange
        String inputFilePath = "testFiles/test2.txt";
        String outputFilePath = "testFiles/result.bin";
        String compressionMode = "unknown";

        // Act
        String result = CompressionMode.runC(inputFilePath, outputFilePath, compressionMode);

        // Assert
        String expectedErrorMessage = "Nieznany tryb kompresji";
        Assertions.assertEquals(expectedErrorMessage, result);
    }
}
