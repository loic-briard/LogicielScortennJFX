/*
 * 
 */
package Flags;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.*;

// TODO: Auto-generated Javadoc
/**
 * The Class ImageRendererFlags.
 */
public class ImageRendererFlags extends DefaultTableCellRenderer {
    
    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The Constant IMAGE_HEIGHT. */
	private static final int IMAGE_HEIGHT = 60;

    /**
     * Gets the table cell renderer component.
     *
     * @param table the table
     * @param value the value
     * @param isSelected the is selected
     * @param hasFocus the has focus
     * @param row the row
     * @param column the column
     * @return the table cell renderer component
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof ImageIcon) {
            ImageIcon icon = (ImageIcon) value;
            setIcon(icon);
            setText("");
            setVerticalAlignment(SwingConstants.CENTER);
            setHorizontalAlignment(SwingConstants.CENTER);
           
            // Définir une taille préférée pour la cellule basée sur la hauteur de l'image
            int width = icon.getIconWidth();
            setPreferredSize(new Dimension(width, IMAGE_HEIGHT));
        } else {
            setIcon(null);
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
        return this;
    }
}