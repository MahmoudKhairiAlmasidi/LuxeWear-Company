package ServerSide;

import java.net.*;
import java.io.*;
import Intermediate.*;
import static ServerSide.ResourceOne.Products;
import java.util.ArrayList;

public class ResourceTwo implements Runnable {
    
    static CoordinatorGUI c_GUI;
    Socket bSocket;
    static ArrayList<Product> Products = new ArrayList<Product>();
    
    public ResourceTwo(Socket bSocket) { 
        this.bSocket = bSocket; 
        Products.add(new Product("Pants", 10000,2));
        Products.add(new Product("Shirt", 20000, 2));
        Products.add(new Product("T-Shirt", 18000, 2));
        Products.add(new Product("Coat", 15000, 2));
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
        ServerSocket ResOne = new ServerSocket(2000);
//        c_GUI = new Coordinator();
//        c_GUI.setVisible(true);
        System.out.println("Waiting For Requests.");
        
        while(true) {
            Socket B = ResOne.accept();
            System.out.println("Receive Request.");
            Thread Branch = new Thread(new ResourceOne(B));
            Branch.start();
        }
    }
}