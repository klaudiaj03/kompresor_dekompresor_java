
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanTree {
    private HuffmanNode root;

    public void buildTreeFromFile(String filePath) throws IOException {
        Map<Integer, String> codeMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                if (parts.length >= 2) {
                    int asciiCode = 0;
                    String code = "";

                    if (parts[0].length() > 0) {
                        asciiCode = Integer.parseInt(parts[0]);
                    }
                    if (parts[1].length() > 0) {
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

    public HuffmanNode getRoot() {
        return root;
    }

    public void displayTree(HuffmanNode node, Pane parent, int direction, String code, double x, double y) {
        if (node == null) {
            return;
        }

        double circleRadius = 40.0;
        double verticalGap = 90.0;
        double horizontalGap = 70.0;

        Circle circle = new Circle(x, y, circleRadius, Color.WHITE);
        circle.setStroke(Color.BLACK);

        if ((char) node.getAsciiCode() != 0) {
            Text charFrequencyText = new Text(x - circleRadius / 2, y + 5, (char) node.getAsciiCode() + " -> " + node.getFrequency());
            charFrequencyText.setStyle("-fx-font-family: monospace");
            parent.getChildren().addAll(circle, charFrequencyText);
        } else {
            Text charFrequencyText = new Text(x - circleRadius / 2, y + 5, (char) node.getAsciiCode() + " " + node.getFrequency());
            charFrequencyText.setStyle("-fx-font-family: monospace");
            parent.getChildren().addAll(circle, charFrequencyText);
        }

        double childX = direction == 0 ? x - horizontalGap : x + horizontalGap;
        double childY = y + verticalGap;

        if (node.getLeft() != null) {
            Line leftLine = new Line(x, y + circleRadius, childX, childY - circleRadius);
            parent.getChildren().add(leftLine);
            Text leftCodeText = new Text(x - circleRadius / 2, y + circleRadius + 15, "0");
            leftCodeText.setStyle("-fx-font-family: monospace");
            parent.getChildren().add(leftCodeText);
            displayTree(node.getLeft(), parent, 0, code + "0", childX, childY);
        }
        if (node.getRight() != null) {
            Line rightLine = new Line(x, y + circleRadius, childX, childY - circleRadius);
            parent.getChildren().add(rightLine);
            Text rightCodeText = new Text(x + circleRadius / 2, y + circleRadius + 15, "1");
            rightCodeText.setStyle("-fx-font-family: monospace");
            parent.getChildren().add(rightCodeText);
            displayTree(node.getRight(), parent, 1, code + "1", childX, childY);
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