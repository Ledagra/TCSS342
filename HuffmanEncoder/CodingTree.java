/*
 * Stephen VanLuven
 * Huffman Encoder Project, Main.java
 * TCSS 342 - Spring 2022
 */
import java.io.*;
import java.util.*;
/*
 * Custom class that builds a priority queue of Huffman nodes that represent unique characters in the original text.
 * After the priority queue is filled (the smallest weighted characters first), the first two nodes are combined as one
 * Huffman tree and reinserted back into the queue based on total weight. This process is repeated until only one
 * tree exists with all characters of the original text. The class then assigns each character a binary value based
 * on position in the tree. These characters and their values are added to a map which is then accessed in order
 * to translate the original text to and from its new binary form.
 */
public class CodingTree {
    /*
    * Custom node class used to build Huffman tree.
     */
    private class Node {
        //Character value of node
        char value;
        //Left child of node, if any.
        Node left;
        //Right child of node, if any.
        Node right;

        /*
        * Node constructor. Initially assigns left and right child to null.
        *
        * @param c which is the character to be assigned to that node.
         */
        Node (char c) {
            this.value = c;
            left = null;
            right = null;
        }
        /*
        * Overridden toString method that returns the character value of the node as a string.
         */
        @Override
        public String toString() {
            return "" + value;
        }
    }
    /*
    * HuffmanTree class that is a custom binary tree class. Uses custom node class above to build a binary tree. Also
    * implements the comparable method so that nodes are sorted on weight instead of character position in alphabet.
     */
    private class HuffmanTree implements Comparable<HuffmanTree> {
        //Root node.
        private Node root;
        //Weight of node as an int.
        private int weight;
        //Depth of current tree from current root to the furthest leaf. Currently, not utilized.
        private int depth;
        /*
        * Constructor method that takes in a character, and its occurrence frequency based on the input text.
        *
        * @param c which is the character value to be assigned
        * @param w which is the number of times the character appears in original text
         */
        HuffmanTree(char c, int w) {
            this.root = new Node(c);
            this.weight = w;
            this.depth = 1;
        }
        /*
        * Constructor that takes in two HuffmanTree nodes and combines them based on their weight.
        *
        * @param one is the first tree
        * @param two is the second tree
         */
        HuffmanTree(HuffmanTree one, HuffmanTree two) {
            this.root = new Node('\0');
            this.weight = one.weight + two.weight;
            if(one.weight >= two.weight) {
                this.root.right = one.root;
                this.root.left = two.root;
            } else {
                this.root.right = two.root;
                this.root.left = one.root;
            }
            this.depth = findDepth(this.root);
        }
        /*
        * Method used to find the depth of a HuffmanTree node root.
        *
        * @param temp is the node that represents the root node of the tree
        * @return int that represents depth of HuffmanTree
         */
        private int findDepth(Node temp) {
            if (this.root == null) {
                System.out.println("Tree is empty!");
                return 0;
            } else {
                int leftD = 0;
                int rightD = 0;
                if(temp.left != null) {
                    leftD = findDepth(temp.left);
                }
                if(temp.right != null) {
                    rightD = findDepth(temp.right);
                }
                int max = Math.max(leftD, rightD);
                return max + 1;
            }
        }
        /*
        * Overridden compareTo method that allows HuffmanTree nodes to be sorted on total weight.
        *
        * @param other is the HuffmanTree to be compared to
        * @return int that represents compare order.
         */
        @Override
        public int compareTo(HuffmanTree other) {
            return this.weight - other.weight;
        }

        /*
        * Overridden toString method that returns HuffmanTree node's character value and total weight.
        *
        * @return string containing weight and character value data
         */
        @Override
        public String toString() {
            return root.toString() + " : " + this.weight;
        }
    }
    //Map that contains character and its new binary value as a string
    private Map<Character, String> codes;
    //String that represents new binary form of original text
    private String bits;
    //PriorityQueue for HuffmanTree nodes based on weight. (Less frequent characters ordered first)
    private PriorityQueue<HuffmanTree> treeQueue;
    //Character array of all 256 ascii values
    private final char[] alphabet;
    //The weight of the fully built HuffmanTree. Used to compare total character count to original text.
    private final int finalTreeWeight;
    //The complete HuffmanTree after all nodes have been combined.
    private final HuffmanTree finalTree;
    //Character array this.alphabet after characters with a 0 frequency have been removed.
    private char[] conciseArray;
    //Byte array that contains final version of compressed text in byte format.
    private byte[] compressedFinal;

    /*
    * Constructor method that creates a new CodingTree object. Order of operations:
    * - Reads in original text. Keeps track of characters used and their frequency.
    * - Creates PriorityQueue of HuffmanTree nodes. Initially one for each character.
    * - Combines first two nodes in queue and reinserts them into queue until only one tree remains containing all nodes.
    * - Assigns the character of each node in that tree a new binary value.
    * - Sends that character and their new binary value into a map for storage.
    * - Reads through original text and converts it using new binary values.
    * - Saves codes Map and compressed text as separate files.
    *
    * Each method keeps track of and reports run time.
    *
    * @param message which is the original text to be read.
     */
    CodingTree(String message) {
        treeQueue = new PriorityQueue<>();
        this.alphabet = genCharArray();
        fillQueue(message);
        this.finalTree = buildFinalHuffmanTree();
        this.finalTreeWeight = finalTree.weight;
        extractCodes();
        encode(message);
        printToFile(message);
    }

    /*
    * Method that generates full 256 ASCII character array so that each can be checked against original text.
    *
    * @return array containing all character values
     */
    private char[] genCharArray() {
        System.out.println("Building ASCII array...");
        double start = System.currentTimeMillis();
        char[] result = new char[256];
        for(int i = 0; i < 256; i++) {
            result[i] = (char) i;
        }
        double end = System.currentTimeMillis();
        System.out.println("Finished in " + ((end - start) / 1000) + " seconds.");
        System.out.println();
        return result;

    }

    /*
    * Method that reads characters from completed HuffmanTree and assigns them a new binary value based on weight.
     */
    private void extractCodes() {
        System.out.println("Extracting binary codes from Huffman Tree...");
        double start = System.currentTimeMillis();
        this.codes = new HashMap<>();
        printCode(this.finalTree.root, "");
        double end = System.currentTimeMillis();
        System.out.println("Finished in " + ((end - start) / 1000) + " seconds.");
        System.out.println();
    }

    /*
    * Method that reads each character in original ASCII array and counts the number of occurrences in the original
    * text. For each non-zero occurrence, a new HuffmanTree node is created and inserted into the PriorityQueue.
    *
    * @param message which is the original text as a string.
     */
    private void fillQueue(String message) {
        System.out.println("Building HuffmanTree for individual characters...");
        double start = System.currentTimeMillis();
        for(char c : this.alphabet) {
            int count = 0;
            count += (int) message.chars().filter(ch -> ch == c).count();
            if(count > 0) {
                this.treeQueue.add(new HuffmanTree(c, count));
            }
        }
        this.conciseArray = new char[treeQueue.size()];
        fillConciseArray();
        double end = System.currentTimeMillis();
        System.out.println("Finished in " + ((end - start) / 1000) + " seconds.");
        System.out.println();
    }

    /*
    * Method that pulls the first two HuffmanTree nodes from the PriorityQueue and combines them into a single tree
    * structure and then reinserts them into the queue as a single node. This process is repeated until only one, final
    * tree remains containing all characters found in the original text.
    *
    * @return HuffmanTree that is the final product
     */
    private HuffmanTree buildFinalHuffmanTree() {
        System.out.println("Building final Huffman Tree...");
        double start = System.currentTimeMillis();
        while (this.treeQueue.size() > 1) {
            this.treeQueue.add(new HuffmanTree(this.treeQueue.poll(), Objects.requireNonNull(this.treeQueue.poll())));
        }
        double end = System.currentTimeMillis();
        System.out.println("Finished in " + ((end - start) / 1000)+ " seconds.");
        System.out.println();
        return this.treeQueue.poll();
    }

    /*
    * Method that calculates weight of the completed HuffmanTree
    *
    * @return int that is the weight of the tree
     */
    public int getFinalTreeWeight() {
        return finalTreeWeight;
    }

    /*
    * Method that constructs an array consisting of only the characters that are in the original text.
     */
    private void fillConciseArray() {
        int pos = 0;
        for(HuffmanTree t : treeQueue) {
            this.conciseArray[pos] = t.root.value;
        }
    }

    /*
    * Helper method for extractCodes that iterates through the final HuffmanTree and assigns new binary values
    *
    * @param root that is the root of the final HuffmanTree
    * @param s that is the string containing the new binary value
     */
    private void printCode(Node root, String s) {
        if (root.left == null || root.right == null && Character.isLetter(root.value)) {
            this.codes.put(root.value, s);
            return;
        }
        printCode(root.left, s + "0");
        printCode(root.right, s + "1");
    }

    /*
    * Method that reads through original text and converts each character into its new binary form.
    *
    * @param s which is the string that contains the original text
     */
    private void encode(String s) {
        System.out.println("Encoding text...");
        double start = System.currentTimeMillis();
        StringBuilder coded = new StringBuilder();
        for(int i = 0; i < s.length(); i++) {
            coded.append(this.codes.get(s.charAt(i)));
        }
        this.bits = coded.toString();
        int numberOfBytes = coded.length() / 8;
        this.compressedFinal = new byte[this.bits.length() % 8 == 0 ? numberOfBytes : numberOfBytes + 1];
        int index = 0, j = 0;
        for (int i = 0; i < this.bits.length(); i += j) {
            j = i + 8 < this.bits.length() ? 8 : this.bits.length() - i;
            this.compressedFinal[index++] = (byte) Integer.parseInt(this.bits.substring(i, i + j), 2);
        }
        double end = System.currentTimeMillis();
        System.out.println("Finished in " + ((end - start) / 1000)+ " seconds.");
        System.out.println();
    }

    /*
    * Method that prints the list of character codes and the compressed version of the original text to txt files.
    *
    * @param s which is the original text
     */
    private void printToFile(String s) {
        System.out.println("Printing compressed file and codes file...");
        double start = System.currentTimeMillis();
        try(PrintWriter pw = new PrintWriter(new File("codes.txt"))) {
            pw.print("");
            pw.append(this.codes.toString());
        } catch (IOException e) {
            System.out.println(e);
        }

        try(FileOutputStream fos = new FileOutputStream(new File("compressed.txt"))) {
            fos.write(this.compressedFinal);
        } catch (IOException e) {
            System.out.println(e);
        }

        double end = System.currentTimeMillis();
        System.out.println("Finished in " + ((end - start) / 1000)+ " seconds.");
        System.out.println();



    }


}
