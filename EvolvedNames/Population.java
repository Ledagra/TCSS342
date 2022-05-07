/*
 * Stephen VanLuven
 * Evolved Names Project, Population.java
 * TCSS 342 - Spring 2022
 */
import java.util.Arrays;
import java.util.Random;

/*
* Population class that creates a user-defined sized array of Genome objects and keeps track of most fit Genome within
* the array.
 */
public class Population {
    // Array of Genome objects.
    private Genome[] array;
    // The genome within the array that is most like the target string.
    private Genome mostFit;
    // Field that keeps track of how big "half" the array size is for future manipulation of population.
    private int half;

    /*
    * Constructor method that takes in user-defined number of genomes to be created and what their mutation rate is.
    *
    * @param numGenomes is number of genomes to be created within array
    * @param mutationRate is the rate at which genomes can be mutated
     */
    Population(int numGenomes, double mutationRate) {
        this.array = new Genome[numGenomes];
        for(int i = 0; i < this.array.length; i++) {
            this.array[i] = new Genome(mutationRate);
        }
        this.mostFit = findFittest();
        this.half = (int) this.array.length / 2;
    }

    /*
    * Simulated "day" or "generation." Each day consists of finding the most fit genome, arranging array in order of
    * most fit, deleting least fit half of population array, and refilling that half with crossovers or mutated clones
    * of most fit half.
     */
    public void day() {
        this.mostFit = findFittest();
        Arrays.sort(this.array);
        for(int i = this.half; i < getArraySize(); i++) {
            this.array[i] = null;
        }
        for(int i = this.half; i < getArraySize(); i++) {
            if(heads()) {
                this.array[i] = new Genome(randoGene());
                this.array[i].mutate();
            } else {
                this.array[i] = new Genome(randoGene());
                this.array[i].crossover(randoGene());
                this.array[i].mutate();
            }
        }


    }
    /*
    * Getter method to return population array size.
    *
    * @return array size as int
     */
    public int getArraySize() {
        return this.array.length;
    }

    /*
    * Getter method that returns most fit Genome in array.
    *
    * @return Genome that is most fit
     */
    public Genome getMostFit() {
        return this.mostFit;
    }

    /*
    * Method that iterates through whole Population array and identifies most fit Genome.
    *
    * @return Genome that is most fit.
     */
    private Genome findFittest() {
        Genome result = new Genome(this.array[0]);

        for(int i = 1; i < this.array.length; i++) {
            if(this.array[i].getFitness() < result.getFitness()) {
                result = this.array[i];
            }
        }
        return result;
    }

    /*
    * Method that uses even and odd numbers to calculate heads or tails.
    *
    * @return boolean that represents heads or tails.
     */
    private boolean heads() {
        Random rnd = new Random();
        return rnd.nextInt() % 2 == 0;
    }

    /*
    * Method that randomly chooses gene to be cloned or crossover'd from most fit half of population
    *
    * @return Genome to be selected.
     */
    private Genome randoGene() {
        Random rnd = new Random();
        return this.array[rnd.nextInt(half - 1)];
    }


}
