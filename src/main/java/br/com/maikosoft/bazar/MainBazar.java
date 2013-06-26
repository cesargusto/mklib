package br.com.maikosoft.bazar;

import br.com.maikosoft.bazar.view.JanelaConsultaRapida;
import br.com.maikosoft.mklib.MkApplication;
import br.com.maikosoft.view.JanelaLogin;

public class MainBazar {

	public static void main(String[] args) {
		
		MkApplication application = MkApplication.getInstance();
		
		application.init("Bazar", "bazar");
		
		JanelaLogin.getInstance();
		
		application.updateMkMenu(EnumMenuBazar.getListMenus());
		
		new JanelaConsultaRapida().showWindow();
	}

}
