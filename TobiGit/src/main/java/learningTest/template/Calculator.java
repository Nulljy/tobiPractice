package learningTest.template;

import learningTest.interfacePrac.BufferedReaderCallback;
import learningTest.interfacePrac.LineCallback;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {
    public Integer calcSum(String filepath) throws IOException {
        BufferedReaderCallback someCallback = new BufferedReaderCallback() {
            @Override
            public Integer doSomethingWithReader(BufferedReader br) throws IOException {
                String line = null;
                Integer sum = 0;
                while((line = br.readLine()) != null) sum += Integer.valueOf(line);
                return sum;
            }
        };
        return fileReadTemplate(filepath, someCallback);
    }

    public Integer calcMultiple(String filepath) throws IOException {
        BufferedReaderCallback someCallback = new BufferedReaderCallback() {
            @Override
            public Integer doSomethingWithReader(BufferedReader br) throws IOException {
                String line = null;
                Integer multiple = 1;
                while((line = br.readLine()) != null) multiple *= Integer.valueOf(line);
                return multiple;
            }
        };
        return fileReadTemplate(filepath, someCallback);
    }

    public Integer calcSum2(String filepath) throws IOException {
        LineCallback<Integer> sumCallback = new LineCallback<Integer>() {
            @Override
            public Integer doSomethingWithLine(String line, Integer res) throws IOException {
                return res + Integer.valueOf(line);
            }
        };
        return fileReadTemplate2(filepath, sumCallback, 0);
    }

    public Integer calcMultiple2(String filepath) throws IOException {
        LineCallback<Integer> multipleCallback = new LineCallback<Integer>() {
            @Override
            public Integer doSomethingWithLine(String line, Integer res) throws IOException {
                return res * Integer.valueOf(line);
            }
        };
        return fileReadTemplate2(filepath, multipleCallback, 1);
    }

    public <T> T fileReadTemplate2(String filePath, LineCallback<T> lineCallback, T initVal) throws IOException {
        // 여기서 doSomethingWithLine에 br도 주입해줄것
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            T res = initVal;
            String line = null;
            while ((line = br.readLine()) != null) {
                res = lineCallback.doSomethingWithLine(line, res);
            }
            return res;
        } catch (IOException e) {
            throw e;
        }
    }

    public Integer fileReadTemplate(String filePath, BufferedReaderCallback bufferedReaderCallback) throws IOException {
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            int result = bufferedReaderCallback.doSomethingWithReader(br);
            return result;
        } catch (IOException e) {
            System.out.println("파일 읽기 중 예외 발생 " + e.getMessage());
            throw e;
        }
    }
}

// JdbcTemplate 보기 259페이지