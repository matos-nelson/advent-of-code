import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main (String [] args) {
        BufferedReader reader;
        List<String> lines = new ArrayList<String>();

        try {
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
        System.out.println(problem_p1(arr));
        System.out.println(problem_p2(arr));
    }

    public static int problem_p1(String [] inputs) {
        System.out.println("Part 1....");
        String [] times = inputs[0].replaceAll("\s+", " ").split(": ")[1].split(" ");
        String [] distances = inputs[1].replaceAll("\s+", " ").split(": ")[1].split(" ");

        int total = 1;
        for(int i = 0; i < times.length; i++) {
            int time = Integer.parseInt(times[i]);
            int distance = Integer.parseInt(distances[i]);

            int count = 0;
            for(int j = 1; j <= time - 1; j++) {
                int result = (time - j) * j;
                //System.out.println("Time: " + time + " Distance: " + distance + " Hold: " + j + " Result: " + result);
                if(result > distance) {
                    count++;
                }
            }
            //System.out.println(count);
            total = count != 0 ? total * count : total;
        }

        return total;
    }

    public static int problem_p2(String [] inputs) {
        System.out.println("Part 2....");
        long time = Long.parseLong(inputs[0].replaceAll("\s+", "").split(":")[1]);
        long distance = Long.parseLong(inputs[1].replaceAll("\s+", "").split(":")[1]);

        int total = 1;
        int count = 0;
        for(int i = 1; i <= time - 1; i++) {
            long result = (time - i) * i;
            if(result > distance) {
                count++;
            }
        }

        return count;
    }
}