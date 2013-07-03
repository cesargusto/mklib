package br.com.maikosoft.bazar.view;

import java.awt.GridBagConstraints;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import br.com.maikosoft.bazar.EnumMenuBazar;
import br.com.maikosoft.bazar.Pedido;
import br.com.maikosoft.bazar.service.PedidoService;
import br.com.maikosoft.mklib.MkButton.MkButtonAbrir;
import br.com.maikosoft.mklib.MkButton.MkButtonNovo;
import br.com.maikosoft.mklib.MkDialog;
import br.com.maikosoft.mklib.MkPanelTable;
import br.com.maikosoft.mklib.MkTable;
import br.com.maikosoft.mklib.MkTableModel;
import br.com.maikosoft.mklib.MkWindow;
import br.com.maikosoft.util.MkUtil;

@SuppressWarnings("serial")
public class JanelaPedidoConsulta extends MkWindow {
	
	private static final Logger logger = Logger.getLogger(JanelaPedidoConsulta.class);
	
//	private MkFieldText fieldBusca;
	private MkPanelTable panelCenter;
	private MkTable<Pedido> table;
	private MkButtonNovo buttonNovo;
	private MkButtonAbrir buttonAbrir;
	
	private PedidoService pedidoService;
	
	@Override
	protected void initWindow() {
		
//		panelCenter.addRow("Busca", fieldBusca, EnumMkButton.PESQUISAR.getButton(this), GridBagConstraints.NONE);
		panelCenter.addRow(table.getJScrollPane(), GridBagConstraints.BOTH);
		
		addPanelCenter(panelCenter, 600, 450);
		
		addPanelButton(true, buttonAbrir, buttonNovo);
		
//		fieldBusca.onEnter(pesquisar());
		table.onDoubleClickOrEnter(buttonAbrir.getOnClick());
		
		pesquisar();
		
	}
	
	protected void abrir() {
		Pedido bean = table.getSeleted(true);
		if (bean !=null) {
			JanelaPedidoCadastro view = new JanelaPedidoCadastro(bean);
			view.showWindow("Cadastro Pedido", false);					
		}
	}
	
	protected void pesquisar() {
		logger.debug("Executando perquisar");
		Map<String, Object> where = new HashMap<String, Object>();
		try {
			List<Pedido> list = pedidoService.findAll(where);
			setPesquisa(list);
		} catch (Exception ex) {
			MkDialog.error("Erro ao pesquisar", ex);
		}
	}
	
	protected void novo() {
		EnumMenuBazar.CADASTRO_PEDIDO_CADASTRAR.getMenu().getAcao().execute();
	}

	public void setPesquisa(List<Pedido> list) {
		table.setModel(new MkTableModel<Pedido>(list, "Id","Cliente", "Saldo") {
			@Override
			protected Object getRow(Pedido bean, int rowIndex, int columnIndex) {
				switch (columnIndex) {
				case 0:
					return bean.getId();	
				case 1:
					return bean.getCliente().getNome();
				case 2:
					return MkUtil.toString(bean.getSaldo());	
				}
				return bean;
			}
		});
		if (list.size() > 0 ) {
			table.getColumnModel().getColumn(0).setPreferredWidth(80);
			table.getColumnModel().getColumn(1).setPreferredWidth(400);
			table.getColumnModel().getColumn(2).setPreferredWidth(100);
			table.getColumnModel().getColumn(2).setCellRenderer(MkTable.getRightRenderer());
			table.requestFocusInWindow();
		}
	}
	
	@Override
	public void refreshWindow() {
		pesquisar();
	}
	
	

}
