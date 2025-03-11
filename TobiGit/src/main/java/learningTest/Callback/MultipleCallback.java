package learningTest.Callback;

import learningTest.interfacePrac.BufferedReaderCallback;

import java.io.BufferedReader;
import java.io.IOException;

public class MultipleCallback implements BufferedReaderCallback {
    @Override
    public Integer doSomethingWithReader(BufferedReader br) throws IOException {
        String line = null;
        Integer multiple = 1;
        while((line = br.readLine()) != null) {
            multiple *= Integer.valueOf(line);
        }
        return multiple;
    }
}
