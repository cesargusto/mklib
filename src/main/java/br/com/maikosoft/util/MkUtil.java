package br.com.maikosoft.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import br.com.maikosoft.core.MkException;


public class MkUtil {

	public static String now() {
		return toString(new Date());
	}

	public static String toString(Date data) {
		return toString(data, "dd/MM/yyyy");
	}
	
	public static String toString(Date data, String pattern) {
		if (data == null) {
			return "";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(data);
	}

	public static Date toDate(String value) throws MkException {
		if (!StringUtils.hasText(value)) {
			return null;
		}

		Pattern p = Pattern.compile("^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))" +
				"(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|" +
				"(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$");
		Matcher m = p.matcher(value);
		if (!m.find()) {
			throw new MkException("Data inválida: " + value);
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			return dateFormat.parse(value);
		} catch (ParseException e) {
			throw new MkException("Data inválida: " + value);
		}
	}

	public static String toString(BigDecimal value) {
		return toString(value, 2);
	}

	public static String toString(BigDecimal value, int casasDecimais) {
		String result = "";
		if (value != null) {
			Locale locale = new Locale("pt", "BR");
			NumberFormat formatter = DecimalFormat.getNumberInstance(locale);
			formatter.setMinimumFractionDigits(casasDecimais);
			formatter.setMaximumFractionDigits(casasDecimais);
			return formatter.format(value);
		}
		return result;
	}

	public static BigDecimal toBigDecimal(String value) {
		if (!StringUtils.hasText(value)) {
			return null;
		}

		StringBuilder sb = new StringBuilder(value.length());
		for (char c : value.toCharArray()) {

			if (Character.isDigit(c)) {
				sb.append(c);
			} else if (c == '-') {
				sb.append(c);
			} else if (c == ',') {
				sb.append('.');
			}
		}
		
		if (sb.length() == 0 ) {
			return null;
		}
		return new BigDecimal(sb.toString());
	}

	public static BigDecimal toBigDecimal(Double value) {
		return toBigDecimal(value, 2);
	}

	public static BigDecimal toBigDecimal(Double value, int casasDecimais) {
		if (value == null) {
			return null;
		}
		BigDecimal valor = new BigDecimal(value, MathContext.DECIMAL32);
		return valor.setScale(casasDecimais, RoundingMode.HALF_EVEN);
	}

	public static String toString(Long value, boolean isFormated) {
		String result = "";
		if (value != null) {
			// Locale locale = Locale.getDefault();
			Locale locale = new Locale("pt", "BR");
			NumberFormat formatter = DecimalFormat.getNumberInstance(locale);
			formatter.setMinimumFractionDigits(0);
			formatter.setMaximumFractionDigits(0);
			return formatter.format(value);
		}
		return result;
	}

	public static Long toLong(String value) {
		BigDecimal bigDecimal = toBigDecimal(value);
		return (bigDecimal == null ? null : bigDecimal.longValue());
	}
	
	public static Integer toInteger(String value, Integer padrao) {
		BigDecimal bigDecimal = toBigDecimal(value);
		return (bigDecimal == null ? padrao : bigDecimal.intValue());
	}
	
	public static String getHash(String value, String algoritimo) throws MkException {
        try {
        	if (!StringUtils.hasText(value)) {
    			return null;
    		}
            java.security.MessageDigest md = java.security.MessageDigest.getInstance(algoritimo);
            md.reset();
            md.update(value.getBytes());
            byte[] digest1 = md.digest();

            StringBuilder encryptedPassword = new StringBuilder();
            for (int i = 0; i < digest1.length; i++) {
                String hex = Integer.toHexString(0xFF & digest1[i]);
                if (hex.length() == 1) {
                    encryptedPassword.append("0").append(hex);
                } else {
                    encryptedPassword.append(hex);
                }
            }
            
            return encryptedPassword.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new MkException("Algoritimo não é suportado #"+algoritimo);
        }
    }

	public static Date setUltimaHora(Date date) {
		Calendar instance = Calendar.getInstance();
		instance.setTime(date);
		instance.set(Calendar.HOUR_OF_DAY, 23);
		instance.set(Calendar.MINUTE, 59);
		instance.set(Calendar.SECOND, 59);
		return instance.getTime();
	}

	public static String somenteNumero(String value) {
		if (!StringUtils.hasText(value)) {
			return null;
		}
		StringBuilder sb = new StringBuilder(value.length());
		for (char c : value.toCharArray()) {
			if (Character.isDigit(c)) {
				sb.append(c);
			}
		}
		return sb.toString();
		
	}
	
	public static String removerAcento(String str) {
		str = Normalizer.normalize(str, Normalizer.Form.NFD);
		str = str.replaceAll("[^\\p{ASCII}]", "");
		return str;
	}
}
