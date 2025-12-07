package GUI;

import java.awt.*;

public class RadioButton extends Component {

    private boolean selected = false;
    private final String text;
    private RadioGroup group;

    public RadioButton(int x, int y, String text) {
        super(x, y, 20, 20);
        this.text = text;
    }

    // Register this radio button in a group
    public void setGroup(RadioGroup g) {
        this.group = g;
        g.add(this);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean s) {
        selected = s;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawOval(xPos, yPos, width, height);

        if (selected) {
            g.fillOval(xPos + 5, yPos + 5, 11, 11);
        }

        g.drawString(text, xPos + 30, yPos + 15);
    }

    @Override
    public void onMouseClick(int mx, int my) {
        if (contains(mx, my)) {
            if (group != null) {
                group.select(this);  // notify group
            } else {
                selected = !selected; // fallback if no group
            }
        }
    }

    public String getText(){
        return text;
    }
}
