/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.Xadrez;

import pt.JogaDeTabuleiro.Peca;
import java.util.Arrays;

/**
 *
 * @author Ricardo
 */
public abstract class PecaXadrez extends Peca implements Cloneable {

    /**
     * Importancia da peça refere ao numero de movimentos que a peça pode
     * realizar.
     */
    public final int importancia;
    /**
     * Cor da peça em questão.
     */
    public final CorPeca corPeca;
    
    private final Movimento[] movimentos;

    /**
     *
     * @param corPeca cor para assignar definitivamente à peça
     * @param movimentos movimentos para assignar definitivamente à peça
     */
    protected PecaXadrez(CorPeca corPeca, Movimento... movimentos) {
        this.movimentos = Arrays.copyOf(movimentos, movimentos.length);
        importancia = movimentos.length;
        this.corPeca = corPeca;
    }

    /**
     *
     * @return Copia dos movimentos da peça em questão
     */
    public Movimento[] getMovimentos() {
        return Arrays.copyOf(movimentos, movimentos.length);
    }
    
    /**
     * 
     * @return nome da peca
     */
    public abstract String getNome();

    /**
     * Clona a peça em questão
     *
     * @return peça clonada;
     * @throws CloneNotSupportedException
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        super.clone();
        return this;
    }

}
