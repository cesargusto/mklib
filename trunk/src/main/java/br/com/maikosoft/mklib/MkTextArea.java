/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.maikosoft.mklib;

import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author michael
 */
@SuppressWarnings("serial")
public class MkTextArea extends JTextArea {
	
	private boolean isTransparent;
	private JScrollPane scrollPane;

    public MkTextArea () {
        super();
        this.setLineWrap(true);
        this.addKeyListener(new java.awt.event.KeyAdapter() {
        	public void keyPressed(java.awt.event.KeyEvent evt) {
        		if (evt.getKeyCode() == KeyEvent.VK_TAB) {
        			if (evt.isShiftDown()) {
        				KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        				manager.focusPreviousComponent();
        			} else {
        				String text = getText();
        				int tam = text.length();
        				if ((tam > 0) && (text.lastIndexOf("\t") == tam - 1)) {
        					setText(text.substring(0, tam - 1));
        					KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        					manager.focusNextComponent();
        					evt.consume();
        				}
        			}
        		}
        	}
        });
        
    }

    public JScrollPane getJScrollPane(String title) {
		if (scrollPane == null) {
			this.scrollPane = new JScrollPane();
			this.scrollPane.setViewportView(this);
		}
		
		if (title !=null) {
			scrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder(title));
		}
		
		if (isTransparent) {
			this.setOpaque(false);
			this.setBorder(BorderFactory.createEmptyBorder());
			scrollPane.setOpaque(false);
			scrollPane.getViewport().setOpaque(false);
			scrollPane.setBorder(BorderFactory.createEmptyBorder());
			scrollPane.setViewportBorder(BorderFactory.createEmptyBorder());
		}
		return scrollPane;
	}

	public void setTransparent(boolean isTransparent) {
		this.isTransparent = isTransparent;
	}
    
    

}
