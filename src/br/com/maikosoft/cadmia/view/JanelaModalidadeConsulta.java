package br.com.maikosoft.cadmia.view;

import java.awt.GridBagConstraints;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import br.com.maikosoft.cadmia.Modalidade;
import br.com.maikosoft.cadmia.EnumMenu;
import br.com.maikosoft.cadmia.service.ModalidadeService;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.core.MkTransferObject;
import br.com.maikosoft.core.MkUtil;
import br.com.maikosoft.layout.swing.EnumMkButton;
import br.com.maikosoft.layout.swing.MkButton.MkButtonTransferir;
import br.com.maikosoft.layout.swing.MkDialog;
import br.com.maikosoft.layout.swing.MkFieldText;
import br.com.maikosoft.layout.swing.MkPanelTable;
import br.com.maikosoft.layout.swing.MkTable;
import br.com.maikosoft.layout.swing.MkTableModel;
import br.com.maikosoft.layout.swing.MkWindow;

@SuppressWarnings("serial")
public class JanelaModalidadeConsulta extends MkWindow {
	
	private static final Logger logger = Logger.getLogger(JanelaModalidadeConsulta.class);
	
	private MkFieldText fieldBusca;
	private MkPanelTable panelCenter;
	private MkTable<Modalidade> table;
	private MkButtonTransferir buttonTransferir;
	
	private ModalidadeService modalidadeService;
	
	private MkTransferObject<Modalidade> transferObject;
	
	@Override
	protected void initWindow() {
		
		panelCenter.addRow("Busca", fieldBusca, EnumMkButton.PESQUISAR.getButton(this), GridBagConstraints.NONE);
		panelCenter.addRow(table.getJScrollPane(), GridBagConstraints.BOTH);
		
		addPanelCenter(panelCenter, 500, 450);
		
		addPanelButton(true, buttonTransferir, EnumMkButton.ABRIR.getButton(this), EnumMkButton.NOVO.getButton(this));
		
		fieldBusca.onEnter(pesquisar());		
		table.onDoubleClickOrEnter((transferObject==null ? abrir() : new MkRun() {
			@Override
			public void execute() {
				transferir();
			}
		}));
		
		buttonTransferir.setVisible((transferObject!=null));
		
	}
	
	protected MkRun abrir() {
		return new MkRun() {
			@Override
			public void execute() {
				Modalidade bean = table.getSeleted(true);
				if (bean !=null) {
					JanelaModalidadeCadastro view = new JanelaModalidadeCadastro(bean);
					view.showWindow("Cadastro Modalidade", false);					
				}
			}
		}; 
	}
	
	protected MkRun pesquisar() {
		return new MkRun() {
			@Override
			public void execute() {
				logger.debug("Executando perquisar");
				Map<String, Object> where = new HashMap<String, Object>();
				where.put("nomeOrId", fieldBusca.getText());
				try {
					List<Modalidade> list = modalidadeService.findAll(where);
					table.setModel(new MkTableModel<Modalidade>(list, "Nome", "Valor") {
						@Override
						protected Object getRow(Modalidade bean, int rowIndex, int columnIndex) {
							switch (columnIndex) {
							case 1:
								return MkUtil.toString(bean.getValor());
							default:
								return bean.getNome();
							}
						}
					});
				} catch (Exception ex) {
					MkDialog.error("Erro ao pesquisar", ex);
				}
			}
		};
	}
	
	protected MkRun novo() {
		return EnumMenu.CADASTRO_MODALIDADE_NOVO.getMenu().getAcao();
	}

	public void setTranferir(MkTransferObject<Modalidade> transferObject) {
		this.transferObject = transferObject;
	}
	
	protected void transferir() {
		Modalidade seleted = table.getSeleted(true);
		if (seleted != null) {
			transferObject.postTranfer(seleted);
			fecharJanela();
		}
	}

}
