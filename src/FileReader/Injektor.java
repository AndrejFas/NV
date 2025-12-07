package FileReader;
import Decoder.ViegenerDecoder;
import Decoder.StreamDecoder;

public class Injektor {

    private static final IReaderService<String> textReader = new TextReader();
    private static final IReaderService<byte[]> binaryReader = new BinaryTextReader();

    public static ViegenerDecoder createViegenerDecoder(){
        return new ViegenerDecoder(textReader);
    }

    public static StreamDecoder createStreamDecoder(){
        return new StreamDecoder(binaryReader);
    }
}
