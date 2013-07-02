package br.com.maikosoft.alianca.view;

import java.awt.GridBagConstraints;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import br.com.maikosoft.alianca.EnumMenuAlianca;
import br.com.maikosoft.alianca.Receita;
import br.com.maikosoft.alianca.service.ReceitaService;
import br.com.maikosoft.mklib.MkButton.MkButtonAbrir;
import br.com.maikosoft.mklib.MkButton.MkButtonNovo;
import br.com.maikosoft.mklib.MkButton.MkButtonPesquisar;
import br.com.maikosoft.mklib.MkDialog;
import br.com.maikosoft.mklib.MkFieldText;
import br.com.maikosoft.mklib.MkPanelTable;
import br.com.maikosoft.mklib.MkTable;
import br.com.maikosoft.mklib.MkTableModel;
import br.com.maikosoft.mklib.MkWindow;
import br.com.maikosoft.util.MkUtil;

@SuppressWarnings("serial")
public class JanelaReceitaConsulta extends MkWindow {
	
	private static final Logger logger = Logger.getLogger(JanelaReceitaConsulta.class);
	
	private MkFieldText fieldBusca;
	private MkPanelTable panelCenter;
	private MkTable<Receita> table;
	private MkButtonNovo buttonNovo;
	private MkButtonAbrir buttonAbrir;
	private MkButtonPesquisar buttonPesquisar;
	
	private ReceitaService receitaService;
	
	@Override
	protected void initWindow() {
		
		panelCenter.addRow("Busca", fieldBusca, buttonPesquisar, GridBagConstraints.NONE);
		panelCenter.addRow(table.getJScrollPane(), GridBagConstraints.BOTH);
		
		addPanelCenter(panelCenter, 700, 450);
		
		addPanelButton(true, buttonAbrir, buttonNovo);
		
		fieldBusca.onEnter(buttonPesquisar.getOnClick());
		table.onDoubleClickOrEnter(buttonAbrir.getOnClick());
		
	}
	
	protected void abrir() {
		Receita bean = table.getSeleted(true);
		if (bean != null) {
			JanelaReceitaCadastro view = new JanelaReceitaCadastro(bean);
			view.showWindow("Cadastro Receita", false);
		}
	}
	
	public void setPesquisa(List<Receita> list) {
		table.setModel(new MkTableModel<Receita>(list, "Data Receita", "Nome", "Telefone") {
			@Override
			protected Object getRow(Receita bean, int rowIndex, int columnIndex) {
				switch (columnIndex) {
				case 0:
					return MkUtil.toString(bean.getDataReceita());
				case 2:
					return bean.getTelefone();
				default:
					return bean.getCliente();
				}
			}
		});
		if (list.size() >0 ) {
			table.requestFocusInWindow();
			table.getColumnModel().getColumn(0).setPreferredWidth(90);
			table.getColumnModel().getColumn(1).setPreferredWidth(300);
			table.getColumnModel().getColumn(2).setPreferredWidth(120);
		}
		
	}
	
	protected void pesquisar() {
		logger.debug("Executando perquisar");
		Map<String, Object> where = new HashMap<String, Object>();
		where.put("nomeOrId", fieldBusca.getText());
		try {
			List<Receita> list = receitaService.findAll(where);
			setPesquisa(list);

		} catch (Exception ex) {
			MkDialog.error("Erro ao pesquisar", ex);
		}
	}
	
	protected void novo() {
		EnumMenuAlianca.CADASTRO_RECEITA_NOVO.getMenu().getAcao().execute();
	}

	@Override
	public void refreshWindow() {
		pesquisar();
	}
	
	

}
