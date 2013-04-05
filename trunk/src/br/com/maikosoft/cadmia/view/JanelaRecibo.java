package br.com.maikosoft.cadmia.view;

import java.awt.GridBagConstraints;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import br.com.maikosoft.cadmia.Cliente;
import br.com.maikosoft.cadmia.Extenso;
import br.com.maikosoft.core.MkTransferObject;
import br.com.maikosoft.core.MkUtil;
import br.com.maikosoft.layout.swing.MkButton.MkButtonImprimir;
import br.com.maikosoft.layout.swing.MkButton.MkButtonPesquisar;
import br.com.maikosoft.layout.swing.MkDialog;
import br.com.maikosoft.layout.swing.MkFieldDate;
import br.com.maikosoft.layout.swing.MkFieldMask;
import br.com.maikosoft.layout.swing.MkFieldMask.EnumMkMask;
import br.com.maikosoft.layout.swing.MkFieldText;
import br.com.maikosoft.layout.swing.MkPanelTable;
import br.com.maikosoft.layout.swing.MkTextArea;
import br.com.maikosoft.layout.swing.MkWindow;

@SuppressWarnings("serial")
public class JanelaRecibo extends MkWindow {
	
	private MkFieldText fieldCliente;
	private MkFieldMask fieldValor;
	private MkFieldDate fieldData;
	private MkTextArea textObservacao;
	private MkButtonImprimir buttonImprimir;
	private MkButtonPesquisar buttonPesquisar;
	
//	private ClienteService clienteService;
	
	private Cliente cliente;
	
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
	
	private MkTransferObject<Cliente> getTransferObject() {
		MkTransferObject<Cliente> transferObject = new MkTransferObject<Cliente>() {
			@Override
			public void postTranfer(Cliente value) {
				cliente = value;
				fieldCliente.setText(value.getNome());
				fieldValor.grabFocus();
			}
		};
		return transferObject;
	}

	protected void imprimir() {
			
		try {
			if (cliente == null) {
				MkDialog.warm("Informe o Cliente");
				buttonPesquisar.grabFocus();
			} else {
					this.waitCursor(true);
					
					HashMap<String, Object> parametro = new HashMap<String, Object>();
					parametro.put("cliente", cliente.getNome());
					BigDecimal valor = MkUtil.toBigDecimal(fieldValor.getText());
					StringBuilder sb = new StringBuilder(50);
					sb.append("R$ ").append(fieldValor.getText()).append(" (").append(new Extenso(valor, true)).append(')');
					parametro.put("valor", sb.toString());
					parametro.put("data", fieldData.getText());
					parametro.put("observacao", textObservacao.getText());
					
					InputStream streamResource = JanelaRelatorioClientePorModalidade.class.getClassLoader().getResourceAsStream("resource/report/Recibo.jasper");
					JasperPrint print = JasperFillManager.fillReport(streamResource, parametro, new JREmptyDataSource());
					JasperViewer.viewReport(print, false);
					
			}
		} catch (JRException ex) {
			MkDialog.error("Erro ao gerar recibo", ex);
		} finally {
			this.waitCursor(false);
		}
			
	}

	public void setDados(Cliente cliente, Date data, BigDecimal valor, String observacao) {
		getTransferObject().postTranfer(cliente);
		fieldValor.setValue(valor);
		fieldData.setText(MkUtil.toString(data));
		textObservacao.setText(observacao);
		imprimir();
	}

}
