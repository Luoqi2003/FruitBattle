package shuiguoxiaoxiaole.physics;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.AnchorPane;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.File;
import java.net.URL;
import java.util.Arrays;

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

    public void addElementWithRandomFruit(double x, double y) {
        List<String> fruitImages = Arrays.asList("/image/click/1.png", "/image/click/2.png");
        if (!fruitImages.isEmpty()) {
            int randomIndex = (int)(Math.random() * fruitImages.size());
            String imagePath = fruitImages.get(randomIndex);
            addElement(imagePath, x, y);
        }
    }

    public void changeElementImage(int index, String newImagePath) {
        if (index >= 0 && index < elements.size()) {
            elements.get(index).changeImage(newImagePath);
        }
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