package br.com.maikosoft.layout.swing;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import org.apache.log4j.Logger;


@SuppressWarnings("serial")
public class MkDialog extends MkWindow {
	
	private static final Logger logger = Logger.getLogger(MkPanelTable.class);
	
	private EnumDialog enumDialog;
	private String message;
	
	public MkDialog(EnumDialog enumDialog, String message) {
		logger.debug("Chamando dialog tipo: "+enumDialog.name());
		this.enumDialog = enumDialog;
		this.message = message;
		showView(enumDialog.title, true);
	}
		
	@Override
	protected void initWindow() {
		
		final MkTextArea textAreaMessage = new MkTextArea();
		textAreaMessage.setLineWrap(true);
		textAreaMessage.setWrapStyleWord(true);
		textAreaMessage.setText(this.message);
		textAreaMessage.setCaretPosition(0);
		textAreaMessage.setOpaque(true);
		
		JLabel icon = new JLabel(new ImageIcon(this.getClass().getResource(enumDialog.icon)));
		
		MkButton buttonCopy = new MkButton();
		buttonCopy.setText("Copiar Mensagem");
		buttonCopy.onClick(new MkRun() {			
			@Override
			public void execute() {
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents( new StringSelection(textAreaMessage.getText()), null);
			}
		});
		
		textAreaMessage.setEditable(false);
		textAreaMessage.setFocusable(false);
		textAreaMessage.setTransparent(true);
		
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints cell = new GridBagConstraints();
		cell.insets = new Insets(15, 15, 15, 15);
		cell.gridx = 0;
		cell.gridy = 0;
		cell.ipadx = 50;
		cell.anchor = GridBagConstraints.CENTER;
		panel.add(icon, cell);
		cell = new GridBagConstraints();
		cell.insets = new Insets(2, 2, 2, 2);
		cell.gridx = 1;
		cell.gridy = 0;
		panel.add(textAreaMessage, cell);				
		
		addPanelCenter(panel, (textAreaMessage.getText().length()<150 ? 500: 800), (textAreaMessage.getText().length()<150 ? 100: 300));
		
		addPanelButton(false, EnumMkButton.FECHAR.getButton(this), buttonCopy);
	}
	
	protected MkRun fechar() {
    	return new MkRun() {
			@Override
			public void execute() {
				fecharJanela();
			}
		};
    }
	

	public static void info(String message) {
		
		ImageIcon icon = new ImageIcon(MkDialog.class.getClassLoader().getResource("resource/icon/informacao.png"));
		final JLabel labelMessage = new JLabel("<html><font size=+1 color='blue' >&nbsp;&nbsp;"+message+"&nbsp;&nbsp;</font></html>", icon, SwingConstants.CENTER);
        
        final MkApplication application = MkApplication.getInstance();
        application.getContentPane().add(labelMessage, BorderLayout.NORTH);
        application.getRootPane().revalidate();
        
        Timer t = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	application.getContentPane().remove(labelMessage);
            	application.getRootPane().revalidate();
            }
        });       
        t.start();
        
//        final JInternalFrame popFrame = new JInternalFrame();
//        popFrame.add(labelMessage);
//        popFrame.setFocusable(false);
//        popFrame.setFrameIcon(new ImageIcon(MkDialog.class.getClassLoader().getResource("resource/icon/informacao.png")));
//        popFrame.setTitle("Aviso");
//        popFrame.pack();
//       
//        int x = (desktopSize.width - popFrame.getWidth())-20;
//        int y = (desktopSize.height - popFrame.getHeight())-20;
//       
//        popFrame.setLocation((x < 0 ? 0 : x), (y < 0 ? 0 : y));
//        desktopPane.add(popFrame);
//        popFrame.setVisible(true);
//       
//        Timer t = new Timer(2000, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                popFrame.dispose();
//            }
//        });       
//        t.start();
//        
//        JInternalFrame[] allFrames = desktopPane.getAllFrames();
//        if (allFrames != null) {
//            for (int i = allFrames.length; i > 0; i--) {
//                if (allFrames[i - 1].isVisible() && allFrames[i - 1].isMaximizable()) {
//                    desktopPane.getDesktopManager().activateFrame(allFrames[i - 1]);
//                    allFrames[i - 1].requestFocusInWindow();
//                }
//            }
//        }
	}
	
//	public static void info(String message, String detail) {
//		new MkDialog(EnumDialog.INFO, message, detail, true);
//	}
//	
	public static void warm(String message) {
		new MkDialog(EnumDialog.WARM, message);
	}
//	
//	public static void warm(String message, boolean isVisibleCopyButton) {
//		new MkDialog(EnumDialog.WARM, message, null, isVisibleCopyButton);
//	}
//	
//	public static void warm(String message, String detail) {
//		new MkDialog(EnumDialog.WARM, message, detail, true);
//	}
//	
	public static boolean confirm(String message) {
		Object[] options = {"Sim", "Não"};		
		Object result = question(message, options);
		return ((result!=null) && (result.equals(options[0])));
		
    }
	
	public static Object question(String message, Object[] options) {
		JOptionPane optionPane = new JOptionPane(message, JOptionPane.QUESTION_MESSAGE, JOptionPane.DEFAULT_OPTION, 
				new ImageIcon(MkDialog.class.getResource(EnumDialog.CONFIRM.icon)), options, options[options.length-1]);
		JDialog dialog = optionPane.createDialog(null, EnumDialog.CONFIRM.title);
		
		dialog.getRootPane().registerKeyboardAction(getFocusListener(false),
	            KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0),
	            JComponent.WHEN_IN_FOCUSED_WINDOW);
		dialog.getRootPane().registerKeyboardAction(getFocusListener(true),
	            KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0),
	            JComponent.WHEN_IN_FOCUSED_WINDOW);
				dialog.setVisible(true);
				
		return optionPane.getValue();
		
    }

//	
//	public static void error(String message) {
//		new MkDialog(EnumDialog.ERROR, message, null, false);
//	}
	
	public static void error(String message, Throwable exception) {
		logger.error(message, exception);
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		exception.printStackTrace(pw);
		
		new MkDialog(EnumDialog.ERROR, message+"\n"+sw.toString());
		
	}

	private enum EnumDialog {
		INFO("Aviso", "/resource/icon/informacao.png"),
		CONFIRM("Confirmação", "/resource/icon/questao.png"),
		ERROR("Erro", "/resource/icon/erro.png"),
		WARM("Atenção", "/resource/icon/atencao.png");
		
		private final String title;
		private final String icon;
		
		EnumDialog(String title, String icon) {
			this.title= title;
			this.icon= icon;
		}
		
	}
	
	public static ActionListener getFocusListener(final boolean next) {
		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				KeyboardFocusManager manager = KeyboardFocusManager
						.getCurrentKeyboardFocusManager();
				if (next) {
					manager.focusNextComponent();
				} else {
					manager.focusPreviousComponent();
				}
			}
		};
		return listener;
	}

}
