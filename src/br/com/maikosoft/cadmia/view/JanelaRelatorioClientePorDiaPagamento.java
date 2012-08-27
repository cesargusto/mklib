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
import net.sf.jasperreports.view.JasperViewer;
import br.com.maikosoft.cadmia.Cliente;
import br.com.maikosoft.cadmia.ClienteAndSaldoVO;
import br.com.maikosoft.cadmia.service.ClienteService;
import br.com.maikosoft.cadmia.service.FinanceiroService;
import br.com.maikosoft.core.MkServiceException;
import br.com.maikosoft.layout.swing.MkButton.MkButtonImprimir;
import br.com.maikosoft.layout.swing.MkComboBox;
import br.com.maikosoft.layout.swing.MkDialog;
import br.com.maikosoft.layout.swing.MkFieldText;
import br.com.maikosoft.layout.swing.MkPanelTable;
import br.com.maikosoft.layout.swing.MkWindow;

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
					List<Cliente> listCliente = clienteService.findAll(where);
					
					LinkedList<ClienteAndSaldoVO> list = new LinkedList<ClienteAndSaldoVO>();
					for (Cliente cliente : listCliente) {
						BigDecimal saldo = financeiroService.getSaldo(cliente);
						list.add(new ClienteAndSaldoVO(cliente, null, saldo));
					}
					
					HashMap<String, Object> parametro = new HashMap<String, Object>();
					parametro.put("diaPagamento", comboDiaPagamentoMensalidade.getSelected());
					InputStream streamResource = JanelaRelatorioClientePorDiaPagamento.class.getClassLoader().getResourceAsStream("resource/report/RelatorioClientePorDiaPagamento.jasper");
					JasperPrint print = JasperFillManager.fillReport(streamResource, parametro, new JRBeanCollectionDataSource(list));
					JasperViewer.viewReport(print, false);
					
			
		} catch (MkServiceException exception) {
			MkDialog.error("Erro ao buscar clientes", exception);
		} catch (JRException ex) {
			MkDialog.error("Erro ao gerar relatório", ex);
		} finally {
			this.waitCursor(false);
		}
			
	}
	
}
