/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.XadrEst;

import pt.Xadrez.CorPeca;
import static pt.Xadrez.Direcao.*;
import pt.Xadrez.Movimento;
import pt.Xadrez.PecaXadrez;



/**
 *  Representa uma Rainha num jogo de xadrEst
 * @author Ricardo
 */
public class Rainha extends PecaXadrez {

 
    /**
     * 
     * @param corPeca cor da peça
     */
    public Rainha(CorPeca corPeca)
    {
        super(  corPeca,
                new Movimento(TOP_LEFT,TOP_LEFT),
                new Movimento(TOP_RIGHT,TOP_RIGHT),
                new Movimento(BOTTOM_RIGHT,BOTTOM_RIGHT),
                new Movimento(BOTTOM_LEFT,BOTTOM_LEFT),
                new Movimento(TOP_LEFT),
                new Movimento(TOP_RIGHT),
                new Movimento(BOTTOM_LEFT),
                new Movimento(BOTTOM_RIGHT),
                new Movimento(LEFT,LEFT),
                new Movimento(RIGHT,RIGHT),
                new Movimento(TOP,TOP),
                new Movimento(BOTTOM,BOTTOM),
                new Movimento(LEFT),
                new Movimento(RIGHT),
                new Movimento(TOP),
                new Movimento(BOTTOM)
                );


    }
    
    /**
     * 
     * @return String com a sigla da peça
     */
    @Override
    public String desenhar() {
        
        return corPeca == CorPeca.BRANCA ? "R":"r";
        
    }
    /**
     * 
     * @return clone da peça
     * @throws CloneNotSupportedException 
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        
        super.clone();
        
        return new Rainha(corPeca);
    }
    /**
     * 
     * @return nome da peça
     */
    @Override
    public String getNome() {
        return "Rainha";
    }
}
