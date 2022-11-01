/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import View.SIFrame;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author engan
 */
public class SIHeader {
    private int InvoiceNumber;
    private String CustomerName;
    private int InvoiceTotal;
    private Date date;

   

    public int getInvoiceNumber() {
        return InvoiceNumber;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public Date getDate() {
        return date;
    }
    private ArrayList<SILine>line; 
 
    
  
    public SIHeader(int InvoiceNumber, String CustomerName, Date date) {
        this.InvoiceNumber = InvoiceNumber;
        this.CustomerName = CustomerName;
        this.date = date;
    }
    
    
   

    public int getInvoiceTotal() {
        return getLine().stream().mapToInt(line -> line.getItemTotal()).sum();
    }
    
    public int getInvoiceTotal2() {
        int total = 0;
        for (SILine line : getLine()) {
            total += line.getItemTotal();
        }
        return total;
    }

    public ArrayList<SILine> getLine() {
        if (line==null){line=new ArrayList<>();}
        return line;
    }
    

    public void setDate(Date date) {
        this.date = date;
    }

    public void setInvoiceNumber(int InvoiceNumber) {
        this.InvoiceNumber = InvoiceNumber;
    }

    public void setCustomerName(String CustomerName) {
        this.CustomerName = CustomerName;
    }

    @Override
    public String toString() {
        return "SIHeader{" + "InvoiceNumber=" + InvoiceNumber + ", CustomerName=" + CustomerName + ", InvoiceTotal=" + InvoiceTotal + ", date=" + date + '}';
    }

    public String getAsCSV(){
return InvoiceNumber+","+SIFrame.sdf.format(date)+","+CustomerName;
    }
    }
