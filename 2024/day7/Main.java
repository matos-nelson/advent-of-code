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

    public static void permute(int n, int index, StringBuilder sb, List<String> p) {

        if (index == n) {
            p.add(sb.toString());
            return;
        }

        sb.setCharAt(index, '+');
        permute(n, index + 1, sb, p);

        sb.setCharAt(index, '*');
        permute(n, index + 1, sb, p);

        // part 2 only
        sb.setCharAt(index, '|');
        permute(n, index + 1, sb, p);
    }

    public static boolean solve(List<String> operators, List<Long> operands, long solution) {

        for(String operator : operators) {

            long result = operands.get(0);
            int index = 0;
            for(int i = 1; i < operands.size(); i++) {

                char op = operator.charAt(index++);
                if(op == '*') {
                    result *= operands.get(i);
                } else if(op == '+') {
                    result += operands.get(i);
                } else if(op == '|') { // part 2 only
                    result = Long.parseLong(result + "" + operands.get(i));
                }
            }

            if(result == solution) {
                return true;
            }
        }

        return false;
    }

    public static long problem_p1(String [] inputs) {
        System.out.println("Part 1....");

        long result = 0;
        for(String input : inputs) {

            String [] equations = input.split(":");
            long solution = Long.parseLong(equations[0]);
            List<Long> operands = new ArrayList<>();

            for(String operand : equations[1].trim().split(" ")) {
                operands.add(Long.parseLong(operand));
            }

            List<String> operators = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            int n = operands.size() - 1;
            for(int i = 0; i < n; i++) {
                sb.append('_');
            }

            permute(n, 0, sb, operators);
            boolean isAbleToBeSolved = solve(operators, operands, solution);
            if(isAbleToBeSolved) {
                result += solution;
            }
        }

        return result;
    }

    public static long problem_p2(String [] inputs) {
        System.out.println("Part 2....");
        return 0L;
    }
}