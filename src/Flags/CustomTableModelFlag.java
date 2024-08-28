package Flags;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;


public class CustomTableModelFlag extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
    private String[] columnNames = { "Acronym", "Flag" };
    private List<Object[]> data;
    private ExecutorService imageLoader = Executors.newFixedThreadPool(2);
    private ImageIcon loadingIcon = new ImageIcon("loading.png");
    private ConcurrentHashMap<String, ImageIcon> imageCache = new ConcurrentHashMap<>();
    private static final int IMAGE_HEIGHT = 60;

    public CustomTableModelFlag(Object[][] data) {
        this.data = new ArrayList<>();
        for (Object[] row : data) {
            this.data.add(row);
        }
        // Redimensionner l'icône de chargement
        this.loadingIcon = resizeIcon(new ImageIcon("loading.png"));
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int row, int col) {
        Object value = data.get(row)[col];
        if (col == 1 && value instanceof String) {
            String imagePath = (String) value;
            if (imageCache.containsKey(imagePath)) {
                return imageCache.get(imagePath);
            } else if (new File(imagePath).exists()) {
                return loadingIcon;
            }
        }
        return value;
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        data.get(row)[col] = value;
        fireTableCellUpdated(row, col);
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return true;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 1 ) {
            return ImageIcon.class;
        }
        return super.getColumnClass(columnIndex);
    }

    public void loadImages() {
        imageLoader.shutdownNow(); // Arrête les tâches de chargement précédentes
        imageLoader = Executors.newFixedThreadPool(1); // Crée un nouveau pool de threads
        for (int row = 0; row < getRowCount(); row++) {
            final int finalRow = row;
            imageLoader.submit(() -> {
                loadImageForCell(finalRow, 1); // Flag
            });
        }
    }

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
    private ImageIcon resizeIcon(ImageIcon icon) {
        Image img = icon.getImage();
        int originalWidth = icon.getIconWidth();
        int originalHeight = icon.getIconHeight();
        int newWidth = (int) ((double) IMAGE_HEIGHT / originalHeight * originalWidth);
        Image resizedImage = img.getScaledInstance(newWidth, IMAGE_HEIGHT, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    public void addRow(Object[] rowData) {
        data.add(rowData);
        fireTableRowsInserted(data.size() - 1, data.size() - 1);
    }

    public void removeRow(int row) {
        data.remove(row);
        fireTableRowsDeleted(row, row);
    }

    public void updateRow(int row, Object[] rowData) {
        data.set(row, rowData);
        fireTableRowsUpdated(row, row);
    }
    public void clearData() {
        int oldSize = data.size();
        data.clear();
        imageCache.clear();
        if (oldSize > 0) {
            fireTableRowsDeleted(0, oldSize - 1);
        }
    }
    public void setNewData(Object[][] newData) {
        clearData();
        for (Object[] row : newData) {
            data.add(row);
        }
        fireTableRowsInserted(0, data.size() - 1);
    }
}