package br.com.maikosoft.alianca.view;

import java.awt.GridBagConstraints;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import br.com.maikosoft.alianca.ClienteAlianca;
import br.com.maikosoft.core.MkTransferObject;
import br.com.maikosoft.mklib.MkButton.MkButtonImprimir;
import br.com.maikosoft.mklib.MkButton.MkButtonPesquisar;
import br.com.maikosoft.mklib.MkDialog;
import br.com.maikosoft.mklib.MkFieldDate;
import br.com.maikosoft.mklib.MkFieldMask;
import br.com.maikosoft.mklib.MkFieldMask.EnumMkMask;
import br.com.maikosoft.mklib.MkFieldText;
import br.com.maikosoft.mklib.MkPanelTable;
import br.com.maikosoft.mklib.MkTextArea;
import br.com.maikosoft.mklib.MkWindow;
import br.com.maikosoft.util.Extenso;
import br.com.maikosoft.util.MkUtil;

@SuppressWarnings("serial")
public class JanelaDuplicataGerar extends MkWindow {
	
	private MkFieldText fieldCliente;
	private MkFieldMask fieldValor;
	private MkFieldDate fieldData;
	private MkTextArea textObservacao;
	private MkButtonImprimir buttonImprimir;
	private MkButtonPesquisar buttonPesquisar;
	
//	private ClienteService clienteService;
	
	private ClienteAlianca clienteAlianca;
	
	@Override
	protected void initWindow() {
		
		MkPanelTable panelTable = new MkPanelTable();
		
		fieldCliente.setEditable(false);
		
		panelTable.addRow("Cliente:", fieldCliente, MkPanelTable.getDefaultCell(3), buttonPesquisar, GridBagConstraints.NONE);
		panelTable.addRow("Valor:", fieldValor, "Data:", fieldData, GridBagConstraints.NONE);
		panelTable.addRow(textObservacao.getJScrollPane("Observação"), GridBagConstraints.BOTH);
		
		fieldValor.setMask(EnumMkMask.CURRENCY);
		
		addPanelCenter(panelTable, 500, 300);
		
		addPanelButton(true, buttonImprimir);
				
	}
	
	protected void pesquisar() {
		JanelaClienteConsulta janelaConsulta = new JanelaClienteConsulta();
		janelaConsulta.setTranferir(getTransferObject());
		janelaConsulta.showWindow("Transferir Cliente", false);
	}
	
	private MkTransferObject<ClienteAlianca> getTransferObject() {
		MkTransferObject<ClienteAlianca> transferObject = new MkTransferObject<ClienteAlianca>() {
			@Override
			public void postTranfer(ClienteAlianca value) {
				clienteAlianca = value;
				fieldCliente.setText(value.getNome());
				fieldValor.grabFocus();
			}
		};
		return transferObject;
	}

	protected void imprimir() {
			
		try {
			if (clienteAlianca == null) {
				MkDialog.warm("Informe o Cliente");
				buttonPesquisar.grabFocus();
			} else {
					this.waitCursor(true);
					
					HashMap<String, Object> parametro = new HashMap<String, Object>();
					parametro.put("cliente", clienteAlianca.getNome());
					BigDecimal valor = MkUtil.toBigDecimal(fieldValor.getText());
					StringBuilder sb = new StringBuilder(50);
					sb.append("R$ ").append(fieldValor.getText()).append(" (").append(new Extenso(valor, true)).append(')');
					parametro.put("valor", sb.toString());
					parametro.put("data", MkUtil.toString(fieldData.getDate()));
					parametro.put("observacao", textObservacao.getText());
					
//					InputStream streamResource = JanelaRelatorioClientePorModalidade.class.getClassLoader().getResourceAsStream("report/cadmia/Recibo.jasper");
//					JasperPrint print = JasperFillManager.fillReport(streamResource, parametro, new JREmptyDataSource());
//					JasperViewer.viewReport(print, false);
					
			}
		} catch (Exception ex) {
			MkDialog.error("Erro ao gerar recibo", ex);
		} finally {
			this.waitCursor(false);
		}
			
	}

	public void setDados(ClienteAlianca clienteAlianca) {
		getTransferObject().postTranfer(clienteAlianca);
	}

}
