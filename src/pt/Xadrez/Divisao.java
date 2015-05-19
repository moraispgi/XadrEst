/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.Xadrez;

import pt.JogoDeTabuleiro.NodeDivisao;
import pt.JogoDeTabuleiro.Peca;

/**
 * Este classe representa uma casa ou divisão no tabuleiro de xadrez, é um nó pois tem várias ligações às restantes divisoes.
 * @author Ricardo
 */
public class Divisao extends NodeDivisao<Direcao> {
    
    /**
     * coordenada do nó relativa ao tabuleiro que o criou
     */
    public final String coordenada;
    private boolean isSelecionado;

    /**
     * @param coordenada coordenada do tabuleiro relativa a este nó
     */
    public Divisao(String coordenada) {

        if (coordenada == null) {
            throw new RuntimeException("A coordenada deve existir");
        }

        this.coordenada = coordenada;

        isSelecionado = false;
    }
    
    /**
     * O nó tem uma peça?
     * @return true se o node tiver uma peça, false se não
     */
    public boolean hasPeca() {
        return pecas.size() == 1;
    }
    
    /**
     * Vais buscar uma peça se houver
     * @return A peça se existir peça
     */
    public PecaXadrez getPeca() {
        if (pecas.size() == 1) {
            try {
                PecaXadrez peca = (PecaXadrez) pecas.get(0);
                return (PecaXadrez) peca.clone();
            } catch (CloneNotSupportedException ex) {
                throw new RuntimeException("A peça não consegue ser clonada");
            }
        }

        throw new RuntimeException("Não existe Peça.");
    }
    
    /**
     * Insire uma peça valida no nó
     * @param peca peca a inserir valida
     */
    public void inserirPeca(PecaXadrez peca) {
        if (peca == null) {
            throw new RuntimeException("A peça tem que existir.");
        }
        if (pecas.size() < 1) {
            try {
                pecas.add((Peca) peca.clone());
            } catch (CloneNotSupportedException ex) {
                throw new RuntimeException("A peça não consegue ser clonada");
            }
        }

    }
    
    /**
     * remove a peça caso ela existir, se não existirem peças irá dar RuntimeException
     */
    public void removerPeca() {
        if (pecas.isEmpty()) {
            throw new RuntimeException("A peça tem que existir.");
        }

        this.pecas.clear();
    }

    /**
     * Seleciona o nó
     */
    public void selecionar() {
        isSelecionado = true;
    }
    
    /**
     * Deseleciona o nó
     */
    public void deSelecionar() {
        isSelecionado = false;
    }
    
    /**
     * Move uma peça percorrendo as ligaçoes deste nó usando um movimento.
     * @param movimento conjunto de instruções para o movimento da peça
     */
    public void moverPeca(Movimento movimento) {

        if (!consegueIrPara(movimento.getDirecoes())) {
            throw new RuntimeException("Instruções inválidas.");
        }

        Divisao destino = ((Divisao) irPara(movimento.getDirecoes()));

        if (destino.hasPeca()) {
            Peca peca = destino.pecas.get(0);
            if (((PecaXadrez) destino.getPeca()).corPeca == ((PecaXadrez) getPeca()).corPeca) {
                throw new RuntimeException("Não é possível sobrepor uma peça da mesma cor.");
            }

            destino.removerPeca(peca);
        }

        moverPeca(pecas.get(0), movimento.getDirecoes());

    }
    
    /**
     * Cria o desenho gráfico do nó
     * @return o desenho gráfico em formato String
     */
    public String desenhar() {
        String resultado = "";

        if (hasPeca()) {
            resultado += getPeca().desenhar();
        } else {
            if (isSelecionado) {
                resultado += "+";
            } else {
                resultado += "~";
            }
        }

        return resultado;
    }
}
