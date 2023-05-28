import javafx.application.Platform;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HuffmanController {
    private HuffmanClient huffmanClient;

    public HuffmanController(HuffmanClient huffmanClient) {
        this.huffmanClient = huffmanClient;
    }

    public void registerEventHandlers() {

        huffmanClient.getInputFileButton().setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(huffmanClient.getPrimaryStage());
            if (selectedFile != null) {
                huffmanClient.getInputField().setText(selectedFile.getAbsolutePath());
            }
        });

        huffmanClient.getOutputFileButton().setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showSaveDialog(huffmanClient.getPrimaryStage());
            if (selectedFile != null) {
                huffmanClient.getOutputField().setText(selectedFile.getAbsolutePath());
            }
        });
        CompessionMode compessionMode= new CompessionMode();
        huffmanClient.getCompressButton().setOnAction(e -> {
            try {
                String mode = compessionMode.getSelectedMode();
                String inputFilePath = huffmanClient.getInputField().getText();
                String outputFilePath = huffmanClient.getOutputField().getText();
                String result = HuffmanCompressor.runC(inputFilePath, outputFilePath, mode);
                huffmanClient.getResultLabel().setText(result);
            } catch (IOException | IllegalArgumentException | InterruptedException ex) {
                huffmanClient.getResultLabel().setText("Błąd podczas kompresji: " + ex.getMessage());
            }
        });

        huffmanClient.getDecompressButton().setOnAction(e -> {
            try {
                String mode = compessionMode.getSelectedMode();
                String inputFilePath = huffmanClient.getInputField().getText();
                String outputFilePath = huffmanClient.getOutputField().getText();
                HuffmanDecompressor.runD(inputFilePath, outputFilePath, mode);
            } catch (IOException | IllegalArgumentException | InterruptedException ex) {
                huffmanClient.getResultLabel().setText("Błąd podczas dekompresji: " + ex.getMessage());
            }
        });

        huffmanClient.getTreeButton().setOnAction(e -> {
            String treeFilePath = "tree.txt";
            String inputFilePath = huffmanClient.getInputField().getText();

            huffmanClient.getTreeButton().setDisable(true);
            huffmanClient.getSaveImageButton().setDisable(true);

            Thread thread = new Thread(() -> {
                try {
                    HuffmanTree huffmanTree = new HuffmanTree();
                    huffmanTree.buildTreeFromFile(treeFilePath);

                    huffmanTree.displayTree(inputFilePath, huffmanTree.getRoot(), huffmanClient.getTreeView(), 680, 80.0, 100);
                    Platform.runLater(() -> {
                        huffmanClient.getSaveImageButton().setDisable(false);
                        huffmanClient.getTreeButton().setDisable(false);
                    });
                } catch (IOException ex) {
                    Platform.runLater(() -> {
                        huffmanClient.getResultLabel().setText("Błąd podczas tworzenia drzewa: " + ex.getMessage());
                        huffmanClient.getTreeButton().setDisable(false);
                    });
                }
            });
            thread.start();
        });

        huffmanClient.getSaveImageButton().setOnAction(e -> {
            try {
                WritableImage snapshot = huffmanClient.getTreeView().snapshot(new SnapshotParameters(), null);
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Zapisz obraz");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Obrazy JPEG", "*.jpeg", "*.jpg"));
                File outputFile = fileChooser.showSaveDialog(huffmanClient.getTreeButton().getScene().getWindow());
                if (outputFile != null) {
                    BufferedImage bufferedImage = new BufferedImage((int) snapshot.getWidth(), (int) snapshot.getHeight(), BufferedImage.TYPE_INT_RGB);
                    PixelReader pixelReader = snapshot.getPixelReader();
                    for (int x = 0; x < snapshot.getWidth(); x++) {
                        for (int y = 0; y < snapshot.getHeight(); y++) {
                            bufferedImage.setRGB(x, y, pixelReader.getArgb(x, y));
                        }
                    }
                    ImageIO.write(bufferedImage, "jpeg", outputFile);
                }
            } catch (IOException ex) {
                huffmanClient.getResultLabel().setText("Błąd podczas zapisu obrazu: " + ex.getMessage());
            }
        });
        FileSize fileSize= new FileSize();
        SavedFrequency savedFrequency= new SavedFrequency();
        BitFrequency huffmanStats= new BitFrequency();
        huffmanClient.getStatystykiButton().setOnAction(e -> {
            String inputFile = huffmanClient.getInputField().getText();
            try {
                String Mode = huffmanClient.getModeComboBox().getSelectionModel().getSelectedItem();
                huffmanStats.generateStats(inputFile, Mode);
                fileSize.generateStats(inputFile, Mode);
                savedFrequency.generateStats(inputFile, Mode);
                huffmanClient.getInputFileSize().setText("Wielkość pliku wejściowego: " + fileSize.getInputFileSize() + "B");
                huffmanClient.getOutputFileSize().setText("Wielkość pliku wyjściowego: " + fileSize.getOutputFileSize() + "B");
                huffmanClient.getTreeFileSize().setText("Wielkość pliku z drzewem: " + fileSize.getTreeFileSizeHuffman() + "B");
                huffmanClient.getFrequencyOf0().setText("Ilość wystąpień 0 w pliku binarnym: " + huffmanStats.getFrequencyOf0Huffman());
                huffmanClient.getFrequencyOf1().setText("Ilość wystąpień 1 w pliku binarnym: " + huffmanStats.getFrequencyOf1Huffman());
                huffmanClient.getFrequencyOfAll().setText("Ilość wystąpień każdego znaku \n w pliku wejściowym:\n " + savedFrequency.getFreqAll());
            } catch (IOException | InterruptedException ex) {
                huffmanClient.getResultLabel().setText("Błąd podczas wyświetlania statystyk: " + ex.getMessage());
            }
        });
    }
}