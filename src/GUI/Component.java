package GUI;

import java.awt.*;
public class Component implements IComponent{
    protected int xPos;
    protected int yPos;
    protected int width;
    protected int height;

    public Component(int x, int y, int width, int height) {
        this.xPos = x;
        this.yPos = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics g) {
        throw new UnsupportedOperationException("Unsupported operation!");
    }

    @Override
    public void onMouseClick(int mx, int my) {}

    @Override
    public void onKeyTyped(char c) {}

    // Hit-test helper
    public boolean contains(int mx, int my) {
        return mx >= xPos && mx <= xPos + width &&
                my >= yPos && my <= yPos + height;
    }

    // Layout getters
    public int getX() { return xPos; }
    public int getY() { return yPos; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }

    @Override
    public void addComponenet(IComponent c) {
        throw new UnsupportedOperationException("Unsupported operation!");
    }

    @Override
    public Boolean removeComponent(IComponent c) {
        throw new UnsupportedOperationException("Unsupported operation!");
    }

}
