package br.com.maikosoft.alianca.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.maikosoft.alianca.Receita;
import br.com.maikosoft.alianca.dao.ReceitaDAO;
import br.com.maikosoft.core.MkDAOException;
import br.com.maikosoft.core.MkService;
import br.com.maikosoft.core.MkServiceException;

@Service
public class ReceitaService extends MkService<Receita, ReceitaDAO> {

	public List<String> findAllOftamologista() throws MkServiceException {
		try {
			return this.genericDao.findAllOftalmologista();
		} catch (MkDAOException ex) {
			throw new MkServiceException("Erro ao listar oftalmos", ex);
		}
	}
	
}
