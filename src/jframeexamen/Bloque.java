package jframeexamen;

import java.awt.Image;
import java.awt.Toolkit;

public class Bloque extends Base {

    private int golpes;

    public Bloque(int posX, int posY) {
        super(posX, posY);
        Image bueno1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("pill/green pill 1.png")).getScaledInstance(40, 32, 1);
        animacion = new Animacion();
        animacion.sumaCuadro(bueno1, 100);
        golpes = 0;
    }

    private static final String PAUSADO = "PAUSADO";
    private static final String DESAPARECE = "DESAPARECE";

    public static String getPausado() {
        return PAUSADO;
    }

    public static String getDesaparece() {
        return DESAPARECE;
    }

    public int getGolpes() {
        return golpes;
    }

    public void addGolpe() {
        golpes++;

    }

    public void cambiaimagen(int gp) {
        if (gp == 1) {
            Image bueno1;
            bueno1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("pill/green pill 2.png")).getScaledInstance(40, 32, 1);
            animacion = new Animacion();
            animacion.sumaCuadro(bueno1, 100);
        }
        if (gp == 2) {
            Image bueno1;
            bueno1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("pill/green pill 3.png")).getScaledInstance(40, 32, 1);
            animacion = new Animacion();
            animacion.sumaCuadro(bueno1, 100);

        }
    }
}
