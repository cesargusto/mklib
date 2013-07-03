package br.com.maikosoft.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.view.JRViewer;
import br.com.maikosoft.mklib.MkDialog;
import br.com.maikosoft.mklib.MkWindow;

@SuppressWarnings("serial")
public class JanelaPrintPreview extends MkWindow {
	
	private JasperPrint print;
	private boolean isFirePrint;
	
	public JanelaPrintPreview(JasperPrint print, boolean isFirePrint) {
		this.print = print;
		this.isFirePrint = isFirePrint;
		KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
    	this.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                closeWindow();
            }
        }, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
	}

	@Override
	protected void initWindow() {
		JRViewer viewer = new JRViewer(print);
		addPanelCenter(viewer, application.getDesktopPane().getWidth(), application.getDesktopPane().getHeight());
		if (isFirePrint) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					try {
						JasperPrintManager.printReport(print, true);
					} catch (JRException ex) {
						MkDialog.error("Erro ao imprimir", ex);
					}					
				}
			});
		}
	}

	public static void showView(JasperPrint print, boolean isFirePrint) {
		JanelaPrintPreview janelaPrintPreview = new JanelaPrintPreview(print, isFirePrint);
		janelaPrintPreview.showWindow("Imprimir", true);
	}
}
