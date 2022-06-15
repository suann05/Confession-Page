/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package confessionpage2;

import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author ngsua
 */
public class Queue<E> {
    
    E[]e;
    private java.util.LinkedList<E> list = new java.util.LinkedList<>();

    public Queue() {
        list = new LinkedList<>();
    }
    
    public Queue(E[] e){
        list = new LinkedList<>(Arrays.asList(e));
    }
    
    public void enqueue(E e){ //add the element into linked list
        list.addLast(e);
    }
    
    public E dequeue(){ //delete element in the linked list
        return list.removeFirst();
    }
    
    public E getElement(){ //get the first element
        return list.getFirst();
    }
    
    public E getElementAt(){ //get last element
        return list.getLast();
    }

    @Override
    public String toString() { //print out the queue list
        return "Queue{" + "list=" + list + '}';
    }
    
    
    
}
