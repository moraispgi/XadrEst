/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.Xadrez;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma Divisao encapsulada, proxy, para que a original não possa ser
 * alterada diretamente pelo exterior do tabuleiro.
 *
 * @author Ricardo
 */
public class ProxyDivisao {

    /**
     * Coordenada da divisao
     */
    public final String coordenada;

    private final Divisao divisao;
    private PecaXadrez pecaBackup;

    /**
     *
     * @param divisao divisão assossiada à proxy
     */
    public ProxyDivisao(Divisao divisao) {

        if (divisao == null) {
            throw new RuntimeException("A divisão deve existir");
        }

        this.coordenada = divisao.coordenada;
        this.divisao = divisao;
        pecaBackup = null;

    }

    /**
     * Seleciona uma divisão
     */
    public void selecionar() {
        divisao.selecionar();
    }

    /**
     * deseleciona uma divisão
     */
    public void deSelecionar() {
        divisao.deSelecionar();
    }

    /**
     *
     * @return true se a divisão tiver uma peça, false se não
     */
    public boolean hasPeca() {
        return divisao.hasPeca();
    }
    
    /**
     * 
     * @return nome da peça
     */
    public String getNomePeca()
    {
        if(!hasPeca())
            throw new RuntimeException("A peça tem de existir nesta divisão");
            
        return ((PecaXadrez)divisao.getPeca()).getNome();
    }

    /**
     *
     * @return importância da peça
     */
    public int getImportanciaPeca() {
        if (!hasPeca()) {
            throw new RuntimeException("A peça não existe.");
        }

        return divisao.getPeca().importancia;
    }
    /**
     * 
     * @return true se foi criado backup false se não
     */
    public boolean hasBackup()
    {
        return pecaBackup != null;
    }
    
    /**
     * Faz uma salvaguarda de uma peça caso exista uma nesta divisão
     */
    public void backupPeca() {
        if (!hasPeca()) {
            throw new RuntimeException("Não existe peça para efetuar backup");
        }

        pecaBackup = divisao.getPeca();
    }

    /**
     * Restaura uma peça salvaguardada caso não exista peça nesta divisão
     */
    public void restaurarPeca() {
        if (hasPeca()) {
            throw new RuntimeException("Não é possiver repor uma peça pois já existe uma na divisão.");
        }
        if (pecaBackup == null) {
            throw new RuntimeException("Não existe salvaguarda desta peça.");
        }

        this.divisao.inserirPeca(pecaBackup);

        pecaBackup = null;
    }

    /**
     * Move uma peça usando um movimento
     *
     * @param movimento movimento a utilizar
     */
    public void moverPeca(Movimento movimento) {

        if (!divisao.hasPeca()) {
            throw new RuntimeException("Não existe peça na divisão");
        }

        PecaXadrez peca = (PecaXadrez) divisao.getPeca();
        for (Movimento valor : peca.getMovimentos()) {
            if (valor.equals(movimento)) {
                divisao.moverPeca(movimento);
                return;
            }
        }

        throw new RuntimeException("O movimento não existe para esta peça");
    }

    /**
     * Segue um movimento e devolve uma proxy da divisão de destino
     *
     * @param movimento movimento para seguir
     * @return proxy da divisao de destino
     */
    public ProxyDivisao getProxyDestino(Movimento movimento) {
        
        Object objeto = divisao.irPara(movimento.getDirecoes());
        
        if(!(objeto instanceof Divisao))
            throw new RuntimeException("A peça de destino não é uma divisao.");
        
        Divisao divisaoDestino = (Divisao)objeto;
        
        return new ProxyDivisao( divisaoDestino);
    }

    /**
     * Testa se consegue executar movimento
     *
     * @param movimento movimento a ser testado
     * @return true se consegue, false se não
     */
    public boolean canSeguirMovimento(Movimento movimento) {
        try {
            return divisao.irPara(movimento.getDirecoes()) != null;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Verifica e devolve todos os movimentos validos de divisão se tiver peça
     *
     * @return lista de movimentos validos
     */
    public List<Movimento> getMovimentosValidos() {
        if (!divisao.hasPeca()) {
            throw new RuntimeException("O node não tem uma peça");
        }

        PecaXadrez peca = divisao.getPeca();
        Movimento[] movimentos = peca.getMovimentos();

        List<Movimento> resultado = new ArrayList<>();
        for (Movimento movimento : movimentos) {
            if (!divisao.consegueIrPara(movimento.getDirecoes())) {
                continue;
            }

            
            Object objeto = divisao.irPara(movimento.getDirecoes());
        
            if(!(objeto instanceof Divisao))
                throw new RuntimeException("A peça de destino não é uma divisao.");

            Divisao nodeDestino = (Divisao)objeto;

            if (nodeDestino.hasPeca()) {
                PecaXadrez pecaDestino = nodeDestino.getPeca();
                if (pecaDestino.corPeca == peca.corPeca) {
                    continue;
                }
            }
            resultado.add(movimento);
        }
        return resultado;

    }

}
