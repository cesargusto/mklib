package br.com.maikosoft.bazar.service;

import org.springframework.stereotype.Service;

import br.com.maikosoft.bazar.Produto;
import br.com.maikosoft.bazar.dao.ProdutoDAO;
import br.com.maikosoft.core.MkService;

@Service
public class ProdutoService extends MkService<Produto, ProdutoDAO> {
	
}
