package br.com.maikosoft.cadmia.view;

import java.math.BigDecimal;
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
import br.com.maikosoft.cadmia.Financeiro;
import br.com.maikosoft.cadmia.service.ClienteService;
import br.com.maikosoft.cadmia.service.FinanceiroService;
import br.com.maikosoft.core.MkException;
import br.com.maikosoft.core.MkServiceException;
import br.com.maikosoft.core.MkUtil;
import br.com.maikosoft.layout.swing.MkButton.MkButtonImprimir;
import br.com.maikosoft.layout.swing.MkDialog;
import br.com.maikosoft.layout.swing.MkFieldDate;
import br.com.maikosoft.layout.swing.MkPanelTable;
import br.com.maikosoft.layout.swing.MkWindow;

@SuppressWarnings("serial")
public class JanelaRelatorioFinanceiro extends MkWindow {
	
//	private static final Logger logger = Logger.getLogger(JanelaLancaMensalidades.class);
	
	private MkFieldDate fieldDataInicial;
	private MkFieldDate fieldDataFinal;
	private MkButtonImprimir buttonImprimir;
	
	private FinanceiroService financeiroService;
	private ClienteService clienteService;
	
	@Override
	protected void initWindow() {
		
		MkPanelTable panelTable = new MkPanelTable();
		panelTable.addRow("Data Inicial", fieldDataInicial);
		panelTable.addRow("Data Final", fieldDataFinal);
		
		addPanelCenter(panelTable, 300, 100);
		
		addPanelButton(true, buttonImprimir);
				
	}
	
	protected void imprimir() {
			
		try {
			if (fieldDataInicial.getDate() == null) {
				MkDialog.warm("Informe a Data Inicial");
				fieldDataInicial.grabFocus();
			} else if (fieldDataFinal.getDate() == null) {
				MkDialog.warm("Informe a Data Final");
				fieldDataFinal.grabFocus();
			} else {
					this.waitCursor(true);
					
					LinkedList<ClienteAndSaldoVO> list = new LinkedList<ClienteAndSaldoVO>();
					List<Cliente> listCliente = clienteService.findAll(null);
					for (Cliente cliente : listCliente) {
						
						BigDecimal totalPago = BigDecimal.ZERO;
						BigDecimal saldoDevedor = BigDecimal.ZERO;
						
						if (cliente.getId()!=null) {
							Map<String, Object> where = new HashMap<String, Object>();
							where.put("cliente_id", cliente.getId());
							where.put("before_data_cadastro", MkUtil.setUltimaHora(fieldDataFinal.getDate()));
							where.put("after_data_cadastro", fieldDataInicial.getDate());							
							List<Financeiro> listFinanceiro = financeiroService.findAll(where );			
							if (list!=null) {
								for (Financeiro financeiro : listFinanceiro) {
									if (financeiro.getDataPagamento() == null) {
										saldoDevedor = saldoDevedor.add(financeiro.getValor());
									} else {
										totalPago = totalPago.add(financeiro.getValor());
									}
								}
							}
						}
						list.add(new ClienteAndSaldoVO(cliente, totalPago, saldoDevedor));
					}
					
					HashMap<String, Object> parametro = new HashMap<String, Object>();
					parametro.put("dataInicial", MkUtil.toString(fieldDataInicial.getDate()));
					parametro.put("dataFinal", MkUtil.toString(fieldDataFinal.getDate()));
					String pathJasper = JanelaRelatorioFinanceiro.class.getClassLoader().getResource("resource/report/RelatorioFinanceiro.jasper").getPath();
					JasperPrint print = JasperFillManager.fillReport(pathJasper, parametro, new JRBeanCollectionDataSource(list));
					JasperViewer.viewReport(print, false);
					
			}
		} catch (MkServiceException exception) {
			MkDialog.error("Erro ao lançar mensalidades", exception);
		} catch (MkException ex) {
			MkDialog.error("Data informada é invalida", ex);
		} catch (JRException ex) {
			MkDialog.error("Erro ao gerar relatório", ex);
		} finally {
			this.waitCursor(false);
		}
			
	}
	

	public class ClienteAndSaldoVO extends Cliente {
		
		private BigDecimal totalPago;
		private BigDecimal saldoDevedor;
		
		public ClienteAndSaldoVO(Cliente cliente, BigDecimal totalPago, BigDecimal saldoDevedor) {
			this.totalPago = totalPago;
			this.saldoDevedor = saldoDevedor;
			this.setNome(cliente.getNome());
			this.setId(cliente.getId());
		}

		public BigDecimal getTotalPago() {
			return totalPago;
		}

		public void setTotalPago(BigDecimal totalPago) {
			this.totalPago = totalPago;
		}

		public BigDecimal getSaldoDevedor() {
			return saldoDevedor;
		}

		public void setSaldoDevedor(BigDecimal saldoDevedor) {
			this.saldoDevedor = saldoDevedor;
		}
		
	}
}