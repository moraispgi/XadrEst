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
 *  Representa um Cavalo num jogo de xadrEst
 * @author Ricardo
 */
public class Cavalo extends PecaXadrez {

    
    /**
     * 
     * @param corPeca cor da peça
     */
    public Cavalo(CorPeca corPeca)
    {
        super(  corPeca,
                
                
                new Movimento(LEFT,LEFT,TOP),
                new Movimento(LEFT,LEFT,BOTTOM),
                new Movimento(RIGHT,RIGHT,TOP),
                new Movimento(RIGHT,RIGHT,BOTTOM),
                new Movimento(TOP,TOP,LEFT),
                new Movimento(TOP,TOP,RIGHT),
                new Movimento(BOTTOM,BOTTOM,LEFT),
                new Movimento(BOTTOM,BOTTOM,RIGHT)
                
               
                );
       
       

    }
    
    /**
     * 
     * @return String com a sigla da peça
     */
    @Override
    public String desenhar() {
        
        return corPeca == CorPeca.BRANCA ? "C":"c";
        
    }
    /**
     * 
     * @return clone da peça
     * @throws CloneNotSupportedException 
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        
        super.clone();
        
        return new Cavalo(corPeca);
    }
    /**
     * 
     * @return nome da peça
     */ 
    @Override
    public String getNome() {
        return "Cavalo";
    }
}
