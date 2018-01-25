package tdd_java_estacionamento.tdd_java_estacionamento;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tdd_java_estacionamento.tdd_java_estacionamento.exceptions.EstacionamentoVeiculoFullException;
import tdd_java_estacionamento.tdd_java_estacionamento.exceptions.VeiculoJaRegistradoException;
import tdd_java_estacionamento.tdd_java_estacionamento.exceptions.VeiculoNaoRegistradoException;
import tdd_java_estacionamento.tdd_java_estacionamento.model.Veiculo;

public class TestGerenciadorEstacionamento {
		

	GerenciadorEstacionamento gerenciador;
	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	
	@Before
	public void carregaManager(){
		
		gerenciador = GerenciadorEstacionamento.getInstance();
		
	}
	
	@After
	public void resetandoVagasDoEstacionamento(){
		for(int i = 0; i < gerenciador.getVeiculos().length ; i++){
			gerenciador.getVeiculos()[i] = null;
		}
	}
	
	
	@Test
	public void testDeveRetornarUmSingletonDaClasse(){
		
		GerenciadorEstacionamento novoGerenciador = GerenciadorEstacionamento.getInstance();
		
		assertEquals(gerenciador,novoGerenciador);
	}
	
	@Test
	public void testDeveRetornarOsDadosCorretosDoVeiculo() throws ParseException{
		
		Date horarioEntrada = this.formatter.parse("2018-01-01 11:00:00");
		Date horarioPagamento = this.formatter.parse("2018-01-01 21:00:00");
		
		Veiculo vehicle = new Veiculo("Fusion","X33-2030",1);		
		
		assertEquals(vehicle.getModelo(),"Fusion");
		assertEquals(vehicle.getPlaca(),"X33-2030");
		//assertEquals(vehicle.getHoraEntrada(),horarioEntrada);
		//assertEquals(vehicle.getHoraPagamento(),horarioPagamento);
		assertEquals(vehicle.getContrato(),1);
		
	}
	
	@Test(expected=VeiculoJaRegistradoException.class)
	public void testDeveGerarUmErroQuandoRegistrarUmCarroJaRegistrado() throws VeiculoJaRegistradoException, EstacionamentoVeiculoFullException, ParseException{
		
		Date horarioEntrada = this.formatter.parse("2018-01-01 11:00:00");
		Date horarioPagamento = this.formatter.parse("2018-01-01 21:00:00");
		
		Veiculo veiculo01 = new Veiculo("Fusion","200-2RR3",1);
		Veiculo veiculo02 = new Veiculo("Fusion","200-2RR3",1);
		
		gerenciador.registrarEntrada(veiculo01);
		gerenciador.registrarEntrada(veiculo02);
	}
	
	@Test
	public void testDeveEstacionarVeiculoNaPrimeiraVagaDisponivel() throws VeiculoJaRegistradoException, EstacionamentoVeiculoFullException, ParseException{
		
		Date horarioEntrada = this.formatter.parse("2018-01-01 11:00:00");
		Date horarioPagamento = this.formatter.parse("2018-01-01 21:00:00");
		
		Veiculo veiculo01 = new Veiculo("Fusion","200-2RR5",1);
		Veiculo veiculo02 = new Veiculo("Fusion","200-2RR4",1);
		
		gerenciador.registrarEntrada(veiculo01);
		gerenciador.registrarEntrada(veiculo02);
		
		Veiculo veiculoPrimeiraPosicao = gerenciador.getVeiculoPelaPosicao(1);
		assertEquals(veiculoPrimeiraPosicao.getPlaca(), veiculo01.getPlaca());
		
		Veiculo veiculoSegundaPosicao = gerenciador.getVeiculoPelaPosicao(2);
		assertEquals(veiculoSegundaPosicao.getPlaca(), veiculo02.getPlaca());
				
	}
	
	@Test(expected=EstacionamentoVeiculoFullException.class)
	public void testNaoDeveMaisAceitarVeiculosComEstacionamentoCheio() throws VeiculoJaRegistradoException, EstacionamentoVeiculoFullException, ParseException{
		Date horarioEntrada = this.formatter.parse("2018-01-01 11:00:00");
		Date horarioPagamento = this.formatter.parse("2018-01-01 21:00:00");
		
		List<String> placas = Arrays.asList("200-0001","200-0002","200-0003",
										    "200-0004","200-0005","200-0006",
										    "200-0007","200-0008","200-0009",
										    "200-0010");
		
		for( String placa : placas){
			Veiculo veiculo = new Veiculo("Ford Fusion",placa,1);
			gerenciador.registrarEntrada(veiculo);
		}
		
		Veiculo veiculoNaoAceito = new Veiculo("Ford Fusion","200-0011",1);
		gerenciador.registrarEntrada(veiculoNaoAceito);
	}
	
	@Test(expected=VeiculoNaoRegistradoException.class)
	public void testDeveGerarExcecaoQuandoRegistrarASaidaDeUmVeiculoInexistente() throws VeiculoNaoRegistradoException, ParseException{
		
		Date horarioEntrada = this.formatter.parse("2018-01-01 11:00:00");
		Date horarioPagamento = this.formatter.parse("2018-01-01 21:00:00");
		
		Veiculo veiculo = new Veiculo("Ford Fusion","200-0050",1);
		
		gerenciador.registrarSaida(veiculo);
	}
	
	@Test
	public void testDeveLiberarAVagaDoCarroQueEstavaEstacionado() throws VeiculoJaRegistradoException, EstacionamentoVeiculoFullException, VeiculoNaoRegistradoException, ParseException{
		/* 
		 * Registrar v�rios carros ( uns 6 );
		 * Registrar a sa�da de um ve�culo intermedi�rio selecionado;
		 * Retornar pela posi��o e ser igual a null ( indicando que aquela posi��o o carro foi liberado corretamente );
		*/
		
		Date horarioEntrada = this.formatter.parse("2018-01-01 11:00:00");
		Date horarioPagamento = this.formatter.parse("2018-01-01 21:00:00");
		
		List<String> placas = Arrays.asList("200-0001","200-0002","200-0003",
			    							"200-0004","200-0005","200-0006");
		
		for( String placa : placas){
			Veiculo veiculo = new Veiculo("Ford Fusion",placa,1);
			gerenciador.registrarEntrada(veiculo);
		}
		
		Veiculo veiculoSelecionado = gerenciador.getVeiculoPelaPosicao(3);
		
		gerenciador.registrarSaida(veiculoSelecionado);
		
		assertNull(gerenciador.getVeiculoPelaPosicao(3));
		
	}
	
	@Test
	public void testDeveRegistrarASaidaDeUmVeiculoIntermediarioEInserirNaPrimeiraPosicaoLivreNaoSendoAPrimeira() throws VeiculoJaRegistradoException, EstacionamentoVeiculoFullException, VeiculoNaoRegistradoException, ParseException{
		
		Date horarioEntrada = this.formatter.parse("2018-01-01 11:00:00");
		Date horarioPagamento = this.formatter.parse("2018-01-01 21:00:00");
		
		List<String> placas = Arrays.asList("200-0001","200-0002","200-0003",
			    							"200-0004","200-0005","200-0006");
		
		for( String placa : placas){
			Veiculo veiculo = new Veiculo("Ford Fusion",placa,1);
			gerenciador.registrarEntrada(veiculo);
		}
		
		
		Veiculo primeiraSaida = gerenciador.getVeiculoPelaPosicao(3);
		Veiculo segundaSaida = gerenciador.getVeiculoPelaPosicao(6);
		
		//Registrando a sa�da do ve�culo na posi��o n�mero 3(indice 2) e 6(indice 5);		
		gerenciador.registrarSaida(primeiraSaida);
		gerenciador.registrarSaida(segundaSaida);
		
		Veiculo novoVeiculo = new Veiculo("Ford Fusion","200-EART",1);
		gerenciador.registrarEntrada(novoVeiculo);
		
		// Verificando usando a busca da posi��o pela placa
		// int positionInserted = manager.getPositionByBoard(new_vehicle.getBoard());
		
		// Retornando a posi��o que deveria ter inserido o ve�culo e verificando que a o carro de tal placa
		// realmente desejava-se estacionar. � a do carro que
		
		Veiculo posicaoInserida = gerenciador.getVeiculoPelaPosicao(3);
		
		assertEquals(posicaoInserida.getPlaca(),"200-EART");
	}

	
	
}
