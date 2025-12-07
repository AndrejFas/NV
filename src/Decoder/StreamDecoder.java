package Decoder;

import FileReader.IReaderService;

public class StreamDecoder implements IDecoder<byte[]> {

    IReaderService<byte[]> readerService;
    public StreamDecoder(IReaderService<byte[]> service){
        readerService = service;
    }
    @Override
    public byte[] decodeText(String filename) {
        return null;
    }

    @Override
    public String getInputText(String filename) throws Exception {
        return null;
    }
}
