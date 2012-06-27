package br.com.maikosoft.cadmia.view;

import javax.swing.JPasswordField;

import org.springframework.util.StringUtils;

import br.com.maikosoft.cadmia.Usuario;
import br.com.maikosoft.cadmia.service.UsuarioService;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.layout.swing.MkButton.MkButtonConfirmar;
import br.com.maikosoft.layout.swing.MkDialog;
import br.com.maikosoft.layout.swing.MkFieldText;
import br.com.maikosoft.layout.swing.MkPanelTable;
import br.com.maikosoft.layout.swing.MkWindow;

@SuppressWarnings("serial")
public class JanelaLogin extends MkWindow {
	
	private MkFieldText fieldNome;
	private JPasswordField fieldSenha;
	
	private MkButtonConfirmar buttonLogin;
		
	private UsuarioService usuarioService;	
	private static JanelaLogin instance;
	private Usuario usuarioLogado;
	private int tentativa = 0;
	
	public static JanelaLogin getInstance() {
		if (instance == null) {
			instance = new JanelaLogin();
		}
		return instance;
	}
	
	@Override
	protected void initWindow() {
		
		MkPanelTable panelTable = new MkPanelTable();
		panelTable.addRow("Nome:",fieldNome);
		panelTable.addRow("Senha:", fieldSenha);
		
		addPanelCenter(panelTable, 200, 150);
		
		buttonLogin.setText("Entrar");
		
		addPanelButton(true, buttonLogin);
	}
	
	protected void confirmar() {
		
		String nome = fieldNome.getText();
		String senha = new String(fieldSenha.getPassword());
		
		if (StringUtils.hasText(nome) && StringUtils.hasText(senha)) {
			Usuario usuario = usuarioService.login(nome, senha);
			if (usuario == null) {
				if (++tentativa % 3 == 0) {
					MkDialog.error("Você efetuou três tentativas inválidas e o sistema será fechado.", null);
					System.exit(0);
				}
				MkDialog.error("Nome ou senha inválido.", null);
			} else {
				usuarioLogado = usuario;
				fieldSenha.setText("");
				this.onCloseView = null;
				this.fecharJanela();
			}
		} else {
			MkDialog.warm("Digite o nome e a senha");
		}

	}
	
	public void login() {
		this.onCloseView = new MkRun() {
			@Override
			public void execute() {
				if (MkDialog.confirm("O sistema será fechado. Deseja continuar?")) {
					System.exit(0);
				}
			}
		};
		this.showView("Login", true);
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}
}
