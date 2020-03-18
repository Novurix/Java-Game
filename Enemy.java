import java.awt.Rectangle;
import java.awt.Graphics;

import java.util.Random;

public class Enemy extends Rectangle {

    int speed = 2;
    int dirY, dirX = 1;

    boolean isDead, canCollide = true;
    Screen screen;

    public Enemy(int xCor, int yCor, Screen screen) {
        setBounds(xCor,yCor,15,15);

        Random randomDir = new Random();
        int randomDirection = randomDir.nextInt(2);

        if (randomDirection == 0) {
            dirX = -1;
        }
        else {
            dirX = 1;
        }

        this.screen = screen;
    }

    public void render(Graphics graphics) {
        graphics.fillRect(this.x, this.y, 15, 15);
    }

    public void update() {
        if (!isDead) {
            this.x += dirX * speed;
            this.y += dirY * speed;
        }
        else {
            Random random = new Random();
            int randomQuantity = random.nextInt(100);

            if (randomQuantity+1 > 20) {
                screen.CreateEnemy(1);
            }
            else {
                screen.CreateEnemy(2);
            }

            screen.DestroyEnemy(this);
        }

        if (this.x >= 1000) {
            dirX = -1;
        }
        else if (this.x <= -25) {
            dirX = 1;
        }
    }

    public void Kill() {
        isDead = true;
        canCollide = false;

        System.out.println("Killed enemy");
    }

    public Rectangle receiveBounds() {
        return new Rectangle(this.x,this.y,this.width,this.height);
    }
}