package pt.JogoDeTabuleiro;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */





/**
 *
 * @author Ricardo
 */
public class Jogador {
    
    /**
     * Tipo de jogador
     */
    protected final TipoJogador tipoJogador;
    /**
     * Tabuleiro associado a este jogador
     */
    protected Tabuleiro tabuleiro;
    
    
    /**
     * 
     * @param tipoJogador tipo de jogador
     */
    public Jogador(TipoJogador tipoJogador)
    {
        this.tipoJogador = tipoJogador;
        
    }
    /**
     * Assgina um tabuleiro ao jogador
     * @param tabuleiro tabuleiro a assignar
     */
    public void assignarTabuleiro(Tabuleiro tabuleiro)
    {
        if(tabuleiro == null)
            throw new RuntimeException("Tabuleiro deve existir");

        this.tabuleiro = tabuleiro;

    }
    
   
    
    
}
