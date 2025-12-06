import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.*;

public class Main {
    public static void main (String [] args) {
        BufferedReader reader;
        List<String> lines = new ArrayList<String>();

        try {
            //reader = new BufferedReader(new FileReader("test_input.txt"));
            reader = new BufferedReader(new FileReader("input.txt"));
            String line = reader.readLine();

            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }

        String[] arr = new String[lines.size()];
        arr = lines.toArray(arr);

        long startTime = System.nanoTime();
        System.out.println(problem_p1(arr));
        long endTime = System.nanoTime();

        System.out.println("P1 Time (s): " + TimeUnit.NANOSECONDS.toSeconds(endTime - startTime) + "\n");

        startTime = System.nanoTime();
        System.out.println(problem_p2(arr));
        endTime = System.nanoTime();

        System.out.println("P2 Time (s): " + TimeUnit.NANOSECONDS.toSeconds(endTime - startTime));
    }

    public static long problem_p1(String [] inputs) {
        System.out.println("Part 1....");

        List<String[]> problems = new ArrayList<>();
        for(String input : inputs) {
            String i = input.replaceAll(" +", " ").trim();
            problems.add(i.split(" "));
        }

        int n = problems.get(0).length;
        int m = problems.size();

        long result = 0L;
        for(int i = 0; i < n; i++) {

            long mResult = 1L;
            long aResult = 0L;

            for(int j = 0; j < m-1; j++) {

                long num = Long.parseLong(problems.get(j)[i]);
                mResult *= num;
                aResult += num;
            }

            String operator = problems.get(m-1)[i];
            if(operator.equals("*")) {
                result += mResult;
            } else {
                result += aResult;
            }
        }

        return result;
    }

    static class Operator {
        public char op;
        public int length;
        public Operator(char op, int length) {
            this.op = op;
            this.length = length;
        }

        public String toString() {
            return "[" + op + "," + this.length + "]";
        }
    }

    public static long problem_p2(String [] inputs) {
        System.out.println("Part 2....");

        List<Operator> operators = new ArrayList<>();
        String operatorLine = inputs[inputs.length-1];
        int index = 0;
        while(index < operatorLine.length()) {

            char op = operatorLine.charAt(index++);
            int length = 0;
            while(index < operatorLine.length() && operatorLine.charAt(index) == ' ') {
                length++;
                index++;
            }

            operators.add(new Operator(op, length));
        }

        operators.get(operators.size()-1).length++;

        int lineIndex = 0;
        long result = 0L;
        for(Operator operator : operators) {
            List<String> digits = new ArrayList<>();
            for(int i = 0; i < inputs.length - 1; i++) {
                digits.add(inputs[i].substring(lineIndex, Math.min(lineIndex + operator.length+1, inputs[i].length())));
            }

            long opResult = operator.op == '*' ? 1L : 0L;
            for(int i = 0; i < operator.length; i++) {

                long num = 0;
                for(int j = 0; j < digits.size(); j++) {

                    char ch = digits.get(j).charAt(i);
                    if(Character.isDigit(ch)) {
                        num = num * 10 + ch - '0';
                    }
                }

                if(operator.op == '*') {
                    opResult *= num;
                } else {
                    opResult += num;
                }
            }

            result += opResult;
            lineIndex += operator.length+1;
        }

        return result;
    }
}