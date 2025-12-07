package GUI;

import java.awt.*;

public interface IComponent {
    void draw(Graphics g);
    void onMouseClick(int x, int y);
    void onKeyTyped(char c);

    // Layout helpers
    int getX();
    int getY();
    int getWidth();
    int getHeight();

    void addComponenet(IComponent c);
    Boolean removeComponent(IComponent c);


}
