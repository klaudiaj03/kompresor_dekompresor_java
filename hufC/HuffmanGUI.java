package hufC;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;


public class HuffmanGUI extends Application {

    private TextField inputField, outputField;
    private ComboBox<String> compressionComboBox;
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
        String[] compressionModes = {"huffman", "huffman v2"};
        compressionComboBox = new ComboBox<>();
        compressionComboBox.getItems().addAll(compressionModes);
        compressionComboBox.getSelectionModel().selectFirst();
        Label resultLabel= new Label();
        compressButton.setOnAction(e -> {
            try {
                String compressionMode = compressionComboBox.getSelectionModel().getSelectedItem().toString();
                String inputFilePath = inputField.getText();
                String outputFilePath = outputField.getText();
                String result = Compress.run(inputFilePath, outputFilePath, compressionMode);
                resultLabel.setText(result);
            } catch (IOException | InterruptedException ex) {
                resultLabel.setText("Błąd podczas kompresji: " + ex.getMessage());
            } catch (IllegalArgumentException ex) {
                resultLabel.setText("Błąd podczas kompresji: " + ex.getMessage());
            }
        });

        decompressButton = new Button("Dekompresuj");
        decompressButton.setOnAction(e -> {
            try {
                String inputFilePath = inputField.getText();
                String outputFilePath = outputField.getText();
                HuffmanDecompressor.decompress(inputFilePath, outputFilePath);
                resultLabel.setText("Dekompresja zakończona.");
            } catch (IllegalArgumentException ex) {
                resultLabel.setText("Błąd podczas dekompresji: " + ex.getMessage());
            }
        });

        treeButton = new Button("Wyświetl \n drzewo");
        statystykiButton = new Button("Wyświetl \n statystyki");
        Label statystyki= new Label("Statystyki");
        Label inputFileSize = new Label("Wielkość pliku wejściowego:");
        Label outputFileSize = new Label("Wielkość pliku wyjściowego:");
        Label treeFileSize = new Label("Wielkość pliku z drzewem:");
        Label frequencyOf0 = new Label("Ilość wystąpień 0 w pliku binarnym:");
        Label frequencyOf1 = new Label("Ilość wystąpień 1 w pliku binarnym:");
        Label frequencyOfAll = new Label("Ilość wystąpień każdego znaku w pliku wejściowym:");

        treeButton.setOnAction(e -> {
            String inputFilePath = "tree.txt";
            try {
                HuffmanTree huffmanTree = new HuffmanTree();
                huffmanTree.buildTreeFromFile(inputFilePath);

                Pane treeView = new Pane();
                huffmanTree.displayTree(huffmanTree.getRoot(), treeView, 0, "", 400.0, 20.0);

                Stage treeStage = new Stage();
                treeStage.setTitle("Drzewo Huffmana");
                ScrollPane scrollPane = new ScrollPane(treeView);
                scrollPane.setFitToWidth(true);

                Scene treeScene = new Scene(scrollPane, 800, 600);
                treeStage.setScene(treeScene);
                treeStage.show();
            } catch (IOException ex) {
                resultLabel.setText("Błąd podczas tworzenia drzewa: " + ex.getMessage());
            }
        });

        statystykiButton.setOnAction(e -> {
            String inputFile = inputField.getText();
            try {
                huffmanStats.generateStats(inputFile);
                inputFileSize.setText("Wielkość pliku wejściowego: " + huffmanStats.getInputFileSize() + "B");
                outputFileSize.setText("Wielkość pliku wyjściowego: " + huffmanStats.getOutputFileSize() + "B");
                treeFileSize.setText("Wielkość pliku z drzewem: " + huffmanStats.getTreeFileSizeHuffman() + "B");
                frequencyOf0.setText("Ilość wystąpień 0 w pliku binarnym: " + huffmanStats.getFrequencyOf0Huffman());
                frequencyOf1.setText("Ilość wystąpień 1 w pliku binarnym: " + huffmanStats.getFrequencyOf1Huffman());
                frequencyOfAll.setText("Ilość wystąpień każdego znaku w pliku wejściowym:\n " + huffmanStats.getFreqAll() );
            } catch (IOException ex) {
                resultLabel.setText("Błąd podczas wyświetlania statystyk" + ex.getMessage());
            } catch (InterruptedException ex) {
                resultLabel.setText("Błąd podczas wyświetlania statystyk" + ex.getMessage());
            }
        });



        GridPane root = new GridPane();
        root.setPadding(new Insets(20));
        root.setHgap(20);
        root.setVgap(20);

// 1 ćwiartka
        StackPane stackPane1 = createStackPane(Color.BLACK);
        GridPane innerGridPane1 = new GridPane();
        innerGridPane1.setAlignment(Pos.CENTER);
        innerGridPane1.setHgap(50);
        innerGridPane1.setVgap(20);
        innerGridPane1.setPadding(new Insets(20, 20, 20, 20));
        innerGridPane1.add(inputFileLabel, 0, 0);
        innerGridPane1.add(inputField, 1, 0);
        innerGridPane1.add(inputFileButton, 2, 0);
        innerGridPane1.add(outputFileLabel, 0, 1);
        innerGridPane1.add(outputField, 1, 1);
        innerGridPane1.add(outputFileButton, 2, 1);
        innerGridPane1.add(compressionLabel, 0, 3);
        innerGridPane1.add(compressionComboBox, 0, 4);
        innerGridPane1.add(compressButton, 0, 2);
        innerGridPane1.add(decompressButton, 1, 2);
        innerGridPane1.add(treeButton, 0, 5);
        innerGridPane1.add(statystykiButton, 1, 5);
        stackPane1.getChildren().add(innerGridPane1);
        root.add(stackPane1, 0, 0);


// 2 ćwiartka
        StackPane stackPane2 = createStackPane(Color.BLACK);
        ScrollPane scrollPane = new ScrollPane();
        GridPane innerGridPane2 = new GridPane();
        innerGridPane2.setAlignment(Pos.CENTER_LEFT);
        innerGridPane2.setHgap(10);
        innerGridPane2.setVgap(20);
        innerGridPane2.setPadding(new Insets(10, 10, 10, 10));
        innerGridPane2.add(statystyki, 0,0);
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
        StackPane stackPane3 = createStackPane(Color.BLACK);
        GridPane innerGridPane3 = new GridPane();
        root.add(stackPane3, 1, 0);

// 4 ćwiartka
        StackPane stackPane4 = createStackPane(Color.BLACK);
        GridPane innerGridPane4 = new GridPane();
        innerGridPane4.setAlignment(Pos.CENTER); // Center align the grid pane
        innerGridPane4.add(resultLabel, 1, 1);
        stackPane4.getChildren().add(innerGridPane4);
        root.add(stackPane4, 1, 1);
        root.setAlignment(Pos.CENTER);
        stackPane4.setAlignment(Pos.CENTER);

        inputFileSize.setFont(new Font("Verdana", 16));
        outputFileSize.setFont(Font.font("Verdana", 16));
        treeFileSize.setFont(Font.font("Verdana", 16));
        frequencyOf0.setFont(Font.font("Verdana", 16));
        frequencyOf1.setFont(Font.font("Verdana", 16));
        frequencyOfAll.setFont(Font.font("Verdana", 16));
        inputFileLabel.setFont(Font.font("Verdana", 16));
        compressionLabel.setFont(Font.font("Verdana", 16));
        outputFileLabel.setFont(Font.font("Verdana", 16));
        resultLabel.setFont(Font.font("Verdana", 16));
        outputFileButton.setPrefSize(50,50);
        inputFileButton.setPrefSize(50,50);
        statystyki.setFont(Font.font("Arial Black",25));
        resultLabel.setPrefSize(500, 300);
        resultLabel.setWrapText(true);
        decompressButton.setPrefSize(200,40);
        compressButton.setPrefSize(200,40);
        statystykiButton.setPrefSize(200,100);
        treeButton.setPrefSize(200,100);
        compressionComboBox.setPrefSize(140,20);
        decompressButton.setTextFill(Color.RED);
        decompressButton.setFont(Font.font("Arial Black", 15));
        compressButton.setTextFill(Color.GREEN);
        compressButton.setFont(Font.font("Arial Black", 15));
        statystykiButton.setFont(Font.font("Arial Black", 15));
        treeButton.setFont(Font.font("Arial Black", 15));
        Scene scene = new Scene(root);

        primaryStage.setWidth(1400);
        primaryStage.setHeight(730);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private StackPane createStackPane(Color color) {
        Rectangle border = new Rectangle(650, 325);
        border.setFill(null);
        border.setStroke(color);
        border.setStrokeWidth(3);
        border.setStrokeType(StrokeType.INSIDE);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(border);

        return stackPane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
