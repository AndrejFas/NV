package FileReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TextReader implements IReaderService<String>{

    @Override
    public String getContent(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        return reader.readLine();
    }
}
