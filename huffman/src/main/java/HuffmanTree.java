import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.*;

public class HuffmanTree {

    private HuffmanNode root;

    public void buildTreeFromFile(String filePath) throws IOException {
        Map<Integer, String> codeMap = AsciiMapBuilder.getAsciiCodeMap(filePath);

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
            assert rightChild != null;
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

        HashMap<Integer, Integer> frequencyMap = HuffmanStats.getFrequencyMap(inputFilePath);

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

    public void displayTree(String inputFilePath, HuffmanNode node, Pane parent, double x, double y, double verticalOffset) throws IOException {
        if (node == null) {
            return;
        }
        int depth = calculateDepth(node);
        double circleRadius = 25.0;
        double verticalGap = 10.0;
        double horizontalGap = 15.0;

        double depthPow = Math.pow(2, depth - 1);
        double childX1 = x - (horizontalGap * depthPow) - (circleRadius / 2);
        double childX2 = x + (horizontalGap * depthPow) + (circleRadius / 2);
        double childY = y + verticalGap + verticalOffset;

        int freq = calculateChildFrequency(inputFilePath, node);
        String charText;
        if (node.getAsciiCode() != 0) {
            charText = String.format("%c: %d", node.getAsciiCode(), freq);
        } else {
            charText = String.format("%d", freq);
        }
        Text charFrequencyText = new Text(x - circleRadius + 5, y, charText);
        charFrequencyText.setStyle("-fx-font-family: monospace");



        Circle circle = new Circle(x, y, circleRadius, Color.WHITE);
        circle.setStroke(Color.BLACK);

        List<Node> nodesToAdd = new ArrayList<>();
        nodesToAdd.add(circle);
        nodesToAdd.add(charFrequencyText);

        if (node.getLeft() != null) {
            Line leftLine = new Line(x, y + circleRadius, childX1, childY - circleRadius);
            Text leftCodeText = new Text((x + childX1) / 2 - 10, (y + childY)/2 , "0");
            leftCodeText.setStyle("-fx-font-family: monospace");
            nodesToAdd.add(leftLine);
            nodesToAdd.add(leftCodeText);

            displayTree(inputFilePath, node.getLeft(), parent, childX1, childY, verticalOffset + verticalGap);
        }

        if (node.getRight() != null) {
            Line rightLine = new Line(x, y + circleRadius, childX2, childY - circleRadius);
            Text rightCodeText = new Text((x + childX2) / 2, (y + childY)/2, "1");
            rightCodeText.setStyle("-fx-font-family: monospace");
            nodesToAdd.add(rightLine);
            nodesToAdd.add(rightCodeText);

            displayTree(inputFilePath, node.getRight(), parent, childX2, childY, verticalOffset + verticalGap);
        }

        Platform.runLater(() -> parent.getChildren().addAll(nodesToAdd));
    }


}
