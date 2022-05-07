/*
 * Stephen VanLuven
 * Huffman Encoder Project, Main.java
 * TCSS 342 - Spring 2022
 */
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
/*
* Program reads in a plain text file and builds a Huffman compression tree based on that text. After new binary
* values are assigned for each pertinent character, the program outputs a compressed version of the original work
* as a text file.
 */
public class Main {
    /*
    * Main method that keeps track of overall run time statistic, calls the CodingTree method to compress the text,
    * and then compares old and new file sizes.
     */
    public static void main(String[] args) throws IOException {
        double start = System.currentTimeMillis();

        File in = new File("WarAndPeace.txt");
        String input = Files.readString(Path.of("WarAndPeace.txt"));

        new CodingTree(input);

        double end = System.currentTimeMillis();
        System.out.println("Total process finished in " + ((end - start) / 1000)+ " seconds.");
        File out = new File("compressed.txt");
        System.out.println("Old file size: " + in.length() / 1024 + " KB!");
        System.out.println("New compressed file size " + out.length() / 1024 + " KB!");
    }

    public static class MainTest {
        /*
        * Test method to test CodingTree class methods.
         */
        @Test
        void testCodingTree() throws IOException {
            String testInput = Files.readString(Path.of("WarAndPeace.txt"));
            CodingTree testTree = new CodingTree(testInput);
            Assertions.assertEquals(testTree.getFinalTreeWeight(), testInput.length());
        }

    }
}
