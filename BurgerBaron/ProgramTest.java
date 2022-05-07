/*
 * Stephen VanLuven
 * Griffin Ryan
 * BurgerBaron Project, Main.java
 * TCSS 342 - Spring 2022
 */
package com.company;

import org.junit.*;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.assertEquals;

/*
* Test class that uses JUnit testing to ensure Burger and MyStack methods work properly.
 */
public class ProgramTest {
    @Test
    @DisplayName("Testing adding, removing, and contents of burger.")
    public void testBurger() {
        Burger test = new Burger(true);
        test.buildBurger();
        assertEquals(15, test.size());
        assertEquals("Pickle", test.peek());
        for(int i = 0; i < 5; i++) {
            test.pop();
        }
        assertEquals("Tomato", test.peek());
        assertEquals(10, test.size());
    }

    @Test
    @DisplayName("Testing different burger build")
    public void testBurger2() {
        Burger test = new Burger(false);
        test.changePatties("Chicken");
        test.addPatty();
        test.addCategory("Veggies");
        test.removeIngredient("Onions");
        test.addIngredient("Cheddar");
        test.buildBurger();
        assertEquals("Pickle", test.peek());
        assertEquals(9, test.size());
        for(int i = 0; i < 5; i++) {
            test.pop();
        }
        assertEquals("Cheddar", test.peek());
        test.pop();
        assertEquals("Chicken", test.peek());
        assertEquals(3, test.size());

    }

    @Test
    @DisplayName("Testing adding, removing, and size of stack.")
    public void testMyStack() {
        MyStack test = new MyStack();
        for(int i = 0; i < 100; i++) {
            test.push(i);
        }
        assertEquals(100, test.size());
        assertEquals(99, test.peek());
        for(int i = 0; i < 50; i++) {
            test.pop();
        }
        assertEquals(49, test.peek());
        assertEquals(50, test.size());

    }
}
