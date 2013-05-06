package br.com.maikosoft.cadmia.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.maikosoft.cadmia.ClienteCadMia;
import br.com.maikosoft.cadmia.ClienteModalidade;
import br.com.maikosoft.cadmia.dao.ClienteModalidadeDAO;
import br.com.maikosoft.cadmia.dao.ModalidadeDAO;
import br.com.maikosoft.core.MkDAOException;
import br.com.maikosoft.core.MkService;
import br.com.maikosoft.core.MkServiceException;

@Service
public class ClienteModalidadeService extends MkService<ClienteModalidade, ClienteModalidadeDAO> {
	
	private static final Logger logger = Logger.getLogger(ClienteModalidadeService.class);
	
	@Autowired
	private ModalidadeDAO modalidadeDAO;
	
	public void carregarModalidades(ClienteCadMia clienteCadMia) throws MkServiceException {
		try {
			Map<String, Object> where = new HashMap<String, Object>();
			where.put("clienteId", clienteCadMia.getId());
			List<ClienteModalidade> list = this.genericDao.findAll(where);
			for (ClienteModalidade clienteModalidade : list) {
				clienteModalidade.setModalidade(modalidadeDAO.findById(clienteModalidade.getModalidade().getId()));
			}
			clienteCadMia.setListModalidade(list);
		} catch (MkDAOException e) {
			throw new MkServiceException(e);
		}
		
		
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void update(List<ClienteModalidade> listModalidade) throws MkServiceException {
		try {
			logger.debug("Atualizando lista de modalidades cliente total: "+listModalidade.size());
			for (ClienteModalidade clienteModalidade : listModalidade) {
				if (clienteModalidade.getId() == null) {
					if (!clienteModalidade.isDelete()) {
						logger.debug("Adiconando modalidade ao cliente# "+clienteModalidade.getModalidade());
						clienteModalidade.setId(this.genericDao.nextId());
						this.genericDao.insert(clienteModalidade);
					}
				} else if (clienteModalidade.isDelete()) {
					logger.debug("Removendo modalidade ao cliente ID# "+clienteModalidade.getId());
					this.genericDao.delete(clienteModalidade.getId());
				}
			}
		} catch (MkDAOException e) {
			throw new MkServiceException(e);
		}
		
	}
	
}
