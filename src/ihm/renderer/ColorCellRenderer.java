package src.ihm.renderer;

import src.ihm.grilles.GrillesVehiculeModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;



public class ColorCellRenderer extends DefaultTableCellRenderer {

    public ColorCellRenderer()
    {
        this.setOpaque(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        GrillesVehiculeModel model = (GrillesVehiculeModel) table.getModel();

        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        
       Color tabCoul = model.couleurBackground(row, column);
        
       c.setBackground(tabCoul);
       ((JLabel) c).setText("");

        return c;
    }
}