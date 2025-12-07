package FileReader;

import java.io.IOException;

public class BinaryTextReader implements IReaderService<byte[]>{

    @Override
    public byte[] getContent(String fileName) throws IOException {
        return new byte[0];
    }
}
