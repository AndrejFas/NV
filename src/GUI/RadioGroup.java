package GUI;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RadioGroup extends Component{

    private final List<RadioButton> buttons = new ArrayList<>();
    private RadioButton selected = null;

    public RadioGroup() {
        super(0, 0, 0, 0);
    }

    public void add(RadioButton b) {
        buttons.add(b);
    }

    public void select(RadioButton b) {
        // Deselect previously selected button
        if (selected != null) {
            selected.setSelected(false);
        }

        // Select new button
        selected = b;
        b.setSelected(true);
    }

    public RadioButton getSelected() {
        return selected;
    }

    @Override
    public void draw(Graphics g) {
        for (IComponent c : buttons) {
            c.draw(g);
        }
    }

}
