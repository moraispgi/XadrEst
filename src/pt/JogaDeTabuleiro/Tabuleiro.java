/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.JogaDeTabuleiro;

import java.util.HashMap;

/**
 * Nota:
 * As peças não poderão entrar como argumento do exterior da classe
 * @author Ricardo
 */
public abstract class Tabuleiro {
    /**
     * Hash map que tem todas as divisoes do tabuleiro destiguiveis por uma String
     */
    protected HashMap<String,NodeDivisao> nodes = new HashMap<>();
    
    
}
