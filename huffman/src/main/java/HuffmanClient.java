import javafx.application.Platform;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HuffmanClient {
    private final HuffmanController huffmanController;

    public HuffmanClient(HuffmanController huffmanController) {
        this.huffmanController = huffmanController;
    }

    public void registerEventHandlers() {

        huffmanController.getInputFileButton().setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(huffmanController.getPrimaryStage());
            if (selectedFile != null) {
                huffmanController.getInputField().setText(selectedFile.getAbsolutePath());
            }
        });

        huffmanController.getOutputFileButton().setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showSaveDialog(huffmanController.getPrimaryStage());
            if (selectedFile != null) {
                huffmanController.getOutputField().setText(selectedFile.getAbsolutePath());
            }
        });
        CompessionMode compessionMode = new CompessionMode();
        compessionMode.selectCompressionMode();

        huffmanController.getCompressButton().setOnAction(e -> {
            try {
                String mode = compessionMode.getSelectedMode();
                String inputFilePath = huffmanController.getInputField().getText();
                String outputFilePath = huffmanController.getOutputField().getText();
                String result = HuffmanCompressor.runC(inputFilePath, outputFilePath, mode);
                huffmanController.getResultLabel().setText(result);
            } catch (IOException | IllegalArgumentException | InterruptedException ex) {
                huffmanController.getResultLabel().setText("Błąd podczas kompresji: " + ex.getMessage());
            }
        });

        compessionMode.selectCompressionMode();

        huffmanController.getDecompressButton().setOnAction(e -> {
            try {
                String mode = compessionMode.getSelectedMode();
                String inputFilePath = huffmanController.getInputField().getText();
                String outputFilePath = huffmanController.getOutputField().getText();
                DecompressionMode.runD(inputFilePath, outputFilePath, mode);
            } catch (IOException | IllegalArgumentException | InterruptedException ex) {
                huffmanController.getResultLabel().setText("Błąd podczas dekompresji: " + ex.getMessage());
            }
        });


        huffmanController.getTreeButton().setOnAction(e -> {
            String treeFilePath = "tree.txt";
            String inputFilePath = huffmanController.getInputField().getText();

            huffmanController.getTreeButton().setDisable(true);
            huffmanController.getSaveImageButton().setDisable(true);

            Thread thread = new Thread(() -> {
                try {
                    HuffmanTree huffmanTree = new HuffmanTree();
                    huffmanTree.buildTreeFromFile(treeFilePath);
                    double treeSize = huffmanTree.getSize(huffmanTree.getRoot());
                    huffmanTree.displayTree(inputFilePath, huffmanTree.getRoot(), huffmanController.getTreeView(), treeSize, 80.0, 100);
                    Platform.runLater(() -> {
                        huffmanController.getSaveImageButton().setDisable(false);
                        huffmanController.getTreeButton().setDisable(false);
                    });
                } catch (IOException ex) {
                    Platform.runLater(() -> {
                        huffmanController.getResultLabel().setText("Błąd podczas tworzenia drzewa: " + ex.getMessage());
                        huffmanController.getTreeButton().setDisable(false);
                    });
                }
            });
            thread.start();
        });

        huffmanController.getSaveImageButton().setOnAction(e -> {
            try {
                WritableImage snapshot = huffmanController.getTreeView().snapshot(new SnapshotParameters(), null);
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Zapisz obraz");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Obrazy JPEG", "*.jpeg", "*.jpg"));
                File outputFile = fileChooser.showSaveDialog(huffmanController.getTreeButton().getScene().getWindow());
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
                huffmanController.getResultLabel().setText("Błąd podczas zapisu obrazu: " + ex.getMessage());
            }
        });
        FileSize fileSize= new FileSize();
        SavedFrequency savedFrequency= new SavedFrequency();
        BitFrequency huffmanStats= new BitFrequency();
        huffmanController.getStatystykiButton().setOnAction(e -> {
            String inputFile = huffmanController.getInputField().getText();
            try {
                String Mode = huffmanController.getModeComboBox().getSelectionModel().getSelectedItem();
                huffmanStats.generateStats(inputFile, Mode);
                fileSize.generateStats(inputFile, Mode);
                savedFrequency.generateStats(inputFile, Mode);
                huffmanController.getInputFileSize().setText("Wielkość pliku wejściowego: " + fileSize.getInputFileSize() + "B");
                huffmanController.getOutputFileSize().setText("Wielkość pliku wyjściowego: " + fileSize.getOutputFileSize() + "B");
                huffmanController.getTreeFileSize().setText("Wielkość pliku z drzewem: " + fileSize.getTreeFileSizeHuffman() + "B");
                huffmanController.getFrequencyOf0().setText("Ilość wystąpień 0 w pliku binarnym: " + huffmanStats.getFrequencyOf0Huffman());
                huffmanController.getFrequencyOf1().setText("Ilość wystąpień 1 w pliku binarnym: " + huffmanStats.getFrequencyOf1Huffman());
                huffmanController.getFrequencyOfAll().setText("Ilość wystąpień każdego znaku \n w pliku wejściowym:\n " + savedFrequency.getFreqAll());
            } catch (IOException | InterruptedException ex) {
                huffmanController.getResultLabel().setText("Błąd podczas wyświetlania statystyk: " + ex.getMessage());
            }
        });
    }
}