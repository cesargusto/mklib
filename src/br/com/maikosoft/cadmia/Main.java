package br.com.maikosoft.cadmia;

import br.com.maikosoft.layout.swing.MkApplication;

public class Main {

	public static void main(String[] args) {
		
		MkApplication application = MkApplication.getInstance();
		application.init("CadMia");
		
		application.setLocation(400, 300);
		
		application.updateMkMenu(EnumMenu.getListMenus());
				
		
	}

}
