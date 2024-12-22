package src.ihm.grilles;

import src.Controleur;
import src.metier.Arete;

import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;

public class GrillesAreteModel extends AbstractTableModel {

    private Controleur ctrl;
    private String[]   tabEntetes;
    ArrayList<Arete>   lstArete;

    public GrillesAreteModel(Controleur ctrl) {
        super();
        this.ctrl = ctrl;
        this.lstArete = this.ctrl.getLstAretes();

        this.tabEntetes = new String[] {"Noeud 1","Noeud 2","Longueur","Type","Sens"};
    }

    public int    getColumnCount()                 { return this.tabEntetes.length;       }
	public int    getRowCount   ()                 { return this.lstArete.size();}
	public String getColumnName (int col)          { return this.tabEntetes[col];         }

    @Override
    public Object getValueAt(int row, int col) {

        switch(col){
            case 0 : return this.lstArete.get(row).getNoeud1().getNom();
            case 1 : return this.lstArete.get(row).getNoeud2().getNom();
            case 2 : return this.lstArete.get(row).getLongueur();
            case 3 : return this.lstArete.get(row).getType().getColor().getRed() + " " + this.lstArete.get(row).getType().getColor().getGreen() + " " + this.lstArete.get(row).getType().getColor().getBlue();
            case 4 : return this.lstArete.get(row).getOrientation();
            default : return null;
        }
        
    }

    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        return getValueAt(0, columnIndex).getClass();
    }

    public void majTable(ArrayList<Arete> lstArete) {   
        this.lstArete = lstArete;
        this.fireTableDataChanged();
    }

    public boolean isCellEditable(int row, int col) {
        return col == 2 || col == 4;
    }

    public void setValueAt(Object value, int row, int col) {
        switch(col){
            case 2 : this.lstArete.get(row).setLongueur((int)value);break;
            case 4 : this.lstArete.get(row).inverserOrientation();break;
            default : break;
        }
        this.fireTableCellUpdated(row, col);
        this.ctrl.majIHM();
        this.ctrl.majNoeud();
        this.ctrl.majArete();
    }
    
    public Color couleurBackground(int rowIndex, int columnIndex)
    {
        String[] str = ((String) this.getValueAt(rowIndex, columnIndex)).split(" ");


        
        return   new Color (Integer.parseInt(str[0]), Integer.parseInt(str[1]), Integer.parseInt(str[2]));
    }   
}
