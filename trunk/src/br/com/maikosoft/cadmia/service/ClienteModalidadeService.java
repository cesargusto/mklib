package br.com.maikosoft.cadmia.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.maikosoft.cadmia.Cliente;
import br.com.maikosoft.cadmia.ClienteModalidade;
import br.com.maikosoft.cadmia.dao.ClienteModalidadeDAO;
import br.com.maikosoft.cadmia.dao.ModalidadeDAO;
import br.com.maikosoft.core.MkDAOException;
import br.com.maikosoft.core.MkService;
import br.com.maikosoft.core.MkServiceException;

@Service
public class ClienteModalidadeService extends MkService<ClienteModalidade, ClienteModalidadeDAO> {
	
	@Autowired
	private ModalidadeDAO modalidadeDAO;
	
	public void carregarModalidades(Cliente cliente) throws MkServiceException {
		try {
			Map<String, Object> where = new HashMap<String, Object>();
			where.put("clienteId", cliente.getId());
			List<ClienteModalidade> list = this.genericDao.findAll(where);
			for (ClienteModalidade clienteModalidade : list) {
				clienteModalidade.setModalidade(modalidadeDAO.findById(clienteModalidade.getId()));
			}
			cliente.setListModalidade(list);
		} catch (MkDAOException e) {
			throw new MkServiceException(e);
		}
		
		
	}
	
}
