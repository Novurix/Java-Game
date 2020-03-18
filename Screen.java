import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Graphics;

import java.util.Random;

import java.awt.event.*;

public class Screen extends JPanel implements ActionListener, KeyListener, MouseListener {

    Background background;
    Window window;

    Player player;
    Enemy[] enemies = new Enemy[100];

    Bullet[] bullets = new Bullet[10000];

    boolean shoot = false;

    int maxEnemies = 10;

    Timer timer = new Timer(10,this);

    public Screen(Window window) {
        background = new Background(this);
        this.window = window;

        addKeyListener(this);
        addMouseListener(this);
        setFocusable(true);

        for (int i = 0; i < maxEnemies; i++) {
            if (enemies[i] == null) {
                Random randomY = new Random();
                Random randomX = new Random();

                int randomYPos = randomY.nextInt(720);
                int randomXPos = randomX.nextInt(1000);
                enemies[i] = new Enemy(randomXPos, randomYPos, this);
            }
        }

        player = new Player(window.getWidth()/2, window.getHeight()/2);

        timer.start();
    }

    public void paint(Graphics graphics) {
        Graphics backgroundGraphics = graphics;
        backgroundGraphics.setColor(Color.black);

        background.render(backgroundGraphics);

        Graphics playerGraphics = graphics;

        if (player.isDead) {
            playerGraphics.setColor(new Color(12,12,12));
            player.render(playerGraphics);
        }

        for (int i = 0; i < enemies.length; i++) {
            if (enemies[i] != null) {
                if (enemies[i].isDead) {
                    Graphics enemyGraphics = graphics;
                    enemyGraphics.setColor(new Color(12,12,12));
                    enemies[i].render(enemyGraphics);
                }
                else {
                    Graphics enemyGraphics = graphics;
                    enemyGraphics.setColor(Color.red);
                    enemies[i].render(enemyGraphics);
                }
            }
        }

        for (int i = 0; i < bullets.length; i++) {
            if (bullets[i] != null) {
                Graphics bulletGraphics = graphics;
                bulletGraphics.setColor(new Color(52, 235, 232));

                bullets[i].render(bulletGraphics);
            }
        }

        if (!player.isDead) {
            playerGraphics.setColor(new Color(52, 235, 232));
            player.render(playerGraphics);
        }
    }

    public void DestroyBullet(Bullet bullet) {
        for (int i = 0; i < bullets.length; i++) {
            if (bullets[i] == bullet) {
                bullets[i] = null;
            }
        }
    }

    public void DestroyEnemy(Enemy enemy) {
        for (int i = 0; i < enemies.length; i++) {
            if (enemies[i] == enemy) {
                enemies[i] = null;
            }
        }
    }

    public void CreateEnemy(int quantity) {
        for (int i = 0; i < quantity; i++) {
            for (int j = 0; j < enemies.length; j++) {
                if (enemies[j] == null) {

                    Random randomY = new Random();
                    Random randomX = new Random();

                    int randomYPos = randomY.nextInt(720);
                    int randomXPos = randomX.nextInt(1000);

                    enemies[j] = new Enemy(randomXPos,randomYPos,this);
                    break;
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (shoot == false) {
            shoot = true;
            for (int i = 0; i < bullets.length; i++) {
                if (bullets[i] == null) {
                    int xCoor = player.x + player.width/3;
                    bullets[i] = new Bullet(xCoor,player.y, this);
                    break;
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (shoot) shoot = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            System.out.println("Pressed W");
            player.setYDir(-1);
        }

        else if (e.getKeyCode() == KeyEvent.VK_S) {
            player.setYDir(1);
        }

        else if (e.getKeyCode() == KeyEvent.VK_A) {
            player.setXDir(-1);
        }

        else if (e.getKeyCode() == KeyEvent.VK_D) {
            player.setXDir(1);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            player.setYDir(0);
        }

        else if (e.getKeyCode() == KeyEvent.VK_S) {
            player.setYDir(0);
        }

        else if (e.getKeyCode() == KeyEvent.VK_A) {
            player.setXDir(0);
        }

        else if (e.getKeyCode() == KeyEvent.VK_D) {
            player.setXDir(0);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        player.update();
        repaint();

        for (int i = 0; i < enemies.length; i++) {
            if (enemies[i] != null) {
                enemies[i].update();
            }
        }

        for (int i = 0; i < bullets.length; i++) {
            if (bullets[i] != null) {
                bullets[i].update();
            }
        }

        if (player.isDead) {
            for (int i = 0; i < enemies.length; i++) {
                enemies[i] = null;
            }
            for (int i = 0; i < bullets.length; i++) {
                bullets[i] = null;
            }
            player = new Player(window.getWidth()/2, window.getHeight()/2);
            for (int i = 0; i < maxEnemies; i++) {
                if (enemies[i] == null) {
                    Random randomY = new Random();
                    Random randomX = new Random();
    
                    int randomYPos = randomY.nextInt(720);
                    int randomXPos = randomX.nextInt(1000);
                    enemies[i] = new Enemy(randomXPos, randomYPos, this);
                }
            }
        }

        PlayerEnemyCollision();
        BulletEnemyCollision();
    }

    void PlayerEnemyCollision() {
        Rectangle playerHitBox = player.receiveBounds();
        for (int i = 0; i < enemies.length; i++) {
            if (enemies[i] != null) {
                if (enemies[i].canCollide) {
                    Rectangle enemyHitBox = enemies[i].receiveBounds();

                    if (enemyHitBox.intersects(playerHitBox)) {
                        player.Kill();
                        enemies[i].Kill();
                    }
                }
            }
        }
    }

    void BulletEnemyCollision() {
        for (int i = 0; i < bullets.length; i++) {
            if (bullets[i] != null) {
                Rectangle bulletHitBox = bullets[i].receiveBounds();
                for (int j = 0; j < enemies.length; j++) {
                    if (enemies[j] != null) {
                        if (enemies[j].canCollide) {
                            Rectangle enemyHitBox = enemies[j].receiveBounds();
        
                            if (enemyHitBox.intersects(bulletHitBox)) {
                                bullets[i] = null;
                                enemies[j].Kill();
                            }
                        }
                    }
                }
            }
        }
    }
}

class Background extends Rectangle {

    Screen screen;

    public Background(Screen screen) {
        this.screen = screen;
    }

    public void render(Graphics graphics) {
        graphics.fillRect(x, y, screen.window.getWidth(), screen.window.getHeight());
    } 
}