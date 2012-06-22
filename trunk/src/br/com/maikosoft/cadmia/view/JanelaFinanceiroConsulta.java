package br.com.maikosoft.cadmia.view;

import java.awt.GridBagConstraints;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JTable;

import org.apache.log4j.Logger;

import br.com.maikosoft.cadmia.Cliente;
import br.com.maikosoft.cadmia.Financeiro;
import br.com.maikosoft.cadmia.service.FinanceiroService;
import br.com.maikosoft.core.MkServiceException;
import br.com.maikosoft.layout.swing.EnumMkButton;
import br.com.maikosoft.layout.swing.MkButton;
import br.com.maikosoft.layout.swing.MkButton.MkButtonPesquisar;
import br.com.maikosoft.layout.swing.MkDialog;
import br.com.maikosoft.layout.swing.MkFieldText;
import br.com.maikosoft.layout.swing.MkPanelTable;
import br.com.maikosoft.layout.swing.MkRun;
import br.com.maikosoft.layout.swing.MkTable;
import br.com.maikosoft.layout.swing.MkTableModel;
import br.com.maikosoft.layout.swing.MkUtil;
import br.com.maikosoft.layout.swing.MkWindow;

@SuppressWarnings("serial")
public class JanelaFinanceiroConsulta extends MkWindow {
	
	private static final Logger logger = Logger.getLogger(JanelaFinanceiroConsulta.class);
	
	private MkFieldText fieldCliente;
	private MkPanelTable panelCenter;
	private MkTable<Financeiro> table;
	private JLabel labelSaldo;
	
	private MkButtonPesquisar buttonAtualizar;
	private MkButton buttonPagar;
	
	private FinanceiroService financeiroService;
	private Cliente cliente;
	
	public JanelaFinanceiroConsulta(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	protected void initWindow() {
		
		buttonAtualizar.setText("Atualizar");
		panelCenter.addRow("Cliente", fieldCliente);
		panelCenter.addRow(table.getJScrollPane(), GridBagConstraints.BOTH);
		
		addPanelCenter(panelCenter, 700, 450);
		
		addPanelButton(true, buttonPagar, buttonAtualizar, EnumMkButton.ABRIR.getButton(this), EnumMkButton.NOVO.getButton(this));
		
		fieldCliente.setEditable(false);
		labelSaldo = new JLabel();
		this.panelButton.add(labelSaldo, 1);
		
		buttonPagar.setText("Pagar Itens Selecionados");
		buttonPagar.onClick(pagarSelecionados());
		
		table.onDoubleClickOrEnter(abrir());
		
		pesquisar();
		
	}
	
	protected MkRun abrir() {
		return abrirFinanceiro(false);
	}
	
	protected MkRun novo() {
		return abrirFinanceiro(true);
	}
	
	protected void pesquisar() {
				logger.debug("Executando perquisar");
				
				fieldCliente.setText(cliente.getNome());
				
				Map<String, Object> where = new HashMap<String, Object>();
				where.put("cliente_id", cliente.getId());
				where.put("before_data_cadastro", new Date());
				
				try {
					List<Financeiro> list = financeiroService.findAll(where);
					table.setModel(new MkTableModel<Financeiro>(list, " ","Data", "Referência", "Valor", "Data Pagamento") {
						@Override
						protected Object getRow(Financeiro bean, int rowIndex, int columnIndex) {
							switch (columnIndex) {
							case 0:
								return bean.getPagar();
							case 1:
								return MkUtil.toString(bean.getDataCadastro());
							case 2:
								return bean.getReferencia();	
							case 3:
								return MkUtil.toString(bean.getValor());
							case 4:
								return MkUtil.toString(bean.getDataPagamento());	
							}
							return bean;
						}
						
						@Override
						public boolean isCellEditable(int row, int columnIndex) {
					        return (columnIndex == 0);
					    }

						@Override
						public void setValueAt(Object value, int rowIndex, int columnIndex) {
							if (value instanceof Boolean) {
								boolean checked = (Boolean) value;
								if (this.rows.get(rowIndex).getDataPagamento() == null) {
									this.rows.get(rowIndex).setPagar(checked);
								}
							}
						}
					});
					labelSaldo.setText("Saldo: "+MkUtil.toString(financeiroService.getSaldo(cliente)));
				} catch (Exception ex) {
					MkDialog.error("Erro ao pesquisar", ex);
				}
				table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				table.getColumnModel().getColumn(0).setPreferredWidth(40);
				table.getColumnModel().getColumn(1).setPreferredWidth(100);
				table.getColumnModel().getColumn(2).setPreferredWidth(300);
				table.getColumnModel().getColumn(3).setPreferredWidth(90);
				table.getColumnModel().getColumn(4).setPreferredWidth(120);

	}
	
	private MkRun abrirFinanceiro(final boolean isNew) {
		return new MkRun() {
			@Override
			public void execute() {
				Financeiro bean = (isNew ? new Financeiro() : table.getSeleted(true));
				if (bean !=null) {
					bean.setCliente(cliente);
					JanelaFinanceiroCadastro view = new JanelaFinanceiroCadastro(bean);
					view.showView("Cadastro Financeiro", false);
					if (isNew) {
						view.novo();
					}
				}
			}
		}; 
	}
	
	private MkRun pagarSelecionados() {
		return new MkRun() {
			@Override
			public void execute() {
				LinkedList<Financeiro> list = new LinkedList<Financeiro>();
				BigDecimal total = BigDecimal.ZERO;
				for (int i = 0; i < table.getRowCount(); i++) {
					Financeiro bean = (Financeiro) table.getModel().getValueAt(i, table.getColumnCount());
					if (bean.getPagar()) {
						list.add(bean);
						total = total.add(bean.getValor());
					}
				}
				if (list.size()==0) {
					MkDialog.info("Primeiro selecione o item a pagar");
				} else {
					String mensagem = " Total de itens: "+list.size()+" Total a pagar: "+MkUtil.toString(total);
					if (MkDialog.confirm("Marcar como pago itens selecionados?\n"+mensagem)) {
						try {
							logger.debug("Executando pagarSelecionados"+mensagem);
							financeiroService.pagar(list);
						} catch (MkServiceException ex) {
							logger.error("Erro Executando pagarSelecionados", ex);
							MkDialog.error(ex.getMessage(), ex);
						} finally {
							pesquisar();
						}
					}
				}
				
			}
		};
	}

}
