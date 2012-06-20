package br.com.maikosoft.cadmia.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.maikosoft.cadmia.Cliente;
import br.com.maikosoft.cadmia.Financeiro;
import br.com.maikosoft.cadmia.dao.ClienteDAO;
import br.com.maikosoft.cadmia.dao.FinanceiroDAO;
import br.com.maikosoft.core.MkDAOException;
import br.com.maikosoft.core.MkService;
import br.com.maikosoft.core.MkServiceException;

@Service
public class FinanceiroService extends MkService<Financeiro, FinanceiroDAO> {
	
	private static final String MENSALIDADE = "MENSALIDADE ";
	
	@Autowired
	private ClienteDAO clienteDAO;

	public void lancarMensalidades(String mes, String ano) throws MkServiceException {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("ddyyyyMMMMM");
			List<Cliente> listCliente = clienteDAO.findAll(Collections.<String, Object>emptyMap());
			for (Cliente cliente : listCliente) {
				
				if (BigDecimal.ZERO.compareTo(cliente.getValorMensalidade()) == 0) {
					continue;
				}
				
				Financeiro financeiro = new Financeiro();
				try {
					Date dataCadastro = dateFormat.parse(cliente.getDiaPagamento()+ano+mes);
					financeiro.setDataCadastro(dataCadastro);
				} catch (ParseException ex) {
					throw new MkServiceException("Erro ao gerar data de cadastro para cliente #"+cliente.getId(), ex);
				}
				financeiro.setId(this.genericDao.nextId());
				financeiro.setCliente(cliente);
				financeiro.setReferencia(MENSALIDADE+mes+"/"+ano);
				financeiro.setValor(cliente.getValorMensalidade());
				this.genericDao.insert(financeiro);
			}
		} catch (MkDAOException exception) {
			throw new MkServiceException(exception);
		}
		
	}
	
}
