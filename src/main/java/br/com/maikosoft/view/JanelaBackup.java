package br.com.maikosoft.view;

import java.awt.GridBagConstraints;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import br.com.maikosoft.mklib.MkButton.MkButtonSalvar;
import br.com.maikosoft.mklib.MkDialog;
import br.com.maikosoft.mklib.MkFieldText;
import br.com.maikosoft.mklib.MkPanelTable;
import br.com.maikosoft.mklib.MkTextArea;
import br.com.maikosoft.mklib.MkWindow;
import br.com.maikosoft.util.MkUtil;

@SuppressWarnings("serial")
public class JanelaBackup extends MkWindow {
	
	private static final Logger logger = Logger.getLogger(JanelaBackup.class);
	
	private MkFieldText fieldPathDB;
	private MkFieldText fieldPathBackup;
	private MkTextArea textAreaMensagem;
	private MkButtonSalvar buttonSalvar;
	
	private BasicDataSource dataSource;
		
	@Override
	protected void initWindow() {
		
		dataSource = application.getApplicationContext().getBean(BasicDataSource.class);
		
		MkPanelTable panelTable = new MkPanelTable();
		panelTable.addRow("Caminho Banco de Dados", fieldPathDB);
		panelTable.addRow("Caminho Backup ", fieldPathBackup);
		panelTable.addRow(textAreaMensagem.getJScrollPane("Saída"), GridBagConstraints.BOTH);
		
		if (new File("C:\\Arquivos de Programas\\PostgreSQL\\9.1\\bin\\pg_dump.exe").exists()) {
			fieldPathDB.setText("C:\\Arquivos de Programas\\PostgreSQL\\9.1\\bin\\pg_dump.exe");
		} else if (new File("C:\\Program Files (x86)\\PostgreSQL\\9.2\\bin\\pg_dump.exe").exists()) {
			fieldPathDB.setText("C:\\Program Files (x86)\\PostgreSQL\\9.2\\bin\\pg_dump.exe");
		} else if (new File("/usr/bin/pg_dump").exists()) {
			fieldPathDB.setText("/usr/bin/pg_dump");
		} else {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.showOpenDialog(this);
			fieldPathDB.setText(fileChooser.getSelectedFile().getAbsolutePath());
		}
		
		File file = new File("");
		fieldPathBackup.setText(file.getAbsolutePath()+File.separatorChar+"backup_"+MkUtil.toString(new Date(), "yyyy_MM_dd")+".sql");
		textAreaMensagem.setEditable(false);
		
		addPanelCenter(panelTable, 600, 500);
		
		buttonSalvar.setText("Gerar Backup");
		
		addPanelButton(true, buttonSalvar);
				
	}
	
	protected void salvar() {
		
		try {
			if (!StringUtils.hasText(fieldPathDB.getText())) {
				MkDialog.info("Informe o caminho para o programa de backup", fieldPathDB);
				fieldPathDB.grabFocus();
			} else if (!StringUtils.hasText(fieldPathBackup.getText())) {
				MkDialog.info("Informe o caminho do arquivo de backup", fieldPathBackup);
				fieldPathBackup.grabFocus();
			} else {
				this.waitCursor(true);
				final List<String> baseCmds = new ArrayList<String>();
				baseCmds.add(fieldPathDB.getText());
				baseCmds.add("-h");
				baseCmds.add("localhost");
				baseCmds.add("-p");
				baseCmds.add("5432");
				baseCmds.add("-U");
				baseCmds.add(dataSource.getUsername());
				baseCmds.add("-b");
				baseCmds.add("-v");
				baseCmds.add("--inserts");
				baseCmds.add("-f");
				baseCmds.add(fieldPathBackup.getText());
				String url = dataSource.getUrl();
				String dataBase = url.substring(url.lastIndexOf('/')+1);
				baseCmds.add(dataBase);
				
				logger.debug("Executando backup:"+baseCmds.toString());
				
				final ProcessBuilder pb = new ProcessBuilder(baseCmds);

				// Set the password
				final Map<String, String> env = pb.environment();
				env.put("PGPASSWORD", dataSource.getPassword());

				final Process process = pb.start();

				final BufferedReader r = new BufferedReader(new InputStreamReader(process.getErrorStream()));
				String line = r.readLine();
				while (line != null) {
					textAreaMensagem.append(line + "\n");
					line = r.readLine();
				}
				r.close();

				process.waitFor();
				
				File fileBackup = new File(fieldPathBackup.getText());
				if (fileBackup.exists()) {
					MkDialog.info("Backup gerado", buttonSalvar);
				} else {
					MkDialog.warm("Backup não foi gerado.");
				}

			}
		} catch (Exception ex) {
			logger.error("Erro Backup", ex);
			MkDialog.error("Erro ao executar backup", ex);
		} finally {
			this.waitCursor(false);
		}
			
	}
}
