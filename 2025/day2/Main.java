import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.*;
import java.util.regex.*;

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

        String [] ranges = inputs[0].split(",");
        long result = 0L;
        for(String range : ranges) {
            int index = range.indexOf('-');
            String start = range.substring(0, index);
            String end = range.substring(index+1);

            long startRange = Long.parseLong(start);
            long endRange = Long.parseLong(end);

            for(Long i = startRange; i <= endRange; i++) {

                String r = i.toString();
                for(int j = 0; j < r.length() / 2; j++) {

                    String left = r.substring(0, j + 1);
                    String right = r.substring(j+1, r.length());
                    if(right.charAt(0) != '0' && left.equals(right)) {
                        long value = Long.parseLong(left+right);
                        result += value;
                    }
                }
            }
        }

        return result;
    }

    public static long problem_p2(String [] inputs) {
        System.out.println("Part 2....");

        String [] ranges = inputs[0].split(",");
        long result = 0L;
        for(String range : ranges) {
            int index = range.indexOf('-');
            String start = range.substring(0, index);
            String end = range.substring(index+1);

            long startRange = Long.parseLong(start);
            long endRange = Long.parseLong(end);

            for(Long i = startRange; i <= endRange; i++) {

                String r = i.toString();
                for(int j = 0; j < r.length() / 2; j++) {

                    String left = r.substring(0, j + 1);
                    String right = r.substring(j+1, r.length());
                    if(right.charAt(0) == '0') {
                        continue;
                    }

                    int count = 1;
                    String temp = new String(right);
                    while (temp.startsWith(left)) {
                        temp = temp.substring(left.length());
                        count++;
                    }

                    if(count >= 2 && temp.equals("")) {
                        long value = Long.parseLong(left + right);
                        result += value;
                        break;
                    }
                }
            }
        }

        return result;
    }
}