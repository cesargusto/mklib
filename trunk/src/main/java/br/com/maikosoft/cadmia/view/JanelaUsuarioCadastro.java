package br.com.maikosoft.cadmia.view;

import javax.swing.JCheckBox;
import javax.swing.JPasswordField;

import br.com.maikosoft.cadmia.Usuario;
import br.com.maikosoft.cadmia.service.UsuarioService;
import br.com.maikosoft.mklib.MkDialog;
import br.com.maikosoft.mklib.MkFieldText;
import br.com.maikosoft.mklib.MkPanelTable;
import br.com.maikosoft.mklib.MkWindow;
import br.com.maikosoft.mklib.MkButton.MkButtonEditar;
import br.com.maikosoft.mklib.MkButton.MkButtonExcluir;
import br.com.maikosoft.mklib.MkButton.MkButtonNovo;
import br.com.maikosoft.mklib.MkButton.MkButtonSalvar;
import br.com.maikosoft.util.MkUtil;

@SuppressWarnings("serial")
public class JanelaUsuarioCadastro extends MkWindow {
	
	private MkFieldText fieldId;
	private MkFieldText fieldNome;
	private JPasswordField fieldSenha;
	private JCheckBox checkBoxAtivo;
	private JCheckBox checkBoxAdministrador;
	
	private MkButtonNovo buttonNovo;
	private MkButtonSalvar buttonSalvar;
	private MkButtonEditar buttonEditar;
	private MkButtonExcluir buttonExcluir;
		
	private Usuario bean;
	private UsuarioService usuarioService;
	
	public JanelaUsuarioCadastro(Usuario bean) {
		this.bean = bean;
	}

	@Override
	protected void initWindow() {
		
		MkPanelTable panelTable = new MkPanelTable();
		panelTable.addRow("Nome:",fieldNome);
		panelTable.addRow("Senha:", fieldSenha);
		panelTable.addRow("", checkBoxAtivo);
		checkBoxAdministrador.setText("Administrador");
		panelTable.addRow("", checkBoxAdministrador);
		
		addPanelCenter(panelTable, 350, 150);
		fieldId.setEnabled(false);
		checkBoxAtivo.setText("Ativo");
	
		addPanelButton(true, buttonNovo, buttonSalvar, buttonEditar, buttonExcluir);
		
		beanToForm(false);
	}
	
	public void novo() {
		bean = new Usuario();
		beanToForm(true);
	}

	protected void editar() {
		beanToForm(true);
	}

	protected void salvar() {
		try {
			bean.setNome(fieldNome.getText());
			if (fieldSenha.getPassword().length != 0 ) {
				bean.setSenha(MkUtil.getHash(new String(fieldSenha.getPassword()), "SHA-256"));
			}
			bean.setAtivo(checkBoxAtivo.isSelected());
			bean.setAdministrador(checkBoxAdministrador.isSelected());

			if (bean.getId() == null) {
				usuarioService.insert(bean);
			} else {
				usuarioService.update(bean);
			}
			MkDialog.info("Usuário salvo com sucesso", buttonSalvar);

			bean = usuarioService.findById(bean.getId());
			beanToForm(false);
			application.refreshWindows();

		} catch (Exception ex) {
			MkDialog.error(ex.getMessage(), ex);
		}
	}

	protected void excluir() {
		if (MkDialog.confirm("Deseja excluir esse registro?")) {
			try {
				usuarioService.delete(bean.getId());
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
		fieldNome.setEditable(isEditMode);
		
		checkBoxAtivo.setSelected(bean.getAtivo());
		checkBoxAtivo.setEnabled(isEditMode);
		
		checkBoxAdministrador.setSelected(bean.isAdministrador());
		checkBoxAdministrador.setEnabled(isEditMode);
		
		fieldSenha.setEditable(isEditMode);
		
		buttonNovo.setEnabled(!isEditMode);
		buttonSalvar.setEnabled(isEditMode);
		buttonEditar.setEnabled(!isEditMode);
		buttonExcluir.setEnabled(!isEditMode);
		
	}

}
