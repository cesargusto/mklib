package br.com.maikosoft.view;

import java.awt.AWTEvent;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JDialog;
import javax.swing.JPasswordField;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import br.com.maikosoft.Usuario;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.mklib.MkDialog;
import br.com.maikosoft.mklib.MkFieldText;
import br.com.maikosoft.mklib.MkPanelTable;
import br.com.maikosoft.mklib.MkWindow;
import br.com.maikosoft.mklib.MkButton.MkButtonConfirmar;
import br.com.maikosoft.service.UsuarioService;

@SuppressWarnings("serial")
public class JanelaLogin extends MkWindow {
	
	private static final Logger logger = Logger.getLogger(JanelaLogin.class);
	
	private MkFieldText fieldNome;
	private JPasswordField fieldSenha;
	
	private MkButtonConfirmar buttonLogin;
		
	private UsuarioService usuarioService;
	private static JanelaLogin instance;
	private Usuario usuarioLogado;
	
	private int tentativa = 0;
	private long tempo = 0;
    private long tempoLimite = 6000000;
    
    public static JanelaLogin getInstance() {
		if (instance == null) {
			logger.debug("iniciou instancia janela login");
			instance = new JanelaLogin();
			instance.showWindow("Login", true);
			instance.iniciaVerificacao();
			instance.iniciaListeners();
		}
		return instance;
	}
	
	@Override
	protected void initWindow() {
		
		MkPanelTable panelTable = new MkPanelTable();
		panelTable.addRow("Nome", MkPanelTable.getDefaultCell(1)).addRow(fieldNome);
		panelTable.addRow("Senha", MkPanelTable.getDefaultCell(1)).addRow(fieldSenha);
		
		addPanelCenter(panelTable, 250, 150);
		
		buttonLogin.setText("Entrar");
		
		addPanelButton(true, buttonLogin);
		
		this.onCloseWindow = new MkRun() {
			@Override
			public void execute() {
				if (MkDialog.confirm("O sistema será fechado. Deseja continuar?")) {
					System.exit(0);
				}
			}
		};
	}
	
	protected void confirmar() {
		
		String nome = fieldNome.getText();
		String senha = new String(fieldSenha.getPassword());
		
		if (logger.isDebugEnabled()) {
			nome = "m";
			senha = "1";
		}
		
		if (StringUtils.hasText(nome) && StringUtils.hasText(senha)) {
			Usuario usuario = usuarioService.login(nome, senha);
			if (usuario == null) {
				if (++tentativa % 3 == 0) {
					MkDialog.error("Você efetuou três tentativas inválidas e o sistema será fechado.", null);
					System.exit(0);
				}
				MkDialog.info("Nome ou senha inválido.", fieldNome);
			} else {
				usuarioLogado = usuario;
				fieldSenha.setText("");
				JDialog window = (JDialog) this.getRootPane().getParent();
				window.setVisible(false);
			}
		} else {
			MkDialog.info("Digite o nome e a senha", fieldNome);
		}

	}
	
	private void relogin() {
		logger.debug("relogin");
		this.fieldNome.setEnabled(false);
		JDialog window = (JDialog) this.getRootPane().getParent();
		window.setVisible(true);
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}
	
	private void iniciaListeners() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

            public boolean dispatchKeyEvent(KeyEvent e) {
                tempo = 0;
                return false;
            }
        });

        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {

            public void eventDispatched(AWTEvent event) {
                tempo = 0;
            }
        }, AWTEvent.MOUSE_EVENT_MASK);

        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {

            public void eventDispatched(AWTEvent event) {
                tempo = 0;
            }
        }, AWTEvent.MOUSE_MOTION_EVENT_MASK);
    }
	
	private void iniciaVerificacao() {
		TimerTask task = new TimerTask() {

            @Override
            public void run() {
                tempo += 1000;
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task, 0, 1000);
        
        Thread sessao = new Thread() {
        	@Override
          public void run() {
              while (true) {
            	  if (tempo >= tempoLimite) {
            		  getInstance().relogin();
            	  }
                  try {
                      Thread.sleep(1000);
                  } catch (Exception ex) {
                	  logger.error("Erro sleep thread");
                  }
              }
          }
        };
        sessao.start();
    }
}