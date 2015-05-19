/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.XadrEst;

import pt.JogoDeTabuleiro.Jogador;
import pt.JogoDeTabuleiro.TipoJogador;
import pt.Xadrez.CorPeca;
import pt.Xadrez.Movimento;
import pt.Xadrez.ProxyDivisao;
import pt.Xadrez.TabuleiroXadrez;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Ricardo
 */
public class JogadorXadrEST extends Jogador {

    private CorPeca cor;
    /**
     * Nome do jogador
     */
    public final String nome;

    /**
     *
     * @param nome nome do jogador
     * @param tipoJogador tipo de jogador
     */
    public JogadorXadrEST(String nome, TipoJogador tipoJogador) {
        super(tipoJogador);

        if (nome == null) {
            throw new RuntimeException("O nome não pode ser null.");
        }

        this.nome = nome;
    }

    /**
     *
     * @param tabuleiro tabuleiro a assignar
     * @param cor cor a assignar
     */
    public void assignarTabuleiro(TabuleiroXadrez tabuleiro, CorPeca cor) {
        super.assignarTabuleiro(tabuleiro);
        if (tabuleiro == null) {
            throw new RuntimeException("Tabuleiro deve existir");
        }
        if (cor == null) {
            throw new RuntimeException("Deve existir uma cor.");
        }

        this.cor = cor;
    }

    /**
     * Efetua uma Jogada
     *
     * @param rootJogada Jogada root
     */
    public void jogar(Jogada rootJogada) {
        
        if (tabuleiro == null || cor == null) {
            throw new RuntimeException("Tabuleiro e cor não foram assignados");
        }
        if (tipoJogador != TipoJogador.HUMANO) {
            jogarMaquina(rootJogada);
            return;
        }

        Deslocacao delocacao = pedirDeslocacao();
        rootJogada
                .getUltima()
                .criarJogadaSeguinte(this, delocacao.divisaoOrigem, delocacao.movimento)
                .executarJogada();

    }

    /**
     * Pede ao utilizador a delocaçao
     *
     * @param selecao selecao
     * @return Devolve as delocaçoes possiveis
     */
    private Deslocacao pedirDeslocacao() {
        List<ProxyDivisao> proxys = ((TabuleiroXadrez) tabuleiro).gerarProxyDivisoes(cor);
        Scanner sc = new Scanner(System.in,"latin1");

        ProxyDivisao proxyOrigem = null;
        do {

            System.out.println("\nPeças disponiveis:\n" + listarCoordenadas(proxys));

            System.out.print("\nInsira a coordenada da peça:");
            String coordenada = sc.nextLine();

            for (ProxyDivisao proxy : proxys) {
                if (coordenada.equals(proxy.coordenada)) {
                    proxyOrigem = proxy;
                    break;
                }
            }
            if (proxyOrigem == null) {
                System.out.println("Coordenada Inválida.");
            }
        } while (proxyOrigem == null);

        System.out.println("\nPeça escolhida: [" + proxyOrigem.getNomePeca() + " - " + proxyOrigem.coordenada + "]");
        Selecao.selecionar(proxyOrigem);

        HashMap<ProxyDivisao, Movimento> destinos = Selecao.getSelecao();
        List<ProxyDivisao> proxysDestino = new ArrayList<>(destinos.keySet());

        ProxyDivisao proxyDestino = null;
        do {

            System.out.println("\n" + ((TabuleiroXadrez) tabuleiro).desenhar(false));

            System.out.println("\nPossibilidades:\n" + listarCoordenadas(proxysDestino));

            System.out.print("\nInsira a coordenada de destino:");
            String coordenada = sc.nextLine();

            for (ProxyDivisao proxy : proxysDestino) {
                if (coordenada.equals(proxy.coordenada)) {
                    proxyDestino = proxy;
                    break;
                }
            }

            if (proxyDestino == null) {
                System.out.println("Coordenada Inválida.");
            }

        } while (proxyDestino == null);

        Selecao.deSelecionar();
        return new Deslocacao(proxyOrigem, destinos.get(proxyDestino));

    }

    /**
     * Efetua a jogada da maquina
     *
     * @param rootJogada jogada root
     */
    private void jogarMaquina(Jogada rootJogada) {
        
        if(cor == null)
            throw new RuntimeException("A cor não foi assignada.");
        
        System.out.println("\nA máquina está a pensar.");
        
        List<Deslocacao> deslocacoes = deslocacoesPossiveis();
        
        int melhorRacio = Integer.MIN_VALUE;
        Deslocacao melhorDeslocacao = deslocacoes.get(0);

        for (Deslocacao deslocacao : deslocacoes) {

            rootJogada.getUltima().criarJogadaSeguinte(this, deslocacao.divisaoOrigem, deslocacao.movimento).executarJogada();

            int camadas;
            switch (tipoJogador) {
                case MAQUINA_FACIL:
                    camadas = 2;
                    break;
                case MAQUINA_NORMAL:
                    camadas = 3;
                    break;
                case MAQUINA_DIFICIL:
                    camadas = 4;
                    break;
                default:
                    throw new RuntimeException("O tipo de jogador não é perceptível.");

            }

            int racio = -alphaBeta(rootJogada, Integer.MIN_VALUE, Integer.MAX_VALUE, camadas, cor, cor.getInversa());
            rootJogada.getUltima().retrocederDefinitivo();

            if (racio > melhorRacio) {
                melhorDeslocacao = deslocacao;
                melhorRacio = racio;
            } else if (racio == melhorRacio) {
                Random rand = new Random();

                if (rand.nextBoolean()) {
                    melhorDeslocacao = deslocacao;
                }
            }

        }

        rootJogada
                .getUltima()
                .criarJogadaSeguinte(this, melhorDeslocacao.divisaoOrigem, melhorDeslocacao.movimento)
                .executarJogada();
    }

    /**
     * Algoritmo NegaMax com Alpha Beta Prunning
     *
     * @param rootJogada root jogada
     * @param alpha valor alpha maximizador
     * @param beta valor beta minimizador
     * @param camada numero de camadas da árvore
     * @param maximizador cor a ser maximizada
     * @param atual cor atual
     * @return racio da jogada
     */
    private int alphaBeta(Jogada rootJogada, int alpha, int beta, int camada, CorPeca maximizador, CorPeca atual) {
        int valor;
        if (atual == maximizador) {
            valor = Integer.MIN_VALUE;
        } else {
            valor = Integer.MAX_VALUE;
        }

        if (camada <= 0) {
            return -((TabuleiroXadrez) tabuleiro).getRacio(atual);
        }

        List<Deslocacao> deslocacoes = deslocacoesPossiveis();

        if (deslocacoes == null) {
            throw new RuntimeException("A lista de deslocações está a null.");
        }

        for (Deslocacao deslocacao : deslocacoes) {
            rootJogada.getUltima().criarJogadaSeguinte(this, deslocacao.divisaoOrigem, deslocacao.movimento).executarJogada();
            int novoValor = alphaBeta(rootJogada, alpha, beta, camada - 1, maximizador, atual.getInversa());
            rootJogada.getUltima().retrocederDefinitivo();

            if (atual == maximizador) {
                valor = novoValor > valor ? novoValor : valor;
                alpha = novoValor > alpha ? novoValor : alpha;

                //Beta cut
                if (valor >= beta) {
                    return valor;
                }
            } else {
                valor = novoValor < valor ? novoValor : valor;
                beta = novoValor < beta ? novoValor : beta;
                if (valor >= beta) {
                    break;
                }

                //Alpha cut
                if (valor <= alpha) {
                    return valor;
                }
            }
        }
        return valor;
    }

    /**
     *
     * @return numero de peças que o jogador tem
     */
    public int getNumeroPecas() {
        return ((TabuleiroXadrez) tabuleiro).getNumeroPecas(cor);
    }

    /**
     *
     * @return lista de deslocações que o jogador pode realizar
     */
    private List<Deslocacao> deslocacoesPossiveis() {

        if (tabuleiro == null || cor == null) {
            throw new RuntimeException("Tabuleiro e cor não foram assignados");
        }

        List<ProxyDivisao> proxys = ((TabuleiroXadrez) tabuleiro).gerarProxyDivisoes(cor);

        List<Deslocacao> deslocacoes = new ArrayList<>();

        proxys
                .stream()
                .forEach(proxy -> {
                    proxy.getMovimentosValidos()
                    .stream()
                    .forEach(movimento -> {
                        deslocacoes.add(new Deslocacao(proxy, movimento));
                    });
                });

        return deslocacoes;
    }

    /**
     *
     * @return String com uma dista de deslocações
     */
    public String listarDeslocacoesPossiveis() {

        if (tabuleiro == null || cor == null) {
            throw new RuntimeException("Tabuleiro e cor não foram assignados");
        }

        String resultado = deslocacoesPossiveis()
                .stream()//Stream
                .map(deslocacao -> "[" + deslocacao.coordenadas + "] ") //mapeia os valores que interessão
                .reduce("", String::concat)// reduz a uma string completa todos os valores
                .trim();

        return resultado;
    }

    /**
     *
     * @param proxys
     * @return lista de coordenadas
     */
    public String listarCoordenadas(List<ProxyDivisao> proxys) {
        if (proxys == null) {
            throw new RuntimeException("A lista de proxys não pode ser null.");
        }
        StringBuilder sb = new StringBuilder();
        String resultado = "";

        for (int i = 0; i < proxys.size(); i++) {
            int numeroColunas = 3;
            if ((i) % numeroColunas == 0 && i != 0) {
                sb.append("\n") ;
            }
            StringBuilder elemento = new StringBuilder();
            int espacamento;
            if (proxys.get(i).hasPeca()) {
                elemento.append("[");
                elemento.append(proxys.get(i).coordenada);
                elemento.append(" - ");
                elemento.append(proxys.get(i).getNomePeca());
                elemento.append("]");
                espacamento = 14;
            } else {
                
                elemento.append("[").append(proxys.get(i).coordenada).append(" - vazio]");
                espacamento = 14;
            }
            

            while (elemento.length() < espacamento) {
                
                elemento.append(" ");
                
            }
            
            sb.append(elemento);
   
            resultado = sb.toString();
            
        }

        return resultado;
    }

}
