package Decoder;

public interface IDecoder<T> {
    public T decodeText(String filename) throws Exception;
    public String getInputText(String filename) throws Exception;
}
