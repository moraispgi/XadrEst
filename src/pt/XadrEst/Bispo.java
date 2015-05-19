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
 *  Representa um bispo num jogo de xadrEst
 * @author Ricardo
 */
public class Bispo extends PecaXadrez {

    
    /**
     * 
     * @param corPeca cor da peça
     */
    public Bispo(CorPeca corPeca)
    {
        super(  corPeca,
                new Movimento(TOP_LEFT,TOP_LEFT),
                new Movimento(TOP_RIGHT,TOP_RIGHT),
                new Movimento(BOTTOM_RIGHT,BOTTOM_RIGHT),
                new Movimento(BOTTOM_LEFT,BOTTOM_LEFT),
                new Movimento(TOP_LEFT),
                new Movimento(TOP_RIGHT),
                new Movimento(BOTTOM_LEFT),
                new Movimento(BOTTOM_RIGHT)
                );
       
       

    }
    
    /**
     * 
     * @return String com a sigla da peça
     */
    @Override
    public String desenhar() {
        
        return corPeca == CorPeca.BRANCA ? "B":"b";
        
    }
    /**
     * 
     * @return clone da peça
     * @throws CloneNotSupportedException 
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        
        super.clone();
        
        return new Bispo(corPeca);
    }
    /**
     * 
     * @return nome da peça
     */
    @Override
    public String getNome() {
        return "Bispo";
    }
    
}
