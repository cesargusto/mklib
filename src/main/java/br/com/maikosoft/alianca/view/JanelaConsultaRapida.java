package br.com.maikosoft.alianca.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JInternalFrame;

import org.springframework.util.StringUtils;

import br.com.maikosoft.alianca.ClienteAlianca;
import br.com.maikosoft.alianca.EnumMenuAlianca;
import br.com.maikosoft.alianca.Receita;
import br.com.maikosoft.alianca.service.ClienteService;
import br.com.maikosoft.alianca.service.ReceitaService;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.core.MkServiceException;
import br.com.maikosoft.mklib.MkDialog;
import br.com.maikosoft.mklib.MkFieldText;
import br.com.maikosoft.mklib.MkPanelTable;
import br.com.maikosoft.mklib.MkWindow;

@SuppressWarnings("serial")
public class JanelaConsultaRapida extends MkWindow {
	
	private MkFieldText fieldBuscaCliente;
	private MkFieldText fieldBuscaReceita;
	
	private ClienteService clienteService;
	private ReceitaService receitaService;

	@Override
	protected void initWindow() {
		
		MkPanelTable panelTableCliente = new MkPanelTable();
		panelTableCliente.setTitle("Cliente");
		panelTableCliente.addRow(fieldBuscaCliente);
		
		MkPanelTable panelTableReceita = new MkPanelTable();
		panelTableReceita.setTitle("Receita");
		panelTableReceita.addRow(fieldBuscaReceita);
		
		addPanelCenter(new MkPanelTable().addRow(panelTableCliente).addRow(panelTableReceita), 200, 120);
		
		fieldBuscaCliente.setColumns(15);
		fieldBuscaReceita.setColumns(15);
		
		fieldBuscaCliente.onEnter(buscaCliente());
		
		fieldBuscaReceita.onEnter(buscaReceita());
		
	}

	private MkRun buscaCliente() {
		return new MkRun() {
			@Override
			public void execute() {
				try {					
					if (StringUtils.hasText(fieldBuscaCliente.getText())) {
						Map<String, Object> where = new HashMap<String, Object>();
						where.put("nomeOrId", fieldBuscaCliente.getText());
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
						fieldBuscaCliente.setText("");
					} else {
						EnumMenuAlianca.CADASTRO_CLIENTE_NOVO.getMenu().getAcao().execute();
					}
					
				} catch (MkServiceException exception) {
					MkDialog.error("Erro consulta rapida", exception);
				}
			}
		};
	}
	
	private MkRun buscaReceita() {
		return new MkRun() {
			@Override
			public void execute() {
				try {					
					if (StringUtils.hasText(fieldBuscaReceita.getText())) {
						Map<String, Object> where = new HashMap<String, Object>();
						where.put("nomeOrId", fieldBuscaReceita.getText());
						List<Receita> list = receitaService.findAll(where);
						if (list.size() == 0) {
							MkDialog.info("Receita não encontrada", application.getJMenuBar());
						} else if (list.size() == 1) {
							new JanelaReceitaCadastro(list.get(0)).showWindow("Cadastro Receita", false);
						} else {
							JanelaReceitaConsulta janelaReceitaConsulta = new JanelaReceitaConsulta();
							janelaReceitaConsulta.showWindow("Consulta Receita", false);
							janelaReceitaConsulta.setPesquisa(list);
						}
						fieldBuscaReceita.setText("");
					} else {
						EnumMenuAlianca.CADASTRO_RECEITA_NOVO.getMenu().getAcao().execute();
					}
					
				} catch (MkServiceException exception) {
					MkDialog.error("Erro consulta rapida", exception);
				}
			}
		};
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
