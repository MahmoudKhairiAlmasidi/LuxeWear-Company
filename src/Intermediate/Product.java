package Intermediate;

import java.io.Serializable;

public class Product implements Serializable{
    private String NameOfItem;
    private int Quantity;
    private int ResourceNum;
    private int BranchNum;

    public int getBranchNum() {
        return BranchNum;
    }

    public void setBranchNum(int BranchNum) {
        this.BranchNum = BranchNum;
    }

    public Product(String NameOfItem, int Quantity, int ResourceNum, int BranchNum) {
        this.NameOfItem = NameOfItem;
        this.Quantity = Quantity;
        this.ResourceNum = ResourceNum;
        this.BranchNum = BranchNum;
    }

    public Product(String NameOfItem, int Quantity, int ResourceNum) {
        this.NameOfItem = NameOfItem;
        this.Quantity = Quantity;
        this.ResourceNum = ResourceNum;
    }

    @Override
    public String toString() {
        return "Clothing{" + "NameOfItem=" + NameOfItem + ", Quantity=" + Quantity + '}';
    }

    public String getNameOfItem() {
        return NameOfItem;
    }

    public void setNameOfItem(String NameOfItem) {
        this.NameOfItem = NameOfItem;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public Product() {
    }

    public int getResourceNum() {
        return ResourceNum;
    }

    public void setResourceNum(int ResourceNum) {
        this.ResourceNum = ResourceNum;
    }

}
