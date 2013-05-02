package br.com.maikosoft.cadmia.view;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

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
		
		List<String> listMes = new LinkedList<String>();
		List<String> listAno = new LinkedList<String>();
		
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMMMM");
		listAno.add(calendar.get(Calendar.YEAR)+"");
		
		for (int i=0; i<12;i++) {
			calendar.add(Calendar.MONTH, 1);
			String result = dateFormat.format(calendar.getTime());
			listMes.add(result);
		}
		
		listAno.add(calendar.get(Calendar.YEAR)+"");
		
		comboMes.setList(listMes);
		comboAno.setList(listAno);
		
		MkPanelTable panelTable = new MkPanelTable();
		panelTable.addRow("Referência", comboMes, comboAno);
		
		addPanelCenter(panelTable, 300, 100);
		
		buttonLancar.setText("Lançar");
		
		addPanelButton(true, buttonLancar);
				
	}
	
	protected void adicionar() {
		try {
			if (MkDialog.confirm("Deseja lançar as mensalidades referêntes a "+comboMes.getSelected())) {
				this.waitCursor(true);
				financeiroService.lancarMensalidades(comboMes.getSelected(), comboAno.getSelected());
				MkDialog.info("Mensalidades lançadas com sucesso.", buttonLancar);
			}
		} catch (MkServiceException exception) {
			MkDialog.error("Erro ao lançar mensalidades", exception);
		} finally {
			this.waitCursor(false);
		}
	}
	

}
