package src.ihm.renderer;

import src.ihm.grilles.GrillesAreteModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class ColorCellRendererArete extends DefaultTableCellRenderer {

    public ColorCellRendererArete()
    {
        this.setOpaque(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        GrillesAreteModel model = (GrillesAreteModel) table.getModel();

        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        
       Color tabCoul = model.couleurBackground(row, column);
        
       
       c.setBackground(tabCoul);
       //cacher le texte
         ((JLabel) c).setText("");
         

        return c;
    }
}