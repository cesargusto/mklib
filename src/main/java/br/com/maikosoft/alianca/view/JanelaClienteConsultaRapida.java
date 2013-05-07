package br.com.maikosoft.alianca.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JInternalFrame;

import org.springframework.util.StringUtils;

import br.com.maikosoft.alianca.ClienteAlianca;
import br.com.maikosoft.alianca.EnumMenuAlianca;
import br.com.maikosoft.alianca.service.ClienteService;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.core.MkServiceException;
import br.com.maikosoft.mklib.MkDialog;
import br.com.maikosoft.mklib.MkFieldText;
import br.com.maikosoft.mklib.MkPanelTable;
import br.com.maikosoft.mklib.MkWindow;

@SuppressWarnings("serial")
public class JanelaClienteConsultaRapida extends MkWindow {
	
	private MkFieldText fieldBusca;
	
	private ClienteService clienteService;

	@Override
	protected void initWindow() {
		
		MkPanelTable panelTableCliente = new MkPanelTable();
		panelTableCliente.setTitle("Cliente");
		panelTableCliente.addRow(fieldBusca);
		
		addPanelCenter(panelTableCliente, 200, 90);
		
		fieldBusca.setColumns(15);
		
		fieldBusca.onEnter(new MkRun() {
			@Override
			public void execute() {
				try {					
					if (StringUtils.hasText(fieldBusca.getText())) {
						Map<String, Object> where = new HashMap<String, Object>();
						where.put("nomeOrId", fieldBusca.getText());
						List<ClienteAlianca> list = clienteService.findAll(where);
						if (list.size() == 0) {
							MkDialog.info("Cliente não encontrado", application.getJMenuBar());
						} else if (list.size() == 1) {
							new JanelaClienteCadastro(list.get(0)).showWindow("Cadastro Cliente", false);
						} else {
							JanelaClienteConsulta janelaClienteConsulta = new JanelaClienteConsulta();
							janelaClienteConsulta.showWindow("Consulta Cliente", false);
							janelaClienteConsulta.setPesquisa(list);
						}
						fieldBusca.setText("");
					} else {
						EnumMenuAlianca.CADASTRO_CLIENTE_NOVO.getMenu().getAcao().execute();
					}
					
				} catch (MkServiceException exception) {
					MkDialog.error("Erro consulta rapida", exception);
				}
			}
		});
		
	}

	public void showWindow() {
		JInternalFrame view = (JInternalFrame) showWindow("Consulta Rápida", false);
		view.setLocation(0, 0);
		view.setMaximizable(false);
		view.setResizable(false);
		view.setClosable(false);
		view.setIconifiable(false);
		view.updateUI();
	}

}