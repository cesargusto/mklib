package br.com.maikosoft.alianca;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import br.com.maikosoft.alianca.view.JanelaClienteCadastro;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.mklib.MkMenu;
import br.com.maikosoft.mklib.MkWindow;

public class MenuAlianca extends JMenuBar {
	
	private enum EnumMenu { 
		CADASTRO, 
		_CLIENTE,
		__NOVO_CLIENTE(JanelaClienteCadastro.class),
		__CONSULTA_CLIENTE,
		MOVIMENTAÇÃO,
		_CADASTRO_DUPLICATA,
		_GERAR_DUPLICATA;


		private EnumMenu() {
			
		}
		
		private EnumMenu(Class<? extends MkWindow> janela) {
			
		}
		
	};
	
//	CADASTRO("Cadastro"),
//		CADASTRO_CLIENTE("Cliente"),
//	MOVIMENTACAO("Movimentação")
//	;
	
	private HashMap<EnumMenu, JMenuItem> listMenuItem = new HashMap<EnumMenu, JMenuItem>();
	
	public MenuAlianca() {		
		for (EnumMenu  enumMenu: EnumMenu.values()) {
			JMenuItem menu = null;
			if (enumMenu.name().startsWith("_"))  {
								
				JMenu menuNivel1 = new JMenu(enumMenu.name());
				listMenuItem.put(enumMenu, menuNivel1);
				menu.add(menuNivel1);
//				new JMenuItem(enumMenu.)
//				final MkRun acao = mkMenu.getAcao();
//				if (acao == null) {
//					menu = new JMenu();
//				} else {
//					menu = new JMenuItem();		
//					menu.addActionListener(new ActionListener() {
//						public void actionPerformed(ActionEvent e) {
//							acao.execute();
//						}
//					});
//				}
					
//				JMenu menuPai = (JMenu)listMenuItem.get(mkMenu.getPai());
//				menuPai.add(menu);				
//				menu.setVisible(mkMenu.isVisivel());
//				if (mkMenu.hasSeparador()) {
//					menuPai.addSeparator();
//				}
			} else {
				menu = new JMenu(enumMenu.name());
				this.add(menu);
				listMenuItem.put(enumMenu, menu);
			}
			
//			menu.setText(enumMenu.name());
//						
//			listMenuItem.put(enumMenu, menu);
		}
		
		
	}

//	public JMenuBar createMenu() {
//		JMenuBar menuPrincipal = new JMenuBar();
//		
		
//		
//        for (MkMenu mkMenu : listMenu) {
//        	if (listMenuItem.containsKey(mkMenu.getClass())) {
//				throw new IllegalArgumentException("Item duplicado: "+mkMenu.getClass());
//			}
//
//        	JMenuItem menu;
//			if (mkMenu.getPai() == null)  {
//				menu = new JMenu();
//				menuPrincipal.add(menu);
//			} else {
//				final MkRun acao = mkMenu.getAcao();
//				if (acao == null) {
//					menu = new JMenu();
//				} else {
//					menu = new JMenuItem();		
//					menu.addActionListener(new ActionListener() {
//						public void actionPerformed(ActionEvent e) {
//							acao.execute();
//						}
//					});
//				}
//					
//				JMenu menuPai = (JMenu)listMenuItem.get(mkMenu.getPai());
//				menuPai.add(menu);				
//				menu.setVisible(mkMenu.isVisivel());
//				if (mkMenu.hasSeparador()) {
//					menuPai.addSeparator();
//				}
//			}
//			
//			menu.setText(mkMenu.getTitulo());
//						
//			listMenuItem.put(mkMenu, menu);
//        }
//		return menuPrincipal;
//	}

}
