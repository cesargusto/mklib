package br.com.maikosoft.bazar.view;

import java.awt.GridBagConstraints;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import br.com.maikosoft.bazar.ClienteBazar;
import br.com.maikosoft.bazar.Pedido;
import br.com.maikosoft.bazar.PedidoItem;
import br.com.maikosoft.bazar.Produto;
import br.com.maikosoft.bazar.service.PedidoService;
import br.com.maikosoft.bazar.service.ProdutoService;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.core.MkTransferObject;
import br.com.maikosoft.mklib.MkButton.MkButtonAdicionar;
import br.com.maikosoft.mklib.MkButton.MkButtonEditar;
import br.com.maikosoft.mklib.MkButton.MkButtonExcluir;
import br.com.maikosoft.mklib.MkButton.MkButtonImprimir;
import br.com.maikosoft.mklib.MkButton.MkButtonNovo;
import br.com.maikosoft.mklib.MkButton.MkButtonPesquisar;
import br.com.maikosoft.mklib.MkButton.MkButtonRemover;
import br.com.maikosoft.mklib.MkButton.MkButtonSalvar;
import br.com.maikosoft.mklib.MkDialog;
import br.com.maikosoft.mklib.MkFieldDate;
import br.com.maikosoft.mklib.MkFieldMask;
import br.com.maikosoft.mklib.MkFieldMask.EnumMkMask;
import br.com.maikosoft.mklib.MkFieldText;
import br.com.maikosoft.mklib.MkPanelTable;
import br.com.maikosoft.mklib.MkTable;
import br.com.maikosoft.mklib.MkTableModel;
import br.com.maikosoft.mklib.MkTextArea;
import br.com.maikosoft.mklib.MkWindow;
import br.com.maikosoft.util.Extenso;
import br.com.maikosoft.util.MkUtil;
import br.com.maikosoft.view.JanelaLogin;

@SuppressWarnings("serial")
public class JanelaPedidoCadastro extends MkWindow {
	
	private MkFieldText fieldId;
	private MkFieldText fieldCliente;
	private MkFieldDate fieldDataPedido;
	private JLabel labelTotal;
	private MkFieldMask fieldDesconto;
	private MkTextArea textObservacao;
	private MkButtonPesquisar buttonPesquisar;
	private JLabel labelSaldo;
	
	private MkTable<PedidoItem> tableItem;
	private MkFieldText fieldProduto;
	private MkButtonAdicionar buttonAdicionar;
	private MkButtonRemover buttonRemover;
	
	private MkButtonNovo buttonNovo;
	private MkButtonSalvar buttonSalvar;
	private MkButtonEditar buttonEditar;	
	private MkButtonExcluir buttonExcluir;
	private MkButtonImprimir buttonImprimir;
		
	private Pedido bean;
	private PedidoService pedidoService;
	private ProdutoService produtoService;
	
	public JanelaPedidoCadastro(Pedido bean) {
		this.bean = bean;
	}

	@Override
	protected void initWindow() {
		
		MkPanelTable panelTable = new MkPanelTable();
		panelTable.addRow("Codigo:",fieldId, "Data Pedido:", fieldDataPedido, "");
		panelTable.addRow("Cliente:",fieldCliente, MkPanelTable.getDefaultCell(4), buttonPesquisar, GridBagConstraints.NONE);
		
		MkPanelTable panelTableItem = new MkPanelTable();
		panelTableItem.setTitle("Itens");
		panelTableItem.addRow("Produto", fieldProduto, buttonAdicionar, GridBagConstraints.NONE, buttonRemover, GridBagConstraints.NONE);
		panelTableItem.addRow(tableItem.getJScrollPane(), GridBagConstraints.BOTH);
		
		panelTable.addRow(panelTableItem, GridBagConstraints.BOTH);
		
		panelTable.addRow("","","", "Total Produto:", labelTotal);
		panelTable.addRow("","","", "Desconto:", fieldDesconto );
		panelTable.addRow("","","", "Saldo:", labelSaldo);
		
		panelTable.addRow(textObservacao.getJScrollPane("Observação"), GridBagConstraints.BOTH);
		
		addPanelCenter(panelTable, 650, 550);
		fieldDesconto.setMask(EnumMkMask.CURRENCY);
		fieldId.setEnabled(false);
		fieldCliente.setEnabled(false);
		fieldDesconto.setColumns(8);
		fieldDesconto.onChange(desconto());
		fieldProduto.onEnter(enterFieldProduto());
	
		//addPanelButton(true, buttonNovo, buttonSalvar, buttonEditar, buttonImprimir, buttonExcluir);
		addPanelButton(true, buttonNovo, buttonSalvar, buttonImprimir);
		
		beanToForm(false);
	}
	
	public void novo() {
		bean = new Pedido();
		bean.setDataPedido(new Date());
		bean.setDesconto(BigDecimal.ZERO);
		beanToForm(true);
		buttonPesquisar.grabFocus();
	}

	protected void editar() {
		beanToForm(true);
		if (!JanelaLogin.getInstance().getUsuarioLogado().isAdministrador()) {
			fieldDataPedido.setEditable(false);
			fieldDesconto.setEditable(false);
		}
	}

	protected void salvar() {
		try {
			bean.setDataPedido(fieldDataPedido.getDate());
//			bean.setTotal(MkUtil.toBigDecimal(labelTotal.getText()));
			bean.setDesconto(MkUtil.toBigDecimal(fieldDesconto.getText()));
			bean.setObservacao(textObservacao.getText());
						
			if (bean.getId() == null) {
				bean.setOwner(JanelaLogin.getInstance().getUsuarioLogado().getId());
				pedidoService.insert(bean);
				imprimir();
			} else {
				pedidoService.update(bean);
			}
			MkDialog.info("Pedido salvo com sucesso", buttonSalvar);

			//bean = pedidoService.findById(bean.getId());
			beanToForm(false);
			application.refreshWindows();

		} catch (Exception ex) {
			MkDialog.error(ex.getMessage(), ex);
		}
	}

	protected void excluir() {
		if (JanelaLogin.getInstance().getUsuarioLogado().isAdministrador()) {
			if (MkDialog.confirm("Deseja excluir esse registro?")) {
				try {
					pedidoService.delete(bean.getId());
					closeWindow();
					application.refreshWindows();
				} catch (Exception ex) {
					MkDialog.error(ex.getMessage(), ex);
				}
			}
		} else {
			MkDialog.warm("Acesso Negado");
		}
	}
	
	
	private void beanToForm(boolean isEditMode) {
		if (bean.getId() == null) {
			fieldId.setText("NOVO");
		} else {
			fieldId.setText(bean.getId()+"");
		}
		if (bean.getCliente() != null) {
			fieldCliente.setText(bean.getCliente().getNome());
		}
		fieldDataPedido.setDate(bean.getDataPedido());
		labelTotal.setText(MkUtil.toString(bean.getTotal()));
		fieldDesconto.setValue(bean.getDesconto());
		textObservacao.setText(bean.getObservacao());
		labelSaldo.setText(MkUtil.toString(bean.getSaldo()));
		
		fieldDataPedido.setEditable(isEditMode);
		fieldDesconto.setEditable(isEditMode);
		textObservacao.setEditable(isEditMode);
		
		buttonNovo.setEnabled(!isEditMode);
		buttonSalvar.setEnabled(isEditMode);
		buttonEditar.setEnabled(!isEditMode);
		buttonExcluir.setEnabled(!isEditMode);
		
		atualizaTableItemESaldo();
		
	}
	
	private MkTransferObject<ClienteBazar> getTransferObject() {
		MkTransferObject<ClienteBazar> transferObject = new MkTransferObject<ClienteBazar>() {
			@Override
			public void postTranfer(ClienteBazar value) {
				bean.setCliente(value);
				fieldCliente.setText(value.getNome());
				fieldProduto.requestFocusInWindow();
			}
		};
		return transferObject;
	}
	
	protected void pesquisar() {
		JanelaClienteConsulta janelaConsulta = new JanelaClienteConsulta();
		janelaConsulta.setTranferir(getTransferObject());
		janelaConsulta.showWindow("Transferir Cliente", false);
	}
	
	protected void imprimir() {
		try {
			if (bean.getCliente() == null) {
				MkDialog.warm("Informe o Cliente");
				buttonPesquisar.grabFocus();
			} else {
					this.waitCursor(true);
					
					HashMap<String,Object> map = new HashMap<String, Object>();
					map.put("clienteNome", bean.getCliente().getNome());
					map.put("dataPedido", bean.getDataPedido());
					map.put("total", bean.getTotal());
					map.put("desconto", bean.getDesconto());
					map.put("saldo", bean.getSaldo());
					map.put("saldoExtenso", new Extenso(bean.getSaldo(), true));
										
					InputStream streamResource = JanelaPedidoCadastro.class.getClassLoader().getResourceAsStream("report/bazar/Pedido.jasper");
					JasperPrint print = JasperFillManager.fillReport(streamResource, map, new JRBeanCollectionDataSource(bean.getListPedidoItem()));
					JasperViewer.viewReport(print, false);
					JasperPrintManager.printReport(print, true);
					
					
			}
		} catch (Exception ex) {
			MkDialog.error("Erro ao imprimir pedido", ex);
		} finally {
			this.waitCursor(false);
		}
			
	}
	
	private void atualizaTableItemESaldo() {
		
		List<PedidoItem> list = bean.getListPedidoItem();
		
		tableItem.setModel(new MkTableModel<PedidoItem>(list, "Produto", "Qtd", "Valor") {
			@Override
			protected Object getRow(PedidoItem bean, int rowIndex, int columnIndex) {
				switch (columnIndex) {
				case 0:
					return bean.getProduto();	
				case 1:
					return bean.getQuantidade();
				case 2:
					return MkUtil.toString(bean.getValor());	
				}
				return bean;
			}
		});
		
		BigDecimal total = BigDecimal.ZERO; 
		
		if (list.size() >0 ) {
			tableItem.getColumnModel().getColumn(0).setPreferredWidth(400);
			tableItem.getColumnModel().getColumn(1).setPreferredWidth(80);
			tableItem.getColumnModel().getColumn(2).setPreferredWidth(100);
			for (PedidoItem pedidoItem : list) {
				total = total.add(pedidoItem.getValor().multiply(new BigDecimal(pedidoItem.getQuantidade())));
			}
		}
		bean.setTotal(total);
		labelTotal.setText(MkUtil.toString(total));
		labelSaldo.setText(MkUtil.toString(bean.getSaldo()));
	}
	
	private void adicionaProduto(Produto produto) {
		JanelaQuantidadeItem janelaQuantidade = new JanelaQuantidadeItem();
		janelaQuantidade.showWindow("Quantidade", true);
		PedidoItem pedidoItem = new PedidoItem();
		pedidoItem.setProduto(produto);
		pedidoItem.setValor(produto.getValor());
		pedidoItem.setQuantidade(janelaQuantidade.getQuantidade());
		bean.getListPedidoItem().add(pedidoItem);
		atualizaTableItemESaldo();
	}
	
	protected void adicionar() {
		MkTransferObject<Produto> transferObject = new MkTransferObject<Produto>() {
			@Override
			public void postTranfer(Produto produto) {
				adicionaProduto(produto);
			}
		};
		JanelaProdutoConsulta janelaProdutoConsulta = new JanelaProdutoConsulta();
		janelaProdutoConsulta.setTranferir(transferObject);
		janelaProdutoConsulta.showWindow("Transferir Produto", false);
	}
	
	protected MkRun enterFieldProduto() {
		return new MkRun() {
			@Override
			public void execute() {				
				Map<String, Object> where = new HashMap<String, Object>();
				where.put("nomeOrId", fieldProduto.getText());
				try {
					List<Produto> list = produtoService.findAll(where);
					if (list.size() == 1) {
						Produto produto = list.get(0);						
						adicionaProduto(produto);
					} else {
						MkDialog.info("Produto não cadastrado", fieldProduto);
					}
					fieldProduto.setText("");
					fieldProduto.requestFocusInWindow();
					
				} catch (Exception ex) {
					MkDialog.error("Erro ao pesquisar produto", ex);
				}
				
				
				
			}
		};
	}
	
	protected void remover() {
		
		PedidoItem pedidoItem = tableItem.getSeleted(true);
		if (pedidoItem !=null) {
			bean.getListPedidoItem().remove(pedidoItem);
			atualizaTableItemESaldo();
			MkDialog.info("Produto removido com sucesso", buttonRemover);
		}
	}
	
	protected MkRun desconto() {
		return new MkRun() {
			@Override
			public void execute() {
				bean.setDesconto(MkUtil.toBigDecimal(fieldDesconto.getText()));
				atualizaTableItemESaldo();
			}
		};
	}
			
}
