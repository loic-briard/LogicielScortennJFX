	package Players;
	
	import java.util.*;

import javax.swing.table.*;
	
	import Main.ImageUtility;
	
	public class CustomTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		private List<Object[]> data = new ArrayList<>();
		private String[] columnNames = {
	            "ID", "Sex", "Name", "Surname", "Display name", "Nationality", "Flag", "Player image", "Ranking", "Prize Total", "Height",
	            "Hand", "Age", "Weight", "Birthdate", "Birthplace", "City Residence"};
	    @Override
	    public int getRowCount() {
	        return data.size();
	    }

	    @Override
	    public int getColumnCount() {
	        return columnNames.length;
	    }
	
	    @Override
	    public Object getValueAt(int rowIndex, int columnIndex) {
	        if (columnIndex == 6 || columnIndex == 7) {
	            return data.get(rowIndex)[columnIndex]; // La valeur de la cellule est déjà une instance d'ImageUtility
	        } else {
	            return data.get(rowIndex)[columnIndex];
	        }
	    }

	    @Override
	    public Class<?> getColumnClass(int columnIndex) {
	        if (columnIndex == 6 || columnIndex == 7) {
	            return ImageUtility.class;
	        } else {
	            return Object.class;
	        }
	    }
	    
	    @Override
	    public String getColumnName(int column) {
	        return columnNames[column];
	    }
	    
	    public void addRow(Object[] rowData) {
	        data.add(rowData);
	        int row = data.size() - 1;
	        fireTableRowsInserted(row, row);
	    }
	    
	    public void removeRow(int rowIndex) {
	        data.remove(rowIndex);
	        fireTableRowsDeleted(rowIndex, rowIndex);
	    }
	
	    public void updateRow(int rowIndex, Object[] newData) {
	        if (rowIndex >= 0 && rowIndex < data.size()) {
	            for (int columnIndex = 0; columnIndex < newData.length; columnIndex++) {
	                if (columnIndex == 6 || columnIndex == 7) {
	                    // Si la colonne contient un ImageUtility, assurez-vous que la nouvelle donnée est également un ImageUtility
	                    if (newData[columnIndex] instanceof ImageUtility) {
	                        data.get(rowIndex)[columnIndex] = newData[columnIndex];
	                    }
	                } else {
	                    // Pour les autres colonnes, remplacez simplement les données
	                    data.get(rowIndex)[columnIndex] = newData[columnIndex];
	                }
	            }

	            fireTableRowsUpdated(rowIndex, rowIndex);
	        }
	    }
	    
	    public void clearData() {
	        int rowCount = getRowCount();
	        data.clear();
	        if (rowCount > 0) {
	            fireTableRowsDeleted(0, rowCount - 1);
	        }
	    }
	}
