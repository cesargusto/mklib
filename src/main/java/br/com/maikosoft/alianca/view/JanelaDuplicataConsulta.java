package br.com.maikosoft.alianca.view;

import java.awt.GridBagConstraints;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import br.com.maikosoft.alianca.Duplicata;
import br.com.maikosoft.alianca.EnumMenuAlianca;
import br.com.maikosoft.alianca.service.DuplicataService;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.mklib.EnumMkButton;
import br.com.maikosoft.mklib.MkDialog;
import br.com.maikosoft.mklib.MkFieldDate;
import br.com.maikosoft.mklib.MkPanelTable;
import br.com.maikosoft.mklib.MkTable;
import br.com.maikosoft.mklib.MkTableModel;
import br.com.maikosoft.mklib.MkWindow;
import br.com.maikosoft.util.MkUtil;

@SuppressWarnings("serial")
public class JanelaDuplicataConsulta extends MkWindow {
	
	private static final Logger logger = Logger.getLogger(JanelaDuplicataConsulta.class);
	
	private MkFieldDate fieldDataIncial;
	private MkFieldDate fieldDataFinal;
	private MkPanelTable panelCenter;
	private MkTable<Duplicata> table;
	
	private DuplicataService duplicataService;
	
	@Override
	protected void initWindow() {
		
		panelCenter.addRow("Data Vencimento Inicial", fieldDataIncial, "Data Vencimento Final", fieldDataFinal, EnumMkButton.PESQUISAR.getButton(this), GridBagConstraints.NONE);
		panelCenter.addRow(table.getJScrollPane(), GridBagConstraints.BOTH);
		
		addPanelCenter(panelCenter, 750, 450);
		
		addPanelButton(true, EnumMkButton.ABRIR.getButton(this), EnumMkButton.NOVO.getButton(this));
		
		table.onDoubleClickOrEnter(abrir());
		
	}
	
	protected MkRun abrir() {
		return new MkRun() {
			@Override
			public void execute() {
				Duplicata bean = table.getSeleted(true);
				if (bean !=null) {
					JanelaDuplicataCadastro view = new JanelaDuplicataCadastro(bean);
					view.showWindow("Cadastro Duplicata", false);					
				}
			}
		}; 
	}
	
	public void setPesquisa(List<Duplicata> list) {
		table.setModel(new MkTableModel<Duplicata>(list, "Ref.", "Cliente", "Valor", "Vencimento", "Pago") {
			@Override
			protected Object getRow(Duplicata bean, int rowIndex, int columnIndex) {
				switch (columnIndex) {
				case 0:
					return bean.getReferencia();
				case 1:
					return bean.getClienteAlianca().getNome();	
				case 2:
					return MkUtil.toString(bean.getValor());
				case 3:
					return MkUtil.toString(bean.getDataVencimento());	
				default:
					return bean.isPago();
				}
			}
		});
		if (list.size() >0 ) {
			table.requestFocusInWindow();
			table.getColumnModel().getColumn(0).setPreferredWidth(60);
			table.getColumnModel().getColumn(1).setPreferredWidth(300);
			table.getColumnModel().getColumn(2).setPreferredWidth(90);
			table.getColumnModel().getColumn(3).setPreferredWidth(60);
			table.getColumnModel().getColumn(4).setPreferredWidth(30);
		}
		
	}
	
	protected MkRun pesquisar() {
		return new MkRun() {
			@Override
			public void execute() {
				try {
					logger.debug("Executando perquisar");
					Map<String, Object> where = new HashMap<String, Object>();
					where.put("before_data_vencimento", fieldDataFinal.getDate());
					where.put("after_data_vencimento", fieldDataIncial.getDate());
				
					List<Duplicata> list = duplicataService.findAll(where);
					setPesquisa(list);
					
				} catch (Exception ex) {
					MkDialog.error("Erro ao pesquisar", ex);
				}
			}
		};
	}
	
	protected MkRun novo() {
		return EnumMenuAlianca.CADASTRO_RECEITA_NOVO.getMenu().getAcao();
	}

	@Override
	public void refreshWindow() {
		pesquisar().execute();
	}
	
	

}
