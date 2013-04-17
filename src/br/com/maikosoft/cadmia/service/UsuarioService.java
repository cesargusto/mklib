package br.com.maikosoft.cadmia.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import br.com.maikosoft.cadmia.Usuario;
import br.com.maikosoft.cadmia.dao.UsuarioDAO;
import br.com.maikosoft.core.MkService;
import br.com.maikosoft.util.MkUtil;

@Service
public class UsuarioService extends MkService<Usuario, UsuarioDAO> {
	
	private static final Logger logger = Logger.getLogger(UsuarioService.class);
	
	public Usuario login(String nome, String senha) {
		try {
			Map<String, Object> where = new HashMap<String, Object>();
			where.put("nome", nome);
			where.put("senha", MkUtil.getHash(senha, "SHA-256"));
			where.put("ativo", true);
			List<Usuario> list = this.genericDao.findAll(where);
			if (list != null && list.size() == 1) {
				return list.get(0);
			}
		} catch (Exception  ex) {
			logger.error("Erro ao efetuar login", ex);
		}
		return null;
	}
	
}
