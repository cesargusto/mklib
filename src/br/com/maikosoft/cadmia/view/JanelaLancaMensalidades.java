package br.com.maikosoft.cadmia.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import br.com.maikosoft.cadmia.service.FinanceiroService;
import br.com.maikosoft.core.MkServiceException;
import br.com.maikosoft.layout.swing.MkButton.MkButtonAdicionar;
import br.com.maikosoft.layout.swing.MkComboBox;
import br.com.maikosoft.layout.swing.MkDialog;
import br.com.maikosoft.layout.swing.MkPanelTable;
import br.com.maikosoft.layout.swing.MkWindow;

@SuppressWarnings("serial")
public class JanelaLancaMensalidades extends MkWindow {
	
//	private static final Logger logger = Logger.getLogger(JanelaLancaMensalidades.class);
	
	private MkComboBox<String> comboMes;
	private MkComboBox<String> comboAno;
	private MkButtonAdicionar buttonLancar;
	
	private FinanceiroService financeiroService;
	
	@Override
	protected void initWindow() {
		
		Calendar calendar = Calendar.getInstance();
		calendar.roll(Calendar.YEAR, false);
		
		List<String> listMes = new LinkedList<String>();
		Set<String> listAno = new LinkedHashSet<String>();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMMMM");
		
		for (int i=0; i<12;i++) {
			calendar.add(Calendar.MONTH, 1);
			String result = dateFormat.format(calendar.getTime());
			listMes.add(result.substring(4));
			listAno.add(result.substring(0, 4));
			
		}
		comboMes.setList(listMes);
		comboAno.setList(new ArrayList<String>(listAno));
		
		MkPanelTable panelTable = new MkPanelTable();
		panelTable.addRow("Referência", comboMes, comboAno);
		
		addPanelCenter(panelTable, 300, 100);
		
		buttonLancar.setText("Lançar");
		
		addPanelButton(true, buttonLancar);
				
	}
	
	protected void adicionar() {
		try {
			this.waitCursor(true);
			financeiroService.lancarMensalidades(comboMes.getSelected(), comboAno.getSelected());
			MkDialog.info("Mensalidades lançadas com sucesso.");
		} catch (MkServiceException exception) {
			MkDialog.error("Erro ao lançar mensalidades", exception);
		} finally {
			this.waitCursor(false);
		}
	}
	

}
