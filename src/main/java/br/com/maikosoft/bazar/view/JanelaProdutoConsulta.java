package br.com.maikosoft.bazar.view;

import java.awt.GridBagConstraints;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import br.com.maikosoft.bazar.EnumMenuBazar;
import br.com.maikosoft.bazar.Produto;
import br.com.maikosoft.bazar.service.ProdutoService;
import br.com.maikosoft.core.MkTransferObject;
import br.com.maikosoft.mklib.MkButton.MkButtonAbrir;
import br.com.maikosoft.mklib.MkButton.MkButtonNovo;
import br.com.maikosoft.mklib.MkButton.MkButtonPesquisar;
import br.com.maikosoft.mklib.MkButton.MkButtonTransferir;
import br.com.maikosoft.mklib.MkDialog;
import br.com.maikosoft.mklib.MkFieldText;
import br.com.maikosoft.mklib.MkPanelTable;
import br.com.maikosoft.mklib.MkTable;
import br.com.maikosoft.mklib.MkTableModel;
import br.com.maikosoft.mklib.MkWindow;
import br.com.maikosoft.util.MkUtil;

@SuppressWarnings("serial")
public class JanelaProdutoConsulta extends MkWindow {
	
	private static final Logger logger = Logger.getLogger(JanelaProdutoConsulta.class);
	
	private MkFieldText fieldBusca;
	private MkPanelTable panelCenter;
	private MkTable<Produto> table;
	private MkButtonTransferir buttonTransferir;
	private MkButtonNovo buttonNovo;
	private MkButtonAbrir buttonAbrir;
	private MkButtonPesquisar buttonPesquisar;
	
	private MkTransferObject<Produto> transferObject;
	private ProdutoService produtoService;
	
	@Override
	protected void initWindow() {
		
		panelCenter.addRow("Busca", fieldBusca, buttonPesquisar, GridBagConstraints.NONE);
		panelCenter.addRow(table.getJScrollPane(), GridBagConstraints.BOTH);
		
		addPanelCenter(panelCenter, 600, 450);
		
		addPanelButton(true, buttonTransferir, buttonAbrir, buttonNovo);
		
		fieldBusca.onEnter(buttonPesquisar.getOnClick());
		table.onDoubleClickOrEnter((transferObject==null ? buttonAbrir.getOnClick() : buttonTransferir.getOnClick()));
		
		buttonTransferir.setVisible((transferObject!=null));
		
	}
	
	protected void abrir() {
		Produto bean = table.getSeleted(true);
		if (bean != null) {
			JanelaProdutoCadastro view = new JanelaProdutoCadastro(bean);
			view.showWindow("Cadastro Produto", false);
		}

	}

	protected void pesquisar() {
		logger.debug("Executando perquisar");
		Map<String, Object> where = new HashMap<String, Object>();
		where.put("nomeOrId", fieldBusca.getText());
		try {
			List<Produto> list = produtoService.findAll(where);
			setPesquisa(list);
		} catch (Exception ex) {
			MkDialog.error("Erro ao pesquisar", ex);
		}
	}
	
	protected void novo() {
		EnumMenuBazar.CADASTRO_PRODUTO_NOVO.getMenu().getAcao().execute();
	}

	public void setPesquisa(List<Produto> list) {
		table.setModel(new MkTableModel<Produto>(list, "Nome", "Valor") {
			@Override
			protected Object getRow(Produto bean, int rowIndex, int columnIndex) {
				switch (columnIndex) {
				case 0:
					return bean.getNome();	
				case 1:
					return MkUtil.toString(bean.getValor());	
				}
				return bean;
			}
		});
		if (list.size() >0 ) {
			table.getColumnModel().getColumn(0).setPreferredWidth(400);
			table.getColumnModel().getColumn(1).setPreferredWidth(100);
			table.getColumnModel().getColumn(1).setCellRenderer(MkTable.getRightRenderer());
			table.requestFocusInWindow();
		}
	}
	
	public void setTranferir(MkTransferObject<Produto> transferObject) {
		this.transferObject = transferObject;
	}
	
	protected void transferir() {
		Produto seleted = table.getSeleted(true);
		if (seleted != null) {
			closeWindow();
			transferObject.postTranfer(seleted);
		}
	}

	@Override
	public void refreshWindow() {
		pesquisar();
	}
	
	

}
