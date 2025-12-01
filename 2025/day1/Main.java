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
        int dial = 50;
        for(String input : inputs) {

            char direction = input.charAt(0);
            int turns = Integer.parseInt(input.substring(1));

            if(direction == 'L') {
                for(int i = 0; i < turns; i++) {
                    if(--dial == -1) {
                        dial = 99;
                    }
                }
                
            }

            if(direction == 'R') {
                for(int i = 0; i < turns; i++) {
                    if(++dial == 100) {
                        dial = 0;
                    }
                }
            }

            if(dial == 0) {
                result++;
            }
        }

        return result;
    }

    public static long problem_p2(String [] inputs) {
        System.out.println("Part 2....");
        long result = 0L;
        int dial = 50;
        for(String input : inputs) {

            char direction = input.charAt(0);
            int turns = Integer.parseInt(input.substring(1));

            if(direction == 'L') {
                for(int i = 0; i < turns; i++) {
                    dial--;
                    if(dial == -1) {
                        dial = 99;
                    } else if(dial == 0) {
                        result++;
                    }
                }
                
            }

            if(direction == 'R') {
                for(int i = 0; i < turns; i++) {
                    if(++dial == 100) {
                        dial = 0;
                    }

                    if(dial == 0) {
                        result++;
                    }
                }
            }
        }

        return result;
    }
}