import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

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

        boolean isRange = true;
        long result = 0L;
        List<List<Long>> ranges = new ArrayList<>();
        for(String input : inputs) {
            if(input.isEmpty()) {
                isRange = false;
                continue;
            }

            if(isRange) {
                String [] r = input.split("-");
                long start = Long.parseLong(r[0]);
                long end = Long.parseLong(r[1]);
                ranges.add(List.of(start, end));

                continue;
            }

            Long ingredient = Long.parseLong(input);
            for(List<Long> range : ranges) {
                if(ingredient >= range.get(0) && ingredient <= range.get(1)) {
                    result++;
                    break;
                }
            }
        }

        return result;
    }

    public static long problem_p2(String [] inputs) {
        System.out.println("Part 2....");
        List<List<Long>> intervals = new ArrayList<>();
        for(String input : inputs) {
            if(input.isEmpty()) {
                break;
            }

            String [] r = input.split("-");
            long start = Long.parseLong(r[0]);
            long end = Long.parseLong(r[1]);
            intervals.add(List.of(start, end));
        }

        intervals.sort(Comparator.comparing((List<Long> l) -> l.get(0))
        .thenComparing(l -> l.get(1)));

        long result = 0L;
        long currentStart = intervals.get(0).get(0);
        long currentEnd = intervals.get(0).get(1);

        for (int i = 1; i < intervals.size(); i++) {
            long start = intervals.get(i).get(0);
            long end = intervals.get(i).get(1);

            if (start <= currentEnd) {
                currentEnd = Math.max(currentEnd, end);
            } else {
                result += (currentEnd - currentStart + 1);
                currentStart = start;
                currentEnd = end;
            }
        }

        return result + currentEnd - currentStart + 1;
    }
}