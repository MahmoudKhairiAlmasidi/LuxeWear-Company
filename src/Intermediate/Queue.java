package Intermediate;

import java.util.ArrayList;

public class Queue {
    private int Back;
    private int Front;
    private ArrayList<String> arr;
    
    public Queue() { Back = -1; Front = -1; arr = new ArrayList<>(); }
    
    public boolean isFull() { return false; }
    
    public boolean isEmpty() { return Back == -1 && Front == -1; }
    
    public void push(String Element) {
        if(isEmpty()) { ++Front; ++Back; arr.add(Element); }
        else { ++Back; arr.add(Element); } }
    
    public void pop() {
        if(isEmpty()) { System.out.println("Queue is empty."); }
        else if(Back == Front) { Back = -1; Front = -1; arr.clear(); }
        else ++Front; }
    
    public String getFront() {
        if(isEmpty()) { System.out.println("Queue is empty."); return null; } 
        else return arr.get(Front); }
    
    public int getSize() { if(isEmpty()) return 0; return Back - Front + 1; }
    
    public String display() { 
        String dis = "";
        if(isEmpty()) { System.out.println("Queue is empty."); return null; }
        for(int i = Front; i <= Back; i++) { dis += (arr.get(i) + "  \n"); }
         return dis;}
}