/*
 * Stephen VanLuven
 * Griffin Ryan
 * BurgerBaron Project, Main.java
 * TCSS 342 - Spring 2022
 */
package com.company;
/*
* Generic custom stack class. Implements custom Stackable interface.
 */
public class MyStack<T> implements Stackable<T> {
    //Previous node field.
    private MyStack<T> previous;
    //Value of node field
    private T value;

    //Empty constructor method.
    MyStack() {}

    //Constructor method that sets previous node and new current value.
    MyStack(MyStack<T> previous, T value) {
        this.previous = previous;
        this.value = value;
    }

    //Overridden push method that creates new node, sets it as previous, and sets "this" node with new value
    @Override
    public void push(T value) {
        this.previous = new MyStack<>(this.previous, this.value);
        this.value = value;
    }

    //Overridden pop method that deletes current node and returns its value
    @Override
    public T pop() {
        if (this.isEmpty())
            throw new IllegalArgumentException("Stack is empty!");

        T top = this.value;
        this.value = this.previous.value;
        this.previous = this.previous.previous;

        return top;
    }

    //Overridden peek method that returns current node value
    @Override
    public T peek() {
        return this.value;
    }

    //Method that checks to see if current MyStack is empty
    public boolean isEmpty() {
        return this.previous == null;
    }

    //Overridden size method that returns size of current MyStack
    @Override
    public int size() {
        return this.isEmpty() ? 0 : 1 + this.previous.size();
    }

    //Overridden toString method that prints out each object in MyStack object. Top most objects will be printed first.
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        MyStack<T> temp = this;

        if (temp.isEmpty()) {
            throw new IllegalArgumentException("Stack is empty!");
        }

        while(!temp.isEmpty()) {
            result.append(temp.pop());
            if (temp.previous == null) {
                result.append("""
                        ]

                        """);
            } else {
                result.append(", ");
            }
        }

        return result.toString();
    }


}
