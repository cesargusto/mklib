package br.com.maikosoft.core;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface MkTransaction <BEAN extends MkBean> {

	@Transactional(readOnly = true)
	public List<BEAN> findAll(Map<String, Object> where) throws MkServiceException;
	
	@Transactional(readOnly = true)
	public BEAN findById(Long id) throws MkServiceException;

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void insert(BEAN item) throws MkServiceException;

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void update(BEAN item) throws MkServiceException;

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void delete(Long id) throws MkServiceException;
	
	@Transactional(readOnly = true)
	public Long count(Map<String, Object> where) throws MkServiceException;
}
