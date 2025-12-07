package GUI;

import java.awt.*;

public class Button extends Component {
    private String text;
    private ICommand onClick;

    public Button(int x, int y, int width, int height, String text) {
        super(x, y, width, height);
        this.text = text;
    }

    public void setOnClick(ICommand r) {
        this.onClick = r;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(xPos, yPos, width, height);

        g.setColor(Color.BLACK);
        g.drawRect(xPos, yPos, width, height);

        g.drawString(text, xPos + 10, yPos + height / 2 + 5);
    }

    @Override
    public void onMouseClick(int mx, int my) {
        if (contains(mx, my)) {
            if (onClick != null) onClick.run();
        }
    }
}
