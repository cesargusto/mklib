package br.com.maikosoft.cadmia.view;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import br.com.maikosoft.cadmia.ClienteAndSaldoVO;
import br.com.maikosoft.cadmia.ClienteCadMia;
import br.com.maikosoft.cadmia.service.ClienteService;
import br.com.maikosoft.cadmia.service.FinanceiroService;
import br.com.maikosoft.core.MkServiceException;
import br.com.maikosoft.mklib.MkButton.MkButtonImprimir;
import br.com.maikosoft.mklib.MkComboBox;
import br.com.maikosoft.mklib.MkDialog;
import br.com.maikosoft.mklib.MkFieldText;
import br.com.maikosoft.mklib.MkPanelTable;
import br.com.maikosoft.mklib.MkWindow;
import br.com.maikosoft.view.JanelaPrintPreview;

@SuppressWarnings("serial")
public class JanelaRelatorioClientePorDiaPagamento extends MkWindow {
	
	private MkFieldText fieldModalidade;
	private MkButtonImprimir buttonImprimir;
	private MkComboBox<String> comboDiaPagamentoMensalidade;
	
	private ClienteService clienteService;
	private FinanceiroService financeiroService;
		
	@Override
	protected void initWindow() {
		
		MkPanelTable panelTable = new MkPanelTable();
		
		fieldModalidade.setEditable(false);
		
		comboDiaPagamentoMensalidade.setList(Arrays.asList("01","05", "10", "15", "20", "25"));
		
		panelTable.addRow("Dia Pagamento:", comboDiaPagamentoMensalidade);
		
		addPanelCenter(panelTable, 320, 100);
		
		addPanelButton(true, buttonImprimir);
				
	}
		
	protected void imprimir() {
			
		try {
				this.waitCursor(true);
					
					Map<String, Object> where = new HashMap<String, Object>();
					where.put("diaPagamento", comboDiaPagamentoMensalidade.getSelected());
					List<ClienteCadMia> listCliente = clienteService.findAll(where);
					
					LinkedList<ClienteAndSaldoVO> list = new LinkedList<ClienteAndSaldoVO>();
					for (ClienteCadMia clienteCadMia : listCliente) {
						BigDecimal saldo = financeiroService.getSaldo(clienteCadMia);
						list.add(new ClienteAndSaldoVO(clienteCadMia, null, saldo));
					}
					
					HashMap<String, Object> parametro = new HashMap<String, Object>();
					parametro.put("diaPagamento", comboDiaPagamentoMensalidade.getSelected());
					InputStream streamResource = JanelaRelatorioClientePorDiaPagamento.class.getClassLoader().getResourceAsStream("report/cadmia/RelatorioClientePorDiaPagamento.jasper");
					JasperPrint print = JasperFillManager.fillReport(streamResource, parametro, new JRBeanCollectionDataSource(list));
					JanelaPrintPreview.showView(print, true);
					
			
		} catch (MkServiceException exception) {
			MkDialog.error("Erro ao buscar clientes", exception);
		} catch (JRException ex) {
			MkDialog.error("Erro ao gerar relatório", ex);
		} finally {
			this.waitCursor(false);
		}
			
	}
	
}
