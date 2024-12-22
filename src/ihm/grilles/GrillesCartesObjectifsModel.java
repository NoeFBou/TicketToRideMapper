package src.ihm.grilles;

import src.Controleur;
import src.metier.CarteObjectif;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class GrillesCartesObjectifsModel extends AbstractTableModel {

    private Controleur ctrl;
    private String[]   tabEntetes;
    ArrayList<CarteObjectif> lstCarteObjectifs;

    public GrillesCartesObjectifsModel(Controleur ctrl) {
        this.ctrl = ctrl;

        lstCarteObjectifs = this.ctrl.getLstObjectifs();

        this.tabEntetes = new String[] {"Noeud 1","Noeud 2","Points"};
    }

    public int    getColumnCount()                 { return this.tabEntetes.length;       }
	public int    getRowCount   ()                 { return this.lstCarteObjectifs.size();}
	public String getColumnName (int col)          { return this.tabEntetes[col];         }

    @Override
    public Object getValueAt(int row, int col) {

        switch(col){
            case 0 : return this.lstCarteObjectifs.get(row).getNoeud1().getNom();
            case 1 : return this.lstCarteObjectifs.get(row).getNoeud2().getNom();
            case 2 : return this.lstCarteObjectifs.get(row).getPoints();
            default : return null;
        }
        
    }
    public Class<?> getColumnClass(int c) { return getValueAt(0, c).getClass();}

    public void majTable(ArrayList<CarteObjectif> lstCarteObjectifs) {   
        this.lstCarteObjectifs = lstCarteObjectifs;
        this.fireTableDataChanged();
    }

    public boolean isCellEditable(int row, int col) {
        return col == 2;
    }

    public void setValueAt(Object value, int row, int col) {
        switch(col){
            case 2 : this.lstCarteObjectifs.get(row).setPoints((int)value);break;
            default : break;
        }
        this.fireTableCellUpdated(row, col);
    }
    
    
}
