/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package batalhadepeoes;

import java.awt.Color;
import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 *
 * @author Bruno Iochins Grisci
 */
public class Peca {
    
    // Constantes de orientacao das pecas   
    public final int CIMA = 0;
    public final int BAIXO = 1;
    public final int DIREITA = 2;
    public final int ESQUERDA = 3;
    public final int DIAGONAL_DIR_SUP = 4;
    public final int DIAGONAL_DIR_INF = 5;
    public final int DIAGONAL_ESQ_SUP = 6;
    public final int DIAGONAL_ESQ_INF = 7;
    
    private int casa_atual;
    private ImageIcon desenho;
    private String texto;
    private int peso;  
    
    public Peca(){
        
    }
    
    public ImageIcon pegaDesenho(){
        return desenho;
    }
    
    public String pegaTexto(){
        return texto;
    }
        
    public int pegaCasa(){
        return casa_atual;
    }
    
    public int pegaValor(){
        return peso;
    }
    
    public void defineDesenho(ImageIcon novo_desenho){
        desenho = novo_desenho;
    }
    
    public void defineTexto(String novo_texto){
        texto = novo_texto;
    }
    
    public void defineCasa(int nova_casa){
        casa_atual = nova_casa;
    }
    
    public void defineValor (int novo_valor){
        peso = novo_valor;
    }
     
   public void escrevePeca(JLabel nova_casa){        
        // Escreve no label a peca desejada
        Color cor; 
        nova_casa.setHorizontalTextPosition(SwingConstants.CENTER);
        nova_casa.setVerticalTextPosition(SwingConstants.CENTER);
        nova_casa.setFont(new Font("Arial MS", Font.PLAIN, 70));
        //for(int i=0;i<3;i++){
            //cor = nova_casa.getBackground();
            //nova_casa.setBackground(Color.RED);
            //nova_casa.setBackground(cor);
            nova_casa.setText(texto);
       // }
    }
   
   public void trocaCasa(JLabel casa_atual, JLabel casa_do_futuro, int indice_casa_do_futuro){        
           // Remove a peca de casa_atual e a coloca em casa_do_futuro
           casa_atual.setText(null);
           this.escrevePeca(casa_do_futuro);
           this.defineCasa(indice_casa_do_futuro);
   }
   
   public int proximaCasaPeao(int casa_atual, int nro_casas, int orientacao){
        int nova_casa=0;
        if (orientacao == this.BAIXO){
            nova_casa = casa_atual - nro_casas*8;
        }
        else{
            if (orientacao == this.CIMA){
               nova_casa = casa_atual + nro_casas*8;
            }
            else{
                if (orientacao == this.DIAGONAL_DIR_INF){
                    nova_casa = casa_atual - nro_casas*7;                   
                }
                else{
                    if (orientacao == this.DIAGONAL_DIR_SUP){
                        nova_casa = casa_atual + nro_casas*9;
                    }
                    else{
                        if (orientacao == this.DIAGONAL_ESQ_INF){
                            nova_casa = casa_atual - nro_casas*9;
                        }
                        else{
                            if (orientacao == this.DIAGONAL_ESQ_SUP){
                                nova_casa = casa_atual + nro_casas*7;
                            }
                        }
                    }
                }
            }
        }
        return nova_casa;     
   }
   
       public int proximaCasaTorre(int casa_atual, int nro_casas, int orientacao){
        int nova_casa=0;
        if (orientacao == this.BAIXO){
            nova_casa = casa_atual - nro_casas*8;
        }
        else{
            if (orientacao == this.CIMA){
               nova_casa = casa_atual + nro_casas*8;
            }
            else{
                if (orientacao == this.DIREITA){
                    nova_casa = casa_atual + nro_casas;                   
                }
                else{
                    if (orientacao == this.ESQUERDA){
                        nova_casa = casa_atual - nro_casas;
                    }                  
                }
            }
        }
        return nova_casa;     
    }
          
}
