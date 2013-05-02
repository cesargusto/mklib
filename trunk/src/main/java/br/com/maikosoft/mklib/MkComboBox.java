package br.com.maikosoft.mklib;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

import br.com.maikosoft.core.MkRun;

/**
 *
 * @author michael
 */
@SuppressWarnings("serial")
public class MkComboBox<T> extends JComboBox {
	
	private List<T> list;
	private MkRun onChangeAdapter;
	
	public MkComboBox() {
		super();
		setPrototypeDisplayValue("XX");
		this.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_F5) {
					refreshList();
				}
			}
		});
		this.addFocusListener(new FocusListener() {	
			public void focusLost(FocusEvent arg0) {
				
			}
			public void focusGained(FocusEvent arg0) {
				setPopupVisible(true);
			}
		});
		
		this.setKeySelectionManager(new MyKeySelectionManager());
		this.addActionListener (new ActionListener () {
				public void actionPerformed(ActionEvent e) {
					if (onChangeAdapter!=null) {
						onChangeAdapter.execute();						
					}
				}
		});
		ComboBoxRenderer renderer = new ComboBoxRenderer();
		this.setRenderer(renderer);

	}
	
	public void onChange(MkRun adapter) {
    	this.onChangeAdapter = adapter; 
	}
	
	public void setList(List<T> list) {
		this.list = list;
//		SwingUtilities.invokeLater(new Runnable() {
//			public void run() {
				refreshList();
//			}
//		});
	}
	
	public void refreshList() {
		this.removeAllItems();
		if (list != null) {
			for (T item : list) {
				this.addItem(item);
			}
		}
	}
	
	private class MyKeySelectionManager implements JComboBox.KeySelectionManager {
        long lastKeyTime = 0;
        String pattern = "";
   
        public int selectionForKey(char aKey, ComboBoxModel model) {
        	
            long curTime = System.currentTimeMillis();
   
            if (curTime - lastKeyTime < 1000) {
                pattern += ("" + aKey).toLowerCase();
            } else {
                pattern = ("" + aKey).toLowerCase();
            }
   
            lastKeyTime = curTime;
            
            String regex = pattern + ".*";
            Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            
            for (int i=0; i<model.getSize() ; i++) {
              if (model.getElementAt(i) != null) {
                  String s = model.getElementAt(i).toString().toLowerCase();
                  if ((p.matcher(s).matches())) {
                      return i;
                  }
              }
            }
            
            regex = ".*"+ pattern + ".*";
            p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            
            for (int i=0; i<model.getSize() ; i++) {
              if (model.getElementAt(i) != null) {
                  String s = model.getElementAt(i).toString().toLowerCase();
                  if ((p.matcher(s).matches())) {
                      return i;
                  }
              }
            }
            
          return -1;
        }
	}
	
	private class ComboBoxRenderer extends JTextField implements ListCellRenderer {
		
		public ComboBoxRenderer() {
			setPrototypeDisplayValue("XXXXX");
		}

		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			
			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}

			if (value == null) {
				setText("");
			} else {
				setText(value.toString());
				setToolTipText(getText());
			}
			return this;
		}
	}

	@SuppressWarnings("unchecked")
	public T getSelected() {
		return (T) getSelectedItem();
	}

	public void setSelected(T selected) {
		this.setSelectedIndex(list.indexOf(selected));
	}
	
}
