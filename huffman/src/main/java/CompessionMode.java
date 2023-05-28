import javafx.scene.control.ComboBox;

public class CompessionMode {
    private ComboBox<String> modeComboBox;
    private String selectedMode;
    public CompessionMode() {
        this.modeComboBox = new ComboBox<>(); // Inicjalizacja zmiennej modeComboBox
    }
    public void selectCompressionMode(){
        modeComboBox = new ComboBox<>();
        String[] modes = {"huffman", "huffman v2"};
        modeComboBox.getItems().addAll(modes);
        modeComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedMode = newValue;
        });
        modeComboBox.getSelectionModel().selectFirst();
    }

    public ComboBox<String> getModeComboBox() {
        return modeComboBox;
    }
    public String getSelectedMode() {
        return selectedMode;
    }
}