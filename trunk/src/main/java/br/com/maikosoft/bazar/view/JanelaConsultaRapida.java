package br.com.maikosoft.bazar.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JInternalFrame;

import org.springframework.util.StringUtils;

import br.com.maikosoft.bazar.EnumMenuBazar;
import br.com.maikosoft.bazar.Produto;
import br.com.maikosoft.bazar.service.ProdutoService;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.core.MkServiceException;
import br.com.maikosoft.mklib.MkButton.MkButtonNovo;
import br.com.maikosoft.mklib.MkDialog;
import br.com.maikosoft.mklib.MkFieldText;
import br.com.maikosoft.mklib.MkPanelTable;
import br.com.maikosoft.mklib.MkWindow;

@SuppressWarnings("serial")
public class JanelaConsultaRapida extends MkWindow {
	
	private MkFieldText fieldBusca;
	private MkButtonNovo buttonNovo;
	
	private ProdutoService produtoService;

	@Override
	protected void initWindow() {
		
		MkPanelTable panelTable = new MkPanelTable();
		panelTable.setTitle("Produto");
		panelTable.addRow(fieldBusca);
		panelTable.addRow(buttonNovo);
		
		addPanelCenter(panelTable, 200, 80);
		
		buttonNovo.setText("Nova Venda");
		buttonNovo.onClick(EnumMenuBazar.CADASTRO_PEDIDO_CADASTRAR.getMenu().getAcao());
		
		fieldBusca.setColumns(15);
		
		fieldBusca.onEnter(new MkRun() {
			@Override
			public void execute() {
				try {					
					if (StringUtils.hasText(fieldBusca.getText())) {
						Map<String, Object> where = new HashMap<String, Object>();
						where.put("nomeOrId", fieldBusca.getText());
						List<Produto> list = produtoService.findAll(where);
						if (list.size() == 0) {
							MkDialog.info("Produto não encontrado", application.getJMenuBar());
						} else if (list.size() == 1) {
							new JanelaProdutoCadastro(list.get(0)).showWindow("Cadastro Produto", false);
						} else {
							JanelaProdutoConsulta janelaProdutoConsulta = new JanelaProdutoConsulta();
							janelaProdutoConsulta.showWindow("Consulta Produto", false);
							janelaProdutoConsulta.setPesquisa(list);
						}
						fieldBusca.setText("");
					} else {
						EnumMenuBazar.CADASTRO_PRODUTO_NOVO.getMenu().getAcao().execute();
					}
					
				} catch (MkServiceException exception) {
					MkDialog.error("Erro consulta rapida", exception);
				}
			}
		});
		
	}

	public void showWindow() {
		JInternalFrame view = (JInternalFrame) showWindow("Menu Rápido", false);
		view.setLocation(0, 0);
		view.setMaximizable(false);
		view.setResizable(false);
		view.setClosable(false);
		view.setIconifiable(false);
		view.updateUI();
	}

}
