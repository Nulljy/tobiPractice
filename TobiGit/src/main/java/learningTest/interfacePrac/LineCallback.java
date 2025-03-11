package learningTest.interfacePrac;

import java.io.BufferedReader;
import java.io.IOException;

public interface LineCallback<T> {
    T doSomethingWithLine(String line, T prev) throws IOException;
}
