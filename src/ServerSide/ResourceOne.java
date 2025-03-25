package ServerSide;

import java.net.*;
import java.io.*;
import Intermediate.*;
import java.util.ArrayList;

public class ResourceOne implements Runnable {
    
    Socket bSocket;
    static ArrayList<Product> Products = new ArrayList<Product>();
    
    public ResourceOne(Socket bSocket) { 
        this.bSocket = bSocket; 
        Products.add(new Product("Pants", 20000, 1));
        Products.add(new Product("Shirt", 15000, 1));
        Products.add(new Product("T-Shirt", 30000, 1));
        Products.add(new Product("Coat", 17000, 1));
    }
    
    public void run() {
        try {
            
            ObjectOutputStream Output = new ObjectOutputStream(bSocket.getOutputStream());
            ObjectInputStream Input = new ObjectInputStream(bSocket.getInputStream());
            
            Product item = (Product)Input.readObject();
            
            for (Product c : Products) {
                if (item.getNameOfItem().equals(c.getNameOfItem())) {
                    if (item.getQuantity() <= c.getQuantity()) {
                        c.setQuantity(c.getQuantity() - item.getQuantity());
                        Output.writeBoolean(true);
                    } else Output.writeBoolean(false);
                }
            }
            
            Output.close();
            Input.close();
            bSocket.close();
        } catch(Exception e) { System.out.println(e); }
    }
    
    public static void main(String[] args) throws IOException {
        ServerSocket ResOne = new ServerSocket(1000);
        System.out.println("Waiting For Requests.");
        
        while(true) {
            Socket B = ResOne.accept();
            System.out.println("Receive Request.");
            Thread Branch = new Thread(new ResourceOne(B));
            Branch.start();
        }
    }
}