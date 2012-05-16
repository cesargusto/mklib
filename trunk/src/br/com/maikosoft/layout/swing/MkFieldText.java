package br.com.maikosoft.layout.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

/**
 *
 * @author michael
 */
@SuppressWarnings("serial")
public class MkFieldText extends JTextField {

     private int maxLength=0;
     private MkRun adapterOnChange;
     private String lastValue;
     private MkRun adapterOnEnter;

     public MkFieldText() {
       this(255);
       this.setColumns(3);
    }
   
     public MkFieldText(int maxLength){  
         super();  
         if (maxLength>0) {
            this.maxLength = maxLength;  
         }
         this.addKeyListener(new LimitedKeyListener());  
     }

     public void setMaxLength(int maxLength){  
         this.maxLength= maxLength;  
         update();  
     }  
       
     private void update(){ 
         if (getText().length()>maxLength){  
             setText(getText().substring(0,maxLength));  
             setCaretPosition(maxLength);  
         }  
     }  
       
    @Override
     public void setText(String arg0){
    	 lastValue = getText();
         super.setText(arg0);  
         update();
         if (adapterOnChange!=null) {
        	 adapterOnChange.execute();
         }
     }  
       
    @Override
     public void paste(){  
        super.paste();  
         update();  
         if (adapterOnChange!=null) {
        	 adapterOnChange.execute();
         }
     }

    public String getLastValue() {
		return lastValue;
	}

	//Classes Internas  
     private class LimitedKeyListener extends KeyAdapter{  
         private boolean backspace= false;  
           
         @Override
        public void keyPressed(KeyEvent e){  
             backspace=(e.getKeyCode()==8); 
             if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            	if (adapterOnEnter!=null) {
            		adapterOnEnter.execute();
            	}
             }
         }  
           
         @Override
         public void keyTyped(KeyEvent e){  
             if (    !backspace  &&  
                     getText().length()>maxLength-1){  
                 e.consume();  
             }  
         }  
     }
     
     public void onChange(MkRun adapter) {
    	this.adapterOnChange = adapter; 
 		
 		this.addActionListener(new ActionListener() {

             public void actionPerformed(ActionEvent e) {
            	 adapterOnChange.execute();
             }
         });
 		
 		this.addFocusListener(new FocusAdapter() {

			@Override
             public void focusLost(FocusEvent e) {
				if ((lastValue!=null) && (!lastValue.equals(getText()))) {
					adapterOnChange.execute();
				}
             }

			@Override
			public void focusGained(FocusEvent e) {
				lastValue = getText();
			}
             
         });
     }

	public void onEnter(MkRun adpter) {
		this.adapterOnEnter = adpter;
	}
   
}
