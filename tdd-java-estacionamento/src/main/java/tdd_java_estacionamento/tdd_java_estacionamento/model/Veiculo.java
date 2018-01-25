package tdd_java_estacionamento.tdd_java_estacionamento.model;

import java.util.Date;

public class Veiculo {
	
	private String modelo;
	private String placa;
	private Date horaEntrada;
	private Date horaPagamento;
	private int contrato; 

	
	/* contrato
	 * 
	 * Mensalista =0, 
	 * Diarista=1. 
	 * hora=2
	 * 
	 * 
	 * */
		
	public Veiculo(String model, String board, int contrato){
		this.modelo = model;
		this.placa  = board;		              
		this.contrato=contrato;
		
	}
	
	public String getModelo(){
		return this.modelo;
	}
	
	public String getPlaca(){
		return this.placa;
	}
	
	public String toString(){
		return this.modelo+" / "+this.placa;
	}

	public int getContrato() {
		return contrato;
	}

	public Date getHoraEntrada() {
				
		return horaEntrada;				
	}
	
	public void setHoraEntrada(Date horaEntrada) {
		
		this.horaEntrada=horaEntrada;				
	}	

	public Date getHoraPagamento() {
		
		return horaPagamento;
	}
	
	public void setHoraPagamento(Date horaPagamento) {
		
		this.horaPagamento= horaPagamento;				
	}	
	


}
