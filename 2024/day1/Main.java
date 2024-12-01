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

    public static long problem_p1(String [] inputs) {
        System.out.println("Part 1....");

        int n = inputs.length;
        long [] list1 = new long[n];
        long [] list2 = new long[n];

        for(int i = 0; i < n; i++) {
            String [] digits = inputs[i].trim().replaceAll(" +", " ").split(" ");
            list1[i] = Long.parseLong(digits[0]);
            list2[i] = Long.parseLong(digits[1]);
        }


        Arrays.sort(list1);
        Arrays.sort(list2);

        long result = 0;
        for(int i = 0; i < n; i++) {
            result += Math.abs(list1[i] - list2[i]);
        }

        return result;
    }

    public static long problem_p2(String [] inputs) {
        System.out.println("Part 2....");
        int n = inputs.length;
        long [] list1 = new long[n];
        long [] list2 = new long[n];

        for(int i = 0; i < n; i++) {
            String [] digits = inputs[i].trim().replaceAll(" +", " ").split(" ");
            list1[i] = Long.parseLong(digits[0]);
            list2[i] = Long.parseLong(digits[1]);
        }

        Map<Long, Long> map = new HashMap<>();
        for(int i = 0; i < n; i++) {
            map.put(list2[i], map.getOrDefault(list2[i], 0L) + 1);
        }

        long result = 0;
        for(int i = 0; i < n; i++) {
            long similarity = map.getOrDefault(list1[i], 0L);
            result += similarity * list1[i];
        }

        return result;
    }
}