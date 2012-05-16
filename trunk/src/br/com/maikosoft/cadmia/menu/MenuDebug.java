package br.com.maikosoft.cadmia.menu;

import java.io.IOException;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import br.com.maikosoft.cadmia.EnumMenu;
import br.com.maikosoft.layout.swing.MkDialog;
import br.com.maikosoft.layout.swing.MkMenu;
import br.com.maikosoft.layout.swing.MkRun;

public class MenuDebug extends MkMenu {
	
	@Override
	public MkRun getAcao() {
		return new MkRun() {
			@Override
			public void execute() {
				PatternLayout patternLayout = new PatternLayout("%p %d{dd/MM/yy HH:mm:ss} %20.40c.%M(%L) | %m%n");
				try {
					Logger.getRootLogger().addAppender(new FileAppender(patternLayout, "saida.txt"));
				} catch (IOException ex) {
					MkDialog.error("Erro iniciando debug", ex);
				}
			}
		};
	}

	@Override
	public String getTitulo() {
		return "Ligar DEBUG";
	}

	@Override
	public MkMenu getPai() {
		return EnumMenu.SISTEMA.getMenu();
	}

}
