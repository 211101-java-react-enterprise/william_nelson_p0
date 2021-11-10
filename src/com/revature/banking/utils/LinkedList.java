package com.revature.banking.utils;



//TODO Not Finished
public class LinkedList<T> implements List<T> {

    private int size;
    private Node<T> head; // implicitly null
    private Node<T> tail = null; // you can explicitly declare them as null, but it's not required.

    @Override
    public boolean add(T data) {

        // Not required, as some data structures do allow for null values.
        if (data == null) {
            return false;
        }

        Node<T> newNode = new Node<>(data);
        if (head == null) {
            tail = head = newNode;
        } else {
            tail = tail.nextNode = newNode;
        }

        size++;

        return true;

    }

    @Override
    public boolean contains(T data) {

        Node<T> runner = head;
        while (runner != null) {
            if (runner.data.equals(data)) {
                return true;
            }
            runner = runner.nextNode;
        }

        // No node with matching data was found
        return false;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // TODO: IMPLEMENT ME!
    @Override
    public boolean remove(T element) {
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    // TODO: IMPLEMENT ME!
    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new RuntimeException("Provided index is out of bounds");
        }

        Node<T> currentNode = head;
        for (int i = 0; i <= index; i++) {
            if (i == index) {
                return currentNode.data;
            }
            currentNode = currentNode.nextNode;
        }

        return null;
    }

    //Function to send the
    @Override
    public String toString() {

        if (head == null) {
            return "[ ]";
        }

        StringBuilder strBldr = new StringBuilder();
        strBldr.append("[ ");

        Node<T> runner = head;
        for (int i = 0; i < this.size; i++) {
            if (i == size - 1) {
                strBldr.append(runner.data.toString()).append(" ");
            } else {
                strBldr.append(runner.data.toString()).append(", ");
            }
            runner = runner.nextNode;
        }

        strBldr.append("]");

        return strBldr.toString();
    }


    public static void staticMethodExample() {
        System.out.println("This is a method that shadows the static method declared by List");
    }

    // Nested Inner Class
    // The outer class (LinkedList) can see all of the members of this class
    private static class Node<T> {
        T data;
        Node<T> nextNode;

        public Node(T data) {
            this.data = data;
        }
    }

}
