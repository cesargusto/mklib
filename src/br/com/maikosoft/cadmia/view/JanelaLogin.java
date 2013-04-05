package br.com.maikosoft.cadmia.view;

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
	
	private static final Logger logger = Logger.getLogger(JanelaLogin.class);
	
	private MkFieldText fieldNome;
	private JPasswordField fieldSenha;
	
	private MkButtonConfirmar buttonLogin;
		
	private UsuarioService usuarioService;
	private static JanelaLogin instance;
	private Usuario usuarioLogado;
	
	private int tentativa = 0;
	private long tempo = 0;
    private long tempoLimite = 300000;
    
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
		panelTable.addRow("Nome:",fieldNome);
		panelTable.addRow("Senha:", fieldSenha);
		
		addPanelCenter(panelTable, 200, 150);
		
		buttonLogin.setText("Entrar");
		
		addPanelButton(true, buttonLogin);
		
		this.onCloseView = new MkRun() {
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
				MkDialog.error("Nome ou senha inválido.", null);
			} else {
				usuarioLogado = usuario;
				fieldSenha.setText("");
				JDialog window = (JDialog) this.getRootPane().getParent();
				window.setVisible(false);
			}
		} else {
			MkDialog.warm("Digite o nome e a senha");
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