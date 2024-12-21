package shuiguoxiaoxiaole.physics;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Bounds;
import shuiguoxiaoxiaole.Director;

public class PhysicsElement {
    private ImageView imageView;
    private double x, y;           
    private double vx, vy;         
    private double gravity = 0.5;  
    private double bounce = 0.7;   
    private double mass = 1.0;     // 添加质量属性
    private boolean isAlive = true;
    private static final double SIZE = 40;

    public PhysicsElement(String imagePath, double startX, double startY) {
        Image image = new Image(PhysicsElement.class.getResourceAsStream(imagePath));
        imageView = new ImageView(image);
        imageView.setFitWidth(SIZE);
        imageView.setFitHeight(SIZE);
        x = startX;
        y = startY;
        vx = (Math.random() - 0.5) * 10;
        vy = -10;
    }

    public void update() {
        vy += gravity;
        x += vx;
        y += vy;
        
        // 边界碰撞
        if (x < 0 || x > Director.WIDTH - SIZE) {
            vx = -vx * bounce;
            x = x < 0 ? 0 : Director.WIDTH - SIZE;
        }
        
        if (y > Director.HEIGHT - SIZE) {
            vy = -vy * bounce;
            y = Director.HEIGHT - SIZE;
            if (Math.abs(vy) < 1) {
                isAlive = false;
            }
        }
        
        imageView.setX(x);
        imageView.setY(y);
    }

    // 添加碰撞检测方法
    public boolean collidesWith(PhysicsElement other) {
        double dx = (this.x + SIZE/2) - (other.x + SIZE/2);
        double dy = (this.y + SIZE/2) - (other.y + SIZE/2);
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < SIZE;
    }

    // 处理碰撞
    public void resolveCollision(PhysicsElement other) {
        double dx = other.x - this.x;
        double dy = other.y - this.y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        
        if (distance == 0) return;  // 防止除以零
        
        // 计算碰撞法线
        double nx = dx / distance;
        double ny = dy / distance;
        
        // 相对速度
        double dvx = other.vx - this.vx;
        double dvy = other.vy - this.vy;
        
        // 计算冲量
        double impulse = (-(1 + bounce) * (dvx * nx + dvy * ny)) / 
                        (1/mass + 1/other.mass);
        
        // 更新速度
        this.vx -= impulse * nx / mass;
        this.vy -= impulse * ny / mass;
        other.vx += impulse * nx / other.mass;
        other.vy += impulse * ny / other.mass;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getVX() { return vx; }
    public double getVY() { return vy; }
    public void setVX(double vx) { this.vx = vx; }
    public void setVY(double vy) { this.vy = vy; }
    public ImageView getImageView() { return imageView; }
    public boolean isAlive() { return isAlive; }

    // 在 PhysicsElement 类中添加一个更改图片的方法
    public void changeImage(String imagePath) {
        Image newImage = new Image(getClass().getClassLoader().getResourceAsStream(imagePath));
        imageView.setImage(newImage);
    }
} 