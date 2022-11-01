/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import View.SIFrame;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author engan
 */
public class HeaderTableModel extends AbstractTableModel{
private ArrayList<SIHeader>invoices;
private String[] columns = {"Num","Name","Date","Total"};

    public HeaderTableModel() {
        
    }

    @Override
    public int getRowCount() {
      return invoices.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;}

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        SIHeader si=invoices.get(rowIndex);
        switch(columnIndex)
        {
            case 0:
                return si.getInvoiceNumber();
            case 1:
                return si.getCustomerName();
            case 2:
                return SIFrame.sdf.format(si.getDate());
            case 3:
                return si.getInvoiceTotal();
            default:
                return"";
        
        }
      }
    

public String getColumnName(int column){
        return columns[column];}

    /**
     *
     * @param invoices
     */
    public HeaderTableModel(ArrayList<SIHeader> invoices) {
        this.invoices = invoices;
    }
    
     

}
  

    

