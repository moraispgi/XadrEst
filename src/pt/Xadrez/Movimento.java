/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.Xadrez;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Representação de um conjunto de intruções relativas à delocação de uma peça
 * por várias divisões
 *
 * @author Ricardo
 */
public final class Movimento {

    private final List<Direcao> listaDirecoes = new ArrayList<>();

    /**
     *
     * @param direcoes conjunto de direções que irão representar definitivamente
     * o movimento
     */
    public Movimento(Direcao... direcoes) {
        for (Direcao direcao : direcoes) {
            listaDirecoes.add(direcao);
        }
    }

    /**
     *
     * @param indice indice que representa a o movimento, este está ordenado,
     * onde 0 é o primerio passo 1 é o segundo e assim sucessivamente.
     * @return a direção relativa ao indice
     */
    public Direcao getDirecao(int indice) {
        return listaDirecoes.get(indice);
    }

    /**
     * @return movimento em forma de array de direções ordenado por ordem de
     * prioridade.
     */
    public Direcao[] getDirecoes() {
        Direcao[] resultado = new Direcao[listaDirecoes.size()];
        listaDirecoes.toArray(resultado);
        return resultado;
    }

    /**
     *
     * @return array de direçoes invertidas
     */
    public Direcao[] getDirecoesInvertidas() {

        Direcao[] resultado = new Direcao[listaDirecoes.size()];
        List<Direcao> lista = new ArrayList<>(listaDirecoes);
        Collections.reverse(lista);
        lista.toArray(resultado);
        
        for(int i=0;i<resultado.length;i++)
        {
            resultado[i] = resultado[i].getInversa();
        }
        
        return resultado;

    }

    /**
     *
     * @return Novo Movimento Invertido
     */
    public Movimento gerarMovimentoInvertido() {
        return new Movimento(getDirecoesInvertidas());
    }

    /**
     *
     * @return numero de istruções relativas ao movimento.
     */
    public int getNumeroDeIntrucoes() {
        return listaDirecoes.size();
    }

    /**
     * Testa se os movimentos são iguais
     *
     * @param movimento movimento a testar
     * @return true se forem iguais, false se não
     */
    @Override
    public boolean equals(Object movimento) {
        
        if(!(movimento instanceof Movimento))
            throw new RuntimeException("O movimento deve ser do tipo Movimento");
        Direcao[] direcoes = ((Movimento)movimento).getDirecoes();

        int x1 = 0;
        int y1 = 0;
        int x2 = 0;
        int y2 = 0;
        
        for (Direcao direcao : direcoes) {
            x1 += direcao.x;
            y1 += direcao.y;
        }
        
        for (Direcao direcao : listaDirecoes) {
            x2 += direcao.x;
            y2 += direcao.y;
        }

        return x1 == x2 && y1 == y2;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.listaDirecoes);
        return hash;
    }

}
