package br.com.maikosoft.mklib;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.com.maikosoft.core.MkRun;

import com.jtattoo.plaf.aluminium.AluminiumLookAndFeel;


@SuppressWarnings("serial")
public class MkApplication extends JFrame {
	
	private static final Logger logger = Logger.getLogger(MkApplication.class);

	private static MkApplication instance;
	private JDesktopPane desktopPane;
	private ApplicationContext applicationContext;
	private List<MkWindow> listMkWindow;
	
	private MkApplication() {
		listMkWindow = new LinkedList<MkWindow>();
		desktopPane = new JDesktopPane() {
			@Override
			public void paintComponent(Graphics g) {
				File logo = new File("logo.jpg");
				try {
					BufferedImage img = javax.imageio.ImageIO.read(logo);
					int xImg = img.getWidth();
					int yImg = img.getHeight();
					int x = (this.getWidth() - xImg) / 2;
					int y = (this.getHeight() - yImg) / 2;
					g.drawImage(img, x, y, xImg, yImg, this);
				} catch (Exception e) {
					g.drawString(logo.getAbsolutePath() + " não existe", 50, 50);
				}
			}
		};
		
	}

	public static MkApplication getInstance() {
		if (instance == null) {
			instance = new MkApplication();
		}
		return instance;
	}

	public void init(String title, String projeto) {
		
		applicationContext = new ClassPathXmlApplicationContext("classpath:/"+projeto+".xml", "classpath:/applicationContext.xml");
		
		this.setTitle(title);
		this.addWindowListener(new WindowAdapter() {
        	@Override
            public void windowClosing(WindowEvent we) {
        		if (logger.isDebugEnabled()) {
        			System.exit(0);
        		}
                if (MkDialog.confirm("Sair do sistema?")) {
                    System.exit(0);
                }
            }
        });
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setSize(1000,800);
		if (logger.isDebugEnabled()) {
			this.setLocation(400, 300);
		} else {
			this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
		this.setLayout(new BorderLayout());
		this.getContentPane().add(desktopPane, BorderLayout.CENTER);
		desktopPane.setBackground(Color.WHITE);
		try {
			Properties largeFontProps = new Properties();
			largeFontProps.setProperty("controlTextFont", "Dialog 14");
	        largeFontProps.setProperty("systemTextFont", "Dialog 14");
	        largeFontProps.setProperty("userTextFont", "Dialog 14");
	        largeFontProps.setProperty("menuTextFont", "Dialog 14");
	        largeFontProps.setProperty("windowTitleFont", "Dialog bold 14");
	        largeFontProps.setProperty("subTextFont", "Dialog 12");
	        
	        AluminiumLookAndFeel.setTheme(largeFontProps);

			UIManager.setLookAndFeel(com.jtattoo.plaf.aluminium.AluminiumLookAndFeel.class.getName());
			
			InputMap im = (InputMap) UIManager.getDefaults().get("Button.focusInputMap"); // Enter button
	        Object pressedAction = im.get(KeyStroke.getKeyStroke("pressed SPACE"));
	        Object releasedAction = im.get(KeyStroke.getKeyStroke("released SPACE"));
	        im.put(KeyStroke.getKeyStroke("pressed ENTER"), pressedAction);
	        im.put(KeyStroke.getKeyStroke("released ENTER"), releasedAction);

			
			SwingUtilities.updateComponentTreeUI(instance);
		} catch (Exception ex) { }
		
		this.setVisible(true);
	}

	public void updateMkMenu(List<MkMenu> listMenu) {
		
		HashMap<MkMenu,JMenuItem> listMenuItem = new HashMap<MkMenu, JMenuItem>();
		 
        JMenuBar menuPrincipal = new JMenuBar();
        for (MkMenu mkMenu : listMenu) {
        	if (listMenuItem.containsKey(mkMenu.getClass())) {
				throw new IllegalArgumentException("Item duplicado: "+mkMenu.getClass());
			}

        	JMenuItem menu;
			if (mkMenu.getPai() == null)  {
				menu = new JMenu();
				menuPrincipal.add(menu);
			} else {
				final MkRun acao = mkMenu.getAcao();
				if (acao == null) {
					menu = new JMenu();
				} else {
					menu = new JMenuItem();		
					menu.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							acao.execute();
						}
					});
				}
					
				JMenu menuPai = (JMenu)listMenuItem.get(mkMenu.getPai());
				menuPai.add(menu);				
				menu.setVisible(mkMenu.isVisivel());
				if (mkMenu.hasSeparador()) {
					menuPai.addSeparator();
				}
			}
			
			menu.setText(mkMenu.getTitulo());
						
			listMenuItem.put(mkMenu, menu);
        }
        
        this.setJMenuBar(menuPrincipal);
        	        		
	}
	
	public void disposeWindow(MkWindow mkWindow) {
		
        Container window = mkWindow.getRootPane().getParent();
       
        if (window instanceof JDialog) {
            JDialog modalFrame = (JDialog) window;
            modalFrame.dispose();
        } else {
            JInternalFrame modalFrame = (JInternalFrame) window;
            modalFrame.dispose();
        }
       
        // ajuste para respeitar o maximizar da ultima janela aberta
        JInternalFrame[] allFrames = desktopPane.getAllFrames();
        if (allFrames != null) {
            for (int i = allFrames.length; i > 0; i--) {
                if (allFrames[i - 1].isVisible() && allFrames[i - 1].isMaximizable()) {
                    desktopPane.getDesktopManager().activateFrame(allFrames[i - 1]);
                    allFrames[i - 1].requestFocusInWindow();
                }
            }
        }
        logger.debug("Removendo MkWindow "+mkWindow.getClass().getName());
        listMkWindow.remove(mkWindow);
    }
	
	public Object showWindow(final MkWindow macWindow, String title, boolean isModal) {
		listMkWindow.add(macWindow);
        if (isModal) {
            JDialog modalFrame = new JDialog(this, title, true);
            //macWindow.setJanela(modalFrame);
            final MkRun onCloseWindow = macWindow.onCloseWindow;
            modalFrame.setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
			modalFrame.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					if (onCloseWindow != null) {
						onCloseWindow.execute();
					}
					disposeWindow(macWindow);
				}
			});
            modalFrame.setLayout(new BorderLayout());
            modalFrame.add(macWindow, BorderLayout.CENTER);
            modalFrame.pack();
            Dimension desktopSize = desktopPane.getSize();
            int x = (desktopSize.width - modalFrame.getWidth()) / 2;
            int y = (desktopSize.height - modalFrame.getHeight()) / 2;
            modalFrame.setLocation((x < 0 ? 0 : x), (y < 0 ? 0 : y));
            if (macWindow.panelButton != null) {
            	modalFrame.getRootPane().setDefaultButton((JButton)macWindow.panelButton.getComponent(0));
            }
            modalFrame.setVisible(true);
            return modalFrame;
        } else {
            JInternalFrame internalFrame = new JInternalFrame(title, true, true, true, true);
            internalFrame.setContentPane(macWindow);
            internalFrame.pack();
//            internalFrame.setBounds(internalFrame.getBounds()); //pq?
            desktopPane.add(internalFrame);
           
            internalFrame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);           
            final MkRun onCloseWindow = macWindow.onCloseWindow;
            if (onCloseWindow !=null) {
                internalFrame.setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
               
                internalFrame.addInternalFrameListener(new InternalFrameAdapter() {
                    public void internalFrameClosing(InternalFrameEvent e){
                        onCloseWindow.execute();
                    }
                });
            }

            Dimension desktopSize = desktopPane.getSize();
            int x = (desktopSize.width - internalFrame.getWidth()) / 2;
            int y = (desktopSize.height - internalFrame.getHeight()) / 2;
            internalFrame.setLocation((x < 0 ? 0 : x), (y < 0 ? 0 : y));
            internalFrame.setVisible(true);
            return internalFrame;
        }
    }

	public JDesktopPane getDesktopPane() {
		return desktopPane;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void refreshWindows() {
		for (int i = 0; i < listMkWindow.size(); i++) {
			listMkWindow.get(i).refreshWindow();
		}
	}
	
	
	
}
