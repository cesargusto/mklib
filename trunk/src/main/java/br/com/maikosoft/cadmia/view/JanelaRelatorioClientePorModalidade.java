package br.com.maikosoft.cadmia.view;

import java.awt.GridBagConstraints;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import br.com.maikosoft.cadmia.ClienteCadMia;
import br.com.maikosoft.cadmia.Modalidade;
import br.com.maikosoft.cadmia.service.ClienteService;
import br.com.maikosoft.core.MkServiceException;
import br.com.maikosoft.core.MkTransferObject;
import br.com.maikosoft.mklib.MkButton.MkButtonImprimir;
import br.com.maikosoft.mklib.MkButton.MkButtonPesquisar;
import br.com.maikosoft.mklib.MkDialog;
import br.com.maikosoft.mklib.MkFieldText;
import br.com.maikosoft.mklib.MkPanelTable;
import br.com.maikosoft.mklib.MkWindow;
import br.com.maikosoft.view.JanelaPrintPreview;

@SuppressWarnings("serial")
public class JanelaRelatorioClientePorModalidade extends MkWindow {
	
	private MkFieldText fieldModalidade;
	private MkButtonImprimir buttonImprimir;
	private MkButtonPesquisar buttonPesquisar;
	
	private ClienteService clienteService;
	
	private Modalidade modalidade;
	
	@Override
	protected void initWindow() {
		
		MkPanelTable panelTable = new MkPanelTable();
		
		fieldModalidade.setEditable(false);
		
		panelTable.addRow("Modalidade:", fieldModalidade, buttonPesquisar, GridBagConstraints.NONE);
		
		addPanelCenter(panelTable, 420, 100);
		
		addPanelButton(true, buttonImprimir);
				
	}
	
	protected void pesquisar() {
		MkTransferObject<Modalidade> transferObject = new MkTransferObject<Modalidade>() {
			@Override
			public void postTranfer(Modalidade value) {
				modalidade = value;
				fieldModalidade.setText(value.getNome());
			}
		};
		JanelaModalidadeConsulta janelaModalidadeConsulta = new JanelaModalidadeConsulta();
		janelaModalidadeConsulta.setTranferir(transferObject);
		janelaModalidadeConsulta.showWindow("Transferir Modalidade", false);
	}
	
	protected void imprimir() {
			
		try {
			if (modalidade == null) {
				MkDialog.warm("Informe a Modalidade");
				buttonPesquisar.grabFocus();
			} else {
					this.waitCursor(true);
					
					Map<String, Object> where = new HashMap<String, Object>();
					where.put("modalidadeId", modalidade.getId());
					List<ClienteCadMia> list = clienteService.findAll(where);
					
					HashMap<String, Object> parametro = new HashMap<String, Object>();
					parametro.put("modalidade", modalidade.getNome());
					InputStream streamResource = JanelaRelatorioClientePorModalidade.class.getClassLoader().getResourceAsStream("report/cadmia/RelatorioClientePorModalidade.jasper");
					JasperPrint print = JasperFillManager.fillReport(streamResource, parametro, new JRBeanCollectionDataSource(list));
					JanelaPrintPreview.showView(print, true);
					
			}
		} catch (MkServiceException exception) {
			MkDialog.error("Erro ao buscar clientes", exception);
		} catch (JRException ex) {
			MkDialog.error("Erro ao gerar relatório", ex);
		} finally {
			this.waitCursor(false);
		}
			
	}
	
}
