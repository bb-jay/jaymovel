package br.com.jaymovel.telas;

import br.com.jaymovel.Formulario;
import br.com.jaymovel.excecoes.SairDaTelaException;
import br.com.jaymovel.excecoes.SaldoInsuficienteException;
import br.com.jaymovel.modelos.conta.Conta;
import br.com.jaymovel.modelos.conta.ContaCorrente;
import br.com.jaymovel.modelos.pessoa.Pessoa;
import br.com.jaymovel.util.Console;

public class TelaLogadaContaCorrente extends Tela {

	private ContaCorrente conta;
	private Pessoa cliente;

	public TelaLogadaContaCorrente(Pessoa cliente) {
		super(
				"Conta corrente",
				new String[] {
						"Meus dados",
						"Depósito",
						"Saque",
						"Transferir",
						"Empréstimos",
						"Pupança",
						"Investimentos",
						"Voltar" },
						false);
		this.cliente = cliente;
		this.conta = (ContaCorrente) Tela.getBanco().selecionada().getCadastro(cliente).getContaCorrente();
	}
	
	@Override
	protected void opcao1() {
		Formulario.meusDados(cliente);
	}

	@Override
	protected void opcao2() {
		System.out.print("Informe valor do depósito: ");
		long deposito = Console.lerMoeda();
		conta.depositar(deposito);
		super.setMensagem("Depositado com sucesso!");
	}
	
	@Override
	protected void opcao3() {
		System.out.print("Informe valor do saque: ");
		long saque = Console.lerMoeda();
		try {conta.sacar(saque);}
		catch (SaldoInsuficienteException e) {
			super.setMensagem("Saldo insuficiente...");
			return;
		}
		super.setMensagem("Saque realizado com sucesso!");
	}
	
	@Override
	protected void opcao4() {
		System.out.print("Informe valor da transferência: ");
		long transferencia = Console.lerMoeda();
		System.out.print("Informe numero da conta para transferencia");
		
		int numeroDestino = Console.lerInt(0, Integer.MAX_VALUE);
		Conta destino = Tela.getBanco().selecionada().getConta(numeroDestino);
		if (destino == null){
			super.setMensagem("Conta de destino não encontrada");
			return;
		}

		try {conta.transferir(transferencia, destino);}
		catch (SaldoInsuficienteException e) {
			super.setMensagem("Saldo insuficiente...");
			return;
		}
	}

	@Override
	protected void opcao5() {
	}

	@Override
	protected void opcao6() {
	}
	
	@Override
	protected void opcao7() {
		
	}
	
	@Override
	protected void opcao8() throws SairDaTelaException{
		throw new SairDaTelaException();
	}

	@Override
	protected void mostraInfo() {
		Console.printaCentro(cliente.getNome());
		Console.printaCentro(String.format(
				"SALDO: %s\tDÍVIDA: %s\tLIMITE: %s",
				conta.getSaldoFormatado(),
				conta.getDividaFormatada(),
				conta.getLimiteFormatado()));
	};

}
