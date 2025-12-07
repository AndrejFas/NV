package GUI;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Pannel extends Component{

    private final List<IComponent> components = new ArrayList<>();
    public Pannel() {
        super(0, 0, 0, 0);
    }

    @Override
    public void draw(Graphics g) {
        for (IComponent c : components) {
            c.draw(g);
        }
    }

    @Override
    public void addComponenet(IComponent c) {
        components.add(c);
    }

    public Boolean removeComponent(IComponent c) {
        return components.remove(c);
    }

    public List<IComponent> getAllComponenets(){
        return components;
    }
}
