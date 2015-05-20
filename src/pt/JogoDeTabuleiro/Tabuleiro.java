/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.JogoDeTabuleiro;

import java.util.HashMap;

/**
 * Nota:
 * As peças não poderão entrar como argumento do exterior da classe
 * @author Ricardo
 * @param <K> tipo que identifica a divisão
 */
public abstract class Tabuleiro<K> {
    /**
     * Hash map que tem todas as divisoes do tabuleiro destiguiveis por uma String
     */
    protected HashMap<K,NodeDivisao> divisoes = new HashMap<>();
    
    
}
