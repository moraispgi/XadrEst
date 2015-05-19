/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.XadrEst;

import pt.JogaDeTabuleiro.TipoJogador;
import pt.Xadrez.CorPeca;
import pt.Xadrez.TabuleiroXadrez;
import java.util.Scanner;

/**
 *
 * @author Ricardo
 */
public class Jogo {
    
    
    private final Jogada jogadaRoot = Jogada.criarRoot();
    private final Scanner scanner;
    private final TabuleiroXadrez tabuleiro = new TabuleiroXadrez();
        
    
    private final JogadorXadrEST jogador1;
    private final JogadorXadrEST jogador2;
    
    /**
     * Construtor do jogo, pede ao utilizador os jogadoress
     */
    public Jogo()
    {
        scanner = new Scanner(System.in,"latin1");
        String nome1, nome2;
        TipoJogador tipo1 = TipoJogador.HUMANO;
        TipoJogador tipo2 = TipoJogador.HUMANO;

        System.out.print("Indique o nome do jogador para as brancas: ");
        nome1 = scanner.nextLine();

        int resposta = 0;
        
        
        while (resposta != 1 && resposta != 2) {
            System.out.print("Escolha o tipo do jogador " + nome1 + " [1 - Humano; 2 - Máquina]: ");
            resposta = scanner.nextInt();
            scanner.nextLine();
        }
        
        if (resposta == 1) {
            tipo1 = TipoJogador.HUMANO;
        } else {
            resposta = 0;
            while (resposta != 1 && resposta != 2 && resposta != 3) {
                System.out.print("Escolha o tipo de dificuldade da máquina [1 - Fácil; 2 - Normal; 3 - Dificil]: ");
                resposta = scanner.nextInt();
                scanner.nextLine();
            }
            switch(resposta)
            {
                case 1:
                    tipo1 = TipoJogador.MAQUINA_FACIL;
                    break;
                case 2:
                    tipo1 = TipoJogador.MAQUINA_NORMAL;
                    break;
                case 3:
                    tipo1 = TipoJogador.MAQUINA_DIFICIL;
                    break;
                default:
                    throw new RuntimeException("Problema com a resposta.");
            }
            
            
        }

        System.out.print("Indique o nome do jogador para as pretas: ");
        nome2 = scanner.nextLine();

        int resposta2 = 0;
        while (resposta2 != 1 && resposta2 != 2) {
            System.out.print("Escolha o tipo do jogador " + nome2 + " [1 - Humano; 2 - Máquina]: ");
            resposta2 = scanner.nextInt();
            scanner.nextLine();
        }

        if (resposta2 == 1) {
            tipo2 = TipoJogador.HUMANO;
        } else {
            resposta = 0;
            while (resposta != 1 && resposta != 2 && resposta != 3) {
                System.out.print("Escolha o tipo de dificuldade da máquina [1 - Fácil; 2 - Normal; 3 - Dificil]: ");
                resposta = scanner.nextInt();
                scanner.nextLine();
            }
            switch(resposta)
            {
                case 1:
                    tipo2 = TipoJogador.MAQUINA_FACIL;
                    break;
                case 2:
                    tipo2 = TipoJogador.MAQUINA_NORMAL;
                    break;
                case 3:
                    tipo2 = TipoJogador.MAQUINA_DIFICIL;
                    break;
                default:
                    throw new RuntimeException("Problema com a resposta.");
            }
        }
        
        jogador1 = new JogadorXadrEST(nome1,tipo1);
        jogador1.assignarTabuleiro(tabuleiro,CorPeca.BRANCA);
        jogador2 = new JogadorXadrEST(nome2,tipo2);
        jogador2.assignarTabuleiro(tabuleiro, CorPeca.PRETA);
        
    }
    
    /**
     * Inicia um novo jogo
     */
    public void novoJogo()
    {
        tabuleiro.iniciar();
        jogadaRoot.retrocederDefinitivo(Integer.MAX_VALUE);
        
        JogadorXadrEST jogadorAtual = jogador1;
        while(tabuleiro.getNumeroPecas(CorPeca.PRETA) > 0 &&
                tabuleiro.getNumeroPecas(CorPeca.BRANCA)>0 &&
                !tabuleiro.doisBispos())
        {
            System.out.println(tabuleiro.desenhar(true));
            jogadorAtual.jogar(jogadaRoot);
            
            jogadorAtual = jogadorAtual == jogador1 ? jogador2:jogador1;
            
        }
        
        if(tabuleiro.getNumeroPecas(CorPeca.BRANCA) <= 0)
            System.out.println("Jogador " + jogador2.nome + " Ganhou!");
        else if(tabuleiro.getNumeroPecas(CorPeca.PRETA) <= 0)
            System.out.println("Jogador " + jogador1.nome + " Ganhou!");
        else
            System.out.println("Empate dois bispos em casas diferentes!");

    }
    
    
}
