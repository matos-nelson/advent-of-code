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
            reader = new BufferedReader(new FileReader("test_input.txt"));
            //reader = new BufferedReader(new FileReader("input.txt"));
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

    public static int maxDepthP1 = 25;
    public static int maxDepthP2 = 75;
    public static long compute(String stone, int depth, int maxDepth) {

        if(depth == maxDepth) {
            return 1;
        }

        if(stone.equals("0")) {
            return compute("1", depth + 1, maxDepth);
        }

        if(stone.length() % 2 == 0) {
            int length = stone.length();
            String s1 = stone.substring(0, length/2);
            String s2 = stone.substring(length/2, length);

            s1 = Long.parseLong(s1) + "";
            s2 = Long.parseLong(s2) + "";

            return compute(s1, depth + 1, maxDepth) + compute(s2, depth + 1, maxDepth);
        }

        String newStone = Long.parseLong(stone) * 2024 + "";
        return compute(newStone, depth + 1, maxDepth);
    }

    public static long problem_p1(String [] inputs) {
        System.out.println("Part 1....");

        String [] stones = inputs[0].trim().split(" ");
        long totalStones = 0;
        for(String stone : stones) {
            long result = compute(stone, 0, maxDepthP1);
            if(result == 0) {
                totalStones++;
            } else {
                totalStones += result;
            }
        }

        return totalStones;
    }

    public static long problem_p2(String [] inputs) {
        System.out.println("Part 2....");
        return 0L;
    }
}