package br.com.maikosoft.cadmia.view;

import java.awt.GridBagConstraints;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import br.com.maikosoft.cadmia.Cliente;
import br.com.maikosoft.cadmia.Financeiro;
import br.com.maikosoft.cadmia.service.FinanceiroService;
import br.com.maikosoft.layout.swing.EnumMkButton;
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
	
	private FinanceiroService financeiroService;
	private Cliente cliente;
	
	public JanelaFinanceiroConsulta(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	protected void initWindow() {
		
		panelCenter.addRow("Cliente", fieldCliente, EnumMkButton.PESQUISAR.getButton(this), GridBagConstraints.NONE);
		panelCenter.addRow(table.getJScrollPane(), GridBagConstraints.BOTH);
		
		addPanelCenter(panelCenter, 600, 450);
		
		addPanelButton(true, EnumMkButton.ABRIR.getButton(this), EnumMkButton.NOVO.getButton(this));
		
		table.onDoubleClickOrEnter(abrir());
		
		pesquisar().execute();
		
	}
	
	protected MkRun abrir() {
		return abrirFinanceiro(false);
	}
	
	protected MkRun pesquisar() {
		return new MkRun() {
			@Override
			public void execute() {
				logger.debug("Executando perquisar");
				
				fieldCliente.setText(cliente.getNome());
				
				Map<String, Object> where = new HashMap<String, Object>();
				where.put("cliente_id", cliente.getId());
				where.put("before_data_cadastro", new Date());
				
				try {
					List<Financeiro> list = financeiroService.findAll(where);
					table.setModel(new MkTableModel<Financeiro>(list, "Data", "Referência", "Valor", "Data Pagamento") {
						@Override
						protected Object getRow(Financeiro bean, int rowIndex, int columnIndex) {
							switch (columnIndex) {
							case 0:
								return MkUtil.toString(bean.getDataCadastro());
							case 1:
								return bean.getReferencia();	
							case 2:
								return MkUtil.toString(bean.getValor());
							case 3:
								return MkUtil.toString(bean.getDataPagamento());	
							}
							return bean;
						}
					});
				} catch (Exception ex) {
					MkDialog.error("Erro ao pesquisar", ex);
				}
			}
		};
	}
	
	protected MkRun novo() {
		return abrirFinanceiro(true);
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

}
