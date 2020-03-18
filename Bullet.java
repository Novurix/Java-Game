import java.awt.Rectangle;
import java.awt.Graphics;

public class Bullet extends Rectangle {

    int speed = 3;
    Screen screen;

    public Bullet(int xCoor, int yCoor, Screen screen) {
        setBounds(xCoor,yCoor,5,10);
        this.screen = screen;
    }

    public void update() {
        this.y -= 1 * speed;

        if (this.y <= 0) {
            screen.DestroyBullet(this);
        }
    }

    public void render(Graphics graphics) {
        graphics.fillRect(this.x, this.y, this.width, this.height);
    }

    public Rectangle receiveBounds() {
        return new Rectangle(this.x,this.y,this.width,this.height);
    }
}