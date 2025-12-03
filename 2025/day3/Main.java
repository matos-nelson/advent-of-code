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

        long result = 0L;
        for(String input : inputs) {

            int max = 0;
            int index = -1;
            for(int i = 0; i < input.length()-1; i++) {
                int num = input.charAt(i) - '0';
                if(num > max) {
                    index = i;
                    max = num;
                }
            }

            long digit = max * 10L;
            max = 0;
            for(int i = index + 1; i < input.length(); i++) {
                int num = input.charAt(i) - '0';
                max = Math.max(num, max);
            }

            digit += max;
            result += digit;
        }

        return result;
    }

    public static long problem_p2(String [] inputs) {
        System.out.println("Part 2....");

        long result = 0L;
        for(String input : inputs) {

            long digit = 0L;
            int index = -1;
            int count = 0;
            for(int i = 12; i > -1 && count < 12; i--) {

                int start = index + 1;
                int max = 0;
                for(int j = start; j < input.length() && j < input.length() - i + 1; j++) {

                    int num = input.charAt(j) - '0';
                    if(num > max) {
                        max = num;
                        index = j;
                    }
                }

                digit = digit * 10 + max;
                count++;
            }

            result += digit;
        }

        return result;
    }
}