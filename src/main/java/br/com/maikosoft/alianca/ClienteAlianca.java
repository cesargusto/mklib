package br.com.maikosoft.alianca;

import java.util.Date;

import br.com.maikosoft.Cliente;

@SuppressWarnings("serial")
public class ClienteAlianca extends Cliente {

	private String estadoCivil;
	private String naturalidade;
	private String pai;
	private String mae;
	private String desde;
	private String seproc;// retirar
	private String enderecoDesde;
	
	private String empresa;
    private String empresaEndereco;
    private String empresaDesde;
    private String empresaTelefone;
    private String empresaSalario;
    private String empresaCargo;
    
    private String conjuge;
    private Date conjugeNascimento;
    private String conjugeEmpresa;
    private String conjugeCargo;
    private String conjugeEmpresaEndereco;
    
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public String getEmpresaEndereco() {
		return empresaEndereco;
	}
	public void setEmpresaEndereco(String empresaEndereco) {
		this.empresaEndereco = empresaEndereco;
	}
	public String getEmpresaDesde() {
		return empresaDesde;
	}
	public void setEmpresaDesde(String empresaDesde) {
		this.empresaDesde = empresaDesde;
	}
	public String getEmpresaTelefone() {
		return empresaTelefone;
	}
	public void setEmpresaTelefone(String empresaTelefone) {
		this.empresaTelefone = empresaTelefone;
	}
	public String getEmpresaSalario() {
		return empresaSalario;
	}
	public void setEmpresaSalario(String empresaSalario) {
		this.empresaSalario = empresaSalario;
	}
	public String getEmpresaCargo() {
		return empresaCargo;
	}
	public void setEmpresaCargo(String empresaCargo) {
		this.empresaCargo = empresaCargo;
	}
	public String getEstadoCivil() {
		return estadoCivil;
	}
	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}
	public String getNaturalidade() {
		return naturalidade;
	}
	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}
	public String getConjuge() {
		return conjuge;
	}
	public void setConjuge(String conjuge) {
		this.conjuge = conjuge;
	}
	public Date getConjugeNascimento() {
		return conjugeNascimento;
	}
	public void setConjugeNascimento(Date conjugeNascimento) {
		this.conjugeNascimento = conjugeNascimento;
	}
	public String getConjugeEmpresa() {
		return conjugeEmpresa;
	}
	public void setConjugeEmpresa(String conjugeEmpresa) {
		this.conjugeEmpresa = conjugeEmpresa;
	}
	public String getConjugeCargo() {
		return conjugeCargo;
	}
	public void setConjugeCargo(String conjugeCargo) {
		this.conjugeCargo = conjugeCargo;
	}
	public String getConjugeEmpresaEndereco() {
		return conjugeEmpresaEndereco;
	}
	public void setConjugeEmpresaEndereco(String conjugeEmpresaEndereco) {
		this.conjugeEmpresaEndereco = conjugeEmpresaEndereco;
	}
	public String getPai() {
		return pai;
	}
	public void setPai(String pai) {
		this.pai = pai;
	}
	public String getMae() {
		return mae;
	}
	public void setMae(String mae) {
		this.mae = mae;
	}
	public String getDesde() {
		return desde;
	}
	public void setDesde(String desde) {
		this.desde = desde;
	}
	public String getSeproc() {
		return seproc;
	}
	public void setSeproc(String seproc) {
		this.seproc = seproc;
	}
	public String getEnderecoDesde() {
		return enderecoDesde;
	}
	public void setEnderecoDesde(String enderecoDesde) {
		this.enderecoDesde = enderecoDesde;
	}
    
    
	
}
