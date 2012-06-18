package br.com.maikosoft.cadmia;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import br.com.maikosoft.core.MkBean;

@SuppressWarnings("serial")
public class Cliente extends MkBean {

	private String nome;
	private String cpf;
	private String rg;
	private java.util.Date dataNascimento;
	private String endereco;
	private String numero;
	private String bairro;
	private String cep;
	private String cidade;
	private String uf;
	private String telefone1;
	private String telefone2;
	private String telefone3;
	private String email;
	private String observacao;	
	private Long diaPagamento;
	private BigDecimal valorMensalidade;
	private String codigoBarra;
	
	private List<ClienteModalidade> listModalidade;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	public java.util.Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(java.util.Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getTelefone1() {
		return telefone1;
	}
	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}
	public String getTelefone2() {
		return telefone2;
	}
	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}
	public String getTelefone3() {
		return telefone3;
	}
	public void setTelefone3(String telefone3) {
		this.telefone3 = telefone3;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public List<ClienteModalidade> getListModalidade() {
		if (listModalidade == null) {
			listModalidade = new LinkedList<ClienteModalidade>();
		}
		return listModalidade;
	}
	public void setListModalidade(List<ClienteModalidade> listModalidade) {
		this.listModalidade = listModalidade;
	}
	public Long getDiaPagamento() {
		return diaPagamento;
	}
	public void setDiaPagamento(Long diaPagamento) {
		this.diaPagamento = diaPagamento;
	}
	public BigDecimal getValorMensalidade() {
		return valorMensalidade;
	}
	public void setValorMensalidade(BigDecimal valorMensalidade) {
		this.valorMensalidade = valorMensalidade;
	}
	public String getCodigoBarra() {
		return codigoBarra;
	}
	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
	}
}
