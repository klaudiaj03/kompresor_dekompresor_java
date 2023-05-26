public class HuffmanNode {
    private final int asciiCode;
    private final int frequency;
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