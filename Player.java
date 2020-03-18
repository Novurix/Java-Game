import java.awt.Rectangle;
import java.awt.Graphics;

public class Player extends Rectangle {

    int speed = 2;
    int xDir, yDir;
    boolean isDead = false;

    int health = 100;

    public Player(int xCoor, int yCoor) {
        setBounds(xCoor, yCoor, 15, 15);
    }

    public void update() {
        if (!isDead) {
            this.x += xDir * speed;
            this.y += yDir * speed;
        }

        if (health <= 0) isDead = true;
    }

    public void setYDir(int yD) {
        yDir = yD;
    }

    public void setXDir(int xD) {
        xDir = xD;
    }

    public void render(Graphics graphics) {
        graphics.fillRect(this.x, this.y, this.width, this.height);
    }

    public Rectangle receiveBounds() {
        return new Rectangle(this.x,this.y,this.width,this.height);
    }

    public void Kill() {
        health = 0;
    }
}