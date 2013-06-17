package br.com.maikosoft.cadmia.view;

import java.awt.GridBagConstraints;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import br.com.maikosoft.cadmia.ClienteCadMia;
import br.com.maikosoft.cadmia.ClienteAndSaldoVO;
import br.com.maikosoft.cadmia.Financeiro;
import br.com.maikosoft.cadmia.Modalidade;
import br.com.maikosoft.cadmia.service.ClienteService;
import br.com.maikosoft.cadmia.service.FinanceiroService;
import br.com.maikosoft.core.MkException;
import br.com.maikosoft.core.MkServiceException;
import br.com.maikosoft.core.MkTransferObject;
import br.com.maikosoft.mklib.MkDialog;
import br.com.maikosoft.mklib.MkFieldDate;
import br.com.maikosoft.mklib.MkFieldText;
import br.com.maikosoft.mklib.MkPanelTable;
import br.com.maikosoft.mklib.MkRadioGroup;
import br.com.maikosoft.mklib.MkWindow;
import br.com.maikosoft.mklib.MkButton.MkButtonImprimir;
import br.com.maikosoft.mklib.MkButton.MkButtonPesquisar;
import br.com.maikosoft.util.MkUtil;

@SuppressWarnings("serial")
public class JanelaRelatorioFinanceiro extends MkWindow {
	
//	private static final Logger logger = Logger.getLogger(JanelaLancaMensalidades.class);
	
	private MkFieldDate fieldDataInicial;
	private MkFieldDate fieldDataFinal;
	private MkButtonImprimir buttonImprimir;
	private MkRadioGroup filtroPago;
	private MkFieldText fieldModalidade;
	private MkButtonPesquisar buttonPesquisar;
	
	private FinanceiroService financeiroService;
	private ClienteService clienteService;
	private Modalidade modalidade;
	
	private String[] radioItens = {"Todos", "Não Pago", "Pago"};
	
	@Override
	protected void initWindow() {
		
		MkPanelTable panelTable = new MkPanelTable();
		
		fieldModalidade.setEditable(false);
		
		panelTable.addRow("Modalidade:", fieldModalidade, buttonPesquisar, GridBagConstraints.NONE);
		
		panelTable.addRow("Data Inicial:", fieldDataInicial);
		panelTable.addRow("Data Final:", fieldDataFinal);
				
		filtroPago.setItens(radioItens, false);
		filtroPago.setSelected(radioItens[0]);
		panelTable.addRow("Filtro:", filtroPago);
		
		addPanelCenter(panelTable, 420, 180);
		
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
			if (fieldDataInicial.getDate() == null) {
				MkDialog.warm("Informe a Data Inicial");
				fieldDataInicial.grabFocus();
			} else if (fieldDataFinal.getDate() == null) {
				MkDialog.warm("Informe a Data Final");
				fieldDataFinal.grabFocus();
			} else {
					this.waitCursor(true);
					
					Map<String, Object> whereCliente = new HashMap<String, Object>();
					if (modalidade != null) {
						whereCliente.put("modalidadeId", modalidade.getId());
					}
					
					LinkedList<ClienteAndSaldoVO> list = new LinkedList<ClienteAndSaldoVO>();
					List<ClienteCadMia> listCliente = clienteService.findAll(whereCliente);
					for (ClienteCadMia clienteCadMia : listCliente) {
						
						BigDecimal totalPago = BigDecimal.ZERO;
						BigDecimal saldoDevedor = BigDecimal.ZERO;
						
						if (clienteCadMia.getId()!=null) {
							Map<String, Object> where = new HashMap<String, Object>();
							where.put("cliente_id", clienteCadMia.getId());
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
						if (filtroPago.getSelected().equals(radioItens[1])) { // nao pago
							if (saldoDevedor.compareTo(BigDecimal.ZERO)>0) {
								list.add(new ClienteAndSaldoVO(clienteCadMia, totalPago, saldoDevedor));
							}
						} else if (filtroPago.getSelected().equals(radioItens[2])) { // pago
							if (totalPago.compareTo(BigDecimal.ZERO) > 0) {
								list.add(new ClienteAndSaldoVO(clienteCadMia, totalPago, saldoDevedor));
							}
						} else {
							list.add(new ClienteAndSaldoVO(clienteCadMia, totalPago, saldoDevedor));
						}
					}
					
					HashMap<String, Object> parametro = new HashMap<String, Object>();
					parametro.put("dataInicial", MkUtil.toString(fieldDataInicial.getDate()));
					parametro.put("dataFinal", MkUtil.toString(fieldDataFinal.getDate()));
					parametro.put("modalidade", (modalidade==null ? "TODAS" : modalidade.getNome()));
					InputStream streamResource = JanelaRelatorioFinanceiro.class.getClassLoader().getResourceAsStream("report/cadmia/RelatorioFinanceiro.jasper");
					JasperPrint print = JasperFillManager.fillReport(streamResource, parametro, new JRBeanCollectionDataSource(list));
					JasperViewer.viewReport(print, false);
					JasperPrintManager.printReport(print, true);
					
			}
		} catch (MkServiceException exception) {
			MkDialog.error("Erro ao buscar clientes e saldo", exception);
		} catch (MkException ex) {
			MkDialog.error("Data informada é invalida", ex);
		} catch (JRException ex) {
			MkDialog.error("Erro ao gerar relatório", ex);
		} finally {
			this.waitCursor(false);
		}
			
	}
}
