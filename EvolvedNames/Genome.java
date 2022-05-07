/*
 * Stephen VanLuven
 * Evolved Names Project, Genome.java
 * TCSS 342 - Spring 2022
 */
import java.util.Random;

/*
* Genome class that creates a Genome object that is to be a part of the Population class array within the Evolved Names
* project.
 */
public class Genome implements Comparable<Genome>{
    //Alphabet array defines what the acceptable chars within the gene field may be.
    private final char[] ALPHABET = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
                                    'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', ' ', '-', '\''};
    //Target String that gene strings will be eventually manipulated into.
    private final String target = "PAULO SERGIO LICCIARDI MESSEDER BARRETO";
    //User defined chance that a Genome object will be mutated per generation. Range of (0 - 1.0)
    private final double MUTATIONRATE;
    //How close each Genome's string is to the target. The lower the number, the closer to the target it is.
    private int fitness;
    //The Genome's string. Defaults to "A", and will eventually be changed to match the target.
    private String gene;

    /*
    * Constructor that takes double arg and sets mutation rate.
    *
    * @param mutRate is the user-defined mutation chance for this Genome.
     */
    Genome(double mutRate) {
        this.MUTATIONRATE = mutRate;
        this.gene = "A";
        this.fitness = fitness();
    }

    /*
    * Constructor that takes another gene and copies all its field information.
    *
    * @param other is the genome to be copied.
     */
    Genome (Genome other) {
        this.gene = other.gene;
        this.MUTATIONRATE = other.MUTATIONRATE;
        this.fitness = fitness();
    }

    /*
    * Method that will, in order, add a random char, delete a random char (if Genome string is size 2 or higher),
    * and/or replace a random char.
     */
    public void mutate() {
        //add random char
        if(mutChance() < this.MUTATIONRATE) {
            StringBuilder temp = new StringBuilder(this.gene);
            temp.insert(randoPos(), randoChar());
            this.gene = String.valueOf(temp);
            
        }
        //delete random char
        if(this.gene.length() >= 2) {
            if(mutChance() < this.mutChance()) {
                StringBuilder temp = new StringBuilder(this.gene);
                temp.deleteCharAt(randoPos());
                this.gene = String.valueOf(temp);
            }
        }
        //replace random char
        if(mutChance() < this.MUTATIONRATE) {
            StringBuilder temp = new StringBuilder(this.gene);
            temp.setCharAt(randoPos(), randoChar());
            this.gene = String.valueOf(temp);
        }
        this.fitness = fitness();

    }

    /*
    * Method that takes two Genome objects and blends their string fields together. Iterates through each gene String,
    * randomly choosing a letter to add until it cannot.
    *
    * @param Genome other is the Genome with which to blend gene Strings with.
     */
    public void crossover(Genome other) {
        StringBuilder temp = new StringBuilder("");
        int max = Math.max(this.gene.length(), other.gene.length());
        char[] thisOne = new char[max];
        char[] otherOne = new char[max];

        for(int i = 0; i < this.gene.length(); i++) {
            thisOne[i] = this.gene.charAt(i);
        }

        for(int i = 0; i < other.gene.length(); i++) {
            otherOne[i] = other.gene.charAt(i);
        }

        for(int i = 0; i < max; i++) {
            if (heads()) {
                if (isInAlphabet(thisOne[i])) {
                    temp.append(thisOne[i]);
                } else {
                    break;
                }
            } else {
                if (isInAlphabet(otherOne[i])) {
                    temp.append(otherOne[i]);
                } else {
                    break;
                }
            }
        }



        this.gene = String.valueOf(temp);
        this.fitness = fitness();
    }

    /*
    * Method that calculates fitness level of current Genome gene String.
    *
    * @return int representing fitness level
     */
    private int fitness() {
        int n = this.gene.length();
        int m = this.target.length();
        int f = Math.abs(n - m) * 2;
        for(int i = 0; i < n && i < m; i++) {
            if (this.gene.charAt(i) != this.target.charAt(i)) {
                f++;
            }
        }
        return f;
    }

    /*
    * Method that returns Genome gene String.
    *
    * @return gene String
     */
    public String toString() {
        return this.gene;
    }

    /*
    * Getter method that returns gene fitness level
    *
    * @return int fitness level
     */
    public int getFitness() {
        return fitness;
    }

    /*
    * Method that randomly generates a number 0-1 inclusive that will decide if a gene is to be mutated.
    *
    * @return double number to decide if a gene is mutated
     */
    private double mutChance() {
        Random rnd = new Random();
        return rnd.nextDouble();
    }

    /*
    * Method that randomly generates a position for a character to be added, deleted, or replaced. 0 - gene length
    * inclusive
    *
    * @return int char position in gene string to be altered
     */
    private int randoPos() {
        Random rnd = new Random();
        return rnd.nextInt(this.gene.length());
    }

    /*
    * Method that randomly chooses char from alphabet to insert or replace into gene string.
    *
    * @return char to be inserted or be used as replacement
     */
    private char randoChar() {
        Random rnd = new Random();
        return ALPHABET[rnd.nextInt(ALPHABET.length)];
    }

    /*
    * Method that calculates heads or tails by using odd or even number.
    *
    * @return boolean that decides "heads or tails."
     */
    private boolean heads() {
        Random rnd = new Random();
        return rnd.nextInt() % 2 == 0;
    }

    /*
    * Method that checks to see if character in string is in the acceptable alphabet.
    *
    * @param c is the char to be checked
    * @return true or false if character is in alphabet
     */
    private boolean isInAlphabet(char c) {
        for(char letter : ALPHABET) {
            if (letter == c) {
                return true;
            }
        }
        return false;
    }

    /*
    * Method that returns target strings
    *
    * @return target String
     */
    public String getTarget() {
        return this.target;
    }

    /*
    * Override to compareTo method that allows the Genomes to be sorted within an array according to fitness.
    *
    * @param Genome o to be compared.
    * @return int comparing order
     */
    @Override
    public int compareTo(Genome o) {
        return this.fitness - o.fitness;
    }
}
