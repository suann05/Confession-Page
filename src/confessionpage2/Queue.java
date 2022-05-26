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
    
    public void enqueue(E e){
        list.addLast(e);
    }
    
    public E dequeue(){
        return list.removeFirst();
    }

    @Override
    public String toString() {
        return "Queue{" + "list=" + list + '}';
    }
    
    
    
}
