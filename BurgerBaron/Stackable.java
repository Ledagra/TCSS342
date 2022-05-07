/*
 * Stephen VanLuven
 * Griffin Ryan
 * BurgerBaron Project, Main.java
 * TCSS 342 - Spring 2022
 */
package com.company;
public interface Stackable<T> {
    void push(T value);
    T pop();
    T peek();
    boolean isEmpty();
    int size();
    String toString();
}
