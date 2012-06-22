package br.com.maikosoft.layout.swing;

import java.util.List;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public abstract class MkTableModel<T> extends AbstractTableModel {

	    protected List<T> rows;
	    protected String[] columnName;

	    public MkTableModel(List<T> rows, String... columnName) {
	        this.rows = rows;
	        this.columnName = columnName;
	    }
	    
	    protected abstract Object getRow(T bean, int rowIndex, int columnIndex);

	    // a table invoca este método para descobrir quantas colunas ela precisa apresentar
	    public int getColumnCount() {
	    	if (rows.size() ==0 ) {
	    		return 1;
	    	} else {
	    		return columnName.length;
	    	}
	    }

	    // a table invoca este método para obter o nome de cada coluna
	    @Override
	    public String getColumnName(int index) {
	    	if (getRowCount() ==0 ) {
	    		return "Nenhum Registro";
	    	} else {
	    		return columnName[index];
	    	}
	    }

	    // a table invoca este método para descobrir quantas linhas ela tem
	    public int getRowCount() {
	        return (rows == null ? 0 : rows.size());
	    }

	    // a table invoca este método para renderizar a célula especificado pelos parâmetros
	    public Object getValueAt(int rowIndex, int columnIndex) {
	        if ((rowIndex >= 0) && (rowIndex < rows.size())) {
	            return getRow(rows.get(rowIndex), rowIndex, columnIndex);
	        }
	        return "";
	    }

	    @Override
	    public Class<?> getColumnClass(int index) {
	        Object valueAt = getValueAt(0, index);
	        return (valueAt == null ? String.class.getClass() : valueAt.getClass());
	    }

		public T getBean(int index) {
			return rows.get(index);
		}

	
}
