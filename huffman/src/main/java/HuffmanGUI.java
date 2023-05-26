import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;

import javax.imageio.ImageIO;
import javafx.scene.SnapshotParameters;



public class HuffmanGUI extends Application {

    private TextField outputField;
    private ComboBox<String> modeComboBox;
    private Button compressButton, decompressButton, treeButton, statystykiButton;

    HuffmanStats huffmanStats = new HuffmanStats();


    @Override
    public void start(Stage primaryStage) {


        // ustawienia okna
        primaryStage.setTitle("Program Huffman");
        primaryStage.setOnCloseRequest(event -> System.exit(0));
        Label inputFileLabel = new Label("Plik wejściowy:");
        TextField inputField = new TextField();
        Button inputFileButton = new Button("Wybierz");

// przypisanie akcji do przycisków
        inputFileButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                inputField.setText(selectedFile.getAbsolutePath());
            }
        });


        Label outputFileLabel = new Label("Plik wyjściowy:");
        outputField = new TextField();
        Button outputFileButton = new Button("Wybierz");

        outputFileButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showSaveDialog(primaryStage);
            if (selectedFile != null) {
                outputField.setText(selectedFile.getAbsolutePath());
            }
        });
        compressButton = new Button("Kompresuj");
        Label compressionLabel = new Label("Tryb:");
        String[] Modes = {"huffman", "huffman v2"};
        modeComboBox = new ComboBox<>();
        modeComboBox.getItems().addAll(Modes);
        modeComboBox.getSelectionModel().selectFirst();
        Label resultLabel = new Label();
        compressButton.setOnAction(e -> {
            try {
                String compressionMode = modeComboBox.getSelectionModel().getSelectedItem();
                String inputFilePath = inputField.getText();
                String outputFilePath = outputField.getText();
                String result = HuffmanCompressor.runC(inputFilePath, outputFilePath, compressionMode);
                resultLabel.setText(result);
            } catch (IOException | IllegalArgumentException | InterruptedException ex) {
                resultLabel.setText("Błąd podczas kompresji: " + ex.getMessage());
            }
        });

        decompressButton = new Button("Dekompresuj");
        decompressButton.setOnAction(e -> {
            try {
                    String decompressionMode = modeComboBox.getSelectionModel().getSelectedItem();
                    String inputFilePath = inputField.getText();
                    String outputFilePath = outputField.getText();
                    HuffmanDecompressor.runD(inputFilePath, outputFilePath, decompressionMode);
                } catch (IOException | IllegalArgumentException | InterruptedException ex) {
                    resultLabel.setText("Błąd podczas dekompresji: " + ex.getMessage());
                }
            });

        treeButton = new Button("Wyświetl \n drzewo");
        statystykiButton = new Button("Wyświetl \n statystyki");
        Label statystyki = new Label("Statystyki");
        Label inputFileSize = new Label();
        Label outputFileSize = new Label();
        Label treeFileSize = new Label();
        Label frequencyOf0 = new Label();
        Label frequencyOf1 = new Label();
        Label frequencyOfAll = new Label();

        Pane treeView = new Pane();
        Button saveImageButton = new Button("Zapisz obraz");
        saveImageButton.setDisable(true);

        treeButton.setOnAction(e -> {
            String treeFilePath = "tree.txt";
            String inputFilePath = inputField.getText();

            treeButton.setDisable(true);
            saveImageButton.setDisable(true);

            Thread thread = new Thread(() -> {
                try {
                    HuffmanTree huffmanTree = new HuffmanTree();
                    huffmanTree.buildTreeFromFile(treeFilePath);

                    huffmanTree.displayTree(inputFilePath, huffmanTree.getRoot(), treeView, 500, 80.0, 100);
                    Platform.runLater(() -> {
                        saveImageButton.setDisable(false);
                        treeButton.setDisable(false);
                    });
                } catch (IOException ex) {
                    Platform.runLater(() -> {
                        resultLabel.setText("Błąd podczas tworzenia drzewa: " + ex.getMessage());
                        treeButton.setDisable(false);
                    });
                }
            });
            thread.start();
        });


        saveImageButton.setOnAction(e -> {
            try {
                WritableImage snapshot = treeView.snapshot(new SnapshotParameters(), null);
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Zapisz obraz");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Obrazy JPEG", "*.jpeg", "*.jpg"));
                File outputFile = fileChooser.showSaveDialog(treeButton.getScene().getWindow());
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
                resultLabel.setText("Błąd podczas zapisu obrazu: " + ex.getMessage());
            }
        });
        statystykiButton.setOnAction(e -> {
            String inputFile = inputField.getText();
            try {
                String Mode = modeComboBox.getSelectionModel().getSelectedItem();
                huffmanStats.generateStats(inputFile, Mode);
                inputFileSize.setText("Wielkość pliku wejściowego: " + huffmanStats.getInputFileSize() + "B");
                outputFileSize.setText("Wielkość pliku wyjściowego: " + huffmanStats.getOutputFileSize() + "B");
                treeFileSize.setText("Wielkość pliku z drzewem: " + huffmanStats.getTreeFileSizeHuffman() + "B");
                frequencyOf0.setText("Ilość wystąpień 0 w pliku binarnym: " + huffmanStats.getFrequencyOf0Huffman());
                frequencyOf1.setText("Ilość wystąpień 1 w pliku binarnym: " + huffmanStats.getFrequencyOf1Huffman());
                frequencyOfAll.setText("Ilość wystąpień każdego znaku \n w pliku wejściowym:\n " + huffmanStats.getFreqAll());
            } catch (IOException | InterruptedException ex) {
                resultLabel.setText("Błąd podczas wyświetlania statystyk: " + ex.getMessage());
            }
        });


        GridPane root = new GridPane();
        root.setPadding(new Insets(20));
        root.setHgap(10);
        root.setVgap(10);

// 1 ćwiartka
        StackPane stackPane1 = createStackPane(300, 270);
        GridPane innerGridPane1 = new GridPane();
        innerGridPane1.setAlignment(Pos.CENTER);
        innerGridPane1.setHgap(10);
        innerGridPane1.setVgap(10);
        innerGridPane1.setPadding(new Insets(1, 10, 1, 10));
        innerGridPane1.add(inputFileLabel, 0, 0);
        innerGridPane1.add(inputField, 1, 0);
        innerGridPane1.add(inputFileButton, 2, 0);
        innerGridPane1.add(outputFileLabel, 0, 1);
        innerGridPane1.add(outputField, 1, 1);
        innerGridPane1.add(outputFileButton, 2, 1);
        innerGridPane1.add(compressionLabel, 0, 2);
        innerGridPane1.add(modeComboBox, 0, 3);
        innerGridPane1.add(compressButton, 0, 4);
        innerGridPane1.add(decompressButton, 1, 4);
        innerGridPane1.add(treeButton, 0, 5);
        innerGridPane1.add(statystykiButton, 1, 5);
        stackPane1.getChildren().add(innerGridPane1);
        root.add(stackPane1, 0, 0);


// 2 ćwiartka
        StackPane stackPane2 = createStackPane(300, 310);
        ScrollPane scrollPane = new ScrollPane();
        GridPane innerGridPane2 = new GridPane();
        innerGridPane2.setAlignment(Pos.CENTER_LEFT);
        innerGridPane2.setHgap(10);
        innerGridPane2.setVgap(10);
        innerGridPane2.setPadding(new Insets(10, 10, 10, 10));
        innerGridPane2.add(statystyki, 0, 0);
        innerGridPane2.add(inputFileSize, 0, 1);
        innerGridPane2.add(outputFileSize, 0, 2);
        innerGridPane2.add(treeFileSize, 0, 3);
        innerGridPane2.add(frequencyOf0, 0, 4);
        innerGridPane2.add(frequencyOf1, 0, 5);
        innerGridPane2.add(frequencyOfAll, 0, 6);
        scrollPane.setContent(innerGridPane2);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        stackPane2.getChildren().add(scrollPane);
        root.add(stackPane2, 0, 1);


// 3 ćwiartka
        StackPane stackPane3 = createStackPane(640, 520);
        GridPane innerGridPane3 = new GridPane();
        ScrollPane scrollPane3 = new ScrollPane();
        innerGridPane3.add(treeView, 0, 1);
        innerGridPane3.add(saveImageButton, 2, 0);
        scrollPane3.setContent(innerGridPane3);
        innerGridPane3.setAlignment(Pos.CENTER);
        scrollPane3.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane3.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        stackPane3.getChildren().add(scrollPane3);
        root.add(stackPane3, 1, 0);

// 4 ćwiartka
        StackPane stackPane4 = createStackPane( 640, 120);
        stackPane4.setPadding(new Insets(0, 0, 10, 0)); // Dodaj padding 10 pikseli od dołu
        GridPane innerGridPane4 = new GridPane();
        innerGridPane4.setAlignment(Pos.CENTER); // Wyśrodkuj GridPane
        innerGridPane4.add(resultLabel, 1, 1);
        stackPane4.getChildren().add(innerGridPane4);
        root.add(stackPane4, 1, 1);

// Ustawienie nachodzenia siatek
        GridPane.setConstraints(stackPane1, 0, 0, 1, 6);
        GridPane.setConstraints(stackPane2, 0, 6, 1, 5);
        GridPane.setConstraints(stackPane3, 1, 0, 1, 7);
        GridPane.setConstraints(stackPane4, 1, 8, 1, 3);




        inputFileSize.setFont(new Font("Verdana", 12));
        outputFileSize.setFont(Font.font("Verdana", 12));
        treeFileSize.setFont(Font.font("Verdana", 12));
        frequencyOf0.setFont(Font.font("Verdana", 12));
        frequencyOf1.setFont(Font.font("Verdana", 12));
        frequencyOfAll.setFont(Font.font("Verdana", 12));
        inputFileLabel.setFont(Font.font("Verdana", 15));
        compressionLabel.setFont(Font.font("Verdana", 12));
        outputFileLabel.setFont(Font.font("Verdana", 15));
        resultLabel.setFont(Font.font("Verdana", 12));
        outputFileButton.setPrefSize(25, 25);
        inputFileButton.setPrefSize(25, 25);
        statystyki.setFont(Font.font("Arial Black", 15));
        resultLabel.setPrefSize(550, 100);
        resultLabel.setWrapText(true);
        decompressButton.setPrefSize(150, 20);
        compressButton.setPrefSize(150, 20);
        statystykiButton.setPrefSize(150, 50);
        treeButton.setPrefSize(150, 50);
        modeComboBox.setPrefSize(120, 10);
        decompressButton.setTextFill(Color.RED);
        decompressButton.setFont(Font.font("Arial Black", 12));
        compressButton.setTextFill(Color.GREEN);
        compressButton.setFont(Font.font("Arial Black", 12));
        statystykiButton.setFont(Font.font("Arial Black", 13));
        treeButton.setFont(Font.font("Arial Black", 13));
        Scene scene = new Scene(root);

        primaryStage.setWidth(1000);
        primaryStage.setHeight(730);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private StackPane createStackPane(Integer width, Integer hight ) {
        Rectangle border = new Rectangle(width, hight);
        border.setFill(null);
        border.setStroke(Color.BLACK);
        border.setStrokeWidth(1);
        border.setStrokeType(StrokeType.INSIDE);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(border);

        return stackPane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}