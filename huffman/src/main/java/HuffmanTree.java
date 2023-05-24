import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class HuffmanTree {

    private HuffmanCompressor compress = new HuffmanCompressor();
    private HuffmanNode root;
    private Pane treeViewPane = new Pane();
    private HashMap<Integer, Integer> frequencyMap;

    public void buildTreeFromFile(String filePath) throws IOException {
        Map<Integer, String> codeMap = new HashMap<>();
        frequencyMap = compress.getFrequencyMap(filePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                if (parts.length == 2) {
                    int asciiCode = 0;
                    String code = "";

                    if (!parts[0].isEmpty()) {
                        asciiCode = Integer.parseInt(parts[0]);
                    }
                    if (!parts[1].isEmpty()) {
                        code = parts[1];
                    }

                    codeMap.put(asciiCode, code);
                }
            }
        }

        root = buildTreeFromCodeMap(codeMap);
    }

    private HuffmanNode buildTreeFromCodeMap(Map<Integer, String> codeMap) {
        PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(HuffmanNode::getFrequency));
        for (Map.Entry<Integer, String> entry : codeMap.entrySet()) {
            int asciiCode = entry.getKey();
            String code = entry.getValue();
            HuffmanNode leafNode = new HuffmanNode(asciiCode, code.length());
            priorityQueue.add(leafNode);
        }

        while (priorityQueue.size() > 1) {
            HuffmanNode leftChild = priorityQueue.poll();
            HuffmanNode rightChild = priorityQueue.poll();
            int totalFrequency = leftChild.getFrequency() + rightChild.getFrequency();
            HuffmanNode parent = new HuffmanNode(0, totalFrequency, leftChild, rightChild);
            priorityQueue.add(parent);
        }

        return priorityQueue.poll();
    }

    public int calculateChildFrequency(String inputFilePath, HuffmanNode node) throws IOException {
        if (node == null) {
            return 0;
        }

        HashMap<Integer, Integer> frequencyMap = compress.getFrequencyMap(inputFilePath);

        int leftFrequency = calculateChildFrequency(inputFilePath, node.getLeft());
        int rightFrequency = calculateChildFrequency(inputFilePath, node.getRight());
        int nodeFrequency = frequencyMap.getOrDefault(node.getAsciiCode(), 0);

        return leftFrequency + rightFrequency + nodeFrequency;
    }

    public HuffmanNode getRoot() {
        return root;
    }

    public int calculateDepth(HuffmanNode node) {
        if (node == null) {
            return 0;
        }
        int leftDepth = calculateDepth(node.getLeft());
        int rightDepth = calculateDepth(node.getRight());
        return Math.max(leftDepth, rightDepth) + 1;
    }

    public void displayTree(String inputFilePath, HuffmanNode node, Pane parent, int direction, String code, double x, double y, double verticalOffset, int depth, HashMap<Integer, Integer> frequencyMap) throws IOException {
        if (node == null) {
            return;
        }
        depth = calculateDepth(node);
        double circleRadius = 25.0;
        double verticalGap = 10.0;
        double horizontalGap = 15.0;

        double depthPow = Math.pow(2, depth - 1);
        double childX1 = x - (horizontalGap * depthPow) - (circleRadius / 2);
        double childX2 = x + (horizontalGap * depthPow) - (circleRadius / 2);
        double childY = y + verticalGap + verticalOffset;

        int freq = calculateChildFrequency(inputFilePath, node);
        String charText = String.format("%c: %d", node.getAsciiCode(), freq);
        Text charFrequencyText = new Text(x - circleRadius + 5, y, charText);
        charFrequencyText.setStyle("-fx-font-family: monospace");

        Circle circle = new Circle(x, y, circleRadius, Color.WHITE);
        circle.setStroke(Color.BLACK);

        // Update UI components on the JavaFX application thread
        Platform.runLater(() -> {
            parent.getChildren().addAll(circle, charFrequencyText);
        });

        if (node.getLeft() != null) {
            Line leftLine = new Line(x, y + circleRadius, childX1, childY - circleRadius);
            Text leftCodeText = new Text(x - circleRadius / 2, y + circleRadius + 30, "0");
            leftCodeText.setStyle("-fx-font-family: monospace");

            // Update UI components on the JavaFX application thread
            Platform.runLater(() -> {
                parent.getChildren().addAll(leftLine, leftCodeText);
            });

            displayTree(inputFilePath, node.getLeft(), parent, 0, code + "0", childX1, childY, verticalOffset + verticalGap, depth + 1, frequencyMap);
        }

        if (node.getRight() != null) {
            Line rightLine = new Line(x, y + circleRadius, childX2, childY - circleRadius);
            Text rightCodeText = new Text(x + circleRadius / 2, y + circleRadius + 30, "1");
            rightCodeText.setStyle("-fx-font-family: monospace");

            // Update UI components on the JavaFX application thread
            Platform.runLater(() -> {
                parent.getChildren().addAll(rightLine, rightCodeText);
            });

            displayTree(inputFilePath, node.getRight(), parent, 1, code + "1", childX2, childY, verticalOffset + verticalGap, depth + 1, frequencyMap);
        }
    }

}

class HuffmanNode {
    private int asciiCode;
    private int frequency;
    private HuffmanNode left;
    private HuffmanNode right;

    public HuffmanNode(int asciiCode, int frequency) {
        this.asciiCode = asciiCode;
        this.frequency = frequency;
    }

    public HuffmanNode(int asciiCode, int frequency, HuffmanNode left, HuffmanNode right) {
        this.asciiCode = asciiCode;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    public int getAsciiCode() {
        return asciiCode;
    }

    public int getFrequency() {
        return frequency;
    }

    public HuffmanNode getLeft() {
        return left;
    }

    public HuffmanNode getRight() {
        return right;
    }
}
