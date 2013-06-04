package br.com.maikosoft.core;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;



public abstract class MkService<BEAN extends MkBean, DAO extends MkDAO<BEAN>>  implements MkTransaction<BEAN> {

	protected DAO genericDao;
	
	@Autowired
	protected void setGenericDAO(DAO genericDao) {
		this.genericDao = genericDao;
	}
	
	public BEAN findById(Long id) throws MkServiceException {
    	try {
    		return (BEAN) genericDao.findById(id);
		} catch (MkDAOException e) {
			throw new MkServiceException(e);
		}
    }
    
	public void insert(BEAN bean) throws MkServiceException {
    	try {
    		bean.setId(genericDao.nextId());
    		genericDao.insert(bean);
		} catch (MkDAOException e) {
			bean.setId(null);
			throw new MkServiceException(e);
		}
    }
    
	public void update(BEAN bean) throws MkServiceException {  
    	try {
    		genericDao.update(bean);
    	} catch (MkDAOException e) {
			throw new MkServiceException(e);
		}
    }
    
	public void delete(Long id) throws MkServiceException {  
    	try {
    		genericDao.delete(id);
		} catch (MkDAOException e) {
			throw new MkServiceException(e);
		}
    }
     
    public List<BEAN> findAll(Map<String, Object> where) throws MkServiceException {
    	try {
    		return genericDao.findAll(where);
		} catch (MkDAOException e) {
			throw new MkServiceException(e);
		}
	}

	public Long count(Map<String, Object> where) throws MkServiceException {
		try {
			return genericDao.count(where);
		} catch (MkDAOException e) {
			throw new MkServiceException(e);
		}
	}

}