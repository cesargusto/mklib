package br.com.maikosoft.view.menu;

import java.io.IOException;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.mklib.MkDialog;
import br.com.maikosoft.mklib.MkMenu;

public class MenuDebug extends MkMenu {
	
	public MenuDebug(MkMenu menuPai) {
		super(menuPai);
	}

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

}
