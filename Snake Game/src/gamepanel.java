import java.awt.*;
import javax.swing.JPanel;
import javax.swing.*;
import java.util.Random;
import java.awt.event.*;

public class gamepanel extends JPanel implements ActionListener {


    static final int screen_width = 600;
    static final int screen_height = 600;
    static final int unit_size = 40;
    static final int game_units = (screen_width * screen_height) / unit_size;
    static final int delay = 130;
    final int x[] = new int[game_units];
    final int y[] = new int[game_units];
    int bodyparts = 2;
    int appleseaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    gamepanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(screen_width, screen_height));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new mykeyadapter());
        startgame();
    }

    public void startgame () {
        newapple();
        running = true;
        timer = new Timer(delay, this);
        timer.start();

    }
    public void paintComponent (Graphics g){
            super.paintComponent(g);
            draw(g);
        }
    public void draw(Graphics g){
        if(running){
            for (int i = 0; i < screen_height / unit_size; i++) {
                g.drawLine(i * unit_size, 0, i * unit_size, screen_height);
                g.drawLine(0, i * unit_size, screen_width, i * unit_size);
            }
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, unit_size, unit_size);
            for (int i = 0; i < bodyparts; i++) {
                if (i == 0) {
                    g.setColor(Color.PINK);
                    g.fillRect(x[i], y[i], unit_size, unit_size);
                } else {
                    g.setColor(new Color(128, 0, 128));
                    g.fillRect(x[i], y[i], unit_size, unit_size);
                }
            }
            g.setColor(Color.magenta);
            g.setFont(new Font("Regular",Font.BOLD,15));
            FontMetrics metrics= getFontMetrics(g.getFont());
            g.drawString("SCORE: "+appleseaten, (screen_width- metrics.stringWidth("SCORE: "+appleseaten))/2,g.getFont().getSize());
        }


       else{
          gameover(g);
       }
    }
    public void newapple () {
            appleX = random.nextInt((int) (screen_width / unit_size)) * unit_size;
            appleY = random.nextInt((int) (screen_height / unit_size)) * unit_size;
        }
   public void move () {
        for (int i = bodyparts; i > 0; i--) {
                x[i] = x[i-1];
                y[i] = y[i-1];
            }
            switch (direction) {
                case 'U':
                    y[0] = y[0] - unit_size;
                    break;
                case 'D':
                    y[0] = y[0] + unit_size;
                    break;
                case 'L':
                    x[0] = x[0] - unit_size;
                    break;
                case 'R':
                    x[0] = x[0] + unit_size;
                    break;
            }
        }
    public void checkapple () {
            if((x[0]==appleX)&&(y[0]==appleY)){
                bodyparts++;
                appleseaten++;
                newapple();
            }
        }
    public void checkCollisions () {
            //checking head and body collision
            for (int i = bodyparts; i >0; i--) {
                if ((x[0] == x[i]) && (y[0] == y[i])) {
                    running = false;
                }
            }
            //checking head touching left boarder
            if (x[0] < 0) {
                running = false;
            }
            //checking head touching left boarder
            if (x[0] > screen_width) {
                running = false;
            }
            //checking head touching top boarder
            if (y[0] < 0) {
                running = false;
            }
            //checking head touching bottom boarder
            if (y[0] > screen_height) {
                running = false;
            }
            if (!running) {
                timer.stop();
            }

        }
    public void gameover (Graphics g){

            g.setFont(new Font("Regular",Font.BOLD,40));
            FontMetrics metrics= getFontMetrics(g.getFont());
            String gameOverText = "GAME OVER!!!";
            String scoreText = "SCORE: " + appleseaten;

            int gameOverX = (screen_width - metrics.stringWidth(gameOverText)) / 2;
            int scoreX = (screen_width - metrics.stringWidth(scoreText)) / 2;
            int textY = screen_height / 2;

            g.setColor(Color.red);
            g.drawString(gameOverText, gameOverX, textY);
            g.setColor(Color.YELLOW);
            g.drawString(scoreText, scoreX, textY + g.getFont().getSize());
        }
@Override
    public void actionPerformed(ActionEvent e)
    {
            if (running)
            {
                move();
                checkapple();
                checkCollisions();
            }
            repaint();
    }
    public class mykeyadapter extends KeyAdapter {
@Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()){
                    case KeyEvent.VK_LEFT:
                        if(direction!='R'){
                            direction='L';
                            break;
                        }
                    case KeyEvent.VK_RIGHT:
                        if(direction!='L'){
                            direction='R';
                            break;
                        }
                    case KeyEvent.VK_UP:
                        if(direction!='D'){
                        direction='U';
                        break;
                        }
                    case KeyEvent.VK_DOWN:
                        if(direction!='U'){
                        direction='D';
                        break;
                    }
                    default:
                        break;

                }

            }
        }

    }

