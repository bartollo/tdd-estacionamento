package tdd_java_estacionamento.tdd_java_estacionamento;

import tdd_java_estacionamento.tdd_java_estacionamento.exceptions.EstacionamentoVeiculoFullException;
import tdd_java_estacionamento.tdd_java_estacionamento.exceptions.VeiculoJaRegistradoException;
import tdd_java_estacionamento.tdd_java_estacionamento.exceptions.VeiculoNaoRegistradoException;
import tdd_java_estacionamento.tdd_java_estacionamento.model.Veiculo;


public class GerenciadorEstacionamento {
	
	private static GerenciadorEstacionamento instance;
	
	private static final int maxVagas = 10;
			
	private Veiculo[] veiculos = new Veiculo[maxVagas];
	
	public static GerenciadorEstacionamento getInstance() {
		
		if ( instance == null ){
			instance = new GerenciadorEstacionamento();
		}
		
		return instance;
	}

	public void registrarEntrada(Veiculo veiculo) throws VeiculoJaRegistradoException, EstacionamentoVeiculoFullException {
		
		if ( this.isVeiculoRegistrado(veiculo) ){
			throw new VeiculoJaRegistradoException();
		}
		
		if(this.isEstacionamentoLotado()){
			throw new EstacionamentoVeiculoFullException();
		}
		
		
		int primeiraVagaLivre = this.getPrimeiraVagaLivre();
		
		this.veiculos[primeiraVagaLivre] = veiculo;			
		
				
	}
	
	public void registrarSaida(Veiculo veiculo) throws VeiculoNaoRegistradoException{
		
		if (!this.isVeiculoRegistrado(veiculo)){
			throw new VeiculoNaoRegistradoException();
		}
		
		int posicaoLiberada = this.getPosicaoPelaPlaca(veiculo.getPlaca());
		
		this.veiculos[posicaoLiberada] = null;
		
	}

	
	public Veiculo getVeiculoPelaPosicao(int i) throws IndexOutOfBoundsException {
		return this.veiculos[i-1];
	}
	
	public int getPosicaoPelaPlaca(String placa){
		
		for( int i = 0; i < this.getQuantidadeVeiculos(); i++){
			if (this.veiculos[i] != null){

				if (this.veiculos[i].getPlaca().equals(placa)){
					return i;
				}
				
			}
		}
		
		return -1;
		
	}
	
	private boolean isVeiculoRegistrado(Veiculo veiculoParaRegistrar){
		
		for ( Veiculo veiculo : veiculos){
			
			if (veiculo != null){
				if ( veiculo.getPlaca().equals(veiculoParaRegistrar.getPlaca()) ){
					return true;
				}		
			}
		}
		
		return false;
		
	}
	
	private int getPrimeiraVagaLivre(){
		
		int i = 0;
		
		for (Veiculo veiculo : veiculos){
			if (veiculo == null){
				break;
			}else{
				i++;				
			}
		}
		
		return i;
		
	}
	
	private boolean isEstacionamentoLotado(){
		
		int qtdeVagas = 0;
		
		for(Veiculo veiculo : veiculos){
			
			if (veiculo != null){
				qtdeVagas++;
			}
		}
		
		if (qtdeVagas == this.getQuantidadeVeiculos()){
			return true;
		}
		
		return false;
		
		
	}
	
	private int getQuantidadeVeiculos(){
		return this.veiculos.length;
	}
	
	
	public Veiculo[] getVeiculos(){
		return this.veiculos;
	}
	
}
