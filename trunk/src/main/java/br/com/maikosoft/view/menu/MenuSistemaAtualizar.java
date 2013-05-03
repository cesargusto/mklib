package br.com.maikosoft.view.menu;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.swing.SwingUtilities;

import org.apache.commons.io.FileUtils;

import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.mklib.MkDialog;
import br.com.maikosoft.mklib.MkMenu;

public class MenuSistemaAtualizar extends MkMenu {
	
	private String projeto;
	
	public MenuSistemaAtualizar(String projeto, MkMenu menuPai) {
		super(menuPai);
		this.projeto = projeto;
	}

	@Override
	public MkRun getAcao() {
		return atualizarSistema();
	}

	@Override
	public String getTitulo() {
		return "Atualizar";
	}

	@Override
	public boolean hasSeparador() {
		return true;
	}	
	
	
	private MkRun atualizarSistema() {
		return new MkRun() {
			@Override
			public void execute() {
				
				MkDialog.warm("Aguarde o sistema esta sendo atualizado");
				
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						File file = new File(projeto+".jar");
						file.delete();
						
						try {
							FileUtils.copyURLToFile(new URL("http://mklib.googlecode.com/files/"+projeto+".jar"), file);
						} catch (IOException ex) {
							MkDialog.error("Erro ao atualizar sistema", ex);
						}
						
						MkDialog.warm("Sistema foi atualizado e será encerrado");
						
						System.exit(0);
						
					}
				});
				
			}
		};
	}

}
