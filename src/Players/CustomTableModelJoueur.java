/*
 * 
 */
package Players;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;


// TODO: Auto-generated Javadoc
/**
 * The Class CustomTableModelJoueur.
 */
public class CustomTableModelJoueur extends AbstractTableModel {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
    
    /** The column names. */
    private String[] columnNames = { "ID", "Sex", "Name", "Surname", "Display name", "Nationality","Country", "Flag", "Player image", "Ranking", "Prize Total", "Height", "Hand", "Age", "Weight", "Birthdate", "Birthplace", "City Residence", "Seeding"};
    
    /** The data. */
    private List<Object[]> data;
    
    /** The image loader. */
    private ExecutorService imageLoader = Executors.newFixedThreadPool(2);
    
    /** The loading icon. */
    private ImageIcon loadingIcon = new ImageIcon("loading.png");
    
    /** The image cache. */
    private ConcurrentHashMap<String, ImageIcon> imageCache = new ConcurrentHashMap<>();
    
    /** The Constant IMAGE_HEIGHT. */
    private static final int IMAGE_HEIGHT = 60;

    /**
     * Instantiates a new custom table model joueur.
     *
     * @param data the data
     */
    public CustomTableModelJoueur(Object[][] data) {
        this.data = new ArrayList<>();
        for (Object[] row : data) {
            this.data.add(row);
        }
        // Redimensionner l'icône de chargement
        this.loadingIcon = resizeIcon(new ImageIcon("loading.png"));
    }

    /**
     * Gets the row count.
     *
     * @return the row count
     */
    @Override
    public int getRowCount() {
        return data.size();
    }

    /**
     * Gets the column count.
     *
     * @return the column count
     */
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * Gets the value at.
     *
     * @param row the row
     * @param col the col
     * @return the value at
     */
    @Override
    public Object getValueAt(int row, int col) {
        Object value = data.get(row)[col];
        if ((col == 7 || col == 8) && value instanceof String) {
            String imagePath = (String) value;
            if (imageCache.containsKey(imagePath)) {
                return imageCache.get(imagePath);
            } else if (new File(imagePath).exists()) {
                return loadingIcon;
            }
        }
        return value;
    }

    /**
     * Sets the value at.
     *
     * @param value the value
     * @param row the row
     * @param col the col
     */
    @Override
    public void setValueAt(Object value, int row, int col) {
        data.get(row)[col] = value;
        fireTableCellUpdated(row, col);
    }

    /**
     * Gets the column name.
     *
     * @param col the col
     * @return the column name
     */
    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    /**
     * Checks if is cell editable.
     *
     * @param row the row
     * @param col the col
     * @return true, if is cell editable
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        return true;
    }

    /**
     * Gets the column class.
     *
     * @param columnIndex the column index
     * @return the column class
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 7 || columnIndex == 8) {
            return ImageIcon.class;
        }
        return super.getColumnClass(columnIndex);
    }

    /**
     * Load images.
     */
    public void loadImages() {
        imageLoader.shutdownNow(); // Arrête les tâches de chargement précédentes
        imageLoader = Executors.newFixedThreadPool(2); // Crée un nouveau pool de threads
        for (int row = 0; row < getRowCount(); row++) {
            final int finalRow = row;
            imageLoader.submit(() -> {
                loadImageForCell(finalRow, 7); // Flag
                loadImageForCell(finalRow, 8); // Player image
            });
        }
    }

    /**
     * Load image for cell.
     *
     * @param row the row
     * @param col the col
     */
    private void loadImageForCell(int row, int col) {
        Object value = data.get(row)[col];
        if (value instanceof String) {
            String imagePath = (String) value;
            if (!imageCache.containsKey(imagePath) && new File(imagePath).exists()) {
                ImageIcon originalIcon = new ImageIcon(imagePath);
                Image originalImage = originalIcon.getImage();
                
                // Calcul de la nouvelle largeur en maintenant les proportions
                int originalWidth = originalIcon.getIconWidth();
                int originalHeight = originalIcon.getIconHeight();
                int newWidth = (int) ((double) IMAGE_HEIGHT / originalHeight * originalWidth);
                
                Image scaledImage = originalImage.getScaledInstance(newWidth, IMAGE_HEIGHT, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                scaledIcon.setDescription(imagePath);
                imageCache.put(imagePath, scaledIcon);
                setValueAt(imagePath, row, col);
                fireTableCellUpdated(row, col);
            }
        }
    }
    
    /**
     * Resize icon.
     *
     * @param icon the icon
     * @return the image icon
     */
    private ImageIcon resizeIcon(ImageIcon icon) {
        Image img = icon.getImage();
        int originalWidth = icon.getIconWidth();
        int originalHeight = icon.getIconHeight();
        int newWidth = (int) ((double) IMAGE_HEIGHT / originalHeight * originalWidth);
        Image resizedImage = img.getScaledInstance(newWidth, IMAGE_HEIGHT, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    /**
     * Adds the row.
     *
     * @param rowData the row data
     */
    public void addRow(Object[] rowData) {
        data.add(rowData);
        fireTableRowsInserted(data.size() - 1, data.size() - 1);
    }

    /**
     * Removes the row.
     *
     * @param row the row
     */
    public void removeRow(int row) {
        data.remove(row);
        fireTableRowsDeleted(row, row);
    }

    /**
     * Update row.
     *
     * @param row the row
     * @param rowData the row data
     */
    public void updateRow(int row, Object[] rowData) {
        data.set(row, rowData);
        fireTableRowsUpdated(row, row);
    }
    
    /**
     * Clear data.
     */
    public void clearData() {
        int oldSize = data.size();
        data.clear();
        imageCache.clear();
        if (oldSize > 0) {
            fireTableRowsDeleted(0, oldSize - 1);
        }
    }
    
    /**
     * Sets the new data.
     *
     * @param newData the new new data
     */
    public void setNewData(Object[][] newData) {
        clearData();
        for (Object[] row : newData) {
            data.add(row);
        }
        fireTableRowsInserted(0, data.size() - 1);
    }
}