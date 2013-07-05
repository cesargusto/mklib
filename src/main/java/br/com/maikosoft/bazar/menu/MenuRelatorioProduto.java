package br.com.maikosoft.bazar.menu;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.mklib.MkApplication;
import br.com.maikosoft.mklib.MkDialog;
import br.com.maikosoft.mklib.MkMenu;
import br.com.maikosoft.view.JanelaLogin;
import br.com.maikosoft.view.JanelaPrintPreview;

public class MenuRelatorioProduto extends MkMenu {

	public MenuRelatorioProduto(MkMenu menuPai) {
		super(menuPai);
	}

	@Override
	public MkRun getAcao() {
		return new MkRun() {
			
			@Override
			public void execute() {
				if (JanelaLogin.getInstance().getUsuarioLogado().isAdministrador()) {
					imprimir();
				} else {
					MkDialog.warm("Acesso Negado");
				}
			}
		};
	}

	@Override
	public String getTitulo() {
		return "Relatório Produto";
	}
	
	protected void imprimir() {
		
		try {
			
			DataSource dataSource = MkApplication.getInstance().getApplicationContext().getBean(DataSource.class);
			
			HashMap<String, Object> parametro = new HashMap<String, Object>();
			InputStream streamResource = MenuRelatorioProduto.class.getClassLoader().getResourceAsStream("report/bazar/Produto.jasper");
			JasperPrint print = JasperFillManager.fillReport(streamResource, parametro, dataSource.getConnection());
			JanelaPrintPreview.showView(print, true);					
			
		} catch (SQLException exception) {
			MkDialog.error("Erro ao buscar produtos", exception);
		} catch (JRException ex) {
			MkDialog.error("Erro ao gerar relatório", ex);
		} 
	}
	
}
