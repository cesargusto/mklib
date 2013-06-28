package br.com.maikosoft.mklib;

import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import br.com.maikosoft.core.MkRun;

@SuppressWarnings("serial")
public class MkTable<T> extends JTable {
	
	// melhorar table model para pegar item selecionado
	
	private MkRun adapterDoubleClickOrEnter;
//	private List<T> list;
	private JScrollPane scrollPane;
	
	public MkTable() {
		super();
		this.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				if (evt.getClickCount() == 2) {
					if (adapterDoubleClickOrEnter != null) {
						adapterDoubleClickOrEnter.execute();
					}
				}
			}
		});
		this.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					if (adapterDoubleClickOrEnter != null) {
						adapterDoubleClickOrEnter.execute();
					}
				}
			}
		});
		this.setAutoCreateRowSorter(true);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//		this.listColumn = new LinkedList<MacColumn<T>>();
	}
	
	public JScrollPane getJScrollPane() {
		if (scrollPane == null) {
			this.scrollPane = new JScrollPane();
			this.scrollPane.setViewportView(this);
		}
		return scrollPane;
	}

	public void onDoubleClickOrEnter(MkRun adapterDoubleClick) {
		this.adapterDoubleClickOrEnter = adapterDoubleClick;
	}

	@Override
	public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
		AWTEvent currentEvent = EventQueue.getCurrentEvent();
		if (currentEvent instanceof KeyEvent) {
			KeyEvent ke = (KeyEvent) currentEvent;
			if (ke.getSource() != this)
				return;
			// focus change with keyboard
			if (this.getColumnCount() == (columnIndex + 1)
					&& KeyStroke.getKeyStrokeForEvent(ke).equals(
							KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0))) {
				KeyboardFocusManager manager = KeyboardFocusManager
						.getCurrentKeyboardFocusManager();
				manager.focusNextComponent();
				rowIndex = rowIndex - 1;
			}
		}

		super.changeSelection(rowIndex, columnIndex, toggle, extend);
	}

	public void setFocus() {
		super.requestFocusInWindow();
		if (this.getRowCount() > 0) {
			this.setRowSelectionInterval(0, 0);
		}
	}
	
//	public void addColumn(MacColumn<T>...column) {
//		for (MacColumn<T> macColumn : column) {
//			this.listColumn.add(macColumn);
//		}
//	}
	
//	public void refreshTable(final List<T> list) {
//		// vai pelar as column adicionadas
//		// criar o model
//		if (list==null) {
//			this.setModel(new DefaultTableModel());
//		} else {
//			AbstractTableModel model = new AbstractTableModel() {
//				
//				@Override
//				public Object getValueAt(int rowIndex, int columnIndex) {
//					return listColumn.get(columnIndex).getValue(list.get(rowIndex));
//				}
//				
//				@Override
//				public int getRowCount() {
//					return (list == null ? 0 : list.size());
//				}
//				
//				@Override
//				public int getColumnCount() {
//					return listColumn.size();
//				}
//				
//				@Override
//			    public String getColumnName(int index) {
//			        return listColumn.get(index).getName();
//			    }
//
//				@Override
//				public Class<?> getColumnClass(int columnIndex) {
//					return listColumn.get(columnIndex).getClazz();
//				}				
//			};
//			
//			this.setModel(model);
//		}
//		
//		// e colocar os prefsize
//	}
	
	public void setFocusEndScrollTable() {
        if (this.getRowCount() != -1) {
            this.setRowSelectionInterval(this.getRowCount() - 1, this.getRowCount() - 1);
            this.scrollRectToVisible(this.getCellRect(this.getRowCount() - 1, 1, false));
        }
    }

	public T getSeleted(boolean isMostraAviso) {
		if (this.getSelectedRow() == -1) {
			if (this.getRowCount() == 0) {
				if (isMostraAviso) {
					MkDialog.info("Seleciona um item na tabela.", this.getTableHeader());
				}
				return null;				
			} else {
				this.setRowSelectionInterval(0, 0);
			}
		}
		int index = this.convertRowIndexToModel(this.getSelectedRow());
		@SuppressWarnings("unchecked")
		MkTableModel<T> model = (MkTableModel<T>) this.getModel();
		return model.getBean(index);
	}

	public static TableCellRenderer getCenterRenderer() {
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment( JLabel.CENTER );
		return renderer;
	}
	
	public static TableCellRenderer getRightRenderer() {
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment( JLabel.RIGHT );
		return renderer;
	}
	
}
