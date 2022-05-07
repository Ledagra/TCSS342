/*
* Stephen VanLuven
* Griffin Ryan
* BurgerBaron Project, Main.java
* TCSS 342 - Spring 2022
 */
package com.company;
import java.io.*;
import java.util.*;

/*
* Program reads burger orders form a .txt input file. Program adds/removes ingredients according to customer
* order and then builds the burger in the proper order. Finally, each ingredient is output into a file ("trace.txt")
* starting with the top most ingredient on the left and the last ingredient on the right.
 */
public class Main {
    //Global variable to track order number.
    static int orderNum = 0;
    //Global variable to print result to .txt file.
    static FileWriter out = null;

    /*
    * Main method sets up input and output. Then, if successful, parses through orders line by line and then prints
    * out the complete burgers on the next line.
    * @param args
     */
    public static void main(String[] args) throws IOException {
        File in = new File(args[0]);
        Scanner input;

        input = new Scanner(in);
        out = new FileWriter("trace.txt");

        while(input.hasNextLine()) {
            String line = input.nextLine();
            parseLine(line);
        }
       out.close();
    }

    /*
    * Method that reads through each word on the order to ensure correct ingredients are added/removed. First prints
    * out order number, then repeats customer order, sets appropriate ingredients to true/false, builds the burger in
    * the correct order, and then prints out resulting burger.
    * @param line is the String containing each customer order.
     */
    static void parseLine(String line) throws IOException {
        String result = "Processing Order " + orderNum++ + ": ";
        result += line + "\n";
        out.write(result);

        Burger burger;

        //Checks to see if burger is getting "the works."
        if(line.contains("Baron Burger"))
            burger = new Burger(true);
          else
            burger = new Burger();

        //Reads through order to find exact match case for "Veggie" which would mean customer wants vegan option
        StringTokenizer bType = new StringTokenizer(line);
        String bString;
        for (int i = 0; i < bType.countTokens(); i++) {
            bString = bType.nextToken();
            if(bString.equals("Veggie")) {
                burger.changePatties("Veggie");
            }
        }
        //Checks to see if customer wants chicken burger
        if(line.contains("Chicken"))
            burger.changePatties("Chicken");

        //Checks to see if customer wants more than one patty.
        if(line.contains("Double")) {
            burger.addPatty();
        }
        if(line.contains("Triple")) {
            burger.addPatty();
            burger.addPatty();
        }

        StringTokenizer st;

        //Orders have four different syntax styles. <Order> with <Additions> is read here.
        if (line.contains("with") && !line.contains("but no")) {
            st = new StringTokenizer(line);
            while(st.hasMoreTokens()) {
                if(st.nextToken().equals("with")) {
                    break;
                }
            }
            while(st.hasMoreTokens()) {
                String s;
                s = st.nextToken();
                switch(s) {
                    case "Veggies", "Sauce", "Cheese" -> burger.addCategory(s);
                    case "Lettuce", "Tomato", "Onions", "Pickle", "Mushrooms",
                            "Ketchup", "Mustard", "Mayonnaise", "Baron-Sauce",
                            "Cheddar", "Mozzarella", "Pepperjack" -> burger.addIngredient(s);
                    default -> {
                    }
                }
            }
        }

        //<Order> with <Additions> but no <Exceptions> read here.
        if (line.contains("with") && line.contains("but no")) {
            st = new StringTokenizer(line);
            while(st.hasMoreTokens()) {
                String s;
                s = st.nextToken();
                if(s.equalsIgnoreCase("but")) {
                    break;
                }
                switch(s) {
                    case "Veggies", "Sauce", "Cheese" -> burger.addCategory(s);
                    case "Lettuce", "Tomato", "Onions", "Pickle", "Mushrooms",
                            "Ketchup", "Mustard", "Mayonnaise", "Baron-Sauce",
                            "Cheddar", "Mozzarella", "Pepperjack" -> burger.addIngredient(s);
                    default -> {
                    }
                }
            }
            while(st.hasMoreTokens()) {
                String s;
                s = st.nextToken();
                switch(s) {
                    case "Veggies", "Sauce", "Cheese" -> burger.removeCategory(s);
                    case "Lettuce", "Tomato", "Onions", "Pickle", "Mushrooms",
                            "Ketchup", "Mustard", "Mayonnaise", "Baron-Sauce",
                            "Cheddar", "Mozzarella", "Pepperjack" -> burger.removeIngredient(s);
                    default -> {
                    }
                }
            }
        }

        //<Order> with no <Omissions> read here.
        if(line.contains("with no") && !line.contains("but")) {
            st = new StringTokenizer(line);
            while(st.hasMoreTokens()) {
                if(st.nextToken().equals("no")) {
                    break;
                }
            }
            while(st.hasMoreTokens()) {
                String s;
                s = st.nextToken();
                switch(s) {
                    case "Veggies", "Sauce", "Cheese" -> burger.removeCategory(s);
                    case "Lettuce", "Tomato", "Onions", "Pickle", "Mushrooms",
                            "Ketchup", "Mustard", "Mayonnaise", "Baron-Sauce",
                            "Cheddar", "Mozzarella", "Pepperjack" -> burger.removeIngredient(s);
                    default -> {
                    }
                }
            }

        }

        //<Order> with no <Omissions> but <Exceptions> read here.
        if(line.contains("with no") && line.contains("but")) {
            st = new StringTokenizer(line);
            while(st.hasMoreTokens()) {
                String s;
                s = st.nextToken();
                if(s.equalsIgnoreCase("but")) {
                    break;
                }
                switch(s) {
                    case "Veggies", "Sauce", "Cheese" -> burger.removeCategory(s);
                    case "Lettuce", "Tomato", "Onions", "Pickle", "Mushrooms",
                            "Ketchup", "Mustard", "Mayonnaise", "Baron-Sauce",
                            "Cheddar", "Mozzarella", "Pepperjack" -> burger.removeIngredient(s);
                    default -> {
                    }
                }
            }
            while(st.hasMoreTokens()) {
                String s;
                s = st.nextToken();
                switch(s) {
                    case "Veggies", "Sauce", "Cheese" -> burger.addCategory(s);
                    case "Lettuce", "Tomato", "Onions", "Pickle", "Mushrooms",
                            "Ketchup", "Mustard", "Mayonnaise", "Baron-Sauce",
                            "Cheddar", "Mozzarella", "Pepperjack" -> burger.addIngredient(s);
                    default -> {
                    }
                }
            }

        }

        burger.buildBurger();
        out.write(burger.toString());
    }
}
