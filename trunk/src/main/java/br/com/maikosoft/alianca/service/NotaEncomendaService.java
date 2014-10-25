package br.com.maikosoft.alianca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.maikosoft.core.MkServiceException;

import com.ibatis.sqlmap.client.SqlMapClient;

@Service
public class NotaEncomendaService {
	
	@Autowired
	protected SqlMapClient sqlMapClient;
	
	public Long nextNumero() throws MkServiceException {
		try {
			return (Long)sqlMapClient.queryForObject("nextNotaEncomenda");
        } catch (Exception e) {
            throw new MkServiceException(e);
        }
	}

	public void updateNumero(Long numero) throws MkServiceException {
		try {
			sqlMapClient.update("updateNumeroNotaEncomenda", numero);
        } catch (Exception e) {
            throw new MkServiceException(e);
        }
		
	}
	
}
