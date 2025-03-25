package Intermediate;

import java.net.*;
import java.io.*;
import java.time.*;

public class Coordinator implements Runnable {
    
    static CoordinatorGUI b_GUI;
    Socket bSocket;
    static Queue Res1 = new Queue();
    static Queue Res2 = new Queue();
    static Queue Res3 = new Queue();
    static final Object mutex = new Object();
    
    public Coordinator(Socket bSocket) { this.bSocket = bSocket; }
    
    public void run() { 
        try{
            
            ObjectOutputStream Output = new ObjectOutputStream(bSocket.getOutputStream());
            ObjectInputStream Input = new ObjectInputStream(bSocket.getInputStream());
            DataOutputStream DOut = new DataOutputStream(bSocket.getOutputStream());
            
            Socket R1;
            Queue ResQueue;
            Product p = (Product)Input.readObject();
            String RId;
            
            String bName = "";
            if(p.getBranchNum() == 1) bName = "Nasr City";
            else if(p.getBranchNum() == 2) bName = "Zamalek";
            else if(p.getBranchNum() == 3) bName = "Maadi";
            else if(p.getBranchNum() == 4) bName = "New Cairo";
            else if(p.getBranchNum() == 5) bName = "Downtwon";
            
            if(p.getResourceNum() == 1) { R1 = new Socket("localhost",1000); ResQueue = Res1; }
            else if(p.getResourceNum() == 2) { R1 = new Socket("localhost",2000); ResQueue = Res2; }
            else { R1 = new Socket("localhost",3000); ResQueue = Res3; }
            
            synchronized(mutex) {
                RId = bName + " Request: " + LocalDate.now();
                ResQueue.push(RId);
                b_GUI.appendTextArea("New Request From " + bName + " Branch Added In Resource " + p.getResourceNum() + " Requests.");
                b_GUI.appendTextArea("Resource " + p.getResourceNum() + " Requests:\n" + ResQueue.display()); }
            
            synchronized(mutex) {
                while(!ResQueue.getFront().equals(RId)) {
                    try { mutex.wait(); } catch(Exception e) { System.out.println(e); } } }
            
            ObjectOutputStream Out = new ObjectOutputStream(R1.getOutputStream());
            ObjectInputStream In = new ObjectInputStream(R1.getInputStream());
                
            Out.writeObject(p);
            Thread.sleep(7000);
            if(In.readBoolean()) { 
                b_GUI.appendTextArea("Request Of " + bName + " Branch Has Been Done Successfully.");
                b_GUI.appendTextArea(bName + " Branch Take " + p.getQuantity() + " " + p.getNameOfItem() + " From Resource " + p.getResourceNum());
                Output.writeUTF("Request Has Been Done Successfully."); 
                Output.flush();
                } else { 
                    b_GUI.appendTextArea("Request Of " + bName + " Branch Has Been Canceled.");
                    b_GUI.appendTextArea("The Quantity Of " + p.getNameOfItem() + " Is Not Available In Resource " + p.getResourceNum());
                    Output.writeUTF("Exceed Available Quantity, Request Has Been Canceled."); 
                    Output.flush(); }
                
                Out.close();
                In.close();
                R1.close();
                
                
                synchronized(mutex) {
                    ResQueue.pop();
                    b_GUI.appendTextArea("Updated Requests:\n" + ResQueue.display());
                    mutex.notifyAll();
                    }
        } catch(Exception e) { System.out.println(e); }
    }
    
    public static void main(String args[]){
        try{
            b_GUI = new CoordinatorGUI();
            b_GUI.setVisible(true);
            ServerSocket s = new ServerSocket(4000);
            System.out.println("Waiting For Requests.");
        
            while(true){
                Socket B = s.accept();
                System.out.println("Receive Request.");
            
                Thread Branch = new Thread(new Coordinator(B));
                Branch.start();
            }
        } catch(Exception e) { System.out.println(e); }
    }
}