package FileReader;
import java.io.IOException;

public interface IReaderService<T> {
    public T getContent(String fileName) throws IOException;

}
