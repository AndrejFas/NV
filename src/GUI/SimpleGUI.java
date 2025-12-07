package GUI;

import Decoder.Decryptor;
import Decoder.EDecoder;
import java.awt.*;
import java.awt.event.*;
public class SimpleGUI extends Canvas{
    private final Pannel pannel = new Pannel();
    public SimpleGUI(Decryptor decryptor) {

        pannel.addComponenet(new Label(20, 90, "Enter name of file:"));

        TextField fileNameTextField = new TextField(20, 100, 150, 20);
        pannel.addComponenet(fileNameTextField);

        RadioGroup group = new RadioGroup();

        RadioButton r1 = new RadioButton(20, 150, "Vieneger");
        RadioButton r2 = new RadioButton(20, 180, "Stream");
        RadioButton r3 = new RadioButton(20, 210, "Cesar");

        r1.setGroup(group);
        r2.setGroup(group);
        r3.setGroup(group);

        pannel.addComponenet(r1);
        pannel.addComponenet(r2);
        pannel.addComponenet(r3);

        TextField loadedText = new TextField(250, 30, 350, 400);
        pannel.addComponenet(loadedText);

        TextField resultText = new TextField(610, 30, 350, 400);
        pannel.addComponenet(resultText);

        Button btn = new Button(20, 30, 100, 30, "Decript");
        btn.setOnClick(() -> {
            try {
                String text = decryptor.getEncryptedText(fileNameTextField.getText(), EDecoder.valueOf(group.getSelected().getText()));
                String decryptedText = decryptor.decodeText(fileNameTextField.getText(), EDecoder.valueOf(group.getSelected().getText()));
                loadedText.setText(text);
                resultText.setText(decryptedText);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        pannel.addComponenet(btn);

        Frame frame = new Frame("Simple GUI");

        frame.add(this);
        frame.setSize(1000, 500);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        this.requestFocus();
    }

    @Override
    public void paint(Graphics g) {
        pannel.draw(g);
    }
}
