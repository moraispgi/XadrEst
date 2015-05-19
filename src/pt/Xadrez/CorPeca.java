/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.Xadrez;


/**
 * Cor de uma peça
 *
 * @author Ricardo
 */
public enum CorPeca  {

    /**
     * Peça de cor preta
     */
    PRETA,
    /**
     * Peça de cor branca
     */
    BRANCA;
    
    /**
     * 
     * @return inversa da cor
     */
    public CorPeca getInversa()
    {
        return this == BRANCA ? PRETA:BRANCA;
    }
    
        
    
}
