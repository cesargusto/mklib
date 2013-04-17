package br.com.maikosoft.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import br.com.maikosoft.core.MkException;

public class CEP {
	
	private static final String INDEXOF_RESULTADO = "<!--?xml version = '1.0' encoding = 'ISO-8859-1'?-->";
    private static final String END_INDEXOF_RESULTADO = "</table>";
    private static final String BUSCA_CEP_SERVICE_BASE_URL =
            "http://www.buscacep.correios.com.br/servicos/dnec/consultaLogradouroAction.do?Metodo=listaLogradouro&TipoConsulta=relaxation&StartRow=1&EndRow=10&relaxation=";
    
    public String logradouro;
    public String bairro;
    public String localidade;
    public String uf;
    public String cep;
    
    
    
	public static List<CEP> buscarCEP(String query) throws MkException {
		
		LinkedList<CEP> result = new LinkedList<CEP>();
		
		try {
			URL url = new URL(BUSCA_CEP_SERVICE_BASE_URL+URLEncoder.encode(query, "ISO-8859-1"));
			Document doc = Jsoup.parse(url, 5000);
			String source = doc.html();
			int indexOf = source.indexOf(INDEXOF_RESULTADO);
			
			if (indexOf != -1) {
				String table = source.substring(indexOf+INDEXOF_RESULTADO.length(),
						(source.indexOf(END_INDEXOF_RESULTADO, indexOf)+END_INDEXOF_RESULTADO.length()));
				Document tableDoc = Jsoup.parse(table);
				Elements linhas = tableDoc.select("td");
				if (!linhas.isEmpty()) {
					int count = 0;
					CEP item = new CEP();
					for (Element td : linhas) {
						switch (++count) {
						case 1:
							item.logradouro = td.text();
							break;
						case 2:
							item.bairro = td.text();
							break;
						case 3:
							item.localidade = td.text();
							break;
						case 4:
							item.uf = td.text();
							break;
						case 5:
							item.cep = td.text();
							break;
						}
						if (count % 5 == 0) {
							result.add(item);
							item = new CEP();
							count = 0;
						}
					}
				}
				
			}
		} catch (MalformedURLException ex) {
			throw new MkException("Parametro para busca de CEP invalido:"+query, ex);
		} catch (IOException ex) {
			throw new MkException("Erro de rede consultando CEP", ex);
		}
		return result;
	}
	

	public static void main( String[] args ) throws Exception {
		
		List<CEP> endereco = CEP.buscarCEP("Capitão Índio Bandeira, campo mourao");
		for (CEP cep : endereco) {
			System.out.println(cep.logradouro);
		}
        
		List<CEP> cep = CEP.buscarCEP("87.309-015");
		System.out.println(cep.get(0).bairro);
		
		
    }

}
