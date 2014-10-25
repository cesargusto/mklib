package br.com.maikosoft.alianca.view;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import br.com.maikosoft.alianca.service.NotaEncomendaService;
import br.com.maikosoft.core.MkServiceException;
import br.com.maikosoft.mklib.MkButton.MkButtonImprimir;
import br.com.maikosoft.mklib.MkDialog;
import br.com.maikosoft.mklib.MkFieldDate;
import br.com.maikosoft.mklib.MkFieldMask;
import br.com.maikosoft.mklib.MkFieldMask.EnumMkMask;
import br.com.maikosoft.mklib.MkFieldText;
import br.com.maikosoft.mklib.MkPanelTable;
import br.com.maikosoft.mklib.MkWindow;
import br.com.maikosoft.util.MkUtil;

@SuppressWarnings("serial")
public class JanelaNotaEncomenda extends MkWindow {
	
	private MkFieldMask fieldNumero;
	private MkFieldDate fieldDataCadastro;
	private MkFieldText fieldNome;
	private MkFieldText fieldEndereco;
	private MkFieldText fieldArmacao;
	private MkFieldText fieldLente;
	private MkFieldDate fieldProcurarDia;
	private MkFieldText fieldProcuaraHora;
	private MkFieldMask fieldTotal;
	private MkFieldMask fieldSinal;
//	private MkFieldMask fieldApagar;
	
	private MkButtonImprimir buttonImprimir;
	
	
	private NotaEncomendaService notaEncomendaService;
	
	@Override
	protected void initWindow() {
		
		MkPanelTable panelTable = new MkPanelTable();
		
		panelTable.addRow("Número:", fieldNumero,"Data:", fieldDataCadastro);
		panelTable.addRow("Nome:", fieldNome, MkPanelTable.getDefaultCell(3));
		panelTable.addRow("Endereço:", fieldEndereco, MkPanelTable.getDefaultCell(3));
		panelTable.addRow("Armação:", fieldArmacao, MkPanelTable.getDefaultCell(3));
		panelTable.addRow("Lente:", fieldLente, MkPanelTable.getDefaultCell(3));
		panelTable.addRow("Procurar dia:", fieldProcurarDia, "Hora:", fieldProcuaraHora);
		panelTable.addRow("Total:", fieldTotal, "Sinal:", fieldSinal);
		
		
		fieldTotal.setMask(EnumMkMask.CURRENCY);
		fieldSinal.setMask(EnumMkMask.CURRENCY);
//		fieldApagar.setMask(EnumMkMask.CURRENCY);
		fieldNumero.setMask(EnumMkMask.NUMBER);
		
		addPanelCenter(panelTable, 610, 300);
		
		addPanelButton(true, buttonImprimir);		
	}
	
	@Override
	public Object showWindow(String title, boolean isModal) {
		Object showWindow = super.showWindow(title, isModal);
		fieldDataCadastro.grabFocus();
		notaEncomendaService = application.getApplicationContext().getBean(NotaEncomendaService.class);
		try {
			fieldNumero.setValue(notaEncomendaService.nextNumero());
		} catch (MkServiceException ex) {
			MkDialog.error("Erro ao obter proximo numero", ex);
		}
		fieldDataCadastro.setDate(new Date());
		return showWindow;
	}



	protected void imprimir() {
		try {
			if (StringUtils.isBlank(fieldNumero.getText())) {
				MkDialog.warm("Informe o Número");
				fieldNumero.grabFocus();
			} else if (fieldDataCadastro.getDate() == null) {
				MkDialog.warm("Informe a Data");
				fieldDataCadastro.grabFocus();
			} else {
					this.waitCursor(true);
					
					Long numero = MkUtil.toLong(fieldNumero.getText());
					
//					List<Duplicata> listDuplicata = duplicataService.gerarDuplicatas(clienteAlianca, 
//							fieldDataVencimento.getDate(), 
//							MkUtil.toBigDecimal(fieldValor.getText()),
//							fieldNumeroParcela.getSelected(),
//							MkUtil.toLong(fieldNumeroNota.getText()),
//							textObservacao.getText());
					
//					HashMap<String,Object> map = new HashMap<String, Object>();
//					map.put("clienteNome", clienteAlianca.getNome());
//					map.put("clienteEndereco", (StringUtils.isBlank(clienteAlianca.getNumero()) ? 
//							clienteAlianca.getEndereco() : 
//							clienteAlianca.getEndereco() + ", " +clienteAlianca.getNumero()));
//					map.put("clienteCPF", clienteAlianca.getCpf());
//					map.put("clienteCidade", clienteAlianca.getCidade());
//										
//					InputStream streamResource = JanelaNotaEncomenda.class.getClassLoader().getResourceAsStream("report/alianca/Duplicata.jasper");
//					JasperPrint print = JasperFillManager.fillReport(streamResource, map, new JRBeanCollectionDataSource(listDuplicata));
//					JanelaPrintPreview.showView(print, true);
										
					notaEncomendaService.updateNumero(numero);
					
					
			}
		} catch (Exception ex) {
			MkDialog.error("Erro ao gerar duplicatas", ex);
		} finally {
			this.waitCursor(false);
		}
			
	}


}
