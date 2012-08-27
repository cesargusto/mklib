package br.com.maikosoft.cadmia;

import java.math.BigDecimal;


public class ClienteAndSaldoVO extends Cliente {
	
	private BigDecimal totalPago;
	private BigDecimal saldoDevedor;
	
	public ClienteAndSaldoVO(Cliente cliente, BigDecimal totalPago, BigDecimal saldoDevedor) {
		this.totalPago = totalPago;
		this.saldoDevedor = saldoDevedor;
		this.setNome(cliente.getNome());
		this.setId(cliente.getId());
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