/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package batalhadepeoes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author Bruno Iochins Grisci, Jorge Alberto Wagner Filho
 */
public class InterfaceGrafica extends javax.swing.JFrame {
    
    // Constantes para desenhar pecas de xadrez com strings em unicode  
    private String CODIGO_UNICODE_TORRE_NEGRA = "\u265C";
    private String CODIGO_UNICODE_TORRE_BRANCA = "\u2656";
    private String CODIGO_UNICODE_PEAO_NEGRO = "\u265F";
    private String CODIGO_UNICODE_PEAO_BRANCO = "\u2659";
    
    // Constantes com o numero de pecas iniciais do jogo
    private final int NRO_PEOES = 8;
    private final int NRO_TORRES = 2;
  
    // Constantes para desenho do tabuleiro   
    private final int NRO_COLUNAS = 8;
    private final int NRO_LINHAS = 8;
    
    // Constantes para identificacao das casas
    // Letras indicam coluna, digitos indicam linha no tabuleiro
    private final int A1 = 0;
    private final int B1 = 1;
    private final int C1 = 2;
    private final int D1 = 3;
    private final int E1 = 4;
    private final int F1 = 5;
    private final int G1 = 6;
    private final int H1 = 7;
    private final int A2 = 8;
    private final int B2 = 9;
    private final int C2 = 10;
    private final int D2 = 11;
    private final int E2 = 12;
    private final int F2 = 13;
    private final int G2 = 14;
    private final int H2 = 15;
    private final int A3 = 16;
    private final int B3 = 17;
    private final int C3 = 18;
    private final int D3 = 19;
    private final int E3 = 20;
    private final int F3 = 21;
    private final int G3 = 22;
    private final int H3 = 23;
    private final int A4 = 24;
    private final int B4 = 25;
    private final int C4 = 26;
    private final int D4 = 27;
    private final int E4 = 28;
    private final int F4 = 29;
    private final int G4 = 30;
    private final int H4 = 31;
    private final int A5 = 32;
    private final int B5 = 33;
    private final int C5 = 34;
    private final int D5 = 35;
    private final int E5 = 36;
    private final int F5 = 37;
    private final int G5 = 38;
    private final int H5 = 39;
    private final int A6 = 40;
    private final int B6 = 41;
    private final int C6 = 42;
    private final int D6 = 43;
    private final int E6 = 44;
    private final int F6 = 45;
    private final int G6 = 46;
    private final int H6 = 47;
    private final int A7 = 48;
    private final int B7 = 49;
    private final int C7 = 50;
    private final int D7 = 51;
    private final int E7 = 52;
    private final int F7 = 53;
    private final int G7 = 54;
    private final int H7 = 55;
    private final int A8 = 56;
    private final int B8 = 57;
    private final int C8 = 58;
    private final int D8 = 59;
    private final int E8 = 60;
    private final int F8 = 61;
    private final int G8 = 62;
    private final int H8 = 63;
    
    private final int ULTIMA_CASA = H8;
    
    // Constantes para identificacao das pecas
    // Indices pares indicam pecas brancas, indices impares indicam pecas negras
    // Indice negativo indica ausencia de peca
    private final int CASA_VAZIA = -1;
    private final int PB0 = 0;
    private final int PN0 = 1;
    private final int PB1 = 2;
    private final int PN1 = 3;
    private final int PB2 = 4;
    private final int PN2 = 5;
    private final int PB3 = 6;
    private final int PN3 = 7;
    private final int PB4 = 8;
    private final int PN4 = 9;
    private final int PB5 = 10;
    private final int PN5 = 11;
    private final int PB6 = 12;
    private final int PN6 = 13;
    private final int PB7 = 14;
    private final int PN7 = 15;
    private final int TB0 = 16;
    private final int TN0 = 17;
    private final int TB1 = 18;
    private final int TN1 = 19;
    
    // Constantes de orientacao das pecas   
    public final int CIMA = 0;
    public final int BAIXO = 1;
    public final int DIREITA = 2;
    public final int ESQUERDA = 3;
    public final int DIAGONAL_DIR_SUP = 4;
    public final int DIAGONAL_DIR_INF = 5;
    public final int DIAGONAL_ESQ_SUP = 6;
    public final int DIAGONAL_ESQ_INF = 7;
    public final boolean BRANCO = true;
    public final boolean NEGRO = !BRANCO;
    
    public int SUPORTE = 13;
    public int ARTILHARIA = 13;
    public int BRUTALIDADE = 7;
    public int ALIANCA = 7;
    public int DETENCAO = 1;
    public int MARCHA = 1;
    
    // Variaveis para a base de dados de estados
    HashMap<Integer,ArrayList<BitSet>> baseDeDados = null;   
    HashMap<Integer,Integer> profundidades = null;
    boolean consultaAtiva = false;
    boolean alimentacaoAtiva = false;
    
    // Variaveis para desfazer e refazer
    Stack<ArrayList<BitSet>> memoria = new Stack<>();
    Stack<ArrayList<BitSet>> futuro = new Stack<>();    
    BitSet PBoriginal, TBoriginal, PNoriginal, TNoriginal;
    
    // Arrays, listas e bitsets utilizados
    Peca[] peca = new Peca[2*NRO_PEOES + 2*NRO_TORRES]; // array que guarda as pecas do jogo
    List<JLabel> tabuleiro = new ArrayList<>(); // lista que guarda labels das casas
    BitSet bitabuleiroPB = new BitSet(NRO_COLUNAS*NRO_LINHAS); // bitmap da posicao dos peoes brancos
    BitSet bitabuleiroPN = new BitSet(NRO_COLUNAS*NRO_LINHAS); // bitmap da posicao dos peoes negros
    BitSet bitabuleiroTB = new BitSet(NRO_COLUNAS*NRO_LINHAS); // bitmap da posicao das torres brancas
    BitSet bitabuleiroTN = new BitSet(NRO_COLUNAS*NRO_LINHAS); // bitmap da posicao das torres negras
    BitSet fronteiras_do_universo_esq = new BitSet((NRO_LINHAS)*(NRO_COLUNAS)); // bitmap das bordas do tabuleiro
    BitSet fronteiras_do_universo_dir = new BitSet((NRO_LINHAS)*(NRO_COLUNAS)); // bitmap das bordas do tabuleiro
    int[] pecaDaCasa = new int[NRO_LINHAS*NRO_COLUNAS]; // array que guarda os indices das pecas nas casas do tabuleiro
    Color[] corDaCasa = new Color[NRO_LINHAS*NRO_COLUNAS]; // array que guarda a cor de fundo das casas do tabuleiro
    
    int casa_selecionada = -1; // guarda o indice da primeira casa selecionada pelo mouse
    int casa_destino = -1; // guarda o indice da segunda casa selecionada pelo mouse
    int novaposicaoocupada = CASA_VAZIA;
    int velhaposicaoocupada = CASA_VAZIA;
    int casa_inimiga_selecionada = CASA_VAZIA;
    int casa_vazia_selecionada = CASA_VAZIA;
    
    int profundidade = 5; // guarda a profundidade desejada pelo algoritmo MINIMAX
    
    boolean cor = BRANCO; // cor do time adversario
    boolean primeirojogo = true;
    
    int enpassantBranco = -1;
    int enpassantNegro = -1;
    
    boolean inverterTabuleiro = false;
        
    /**
     * Creates new form InterfaceGrafica
     */
    public InterfaceGrafica() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        PainelTabuleiro = new javax.swing.JPanel();
        casa6A = new javax.swing.JLabel();
        casa8G = new javax.swing.JLabel();
        casa3F = new javax.swing.JLabel();
        casa3D = new javax.swing.JLabel();
        casa2B = new javax.swing.JLabel();
        casa1C = new javax.swing.JLabel();
        casa2H = new javax.swing.JLabel();
        casa7B = new javax.swing.JLabel();
        casa4A = new javax.swing.JLabel();
        casa5F = new javax.swing.JLabel();
        casa8E = new javax.swing.JLabel();
        casa5B = new javax.swing.JLabel();
        casa6E = new javax.swing.JLabel();
        casa4B = new javax.swing.JLabel();
        casa7D = new javax.swing.JLabel();
        casa7C = new javax.swing.JLabel();
        casa7A = new javax.swing.JLabel();
        casa4E = new javax.swing.JLabel();
        casa4G = new javax.swing.JLabel();
        casa1H = new javax.swing.JLabel();
        casa6F = new javax.swing.JLabel();
        casa1F = new javax.swing.JLabel();
        casa8H = new javax.swing.JLabel();
        casa6B = new javax.swing.JLabel();
        casa2A = new javax.swing.JLabel();
        casa8D = new javax.swing.JLabel();
        casa8B = new javax.swing.JLabel();
        casa7E = new javax.swing.JLabel();
        casa8A = new javax.swing.JLabel();
        casa5G = new javax.swing.JLabel();
        casa6D = new javax.swing.JLabel();
        casa8F = new javax.swing.JLabel();
        casa4D = new javax.swing.JLabel();
        casa3E = new javax.swing.JLabel();
        casa6H = new javax.swing.JLabel();
        casa4C = new javax.swing.JLabel();
        casa2D = new javax.swing.JLabel();
        casa6G = new javax.swing.JLabel();
        casa3G = new javax.swing.JLabel();
        casa1E = new javax.swing.JLabel();
        casa7H = new javax.swing.JLabel();
        casa6C = new javax.swing.JLabel();
        casa5C = new javax.swing.JLabel();
        casa3B = new javax.swing.JLabel();
        casa1G = new javax.swing.JLabel();
        casa8C = new javax.swing.JLabel();
        casa4F = new javax.swing.JLabel();
        casa1A = new javax.swing.JLabel();
        casa2C = new javax.swing.JLabel();
        casa3A = new javax.swing.JLabel();
        casa5A = new javax.swing.JLabel();
        casa7F = new javax.swing.JLabel();
        casa7G = new javax.swing.JLabel();
        casa2E = new javax.swing.JLabel();
        casa2F = new javax.swing.JLabel();
        casa5H = new javax.swing.JLabel();
        casa5D = new javax.swing.JLabel();
        casa3H = new javax.swing.JLabel();
        casa1B = new javax.swing.JLabel();
        casa5E = new javax.swing.JLabel();
        casa2G = new javax.swing.JLabel();
        casa1D = new javax.swing.JLabel();
        casa3C = new javax.swing.JLabel();
        casa4H = new javax.swing.JLabel();
        LabelLinha8 = new javax.swing.JLabel();
        LabelLinha7 = new javax.swing.JLabel();
        LabelLinha6 = new javax.swing.JLabel();
        labelLinha5 = new javax.swing.JLabel();
        labelLinha4 = new javax.swing.JLabel();
        labelLinha3 = new javax.swing.JLabel();
        labelLinha2 = new javax.swing.JLabel();
        labelLinha1 = new javax.swing.JLabel();
        labelColunaA = new javax.swing.JLabel();
        labelColunaB = new javax.swing.JLabel();
        labelColunaC = new javax.swing.JLabel();
        labelColunaD = new javax.swing.JLabel();
        labelColunaE = new javax.swing.JLabel();
        labelColunaF = new javax.swing.JLabel();
        labelColunaG = new javax.swing.JLabel();
        labelColunaH = new javax.swing.JLabel();
        labelProfundidade = new javax.swing.JLabel();
        spinnerProfundidade = new javax.swing.JSpinner();
        labelCor = new javax.swing.JLabel();
        botaoCor = new javax.swing.JToggleButton();
        botaoCor.setBackground(Color.WHITE);
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        sliderDetencao = new javax.swing.JSlider();
        sliderAlianca = new javax.swing.JSlider();
        sliderSuporte = new javax.swing.JSlider();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        sliderMarcha = new javax.swing.JSlider();
        sliderBrutalidade = new javax.swing.JSlider();
        sliderArtilharia = new javax.swing.JSlider();
        inverterTabuleiroCheckBox = new javax.swing.JCheckBox();
        consultarBDCheckBox = new javax.swing.JCheckBox();
        alimentarBDCheckBox = new javax.swing.JCheckBox();
        hitOuMissLabel = new javax.swing.JLabel();
        Menu = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        MenuNovoJogo = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        MenuRemoverPeca = new javax.swing.JMenuItem();
        RecortarMenuItem = new javax.swing.JMenuItem();
        ColarMenuItem = new javax.swing.JMenuItem();
        DesfazerMenu = new javax.swing.JMenuItem();
        RefazerMenuItem = new javax.swing.JMenuItem();

        jLabel2.setText("8");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Batalha de Peões -  Bruno Iochins Grisci & Jorge Alberto Wagner Filho");
        setBackground(new java.awt.Color(0, 0, 0));
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocationByPlatform(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                finalizaPrograma(evt);
            }
        });

        casa6A.setBackground(new java.awt.Color(255, 255, 255));
        casa6A.setToolTipText(null);
        casa6A.setEnabled(false);
        casa6A.setMaximumSize(new java.awt.Dimension(50, 50));
        casa6A.setMinimumSize(new java.awt.Dimension(50, 50));
        casa6A.setName(""); // NOI18N
        casa6A.setOpaque(true);
        casa6A.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa6AMouseClicked(evt);
            }
        });

        casa8G.setBackground(new java.awt.Color(255, 255, 255));
        casa8G.setToolTipText(null);
        casa8G.setEnabled(false);
        casa8G.setMaximumSize(new java.awt.Dimension(50, 50));
        casa8G.setMinimumSize(new java.awt.Dimension(50, 50));
        casa8G.setName(""); // NOI18N
        casa8G.setOpaque(true);
        casa8G.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa8GMouseClicked(evt);
            }
        });

        casa3F.setBackground(new java.awt.Color(255, 255, 255));
        casa3F.setToolTipText(null);
        casa3F.setEnabled(false);
        casa3F.setMaximumSize(new java.awt.Dimension(50, 50));
        casa3F.setMinimumSize(new java.awt.Dimension(50, 50));
        casa3F.setName(""); // NOI18N
        casa3F.setOpaque(true);
        casa3F.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa3FMouseClicked(evt);
            }
        });

        casa3D.setBackground(new java.awt.Color(255, 255, 255));
        casa3D.setToolTipText(null);
        casa3D.setEnabled(false);
        casa3D.setMaximumSize(new java.awt.Dimension(50, 50));
        casa3D.setMinimumSize(new java.awt.Dimension(50, 50));
        casa3D.setName(""); // NOI18N
        casa3D.setOpaque(true);
        casa3D.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa3DMouseClicked(evt);
            }
        });

        casa2B.setBackground(new java.awt.Color(153, 153, 153));
        casa2B.setToolTipText(null);
        casa2B.setEnabled(false);
        casa2B.setMaximumSize(new java.awt.Dimension(50, 50));
        casa2B.setMinimumSize(new java.awt.Dimension(50, 50));
        casa2B.setName(""); // NOI18N
        casa2B.setOpaque(true);
        casa2B.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa2BMouseClicked(evt);
            }
        });

        casa1C.setBackground(new java.awt.Color(153, 153, 153));
        casa1C.setToolTipText(null);
        casa1C.setEnabled(false);
        casa1C.setMaximumSize(new java.awt.Dimension(50, 50));
        casa1C.setMinimumSize(new java.awt.Dimension(50, 50));
        casa1C.setName(""); // NOI18N
        casa1C.setOpaque(true);
        casa1C.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa1CMouseClicked(evt);
            }
        });

        casa2H.setBackground(new java.awt.Color(153, 153, 153));
        casa2H.setToolTipText(null);
        casa2H.setEnabled(false);
        casa2H.setMaximumSize(new java.awt.Dimension(50, 50));
        casa2H.setMinimumSize(new java.awt.Dimension(50, 50));
        casa2H.setName(""); // NOI18N
        casa2H.setOpaque(true);
        casa2H.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa2HMouseClicked(evt);
            }
        });

        casa7B.setBackground(new java.awt.Color(255, 255, 255));
        casa7B.setToolTipText(null);
        casa7B.setEnabled(false);
        casa7B.setName(""); // NOI18N
        casa7B.setOpaque(true);
        casa7B.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa7BMouseClicked(evt);
            }
        });

        casa4A.setBackground(new java.awt.Color(255, 255, 255));
        casa4A.setToolTipText(null);
        casa4A.setEnabled(false);
        casa4A.setMaximumSize(new java.awt.Dimension(50, 50));
        casa4A.setMinimumSize(new java.awt.Dimension(50, 50));
        casa4A.setName(""); // NOI18N
        casa4A.setOpaque(true);
        casa4A.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa4AMouseClicked(evt);
            }
        });

        casa5F.setBackground(new java.awt.Color(255, 255, 255));
        casa5F.setToolTipText(null);
        casa5F.setEnabled(false);
        casa5F.setMaximumSize(new java.awt.Dimension(50, 50));
        casa5F.setMinimumSize(new java.awt.Dimension(50, 50));
        casa5F.setName(""); // NOI18N
        casa5F.setOpaque(true);
        casa5F.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa5FMouseClicked(evt);
            }
        });

        casa8E.setBackground(new java.awt.Color(255, 255, 255));
        casa8E.setToolTipText(null);
        casa8E.setEnabled(false);
        casa8E.setMaximumSize(new java.awt.Dimension(50, 50));
        casa8E.setMinimumSize(new java.awt.Dimension(50, 50));
        casa8E.setName(""); // NOI18N
        casa8E.setOpaque(true);
        casa8E.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa8EMouseClicked(evt);
            }
        });

        casa5B.setBackground(new java.awt.Color(255, 255, 255));
        casa5B.setToolTipText(null);
        casa5B.setEnabled(false);
        casa5B.setMaximumSize(new java.awt.Dimension(50, 50));
        casa5B.setMinimumSize(new java.awt.Dimension(50, 50));
        casa5B.setName(""); // NOI18N
        casa5B.setOpaque(true);
        casa5B.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa5BMouseClicked(evt);
            }
        });

        casa6E.setBackground(new java.awt.Color(255, 255, 255));
        casa6E.setToolTipText(null);
        casa6E.setEnabled(false);
        casa6E.setMaximumSize(new java.awt.Dimension(50, 50));
        casa6E.setMinimumSize(new java.awt.Dimension(50, 50));
        casa6E.setName(""); // NOI18N
        casa6E.setOpaque(true);
        casa6E.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa6EMouseClicked(evt);
            }
        });

        casa4B.setBackground(new java.awt.Color(153, 153, 153));
        casa4B.setToolTipText(null);
        casa4B.setEnabled(false);
        casa4B.setMaximumSize(new java.awt.Dimension(50, 50));
        casa4B.setMinimumSize(new java.awt.Dimension(50, 50));
        casa4B.setName(""); // NOI18N
        casa4B.setOpaque(true);
        casa4B.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa4BMouseClicked(evt);
            }
        });

        casa7D.setBackground(new java.awt.Color(255, 255, 255));
        casa7D.setToolTipText(null);
        casa7D.setEnabled(false);
        casa7D.setMaximumSize(new java.awt.Dimension(50, 50));
        casa7D.setMinimumSize(new java.awt.Dimension(50, 50));
        casa7D.setName(""); // NOI18N
        casa7D.setOpaque(true);
        casa7D.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa7DMouseClicked(evt);
            }
        });

        casa7C.setBackground(new java.awt.Color(153, 153, 153));
        casa7C.setToolTipText(null);
        casa7C.setEnabled(false);
        casa7C.setName(""); // NOI18N
        casa7C.setOpaque(true);
        casa7C.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa7CMouseClicked(evt);
            }
        });

        casa7A.setBackground(new java.awt.Color(153, 153, 153));
        casa7A.setToolTipText(null);
        casa7A.setEnabled(false);
        casa7A.setName(""); // NOI18N
        casa7A.setOpaque(true);
        casa7A.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa7AMouseClicked(evt);
            }
        });

        casa4E.setBackground(new java.awt.Color(255, 255, 255));
        casa4E.setToolTipText(null);
        casa4E.setEnabled(false);
        casa4E.setMaximumSize(new java.awt.Dimension(50, 50));
        casa4E.setMinimumSize(new java.awt.Dimension(50, 50));
        casa4E.setName(""); // NOI18N
        casa4E.setOpaque(true);
        casa4E.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa4EMouseClicked(evt);
            }
        });

        casa4G.setBackground(new java.awt.Color(255, 255, 255));
        casa4G.setToolTipText(null);
        casa4G.setEnabled(false);
        casa4G.setMaximumSize(new java.awt.Dimension(50, 50));
        casa4G.setMinimumSize(new java.awt.Dimension(50, 50));
        casa4G.setName(""); // NOI18N
        casa4G.setOpaque(true);
        casa4G.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa4GMouseClicked(evt);
            }
        });

        casa1H.setBackground(new java.awt.Color(255, 255, 255));
        casa1H.setToolTipText(null);
        casa1H.setEnabled(false);
        casa1H.setMaximumSize(new java.awt.Dimension(50, 50));
        casa1H.setMinimumSize(new java.awt.Dimension(50, 50));
        casa1H.setName(""); // NOI18N
        casa1H.setOpaque(true);
        casa1H.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa1HMouseClicked(evt);
            }
        });

        casa6F.setBackground(new java.awt.Color(153, 153, 153));
        casa6F.setToolTipText(null);
        casa6F.setEnabled(false);
        casa6F.setMaximumSize(new java.awt.Dimension(50, 50));
        casa6F.setMinimumSize(new java.awt.Dimension(50, 50));
        casa6F.setName(""); // NOI18N
        casa6F.setOpaque(true);
        casa6F.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa6FMouseClicked(evt);
            }
        });

        casa1F.setBackground(new java.awt.Color(255, 255, 255));
        casa1F.setToolTipText(null);
        casa1F.setEnabled(false);
        casa1F.setMaximumSize(new java.awt.Dimension(50, 50));
        casa1F.setMinimumSize(new java.awt.Dimension(50, 50));
        casa1F.setName(""); // NOI18N
        casa1F.setOpaque(true);
        casa1F.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa1FMouseClicked(evt);
            }
        });

        casa8H.setBackground(new java.awt.Color(153, 153, 153));
        casa8H.setToolTipText(null);
        casa8H.setEnabled(false);
        casa8H.setName(""); // NOI18N
        casa8H.setOpaque(true);
        casa8H.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa8HMouseClicked(evt);
            }
        });

        casa6B.setBackground(new java.awt.Color(153, 153, 153));
        casa6B.setToolTipText(null);
        casa6B.setEnabled(false);
        casa6B.setMaximumSize(new java.awt.Dimension(50, 50));
        casa6B.setMinimumSize(new java.awt.Dimension(50, 50));
        casa6B.setName(""); // NOI18N
        casa6B.setOpaque(true);
        casa6B.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa6BMouseClicked(evt);
            }
        });

        casa2A.setBackground(new java.awt.Color(255, 255, 255));
        casa2A.setToolTipText(null);
        casa2A.setMaximumSize(new java.awt.Dimension(50, 50));
        casa2A.setMinimumSize(new java.awt.Dimension(50, 50));
        casa2A.setName(""); // NOI18N
        casa2A.setOpaque(true);
        casa2A.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa2AMouseClicked(evt);
            }
        });

        casa8D.setBackground(new java.awt.Color(153, 153, 153));
        casa8D.setToolTipText(null);
        casa8D.setEnabled(false);
        casa8D.setMaximumSize(new java.awt.Dimension(50, 50));
        casa8D.setMinimumSize(new java.awt.Dimension(50, 50));
        casa8D.setName(""); // NOI18N
        casa8D.setOpaque(true);
        casa8D.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa8DMouseClicked(evt);
            }
        });

        casa8B.setBackground(new java.awt.Color(153, 153, 153));
        casa8B.setToolTipText(null);
        casa8B.setEnabled(false);
        casa8B.setMaximumSize(new java.awt.Dimension(50, 50));
        casa8B.setMinimumSize(new java.awt.Dimension(50, 50));
        casa8B.setName(""); // NOI18N
        casa8B.setOpaque(true);
        casa8B.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa8BMouseClicked(evt);
            }
        });

        casa7E.setBackground(new java.awt.Color(153, 153, 153));
        casa7E.setToolTipText(null);
        casa7E.setEnabled(false);
        casa7E.setMaximumSize(new java.awt.Dimension(50, 50));
        casa7E.setMinimumSize(new java.awt.Dimension(50, 50));
        casa7E.setName(""); // NOI18N
        casa7E.setOpaque(true);
        casa7E.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa7EMouseClicked(evt);
            }
        });

        casa8A.setBackground(new java.awt.Color(255, 255, 255));
        casa8A.setToolTipText(null);
        casa8A.setEnabled(false);
        casa8A.setName(""); // NOI18N
        casa8A.setOpaque(true);
        casa8A.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa8AMouseClicked(evt);
            }
        });

        casa5G.setBackground(new java.awt.Color(153, 153, 153));
        casa5G.setToolTipText(null);
        casa5G.setEnabled(false);
        casa5G.setMaximumSize(new java.awt.Dimension(50, 50));
        casa5G.setMinimumSize(new java.awt.Dimension(50, 50));
        casa5G.setName(""); // NOI18N
        casa5G.setOpaque(true);
        casa5G.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa5GMouseClicked(evt);
            }
        });

        casa6D.setBackground(new java.awt.Color(153, 153, 153));
        casa6D.setToolTipText(null);
        casa6D.setEnabled(false);
        casa6D.setMaximumSize(new java.awt.Dimension(50, 50));
        casa6D.setMinimumSize(new java.awt.Dimension(50, 50));
        casa6D.setName(""); // NOI18N
        casa6D.setOpaque(true);
        casa6D.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa6DMouseClicked(evt);
            }
        });

        casa8F.setBackground(new java.awt.Color(153, 153, 153));
        casa8F.setToolTipText(null);
        casa8F.setEnabled(false);
        casa8F.setMaximumSize(new java.awt.Dimension(50, 50));
        casa8F.setMinimumSize(new java.awt.Dimension(50, 50));
        casa8F.setName(""); // NOI18N
        casa8F.setOpaque(true);
        casa8F.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa8FMouseClicked(evt);
            }
        });

        casa4D.setBackground(new java.awt.Color(153, 153, 153));
        casa4D.setToolTipText(null);
        casa4D.setEnabled(false);
        casa4D.setMaximumSize(new java.awt.Dimension(50, 50));
        casa4D.setMinimumSize(new java.awt.Dimension(50, 50));
        casa4D.setName(""); // NOI18N
        casa4D.setOpaque(true);
        casa4D.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa4DMouseClicked(evt);
            }
        });

        casa3E.setBackground(new java.awt.Color(153, 153, 153));
        casa3E.setToolTipText(null);
        casa3E.setEnabled(false);
        casa3E.setMaximumSize(new java.awt.Dimension(50, 50));
        casa3E.setMinimumSize(new java.awt.Dimension(50, 50));
        casa3E.setName(""); // NOI18N
        casa3E.setOpaque(true);
        casa3E.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa3EMouseClicked(evt);
            }
        });

        casa6H.setBackground(new java.awt.Color(153, 153, 153));
        casa6H.setToolTipText(null);
        casa6H.setEnabled(false);
        casa6H.setMaximumSize(new java.awt.Dimension(50, 50));
        casa6H.setMinimumSize(new java.awt.Dimension(50, 50));
        casa6H.setName(""); // NOI18N
        casa6H.setOpaque(true);
        casa6H.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa6HMouseClicked(evt);
            }
        });

        casa4C.setBackground(new java.awt.Color(255, 255, 255));
        casa4C.setToolTipText(null);
        casa4C.setEnabled(false);
        casa4C.setMaximumSize(new java.awt.Dimension(50, 50));
        casa4C.setMinimumSize(new java.awt.Dimension(50, 50));
        casa4C.setName(""); // NOI18N
        casa4C.setOpaque(true);
        casa4C.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa4CMouseClicked(evt);
            }
        });

        casa2D.setBackground(new java.awt.Color(153, 153, 153));
        casa2D.setToolTipText(null);
        casa2D.setEnabled(false);
        casa2D.setMaximumSize(new java.awt.Dimension(50, 50));
        casa2D.setMinimumSize(new java.awt.Dimension(50, 50));
        casa2D.setName(""); // NOI18N
        casa2D.setOpaque(true);
        casa2D.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa2DMouseClicked(evt);
            }
        });

        casa6G.setBackground(new java.awt.Color(255, 255, 255));
        casa6G.setToolTipText(null);
        casa6G.setEnabled(false);
        casa6G.setMaximumSize(new java.awt.Dimension(50, 50));
        casa6G.setMinimumSize(new java.awt.Dimension(50, 50));
        casa6G.setName(""); // NOI18N
        casa6G.setOpaque(true);
        casa6G.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa6GMouseClicked(evt);
            }
        });

        casa3G.setBackground(new java.awt.Color(153, 153, 153));
        casa3G.setToolTipText(null);
        casa3G.setEnabled(false);
        casa3G.setMaximumSize(new java.awt.Dimension(50, 50));
        casa3G.setMinimumSize(new java.awt.Dimension(50, 50));
        casa3G.setName(""); // NOI18N
        casa3G.setOpaque(true);
        casa3G.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa3GMouseClicked(evt);
            }
        });

        casa1E.setBackground(new java.awt.Color(153, 153, 153));
        casa1E.setToolTipText(null);
        casa1E.setEnabled(false);
        casa1E.setMaximumSize(new java.awt.Dimension(50, 50));
        casa1E.setMinimumSize(new java.awt.Dimension(50, 50));
        casa1E.setName(""); // NOI18N
        casa1E.setOpaque(true);
        casa1E.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa1EMouseClicked(evt);
            }
        });

        casa7H.setBackground(new java.awt.Color(255, 255, 255));
        casa7H.setToolTipText(null);
        casa7H.setEnabled(false);
        casa7H.setMaximumSize(new java.awt.Dimension(50, 50));
        casa7H.setMinimumSize(new java.awt.Dimension(50, 50));
        casa7H.setName(""); // NOI18N
        casa7H.setOpaque(true);
        casa7H.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa7HMouseClicked(evt);
            }
        });

        casa6C.setBackground(new java.awt.Color(255, 255, 255));
        casa6C.setToolTipText(null);
        casa6C.setEnabled(false);
        casa6C.setMaximumSize(new java.awt.Dimension(50, 50));
        casa6C.setMinimumSize(new java.awt.Dimension(50, 50));
        casa6C.setName(""); // NOI18N
        casa6C.setOpaque(true);
        casa6C.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa6CMouseClicked(evt);
            }
        });

        casa5C.setBackground(new java.awt.Color(153, 153, 153));
        casa5C.setToolTipText(null);
        casa5C.setEnabled(false);
        casa5C.setMaximumSize(new java.awt.Dimension(50, 50));
        casa5C.setMinimumSize(new java.awt.Dimension(50, 50));
        casa5C.setName(""); // NOI18N
        casa5C.setOpaque(true);
        casa5C.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa5CMouseClicked(evt);
            }
        });

        casa3B.setBackground(new java.awt.Color(255, 255, 255));
        casa3B.setToolTipText(null);
        casa3B.setEnabled(false);
        casa3B.setMaximumSize(new java.awt.Dimension(50, 50));
        casa3B.setMinimumSize(new java.awt.Dimension(50, 50));
        casa3B.setName(""); // NOI18N
        casa3B.setOpaque(true);
        casa3B.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa3BMouseClicked(evt);
            }
        });

        casa1G.setBackground(new java.awt.Color(153, 153, 153));
        casa1G.setToolTipText(null);
        casa1G.setEnabled(false);
        casa1G.setMaximumSize(new java.awt.Dimension(50, 50));
        casa1G.setMinimumSize(new java.awt.Dimension(50, 50));
        casa1G.setName(""); // NOI18N
        casa1G.setOpaque(true);
        casa1G.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa1GMouseClicked(evt);
            }
        });

        casa8C.setBackground(new java.awt.Color(255, 255, 255));
        casa8C.setToolTipText(null);
        casa8C.setEnabled(false);
        casa8C.setMaximumSize(new java.awt.Dimension(50, 50));
        casa8C.setMinimumSize(new java.awt.Dimension(50, 50));
        casa8C.setName(""); // NOI18N
        casa8C.setOpaque(true);
        casa8C.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa8CMouseClicked(evt);
            }
        });

        casa4F.setBackground(new java.awt.Color(153, 153, 153));
        casa4F.setToolTipText(null);
        casa4F.setEnabled(false);
        casa4F.setMaximumSize(new java.awt.Dimension(50, 50));
        casa4F.setMinimumSize(new java.awt.Dimension(50, 50));
        casa4F.setName(""); // NOI18N
        casa4F.setOpaque(true);
        casa4F.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa4FMouseClicked(evt);
            }
        });

        casa1A.setBackground(new java.awt.Color(153, 153, 153));
        casa1A.setToolTipText(null);
        casa1A.setEnabled(false);
        casa1A.setMinimumSize(new java.awt.Dimension(50, 50));
        casa1A.setName(""); // NOI18N
        casa1A.setOpaque(true);
        casa1A.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa1AMouseClicked(evt);
            }
        });

        casa2C.setBackground(new java.awt.Color(255, 255, 255));
        casa2C.setToolTipText(null);
        casa2C.setEnabled(false);
        casa2C.setMaximumSize(new java.awt.Dimension(50, 50));
        casa2C.setMinimumSize(new java.awt.Dimension(50, 50));
        casa2C.setName(""); // NOI18N
        casa2C.setOpaque(true);
        casa2C.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa2CMouseClicked(evt);
            }
        });

        casa3A.setBackground(new java.awt.Color(153, 153, 153));
        casa3A.setToolTipText(null);
        casa3A.setEnabled(false);
        casa3A.setMaximumSize(new java.awt.Dimension(50, 50));
        casa3A.setMinimumSize(new java.awt.Dimension(50, 50));
        casa3A.setName(""); // NOI18N
        casa3A.setOpaque(true);
        casa3A.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa3AMouseClicked(evt);
            }
        });

        casa5A.setBackground(new java.awt.Color(153, 153, 153));
        casa5A.setToolTipText(null);
        casa5A.setEnabled(false);
        casa5A.setMaximumSize(new java.awt.Dimension(50, 50));
        casa5A.setMinimumSize(new java.awt.Dimension(50, 50));
        casa5A.setName(""); // NOI18N
        casa5A.setOpaque(true);
        casa5A.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa5AMouseClicked(evt);
            }
        });

        casa7F.setBackground(new java.awt.Color(255, 255, 255));
        casa7F.setToolTipText(null);
        casa7F.setEnabled(false);
        casa7F.setMaximumSize(new java.awt.Dimension(50, 50));
        casa7F.setMinimumSize(new java.awt.Dimension(50, 50));
        casa7F.setName(""); // NOI18N
        casa7F.setOpaque(true);
        casa7F.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa7FMouseClicked(evt);
            }
        });

        casa7G.setBackground(new java.awt.Color(153, 153, 153));
        casa7G.setToolTipText(null);
        casa7G.setEnabled(false);
        casa7G.setMaximumSize(new java.awt.Dimension(50, 50));
        casa7G.setMinimumSize(new java.awt.Dimension(50, 50));
        casa7G.setName(""); // NOI18N
        casa7G.setOpaque(true);
        casa7G.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa7GMouseClicked(evt);
            }
        });

        casa2E.setBackground(new java.awt.Color(255, 255, 255));
        casa2E.setToolTipText(null);
        casa2E.setEnabled(false);
        casa2E.setMaximumSize(new java.awt.Dimension(50, 50));
        casa2E.setMinimumSize(new java.awt.Dimension(50, 50));
        casa2E.setName(""); // NOI18N
        casa2E.setOpaque(true);
        casa2E.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa2EMouseClicked(evt);
            }
        });

        casa2F.setBackground(new java.awt.Color(153, 153, 153));
        casa2F.setToolTipText(null);
        casa2F.setEnabled(false);
        casa2F.setMaximumSize(new java.awt.Dimension(50, 50));
        casa2F.setMinimumSize(new java.awt.Dimension(50, 50));
        casa2F.setName(""); // NOI18N
        casa2F.setOpaque(true);
        casa2F.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa2FMouseClicked(evt);
            }
        });

        casa5H.setBackground(new java.awt.Color(255, 255, 255));
        casa5H.setToolTipText(null);
        casa5H.setEnabled(false);
        casa5H.setMaximumSize(new java.awt.Dimension(50, 50));
        casa5H.setMinimumSize(new java.awt.Dimension(50, 50));
        casa5H.setName(""); // NOI18N
        casa5H.setOpaque(true);
        casa5H.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa5HMouseClicked(evt);
            }
        });

        casa5D.setBackground(new java.awt.Color(255, 255, 255));
        casa5D.setToolTipText(null);
        casa5D.setEnabled(false);
        casa5D.setMaximumSize(new java.awt.Dimension(50, 50));
        casa5D.setMinimumSize(new java.awt.Dimension(50, 50));
        casa5D.setName(""); // NOI18N
        casa5D.setOpaque(true);
        casa5D.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa5DMouseClicked(evt);
            }
        });

        casa3H.setBackground(new java.awt.Color(255, 255, 255));
        casa3H.setToolTipText(null);
        casa3H.setEnabled(false);
        casa3H.setMaximumSize(new java.awt.Dimension(50, 50));
        casa3H.setMinimumSize(new java.awt.Dimension(50, 50));
        casa3H.setName(""); // NOI18N
        casa3H.setOpaque(true);
        casa3H.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa3HMouseClicked(evt);
            }
        });

        casa1B.setBackground(new java.awt.Color(255, 255, 255));
        casa1B.setToolTipText(null);
        casa1B.setEnabled(false);
        casa1B.setMaximumSize(new java.awt.Dimension(50, 50));
        casa1B.setMinimumSize(new java.awt.Dimension(50, 50));
        casa1B.setName(""); // NOI18N
        casa1B.setOpaque(true);
        casa1B.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa1BMouseClicked(evt);
            }
        });

        casa5E.setBackground(new java.awt.Color(153, 153, 153));
        casa5E.setToolTipText(null);
        casa5E.setEnabled(false);
        casa5E.setMaximumSize(new java.awt.Dimension(50, 50));
        casa5E.setMinimumSize(new java.awt.Dimension(50, 50));
        casa5E.setName(""); // NOI18N
        casa5E.setOpaque(true);
        casa5E.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa5EMouseClicked(evt);
            }
        });

        casa2G.setBackground(new java.awt.Color(255, 255, 255));
        casa2G.setToolTipText(null);
        casa2G.setEnabled(false);
        casa2G.setMaximumSize(new java.awt.Dimension(50, 50));
        casa2G.setMinimumSize(new java.awt.Dimension(50, 50));
        casa2G.setName(""); // NOI18N
        casa2G.setOpaque(true);
        casa2G.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa2GMouseClicked(evt);
            }
        });

        casa1D.setBackground(new java.awt.Color(255, 255, 255));
        casa1D.setToolTipText(null);
        casa1D.setEnabled(false);
        casa1D.setMaximumSize(new java.awt.Dimension(50, 50));
        casa1D.setMinimumSize(new java.awt.Dimension(50, 50));
        casa1D.setName(""); // NOI18N
        casa1D.setOpaque(true);
        casa1D.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa1DMouseClicked(evt);
            }
        });

        casa3C.setBackground(new java.awt.Color(153, 153, 153));
        casa3C.setToolTipText(null);
        casa3C.setEnabled(false);
        casa3C.setMaximumSize(new java.awt.Dimension(50, 50));
        casa3C.setMinimumSize(new java.awt.Dimension(50, 50));
        casa3C.setName(""); // NOI18N
        casa3C.setOpaque(true);
        casa3C.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa3CMouseClicked(evt);
            }
        });

        casa4H.setBackground(new java.awt.Color(153, 153, 153));
        casa4H.setToolTipText(null);
        casa4H.setEnabled(false);
        casa4H.setMaximumSize(new java.awt.Dimension(50, 50));
        casa4H.setMinimumSize(new java.awt.Dimension(50, 50));
        casa4H.setName(""); // NOI18N
        casa4H.setOpaque(true);
        casa4H.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa4HMouseClicked(evt);
            }
        });

        LabelLinha8.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        LabelLinha8.setText("   8");
        LabelLinha8.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        LabelLinha7.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        LabelLinha7.setText("   7");
        LabelLinha7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        LabelLinha7.setInheritsPopupMenu(false);

        LabelLinha6.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        LabelLinha6.setText("   6");

        labelLinha5.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        labelLinha5.setText("   5");

        labelLinha4.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        labelLinha4.setText("   4");

        labelLinha3.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        labelLinha3.setText("   3");

        labelLinha2.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        labelLinha2.setText("   2");

        labelLinha1.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        labelLinha1.setText("   1");

        labelColunaA.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        labelColunaA.setText("  A");

        labelColunaB.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        labelColunaB.setText("  B");

        labelColunaC.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        labelColunaC.setText("  C");

        labelColunaD.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        labelColunaD.setText("  D");

        labelColunaE.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        labelColunaE.setText("  E");

        labelColunaF.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        labelColunaF.setText("  F");

        labelColunaG.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        labelColunaG.setText("  G");

        labelColunaH.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        labelColunaH.setText("  H");

        javax.swing.GroupLayout PainelTabuleiroLayout = new javax.swing.GroupLayout(PainelTabuleiro);
        PainelTabuleiro.setLayout(PainelTabuleiroLayout);
        PainelTabuleiroLayout.setHorizontalGroup(
            PainelTabuleiroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelTabuleiroLayout.createSequentialGroup()
                .addGroup(PainelTabuleiroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelTabuleiroLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(PainelTabuleiroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(PainelTabuleiroLayout.createSequentialGroup()
                                .addComponent(LabelLinha7)
                                .addGap(0, 0, 0)
                                .addComponent(casa7A, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa7B, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa7C, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa7D, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa7E, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa7F, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa7G, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa7H, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PainelTabuleiroLayout.createSequentialGroup()
                                .addComponent(labelLinha5)
                                .addGap(0, 0, 0)
                                .addComponent(casa5A, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa5B, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa5C, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa5D, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa5E, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa5F, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa5G, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa5H, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PainelTabuleiroLayout.createSequentialGroup()
                                .addComponent(LabelLinha6)
                                .addGap(0, 0, 0)
                                .addComponent(casa6A, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa6B, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa6C, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa6D, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa6E, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa6F, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa6G, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa6H, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PainelTabuleiroLayout.createSequentialGroup()
                                .addComponent(labelLinha1)
                                .addGap(0, 0, 0)
                                .addComponent(casa1A, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa1B, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa1C, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa1D, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa1E, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa1F, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa1G, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa1H, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PainelTabuleiroLayout.createSequentialGroup()
                                .addComponent(labelLinha4)
                                .addGap(0, 0, 0)
                                .addComponent(casa4A, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa4B, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa4C, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa4D, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa4E, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa4F, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa4G, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa4H, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PainelTabuleiroLayout.createSequentialGroup()
                                .addComponent(labelLinha2)
                                .addGap(0, 0, 0)
                                .addComponent(casa2A, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa2B, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa2C, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa2D, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa2E, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa2F, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa2G, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa2H, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PainelTabuleiroLayout.createSequentialGroup()
                                .addComponent(LabelLinha8)
                                .addGap(0, 0, 0)
                                .addComponent(casa8A, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa8B, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa8C, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa8D, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa8E, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa8F, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa8G, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa8H, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PainelTabuleiroLayout.createSequentialGroup()
                                .addComponent(labelLinha3)
                                .addGap(0, 0, 0)
                                .addComponent(casa3A, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa3B, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa3C, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa3D, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa3E, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa3F, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa3G, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(casa3H, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(PainelTabuleiroLayout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(labelColunaA)
                        .addGap(0, 0, 0)
                        .addComponent(labelColunaB)
                        .addGap(0, 0, 0)
                        .addComponent(labelColunaC)
                        .addGap(0, 0, 0)
                        .addComponent(labelColunaD)
                        .addGap(0, 0, 0)
                        .addComponent(labelColunaE)
                        .addGap(0, 0, 0)
                        .addComponent(labelColunaF)
                        .addGap(0, 0, 0)
                        .addComponent(labelColunaG)
                        .addGap(0, 0, 0)
                        .addComponent(labelColunaH)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PainelTabuleiroLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {LabelLinha6, LabelLinha7, LabelLinha8, casa1A, casa1B, casa1C, casa1D, casa1E, casa1F, casa1G, casa1H, casa2A, casa2B, casa2C, casa2D, casa2E, casa2F, casa2G, casa2H, casa3A, casa3B, casa3C, casa3D, casa3E, casa3F, casa3G, casa3H, casa4A, casa4B, casa4C, casa4D, casa4E, casa4F, casa4G, casa4H, casa5A, casa5B, casa5C, casa5D, casa5E, casa5F, casa5G, casa5H, casa6A, casa6B, casa6C, casa6D, casa6E, casa6F, casa6G, casa6H, casa7A, casa7B, casa7C, casa7D, casa7E, casa7F, casa7G, casa7H, casa8A, casa8B, casa8C, casa8D, casa8E, casa8F, casa8G, casa8H, labelColunaA, labelColunaB, labelColunaC, labelColunaD, labelColunaE, labelColunaF, labelColunaG, labelColunaH, labelLinha1, labelLinha2, labelLinha3, labelLinha4, labelLinha5});

        PainelTabuleiroLayout.setVerticalGroup(
            PainelTabuleiroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelTabuleiroLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(PainelTabuleiroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(casa8A, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PainelTabuleiroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(casa8B, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(casa8C, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(casa8D, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(casa8E, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(casa8F, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(casa8G, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(casa8H, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(LabelLinha8))
                .addGroup(PainelTabuleiroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelTabuleiroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(casa7A, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(casa7B, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(casa7C, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(casa7D, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(casa7E, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(casa7F, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(casa7G, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(casa7H, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(LabelLinha7))
                .addGroup(PainelTabuleiroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelTabuleiroLayout.createSequentialGroup()
                        .addGroup(PainelTabuleiroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(casa6B, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(casa6C, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(casa6D, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(casa6E, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(casa6F, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(casa6A, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(casa6G, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(casa6H, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(PainelTabuleiroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(casa5A, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(casa5B, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(casa5C, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(casa5D, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(casa5E, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(casa5F, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(casa5G, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(casa5H, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(PainelTabuleiroLayout.createSequentialGroup()
                        .addComponent(LabelLinha6)
                        .addGap(0, 0, 0)
                        .addComponent(labelLinha5)))
                .addGroup(PainelTabuleiroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelTabuleiroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(casa4B, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(casa4C, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(casa4D, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(casa4E, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(casa4F, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(casa4A, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(casa4G, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(casa4H, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(labelLinha4))
                .addGroup(PainelTabuleiroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelTabuleiroLayout.createSequentialGroup()
                        .addGroup(PainelTabuleiroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(casa3A, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(casa3B, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(casa3C, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(casa3D, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(casa3E, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(casa3F, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(casa3G, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(casa3H, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(PainelTabuleiroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(casa2B, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(casa2C, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(casa2D, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(casa2E, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(casa2F, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(casa2A, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(casa2G, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(casa2H, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(PainelTabuleiroLayout.createSequentialGroup()
                        .addComponent(labelLinha3)
                        .addGap(0, 0, 0)
                        .addComponent(labelLinha2)))
                .addGroup(PainelTabuleiroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelTabuleiroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(casa1A, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(casa1B, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(casa1C, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(casa1D, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(casa1E, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(casa1F, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(casa1G, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(casa1H, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(labelLinha1))
                .addGap(0, 0, 0)
                .addGroup(PainelTabuleiroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelColunaA)
                    .addComponent(labelColunaB)
                    .addComponent(labelColunaC)
                    .addComponent(labelColunaD)
                    .addComponent(labelColunaE)
                    .addComponent(labelColunaF)
                    .addComponent(labelColunaG)
                    .addComponent(labelColunaH))
                .addGap(10, 10, 10))
        );

        PainelTabuleiroLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {LabelLinha6, LabelLinha7, LabelLinha8, casa1A, casa1B, casa1C, casa1D, casa1E, casa1F, casa1G, casa1H, casa2A, casa2B, casa2C, casa2D, casa2E, casa2F, casa2G, casa2H, casa3A, casa3B, casa3C, casa3D, casa3E, casa3F, casa3G, casa3H, casa4A, casa4B, casa4C, casa4D, casa4E, casa4F, casa4G, casa4H, casa5A, casa5B, casa5C, casa5D, casa5E, casa5F, casa5G, casa5H, casa6A, casa6B, casa6C, casa6D, casa6E, casa6F, casa6G, casa6H, casa7A, casa7B, casa7C, casa7D, casa7E, casa7F, casa7G, casa7H, casa8A, casa8B, casa8C, casa8D, casa8E, casa8F, casa8G, casa8H, labelColunaA, labelColunaB, labelColunaC, labelColunaD, labelColunaE, labelColunaF, labelColunaG, labelColunaH, labelLinha1, labelLinha2, labelLinha3, labelLinha4, labelLinha5});

        labelProfundidade.setText("Profundidade:");

        spinnerProfundidade.setModel(new javax.swing.SpinnerNumberModel(5, 1, 99, 1));
        spinnerProfundidade.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        spinnerProfundidade.setFocusTraversalKeysEnabled(false);
        spinnerProfundidade.setFocusable(false);
        spinnerProfundidade.setRequestFocusEnabled(false);
        spinnerProfundidade.setValue(5);
        spinnerProfundidade.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinnerProfundidadeStateChanged(evt);
            }
        });

        labelCor.setText("Cor:");

        botaoCor.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        botaoCor.setContentAreaFilled(false);
        botaoCor.setOpaque(true);
        botaoCor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoCorActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Estratégia da IA"));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Defensiva"));

        jLabel1.setText("Bloqueio:");

        jLabel3.setText("Aliança:");

        jLabel4.setText("Suporte:");

        sliderDetencao.setMaximum(20);
        sliderDetencao.setToolTipText("");
        sliderDetencao.setValue(1);
        sliderDetencao.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderDetencaoStateChanged(evt);
            }
        });

        sliderAlianca.setMaximum(20);
        sliderAlianca.setValue(7);
        sliderAlianca.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderAliancaStateChanged(evt);
            }
        });

        sliderSuporte.setMaximum(20);
        sliderSuporte.setValue(13);
        sliderSuporte.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderSuporteStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sliderDetencao, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sliderAlianca, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sliderSuporte, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(sliderDetencao, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(sliderAlianca, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(sliderSuporte, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Ofensiva"));

        jLabel5.setText("Marcha:");

        jLabel6.setText("Brutalidade:");

        jLabel7.setText("Artilharia:");

        sliderMarcha.setMaximum(20);
        sliderMarcha.setValue(1);
        sliderMarcha.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderMarchaStateChanged(evt);
            }
        });

        sliderBrutalidade.setMaximum(20);
        sliderBrutalidade.setValue(7);
        sliderBrutalidade.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderBrutalidadeStateChanged(evt);
            }
        });

        sliderArtilharia.setMaximum(20);
        sliderArtilharia.setValue(13);
        sliderArtilharia.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderArtilhariaStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sliderMarcha, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sliderArtilharia, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sliderBrutalidade, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(sliderMarcha, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(sliderBrutalidade, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(sliderArtilharia, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        inverterTabuleiroCheckBox.setText("Inverter tabuleiro");
        inverterTabuleiroCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inverterTabuleiroCheckBoxActionPerformed(evt);
            }
        });

        consultarBDCheckBox.setText("Consultar base de dados");
        consultarBDCheckBox.setToolTipText("Procura na base de dados um estado destino pré-calculado com profundidade maior ou igual à profundidade solicitada.\nCaso não encontre, escolhe um novo estado executando o minimax.\nObs: no caso do estado pré-calculado, ignora estratégia atual da IA.");
        consultarBDCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consultarBDCheckBoxActionPerformed(evt);
            }
        });

        alimentarBDCheckBox.setText("Alimentar base de dados");
        alimentarBDCheckBox.setToolTipText("Insere o resultado do minimax na base de dados caso o estado destino desse estado esteja sendo calculado \npela primeira vez ou esteja sendo calculado com uma profundidade maior do que a já pré-calculada.");
        alimentarBDCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alimentarBDCheckBoxActionPerformed(evt);
            }
        });

        hitOuMissLabel.setText("   ");

        jMenu1.setText("Jogo");

        MenuNovoJogo.setText("Novo jogo");
        MenuNovoJogo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuNovoJogoActionPerformed(evt);
            }
        });
        jMenu1.add(MenuNovoJogo);

        Menu.add(jMenu1);

        jMenu2.setText("Editar");

        MenuRemoverPeca.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
        MenuRemoverPeca.setText("Remover Peça");
        MenuRemoverPeca.setEnabled(false);
        MenuRemoverPeca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuRemoverPecaActionPerformed(evt);
            }
        });
        jMenu2.add(MenuRemoverPeca);

        RecortarMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        RecortarMenuItem.setText("Recortar");
        RecortarMenuItem.setEnabled(false);
        RecortarMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RecortarMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(RecortarMenuItem);

        ColarMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        ColarMenuItem.setText("Colar");
        ColarMenuItem.setEnabled(false);
        ColarMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ColarMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(ColarMenuItem);

        DesfazerMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        DesfazerMenu.setText("Desfazer");
        DesfazerMenu.setEnabled(false);
        DesfazerMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DesfazerMenuActionPerformed(evt);
            }
        });
        jMenu2.add(DesfazerMenu);

        RefazerMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        RefazerMenuItem.setText("Refazer");
        RefazerMenuItem.setEnabled(false);
        RefazerMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefazerMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(RefazerMenuItem);

        Menu.add(jMenu2);

        setJMenuBar(Menu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(PainelTabuleiro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelProfundidade)
                            .addComponent(labelCor))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(botaoCor, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spinnerProfundidade, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(inverterTabuleiroCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(alimentarBDCheckBox)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(consultarBDCheckBox)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(hitOuMissLabel)))
                                .addGap(0, 34, Short.MAX_VALUE)))))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {botaoCor, spinnerProfundidade});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(PainelTabuleiro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelProfundidade)
                            .addComponent(spinnerProfundidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(botaoCor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelCor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(inverterTabuleiroCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(consultarBDCheckBox)
                            .addComponent(hitOuMissLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(alimentarBDCheckBox)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {botaoCor, spinnerProfundidade});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void MenuNovoJogoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuNovoJogoActionPerformed
        if(!primeirojogo) memoriza();
        RefazerMenuItem.setEnabled(false);
        criaTabuleiro();
        preencheTabuleiro();               
    }//GEN-LAST:event_MenuNovoJogoActionPerformed

// Eventos de cliques nas casas
    private void casa1AMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa1AMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(A1);
        else desfazSelecoes(A1);
    }//GEN-LAST:event_casa1AMouseClicked

    private void casa1BMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa1BMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(B1);
        else desfazSelecoes(B1);
    }//GEN-LAST:event_casa1BMouseClicked

    private void casa1CMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa1CMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(C1);
        else desfazSelecoes(C1);
    }//GEN-LAST:event_casa1CMouseClicked

    private void casa1DMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa1DMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(D1);
        else desfazSelecoes(D1);
    }//GEN-LAST:event_casa1DMouseClicked

    private void casa1EMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa1EMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(E1);
        else desfazSelecoes(E1);
    }//GEN-LAST:event_casa1EMouseClicked

    private void casa1FMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa1FMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(F1);
        else desfazSelecoes(F1);
    }//GEN-LAST:event_casa1FMouseClicked

    private void casa1GMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa1GMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(G1);
        else desfazSelecoes(G1);
    }//GEN-LAST:event_casa1GMouseClicked

    private void casa1HMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa1HMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(H1);
        else desfazSelecoes(H1);
    }//GEN-LAST:event_casa1HMouseClicked

    private void casa2AMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa2AMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(A2);
        else desfazSelecoes(A2);
    }//GEN-LAST:event_casa2AMouseClicked

    private void casa2BMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa2BMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(B2);
        else desfazSelecoes(B2);
    }//GEN-LAST:event_casa2BMouseClicked

    private void casa2CMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa2CMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(C2);
        else desfazSelecoes(C2);
    }//GEN-LAST:event_casa2CMouseClicked

    private void casa2DMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa2DMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(D2);
        else desfazSelecoes(D2);
    }//GEN-LAST:event_casa2DMouseClicked

    private void casa2EMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa2EMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(E2);
        else desfazSelecoes(E2);
    }//GEN-LAST:event_casa2EMouseClicked

    private void casa2FMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa2FMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(F2);
        else desfazSelecoes(F2);
    }//GEN-LAST:event_casa2FMouseClicked

    private void casa2GMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa2GMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(G2);
        else desfazSelecoes(G2);
    }//GEN-LAST:event_casa2GMouseClicked

    private void casa2HMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa2HMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(H2);
        else desfazSelecoes(H2);
    }//GEN-LAST:event_casa2HMouseClicked

    private void casa3AMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa3AMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(A3);
        else desfazSelecoes(A3);
    }//GEN-LAST:event_casa3AMouseClicked

    private void casa3BMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa3BMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(B3);
        else desfazSelecoes(B3);
    }//GEN-LAST:event_casa3BMouseClicked

    private void casa3CMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa3CMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(C3);
        else desfazSelecoes(C3);
    }//GEN-LAST:event_casa3CMouseClicked

    private void casa3DMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa3DMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(D3);
        else desfazSelecoes(D3);
    }//GEN-LAST:event_casa3DMouseClicked

    private void casa3EMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa3EMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(E3);
        else desfazSelecoes(E3);
    }//GEN-LAST:event_casa3EMouseClicked

    private void casa3FMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa3FMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(F3);
        else desfazSelecoes(F3);
    }//GEN-LAST:event_casa3FMouseClicked

    private void casa3GMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa3GMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(G3);
        else desfazSelecoes(G3);
    }//GEN-LAST:event_casa3GMouseClicked

    private void casa3HMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa3HMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(H3);
        else desfazSelecoes(H3);
    }//GEN-LAST:event_casa3HMouseClicked

    private void casa4AMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa4AMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(A4);
        else desfazSelecoes(A4);
    }//GEN-LAST:event_casa4AMouseClicked

    private void casa4BMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa4BMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(B4);
        else desfazSelecoes(B4);
    }//GEN-LAST:event_casa4BMouseClicked

    private void casa4CMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa4CMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(C4);
        else desfazSelecoes(C4);
    }//GEN-LAST:event_casa4CMouseClicked

    private void casa4DMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa4DMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(D4);
        else desfazSelecoes(D4);
    }//GEN-LAST:event_casa4DMouseClicked

    private void casa4EMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa4EMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(E4);
        else desfazSelecoes(E4);
    }//GEN-LAST:event_casa4EMouseClicked

    private void casa4FMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa4FMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(F4);
        else desfazSelecoes(F4);
    }//GEN-LAST:event_casa4FMouseClicked

    private void casa4GMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa4GMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(G4);
        else desfazSelecoes(G4);
    }//GEN-LAST:event_casa4GMouseClicked

    private void casa4HMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa4HMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(H4);
        else desfazSelecoes(H4);
    }//GEN-LAST:event_casa4HMouseClicked

    private void casa5AMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa5AMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(A5);
        else desfazSelecoes(A5);
    }//GEN-LAST:event_casa5AMouseClicked

    private void casa5BMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa5BMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(B5);
        else desfazSelecoes(B5);
    }//GEN-LAST:event_casa5BMouseClicked

    private void casa5CMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa5CMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(C5);
        else desfazSelecoes(C5);
    }//GEN-LAST:event_casa5CMouseClicked

    private void casa5DMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa5DMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(D5);
        else desfazSelecoes(D5);
    }//GEN-LAST:event_casa5DMouseClicked

    private void casa5EMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa5EMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(E5);
        else desfazSelecoes(E5);
    }//GEN-LAST:event_casa5EMouseClicked

    private void casa5FMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa5FMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(F5);
        else desfazSelecoes(F5);
    }//GEN-LAST:event_casa5FMouseClicked

    private void casa5GMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa5GMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(G5);
        else desfazSelecoes(G5);
    }//GEN-LAST:event_casa5GMouseClicked

    private void casa5HMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa5HMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(H5);
        else desfazSelecoes(H5);
    }//GEN-LAST:event_casa5HMouseClicked

    private void casa6AMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa6AMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(A6);
        else desfazSelecoes(A6);
    }//GEN-LAST:event_casa6AMouseClicked

    private void casa6BMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa6BMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(B6);
        else desfazSelecoes(B6);
    }//GEN-LAST:event_casa6BMouseClicked

    private void casa6CMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa6CMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(C6);
        else desfazSelecoes(C6);
    }//GEN-LAST:event_casa6CMouseClicked

    private void casa6DMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa6DMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(D6);
        else desfazSelecoes(D6);
    }//GEN-LAST:event_casa6DMouseClicked

    private void casa6EMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa6EMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(E6);
        else desfazSelecoes(E6);
    }//GEN-LAST:event_casa6EMouseClicked

    private void casa6FMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa6FMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(F6);
        else desfazSelecoes(F6);
    }//GEN-LAST:event_casa6FMouseClicked

    private void casa6GMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa6GMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(G6);
        else desfazSelecoes(G6);
    }//GEN-LAST:event_casa6GMouseClicked

    private void casa6HMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa6HMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(H6);
        else desfazSelecoes(H6);
    }//GEN-LAST:event_casa6HMouseClicked

    private void casa7AMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa7AMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(A7);
        else desfazSelecoes(A7);
    }//GEN-LAST:event_casa7AMouseClicked

    private void casa7BMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa7BMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(B7);
        else desfazSelecoes(B7);
    }//GEN-LAST:event_casa7BMouseClicked

    private void casa7CMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa7CMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(C7);
        else desfazSelecoes(C7);
    }//GEN-LAST:event_casa7CMouseClicked

    private void casa7DMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa7DMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(D7);
        else desfazSelecoes(D7);
    }//GEN-LAST:event_casa7DMouseClicked

    private void casa7EMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa7EMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(E7);
        else desfazSelecoes(E7);
    }//GEN-LAST:event_casa7EMouseClicked

    private void casa7FMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa7FMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(F7);
        else desfazSelecoes(F7);
    }//GEN-LAST:event_casa7FMouseClicked

    private void casa7GMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa7GMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(G7);
        else desfazSelecoes(G7);
    }//GEN-LAST:event_casa7GMouseClicked

    private void casa7HMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa7HMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(H7);
        else desfazSelecoes(H7);
    }//GEN-LAST:event_casa7HMouseClicked

    private void casa8AMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa8AMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(A8);
        else desfazSelecoes(A8);
    }//GEN-LAST:event_casa8AMouseClicked

    private void casa8BMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa8BMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(B8);
        else desfazSelecoes(B8);
    }//GEN-LAST:event_casa8BMouseClicked

    private void casa8CMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa8CMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(C8);
        else desfazSelecoes(C8);
    }//GEN-LAST:event_casa8CMouseClicked

    private void casa8DMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa8DMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(D8);
        else desfazSelecoes(D8);
    }//GEN-LAST:event_casa8DMouseClicked

    private void casa8EMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa8EMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(E8);
        else desfazSelecoes(E8);
    }//GEN-LAST:event_casa8EMouseClicked

    private void casa8FMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa8FMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(F8);
        else desfazSelecoes(F8);
    }//GEN-LAST:event_casa8FMouseClicked

    private void casa8GMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa8GMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(G8);
        else desfazSelecoes(G8);
    }//GEN-LAST:event_casa8GMouseClicked

    private void casa8HMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa8HMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1) movimentoDaPeca(H8);
        else desfazSelecoes(H8);
    }//GEN-LAST:event_casa8HMouseClicked

    private void spinnerProfundidadeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spinnerProfundidadeStateChanged
        profundidade = (int)spinnerProfundidade.getValue();
    }//GEN-LAST:event_spinnerProfundidadeStateChanged

    private void botaoCorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoCorActionPerformed
      if (cor){
        CODIGO_UNICODE_TORRE_NEGRA = "\u2656";
        CODIGO_UNICODE_TORRE_BRANCA = "\u265C";
        CODIGO_UNICODE_PEAO_NEGRO = "\u2659";
        CODIGO_UNICODE_PEAO_BRANCO = "\u265F";
        cor = NEGRO;
        botaoCor.setBackground(Color.BLACK);
      }
      else{
        CODIGO_UNICODE_TORRE_NEGRA = "\u265C";
        CODIGO_UNICODE_TORRE_BRANCA = "\u2656";
        CODIGO_UNICODE_PEAO_NEGRO = "\u265F";
        CODIGO_UNICODE_PEAO_BRANCO = "\u2659";
        cor = BRANCO;
        botaoCor.setBackground(Color.WHITE);
      }
    }//GEN-LAST:event_botaoCorActionPerformed

    private void sliderDetencaoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderDetencaoStateChanged
        DETENCAO = sliderDetencao.getValue();
    }//GEN-LAST:event_sliderDetencaoStateChanged

    private void sliderAliancaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderAliancaStateChanged
        ALIANCA = sliderAlianca.getValue();
    }//GEN-LAST:event_sliderAliancaStateChanged

    private void sliderSuporteStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderSuporteStateChanged
        SUPORTE = sliderSuporte.getValue();
    }//GEN-LAST:event_sliderSuporteStateChanged

    private void sliderMarchaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderMarchaStateChanged
        MARCHA = sliderMarcha.getValue();
    }//GEN-LAST:event_sliderMarchaStateChanged

    private void sliderBrutalidadeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderBrutalidadeStateChanged
        BRUTALIDADE = sliderBrutalidade.getValue();
    }//GEN-LAST:event_sliderBrutalidadeStateChanged

    private void sliderArtilhariaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderArtilhariaStateChanged
        ARTILHARIA = sliderArtilharia.getValue();
    }//GEN-LAST:event_sliderArtilhariaStateChanged

    private void MenuRemoverPecaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuRemoverPecaActionPerformed
        
        if(casa_selecionada != CASA_VAZIA){
            memoriza();
            ((JLabel)tabuleiro.get(casa_selecionada)).setText(null);
            peca[pecaDaCasa[casa_selecionada]].defineCasa(CASA_VAZIA);
            pecaDaCasa[casa_selecionada] = CASA_VAZIA;
            ((JLabel)tabuleiro.get(casa_selecionada)).setBackground(corDaCasa[casa_selecionada]);
            criaBitBoard();   
            casa_selecionada = CASA_VAZIA;
        }
        
        else if(casa_inimiga_selecionada != CASA_VAZIA) {
            memoriza();
            ((JLabel)tabuleiro.get(casa_inimiga_selecionada)).setText(null);
            peca[pecaDaCasa[casa_inimiga_selecionada]].defineCasa(CASA_VAZIA);
            pecaDaCasa[casa_inimiga_selecionada] = CASA_VAZIA;
            ((JLabel)tabuleiro.get(casa_inimiga_selecionada)).setBackground(corDaCasa[casa_inimiga_selecionada]);
            criaBitBoard();   
            casa_inimiga_selecionada = CASA_VAZIA;
        }   
        
        MenuRemoverPeca.setEnabled(false);
        
    }//GEN-LAST:event_MenuRemoverPecaActionPerformed

    private void DesfazerMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DesfazerMenuActionPerformed
        
        relembra();

        if(novaposicaoocupada>=0) ((JLabel)tabuleiro.get(novaposicaoocupada)).setBackground(corDaCasa[novaposicaoocupada]);
        if(velhaposicaoocupada>=0) ((JLabel)tabuleiro.get(velhaposicaoocupada)).setBackground(corDaCasa[velhaposicaoocupada]);
        if(casa_selecionada>=0) ((JLabel)tabuleiro.get(casa_selecionada)).setBackground(corDaCasa[casa_selecionada]);
        if(casa_inimiga_selecionada>=0) ((JLabel)tabuleiro.get(casa_inimiga_selecionada)).setBackground(corDaCasa[casa_inimiga_selecionada]);      
    
        converteBitBoard();      
        
    }//GEN-LAST:event_DesfazerMenuActionPerformed

    private void inverterTabuleiroCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inverterTabuleiroCheckBoxActionPerformed
        
        if(inverterTabuleiroCheckBox.isSelected()) inverterTabuleiro = true;
        else inverterTabuleiro = false;
        
        if(!primeirojogo) {
            criaBitBoard();
            inverteBitboards();
            converteBitBoard();
            
            if(casa_selecionada>=0) {
                ((JLabel)tabuleiro.get(casa_selecionada)).setBackground(corDaCasa[casa_selecionada]);
                casa_selecionada = CASA_VAZIA;
            }
            if(novaposicaoocupada>=0) ((JLabel)tabuleiro.get(novaposicaoocupada)).setBackground(corDaCasa[novaposicaoocupada]);
            if(velhaposicaoocupada>=0) ((JLabel)tabuleiro.get(velhaposicaoocupada)).setBackground(corDaCasa[velhaposicaoocupada]);
            if(casa_inimiga_selecionada != CASA_VAZIA) {
                ((JLabel)tabuleiro.get(casa_inimiga_selecionada)).setBackground(corDaCasa[casa_inimiga_selecionada]);
                casa_inimiga_selecionada = CASA_VAZIA;
                casa_vazia_selecionada = CASA_VAZIA;
                MenuRemoverPeca.setEnabled(false);
                RecortarMenuItem.setEnabled(false);
            }
        }
        
    }//GEN-LAST:event_inverterTabuleiroCheckBoxActionPerformed

    
    int id_clipboard = -1;
    
    private void ColarMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ColarMenuItemActionPerformed
        
        if((casa_vazia_selecionada != CASA_VAZIA)&&(id_clipboard>=0)){
            memoriza();

            casa_destino = casa_vazia_selecionada;
            if (pecaDaCasa[casa_destino]%2 != 0 && pecaDaCasa[casa_destino] != CASA_VAZIA){
                 peca[pecaDaCasa[casa_destino]].defineCasa(CASA_VAZIA);
            }
            
            peca[id_clipboard].escrevePeca((JLabel)tabuleiro.get(casa_destino));
            peca[id_clipboard].defineCasa(casa_destino);
            
            pecaDaCasa[casa_destino] = id_clipboard;
            casa_destino = CASA_VAZIA;

            criaBitBoard();   
            converteBitBoard();
        }
        
        ColarMenuItem.setEnabled(false);
        id_clipboard = -1;
        
    }//GEN-LAST:event_ColarMenuItemActionPerformed
    
    private void RecortarMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RecortarMenuItemActionPerformed
        
        if(casa_selecionada != CASA_VAZIA){
            memoriza();
            ((JLabel)tabuleiro.get(casa_selecionada)).setText(null);
            id_clipboard = pecaDaCasa[casa_selecionada];
            peca[pecaDaCasa[casa_selecionada]].defineCasa(CASA_VAZIA);
            pecaDaCasa[casa_selecionada] = CASA_VAZIA;
            ((JLabel)tabuleiro.get(casa_selecionada)).setBackground(corDaCasa[casa_selecionada]);
            criaBitBoard();   
            casa_selecionada = CASA_VAZIA;
        }
        
        else if(casa_inimiga_selecionada != CASA_VAZIA) {
            memoriza();
            ((JLabel)tabuleiro.get(casa_inimiga_selecionada)).setText(null);
            id_clipboard = pecaDaCasa[casa_inimiga_selecionada];
            peca[pecaDaCasa[casa_inimiga_selecionada]].defineCasa(CASA_VAZIA);
            pecaDaCasa[casa_inimiga_selecionada] = CASA_VAZIA;
            ((JLabel)tabuleiro.get(casa_inimiga_selecionada)).setBackground(corDaCasa[casa_inimiga_selecionada]);
            criaBitBoard();   
            casa_inimiga_selecionada = CASA_VAZIA;
        }       
        
        RecortarMenuItem.setEnabled(false);
        
    }//GEN-LAST:event_RecortarMenuItemActionPerformed

    private void consultarBDCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consultarBDCheckBoxActionPerformed
        
        if(consultarBDCheckBox.isSelected()) {
            carregaBD();
            consultaAtiva = true;
        }
        else {
            //salvaBD();
            consultaAtiva = false;
        }
        
    }//GEN-LAST:event_consultarBDCheckBoxActionPerformed

    private void RefazerMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefazerMenuItemActionPerformed
               
        adivinha();

        if(novaposicaoocupada>=0) ((JLabel)tabuleiro.get(novaposicaoocupada)).setBackground(corDaCasa[novaposicaoocupada]);
        if(velhaposicaoocupada>=0) ((JLabel)tabuleiro.get(velhaposicaoocupada)).setBackground(corDaCasa[velhaposicaoocupada]);
        if(casa_selecionada>=0) ((JLabel)tabuleiro.get(casa_selecionada)).setBackground(corDaCasa[casa_selecionada]);
        if(casa_inimiga_selecionada>=0) ((JLabel)tabuleiro.get(casa_inimiga_selecionada)).setBackground(corDaCasa[casa_inimiga_selecionada]);      
    
        converteBitBoard();  
        
    }//GEN-LAST:event_RefazerMenuItemActionPerformed

    private void alimentarBDCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alimentarBDCheckBoxActionPerformed
        
        if(alimentarBDCheckBox.isSelected()) {
            alimentacaoAtiva = true;
            if(baseDeDados == null) carregaBD();
        }
        else {
            salvaBD();
            alimentacaoAtiva = false;
        }
        
    }//GEN-LAST:event_alimentarBDCheckBoxActionPerformed

    private void finalizaPrograma(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_finalizaPrograma
        
        if(alimentacaoAtiva == true && baseDeDados != null) salvaBD();
        System.exit(0);
        
    }//GEN-LAST:event_finalizaPrograma

    //////////////////////////////////////////////////////////////////////////// 
    // Funcoes para desfazer/refazer
    
    public void memoriza(){
        
        ArrayList<BitSet> estado = new ArrayList<>();
        estado.add((BitSet)bitabuleiroPB.clone());
        estado.add((BitSet)bitabuleiroTB.clone());
        estado.add((BitSet)bitabuleiroPN.clone());
        estado.add((BitSet)bitabuleiroTN.clone());
        
        memoria.push(estado);
   
        DesfazerMenu.setEnabled(true);
                
        
    }
    
    public void relembra(){
        
        ArrayList<BitSet> estadoPossivel = new ArrayList<>();
        estadoPossivel.add((BitSet)bitabuleiroPB.clone());
        estadoPossivel.add((BitSet)bitabuleiroTB.clone());
        estadoPossivel.add((BitSet)bitabuleiroPN.clone());
        estadoPossivel.add((BitSet)bitabuleiroTN.clone());
    
        futuro.push(estadoPossivel);
        
        ArrayList<BitSet> fenix = memoria.pop();
        bitabuleiroPB = fenix.get(0);
        bitabuleiroTB = fenix.get(1);
        bitabuleiroPN = fenix.get(2);
        bitabuleiroTN = fenix.get(3);
        
        if(memoria.isEmpty()) DesfazerMenu.setEnabled(false);
        id_clipboard = -1;
        ColarMenuItem.setEnabled(false);
        
        RefazerMenuItem.setEnabled(true);
        
    }
    
    public void adivinha(){
        
        memoriza();
        
        ArrayList<BitSet> flashForward = futuro.pop();
        bitabuleiroPB = flashForward.get(0);
        bitabuleiroTB = flashForward.get(1);
        bitabuleiroPN = flashForward.get(2);
        bitabuleiroTN = flashForward.get(3);
                
        if(futuro.isEmpty()) RefazerMenuItem.setEnabled(false);
        id_clipboard = -1;
        ColarMenuItem.setEnabled(false);
        
    }
    
    //////////////////////////////////////////////////////////////
    // Funcoes para BD
    
    public void carregaBD(){
        FileInputStream saveFile = null;
        try {
            saveFile = new FileInputStream("database");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(InterfaceGrafica.class.getName()).log(Level.SEVERE, null, ex);
        }
        ObjectInputStream save;
        Object obj1=null, obj2=null;
        try {
            save = new ObjectInputStream(saveFile);
            try {
                obj1 = save.readObject();
                obj2 = save.readObject();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(InterfaceGrafica.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(InterfaceGrafica.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        baseDeDados = (HashMap<Integer,ArrayList<BitSet>>)obj1;
        profundidades = (HashMap<Integer,Integer>)obj2;
        
    }
    
    public void salvaBD(){
        try {
            FileOutputStream saveFile = new FileOutputStream("database");
            ObjectOutputStream save;
            try {
                save = new ObjectOutputStream(saveFile);
                save.writeObject(baseDeDados);
                save.writeObject(profundidades);
                save.close();
            } catch (IOException ex) {
                Logger.getLogger(InterfaceGrafica.class.getName()).log(Level.SEVERE, null, ex);
            }            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(InterfaceGrafica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void insereBD(int key, ArrayList<BitSet> value, int profundidade){
  
        boolean jaTem = baseDeDados.containsKey(key);
        
        if((jaTem == false) || (jaTem == true && profundidades.get(key) < profundidade)) {
            baseDeDados.put(key, value);
            profundidades.put(key, profundidade);
        }
        
    }
       
    public ArrayList<BitSet> consultaBD(int profundidade, BitSet boardPB, BitSet boardTB, BitSet boardPN, BitSet boardTN){
        
        ArrayList<BitSet> estadoOrigem = new ArrayList<>();
        estadoOrigem.add(boardPB);
        estadoOrigem.add(boardTB);
        estadoOrigem.add(boardPN);
        estadoOrigem.add(boardTN);
        int key = estadoOrigem.hashCode();
        
        if(baseDeDados.containsKey(key) && profundidades.get(key) >= profundidade) {
            return baseDeDados.get(key);
        }
        else {
            return null;
        }
        
    }
       
    //////////////////////////////////////////////////////////////
    
    public void criaTabuleiro(){
        // Insere as casas na lista tabuleiro
        tabuleiro.add(casa1A); // i=0
        tabuleiro.add(casa1B); // i=1
        tabuleiro.add(casa1C); // i=2
        tabuleiro.add(casa1D); // i=3
        tabuleiro.add(casa1E); // i=4
        tabuleiro.add(casa1F); // i=5
        tabuleiro.add(casa1G); // i=6
        tabuleiro.add(casa1H); // i=7
        tabuleiro.add(casa2A); // i=8
        tabuleiro.add(casa2B); // i=9
        tabuleiro.add(casa2C); // i=10
        tabuleiro.add(casa2D); // i=11
        tabuleiro.add(casa2E); // i=12
        tabuleiro.add(casa2F); // i=13
        tabuleiro.add(casa2G); // i=14
        tabuleiro.add(casa2H); // i=15
        tabuleiro.add(casa3A); // i=16
        tabuleiro.add(casa3B); // i=17
        tabuleiro.add(casa3C); // i=18
        tabuleiro.add(casa3D); // i=19
        tabuleiro.add(casa3E); // i=20
        tabuleiro.add(casa3F); // i=21
        tabuleiro.add(casa3G); // i=22
        tabuleiro.add(casa3H); // i=23
        tabuleiro.add(casa4A); // i=24
        tabuleiro.add(casa4B); // i=25
        tabuleiro.add(casa4C); // i=26
        tabuleiro.add(casa4D); // i=27
        tabuleiro.add(casa4E); // i=28
        tabuleiro.add(casa4F); // i=29
        tabuleiro.add(casa4G); // i=30
        tabuleiro.add(casa4H); // i=31
        tabuleiro.add(casa5A); // i=32
        tabuleiro.add(casa5B); // i=33
        tabuleiro.add(casa5C); // i=34
        tabuleiro.add(casa5D); // i=35
        tabuleiro.add(casa5E); // i=36
        tabuleiro.add(casa5F); // i=37
        tabuleiro.add(casa5G); // i=38
        tabuleiro.add(casa5H); // i=39
        tabuleiro.add(casa6A); // i=40
        tabuleiro.add(casa6B); // i=41
        tabuleiro.add(casa6C); // i=42
        tabuleiro.add(casa6D); // i=43
        tabuleiro.add(casa6E); // i=44
        tabuleiro.add(casa6F); // i=45
        tabuleiro.add(casa6G); // i=46
        tabuleiro.add(casa6H); // i=47
        tabuleiro.add(casa7A); // i=48
        tabuleiro.add(casa7B); // i=49
        tabuleiro.add(casa7C); // i=50
        tabuleiro.add(casa7D); // i=51
        tabuleiro.add(casa7E); // i=52
        tabuleiro.add(casa7F); // i=53
        tabuleiro.add(casa7G); // i=54
        tabuleiro.add(casa7H); // i=55
        tabuleiro.add(casa8A); // i=56
        tabuleiro.add(casa8B); // i=57
        tabuleiro.add(casa8C); // i=58
        tabuleiro.add(casa8D); // i=59
        tabuleiro.add(casa8E); // i=60
        tabuleiro.add(casa8F); // i=61
        tabuleiro.add(casa8G); // i=62
        tabuleiro.add(casa8H); // i=63
        
        for (int i=A1;i<NRO_LINHAS*NRO_COLUNAS;i++){
            ((JLabel)tabuleiro.get(i)).setEnabled(true);
            ((JLabel)tabuleiro.get(i)).setText(null);
        }
        
        // Inicializa as casas como vazias
        for (int i=A1;i<NRO_LINHAS*NRO_COLUNAS;i++){
            pecaDaCasa[i] = CASA_VAZIA;
        }
        
        // Salva a cor de fundo de todas as casas
        if (primeirojogo){
            for (int i=A1;i<NRO_LINHAS*NRO_COLUNAS;i++){
                corDaCasa[i] = ((JLabel)tabuleiro.get(i)).getBackground();
            }
            primeirojogo=false;
        }
        else{
        // Restaura a cor de fundo de todas as casas
            for (int i=A1;i<NRO_LINHAS*NRO_COLUNAS;i++){
                ((JLabel)tabuleiro.get(i)).setBackground(corDaCasa[i]);
            }
        }
        
        // Cria o bitboard de limites do tabuleiro
        fronteiras_do_universo_esq.clear();
        fronteiras_do_universo_dir.clear();
        for (int i = A1; i<(NRO_LINHAS)*(NRO_COLUNAS);i=i+8){
            fronteiras_do_universo_esq.set(i);
            fronteiras_do_universo_dir.set(i+7);
        }
        
        casa_selecionada = CASA_VAZIA;
        casa_destino = CASA_VAZIA;
        enpassantBranco = -1;
        enpassantNegro = -1;
        
        if(inverterTabuleiro == true) inverteBitboards();
        
    }
    
    public void preencheTabuleiro(){
        // Posiciona as pecas em suas posicoes inicias    
        
        // Cria peoes brancos
        for (int id=0,j=A2;id<2*NRO_PEOES;id=id+2){           
            peca[id] = new Peca();
            peca[id].defineTexto(CODIGO_UNICODE_PEAO_BRANCO);
            peca[id].escrevePeca((JLabel)tabuleiro.get(j));
            peca[id].defineCasa(j);
            peca[id].defineValor(1);
            pecaDaCasa[j] = id;
            j++;
        }
        
        // Cria peoes negros
        for (int id=1,j=A7;id<2*NRO_PEOES;id=id+2){
            peca[id] = new Peca();
            peca[id].defineTexto(CODIGO_UNICODE_PEAO_NEGRO);
            peca[id].escrevePeca((JLabel)tabuleiro.get(j));
            peca[id].defineCasa(j);
            peca[id].defineValor(1);
            pecaDaCasa[j] = id;
            j++;
        }
        
        // Cria torres brancas
        peca[TB0] = new Peca();
        peca[TB0].defineTexto(CODIGO_UNICODE_TORRE_BRANCA);
        peca[TB0].escrevePeca((JLabel)tabuleiro.get(A1));
        peca[TB0].defineCasa(A1);
        peca[TB0].defineValor(4);
        pecaDaCasa[A1] = TB0;
        
        peca[TB1] = new Peca();
        peca[TB1].defineTexto(CODIGO_UNICODE_TORRE_BRANCA);
        peca[TB1].escrevePeca((JLabel)tabuleiro.get(H1));
        peca[TB1].defineCasa(H1);
        peca[TB1].defineValor(4);
        pecaDaCasa[H1] = TB1;
        
        // Cria torres negras
        peca[TN0] = new Peca();
        peca[TN0].defineTexto(CODIGO_UNICODE_TORRE_NEGRA);
        peca[TN0].escrevePeca((JLabel)tabuleiro.get(A8));
        peca[TN0].defineCasa(A8);
        peca[TN0].defineValor(4);
        pecaDaCasa[A8] = TN0;
        
        peca[TN1] = new Peca();
        peca[TN1].defineTexto(CODIGO_UNICODE_TORRE_NEGRA);
        peca[TN1].escrevePeca((JLabel)tabuleiro.get(H8));      
        peca[TN1].defineCasa(H8);
        peca[TN1].defineValor(4);
        pecaDaCasa[H8] = TN1;
        
        criaBitBoard();   
        if(inverterTabuleiro == true) inverteBitboards();
        converteBitBoard();
        
        Component[] comps = spinnerProfundidade.getEditor().getComponents();
        for (Component component : comps) {
            component.setFocusable(false);
        }
        
        if(!cor) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            memoriza();
            if(inverterTabuleiro == true) inverteBitboards();
            backup();
            if(consultaAtiva == true) {
                ArrayList<BitSet> estado = new ArrayList<>();
                estado.add((BitSet)bitabuleiroPB.clone());
                estado.add((BitSet)bitabuleiroTB.clone());
                estado.add((BitSet)bitabuleiroPN.clone());
                estado.add((BitSet)bitabuleiroTN.clone());
                
                ArrayList<BitSet> sucesso = consultaBD(profundidade, bitabuleiroPB, bitabuleiroTB, bitabuleiroPN, bitabuleiroTN);
                if(sucesso == null) {
                    hitOuMissLabel.setText("(miss)");
                    aplicaNovoEstado(minimax(bitabuleiroPB, bitabuleiroTB, bitabuleiroPN, bitabuleiroTN, memoria.peek().get(0), memoria.peek().get(2)));
                }
                else {
                    hitOuMissLabel.setText("(hit (" + profundidades.get(estado.hashCode()).toString() + "))");
                    aplicaNovoEstado(sucesso);
                }
            }
            else {
                aplicaNovoEstado(minimax(bitabuleiroPB, bitabuleiroTB, bitabuleiroPN, bitabuleiroTN, memoria.peek().get(0), memoria.peek().get(2)));
            }          
            if(inverterTabuleiro == true) inverteBitboards();
            this.setCursor(Cursor.getDefaultCursor());
            pintaRastroMovimento();
            converteBitBoard();
        }
           
    }
    
    public void backup() {
        
        if(inverterTabuleiro == true) inverteBitboards();
                        
        PBoriginal = (BitSet)bitabuleiroPB.clone();
        TBoriginal = (BitSet)bitabuleiroTB.clone();
        PNoriginal = (BitSet)bitabuleiroPN.clone();
        TNoriginal = (BitSet)bitabuleiroTN.clone();
        
        if(inverterTabuleiro == true) inverteBitboards();
        
    }
    
    public void pintaRastroMovimento() {
                
        BitSet novoPN = new BitSet();
        BitSet novoPB = new BitSet();
        
        novaposicaoocupada = -1;
        velhaposicaoocupada = -1;
        BitSet temp;
        int n;
        
        temp = (BitSet)(bitabuleiroPB).clone();
        temp.andNot(PBoriginal);
        if((n = temp.nextSetBit(0))>=0) novaposicaoocupada = n;
        temp = (BitSet)(bitabuleiroTB).clone();
        temp.andNot(TBoriginal);
        if((n = temp.nextSetBit(0))>=0) novaposicaoocupada = n;
        temp = (BitSet)(bitabuleiroPN).clone();
        temp.andNot(PNoriginal); 
        if((n = temp.nextSetBit(0))>=0) novaposicaoocupada = n;
        temp = (BitSet)(bitabuleiroTN).clone();
        temp.andNot(TNoriginal);
        if((n = temp.nextSetBit(0))>=0) novaposicaoocupada = n; 
        
        temp = (BitSet)(PBoriginal).clone();
        temp.andNot(bitabuleiroPB);
        if((n = temp.nextSetBit(0))>=0) velhaposicaoocupada = n;
        temp = (BitSet)(TBoriginal).clone();
        temp.andNot(bitabuleiroTB);
        if((n = temp.nextSetBit(0))>=0) velhaposicaoocupada = n;
        temp = (BitSet)(PNoriginal).clone();
        temp.andNot(bitabuleiroPN);
        if((n = temp.nextSetBit(0))>=0) velhaposicaoocupada = n;
        temp = (BitSet)(TNoriginal).clone();
        temp.andNot(bitabuleiroTN);
        if((n = temp.nextSetBit(0))>=0) velhaposicaoocupada = n;
        
        if(novaposicaoocupada>=0 && velhaposicaoocupada>=0) {
                ((JLabel)tabuleiro.get(velhaposicaoocupada)).setBackground(Color.magenta);
                ((JLabel)tabuleiro.get(novaposicaoocupada)).setBackground(Color.cyan);
        }      
        
    }
    
    public void desfazSelecoes(int peca){
        
        if(novaposicaoocupada==peca) {
            ((JLabel)tabuleiro.get(novaposicaoocupada)).setBackground(corDaCasa[novaposicaoocupada]);
            novaposicaoocupada = -1;
        }
        if(velhaposicaoocupada==peca) {
            ((JLabel)tabuleiro.get(velhaposicaoocupada)).setBackground(corDaCasa[velhaposicaoocupada]);
            velhaposicaoocupada = -1;
        }
        if(casa_selecionada==peca) {
            ((JLabel)tabuleiro.get(casa_selecionada)).setBackground(corDaCasa[casa_selecionada]);
            casa_selecionada = -1;
        }
        if(casa_inimiga_selecionada==peca) {
            ((JLabel)tabuleiro.get(casa_inimiga_selecionada)).setBackground(corDaCasa[casa_inimiga_selecionada]);
            casa_inimiga_selecionada = -1;
        }
        if(casa_vazia_selecionada==peca){
            casa_vazia_selecionada = -1;
        }
        
    }
    
    public void inverteBitboards(){
        
        boolean aux;
        
        for(int i=0; i<64/2; i++){
            
            aux = bitabuleiroPB.get(i);
            bitabuleiroPB.set(i, bitabuleiroPB.get(63-i));
            bitabuleiroPB.set(63-i, aux);
                    
            aux = bitabuleiroTB.get(i);
            bitabuleiroTB.set(i, bitabuleiroTB.get(63-i));
            bitabuleiroTB.set(63-i, aux);
            
            aux = bitabuleiroPN.get(i);
            bitabuleiroPN.set(i, bitabuleiroPN.get(63-i));
            bitabuleiroPN.set(63-i, aux);
            
            aux = bitabuleiroTN.get(i);
            bitabuleiroTN.set(i, bitabuleiroTN.get(63-i));
            bitabuleiroTN.set(63-i, aux);
      
        }
        
    }
    
    public void movimentoDaPeca(int casa){
        int id_peca;
        
        if ((pecaDaCasa[casa] != CASA_VAZIA) && (pecaDaCasa[casa]%2 != 0) && (casa_selecionada == CASA_VAZIA)){
            
            if(casa_inimiga_selecionada != CASA_VAZIA) {
                ((JLabel)tabuleiro.get(casa_inimiga_selecionada)).setBackground(corDaCasa[casa_inimiga_selecionada]);
            }
            casa_vazia_selecionada = CASA_VAZIA;
            ColarMenuItem.setEnabled(false);
            id_clipboard = -1;
            casa_inimiga_selecionada = casa;
            ((JLabel)tabuleiro.get(casa)).setBackground(Color.orange);
            MenuRemoverPeca.setEnabled(true);
            RecortarMenuItem.setEnabled(true);
            
        }
        
        if ((pecaDaCasa[casa] != CASA_VAZIA) && (pecaDaCasa[casa]%2 == 0)){
            
            if(novaposicaoocupada>=0) ((JLabel)tabuleiro.get(novaposicaoocupada)).setBackground(corDaCasa[novaposicaoocupada]);
            if(velhaposicaoocupada>=0) ((JLabel)tabuleiro.get(velhaposicaoocupada)).setBackground(corDaCasa[velhaposicaoocupada]);
            if(casa_inimiga_selecionada != CASA_VAZIA) {
                ((JLabel)tabuleiro.get(casa_inimiga_selecionada)).setBackground(corDaCasa[casa_inimiga_selecionada]);
                casa_inimiga_selecionada = CASA_VAZIA;
            }
            MenuRemoverPeca.setEnabled(true);
            
            if (casa_selecionada != CASA_VAZIA){
              ((JLabel)tabuleiro.get(casa_selecionada)).setBackground(corDaCasa[casa_selecionada]);  
            }           
            casa_vazia_selecionada = CASA_VAZIA;
            RecortarMenuItem.setEnabled(true);
            ColarMenuItem.setEnabled(false);
            id_clipboard = -1;
            casa_selecionada = casa;
            ((JLabel)tabuleiro.get(casa)).setBackground(Color.green);
            casa_destino = CASA_VAZIA;
        }
        if (((pecaDaCasa[casa] == CASA_VAZIA) || (pecaDaCasa[casa]%2 != 0)) && casa_selecionada != CASA_VAZIA){
            
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            
            memoriza();
            casa_vazia_selecionada = CASA_VAZIA;
            ColarMenuItem.setEnabled(false);
            id_clipboard = -1;
            MenuRemoverPeca.setEnabled(false);
            RecortarMenuItem.setEnabled(false);
            
            casa_destino = casa;
            if (pecaDaCasa[casa_destino]%2 != 0 && pecaDaCasa[casa_destino] != CASA_VAZIA){
                 peca[pecaDaCasa[casa_destino]].defineCasa(CASA_VAZIA);
            }
            id_peca = pecaDaCasa[casa_selecionada];
            peca[id_peca].trocaCasa((JLabel)tabuleiro.get(casa_selecionada), (JLabel)tabuleiro.get(casa_destino), casa_destino);
            pecaDaCasa[casa_selecionada] = CASA_VAZIA;
            pecaDaCasa[casa_destino] = id_peca;
            ((JLabel)tabuleiro.get(casa_selecionada)).setBackground(corDaCasa[casa_selecionada]);
            casa_selecionada = CASA_VAZIA;
            casa_destino = CASA_VAZIA;
            
            criaBitBoard();           
            
            converteBitBoard();   //deveria atualizar o movimento antes do minimax :/
            boolean ok = false;
            if(ok == true ) return;
            
            if(inverterTabuleiro == true) inverteBitboards(); //inverte para o normal
            
            detectarEnpassada();
            backup();
            if(consultaAtiva == true) {
                ArrayList<BitSet> estado = new ArrayList<>();
                estado.add((BitSet)bitabuleiroPB.clone());
                estado.add((BitSet)bitabuleiroTB.clone());
                estado.add((BitSet)bitabuleiroPN.clone());
                estado.add((BitSet)bitabuleiroTN.clone());
                
                ArrayList<BitSet> sucesso = consultaBD(profundidade, bitabuleiroPB, bitabuleiroTB, bitabuleiroPN, bitabuleiroTN);
                if(sucesso == null) {
                    hitOuMissLabel.setText("(miss)");
                    aplicaNovoEstado(minimax(bitabuleiroPB, bitabuleiroTB, bitabuleiroPN, bitabuleiroTN, memoria.peek().get(0), memoria.peek().get(2)));
                }
                else {
                    hitOuMissLabel.setText("(hit (" + profundidades.get(estado.hashCode()).toString() + "))");
                  // aplicaNovoEstado(minimax(bitabuleiroPB, bitabuleiroTB, bitabuleiroPN, bitabuleiroTN, memoria.peek().get(0), memoria.peek().get(2)));
aplicaNovoEstado(sucesso);
                }
            }
            else {
                aplicaNovoEstado(minimax(bitabuleiroPB, bitabuleiroTB, bitabuleiroPN, bitabuleiroTN, memoria.peek().get(0), memoria.peek().get(2)));
            }
            
            if(inverterTabuleiro == true) inverteBitboards(); //inverte para invertido
            
            
            if(!futuro.isEmpty()){
                ArrayList<BitSet> estado = new ArrayList<>();
                estado.add((BitSet)bitabuleiroPB.clone());
                estado.add((BitSet)bitabuleiroTB.clone());
                estado.add((BitSet)bitabuleiroPN.clone());
                estado.add((BitSet)bitabuleiroTN.clone());
                
                if(!estado.equals(futuro.peek())){
                    futuro.removeAllElements();
                    RefazerMenuItem.setEnabled(false);
                }
                
            }
            
            
            this.setCursor(Cursor.getDefaultCursor());
            
            pintaRastroMovimento();
            converteBitBoard();
            
        }
        
        else {
            casa_vazia_selecionada = casa;
            if(id_clipboard >= 0) ColarMenuItem.setEnabled(true);
        }
            
    }
    
    public void criaBitBoard(){
        // Cria botboards do estado atual do tabuleiro a partir do array de pecas
        bitabuleiroPB.clear();
        bitabuleiroPN.clear();
        bitabuleiroTB.clear();
        bitabuleiroTN.clear();       
        for (int id=0;id<NRO_PEOES*2;id=id+2){
            if (peca[id].pegaCasa() > CASA_VAZIA){
                bitabuleiroPB.set(peca[id].pegaCasa());
            }
            if (peca[id+1].pegaCasa() > CASA_VAZIA){
                bitabuleiroPN.set(peca[id+1].pegaCasa());
            }
        }
        for (int id=TB0;id<TB0+NRO_TORRES*2;id=id+2){
            if (peca[id].pegaCasa() > CASA_VAZIA){
                bitabuleiroTB.set(peca[id].pegaCasa());
            }
            if (peca[id+1].pegaCasa() > CASA_VAZIA){
                bitabuleiroTN.set(peca[id+1].pegaCasa());
            }
        }
    }
    
    public BitSet avancaPeao(BitSet boardP, BitSet boardTAly, BitSet boardPEnemy, BitSet boardTEnemy, int sentido){
        // Gera as possibilidades de avanco do peao
        BitSet movPeao = new BitSet();
        BitSet entraves = new BitSet();
              
        int deslocamento = 8;
        int limite = 0;
        
        entraves = (BitSet)boardPEnemy.clone();
        entraves.or(boardTEnemy);
        entraves.or(boardTAly);
        entraves.or(boardP);
        movPeao.clear();
        
        if (sentido == CIMA){
            deslocamento = -8;
            limite = 63;
        
            for (int i = boardP.nextSetBit(0); i >= 0; i = boardP.nextSetBit(i+1)){
               if ((i-deslocamento)<=limite){                   
                   if (!entraves.get(i-deslocamento) && (i-deslocamento <= limite)){
                       movPeao.set(i-deslocamento);
                   }
               }
           }
        }
        else{      
            for (int i = boardP.nextSetBit(0); i >= 0; i = boardP.nextSetBit(i+1)){
               if ((i-deslocamento)>=limite){
                   if (!entraves.get(i-deslocamento) && (i-deslocamento >= limite)){
                       movPeao.set(i-deslocamento);
                   }
               }
           }
        }
        
        return movPeao;
    }
    
    public BitSet saltoDuploPeao(BitSet boardP, BitSet boardTAly, BitSet boardPEnemy, BitSet boardTEnemy, int sentido){
        // Movimento inicial de salto duplo do Peao
        BitSet movPeao = new BitSet();
        BitSet entraves = new BitSet();
              
        int deslocamento = 16;
        
        entraves = (BitSet)boardPEnemy.clone();
        entraves.or(boardTEnemy);
        entraves.or(boardTAly);
        entraves.or(boardP);
        movPeao.clear();
        
        if (sentido == CIMA){
            deslocamento = -16;
        
            for (int i = boardP.nextSetBit(0); i >= 0; i = boardP.nextSetBit(i+1)){
               if ((encontra_linha(i) == 2)){
                   if (!entraves.get(i-deslocamento) && !entraves.get(i-(deslocamento/2))){
                       movPeao.set(i-deslocamento);
                   }
               }
           }
        }
        else{      
            for (int i = boardP.nextSetBit(0); i >= 0; i = boardP.nextSetBit(i+1)){
               if ((encontra_linha(i) == 7)){
                   if (!entraves.get(i-deslocamento) && !entraves.get(i-(deslocamento/2))){
                       movPeao.set(i-deslocamento);
                   }
               }
           }
        }
      
        return movPeao;
    }
    
    public BitSet ataquePeao(BitSet boardP, BitSet boardTAly, BitSet boardPEnemy, BitSet boardTEnemy, int sentido){
        // Gera as possibilidades de ataque diagonal do peao
        BitSet movPeao = new BitSet();
        BitSet alvos = new BitSet();
        BitSet limite = new BitSet ();
        BitSet torres = new BitSet ();
        BitSet peoes = new BitSet ();
        BitSet todomundo = new BitSet();
        int deslocamento = 0;
        int borda = 0;
        
        limite.clear();
        todomundo.clear();
        todomundo.or(boardP);
        todomundo.or(boardTAly);
        todomundo.or(boardPEnemy);
        todomundo.or(boardTEnemy);
        torres = (BitSet)boardTEnemy.clone();
        peoes = (BitSet)boardPEnemy.clone();       
        alvos = (BitSet)boardPEnemy.clone();
        alvos.or(boardTEnemy);
        movPeao.clear();
        
        if (sentido == DIAGONAL_DIR_SUP){
            deslocamento = 9;
            borda = 63;
            limite = (BitSet)fronteiras_do_universo_esq.clone();
            
            for (int i = boardP.nextSetBit(0); i >= 0; i = boardP.nextSetBit(i+1)){
                if ((i+deslocamento)<=borda){
                    if ((alvos.get(i+deslocamento) && !limite.get(i+deslocamento)) && (i+deslocamento <= borda)){
                        movPeao.set(i+deslocamento);                 
                    }
                }
            }              
        }
        else{
        if (sentido == DIAGONAL_DIR_INF){
            deslocamento = -7;
            borda = 0;
            limite = (BitSet)fronteiras_do_universo_esq.clone();
            
            for (int i = boardP.nextSetBit(0); i >= 0; i = boardP.nextSetBit(i+1)){
                if ((i+deslocamento)>=borda){
                    if ((alvos.get(i+deslocamento) && !limite.get(i+deslocamento)) && (i+deslocamento >= borda)){
                        movPeao.set(i+deslocamento);                 
                    }
                }
            }              
        }
        else{
        if (sentido == DIAGONAL_ESQ_SUP){
            deslocamento = 7;
            borda = 63;
            limite = (BitSet)fronteiras_do_universo_dir.clone();
            
            for (int i = boardP.nextSetBit(0); i >= 0; i = boardP.nextSetBit(i+1)){
                if ((i+deslocamento)<=borda){
                    if ((alvos.get(i+deslocamento) && !limite.get(i+deslocamento)) && (i+deslocamento <= borda)){
                        movPeao.set(i+deslocamento);                 
                    }
                }
            }              
        }
        else{
        if (sentido == DIAGONAL_ESQ_INF){
            deslocamento = -9;
            borda = 0;
            limite = (BitSet)fronteiras_do_universo_dir.clone();
            
            for (int i = boardP.nextSetBit(0); i >= 0; i = boardP.nextSetBit(i+1)){
                if ((i+deslocamento)>=borda){
                    if ((alvos.get(i+deslocamento) && !limite.get(i+deslocamento)) && (i+deslocamento >= borda)){
                        movPeao.set(i+deslocamento);                 
                    }
                }
            }              
        }            
        }
        }
        }
     
        return movPeao;
        
    }
    
    public BitSet aIncrivelFuncaoDaMovimentacaoDasTorres(BitSet movTorreUm, BitSet outros, BitSet alvos, int j, BitSet boardT){
       if (boardT.cardinality()>=1){  
           // MOVIMENTO HORIZONTAL PARA DIREITA
           int i = j;
           while (!fronteiras_do_universo_dir.get(i) && (!outros.get(i+1))){
               if (alvos.get(i+1)){
                   movTorreUm.set(i+1);
                   break;
               }
               else{
                   movTorreUm.set(i+1);
                   i=i+1;
               } 
           }
           
           // MOVIMENTO HORIZONTAL PARA ESQUERDA
           i=j;
           while (!fronteiras_do_universo_esq.get(i) && (!outros.get(i-1))){
               if (alvos.get(i-1)){
                   movTorreUm.set(i-1);
                   break;
               }
               else{
                   movTorreUm.set(i-1);                  
                   i=i-1;
               } 
               
           }
           
           // MOVIMENTO VERTICAL PARA CIMA
           i=j;
           while (i<A8 && (!outros.get(i+8))){
               if (alvos.get(i+8)){
                   movTorreUm.set(i+8);
                   break;
               }
               else{
                   movTorreUm.set(i+8);
                   i=i+8;
               }   
           }
           
           // MOVIMENTO VERTICAL PARA BAIXO         
           i=j;
           while (i>H1 && (!outros.get(i-8))){
               if (alvos.get(i-8)){
                   movTorreUm.set(i-8);
                   break;
               }
               else{
                   movTorreUm.set(i-8);
                   i=i-8;
               }     
           }       
       } 
              
       return movTorreUm;
    }
 
    public BitSet avancaTorre(BitSet boardT, BitSet boardPAly, BitSet boardPEnemy, BitSet boardTEnemy, int torre){
        // Gera as possibilidades de movimentacao e ataque da torre
        BitSet movTorreUm = new BitSet();
        BitSet movTorreDois = new BitSet();
        BitSet aliados = new BitSet(); // pecas do mesmo time
        BitSet alvos = new BitSet(); // pecas adversarias
        
        movTorreUm.clear();
        movTorreDois.clear();
        aliados.clear();
        alvos.clear();
        
        aliados.or(boardT);
        aliados.or(boardPAly);
        alvos.or(boardTEnemy);
        alvos.or(boardPEnemy);
      
        int j = boardT.nextSetBit(0);
        movTorreUm = aIncrivelFuncaoDaMovimentacaoDasTorres(movTorreUm, aliados, alvos, j, boardT);
        j = boardT.nextSetBit(j+1);
        if (j >= 0){  
            movTorreDois = aIncrivelFuncaoDaMovimentacaoDasTorres(movTorreDois, aliados, alvos, j, boardT);
        }
        
        if (torre == 1){
            return movTorreUm;
        }
        else{
            return movTorreDois;
        }
        
    }
    
    public void converteBitBoard(){
        // Atualiza o array de pecas e de casas a partir dos bitboards
        int jpb=PB0; 
        int jpn=PN0; 
        int jtb=TB0; 
        int jtn=TN0;
        for (int i=PB0;i<20;i++){
            peca[i].defineCasa(CASA_VAZIA);
        }
        for (int i=A1;i<NRO_COLUNAS*NRO_LINHAS;i++){
            pecaDaCasa[i] = CASA_VAZIA;
            ((JLabel)tabuleiro.get(i)).setText(null);
        }
        for (int i=0;i<NRO_COLUNAS*NRO_LINHAS;i++){
            if(bitabuleiroPB.get(i)){
                peca[jpb].defineCasa(i);
                pecaDaCasa[i] = jpb;
                peca[jpb].escrevePeca((JLabel)tabuleiro.get(i));
                jpb=jpb+2;
            }
            if (bitabuleiroPN.get(i)){
                peca[jpn].defineCasa(i);
                pecaDaCasa[i] = jpn;
                peca[jpn].escrevePeca((JLabel)tabuleiro.get(i));
                jpn = jpn+2;             
            }
            if (bitabuleiroTB.get(i)){
                peca[jtb].defineCasa(i);
                pecaDaCasa[i] = jtb;
                peca[jtb].escrevePeca((JLabel)tabuleiro.get(i));
                jtb = jtb+2;             
            }
            if (bitabuleiroTN.get(i)){
                peca[jtn].defineCasa(i);
                pecaDaCasa[i] = jtn;
                peca[jtn].escrevePeca((JLabel)tabuleiro.get(i));
                jtn = jtn+2;             
            }            
        }
    }
    
    public void detectarEnpassada(){
    
        if(PBoriginal!=null){

        if(bitabuleiroPN.get(A5) && bitabuleiroPB.get(A6) && PBoriginal.get(B5) && PNoriginal.get(A7)) {           
            bitabuleiroPN.clear(A5);
        }

        for(int i = bitabuleiroPN.nextSetBit(B5); i>=0 && i<=G5; i = bitabuleiroPN.nextSetBit(i+1)) {

            if(bitabuleiroPB.get(i+8) && PBoriginal.get(i+1) && PNoriginal.get(i+16)) {             
                bitabuleiroPN.clear(i);
            }
            
            if(bitabuleiroPB.get(i+8) && PBoriginal.get(i-1) && PNoriginal.get(i+16)) {        
                bitabuleiroPN.clear(i);
            }
        }

        if(bitabuleiroPN.get(H5) && bitabuleiroPB.get(H6) && PBoriginal.get(G5) && PNoriginal.get(H7)) {
            bitabuleiroPN.clear(H5);
        }
        }
    }
    
    ArrayList<BitSet> fabricaDeEstados(ArrayList<BitSet> todosOsEstados, BitSet boardPB, BitSet boardTB, BitSet boardPN, BitSet boardTN, BitSet antePB, BitSet antePN, boolean cor, boolean torres){
        
        BitSet avanteP;
        BitSet avante2P;
        BitSet atk1P;
        BitSet atk2P;
        BitSet avanteT1;
        BitSet avanteT2;
        
        todosOsEstados.clear();
  
        if (cor){
            avanteP  = avancaPeao(boardPB, boardTB, boardPN, boardTN, CIMA);
            avante2P = saltoDuploPeao(boardPB, boardTB, boardPN, boardTN, CIMA);
            atk1P    = ataquePeao(boardPB, boardTB, boardPN, boardTN, DIAGONAL_DIR_SUP);
            atk2P    = ataquePeao(boardPB, boardTB, boardPN, boardTN, DIAGONAL_ESQ_SUP);
            avanteT1 = avancaTorre(boardTB, boardPB, boardPN, boardTN, 1);
            avanteT2 = avancaTorre(boardTB, boardPB, boardPN, boardTN, 2);
        }
        else{
            avanteP  = avancaPeao(boardPN, boardTN, boardPB, boardTB, BAIXO);
            avante2P = saltoDuploPeao(boardPN, boardTN, boardPB, boardTB, BAIXO);
            atk1P    = ataquePeao(boardPN, boardTN, boardPB, boardTB, DIAGONAL_DIR_INF); 
            atk2P    = ataquePeao(boardPN, boardTN, boardPB, boardTB, DIAGONAL_ESQ_INF);
            avanteT1 = avancaTorre(boardTN, boardPN, boardPB, boardTB, 1); 
            avanteT2 = avancaTorre(boardTN, boardPN, boardPB, boardTB, 2);
        }
        
        for (int i = atk1P.nextSetBit(0); i >= 0; i = atk1P.nextSetBit(i+1)) {
             
            BitSet estadoPB = (BitSet)boardPB.clone();
            BitSet estadoTB = (BitSet)boardTB.clone();
            BitSet estadoPN = (BitSet)boardPN.clone();
            BitSet estadoTN = (BitSet)boardTN.clone();
             
             if(cor){
                 estadoPB.clear(i-9);
                 estadoPB.set(i);
                 estadoPN.clear(i);      
                 estadoTN.clear(i);
             }
             else{
                 estadoPN.clear(i+7);
                 estadoPN.set(i);               
                 estadoPB.clear(i);
                 estadoTB.clear(i);
             }
             
             todosOsEstados.add(estadoPB);
             todosOsEstados.add(estadoTB);
             todosOsEstados.add(estadoPN);
             todosOsEstados.add(estadoTN);
         }         
        
         for (int i = atk2P.nextSetBit(0); i >= 0; i = atk2P.nextSetBit(i+1)) {
             
             BitSet estadoPB = (BitSet)boardPB.clone();
             BitSet estadoTB = (BitSet)boardTB.clone();
             BitSet estadoPN = (BitSet)boardPN.clone();
             BitSet estadoTN = (BitSet)boardTN.clone();
             
             if(cor){
                 estadoPB.clear(i-7);
                 estadoPB.set(i);
                 estadoPN.clear(i);                
                 estadoTN.clear(i);
             }
             else{
                 estadoPN.clear(i+9);
                 estadoPN.set(i);
                 estadoPB.clear(i);        
                 estadoTB.clear(i);
             }
             
             todosOsEstados.add(estadoPB);
             todosOsEstados.add(estadoTB);
             todosOsEstados.add(estadoPN);
             todosOsEstados.add(estadoTN);             
         }
        
         for (int i = avanteP.nextSetBit(0); i >= 0; i = avanteP.nextSetBit(i+1)) {
             
             BitSet estadoPB = (BitSet)boardPB.clone();
             BitSet estadoTB = (BitSet)boardTB.clone();
             BitSet estadoPN = (BitSet)boardPN.clone();
             BitSet estadoTN = (BitSet)boardTN.clone();
             
             if (cor){
                 estadoPB.clear(i-8);
                 estadoPB.set(i);
             }
             else{
                 estadoPN.clear(i+8);
                 estadoPN.set(i);
             }
             
             todosOsEstados.add(estadoPB);
             todosOsEstados.add(estadoTB);
             todosOsEstados.add(estadoPN);
             todosOsEstados.add(estadoTN);
         }
         
         for (int i = avante2P.nextSetBit(0); i >= 0; i = avante2P.nextSetBit(i+1)) {
             
             BitSet estadoPB = (BitSet)boardPB.clone();
             BitSet estadoTB = (BitSet)boardTB.clone();
             BitSet estadoPN = (BitSet)boardPN.clone();
             BitSet estadoTN = (BitSet)boardTN.clone();
             
             if (cor){
                 estadoPB.clear(i-16);
                 estadoPB.set(i);
             }
             else{
                 estadoPN.clear(i+16);
                 estadoPN.set(i);
             }
             
             todosOsEstados.add(estadoPB);
             todosOsEstados.add(estadoTB);
             todosOsEstados.add(estadoPN);
             todosOsEstados.add(estadoTN);
         }         
         
 if(torres){
        for (int i = avanteT1.nextSetBit(0); i >= 0; i = avanteT1.nextSetBit(i+1)) {
             
             BitSet estadoPB = (BitSet)boardPB.clone();
             BitSet estadoTB = (BitSet)boardTB.clone();
             BitSet estadoPN = (BitSet)boardPN.clone();
             BitSet estadoTN = (BitSet)boardTN.clone();
             
             if(cor){
                 estadoTB.clear(estadoTB.nextSetBit(0));
                 estadoTB.set(i);
                 estadoPN.clear(i);
                 estadoTN.clear(i);                 
             }
             else{
                 estadoTN.clear(estadoTN.nextSetBit(0));
                 estadoTN.set(i);
                 estadoPB.clear(i);
                 estadoTB.clear(i);
             }
             
            
             todosOsEstados.add(estadoPB);
             todosOsEstados.add(estadoTB);
             todosOsEstados.add(estadoPN);
             todosOsEstados.add(estadoTN);                
             
         }  
         
         for (int i = avanteT2.nextSetBit(0); i >= 0; i = avanteT2.nextSetBit(i+1)) {
             
             BitSet estadoPB = (BitSet)boardPB.clone();
             BitSet estadoTB = (BitSet)boardTB.clone();
             BitSet estadoPN = (BitSet)boardPN.clone();
             BitSet estadoTN = (BitSet)boardTN.clone();
             
             if(cor){
                 estadoTB.clear(estadoTB.previousSetBit(63));
                 estadoTB.set(i);
                 estadoPN.clear(i);
                 estadoTN.clear(i);                 
             }
             else{
                 estadoTN.clear(estadoTN.previousSetBit(63));
                 estadoTN.set(i);
                 estadoPB.clear(i);
                 estadoTB.clear(i);                 
             }
            
             todosOsEstados.add(estadoPB);
             todosOsEstados.add(estadoTB);
             todosOsEstados.add(estadoPN);
             todosOsEstados.add(estadoTN); 
         }           
 }         
         if(cor == NEGRO) {
            if(boardPB.get(A4) && boardPN.get(B4) && antePB.get(A2)) {
                BitSet estadoPB = (BitSet)boardPB.clone();
                BitSet estadoTB = (BitSet)boardTB.clone();
                BitSet estadoPN = (BitSet)boardPN.clone();
                BitSet estadoTN = (BitSet)boardTN.clone();
            
                estadoPN.set(B3);
                estadoPB.clear(A4);
                estadoPN.clear(B4);
             }


             for(int i = boardPB.nextSetBit(B4); i>=0 && i<=G4; i = boardPB.nextSetBit(i+1)) {

                BitSet estadoPB = (BitSet)boardPB.clone();
                BitSet estadoTB = (BitSet)boardTB.clone();
                BitSet estadoPN = (BitSet)boardPN.clone();
                BitSet estadoTN = (BitSet)boardTN.clone();

                if(boardPN.get(i-1) && antePB.get(i-16)) {             
                    estadoPN.set(i-8);
                    estadoPB.clear(i);
                    estadoPN.clear(i-1);        

                    todosOsEstados.add(estadoPB);
                    todosOsEstados.add(estadoTB);
                    todosOsEstados.add(estadoPN);
                    todosOsEstados.add(estadoTN);                 
                }
            

                if(boardPN.get(i+1) && antePB.get(i-16)) {        
                    estadoPN.set(i-8);
                    estadoPB.clear(i);
                    estadoPN.clear(i+1);

                    todosOsEstados.add(estadoPB);
                    todosOsEstados.add(estadoTB);
                    todosOsEstados.add(estadoPN);
                    todosOsEstados.add(estadoTN); 
                }

             }

            if(boardPB.get(H4) && boardPN.get(G4) && antePB.get(G2)) {
               BitSet estadoPB = (BitSet)boardPB.clone();
               BitSet estadoTB = (BitSet)boardTB.clone();
               BitSet estadoPN = (BitSet)boardPN.clone();
               BitSet estadoTN = (BitSet)boardTN.clone();

               estadoPN.set(G3);
               estadoPB.clear(H4);
               estadoPN.clear(G4);
            }
         }
         
         else {
             
             if(boardPN.get(A5) && boardPB.get(B5) && antePN.get(A7)) {
                BitSet estadoPB = (BitSet)boardPB.clone();
                BitSet estadoTB = (BitSet)boardTB.clone();
                BitSet estadoPN = (BitSet)boardPN.clone();
                BitSet estadoTN = (BitSet)boardTN.clone();
            
                estadoPB.set(B6);
                estadoPN.clear(A5);
                estadoPB.clear(B5);
             }


             for(int i = boardPN.nextSetBit(B5); i>=0 && i<=G5; i = boardPN.nextSetBit(i+1)) {

                BitSet estadoPB = (BitSet)boardPB.clone();
                BitSet estadoTB = (BitSet)boardTB.clone();
                BitSet estadoPN = (BitSet)boardPN.clone();
                BitSet estadoTN = (BitSet)boardTN.clone();

                if(boardPB.get(i+1) && antePN.get(i+16)) {             
                    estadoPB.set(i+8);
                    estadoPN.clear(i);
                    estadoPB.clear(i+1);        

                    todosOsEstados.add(estadoPB);
                    todosOsEstados.add(estadoTB);
                    todosOsEstados.add(estadoPN);
                    todosOsEstados.add(estadoTN);                 
                }
            

                if(boardPB.get(i-1) && antePN.get(i+16)) {        
                    estadoPB.set(i+8);
                    estadoPN.clear(i);
                    estadoPB.clear(i-1);

                    todosOsEstados.add(estadoPB);
                    todosOsEstados.add(estadoTB);
                    todosOsEstados.add(estadoPN);
                    todosOsEstados.add(estadoTN); 
                }

             }

            if(boardPN.get(H5) && boardPB.get(G5) && antePN.get(G7)) {
               BitSet estadoPB = (BitSet)boardPB.clone();
               BitSet estadoTB = (BitSet)boardTB.clone();
               BitSet estadoPN = (BitSet)boardPN.clone();
               BitSet estadoTN = (BitSet)boardTN.clone();

               estadoPB.set(G6);
               estadoPN.clear(H5);
               estadoPB.clear(G5);
            }
             
         }
    
        return todosOsEstados;
    }
        
    public ArrayList<BitSet> minimax (BitSet boardPB, BitSet boardTB, BitSet boardPN, BitSet boardTN, BitSet antePB, BitSet antePN) {
        
        ArrayList<BitSet> estadoOrigem = new ArrayList<>(), estadoDestino = null;
        boolean alimentacao = alimentacaoAtiva;
        if(alimentacao == true) {
            estadoOrigem.add((BitSet)boardPB.clone());
            estadoOrigem.add((BitSet)boardTB.clone());
            estadoOrigem.add((BitSet)boardPN.clone());
            estadoOrigem.add((BitSet)boardTN.clone());
        }
        
        int alfa = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;     
        ArrayList<BitSet> sucessores = (ArrayList)fabricaDeEstados(new ArrayList<BitSet>(), boardPB, boardTB, boardPN, boardTN, antePB, antePN, NEGRO, true);      
        
        for(int i=0; i<sucessores.size(); i+=4) {
            int x = Math.max(alfa, valormin((BitSet)sucessores.get(i), (BitSet)sucessores.get(i+1), (BitSet)sucessores.get(i+2), (BitSet)sucessores.get(i+3),
                                                boardPB, boardPN, alfa, beta, 2));              
            if(x!=alfa) {             
           
                alfa = x;
                boardPB = (BitSet)sucessores.get(i);
                boardTB = (BitSet)sucessores.get(i+1);
                boardPN = (BitSet)sucessores.get(i+2);
                boardTN = (BitSet)sucessores.get(i+3);
                
            }        
            if(beta<=alfa) break;         
        }  
      
        estadoDestino = new ArrayList<>();
        estadoDestino.add((BitSet)boardPB.clone());
        estadoDestino.add((BitSet)boardTB.clone());
        estadoDestino.add((BitSet)boardPN.clone());
        estadoDestino.add((BitSet)boardTN.clone());
        
        if(alimentacao == true) {         
            insereBD(estadoOrigem.hashCode(), estadoDestino, profundidade);  
        }
        
        return estadoDestino;
        
    }
    
    public void aplicaNovoEstado(ArrayList<BitSet> estado) {
        
        bitabuleiroPB = estado.get(0);
        bitabuleiroTB = estado.get(1);
        bitabuleiroPN = estado.get(2);
        bitabuleiroTN = estado.get(3);
        
    }
    
    int valormax(BitSet boardPB, BitSet boardTB, BitSet boardPN, BitSet boardTN, BitSet antePB, BitSet antePN, int alfa, int beta, int nivel){ 
    
        ArrayList<BitSet> sucessores = (ArrayList<BitSet>)fabricaDeEstados(new ArrayList<BitSet>(), boardPB, boardTB, boardPN, boardTN, antePB, antePN, NEGRO, true).clone();
        if(nivel>=profundidade || sucessores.isEmpty() || alfa >= 900) return avaliador(boardPB, boardTB, boardPN, boardTN);
        
        for(int i=0; i<sucessores.size(); i+=4) {       
            alfa = Math.max(alfa, valormin((BitSet)sucessores.get(i), (BitSet)sucessores.get(i+1), (BitSet)sucessores.get(i+2), (BitSet)sucessores.get(i+3), boardPB, boardPN, alfa, beta, nivel+1)); 
            if(beta<=alfa) break;
        }
                      
        return alfa;
        
    }
    
    int valormin(BitSet boardPB, BitSet boardTB, BitSet boardPN, BitSet boardTN, BitSet antePB, BitSet antePN, int alfa, int beta, int nivel){     
        
        ArrayList<BitSet> sucessores = (ArrayList<BitSet>)fabricaDeEstados(new ArrayList(), boardPB, boardTB, boardPN, boardTN, antePB, antePN, BRANCO, true).clone(); 
        if(nivel>=profundidade || sucessores.isEmpty() || beta <=-900) return avaliador(boardPB, boardTB, boardPN, boardTN);
        
        for(int i=0; i<sucessores.size(); i+=4) {       
            beta = Math.min(beta, valormax((BitSet)sucessores.get(i), (BitSet)sucessores.get(i+1), (BitSet)sucessores.get(i+2), (BitSet)sucessores.get(i+3), boardPB, boardPN, alfa, beta, nivel+1)); 
            if(beta<=alfa) break;
        }    
        
        return beta; 
    }
       
    // FUNCOES DE AVALIACAO
    int encontra_linha(int x){
        // Dado o indice de uma peca, retorna a linha em que ela se encontra
        x = (x/8)+1;
        return x;
    }
    
    int avaliador(BitSet boardPB, BitSet boardTB, BitSet boardPN, BitSet boardTN){
        // Retorna a comparacao entre os estados do jogador branco e do jogador negro
        //return (avalB(boardPB, boardTB) - avalN(boardPN, boardTN));
        return (avalN(boardPN, boardTN) - avalB(boardPB, boardTB));
    }
    
    int avalB(BitSet boardPB, BitSet boardTB){
        // Retorna o valor do estado das pecas brancas
        int sum = 0;
        int batedor = 0;
        int linha = 0;
        for (int i = boardPB.nextSetBit(0); i >= 0; i = boardPB.nextSetBit(i+1)){
            linha = encontra_linha(i);
            if(boardPB.cardinality() == 0){
                return -1000;
            }
            else{
                if(encontra_linha(i) == 8){
                    return 1000;
                }
                else{               
                    sum = sum + encontra_linha(i);
                }
                if (encontra_linha(i) > batedor){
                    batedor = encontra_linha(i);
                }
            }
        }      
        return (DETENCAO*(sum) + batedor + BRUTALIDADE*boardPB.cardinality() + ARTILHARIA*boardTB.cardinality());
    }
    
    int avalN(BitSet boardPN, BitSet boardTN){
        // Retorna o valor do estado das pecas negras
        int batedor = 8;
        int sum = 0;
        int linha = 0;
        for (int i = boardPN.nextSetBit(0); i >= 0; i = boardPN.nextSetBit(i+1)){
            linha = encontra_linha(i);
            if (boardPN.cardinality() == 0){
                return -1000;
            }
            else{
                if(linha == 1){
                    return 1000;
                }          
                else{
                    sum = sum + (8 - encontra_linha(i));
                }
                if (linha < batedor){
                    batedor = linha;
                }            
            }
        }
        return (MARCHA*(sum) + batedor + ALIANCA*boardPN.cardinality() + SUPORTE*boardTN.cardinality());        
    }
      
    ////////////////////////////////////////////////////////////////////////////
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InterfaceGrafica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterfaceGrafica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterfaceGrafica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfaceGrafica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>        
              
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InterfaceGrafica().setVisible(true);               
            }                      

        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem ColarMenuItem;
    private javax.swing.JMenuItem DesfazerMenu;
    private javax.swing.JLabel LabelLinha6;
    private javax.swing.JLabel LabelLinha7;
    private javax.swing.JLabel LabelLinha8;
    private javax.swing.JMenuBar Menu;
    private javax.swing.JMenuItem MenuNovoJogo;
    private javax.swing.JMenuItem MenuRemoverPeca;
    private javax.swing.JPanel PainelTabuleiro;
    private javax.swing.JMenuItem RecortarMenuItem;
    private javax.swing.JMenuItem RefazerMenuItem;
    private javax.swing.JCheckBox alimentarBDCheckBox;
    private javax.swing.JToggleButton botaoCor;
    private javax.swing.JLabel casa1A;
    private javax.swing.JLabel casa1B;
    private javax.swing.JLabel casa1C;
    private javax.swing.JLabel casa1D;
    private javax.swing.JLabel casa1E;
    private javax.swing.JLabel casa1F;
    private javax.swing.JLabel casa1G;
    private javax.swing.JLabel casa1H;
    private javax.swing.JLabel casa2A;
    private javax.swing.JLabel casa2B;
    private javax.swing.JLabel casa2C;
    private javax.swing.JLabel casa2D;
    private javax.swing.JLabel casa2E;
    private javax.swing.JLabel casa2F;
    private javax.swing.JLabel casa2G;
    private javax.swing.JLabel casa2H;
    private javax.swing.JLabel casa3A;
    private javax.swing.JLabel casa3B;
    private javax.swing.JLabel casa3C;
    private javax.swing.JLabel casa3D;
    private javax.swing.JLabel casa3E;
    private javax.swing.JLabel casa3F;
    private javax.swing.JLabel casa3G;
    private javax.swing.JLabel casa3H;
    private javax.swing.JLabel casa4A;
    private javax.swing.JLabel casa4B;
    private javax.swing.JLabel casa4C;
    private javax.swing.JLabel casa4D;
    private javax.swing.JLabel casa4E;
    private javax.swing.JLabel casa4F;
    private javax.swing.JLabel casa4G;
    private javax.swing.JLabel casa4H;
    private javax.swing.JLabel casa5A;
    private javax.swing.JLabel casa5B;
    private javax.swing.JLabel casa5C;
    private javax.swing.JLabel casa5D;
    private javax.swing.JLabel casa5E;
    private javax.swing.JLabel casa5F;
    private javax.swing.JLabel casa5G;
    private javax.swing.JLabel casa5H;
    private javax.swing.JLabel casa6A;
    private javax.swing.JLabel casa6B;
    private javax.swing.JLabel casa6C;
    private javax.swing.JLabel casa6D;
    private javax.swing.JLabel casa6E;
    private javax.swing.JLabel casa6F;
    private javax.swing.JLabel casa6G;
    private javax.swing.JLabel casa6H;
    private javax.swing.JLabel casa7A;
    private javax.swing.JLabel casa7B;
    private javax.swing.JLabel casa7C;
    private javax.swing.JLabel casa7D;
    private javax.swing.JLabel casa7E;
    private javax.swing.JLabel casa7F;
    private javax.swing.JLabel casa7G;
    private javax.swing.JLabel casa7H;
    private javax.swing.JLabel casa8A;
    private javax.swing.JLabel casa8B;
    private javax.swing.JLabel casa8C;
    private javax.swing.JLabel casa8D;
    private javax.swing.JLabel casa8E;
    private javax.swing.JLabel casa8F;
    private javax.swing.JLabel casa8G;
    private javax.swing.JLabel casa8H;
    private javax.swing.JCheckBox consultarBDCheckBox;
    private javax.swing.JLabel hitOuMissLabel;
    private javax.swing.JCheckBox inverterTabuleiroCheckBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel labelColunaA;
    private javax.swing.JLabel labelColunaB;
    private javax.swing.JLabel labelColunaC;
    private javax.swing.JLabel labelColunaD;
    private javax.swing.JLabel labelColunaE;
    private javax.swing.JLabel labelColunaF;
    private javax.swing.JLabel labelColunaG;
    private javax.swing.JLabel labelColunaH;
    private javax.swing.JLabel labelCor;
    private javax.swing.JLabel labelLinha1;
    private javax.swing.JLabel labelLinha2;
    private javax.swing.JLabel labelLinha3;
    private javax.swing.JLabel labelLinha4;
    private javax.swing.JLabel labelLinha5;
    private javax.swing.JLabel labelProfundidade;
    private javax.swing.JSlider sliderAlianca;
    private javax.swing.JSlider sliderArtilharia;
    private javax.swing.JSlider sliderBrutalidade;
    private javax.swing.JSlider sliderDetencao;
    private javax.swing.JSlider sliderMarcha;
    private javax.swing.JSlider sliderSuporte;
    private javax.swing.JSpinner spinnerProfundidade;
    // End of variables declaration//GEN-END:variables
}
