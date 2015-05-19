/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.XadrEst;

import pt.Xadrez.Movimento;
import pt.Xadrez.ProxyDivisao;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Ricardo
 */
public class Selecao {

    private static boolean isSelecionado = false;

    private static volatile ProxyDivisao original;
    private final static HashMap<ProxyDivisao, Movimento> listaSelecionados = new HashMap<>();

    /**
     * Inicializa a seleção deselecionada
     */
    public Selecao() {
        
    }

    /**
     * Seleciona a divisao
     * @param node Proxy da divisao
     */
    public static void selecionar(ProxyDivisao node) {
        if (!node.hasPeca()) {
            throw new RuntimeException("O nó a selecionar deve ter uma peça");
        }

        deSelecionar();

        Selecao.isSelecionado = true;

        node.selecionar();
        original = node;

        List<Movimento> movimentos = node.getMovimentosValidos();
        movimentos
                .stream()
                .map((movimento) -> {
                    ProxyDivisao destino = node.getProxyDestino(movimento);
                    listaSelecionados.put(destino, movimento);
                    return destino;
                })
                .forEach((destino) -> {
                    destino.selecionar();
                });
    }

    /**
     * 
     * @return hashMap de proxys de divisao e respetivos movimentos que os geraram
     */
    public static HashMap<ProxyDivisao, Movimento> getSelecao() {
        return new HashMap<>(listaSelecionados);
    }

    /**
     * Deseleciona as divisoes selecionadas
     */
    public static synchronized void deSelecionar() {
        Selecao.isSelecionado = false;
        
        if (Selecao.original != null) {
            
            Selecao.original.deSelecionar();
            Selecao.original = null;
        }

        Iterator itr = listaSelecionados.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry pair = (Map.Entry) itr.next();
            ProxyDivisao divisao = (ProxyDivisao) pair.getKey();
            divisao.deSelecionar();
            itr.remove();
        }
    }

    /**
     * 
     * @return true se estiver seleciona, false se não
     */
    public static boolean isSelecionado() {
        return Selecao.isSelecionado;
    }

}
