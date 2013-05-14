package br.com.maikosoft.alianca;

import java.util.Date;

import br.com.maikosoft.core.MkBean;

@SuppressWarnings("serial")
public class Receita extends MkBean {
	
	private String cliente;
	private String telefone;
    private String oftalmologista;
    private Date dataReceita;
    private String olhoDireitoLonge;
    private String olhoEsquerdoLonge;
    private String olhoDireitoPerto;
    private String olhoEsquerdoPerto;
    private String adicao;
    private String lente;
    private String armacao;
    private String observacao;
    
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getOftalmologista() {
		return oftalmologista;
	}
	public void setOftalmologista(String oftalmologista) {
		this.oftalmologista = oftalmologista;
	}
	public Date getDataReceita() {
		return dataReceita;
	}
	public void setDataReceita(Date dataReceita) {
		this.dataReceita = dataReceita;
	}
	public String getOlhoDireitoLonge() {
		return olhoDireitoLonge;
	}
	public void setOlhoDireitoLonge(String olhoDireitoLonge) {
		this.olhoDireitoLonge = olhoDireitoLonge;
	}
	public String getOlhoEsquerdoLonge() {
		return olhoEsquerdoLonge;
	}
	public void setOlhoEsquerdoLonge(String olhoEsquerdoLonge) {
		this.olhoEsquerdoLonge = olhoEsquerdoLonge;
	}
	public String getOlhoDireitoPerto() {
		return olhoDireitoPerto;
	}
	public void setOlhoDireitoPerto(String olhoDireitoPerto) {
		this.olhoDireitoPerto = olhoDireitoPerto;
	}
	public String getOlhoEsquerdoPerto() {
		return olhoEsquerdoPerto;
	}
	public void setOlhoEsquerdoPerto(String olhoEsquerdoPerto) {
		this.olhoEsquerdoPerto = olhoEsquerdoPerto;
	}
	public String getAdicao() {
		return adicao;
	}
	public void setAdicao(String adicao) {
		this.adicao = adicao;
	}
	public String getLente() {
		return lente;
	}
	public void setLente(String lente) {
		this.lente = lente;
	}
	public String getArmacao() {
		return armacao;
	}
	public void setArmacao(String armacao) {
		this.armacao = armacao;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
    
    

}
