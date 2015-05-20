/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.Xadrez;

import pt.JogoDeTabuleiro.NodeDivisao;
import pt.JogoDeTabuleiro.Tabuleiro;
import pt.XadrEst.Rei;
import pt.XadrEst.Cavalo;
import pt.XadrEst.Torre;
import pt.XadrEst.Bispo;
import pt.XadrEst.Rainha;
import pt.XadrEst.Peao;
import java.util.ArrayList;
import java.util.List;

/**
 * Representação de um tabuleiro de Xadrez com várias divisões.
 * @author Ricardos
 */
public class TabuleiroXadrez extends Tabuleiro<String> {


    

    /**
     * Inicializa todas as peças no tabuleiro e cria as divisões
     */
    public void iniciar() {

        for (char c = 'A'; c <= 'H'; c++) {
            for (int i = 8; i > 0; i--) {
                String id = String.format("%c%d", c, i);
                this.divisoes.put(id, this.criarDivisão(id));
            }
        }

        for (char c = 'A'; c <= 'H'; c++) {
            for (int i = 8; i > 0; i--) {
                
                Object objeto = this.divisoes.get(String.format("%c%d", c, i));
                
                if(!(objeto instanceof Divisao))
                {
                    throw new RuntimeException("O nó não é uma divisão");
                }
                
                Divisao node = (Divisao)objeto;
                if (c != 'A') {
                    node.criarLigacao(Direcao.LEFT, this.divisoes.get(String.format("%c%d", (char) (c - 1), i)));
                    if (i != 8) {
                        node.criarLigacao(Direcao.TOP_LEFT, this.divisoes.get(String.format("%c%d", (char) (c - 1), i + 1)));
                    }
                    if (i != 1) {
                        node.criarLigacao(Direcao.BOTTOM_LEFT, this.divisoes.get(String.format("%c%d", (char) (c - 1), i - 1)));
                    }
                }

                if (c != 'H') {
                    node.criarLigacao(Direcao.RIGHT, this.divisoes.get(String.format("%c%d", (char) (c + 1), i)));
                    if (i != 8) {
                        node.criarLigacao(Direcao.TOP_RIGHT, this.divisoes.get(String.format("%c%d", (char) (c + 1), i + 1)));
                    }
                    if (i != 1) {
                        node.criarLigacao(Direcao.BOTTOM_RIGHT, this.divisoes.get(String.format("%c%d", (char) (c + 1), i - 1)));
                    }
                }
                if (i != 8) {
                    node.criarLigacao(Direcao.TOP, this.divisoes.get(String.format("%c%d", c, i + 1)));
                }
                if (i != 1) {
                    node.criarLigacao(Direcao.BOTTOM, this.divisoes.get(String.format("%c%d", c, i - 1)));
                }

            }
        }

        for (char c = 'A'; c <= 'H'; c++) {
            this.divisoes.get(String.format("%c7", c)).inserirPeca(new Peao(CorPeca.BRANCA));
            this.divisoes.get(String.format("%c2", c)).inserirPeca(new Peao(CorPeca.PRETA));
        }

        this.divisoes.get("A8").inserirPeca(new Torre(CorPeca.BRANCA));
        this.divisoes.get("H8").inserirPeca(new Torre(CorPeca.BRANCA));

        this.divisoes.get("A1").inserirPeca(new Torre(CorPeca.PRETA));
        this.divisoes.get("H1").inserirPeca(new Torre(CorPeca.PRETA));

        this.divisoes.get("B8").inserirPeca(new Cavalo(CorPeca.BRANCA));
        this.divisoes.get("G8").inserirPeca(new Cavalo(CorPeca.BRANCA));

        this.divisoes.get("B1").inserirPeca(new Cavalo(CorPeca.PRETA));
        this.divisoes.get("G1").inserirPeca(new Cavalo(CorPeca.PRETA));

        this.divisoes.get("C8").inserirPeca(new Bispo(CorPeca.BRANCA));
        this.divisoes.get("F8").inserirPeca(new Bispo(CorPeca.BRANCA));

        this.divisoes.get("C1").inserirPeca(new Bispo(CorPeca.PRETA));
        this.divisoes.get("F1").inserirPeca(new Bispo(CorPeca.PRETA));

        this.divisoes.get("D8").inserirPeca(new Rei(CorPeca.BRANCA));
        this.divisoes.get("E8").inserirPeca(new Rainha(CorPeca.BRANCA));

        this.divisoes.get("D1").inserirPeca(new Rei(CorPeca.PRETA));
        this.divisoes.get("E1").inserirPeca(new Rainha(CorPeca.PRETA));

    }
    
    /**
     * Cria uma divisão internamente
     *
     * @param coordenada
     * @return nó criado
     */
    protected NodeDivisao criarDivisão(String coordenada) {
        return new Divisao(coordenada);
    }
    
    /**
     *
     * @param coordenada coordenada da divisão
     * @return uma proxy com a divisão na respetiva coordenada
     */
    public ProxyDivisao gerarProxyDivisao(String coordenada) {
        
        Object objeto = this.divisoes.get(coordenada);
        if(!(objeto instanceof Divisao))
        {
            throw new RuntimeException("O nó não é uma divisão");
        }
        Divisao node = (Divisao) objeto;
        
        return new ProxyDivisao(node);
    }

    /**
     *
     * @param cor cor das peças cuja divisão onde estão será devolvida
     * @return varias proxys que contêm as divisoes onde existem peças da cor
     * recebida
     */
    public List<ProxyDivisao> gerarProxyDivisoes(CorPeca cor) {
        List<ProxyDivisao> resultado = new ArrayList<>();
        for (char c = 'A'; c <= 'H'; c++) {
            for (int i = 8; i > 0; i--) {
                String id = String.format("%c%d", c, i);
                
                Object objeto = this.divisoes.get(id);
                if(!(objeto instanceof Divisao))
                {
                    throw new RuntimeException("O nó não é uma divisão");
                }
                Divisao node = (Divisao) objeto;

                if (node.hasPeca() && ((PecaXadrez) node.getPeca()).corPeca == cor) {
                    resultado.add(new ProxyDivisao(node));
                }
            }
        }

        return resultado;
    }
    /**
     * 
     * @param cor cor das peças
     * @return numero de peças que existem no tabuleiro da cor recebida
     */
    public int getNumeroPecas(CorPeca cor)
    {
        int contador = 0;
        for (char c = 'A'; c <= 'H'; c++) {
            for (int i = 8; i > 0; i--) {
                String id = String.format("%c%d", c, i);
                
                Object objeto = this.divisoes.get(id);
                if(!(objeto instanceof Divisao))
                {
                    throw new RuntimeException("O nó não é uma divisão");
                }
                Divisao node = (Divisao) objeto;
                
                
                if (node.hasPeca() && ((PecaXadrez) node.getPeca()).corPeca == cor) {
                    contador++;
                }
            }
        }
        return contador;
    }
    
    
    
    /**
     * 
     * @param cor cor das peças
     * @return racio do tabuleiro na perspectiva da cor recebida
     */
    public int getRacio(CorPeca cor)
    {
        List<ProxyDivisao> lista1 = gerarProxyDivisoes(cor);
        List<ProxyDivisao> lista2 = gerarProxyDivisoes(cor.getInversa());
        
        int racio=0;
     
        racio = lista1
                .stream()
                .map((proxy) -> proxy.getImportanciaPeca())
                .reduce(racio, Integer::sum);
        
        racio = lista2
                .stream()
                .map((proxy) -> -proxy.getImportanciaPeca())
                .reduce(racio, Integer::sum);
        
        return racio;
    }
    
    /**
     * 
     * @return true se existirem apenas dois bispos em casas diferentes, false se não 
     */
    public boolean doisBispos()
    {
        int numeroDePecas = getNumeroPecas(CorPeca.BRANCA);
        
        if(numeroDePecas != 1)
            return false;
        if(numeroDePecas != getNumeroPecas(CorPeca.PRETA))
            return false;
        
        int primeira = -1;
        
        for (char c = 'A'; c <= 'H'; c++) {
            for (int i = 8; i > 0; i--) {
                String id = String.format("%c%d", c, i);
                
                Object objeto = this.divisoes.get(id);
                if(!(objeto instanceof Divisao))
                {
                    throw new RuntimeException("O nó não é uma divisão");
                }
                Divisao node = (Divisao) objeto;
                
                if (node.hasPeca() && ((PecaXadrez) node.getPeca()).getNome().equals("Bispo")) {
                    int a = c - 65;
                    
                    if(primeira == -1)
                        primeira = (a+i)%2;
                    else
                        return primeira != (a+i)%2;
                        
                        
                    
                }
            }
        }
        
        return false;
    }
    
    
    /**
     * Cria o desenho gráfico do nó
     *
     * @param comEstado true se desenha o estado false se não
     * @return o desenho gráfico em formato String
     */
    public String desenhar(boolean comEstado) {
        StringBuilder buffer = new StringBuilder();
        
        buffer.append("  A B C D E F G H");
        if(comEstado)
            buffer.append("\t-----------------");
        
        buffer.append("\n");
        for (int i = 8; i > 0; i--) {
            buffer.append(i);
            buffer.append(" ");
            for (char c = 'A'; c <= 'H'; c++) {

                String id = String.format("%c%d", c, i);

                Object objeto = this.divisoes.get(id);
                if(!(objeto instanceof Divisao))
                {
                    throw new RuntimeException("O nó não é uma divisão");
                }
                Divisao node = (Divisao) objeto;
                buffer.append(node.desenhar());
                if (c != 'H') {
                    buffer.append(" ");
                }
            }
            buffer.append(" ");
            buffer.append(i);
            if(comEstado)
            {
                int numeroPecas;
                if(i == 8)
                {
                    buffer.append("\t-     ESTADO    -");
                }
                else if(i == 7)
                {
                    numeroPecas = getNumeroPecas(CorPeca.BRANCA);
                    buffer.append("\t-  Brancas: " );
                    buffer.append(numeroPecas);
                    buffer.append("  ");
                    if(numeroPecas<10)
                        buffer.append(" ");
                    buffer.append("-");
                }
                else if(i== 6)
                {
                    numeroPecas = getNumeroPecas(CorPeca.PRETA);
                    buffer.append("\t-  Pretas:  ");
                    buffer.append(numeroPecas);
                    buffer.append("  ");
                    if(numeroPecas<10)
                        buffer.append(" ");
                    buffer.append("-");
                }
                else
                    buffer.append("\t-\t\t-");
            }
            buffer.append("\n");
                
        }
        buffer.append("  A B C D E F G H");
        if(comEstado)
            buffer.append("\t-----------------\n");
        
        return buffer.toString();
    }

}
