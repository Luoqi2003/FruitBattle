package shuiguoxiaoxiaole.physics;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.AnchorPane;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PhysicsManager {
    private static PhysicsManager instance = new PhysicsManager();
    private List<PhysicsElement> elements = new ArrayList<>();
    private AnchorPane gamePane;
    private AnimationTimer timer;

    private PhysicsManager() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
    }

    public static PhysicsManager getInstance() {
        return instance;
    }

    public void setGamePane(AnchorPane pane) {
        this.gamePane = pane;
    }

    public void addElement(String imagePath, double x, double y) {
        PhysicsElement element = new PhysicsElement(imagePath, x, y);
        elements.add(element);
        gamePane.getChildren().add(element.getImageView());
    }

    private void update() {
        // 更新所有元素
        for (PhysicsElement element : elements) {
            element.update();
        }

        // 检查碰撞
        for (int i = 0; i < elements.size(); i++) {
            for (int j = i + 1; j < elements.size(); j++) {
                PhysicsElement e1 = elements.get(i);
                PhysicsElement e2 = elements.get(j);
                
                if (e1.collidesWith(e2)) {
                    e1.resolveCollision(e2);
                }
            }
        }

        // 移除死亡的元素
        Iterator<PhysicsElement> iterator = elements.iterator();
        while (iterator.hasNext()) {
            PhysicsElement element = iterator.next();
            if (!element.isAlive()) {
                gamePane.getChildren().remove(element.getImageView());
                iterator.remove();
            }
        }
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public void clear() {
        for (PhysicsElement element : elements) {
            gamePane.getChildren().remove(element.getImageView());
        }
        elements.clear();
    }
} 