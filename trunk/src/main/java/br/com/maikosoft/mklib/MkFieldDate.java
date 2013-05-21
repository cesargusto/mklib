package br.com.maikosoft.mklib;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Date;

import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;

import net.sourceforge.jcalendarbutton.JCalendarButton;
import br.com.maikosoft.core.MkException;
import br.com.maikosoft.util.MkUtil;



@SuppressWarnings("serial")
public class MkFieldDate extends JPanel {
	
	private JFormattedTextField field;
	private JCalendarButton buttonCalendario;

    public MkFieldDate() {
        
    	this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
    	
    	this.field = new JFormattedTextField();
    	
    	this.buttonCalendario = new JCalendarButton();
    	this.buttonCalendario.setFocusable(false);
    	this.buttonCalendario.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                if (evt.getNewValue() instanceof Date) {
                	if (field.isEditable()) {
                		Date value = (Date)evt.getNewValue();
                		field.setText(MkUtil.toString(value));
                	}
                }
            }
        });
    	this.buttonCalendario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == buttonCalendario)
		        	{
					try {
						buttonCalendario.setTargetDate(getDate());
					} catch (MkException ex) {
						
					}
		        }
			}
		});
    	
    	
        this.add(field);
        this.add(buttonCalendario);
    	
        try {
        	MaskFormatter maskFormatter = new MaskFormatter("##/##/####");
        	maskFormatter.install(field);
        	maskFormatter.setPlaceholderCharacter('_');  
        } catch (ParseException e) {  }
        
        field.setColumns(8);
        field.setText(null); // para começar a formatacao certa;
        field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
            	if (field.isEditable()) {
            		if ("__/__/____".equals(field.getText())) {
            			field.setText(evt.getKeyChar()+ (MkUtil.now().substring(1)));
            			evt.consume();
            			field.setCaretPosition(1);
            		}
            	}
            }
        });
        
        
    }
	
	public Date getDate() throws MkException {
    	if ("__/__/____".equals(field.getText())) {
    		return null;
    	} else {
    		try {
				return MkUtil.toDate(field.getText());
			} catch (MkException ex) {
				MkDialog.info("Data inválida", field);
				throw ex;
			}
    	}
    }
	
	public void setDate(Date value) {
		field.setText(MkUtil.toString(value));
	}

	public void setEditable(boolean editable) {
		field.setEditable(editable);
	}

}