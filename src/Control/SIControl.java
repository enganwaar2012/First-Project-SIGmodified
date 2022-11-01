/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.HeaderTableModel;
import Model.ItemTable;
import Model.SIHeader;
import Model.SILine;
import View.SIFrame;
import View.SIHeaderDialog;
import View.SILineDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author engan
 */
public class SIControl implements ActionListener,ListSelectionListener{
    private SIFrame frame;
    private SIHeaderDialog headerdialoge;
    private SILineDialog linedialoge;
    
  
    
    public SIControl(SIFrame frame) {
        this.frame = frame;
    }
    

    

    @Override
    public void actionPerformed(ActionEvent e) {
        String action =e.getActionCommand();
        switch(action){
            case "Create New Invoice":
                newInvoice();
                break;

            case "Delete Invoice":
                deleteInvoice();
                break;

            case "Create Item":
                createItem();
                break;

            case "Delete Item":
                deleteItem();
                break;

            case "load File":
                loadFile(null, null);
                break;

            case "Save File":
                saveData();
            case"Create New InvoiceOk" :
                ctreateNewInvoiceOK();
            case"Create New InvoiceCancel":
                    createNewInvoiceCancel();
            case"Create Item Ok" :
                createItemOK();
            case"Create Item Cancel":
                    createItemCancel();        
                    
                    
                break;
                    
    }
     
}

   
    
    
    private void saveData() {
     JFileChooser fc = new JFileChooser();
        File hFile = null;
        File lFile = null;
        JOptionPane.showMessageDialog(frame, "Select Header File", "Header File", JOptionPane.QUESTION_MESSAGE);
        int result = fc.showSaveDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            hFile = fc.getSelectedFile();
            JOptionPane.showMessageDialog(frame, "Select Line File", "Line File", JOptionPane.QUESTION_MESSAGE);
            result = fc.showSaveDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                lFile = fc.getSelectedFile();
            }
        }
        
        if (hFile != null && lFile != null) {
            String hData = "";
            String lData = "";
                for (SIHeader header : frame.getInvoices()) {
                hData += header.getAsCSV();
                hData += "\n";
                
                for (SILine item : header.getLine()) {
                    lData += item.getAsCSV();
                    lData += "\n";
                }
            }
            System.out.println("Check");
            try {
                FileWriter hfw = new FileWriter(hFile);
                FileWriter lfw = new FileWriter(lFile);
                hfw.write(hData);
                lfw.write(lData);
                hfw.flush();
                lfw.flush();
                hfw.close();
                lfw.close();  
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error while writing files: \n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
    }
    }

    public void loadFile(String hPath,String lPath) {
        File hFile = null;
        File lFile = null;
        if (hPath == null && lPath == null) {
            JFileChooser fc = new JFileChooser();
            JOptionPane.showMessageDialog(frame, "Load Header file!", "Header", JOptionPane.INFORMATION_MESSAGE);
            int result = fc.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                hFile = fc.getSelectedFile();
                JOptionPane.showMessageDialog(frame, "Load Line file!", "Line", JOptionPane.INFORMATION_MESSAGE);
                result = fc.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    lFile = fc.getSelectedFile();
                    
                }
            }
        } else {
            hFile = new File(hPath);
            lFile = new File(lPath);
        }

        if (hFile != null && lFile != null) {
            try {
                List<String> hLines = readFile(hFile);
               
                List<String> lLines = readFile(lFile);
                   frame.getInvoices().clear();
                   for (String hLine : hLines) {
                    String[] segments = hLine.split(",");
                    if(segments.length <3)
                    {JOptionPane.showMessageDialog
                    (frame, "Error in header format: " + hLine, "Error", JOptionPane.ERROR_MESSAGE);
                     }
                     int num =Integer.parseInt(segments[0]);
                    Date date = new Date();
                    try {
                        date = SIFrame.sdf.parse(segments[1]);
                    } catch (ParseException ex) {
                    JOptionPane.showMessageDialog
                    (frame, "Error in date format: " +hLine, "Error", JOptionPane.ERROR_MESSAGE);
                    
                    }
                        
                    String name = segments[2];
                    SIHeader inv;
                    inv = new SIHeader(num, name, date);
                    frame.getInvoices().add(inv);
                    
                }
                frame.setHeadertablemodel(new HeaderTableModel(frame.getInvoices()));
                frame.getlinesTable().clearSelection();
                for(String line:lLines){
                String[] frames = line.split(",");
                if(frames.length <4)
                    {JOptionPane.showMessageDialog
                    (frame, "Error in lines format: " + line, "Error", JOptionPane.ERROR_MESSAGE);
                    
                     }
                int No = Integer.parseInt(frames[0]);
                String itemName = frames[1];
                int itemPrice=Integer.parseInt(frames[2]);
                int count = Integer.parseInt(frames[3]);
                SIHeader inv=frame.getInvoiceByNum(No);
                SILine item =new SILine(itemName,itemPrice,count,inv);
                inv.getLine().add(item);
                }
               
                
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error while reading data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }    
}
        
    
    private List<String> readFile(File f) throws IOException {
        
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        List<String> lines = new ArrayList<>();
        String line = null;

        while ((line = br.readLine()) != null) {
            lines.add(line);
        }

        return lines;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedRow=frame.getInvoicesTable().getSelectedRow();
         System.out.println("select listener"+selectedRow);
        
         if(selectedRow!=-1){
            SIHeader selectedInvoice = frame.getInvoices().get(selectedRow);
            frame.getCustomerName().setText(""+selectedInvoice.getCustomerName());
            frame.getInvoiceTotal().setText(""+selectedInvoice.getInvoiceTotal());
            frame.getInvoiceDate().setText(frame.sdf.format(selectedInvoice.getDate()));
            frame.getInvoiceNumber().setText(""+selectedInvoice.getInvoiceNumber());
            frame.setItemtable(new ItemTable(selectedInvoice.getLine()));
           
         }else{
         
            frame.getCustomerName().setText("");
            frame.getInvoiceTotal().setText("");
            frame.getInvoiceDate().setText("");
            frame.getInvoiceNumber().setText("");
            frame.setItemtable(new ItemTable());
         }
        
       
    }

    private void newInvoice() {
    headerdialoge=new SIHeaderDialog(frame);
    headerdialoge.setVisible(true);
    
    }
    
    private void deleteInvoice() {
    int selectedRow=frame.getInvoicesTable().getSelectedRow();
    if (selectedRow!= -1){
    frame.getInvoices().remove(selectedRow);
    frame.getHeadertablemodel().fireTableDataChanged();
    }
  else{
        JOptionPane.showMessageDialog(frame, "Please select invoice", "", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    

    private void ctreateNewInvoiceOK() {
        try{
        String dateStr = headerdialoge.getInvDateField().getText();
            String CustomerName = headerdialoge.getCustNameField().getText();
            Date date = frame.sdf.parse(dateStr);
            int InvoiceNumber = frame.getNextInvoiceNum();
            SIHeader inv = new SIHeader(InvoiceNumber,CustomerName, date);
            frame.getInvoices().add(inv);
            frame.getHeadertablemodel().fireTableDataChanged();
            createNewInvoiceCancel();}
        catch(ParseException ex){JOptionPane.showMessageDialog(frame, "Error in Date format", "Error", JOptionPane.ERROR_MESSAGE);}
    }
    
    
    private void createNewInvoiceCancel() {
        headerdialoge.setVisible(false);
        headerdialoge.dispose();
        headerdialoge = null;
    }

    
    
    private void createItem() {
        int selectedInvoice = frame.getInvoicesTable().getSelectedRow();
        if (selectedInvoice == -1) {
            JOptionPane.showMessageDialog(frame, " select Invoice", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
    
    linedialoge=new SILineDialog(frame);
    linedialoge.setVisible(true);
    }
    }
    private void deleteItem() {
        int selectedInvoice = frame.getInvoicesTable().getSelectedRow();
        int selectedItem = frame.getlinesTable().getSelectedRow();
        if (selectedInvoice != -1 && selectedItem != -1) {
            SIHeader invoice = frame.getInvoices().get(selectedInvoice);
            invoice.getLine().remove(selectedItem);
            frame.getitemTable().fireTableDataChanged();
            frame.getHeadertablemodel().fireTableDataChanged();
            frame.getInvoicesTable().setRowSelectionInterval(selectedInvoice, selectedInvoice);
        }
        else{
        JOptionPane.showMessageDialog(frame, "Please select item", "", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void createItemOK() {
        String ItemName = linedialoge.getItemNameField().getText();
        int Count = Integer.parseInt(linedialoge.getItemCountField().getText());
        int ItemPrice = Integer.parseInt(linedialoge.getItemPriceField().getText());
        int selectedInvIndex = frame.getInvoicesTable().getSelectedRow();
        SIHeader inv = frame.getInvoices().get(selectedInvIndex);
        
        new SILine(ItemName,  ItemPrice,  Count, inv);
        frame.getHeadertablemodel().fireTableDataChanged();
        frame.getInvoicesTable().setRowSelectionInterval(selectedInvIndex, selectedInvIndex);
        createItemCancel();
    }

    private void createItemCancel() {
        linedialoge.setVisible(false);
        linedialoge.dispose();
        linedialoge = null;
    }
    

}



    
    

    
        
    


