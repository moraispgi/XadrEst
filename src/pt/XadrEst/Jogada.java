/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.XadrEst;

import pt.Xadrez.Movimento;
import pt.Xadrez.ProxyDivisao;

/**
 *
 * @author Ricardo
 */
public class Jogada {

    private JogadorXadrEST jogador;

    int REMOVER;
    
    private Movimento movimento;
    private ProxyDivisao divisaoOrigem;
    private ProxyDivisao divisaoDestino;

    private Jogada jogadaAnterior;
    private Jogada jogadaPosterior;

    /**
     * Valor da Jogada
     */
    public final int valorJogada;
    private final boolean isRoot;

    private boolean wasExecutada;

    /**
     * Cria um root
     */
    protected Jogada() {
        isRoot = true;
        valorJogada = 0;
        wasExecutada = true;
        movimento = null;

    }

    /**
     *
     * @param jogadaAnterior jogada anterior
     * @param jogador Jogador associado à jogada
     * @param divisaoOrigem divisão onde origina a jogada
     * @param movimento movimento que será executado
     */
    private Jogada(Jogada jogadaAnterior, JogadorXadrEST jogador, ProxyDivisao divisaoOrigem, Movimento movimento) {

        if (jogadaAnterior.getUltima() != jogadaAnterior) {
            throw new RuntimeException("A jogada anterior deve ser a ultima da lista.");
        }

        if (!jogadaAnterior.wasExecutada) {
            throw new RuntimeException("A jogada anterior deve ser executada antes desta.");
        }

        this.jogadaAnterior = jogadaAnterior;

        if (jogador == null) {
            throw new RuntimeException("O jogador deve existir");
        }

        if (divisaoOrigem == null) {
            throw new RuntimeException("A divisao de origem deve existir");
        }

        if (!divisaoOrigem.hasPeca()) {
            throw new RuntimeException("Não existe peça na coordenada de origem.");
        }

        if (!divisaoOrigem.canSeguirMovimento(movimento)) {
            throw new RuntimeException("Esta jogada não é valida.");
        }

        this.divisaoOrigem = divisaoOrigem;
        this.divisaoDestino = divisaoOrigem.getProxyDestino(movimento);
        this.movimento = movimento;

        if (this.divisaoDestino.hasPeca()) {
            this.divisaoDestino.backupPeca();
        }

        if (this.divisaoDestino.hasPeca()) {
            valorJogada = this.divisaoDestino.getImportanciaPeca();
        } else {
            valorJogada = 0;
        }

        wasExecutada = false;
        isRoot = false;
    }

    /**
     * Cria uma jogada root que será usada como ponto de partida para outras
     * jogadas
     *
     * @return nova jogada root
     */
    public static Jogada criarRoot() {
        return new Jogada();
    }

    /**
     * Cria uma jogada posterior a esta
     *
     * @param jogador jogador que realiza a jogada
     * @param divisaoOrigem proxy da divisao de origem
     * @param movimento movimento que será executado
     * @return Jogada em questão
     */
    public Jogada criarJogadaSeguinte(JogadorXadrEST jogador, ProxyDivisao divisaoOrigem, Movimento movimento) {
        Jogada jogada = new Jogada(this, jogador, divisaoOrigem, movimento);
        this.jogadaPosterior = jogada;
        return jogada;
    }
    
    /**
     * 
     * @return true se a jogada foi executada, false se não
     */
    public boolean wasExecutada()
    {
        return wasExecutada;
    }

    /**
     * Executa a jogada
     */
    public void executarJogada() {
        if(wasExecutada)
            throw new RuntimeException("A jogada já foi executada.");
        
        divisaoOrigem.moverPeca(movimento);
        wasExecutada = true;
    }

    /**
     * Verifica se existe próxima
     *
     * @return true se existir próxima, false se não
     */
    public boolean hasProxima() {
        return jogadaPosterior != null;
    }

    /**
     * Verifica se existe anterior
     *
     * @return true se existir anterior, false se não
     */
    public boolean hasAnterior() {
        return jogadaAnterior != null;
    }

    /**
     *
     * @return jogada posterior
     */
    public Jogada getProxima() {
        if (!hasProxima()) {
            throw new RuntimeException("Não existe proxima.");
        }

        return jogadaPosterior;
    }

    /**
     *
     * @return jogada anterior
     */
    public Jogada getAnterior() {
        if (!hasAnterior()) {
            throw new RuntimeException("Não existe proxima.");
        }

        return jogadaAnterior;
    }

    /**
     * Devolve a ultima jogada percorrendo todas a jogadas pela ligação
     * posterior
     *
     * @return ultima jogada
     */
    public Jogada getUltima() {
        Jogada ultima = this;
        
        while (ultima.jogadaPosterior != null) {
            ultima = ultima.jogadaPosterior;
        }

        return ultima;
    }
    
    /**
     * Devolve a ultima jogada executada percorrente todas a jogadas pela ligação
     * posterior
     *
     * @return ultima jogada
     */
    public Jogada jogadaUltimaExecutada() {
        Jogada ultima = this;
        
        while (ultima.jogadaPosterior != null && ultima.jogadaPosterior.wasExecutada) {
            ultima = ultima.jogadaPosterior;
        }

        return ultima;
    }

    /**
     * remove todas as ligações de destino e de origem relativas a esta jogada
     */
    private void removerLigacoes() {
        if (jogadaPosterior != null) {
            jogadaPosterior.removerLigacoes();
            jogadaPosterior = null;
        }
        if (jogadaAnterior == null) {
            return;
        }

        jogadaAnterior.jogadaPosterior = null;

    }

    /**
     * retrocede as jogadas até esta jogada
     */
    public void ir() {
        Jogada ultima = getUltima();

        while (ultima != this && !ultima.isRoot) {
            ultima.retroceder();

            ultima = ultima.jogadaAnterior;
        }
    }

    /**
     * Retrocede a jogada
     */
    public void retroceder() {
        divisaoDestino.moverPeca(movimento.gerarMovimentoInvertido());

        if (divisaoDestino.hasBackup()) {
            divisaoDestino.restaurarPeca();
        }

        wasExecutada = false;
    }

    /**
     * retrocede as jogadas n vezes a partir deste ponto
     *
     * @param vezes numero de vezes que irá retroceder
     */
    public void retroceder(int vezes) {
        if (jogadaPosterior != null && jogadaPosterior.wasExecutada) {
            throw new RuntimeException("Não é possível retroceder a jogada pois existem jogadas posteriores já executadas.");
        }

        if (!this.isRoot && vezes > 0) {
            retroceder();
            jogadaAnterior.retroceder(vezes - 1);
        }

    }

    /**
     * Retrocede a jogada e apaga definitivamente todas as ligações a esta
     */
    public void retrocederDefinitivo() {
        
        retroceder();

        removerLigacoes();

    }

    /**
     * retrocede definitivamente as jogadas n vezes a partir deste ponto
     *
     * @param vezes numero de vezes que irá retroceder
     */
    public void retrocederDefinitivo(int vezes) {
        if (jogadaPosterior != null) {
            throw new RuntimeException("Não é possível retroceder a jogada pois existem jogadas posteriores.");
        }

        if (!this.isRoot && vezes > 0) {
            retrocederDefinitivo();
            jogadaAnterior.retrocederDefinitivo(vezes - 1);
        }

    }

}
