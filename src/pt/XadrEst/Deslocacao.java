/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.XadrEst;

import pt.Xadrez.Movimento;
import pt.Xadrez.ProxyDivisao;

/**
 *
 * @author Ricardo
 */
public class Deslocacao {

    /**
     * Divisao de origem
     */
    public final ProxyDivisao divisaoOrigem;
    /**
     * Movimento a realizar
     */
    public final Movimento movimento;

    /**
     * Coordenadas de origem e destino
     */
    public final String coordenadas;

    /**
     *
     * @param divisaoOrigem divisao de origem
     * @param movimento movimento a realizar
     */
    public Deslocacao(ProxyDivisao divisaoOrigem, Movimento movimento) {
        if (divisaoOrigem == null) {
            throw new RuntimeException("A divisao de origem tem de existir.");
        }
        if (movimento == null) {
            throw new RuntimeException("O movimento deve existir.");
        }

        this.divisaoOrigem = divisaoOrigem;
        this.movimento = movimento;

        if (!this.divisaoOrigem.canSeguirMovimento(this.movimento)) {
            throw new RuntimeException("O movimento é inválido.");
        }

        coordenadas = divisaoOrigem.coordenada + " "
                + this.divisaoOrigem.getProxyDestino(movimento).coordenada;

    }

}
