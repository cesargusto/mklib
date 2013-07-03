package br.com.maikosoft.bazar.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.maikosoft.bazar.Pedido;
import br.com.maikosoft.bazar.PedidoItem;
import br.com.maikosoft.bazar.dao.PedidoDAO;
import br.com.maikosoft.bazar.dao.PedidoItemDAO;
import br.com.maikosoft.core.MkDAOException;
import br.com.maikosoft.core.MkService;
import br.com.maikosoft.core.MkServiceException;

@Service
public class PedidoService extends MkService<Pedido, PedidoDAO> {
	
	@Autowired
	private PedidoItemDAO pedidoItemDAO;
	
	@Override
	public Pedido findById(Long id) throws MkServiceException {
		Pedido bean = super.findById(id);
		Map<String, Object> where = new HashMap<String, Object>();
		where.put("pedido_id", bean.getId());
		try {
			bean.setListPedidoItem(pedidoItemDAO.findAll(where));
		} catch (MkDAOException ex) {
			throw new MkServiceException("Erro carregando itens pedido", ex);
		}
		return bean;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void insert(Pedido bean) throws MkServiceException {
		try {
    		bean.setId(genericDao.nextId());
    		genericDao.insert(bean);
    		for (PedidoItem pedidoItem : bean.getListPedidoItem()) {
    			pedidoItem.setId(pedidoItemDAO.nextId());
    			pedidoItem.setOwner(bean.getOwner());
    			pedidoItem.setPedido(bean);
				pedidoItemDAO.insert(pedidoItem);
			}
    		
		} catch (MkDAOException e) {
			bean.setId(null);
			throw new MkServiceException(e);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void updateItens(Pedido bean) throws MkServiceException {
		try {
			for (PedidoItem pedidoItem : bean.getListPedidoItem()) {
				if (pedidoItem.isDelete()) {
					if (pedidoItem.getId() != null) {
						pedidoItemDAO.delete(pedidoItem.getId());
					}
				} else {
					if (pedidoItem.getId() == null) {
						pedidoItem.setId(pedidoItemDAO.nextId());
						pedidoItem.setOwner(bean.getOwner());
						pedidoItem.setPedido(bean);
						pedidoItemDAO.insert(pedidoItem);
					}
				}
			}
		} catch (MkDAOException ex) {
			throw new MkServiceException("Erro atualizando itens pedido", ex);
		}
	}
	
}
