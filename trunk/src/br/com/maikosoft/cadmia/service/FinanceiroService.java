package br.com.maikosoft.cadmia.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
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
	
	private static final Logger logger = Logger.getLogger(FinanceiroService.class);
	private static final String MENSALIDADE = "Mensalidade ";
	
	@Autowired
	private ClienteDAO clienteDAO;

	public void lancarMensalidades(String mes, String ano) throws MkServiceException {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("hhmmssddyyyyMMMMM");
			List<Cliente> listCliente = clienteDAO.findAll(Collections.<String, Object>emptyMap());
			for (Cliente cliente : listCliente) {
				
				if (BigDecimal.ZERO.compareTo(cliente.getValorMensalidade()) == 0) {
					continue;
				}
				
				Financeiro financeiro = new Financeiro();
				try {
					Date dataCadastro = dateFormat.parse("235959"+cliente.getDiaPagamento()+ano+mes);
					financeiro.setDataCadastro(dataCadastro);
				} catch (ParseException ex) {
					throw new MkServiceException("Erro ao gerar data de cadastro para cliente #"+cliente.getId(), ex);
				}
				financeiro.setId(this.genericDao.nextId());
				financeiro.setCliente(cliente);
				financeiro.setReferencia(MENSALIDADE+mes+"/"+ano);
//				financeiro.setValor(cliente.getValorMensalidade().multiply(new BigDecimal(-1)));
				financeiro.setValor(cliente.getValorMensalidade());
				this.genericDao.insert(financeiro);
			}
		} catch (MkDAOException exception) {
			throw new MkServiceException(exception);
		}
		
	}

	public BigDecimal getSaldo(Cliente cliente) throws MkServiceException {
		try {
			BigDecimal saldo = BigDecimal.ZERO;
			
			if (cliente.getId()!=null) {
				Map<String, Object> where = new HashMap<String, Object>();
				where.put("cliente_id", cliente.getId());
				where.put("before_data_cadastro", new Date());
				
				List<Financeiro> list = this.genericDao.findAll(where );			
				if (list!=null) {
					for (Financeiro financeiro : list) {
						if (financeiro.getDataPagamento() == null) {
							saldo = saldo.subtract(financeiro.getValor());
						}
					}
				}
			}
			
			logger.debug("Retornando saldo:"+saldo);
			return saldo;			
		} catch (MkDAOException exception) {
			throw new MkServiceException("Erro obtendo saldo cliente #"+cliente.getId(), exception);
		}
	}

	public void pagar(LinkedList<Financeiro> list) throws MkServiceException {
		try {
			for (Financeiro financeiro : list) {
				financeiro.setDataPagamento(new Date());
				this.genericDao.update(financeiro);
			}
		} catch (MkDAOException exception) {
			throw new MkServiceException("Erro efetuando pagamento financeiro",
					exception);
		}
	}
	
}