package br.com.maikosoft.cadmia;

import java.math.BigDecimal;


@SuppressWarnings("serial")
public class ClienteAndSaldoVO extends ClienteCadMia {
	
	private BigDecimal totalPago;
	private BigDecimal saldoDevedor;
	
	public ClienteAndSaldoVO(ClienteCadMia clienteCadMia, BigDecimal totalPago, BigDecimal saldoDevedor) {
		this.totalPago = totalPago;
		this.saldoDevedor = saldoDevedor;
		this.setNome(clienteCadMia.getNome());
		this.setId(clienteCadMia.getId());
		this.setCpf(clienteCadMia.getCpf());
	}

	public BigDecimal getTotalPago() {
		return totalPago;
	}

	public void setTotalPago(BigDecimal totalPago) {
		this.totalPago = totalPago;
	}

	public BigDecimal getSaldoDevedor() {
		return saldoDevedor;
	}

	public void setSaldoDevedor(BigDecimal saldoDevedor) {
		this.saldoDevedor = saldoDevedor;
	}
	
}