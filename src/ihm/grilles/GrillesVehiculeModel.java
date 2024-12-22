package src.ihm.grilles;

import src.Controleur;
import src.metier.Type;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;

public class GrillesVehiculeModel extends AbstractTableModel
{
    private Controleur ctrl;

	private String[]   tabEntetes;
    private Object[][] tabVehicules;

    public GrillesVehiculeModel(Controleur ctrl)
    {
        super();
        this.ctrl = ctrl;
        this.tabVehicules = new Object[this.ctrl.getLstType().size()][2];

        this.tabEntetes = new String[] { "nombre Carte", "Couleur"};


        majTable(this.ctrl.getLstType());
        Font font = new Font("Arial", Font.PLAIN, 15);
        UIManager.put("Table.font", font);
    }

    public void majTable(ArrayList<Type> lstType) {
        
        if (lstType.size() > 0) {
            
            this.tabVehicules = new Object[lstType.size()][2];

            for (int i = 0; i < lstType.size(); i++)
            {
                this.tabVehicules[i][0] = 0;
                this.tabVehicules[i][1] = null;
            }

            for (int i = 0; i < lstType.size(); i++)
            {
                this.tabVehicules[i][0] = 12;
                this.tabVehicules[i][1] = this.ctrl.getLstType().get(i).getColor();
            }
            
        }

        this.fireTableDataChanged();
    }

    public int getRowCount() 
    {
        return this.tabVehicules.length;
    }

    public int getColumnCount() 
    {
        return this.tabEntetes.length;
    }

    public String getColumnName(int columnIndex) 
    {
        return this.tabEntetes[columnIndex];
    }
    
    public Color couleurBackground(int rowIndex, int columnIndex) 
    {
        return (Color)  this.tabVehicules[rowIndex][columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) 
    {
        switch(columnIndex){
            case 0:
                return (int) this.tabVehicules[rowIndex][columnIndex];
            case 1:
                return (Color) this.tabVehicules[rowIndex][columnIndex];
            default:
                return null; //Ne devrait jamais arriver
        }
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true; //Toutes les cellules éditables
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        //la valeur de chaque cellule doit etre un nombre sauf 0
        if(aValue instanceof Integer && (Integer)aValue > 0){
            this.tabVehicules[rowIndex][columnIndex] = aValue;
            fireTableCellUpdated(rowIndex, columnIndex);
        }

        //la valeur de chaque cellule ne peut pas être null, egal a zero ou vide
        if(aValue != null && !aValue.equals("") && !aValue.equals(0)){
            this.tabVehicules[rowIndex][columnIndex] = aValue;
            fireTableCellUpdated(rowIndex, columnIndex);
        }

    }
    
}