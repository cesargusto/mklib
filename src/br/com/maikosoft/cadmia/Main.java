package br.com.maikosoft.cadmia;

import javax.swing.JInternalFrame;

import br.com.maikosoft.cadmia.view.JanelaClienteConsultaRapida;
import br.com.maikosoft.layout.swing.MkApplication;

public class Main {

	public static void main(String[] args) {
		
		MkApplication application = MkApplication.getInstance();
		application.init("CadMia");
		
		application.setLocation(400, 300);
		
		application.updateMkMenu(EnumMenu.getListMenus());
		
		JanelaClienteConsultaRapida consultaRapida = new JanelaClienteConsultaRapida();
		JInternalFrame view = (JInternalFrame) consultaRapida.showView("Consulta Rápida", false);
		view.setLocation(0, 0);
		view.setMaximizable(false);
		view.setClosable(false);
		view.setIconifiable(false);
		view.updateUI();
				
	}

}
