/*
 * Stephen VanLuven
 * Evolved Names Project, Main.java
 * TCSS 342 - Spring 2022
 */
import org.junit.Assert;
import org.junit.Test;
/*
 * Program manipulates an array of strings such that each string starts out as "A" and ends up at the target string
 * over the course of many iterations. Each generation consists of ordering the array by how fit each string is in
 * comparison to the target, deleting the least fit half of the population, and then refilling the now empty half
 * with Strings that are compromised of the more fit half. This cycle is repeated until at least one of the Strings
 * matches the target exactly.
 */
public class Main {

    /*
    * Main method that creates the string population, iterates until the target is reached, and then prints out
    * time and generations statistics at the end.
    *
    * @param args
     */
    public static void main(String[] args) {
        double startTime = System.currentTimeMillis();
        Population test = new Population(100, 0.05);
        int gen = 0;
        System.out.println("Most fit gene before process: " + test.getMostFit().toString());
        System.out.println("Fitness: " + test.getMostFit().getFitness());
        System.out.println();
        while(test.getMostFit().getFitness() != 0) {
            System.out.println("Gen " + gen + ":" + " (\"" + test.getMostFit().toString() + "\", " +
                    test.getMostFit().getFitness() + ")");

            test.day();
            gen++;
        }
        System.out.println("Gen " + gen + ":" + " (\"" + test.getMostFit().toString() + "\", " +
                test.getMostFit().getFitness() + ")");
        double endTime = System.currentTimeMillis();
        double duration = (endTime - startTime) / 1000;
        System.out.println("Generations: " + (gen + 1));
        System.out.println("This operation took " + duration + " seconds!");
    }

    /*
    * Test class for the Genome and Population classes.
     */
    public static class MainTest {
        /*
        * Tests functionality of Genome class.
         */
        @Test
        public void testGenome() {
            Genome ex = new Genome(0.05);
            Assert.assertEquals(77, ex.getFitness());
            Assert.assertEquals("A",ex.toString());
            Genome ex1 = new Genome(ex);
            Assert.assertEquals(77, ex1.getFitness());
            Assert.assertEquals("A", ex1.toString());
        }

        /*
        * Tests functionality of population class.
         */
        @Test
        public void testPopulation() {
            Population ex = new Population(100, 0.05);
            Assert.assertEquals(100, ex.getArraySize());
            Assert.assertEquals("A", ex.getMostFit().toString());
            Assert.assertEquals(77, ex.getMostFit().getFitness());
            while (ex.getMostFit().getFitness() != 0) {
                ex.day();
            }
            Assert.assertEquals(0, ex.getMostFit().getFitness());
            Assert.assertEquals(ex.getMostFit().getTarget(), ex.getMostFit().toString());
        }
    }
}
