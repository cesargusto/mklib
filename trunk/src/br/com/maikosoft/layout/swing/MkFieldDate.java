package br.com.maikosoft.layout.swing;

import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.Date;

import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

import br.com.maikosoft.core.MkException;
import br.com.maikosoft.core.MkUtil;



@SuppressWarnings("serial")
public class MkFieldDate extends JFormattedTextField {

    public MkFieldDate() {
        super();
        this.setColumns(8);
        try {
            MaskFormatter maskFormatter = new MaskFormatter("##/##/####");
            maskFormatter.install(this);
            maskFormatter.setPlaceholderCharacter('_');  
        } catch (ParseException e) {
        }
        
        this.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                if ("__/__/____".equals(getText())) {
                    setText(evt.getKeyChar()+ (MkUtil.now().substring(1)));
                    evt.consume();
                    setCaretPosition(1);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
//            	Calendar instance = Calendar.getInstance();
//    			instance.setTime(data);
//    			if ((instance.get(Calendar.YEAR) < 1900) || (instance.get(Calendar.YEAR) > 2099)) {
//    				MacDialog.warm("Ano Data inválida: " + instance.get(Calendar.YEAR));
//    				Calendar now = Calendar.getInstance();
//    				instance.set(Calendar.YEAR, now.get(Calendar.YEAR));
//    				data = instance.getTime();				
//    			}
            }
           
        });
    }
    
    @Override
	public void setValue(Object value) {
		throw new RuntimeException("Utilizar metodo setText");
	}
	
	public Date getDate() throws MkException {
    	if ("__/__/____".equals(getText())) {
    		return null;
    	} else {
    		try {
				return MkUtil.toDate(this.getText());
			} catch (MkException ex) {
				this.grabFocus();
				throw ex;
			}
    	}
    }

}