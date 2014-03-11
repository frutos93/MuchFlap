package jframeexamen;


import java.awt.Image;
import java.awt.Toolkit;

public class Bird extends Base {


    public Bird(int posX, int posY) {
        super(posX, posY);
        Image malo1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("pidg/pid8.png")).getScaledInstance(45, 45   , 1);
        Image malo2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("pidg/pid7.png")).getScaledInstance(45, 45   , 1);
        Image malo3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("pidg/pid6.png")).getScaledInstance(45, 45   , 1);
        Image malo4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("pidg/pid5.png")).getScaledInstance(45, 45   , 1);
        Image malo5 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("pidg/pid4.png")).getScaledInstance(45, 45   , 1);
        Image malo6 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("pidg/pid3.png")).getScaledInstance(45, 45   , 1);
        Image malo7 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("pidg/pid2.png")).getScaledInstance(45, 45   , 1);
        Image malo8 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("pidg/pid1.png")).getScaledInstance(45, 45   , 1);
        Image malo9 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("pidg/pid16.png")).getScaledInstance(45, 45   , 1);
        Image malo10 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("pidg/pid15.png")).getScaledInstance(45, 45   , 1);
        Image malo11 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("pidg/pid14.png")).getScaledInstance(45, 45   , 1);
        Image malo12 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("pidg/pid13.png")).getScaledInstance(45, 45   , 1);
        Image malo13 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("pidg/pid12.png")).getScaledInstance(45, 45   , 1);
        Image malo14 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("pidg/pid11.png")).getScaledInstance(45, 45   , 1);
        Image malo15 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("pidg/pid10.png")).getScaledInstance(45, 45   , 1);
        Image malo16 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("pidg/pid9.png")).getScaledInstance(45, 45   , 1);
        Image malo17 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("pidg/pid19.png")).getScaledInstance(45, 45   , 1);
        Image malo18 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("pidg/pid18.png")).getScaledInstance(45, 45   , 1);
        Image malo19 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("pidg/pid17.png")).getScaledInstance(45, 45   , 1);
        
        
        animacion = new Animacion();
        animacion.sumaCuadro(malo1, 100);
        animacion.sumaCuadro(malo2, 100);
        animacion.sumaCuadro(malo3, 100);
        animacion.sumaCuadro(malo4, 100);
        animacion.sumaCuadro(malo5, 100);
        animacion.sumaCuadro(malo6, 100);
        animacion.sumaCuadro(malo7, 100);
        animacion.sumaCuadro(malo8, 100);
        animacion.sumaCuadro(malo9, 100);
        animacion.sumaCuadro(malo10, 100);
        animacion.sumaCuadro(malo11, 100);
        animacion.sumaCuadro(malo12, 100);
        animacion.sumaCuadro(malo13, 100);
        animacion.sumaCuadro(malo14, 100);
        animacion.sumaCuadro(malo15, 100);
        animacion.sumaCuadro(malo16, 100);
        animacion.sumaCuadro(malo17, 100);
        animacion.sumaCuadro(malo18, 100);
        animacion.sumaCuadro(malo19, 100);
    }

    private static final String PAUSADO = "PAUSADO";
    private static final String DESAPARECE = "DESAPARECE";

    public static String getPausado() {
        return PAUSADO;
    }

    public static String getDesaparece() {
        return DESAPARECE;
    }

}
