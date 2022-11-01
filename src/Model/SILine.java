/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import View.SIFrame;

/**
 *
 * @author engan
 */
public class SILine  {
    
    private String ItemName;
    private int ItemPrice;
    private int Count;

    public SIHeader getInv() {
        return inv;
    }
    private SIHeader inv; 
    

   
    public SILine( String ItemName, int ItemPrice, int Count,SIHeader inv) {
      
        this.ItemName = ItemName;
        this.ItemPrice = ItemPrice;
        this.Count = Count;
        this.inv=inv;
        inv.getLine().add(this);
        
    }

    
    

    public String getItemName() {
        return ItemName;
    }

    public int getItemPrice() {
        return ItemPrice;
    }

    public int getCount() {
        return Count;
    }

    

    public void setItemName(String ItemName) {
        this.ItemName = ItemName;
    }

    public void setItemPrice(int ItemPrice) {
        this.ItemPrice = ItemPrice;
    }

    public void setCount(int Count) {
        this.Count = Count;
    }
    public int getItemTotal(){
        return Count*ItemPrice;
    }

    @Override
    public String toString() {
        return "SILine{" + ", ItemName=" + ItemName + ", ItemPrice=" + ItemPrice + ", Count=" + Count + '}';
    }
    public String getAsCSV(){
return getInv().getInvoiceNumber()+","+ItemName+","+ItemPrice+","+Count;
    }
   } 

