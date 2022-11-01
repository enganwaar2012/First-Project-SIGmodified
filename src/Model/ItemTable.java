/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import javax.swing.event.EventListenerList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author engan
 */
public class ItemTable extends AbstractTableModel{
    
private ArrayList<SILine> items;
    private String[] columns = {"Item Name", "Item Price", "Count", "Total"};

    public ItemTable(ArrayList<SILine> items) {
        this.items = items;
    }

    public ItemTable() {
        this(new ArrayList<SILine>());
    }

   public int getRowCount(){
       return items.size();
   }
   public int getColumnCount(){
   return columns.length;
   }
   public String getColumnName(int column) {
        return columns[column]; }
    

    
    @Override
     public Object getValueAt(int rowIndex, int columnIndex) {
        SILine line = items.get(rowIndex);
        switch (columnIndex) {
            case 0: return line.getItemName();
            case 1: return line.getItemPrice();
            case 2: return line.getCount();
            case 3: return line.getItemTotal();
            default: return "";
        }
    }

    
   

    

    
}
