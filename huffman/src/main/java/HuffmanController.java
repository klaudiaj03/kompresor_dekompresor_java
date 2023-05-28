import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;

public class HuffmanController extends Application {

    private TextField outputField;
    private ComboBox<String> modeComboBox;
    private Button compressButton, decompressButton, treeButton, statystykiButton, inputFileButton, outputFileButton, saveImageButton;
    private Label inputFileLabel, outputFileLabel, compressionLabel, resultLabel, statsLabel, inputFileSize, outputFileSize, frequencyOf0, frequencyOf1, frequencyOfAll, treeFileSize;
    private StackPane stackPane1, stackPane2, stackPane3, stackPane4;
    private TextField inputField;
    private GridPane root;
    private Pane treeView;
    private Stage primaryStage;
    CompessionMode compessionMode= new CompessionMode();

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        primaryStage.setTitle("Program Huffman");
        primaryStage.setOnCloseRequest(event -> System.exit(0));


        inputFileLabel = new Label("Plik wejściowy:");
        inputField = new TextField();
        inputFileButton = new Button("Wybierz");

        outputFileLabel = new Label("Plik wyjściowy:");
        outputField = new TextField();
        outputFileButton = new Button("Wybierz");

        compressButton = new Button("Kompresuj");
        compressionLabel = new Label("Tryb:");
        compessionMode.selectCompressionMode();
        modeComboBox = new ComboBox<>();
        resultLabel = new Label();

        decompressButton = new Button("Dekompresuj");

        treeButton = new Button("Wyświetl \n drzewo");
        statystykiButton = new Button("Wyświetl \n statystyki");
        statsLabel = new Label("Statystyki");
        inputFileSize = new Label();
        outputFileSize = new Label();
        treeFileSize = new Label();
        frequencyOf0 = new Label();
        frequencyOf1 = new Label();
        frequencyOfAll = new Label();

        treeView = new Pane();
        saveImageButton = new Button("Zapisz obraz");
        saveImageButton.setDisable(true);

        root = new GridPane();
        root.setPadding(new Insets(20));
        root.setHgap(10);
        root.setVgap(10);

        optionsWindow();
        statsWindow();
        treeWindow();
        infoWindow();
        windowsLayout();
        settings();
        HuffmanClient huffmanClient = new HuffmanClient(this);
        huffmanClient.registerEventHandlers();



    }

    public void optionsWindow() {
        stackPane1 = createStackPane(300, 270);
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
        innerGridPane1.add(compessionMode.getModeComboBox(), 0, 3);
        innerGridPane1.add(compressButton, 0, 4);
        innerGridPane1.add(decompressButton, 1, 4);
        innerGridPane1.add(treeButton, 0, 5);
        innerGridPane1.add(statystykiButton, 1, 5);
        stackPane1.getChildren().add(innerGridPane1);
        root.add(stackPane1, 0, 0);
    }

    public void statsWindow() {
        stackPane2 = createStackPane(300, 310);
        ScrollPane scrollPane = new ScrollPane();
        GridPane innerGridPane2 = new GridPane();
        innerGridPane2.setAlignment(Pos.CENTER_LEFT);
        innerGridPane2.setHgap(10);
        innerGridPane2.setVgap(10);
        innerGridPane2.setPadding(new Insets(10, 10, 10, 10));
        innerGridPane2.add(statsLabel, 0, 0);
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
    }

    public void treeWindow() {
        stackPane3 = createStackPane(640, 520);
        GridPane innerGridPane3 = new GridPane();
        ScrollPane scrollPane3 = new ScrollPane(treeView);
        scrollPane3.setPrefSize(640, 520);
        scrollPane3.setPannable(true);
        scrollPane3.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane3.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane3.setStyle("-fx-background-color: white;"); // Dodajemy białe tło do scrollPane

        StackPane.setAlignment(scrollPane3, Pos.CENTER); // Ustawiamy scrollPane na środku stackPane
        stackPane3.getChildren().add(scrollPane3);
        stackPane3.setAlignment(Pos.CENTER); // Wyśrodkowujemy stackPane
        root.add(stackPane3, 1, 0);

        // Ustawienie początkowego widoku na środek
        scrollPane3.setHvalue(0.5); // Ustawienie poziomego przewijania na środek
        scrollPane3.setVvalue(0.5); // Ustawienie pionowego przewijania na środek
    }




    public void infoWindow() {
        stackPane4 = createStackPane(640, 120);
        stackPane4.setPadding(new Insets(0, 0, 10, 0));
        GridPane innerGridPane4 = new GridPane();
        innerGridPane4.setAlignment(Pos.CENTER);
        innerGridPane4.add(resultLabel, 1, 1);
        stackPane4.getChildren().add(innerGridPane4);
        root.add(stackPane4, 1, 1);
    }

    public void windowsLayout() {
        GridPane.setConstraints(stackPane1, 0, 0, 1, 6);
        GridPane.setConstraints(stackPane2, 0, 6, 1, 5);
        GridPane.setConstraints(stackPane3, 1, 0, 1, 7);
        GridPane.setConstraints(stackPane4, 1, 8, 1, 3);
    }


    public void settings() {
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
        statsLabel.setFont(Font.font("Arial Black", 15));
        resultLabel.setPrefSize(550, 100);
        resultLabel.setWrapText(true);
        decompressButton.setPrefSize(150, 20);
        compressButton.setPrefSize(150, 20);
        statystykiButton.setPrefSize(150, 50);
        treeButton.setPrefSize(150, 50);
        compessionMode.getModeComboBox().setPrefSize(120, 10);
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

    private StackPane createStackPane(Integer width, Integer hight) {
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

    public ComboBox<String> getModeComboBox() {
        return modeComboBox;
    }

    public Label getResultLabel() {
        return resultLabel;
    }

    public TextField getOutputField() {
        return outputField;
    }

    public Button getTreeButton() {
        return treeButton;
    }

    public Label getInputFileSize() {
        return inputFileSize;
    }

    public Label getOutputFileSize() {
        return outputFileSize;
    }

    public Button getStatystykiButton() {
        return statystykiButton;
    }

    public Button getSaveImageButton() {
        return saveImageButton;
    }

    public Button getOutputFileButton() {
        return outputFileButton;
    }

    public Button getInputFileButton() {
        return inputFileButton;
    }

    public Button getDecompressButton() {
        return decompressButton;
    }

    public Button getCompressButton() {
        return compressButton;
    }

    public TextField getInputField() {
        return inputField;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Pane getTreeView() {
        return treeView;
    }

    public Label getFrequencyOf0() {
        return frequencyOf0;
    }

    public Label getTreeFileSize() {
        return treeFileSize;
    }

    public Label getFrequencyOfAll() {
        return frequencyOfAll;
    }

    public Label getFrequencyOf1() {
        return frequencyOf1;
    }
}
