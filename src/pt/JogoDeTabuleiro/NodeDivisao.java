/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.JogoDeTabuleiro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Nó que representa uma divisão num tabuleiro genérico
 * @author Ricardo
 * @param <K> Chave representativa de uma ligação
 */
public class NodeDivisao<K> {

    /**
     * Ligaçoes de destino
     */
    protected HashMap<K, NodeDivisao> destinos = new HashMap<>();
    /**
     * Ligaçoes de origem
     */
    protected HashMap<K, NodeDivisao> origens = new HashMap<>();

    /**
     * Lista de peças contidas na divisao
     */
    protected List<Peca> pecas = new ArrayList<>();

    /**
     *
     * @param chave chave da ligação
     * @param node nó de destino
     */
    public void criarLigacao(K chave, NodeDivisao node) {
        if (node == null) {
            throw new RuntimeException("Ligação não pode ser feita a nodes nulos");
        }
        if (this.destinos.containsKey(chave)) {
            throw new RuntimeException("Id da ligação já existe.");
        }
        if (this.destinos.containsValue(node)) {
            throw new RuntimeException("Node da ligação já existe.");
        }

        this.destinos.put(chave, node);

        //Cria a origem desta ligação
        node.origens.put(chave, this);

    }

    /**
     * Corta todas as ligações de destino e de origem
     */
    public void cortarLigacoes() {
        Set<Map.Entry<K, NodeDivisao>> entrySet = destinos.entrySet();
        entrySet.stream().map((entry) -> {
            K idNode = entry.getKey();
            NodeDivisao node = entry.getValue();
            node.cortarLigacao(idNode);
            return idNode;
        }).forEach((idNode) -> {
            destinos.remove(idNode);
        });

        entrySet = origens.entrySet();
        entrySet.stream().map((entry) -> {
            K idNode = entry.getKey();
            NodeDivisao node = entry.getValue();
            node.destinos.remove(idNode);
            return idNode;
        }).forEach((idNode) -> {
            origens.remove(idNode);
        });

    }

    /**
     * Corta uma ligação pela sua chave
     *
     * @param chave chave da ligação
     */
    public void cortarLigacao(K chave) {
        if (chave == null) {
            throw new RuntimeException("Id da ligação não pode ser nulo.");
        }

        if (!this.destinos.containsKey(chave)) {
            throw new RuntimeException("Id não existe.");
        }

        this.destinos.get(chave).origens.remove(chave);

        this.destinos.remove(chave);
    }

    /**
     * Verifica se existe uma ligação com o id recebido
     *
     * @param id id da ligaçao
     * @return true se a ligaçao existir false se nao
     */
    public boolean ligacaoExists(K id) {
        if (id == null) {
            throw new RuntimeException("Nó não pode ser nulo.");
        }

        return this.destinos.containsKey(id);
    }

    /**
     * Segue uma lista de instruções através dos id's das ligações
     *
     * @param chavesLigacao ids de ligações por ordem
     * @return Nó de chegada
     */
    public NodeDivisao irPara(K... chavesLigacao) {
        if (chavesLigacao.length == 0) {
            throw new RuntimeException("Não existe nenhum indice a processar.");
        }

        return irPara(0, chavesLigacao);
    }

    /**
     * Segue um conjunto de chaves de ligação e devolve o nó resultante
     *
     * @param i indice de inicio geralmente 0
     * @param chavesLigacao chaves de ligação para seguir
     * @return nó de chegada
     */
    private NodeDivisao irPara(int i, K... chavesLigacao) {
        if (i < 0) {
            throw new RuntimeException("Indice do array não pode ser negativo.");
        }

        if (i >= chavesLigacao.length) {
            return this;
        }

        if (chavesLigacao[i] == null) {
            throw new RuntimeException("Indice não pode ser nulo");
        }

        return destinos.get(chavesLigacao[i]).irPara(i + 1, chavesLigacao);

    }

    /**
     * Testa se consegue seguir um conjunto de chaves de ligação
     *
     * @param chavesLigacao
     * @return true se conseguir, false se não
     */
    public boolean consegueIrPara(K... chavesLigacao) {
        try {
            irPara(chavesLigacao);
            return true;
        } catch (RuntimeException ex) {
            return false;
        }

    }
    
    /**
     *
     * @return true se este nó é o fim de uma linha
     */
    public boolean isFimDaLinha() {
        return this.destinos.size() <= 1;
    }

    /**
     * Insere a peca no nó
     *
     * @param peca peça a ser inserida
     */
    public void inserirPeca(Peca peca) {
        if (peca == null) {
            throw new RuntimeException("A peça tem que existir.");
        }
        pecas.add(peca);
    }

    /**
     * retira a peca no nó
     *
     * @param peca peca para ser testada a referência
     */
    public void removerPeca(Peca peca) {
        if (peca == null) {
            throw new RuntimeException("A peça tem que existir.");
        }
        if (!pecas.contains(peca)) {
            throw new RuntimeException("A peça com este id não está contida neste nó.");
        }
        pecas.remove(peca);
    }

    /**
     * Vê se existe uma certa peça neste nó
     *
     * @param peca peça a comparar a referência
     * @return true se existir esta peça no nó false se não
     */
    public int hasPeca(Peca peca) {
        if (peca == null) {
            throw new RuntimeException("A peça tem de existir");
        }

        for (int i = 0; i < pecas.size(); i++) {
            if (peca == pecas.get(i)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Move uma peça seguinto um conjunto de chave de ligaº ao
     *
     * @param peca peça a mover
     * @param chavesLigacao conjunto de instruções para mover a peça
     */
    public void moverPeca(Peca peca, K... chavesLigacao) {
        if (peca == null) {
            throw new RuntimeException("A peça tem de existir");
        }
        if (!consegueIrPara(chavesLigacao)) {
            throw new RuntimeException("As instruções não são validas.");
        }
        if (hasPeca(peca) < 0) {
            throw new RuntimeException("A peça tem de existir neste nó.");
        }

        removerPeca(peca);
        irPara(chavesLigacao).inserirPeca(peca);
    }

}
