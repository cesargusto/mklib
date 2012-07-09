package br.com.maikosoft.cadmia.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JInternalFrame;

import org.springframework.util.StringUtils;

import br.com.maikosoft.cadmia.Cliente;
import br.com.maikosoft.cadmia.EnumMenu;
import br.com.maikosoft.cadmia.service.ClienteService;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.core.MkServiceException;
import br.com.maikosoft.layout.swing.MkDialog;
import br.com.maikosoft.layout.swing.MkFieldText;
import br.com.maikosoft.layout.swing.MkWindow;

@SuppressWarnings("serial")
public class JanelaClienteConsultaRapida extends MkWindow {
	
	private MkFieldText fieldBusca;
	
	private ClienteService clienteService;

	@Override
	protected void initWindow() {
		
		addPanelCenter(fieldBusca, 200, 30);
		
		fieldBusca.setColumns(15);
		
		fieldBusca.onEnter(new MkRun() {
			@Override
			public void execute() {
				try {					
					if (StringUtils.hasText(fieldBusca.getText())) {
						Map<String, Object> where = new HashMap<String, Object>();
						where.put("nomeOrId", fieldBusca.getText());
						List<Cliente> list = clienteService.findAll(where);
						if (list.size() == 0) {
							MkDialog.warm("Cliente não encontrado");
						} else {
							new JanelaClienteCadastro(list.get(0)).showWindow("Cadastro Cliente", false);
						}
						fieldBusca.setText("");
					} else {
						EnumMenu.CADASTRO_CLIENTE_NOVO.getMenu().getAcao().execute();
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
		view.setClosable(false);
		view.setIconifiable(false);
		view.updateUI();
	}

}
