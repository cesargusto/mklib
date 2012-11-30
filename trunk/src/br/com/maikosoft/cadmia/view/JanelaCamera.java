package br.com.maikosoft.cadmia.view;

import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.JFileChooser;

import br.com.maikosoft.layout.swing.MkWindow;

@SuppressWarnings("serial")
public class JanelaCamera extends MkWindow {
	
	private static JanelaCamera instance;

	@Override
	protected void initWindow() {
		
	}

	public static JanelaCamera getIntance() {
		if (instance == null) {
			instance = new JanelaCamera();
		}
		return instance;
	}

	public byte[] getFoto() {
		try {
			JFileChooser dialog = new JFileChooser();
			dialog.showOpenDialog(this);
			RandomAccessFile f = new RandomAccessFile(dialog.getSelectedFile(), "r");
			byte[] b = new byte[(int)f.length()];
			f.read(b);
			return b;
		} catch (IOException e) {
			return null;
		}
	}
	
	

}
