package br.com.maikosoft.bazar.view;

import java.awt.GridBagConstraints;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import br.com.maikosoft.bazar.ClienteBazar;
import br.com.maikosoft.bazar.service.ClienteService;
import br.com.maikosoft.core.MkException;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.mklib.MkButton.MkButtonEditar;
import br.com.maikosoft.mklib.MkButton.MkButtonExcluir;
import br.com.maikosoft.mklib.MkButton.MkButtonNovo;
import br.com.maikosoft.mklib.MkButton.MkButtonSalvar;
import br.com.maikosoft.mklib.MkComboBox;
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
import br.com.maikosoft.util.CEP;
import br.com.maikosoft.util.EnumUF;
import br.com.maikosoft.view.JanelaLogin;

@SuppressWarnings("serial")
public class JanelaClienteCadastro extends MkWindow {
	
	private MkFieldText fieldId;
	private MkFieldDate fieldDataNascimento;
	private MkFieldText fieldNome;
	private MkFieldMask fieldCpf;
	private MkFieldText fieldRg;
	private MkFieldText fieldEndereco;
	private MkFieldText fieldNumero;
	private MkFieldText fieldBairro;
	private MkFieldMask fieldCep;
	private MkFieldText fieldCidade;
	private MkComboBox<EnumUF> comboUf;
	private MkFieldText fieldTelefone1;
	private MkFieldText fieldTelefone2;
	private MkFieldText fieldTelefone3;
	private MkFieldText fieldEmail;
	private MkTextArea textObservacao;
		
	
	private MkButtonNovo buttonNovo;
	private MkButtonSalvar buttonSalvar;
	private MkButtonEditar buttonEditar;
	private MkButtonExcluir buttonExcluir;
		
	private ClienteBazar bean;
	private ClienteService clienteService;
	
	public JanelaClienteCadastro(ClienteBazar bean) {
		this.bean = (bean == null ? new ClienteBazar() : bean);
		
	}

	@Override
	protected void initWindow() {
		
		MkPanelTable panelTable = new MkPanelTable();
		panelTable.addRow("Nome:",fieldNome, MkPanelTable.getDefaultCell(3), "Código:", fieldId);		
		panelTable.addRow("CPF:", fieldCpf, "RG:", fieldRg, "Data Nacimento:", fieldDataNascimento, GridBagConstraints.NONE);
		panelTable.addRow("Endereço:", fieldEndereco, MkPanelTable.getDefaultCell(3), "Numero:", fieldNumero);
		panelTable.addRow("Bairro:", fieldBairro, "CEP:", fieldCep, "Cidade:", new MkPanelTable().addRow(fieldCidade, comboUf, GridBagConstraints.NONE));
		panelTable.addRow("Telefone 1:", fieldTelefone1, "Telefone 2:", fieldTelefone2, "Telefone 3:", fieldTelefone3);
		panelTable.addRow("e-mail", fieldEmail, MkPanelTable.getDefaultCell(3));
							
		panelTable.addRow(textObservacao.getJScrollPane("Observação"), GridBagConstraints.BOTH);
		
		addPanelCenter(panelTable, 800, 450);
		
		comboUf.setList(Arrays.asList(EnumUF.values()));
		fieldCpf.setMask(EnumMkMask.CPF);
		fieldCep.setMask(EnumMkMask.CEP);
		fieldCep.onChange(buscaEndereco());
		fieldEndereco.onChange(buscaCEP());
		fieldId.setEnabled(false);
	
		addPanelButton(true, buttonNovo, buttonSalvar, buttonEditar, buttonExcluir);
		
		if (bean.getId() == null) {
			novo();
		} else {
			beanToForm(false);
		}
	}

	protected void novo() {
		bean = new ClienteBazar();
		beanToForm(true);
		fieldCidade.setText("Maringá");
		comboUf.setSelected(EnumUF.PR);
	}

	protected void editar() {
		beanToForm(true);
	}

	protected void salvar() {
		try {
			bean.setNome(fieldNome.getText());
			bean.setDataNascimento(fieldDataNascimento.getDate());
			bean.setCpf(fieldCpf.getText());
			bean.setRg(fieldRg.getText());
			bean.setEndereco(fieldEndereco.getText());
			bean.setNumero(fieldNumero.getText());
			bean.setBairro(fieldBairro.getText());
			bean.setCep(fieldCep.getText());
			bean.setCidade(fieldCidade.getText());
			bean.setUf(comboUf.getSelected().name());
			bean.setTelefone1(fieldTelefone1.getText());
			bean.setTelefone2(fieldTelefone2.getText());
			bean.setTelefone3(fieldTelefone3.getText());
			bean.setEmail(fieldEmail.getText());
			bean.setObservacao(textObservacao.getText());

			if (bean.getId() == null) {
				bean.setOwner(JanelaLogin.getInstance().getUsuarioLogado().getId());
				clienteService.insert(bean);
			} else {
				clienteService.update(bean);
			}
			MkDialog.info("Cliente salvo com sucesso", buttonSalvar);
			
			bean = clienteService.findById(bean.getId());
			
			beanToForm(false);
			
			application.refreshWindows();
			
			this.requestFocusInWindow();

		} catch (Exception ex) {
			MkDialog.error(ex.getMessage(), ex);
		}
	}

	protected void excluir() {
		if (MkDialog.confirm("Deseja excluir esse registro?")) {
			try {
				clienteService.delete(bean.getId());
				closeWindow();
				application.refreshWindows();
			} catch (Exception ex) {
				MkDialog.error(ex.getMessage(), ex);
			}
		}
	}
	
	
	private void beanToForm(boolean isEditMode) {
		if (bean.getId() == null) {
			fieldId.setText("NOVO");
		} else {
			fieldId.setText(bean.getId()+"");
		}
		fieldNome.setText(bean.getNome());
		fieldDataNascimento.setDate(bean.getDataNascimento());
		fieldCpf.setText(bean.getCpf());
		fieldRg.setText(bean.getRg());
		fieldEndereco.setText(bean.getEndereco());
		fieldNumero.setText(bean.getNumero());
		fieldBairro.setText(bean.getBairro());
		fieldCep.setValue(bean.getCep());
		fieldCidade.setText(bean.getCidade());
		if (bean.getUf() != null) {
			comboUf.setSelected(EnumUF.valueOf(bean.getUf()));
		}
		fieldTelefone1.setText(bean.getTelefone1());
		fieldTelefone2.setText(bean.getTelefone2());
		fieldTelefone3.setText(bean.getTelefone3());
		fieldEmail.setText(bean.getEmail());
		textObservacao.setText(bean.getObservacao());
		
		fieldNome.setEditable(isEditMode);
		fieldDataNascimento.setEditable(isEditMode);
		fieldCpf.setEditable(isEditMode);
		fieldRg.setEditable(isEditMode);
		fieldEndereco.setEditable(isEditMode);
		fieldNumero.setEditable(isEditMode);
		fieldBairro.setEditable(isEditMode);
		fieldCep.setEditable(isEditMode);
		fieldCidade.setEditable(isEditMode);
		comboUf.setEnabled(isEditMode);
		fieldTelefone1.setEditable(isEditMode);
		fieldTelefone2.setEditable(isEditMode);
		fieldTelefone3.setEditable(isEditMode);
		fieldEmail.setEditable(isEditMode);
		textObservacao.setEditable(isEditMode);
				
		buttonNovo.setEnabled(!isEditMode);
		buttonSalvar.setEnabled(isEditMode);
		buttonEditar.setEnabled(!isEditMode);
		buttonExcluir.setEnabled(!isEditMode);
						
	}
	
	
	private MkRun buscaEndereco() {
		return new MkRun() {
			@Override
			public void execute() {
								
				if (buttonSalvar.isEnabled()
						&& (StringUtils.isBlank(fieldEndereco.getText())) 
							&& (!"  .   -   ".equals(fieldCep.getText()))) {
					try {
						List<CEP> buscarCEP = CEP.buscarCEP(fieldCep.getText());
						if (buscarCEP.size() == 0) {
							MkDialog.info("CEP inválido", fieldCep);
						} else {
							fieldEndereco.setText(buscarCEP.get(0).logradouro);
							fieldBairro.setText(buscarCEP.get(0).bairro);
							fieldCidade.setText(buscarCEP.get(0).localidade);
							comboUf.setSelected(EnumUF.valueOf(buscarCEP.get(0).uf));
							fieldNumero.grabFocus();
						}
					} catch (MkException ex) {
						MkDialog.info(ex.getMessage(), fieldCep);
					}
				}
			}
		};
	}
	
	private MkRun buscaCEP() {
		return new MkRun() {
			
			protected CEP cepRetorno;
			
			@Override
			public void execute() {
				if (buttonSalvar.isEnabled() && ("  .   -   ".equals(fieldCep.getText())) 
						&& (!StringUtils.isBlank(fieldEndereco.getText()))
							&& (!StringUtils.isBlank(fieldCidade.getText()))) {
					try {
						final List<CEP> buscarCEP = CEP.buscarCEP(fieldEndereco.getText()+
								", "+fieldCidade.getText());
						if (buscarCEP.size() == 0) {
							MkDialog.info("CEP não encontrado", fieldEndereco);
						} else if (buscarCEP.size() == 1) {
							fieldCep.setText(buscarCEP.get(0).cep);
						} else {
							MkWindow janelaCEP = new MkWindow() {
								@Override
								protected void initWindow() {
									final MkTable<CEP> table = new MkTable<CEP>();
									table.setModel(new MkTableModel<CEP>(buscarCEP, "Logradouro", "Bairro", "Localidade", "CEP") {
										@Override
										protected Object getRow(CEP bean,
												int rowIndex, int columnIndex) {
											switch (columnIndex) {
											case 0:
												return bean.logradouro;
											case 1:
												return bean.bairro;
											case 2:
												return bean.localidade;
											default:
												return bean.cep;
											}
										}
									});
									
									table.getColumnModel().getColumn(0).setPreferredWidth(200);
									table.getColumnModel().getColumn(1).setPreferredWidth(100);
									table.getColumnModel().getColumn(2).setPreferredWidth(50);
									table.getColumnModel().getColumn(3).setPreferredWidth(30);
									
									table.onDoubleClickOrEnter(new MkRun() {
										@Override
										public void execute() {
											cepRetorno = table.getSeleted(false);
											closeWindow();
										}
									});
									addPanelCenter(new MkPanelTable().addRow(table), 600, 150);
								}
							};
							janelaCEP.showWindow("Escolha um CEP", true);
							if (cepRetorno != null) {
								fieldCep.setText(cepRetorno.cep);
								if (StringUtils.isBlank(fieldBairro.getText())) {
									fieldBairro.setText(cepRetorno.bairro);
								}
							}
						}
						
					} catch (MkException ex) {
						MkDialog.info(ex.getMessage(), fieldEndereco);
					}
				}
			}
		};
	}
	
	

}
