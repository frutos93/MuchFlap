package jframeexamen;

import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.net.URL;
import java.util.LinkedList;

public class JFrameExamen extends JFrame implements Runnable, KeyListener, MouseListener, MouseMotionListener {

    private static final long serialVersionUID = 1L;
    // Se declaran las variables. 
    private Image dbImage;	// Imagen a proyectar	
    private Graphics dbg;	// Objeto grafico
    private int contbloques;    // Contador de bloques destruidos
    private Bird birdie;          // Objeto bola.
    private Bloque pill;        // Objeto Bloque usado para inicializar las listas 1 y 3
    private BloqueR pillR;      // Objeto BloqueR usado para inicializar las listas 2 y 4
    private Barra1 bar;         // Objeto barra, es el movido por el jugador.
    private boolean musicafondo;// Boolean utilizado para correr o pausar la música de fondo
    private int vidas;          // Contador de vidas
    private Image game_over;    // Imagen de victoria
    private Image perder;       // Imagen de derrota
    private Image pause;        // Imagen usada para la pausa
    private int direccion;      // Variable para la dirección del personaje
    private int score;          // Variable de puntuacion
    private boolean move;       // Variable utilizada para saber si el personaje se esta moviendo o no
    private boolean pausa;      // Booleano para pausar
    private boolean moverbola;  // Booleano que indica si la bola se esta moviendo
    private boolean instrucciones; // Booleano indicado para saber si se estan mostrando las instrucciones
    private boolean empezar;    // Booleano para comenzar el juego y quitar la pantalla de inicio
    private LinkedList<Bloque> lista; // Listas de bloques
    private LinkedList<BloqueR> lista2;
    private LinkedList<Bloque> lista3;
    private LinkedList<BloqueR> lista4;
    private Image fondo;        // Imagen de fondo
    private Image inicio;       // Imagen de inicio
    private int velocidad;
    private int aceleracion;
    private int y;
    private boolean inicia;
    private boolean salta;

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
        lista3 = new LinkedList();
        lista4 = new LinkedList();
        pausa = false;
        move = false;
        moverbola = false;
        musicafondo = true;
        velocidad = 2;
        aceleracion = 1;
        direccion = 0;
        score = 0;                    //puntaje inicial
        vidas = 3;                    //vidaas iniciales
        bar = new Barra1(getWidth() / 2, getHeight() - 30);
        bar.setPosX(getWidth() / 2 - bar.getAncho() / 2);
        setBackground(Color.black);
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        birdie = new Bird(50, y);

        URL goURL = this.getClass().getResource("barra/creditos.png");
        game_over = Toolkit.getDefaultToolkit().getImage(goURL);
        URL fURL = this.getClass().getResource("Fondo/FondoDos.jpg");
        fondo = Toolkit.getDefaultToolkit().getImage(fURL).getScaledInstance(getWidth(), getHeight(), 1);
        URL aURL = this.getClass().getResource("pill/gameover.jpg");
        perder = Toolkit.getDefaultToolkit().getImage(aURL);
        URL gURL = this.getClass().getResource("pill/imagenpausa.jpg");
        pause = Toolkit.getDefaultToolkit().getImage(gURL);

        instrucciones = false;
        empezar = false;
        URL emp = this.getClass().getResource("barra/Login.png");
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

        if (inicia) {
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
        }

        if (move) {
            bar.setMoviendose(true);
            switch (direccion) {
                case 3: {
                    bar.setPosX(bar.getPosX() - 6);
                    break; //se mueve hacia la izquierda
                }
                case 4: {

                    bar.setPosX(bar.getPosX() + 6);
                    break; //se mueve hacia la derecha
                }
            }
        }
    }

    /**
     * Metodo usado para checar las colisiones del objeto link con el objeto
     * armadura y además con las orillas del <code>Applet</code>.
     */
    public void checaColision() {
        if (bar.getPosX() + bar.getAncho() > getWidth()) {
            bar.setPosX(getWidth() - bar.getAncho());
        }
        if (bar.getPosX() < 0) {
            bar.setPosX(0);
        }
        if (birdie.getPosY() < 30) {
            birdie.setPosY(30);
            salta = false;
        }

        if (birdie.getPosY() + birdie.getAlto() > getHeight()) {
            inicia = false;
            salta = false;
            velocidad=2;
            aceleracion=1;
            birdie.setPosY(getHeight() / 2);
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
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) { //Al presionar la barra espaciadora lanza la pelota.
            salta = true;
        } else if (e.getKeyCode() == KeyEvent.VK_R) { //Al presionar la tecla R reinicia el juego.
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
        move = false;
        bar.setMoviendose(false);
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
        if (!inicia) {
            inicia = true;
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
            if (birdie != null && bar != null) {
                //Se Pintan todas las pildoras del juego

                g.drawImage(birdie.getImagenI(), birdie.getPosX(), birdie.getPosY(), this);//Pinta la bola
                g.drawImage(bar.getImagenI(), bar.getPosX(), bar.getPosY(), this);  //Pinta la Barra

                g.setColor(Color.black);//Despliega los puntos, las vidas y el comando de Instrucciones
                g.drawString("Puntos = " + score, 20, 50);
                g.drawString("Vidas = " + vidas, 20, 70);
                g.drawString("Presiona I para ver instrucciones.", getWidth() - 200, 50);
                g.drawString("Bloques destruidos: " + contbloques, 20, 90);
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
