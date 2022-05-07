/*
 * Stephen VanLuven
 * Griffin Ryan
 * BurgerBaron Project, Main.java
 * TCSS 342 - Spring 2022
 */
package com.company;
/*
* Burger class that extends custom MyStack class with <String> objects.
 */
public class Burger extends MyStack<String> {
    //Ingredient fields. True if on the burger, false otherwise.
    private boolean pickle;
    private boolean mayo;
    private boolean baronSauce;
    private boolean lettuce;
    private boolean tomato;
    private boolean onion;
    private boolean pepperJack;
    private boolean mozzarella;
    private boolean cheddar;
    private boolean mushroom;
    private boolean ketchup;
    private boolean mustard;
    //Patty count always defaults to one.
    private int pattyCount = 1;
    //Patty type always defaults to beef.
    private String pattyType = "Beef";

    //Empty constructor method.
    Burger() {

    }

    /*
    * Constructor method that takes boolean argument. If true, all ingredients set to true. False otherwise.
    * @param theWorks is the boolean for all ingredients.
     */
    Burger(boolean theWorks) {
        if (!theWorks) {
            new Burger();
        } else {
            this.pickle = true;
            this.mayo = true;
            this.baronSauce = true;
            this.lettuce = true;
            this.tomato = true;
            this.onion = true;
            this.pepperJack = true;
            this.mozzarella = true;
            this.cheddar = true;
            this.mushroom = true;
            this.ketchup = true;
            this.mustard = true;
        }

    }

    /*
    * Method that will change the patty type to Chicken or Veggie if requested.
    * @param pattyType is a string value containing new patty type.
     */
    void changePatties(String pattyType) {
        this.pattyType = pattyType;
    }

    /*
    * Method that will increment patty count by 1.
     */
    void addPatty() {
        pattyCount++;
    }

    /*
    * Method that will add entire category of ingredients if requested.
    * @param type is the category of ingredients requested.
     */
    void addCategory(String type) {
        switch (type) {
            case "Cheese" -> {
                this.cheddar = true;
                this.mozzarella = true;
                this.pepperJack = true;
            }
            case "Sauce" -> {
                this.ketchup = true;
                this.mayo = true;
                this.baronSauce = true;
                this.mustard = true;
            }
            case "Veggies" -> {
                this.mushroom = true;
                this.lettuce = true;
                this.tomato = true;
                this.onion = true;
                this.pickle = true;
            }
            default -> {
            }
        }
    }

    /*
    * Method that will remove requested category.
    * @param type is requested category.
     */
    void removeCategory(String type) {
        switch (type) {
            case "Cheese" -> {
                this.cheddar = false;
                this.mozzarella = false;
                this.pepperJack = false;
            }
            case "Sauce" -> {
                this.ketchup = false;
                this.mayo = false;
                this.baronSauce = false;
                this.mustard = false;
            }
            case "Veggies" -> {
                this.mushroom = false;
                this.lettuce = false;
                this.tomato = false;
                this.onion = false;
                this.pickle = false;
            }
            default -> {
            }
        }

    }

    /*
    * Method that will add individually requested ingredient.
    * @param type is the ingredient
     */
    void addIngredient(String type) {
        String lowerCase = type.toLowerCase();
        switch (lowerCase) {
            case "pickle" -> this.pickle = true;
            case "mayonnaise" -> this.mayo = true;
            case "baron-sauce" -> this.baronSauce = true;
            case "lettuce" -> this.lettuce = true;
            case "tomato" -> this.tomato = true;
            case "onions" -> this.onion = true;
            case "pepperjack" -> this.pepperJack = true;
            case "mozzarella" -> this.mozzarella = true;
            case "cheddar" -> this.cheddar = true;
            case "mushrooms" -> this.mushroom = true;
            case "ketchup" -> this.ketchup = true;
            case "mustard" -> this.mustard = true;
            default -> {
            }
        }

    }

    /*
    * Method that will remove individual ingredient.
    * @param type is the ingredient to be removed.
     */
    void removeIngredient(String type) {
        String lowerCase = type.toLowerCase();
        switch (lowerCase) {
            case "pickle" -> this.pickle = false;
            case "mayonnaise" -> this.mayo = false;
            case "baron-sauce" -> this.baronSauce = false;
            case "lettuce" -> this.lettuce = false;
            case "tomato" -> this.tomato = false;
            case "onions" -> this.onion = false;
            case "pepperjack" -> this.pepperJack = false;
            case "mozzarella" -> this.mozzarella = false;
            case "cheddar" -> this.cheddar = false;
            case "mushroom" -> this.mushroom = false;
            case "ketchup" -> this.ketchup = false;
            case "mustard" -> this.mustard = false;
            default -> {
            }
        }

    }

    /*
    * Method that builds the burger in the correct order based on ingredients requested by customer.
     */
    void buildBurger() {
        this.push("Bun");
        if (ketchup) {this.push("Ketchup"); }
        if (mustard) {this.push("Mustard");}
        if (mushroom) {this.push("Mushrooms");}
        this.push(pattyType);
        if(cheddar) {this.push("Cheddar");}
        if(mozzarella) {this.push("Mozzarella");}
        if(pepperJack) {this.push("Pepperjack");}
        for(int i = 1; i < pattyCount; i++) {this.push(pattyType);}
        if(onion) {this.push("Onion");}
        if (tomato) {this.push("Tomato");}
        if (lettuce) {this.push("Lettuce");}
        if (baronSauce) {this.push("Baron Sauce");}
        if (mayo) {this.push("Mayonnaise");}
        this.push("Bun");
        if(pickle) {this.push("Pickle");}
    }

}
