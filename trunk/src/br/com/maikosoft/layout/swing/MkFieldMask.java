package br.com.maikosoft.layout.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import javax.swing.JFormattedTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

@SuppressWarnings("serial")
public class MkFieldMask extends JFormattedTextField {
	
	private NumberFormat format;
	private MaskFormatter maskFormatter;
	
	public MkFieldMask() {
		this.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusGained(java.awt.event.FocusEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						selectAll();
					}
				});
			}
		});
	}
	
	@Override
	public void setValue(Object value) {
		if (format==null) {
			super.setValue(value); 
		} else {
			this.setText(format.format(value));
		}
	}

	public void onChange(final MkRun adapter) {
		
		this.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                adapter.execute();
            }
        });
    	
		this.addFocusListener(new FocusAdapter() {

            @Override
            public void focusLost(FocusEvent e) {
            	adapter.execute();
            }
        });
    }


	public void setMask(EnumMask mask) {
		if (mask.isInstall()) {
			try {
				if (maskFormatter==null) {
					maskFormatter = new MaskFormatter(mask.getMask());
				} else {
					maskFormatter.uninstall();
					maskFormatter = new MaskFormatter(mask.getMask());
				}
				maskFormatter.install(this);
				this.revalidate();				
			} catch (ParseException e) {
				throw new IllegalArgumentException(e);
			}
		} else {
			
			Locale locale = new Locale("pt", "BR");
			if (EnumMask.DECIMAL.equals(mask)) {
				locale = Locale.ENGLISH;
			}
			format = NumberFormat.getInstance(locale);
			format.setRoundingMode(RoundingMode.HALF_EVEN);
			format.setMinimumFractionDigits(mask.getMinFrac());
			format.setMaximumFractionDigits(mask.getMaxFrac());
			format.setGroupingUsed(mask.isGroup());
			format.setMaximumIntegerDigits(10);
			NumberFormatter formatterValor = new NumberFormatter(format);
			formatterValor.setValueClass(BigDecimal.class);
			formatterValor.setAllowsInvalid(false);
			this.setFormatterFactory(new DefaultFormatterFactory(formatterValor));
			this.setValue(BigDecimal.ZERO);
			this.setHorizontalAlignment(SwingConstants.RIGHT);
		}
	}
	
	public enum EnumMask {
		CURRENCY(2, 2, true),
	    DATE("##/##/####"),
	    CPF("###.###.###-##"),
	    CNPJ("##.###.###/####-##"),
	    NUMBER(0, 0, true),
	    DECIMAL(1, 5, false),
	    INTEGER(0, 0, false),
	    CEP("##.###-###"),
	    CELLPHONE("(##) ####-####"),
	    CURRENCY4(4, 4, true);
	    
	    private String mask;
	    private boolean install;
	    private int minFrac;
	    private int maxFrac;
	    private boolean isGroup;
	    
	    private EnumMask(String mask) {
	    	this.install = true;
	        this.mask = mask;
	    }

	    private EnumMask(int minFrac, int maxFrac, boolean isGroup) {
	    	this.install = false;
			this.minFrac = minFrac;
			this.maxFrac = maxFrac;
			this.isGroup = isGroup;
		}

		public String getMask() {
	        return mask;
	    }

		public boolean isInstall() {
			return install;
		}

		public int getMinFrac() {
			return minFrac;
		}

		public void setMinFrac(int minFrac) {
			this.minFrac = minFrac;
		}

		public int getMaxFrac() {
			return maxFrac;
		}

		public void setMaxFrac(int maxFrac) {
			this.maxFrac = maxFrac;
		}

		public boolean isGroup() {
			return isGroup;
		}

		public void setGroup(boolean isGroup) {
			this.isGroup = isGroup;
		}
	}
}
