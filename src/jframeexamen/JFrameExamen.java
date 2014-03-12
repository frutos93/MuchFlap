package jframeexamen;

import java.awt.*;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.net.URL;
import java.util.LinkedList;
import java.util.Random;

public class JFrameExamen extends JFrame implements Runnable, KeyListener, MouseListener, MouseMotionListener {

    private static final long serialVersionUID = 1L;
    // Se declaran las variables. 
    private Image dbImage;	// Imagen a proyectar	
    private Graphics dbg;	// Objeto grafico
    private int contbloques;    // Contador de bloques destruidos
    private Bird birdie;          // Objeto bola.
    private Barra1 pared;         // Objeto barra, es el movido por el jugador.
    private ParedInv pared2;
    private boolean musicafondo;// Boolean utilizado para correr o pausar la música de fondo
    private int vidas;          // Contador de vidas
    private Image game_over;    // Imagen de victoria
    private Image perder;       // Imagen de derrota
    private Image puntaje;
    private Image pause;        // Imagen usada para la pausa
    private int direccion;      // Variable para la dirección del personaje
    private int score;          // Variable de puntuacion
    private boolean move;       // Variable utilizada para saber si el personaje se esta moviendo o no
    private boolean pausa;      // Booleano para pausar
    private boolean moverbola;  // Booleano que indica si la bola se esta moviendo
    private boolean instrucciones; // Booleano indicado para saber si se estan mostrando las instrucciones
    private boolean empezar;    // Booleano para comenzar el juego y quitar la pantalla de inicio
    private LinkedList<Barra1> lista; // Listas de bloques
    private LinkedList<ParedInv> lista2;
    private LinkedList<RandomText> listaTexto;
    private Image fondo;        // Imagen de fondo
    private Image inicio;       // Imagen de inicio
    private int velocidad;
    private int aceleracion;
    private int y;
    private boolean inicia;
    private boolean salta;
    private Random rnd;
    private boolean choca;
    private long tiempo;
    private boolean terminado;
    private long tiempoActual;

    /**
     * Constructor vacio de la clase <code>JFrameExamen</code>.
     */
    public JFrameExamen() {
        init();
        start();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Metodo <I>init</I> sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se inizializan las variables o se crean los objetos a
     * usarse en el <code>Applet</code> y se definen funcionalidades.
     */
    public void init() {

        setSize(800, 500);
        inicia = false;
        //acabarjuego = false;
        contbloques = 0;//oli
        y = getHeight() / 2;
        lista = new LinkedList();
        lista2 = new LinkedList();
        listaTexto = new LinkedList();
        pausa = false;
        move = false;
        tiempo = 0;
        moverbola = false;
        musicafondo = true;
        choca = false;
        salta = false;
        velocidad = 2;

        terminado = false;
        aceleracion = 1;
        direccion = 0;
        score = 0;                    //puntaje inicial
        vidas = 3;                    //vidaas iniciales
        setBackground(Color.black);
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        birdie = new Bird(50, y);
        birdie.setMoviendose(true);
        for (int i = 1; i <= 3; i++) {
            if (i == 1) {
                pared = new Barra1(getWidth(), getHeight() / 2);
                lista.add(pared);
                pared2 = new ParedInv(pared.getPosX(), pared.getPosY() - 450);
                lista2.add(pared2);
            } else {
                Barra1 paredaux = (Barra1) lista.get(i - 2);
                pared = new Barra1(paredaux.getPosX() + paredaux.getAncho() + 200, getHeight() / 2);
                pared2 = new ParedInv(pared.getPosX(), pared.getPosY() - 450);
                lista.add(pared);
                lista2.add(pared2);
            }

        }

        URL goURL = this.getClass().getResource("barra/creditos.png");
        game_over = Toolkit.getDefaultToolkit().getImage(goURL);
        URL fURL = this.getClass().getResource("Fondo/fondo.jpg");
        fondo = Toolkit.getDefaultToolkit().getImage(fURL).getScaledInstance(getWidth(), getHeight(), 1);
        URL aURL = this.getClass().getResource("pill/gameover.jpg");
        perder = Toolkit.getDefaultToolkit().getImage(aURL);
        URL gURL = this.getClass().getResource("pill/imagenpausa.jpg");
        pause = Toolkit.getDefaultToolkit().getImage(gURL);
        URL jURL = this.getClass().getResource("fondo/winn.png");
        puntaje = Toolkit.getDefaultToolkit().getImage(jURL).getScaledInstance(getWidth() / 3, getHeight() / 3, 1);
        instrucciones = false;
        empezar = false;
        URL emp = this.getClass().getResource("barra/login.jpg");
        inicio = Toolkit.getDefaultToolkit().getImage(emp);

    }

    /**
     * Metodo <I>Start</I> sobrescrito de la clase <code>Thread</code>.<P>
     * Este metodo comienza la ejecucion del hilo. Esto llama al metodo
     * <code>run</code>
     */
    public void start() {
        // Declaras un hilo
        Thread th = new Thread(this);
        // Empieza el hilo
        th.start();
    }

    /**
     * Metodo <I>run</I> sobrescrito de la clase <code>Thread</code>.<P>
     * En este metodo se ejecuta el hilo, es un ciclo indefinido donde se
     * incrementa la posicion en x o y dependiendo de la direccion, finalmente
     * se repinta el <code>Applet</code> y luego manda a dormir el hilo.
     *
     */
    public void run() {
        tiempoActual = System.currentTimeMillis();
        while (true) {
            if (!pausa && empezar) {
                actualiza();
                checaColision();
            }
            repaint();    // Se actualiza el <code>Applet</code> repintando el contenido.
            try {
                // El thread se duerme.
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                System.out.println("Error en " + ex.toString());
            }
        }
    }

    /**
     * Metodo <I>actualiza</I>.
     * <P>
     * En este metodo se actualizan las posiciones de link como de la armadura,
     * ya sea por presionar una tecla o por moverlos con el mouse.
     */
    public void actualiza() {
        long tiempoTranscurrido = System.currentTimeMillis() - tiempoActual;
        tiempoActual += tiempoTranscurrido;
        birdie.actualiza(tiempoTranscurrido);

        if (inicia) {
            for (RandomText r : listaTexto) {
                r.cont++;
                if (r.cont > 50) {
                    listaTexto.remove(r);
                    break;
                }
            }
            velocidad = velocidad + aceleracion;
            y = y + velocidad;
            if (y < 30) {
                y = 30;
            }
            birdie.setPosY(y);
            if (salta) {
                velocidad = -10;
                salta = false;
            }
            for (Barra1 i : lista) {
                if (score <= 10) {
                    i.setPosX(i.getPosX() - 5);
                } else if (score > 10 && score <= 20) {
                    i.setPosX(i.getPosX() - 8);
                } else {
                    i.setPosX(i.getPosX() - 10);
                }
                if (i.getPosX() + i.getAncho() < 0) {
                    rnd = new Random();
                    int h;
                    h = rnd.nextInt((getHeight() / 2 + 200) - getHeight() / 2) + getHeight() / 2;
                    i.setPosY(h);
                    i.setPosX(getWidth() + 10);
                }
            }
            for (ParedInv i : lista2) {
                if (score <= 10) {
                    i.setPosX(i.getPosX() - 5);
                } else if (score > 10 && score <= 20) {
                    i.setPosX(i.getPosX() - 8);
                } else {
                    i.setPosX(i.getPosX() - 10);
                }
                if (i.getPosX() + i.getAncho() < 0) {
                    int h;
                    h = lista2.indexOf(i);
                    Barra1 paredaux = (Barra1) lista.get(h);
                    i.setPosY(paredaux.getPosY() - 450);
                    i.setPosX(paredaux.getPosX());
                }
            }

            for (ParedInv i : lista2) {
                if (birdie.getPosX() == i.getPosX()) {
                    score++;
                }
            }

        }

    }

    /**
     * Metodo usado para checar las colisiones del objeto link con el objeto
     * armadura y además con las orillas del <code>Applet</code>.
     */
    public void checaColision() {

        if (birdie.getPosY() < 30) {
            birdie.setPosY(30);
            salta = false;
        }

        if (birdie.getPosY() + birdie.getAlto() > getHeight()) {
            inicia = false;
            choca = true;
            terminado = true;
        }

        for (Barra1 i : lista) {
            if (birdie.intersecta(i)) {
                inicia = false;
                choca = true;
                terminado = true;
            }
        }
        for (ParedInv i : lista2) {
            if (birdie.intersecta(i)) {
                inicia = false;
                choca = true;
                terminado = true;
            }

        }
    }

    /**
     * Metodo <I>update</I> sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo lo que hace es actualizar el contenedor
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint(Graphics g) {
        // Inicializan el DoubleBuffer
        if (dbImage == null) {
            dbImage = createImage(this.getSize().width, this.getSize().height);
            dbg = dbImage.getGraphics();
        }

        // Actualiza la imagen de fondo.
        dbg.setColor(getBackground());
        dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);

        // Actualiza el Foreground.
        dbg.setColor(getForeground());
        paint1(dbg);

        // Dibuja la imagen actualizada
        g.drawImage(dbImage, 0, 0, this);

    }

    /**
     * Metodo <I>keypPressed</I> sobrescrito de la clase
     * <code>KeyEvent</code>.<P>
     * En este método se actualiza la variable de dirección dependiendo de la
     * tecla que haya sido precionado El parámetro e se usará cpara obtener la
     * acción de la tecla que fue presionada.
     *
     */
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {//Al presionar la flecha izquierda se mueve a la izquierda
            direccion = 3;
            move = true;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            direccion = 4; //Al presionar la flecha derecha se mueve la barra a la derecha
            move = true;
        } else if (e.getKeyCode() == KeyEvent.VK_P) {//Al presionar la P activa la Pausa del juego

            pausa = !pausa;
        } else if (e.getKeyCode() == KeyEvent.VK_E && !empezar) {//Al presionar la tecla E se empieza el juego
            empezar = true;
        } else if (e.getKeyCode() == KeyEvent.VK_S && !pausa) { //Al presionar la tecla S se apaga/prende el sonido

        } else if (e.getKeyCode() == KeyEvent.VK_I) { //Al presionar la tecla I se muestran/quitan las Instrucciones
            instrucciones = !instrucciones;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE && empezar) { //Al presionar la barra espaciadora lanza la pelota.
            salta = true;
            agregaTexto();
        } else if (e.getKeyCode() == KeyEvent.VK_R) { //Al presionar la tecla R reinicia el juego.
            choca = false;
            inicia = false;
            salta = false;
            velocidad = 2;
            terminado = false;
            score = 0;
            aceleracion = 1;
            y = getHeight() / 2;
            birdie.setPosY(y);
            lista.clear();
            lista2.clear();
            for (int i = 1; i <= 3; i++) {
                if (i == 1) {
                    pared = new Barra1(getWidth(), getHeight() / 2);
                    lista.add(pared);
                    pared2 = new ParedInv(pared.getPosX(), pared.getPosY() - 450);
                    lista2.add(pared2);
                } else {
                    Barra1 paredaux = (Barra1) lista.get(i - 2);
                    pared = new Barra1(paredaux.getPosX() + paredaux.getAncho() + 200, getHeight() / 2);
                    pared2 = new ParedInv(pared.getPosX(), pared.getPosY() - 450);
                    lista.add(pared);
                    lista2.add(pared2);
                }
            }

        }
    }

    public void keyTyped(KeyEvent e) {
    }

    /**
     * Metodo <I>keyReleased</I> sobrescrito de la clase
     * <code>KeyEvent</code>.<P>
     * En este método se verifica si alguna tecla que haya sido presionada es
     * liberada. Si es liberada la booleana que controla el movimiento se
     * convierte en falsa.
     */
    public void keyReleased(KeyEvent e) {
    }

    /**
     * Metodo <I>mouseClicked</I> sobrescrito de la clase
     * <code>MouseEvent</code>.
     * <P>
     * Este metodo es invocado cuando se ha presionado un boton del mouse en un
     * componente.
     *
     * @param e es el evento generado al ocurrir lo descrito.
     */
    public void mouseClicked(MouseEvent e) {
        if (!empezar) {
            empezar = true;
        } else {
            if (!inicia && !choca) {
                inicia = true;
            }
        }
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    /**
     * Metodo <I>MousePressed</I> sobrescrito de la clase
     * <code>mouseEvent</code>.<P>
     * En este metodo se verifica si el mouse ha dado click sobre la imágen. Al
     * verificar que haya dado un click se actualizan las coordenadas de 'x' y
     * 'y' para ajustar el desfase que puede tener la imagen con el click
     */
    public void mousePressed(MouseEvent e) {
    }

    /**
     * Metodo <I>MouseReleased</I> sobrescrito de la clase
     * <code>MouseEvent</code>.<P>
     * En este método se verifica si el click del mouse ha sido liberado, si sí
     * entonces la booleana que l ocontrola se hace falsa, para marcar que ya no
     * está siendo presionadao.
     */
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * Metodo <I>mouseReleased</I> sobrescrito de la clase
     * <code>MouseEvent</code>.<P>
     * Este metodo es invocado cuando el cursor es movido dentro de un
     * componente sin presionar ningun boton.
     *
     * @param e es el evento generado al ocurrir lo descrito.
     */
    public void mouseMoved(MouseEvent e) {

    }

    /**
     * Metodo <I>mouseDragged</I> sobrescrito de la clase
     * <code>MouseEvent</code>.<P>
     * Este metodo es invocado cuando se presiona un boton en un componente, y
     * luego este es arrastrado.
     *
     * @param e es el evento generado al ocurrir lo descrito.
     */
    public void mouseDragged(MouseEvent e) {
    }

    public void agregaTexto() {
        RandomText r;
        r = new RandomText();
        int p;
        r.posX = (int) (Math.random() * (getWidth() - 100));
        r.posY = (int) (Math.random() * (getHeight() - 20)) + 20;
        p = (int) (Math.random() * 11);
        switch (p) {
            case 0:
                r.col = Color.BLUE;
                break;
            case 1:
                r.col = Color.CYAN;
                break;
            case 2:
                r.col = Color.GRAY;
                break;
            case 3:
                r.col = Color.GREEN;
                break;
            case 4:
                r.col = Color.MAGENTA;
                break;
            case 5:
                r.col = Color.ORANGE;
                break;
            case 6:
                r.col = Color.PINK;
                break;
            case 7:
                r.col = Color.RED;
                break;
            case 8:
                r.col = Color.WHITE;
                break;
            case 9:
                r.col = Color.YELLOW;
                break;
            default:
                r.col = Color.black;
                break;
        }
        p = (int) (Math.random() * 10) + 1;
        switch (p) {
            case 0:
                r.texto = "Very wow";
                break;
            case 1:
                r.texto = "many color";
                break;
            case 2:
                r.texto = "such awesome";
                break;
            case 3:
                r.texto = "much score";
                break;
            case 4:
                r.texto = "many mejorado";
                break;
            case 5:
                r.texto = "so doge";
                break;
            case 6:
                r.texto = "such art";
                break;
            case 7:
                r.texto = "Very videogme";
                break;
            case 8:
                r.texto = "150 pokemon";
                break;
            case 9:
                r.texto = "op frutos crujientes";
                break;
            default:
                r.texto = "wow";
                break;
        }
        listaTexto.add(r);
    }

    /**
     * Metodo <I>paint1</I> sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo se dibuja la imagen con la posicion actualizada, ademas
     * que cuando la imagen es cargada te despliega una advertencia.
     *
     * @paramg es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint1(Graphics g) {
        if (!empezar && inicio != null) {
            g.drawImage(inicio, 0, 0, this);
        } else if (vidas > 0) {
            g.drawImage(fondo, 0, 0, this);
            if (birdie != null && lista != null && lista2 != null) {
                //Se Pintan todas las pildoras del juego

                g.drawImage(birdie.getImagenI(), birdie.getPosX(), birdie.getPosY(), this);//Pinta la bola
                for (Barra1 i : lista) {
                    g.drawImage(i.getImagenI(), i.getPosX(), i.getPosY(), this);  //Pinta la Barra
                }
                for (ParedInv i : lista2) {
                    g.drawImage(i.getImagenI(), i.getPosX(), i.getPosY(), this);
                }
                g.setColor(Color.black);//Despliega los puntos, las vidas y el comando de Instrucciones
                g.drawString("Puntos = " + score, 20, 50);
                g.drawString("Vidas = " + vidas, 20, 70);
                g.drawString("Presiona I para ver instrucciones.", getWidth() - 200, 50);
                g.drawString("Bloques destruidos: " + contbloques, 20, 90);
                Font fr = new Font("04b_19", Font.PLAIN, 15);
                Font fa = new Font("Arial", Font.BOLD, 16);
                for (RandomText r : listaTexto) {
                    g.setColor(r.col);
                    g.setFont(fa);
                    g.drawString(r.texto, r.posX, r.posY);
                }
                g.setFont(fr);
                g.setColor(Color.black);
                if (terminado) {
                    g.drawImage(puntaje, getWidth() / 2 - 50, getHeight() / 2 - 100, this);
                    g.drawString("" + score, getWidth() / 2, getHeight() / 2);
                } else {
                    g.drawString("" + score, getWidth() / 2, 100);
                }
                //    if (pausa) {
                //        g.setColor(Color.white);
                //        g.drawString(pill.getPausado(), pill.getPosX() + pill.getAncho() / 3, pill.getPosY() + pill.getAlto() / 2);
                //    }
                if (pausa) {//Al pausar se despliega una imágen de pausa.
                    g.drawImage(pause, 0, 20, this);
                }
                if (instrucciones) {//Al presionar la I se muestran las instrucciones
                    g.drawString("Instrucciones:", 20, 90);
                    g.drawString("Para mover la barra, presiona las teclas de flecha izquierda o derecha.", 20, 110);
                    g.drawString("Presiona R para reiniciar la partida, P para pausar y S para detener la musica.", 20, 130);
                    g.drawString("Si la pastilla cae 3 veces pierdes el juego.", 20, 150);
                    g.drawString("La pastilla tiene que golpear tres veces a cada bloque para destruirlos.", 20, 170);
                    g.drawString("Ganas el juego al haber destruido todos los bloques.", 20, 190);
                }

            } else {
                //Da un mensaje mientras se carga el dibujo	
                g.drawString("No se cargo la imagen..", 20, 20);
            }

        } else {
            g.drawImage(perder, 140, 20, this); //Cuando pierdes se despliega la pantalla de perder
            g.setColor(Color.white);
            g.drawString("Tu puntaje fue de: " + score, getWidth() / 3 + 50, 100);
        }
        if (contbloques >= 56) { //Cuando ganas se despliega la pantalla de creditos
            //acabarjuego = true;
            musicafondo = true;
            g.drawImage(game_over, 0, 20, this);

        }
    }

}
