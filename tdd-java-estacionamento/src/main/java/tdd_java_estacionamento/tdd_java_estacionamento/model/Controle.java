package tdd_java_estacionamento.tdd_java_estacionamento.model;

import java.util.Date;

public class Controle {
	
	private String placa;
	private Date horaEntrada;
	private Date horaSaida;
	private Date horaPagamento;
	
	public Controle(String placa, Date horaEntrada) {
		this.placa = placa;
		this.horaEntrada = horaEntrada;
	}	
	
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public Date getHoraEntrada() {
		return horaEntrada;
	}
	public void setHoraEntrada(Date horaEntrada) {
		this.horaEntrada = horaEntrada;
	}
	public Date getHoraSaida() {
		return horaSaida;
	}
	public void setHoraSaida(Date horaSaida) {
		this.horaSaida = horaSaida;
	}
	public Date getHoraPagamento() {
		return horaPagamento;
	}
	public void setHoraPagamento(Date horaPagamento) {
		this.horaPagamento = horaPagamento;
	}

	

}
