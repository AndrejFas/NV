package GUI;

import java.awt.*;

public class Label extends Component {

    private String text;

    public Label(int x, int y, String text) {
        super(x, y, 0, 0);  // width/height not needed
        this.text = text;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawString(text, xPos, yPos);
    }
}
