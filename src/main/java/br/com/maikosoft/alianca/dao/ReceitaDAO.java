package br.com.maikosoft.alianca.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.maikosoft.alianca.Receita;
import br.com.maikosoft.core.MkDAO;
import br.com.maikosoft.core.MkDAOException;

@Repository
public class ReceitaDAO extends MkDAO<Receita> {
	
	@SuppressWarnings("unchecked")
	public List<String> findAllOftalmologista() throws MkDAOException {
		try {
			return (List<String>)sqlMapClient.queryForList("oftalmoReceita");
		} catch (SQLException e) {
			throw new MkDAOException(e);
		}
	}

}
