package br.com.maikosoft.core;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;


import com.ibatis.sqlmap.client.SqlMapClient;

public abstract class MkDAO<BEAN extends MkBean> {
	
	private static final Logger logger = Logger.getLogger(MkDAO.class);
	
	@Autowired
	protected SqlMapClient sqlMapClient;
	
	protected Class<BEAN> beanClass;

	@SuppressWarnings("unchecked")
	public MkDAO() {
		try {
			ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
	        Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();       
	        beanClass = (Class<BEAN>) actualTypeArguments[0];
    	} catch (Exception e) {
    		logger.error("Erro carregando ibatis", e);
		}
	}

	@SuppressWarnings("unchecked")
	public BEAN findById(Long id) throws MkDAOException {
		try {
			return (BEAN) sqlMapClient.queryForObject("get"+beanClass.getSimpleName(), id);
		} catch (SQLException e) {
			logger.error(beanClass.getSimpleName()+" não pode ser recuperado ID#"+id, e);
			throw new MkDAOException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public BEAN insert(BEAN bean) throws MkDAOException {
		try {
			return (BEAN) sqlMapClient.insert("insert"+beanClass.getSimpleName(), bean);
		} catch (SQLException e) {
			logger.error(beanClass.getSimpleName()+" não pode ser inserido #"+bean, e);
			throw new MkDAOException(e);
		}
	}

	public boolean update(BEAN bean) throws MkDAOException {
		try {
			int update = sqlMapClient.update("update"+beanClass.getSimpleName(), bean);
			return (update>0);			
		} catch (SQLException e) {
			logger.error(beanClass.getSimpleName()+" não pode ser atualizado #"+bean, e);
			throw new MkDAOException(e);
		}	
	}

	public boolean delete(Serializable id) throws MkDAOException {
		try {
			int delete = sqlMapClient.delete("delete"+beanClass.getSimpleName(), id);
			return (delete>0);
		} catch (SQLException e) {
			logger.error(beanClass.getSimpleName()+" não pode ser deletado ID#"+id, e);
			throw new MkDAOException(e);
		}
	}

	public Long nextId() throws MkDAOException {
		try {
			return (Long) sqlMapClient.queryForObject("nextId"+beanClass.getSimpleName());
		} catch (SQLException e) {
			logger.error("Não pode recuperar proximo ID#"+beanClass.getSimpleName(), e);
			throw new MkDAOException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<BEAN> findAll(Map<String, Object> where) throws MkDAOException {
		try {
			return sqlMapClient.queryForList("all"+beanClass.getSimpleName(),  where);
		} catch (SQLException e) {
			logger.error("Erro executando findAll", e);
			throw new MkDAOException(e);
		}
	}
	
	public Long count(Map<String, Object> where)  throws MkDAOException {
        try {
			return (Long)sqlMapClient.queryForObject("count"+beanClass.getSimpleName(), where);
        } catch (Exception e) {
        	logger.error("Erro executando count", e);
            throw new MkDAOException(e);
        }
    }
	
}
