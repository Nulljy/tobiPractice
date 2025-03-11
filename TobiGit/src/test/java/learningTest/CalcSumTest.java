package learningTest;

import learningTest.Callback.MultipleCallback;
import learningTest.interfacePrac.BufferedReaderCallback;
import learningTest.template.Calculator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CalcSumTest {
    Calculator calculator;
    BufferedReaderCallback bufferedReaderCallback;
    String fileName;
    String filePath;

    @BeforeEach
    public void initTest() {
        this.calculator = new Calculator();
        this.bufferedReaderCallback = new MultipleCallback();
        this.fileName = "/numbers.txt";
        this.filePath = calculator.getClass().getResource(fileName).getPath();
    }

    @Test
    public void sumOfNumbers() throws IOException {
        Calculator calculator = new Calculator();
        int sum = calculator.calcSum(getClass().getResource(filePath).getPath());
        Assertions.assertThat(sum).isEqualTo(10);
    }

    // br을 제공하고 Int를 가져오는 템플릿 생성
    // 매개인자는 BufferedReader와 filePath
//    @Test
//    public void fileReadTemplate() throws IOException{
//        try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
//            int result = bufferedReaderCallback.doSomethingWithReader(br);
//            Assertions.assertThat(result).isEqualTo(23);
//        } catch (IOException e) {
//            System.out.println("파일 읽기 중 예외 발생: " + e.getMessage());
//            throw e;
//        }
//    }

    @Test
    public void calcSum() throws IOException{
        Assertions.assertThat(calculator.calcSum(filePath)).isEqualTo(10);
    }

    @Test
    public void calcMultiple() throws IOException{
        Assertions.assertThat(calculator.calcMultiple(filePath)).isEqualTo(10);
    }


}

// 여기는 진짜 테스트로 add, multiple 같은 메서드만 남기고
// Calculator 클래스에 calcSum, calcMultiple 같은 메서드 만들기