/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.Xadrez;

/**
 * Representa uma direção relativa entre uma divisão e a sua vizinhança
 * @author Ricardo
 */
public enum Direcao {
    /** Em cima à esqueda*/
    TOP_LEFT(0,-1,-1),
    /** Em baixo à direita*/
    BOTTOM_RIGHT(1,1,1),
    /** Em cima à direita*/
    TOP_RIGHT(2,1,-1),
    /** Em baixo à esquerda*/
    BOTTOM_LEFT(3,-1,1),
     /** À esquerda*/
    LEFT(4,-1,0),
    /** À direita*/
    RIGHT(5,1,0),
    /** Em cima*/
    TOP(6,0,-1),
    /** Em baixo*/
    BOTTOM(7,0,1);
    
    /**
     * Inversa da direcao
     */
    public final Direcao inversa;
    /**
     * id da direcao
     */
    public final int id;
    /**
     * valor vectorial x
     */
    public final int x;
    /**
     * valor vectorial y
     */
    public final int y;
    
   
    private Direcao(int id,int x,int y)
    {
        this.x = x;
        this.y = y;
        
        this.id = id;
        
        if(id%2 != 0)
            this.inversa = get(id-1);
        else
            this.inversa = get(id+1);

    }
    
    /**
     * 
     * @param id id da direcao
     * @return direcao
     */
    public Direcao get(int id)
    {   
        switch(id)
        {
            case 0:
                return TOP_LEFT;
            case 1:
                return BOTTOM_RIGHT;
            case 2:
                return TOP_RIGHT;
            case 3:
                return BOTTOM_LEFT;
            case 4:
                return LEFT;
            case 5:
                return RIGHT;
            case 6:
                return TOP;
            case 7:
                return BOTTOM;
            
        }
        return null;
    }
    /**
     * 
     * @return inversa da Direcao
     */
    public Direcao getInversa()
    {
        if(id%2 != 0)
            return get(id-1);
        else
            return get(id+1);
    }

}
