package Decoder;

import FileReader.Injektor;
import GUI.SimpleGUI;
import Utils.Utils;

public class Decryptor {
    public Decryptor() throws Exception {

        SimpleGUI gui = new SimpleGUI(this);

    }

    public String decodeText(String fileName, EDecoder eDecoder) throws Exception {
        switch(eDecoder){
            case Vieneger:
                IDecoder<String> decoderV = Injektor.createViegenerDecoder();
                return decoderV.decodeText(fileName);
            case Stream:
                IDecoder<byte[]> decoderS = Injektor.createStreamDecoder();
                byte[] byteText = decoderS.decodeText(fileName);
                return Utils.byteToStr(byteText);
            default:
                throw new Exception("Nebol vybrany desifrator!");
        }
    }
    public String getEncryptedText(String fileName, EDecoder eDecoder)
            throws Exception {
        switch(eDecoder){
            case Vieneger:
                IDecoder<String> decoderV = Injektor.createViegenerDecoder();
                return decoderV.getInputText(fileName);
            case Stream:
                IDecoder<byte[]> decoderS = Injektor.createStreamDecoder();
                return decoderS.getInputText(fileName);
            default:
                throw new Exception("Nebol vybrany desifrator!");
        }
    }
}
