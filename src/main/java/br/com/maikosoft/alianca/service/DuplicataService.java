package br.com.maikosoft.alianca.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.maikosoft.alianca.ClienteAlianca;
import br.com.maikosoft.alianca.Duplicata;
import br.com.maikosoft.alianca.dao.DuplicataDAO;
import br.com.maikosoft.core.MkService;
import br.com.maikosoft.util.Extenso;
import br.com.maikosoft.view.JanelaLogin;

@Service
public class DuplicataService extends MkService<Duplicata, DuplicataDAO> {

	@Transactional(readOnly=true)
	public List<Duplicata> gerarDuplicatas(ClienteAlianca clienteAlianca,
			Date primeiroVencimento, 
			BigDecimal valor, 
			Integer numeroParcela, 
			Long numeroNota,
			String observacao) {
		
		BigDecimal valorTotal = valor.multiply(new BigDecimal(numeroParcela));
		String vendedor = JanelaLogin.getInstance().getUsuarioLogado().getNome();
		Calendar dataVencimento = Calendar.getInstance();
		dataVencimento.setTime(primeiroVencimento);
		String valorExtenso = new Extenso(valor, true).toString();
		
		LinkedList<Duplicata> result = new LinkedList<Duplicata>();
		
		
		for (int i = 1; i <= numeroParcela; i++) {
			Duplicata duplicata = new Duplicata();
			duplicata.setClienteAlianca(clienteAlianca);
			duplicata.setDataCadastro(new Date());
			duplicata.setDataVencimento(dataVencimento.getTime());
			duplicata.setNumeroNota(numeroNota);
			duplicata.setObservacao(observacao);
			duplicata.setReferencia(i+"/"+numeroParcela);
			duplicata.setValor(valor);
			duplicata.setValorTotal(valorTotal);
			duplicata.setVendedor(vendedor);
			duplicata.setValorExtenso(valorExtenso);
			
			result.add(duplicata);
			
			dataVencimento.add(Calendar.MONTH, 1);
		}
		
		return result;
	}
	
}
