package br.com.maikosoft.bazar.service;

import java.util.HashMap;
import java.util.List;
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

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void insert(Pedido bean) throws MkServiceException {
		try {
    		bean.setId(genericDao.nextId());
    		genericDao.insert(bean);
    		for (PedidoItem item : bean.getListPedidoItem()) {
    			item.setId(pedidoItemDAO.nextId());
    			item.setOwner(bean.getOwner());
    			item.setPedido(bean);
				pedidoItemDAO.insert(item);
			}
    		
		} catch (MkDAOException e) {
			bean.setId(null);
			throw new MkServiceException(e);
		}
	}

	public void getItens(Pedido bean) throws MkServiceException {
		try {
			Map<String, Object> where = new HashMap<String, Object>();
			where.put("pedido_id", bean.getId());
			List<PedidoItem> findAll = pedidoItemDAO.findAll(where);
    		bean.setListPedidoItem(findAll);
		} catch (MkDAOException e) {
			throw new MkServiceException(e);
		}
	} 
	
	
}
