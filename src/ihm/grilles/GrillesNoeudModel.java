package src.ihm.grilles;

import src.Controleur;
import src.metier.Noeud;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class GrillesNoeudModel extends AbstractTableModel
{
    private Controleur ctrl;

	private String[] tabEntetes;
    ArrayList<Noeud> lstNoeuds;

    public GrillesNoeudModel(Controleur ctrl)
    {
        this.ctrl = ctrl;
		lstNoeuds = this.ctrl.getLstNoeuds();
        
        this.tabEntetes = new String[] { "Nom", "X", "Y", "Nom X", "Nom Y"};
    }

    
    public int    getColumnCount()                 { return this.tabEntetes.length;      }
	public int    getRowCount   ()                 { return this.lstNoeuds.size();       }
	public String getColumnName (int col)          { return this.tabEntetes[col];        }
	

    public Object getValueAt    (int row, int col)
    { 
        switch(col)
        {
            case 0: return this.lstNoeuds.get(row).getNom();
            case 1: return (int) (this.lstNoeuds.get(row).getX());
            case 2: return (int) (this.lstNoeuds.get(row).getY());
            case 3: return this.lstNoeuds.get(row).getNomX();
            case 4: return this.lstNoeuds.get(row).getNomY();
            default: return null;
        }

    }

	public Class<?>  getColumnClass(int c) { return getValueAt(0, c).getClass(); }

    public void majTable(ArrayList<Noeud> lstNoeuds)
    {   
        this.lstNoeuds = lstNoeuds;
        this.fireTableDataChanged();
    }

    public boolean isCellEditable(int row, int col)
    {
        return true;
    }

    public void setValueAt(Object value, int row, int col)
    {
        if (col < 0 || col > 4) return;

        else {
            switch(col) {
                case 0 : this.lstNoeuds.get(row).setNom((String)value);break;
                case 1 : this.lstNoeuds.get(row).setX((int)value);break;
                case 2 : this.lstNoeuds.get(row).setY((int)value);break;
                case 3 : this.lstNoeuds.get(row).setNomX((int)value);break;
                case 4 : this.lstNoeuds.get(row).setNomY((int)value);break;
            }
            this.fireTableCellUpdated(row, col);
            this.ctrl.majIHM();
            this.ctrl.majNoeud();
        }
        
    }

    
}