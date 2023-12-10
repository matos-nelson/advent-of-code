import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.*;

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
        //System.out.println(problem_p1(arr));
        System.out.println(problem_p2(arr));
    }

    public static int problem_p1(String [] inputs) {
        System.out.println("Part 1....");
        String directions = inputs[0];

        HashMap<String, List<String>> map = new HashMap<>();
        for(int i = 2; i < inputs.length; i++) {
            String [] route = inputs[i].split(" = ");
            String position = route[0];
            String [] locations = route[1].replaceAll("\\(", "").replaceAll("\\)", "").split(", ");

            map.put(position, Arrays.asList(locations));
        }

        String position = "AAA";
        int index = 0;
        int count = 0;

        while(!position.equals("ZZZ")) {
            int direction = directions.charAt(index) == 'L' ? 0 : 1;
            position = map.get(position).get(direction);
            //System.out.println("D: " + directions.charAt(index) + " P: " + position + " I: " + index);
            index++;
            count++;
            if(index == directions.length()) {
                index = 0;
            }
        }

        return count;
    }

    public static long problem_p2(String [] inputs) {
        System.out.println("Part 2....");
        String directions = inputs[0];

        HashMap<String, List<String>> map = new HashMap<>();
        for(int i = 2; i < inputs.length; i++) {
            String [] route = inputs[i].split(" = ");
            String position = route[0];
            String [] locations = route[1].replaceAll("\\(", "").replaceAll("\\)", "").split(", ");

            map.put(position, Arrays.asList(locations));
        }

        List<String> positions = map.keySet()
        .stream()
        .filter(x -> x.charAt(x.length()-1) == 'A')
        .collect(Collectors.toCollection(ArrayList::new));

        List<Long> results = new ArrayList<Long>();
        for(int i = 0; i < positions.size(); i++) {
            int index = 0;
            long count = 0;
            String position = positions.get(i);
            while(position.charAt(position.length() - 1) != 'Z') {
                int direction = directions.charAt(index) == 'L' ? 0 : 1;
                position = map.get(position).get(direction);
                //System.out.println("D: " + directions.charAt(index) + " P: " + position + " I: " + index);
                index++;
                count++;
                if(index == directions.length()) {
                    index = 0;
                }
            }
            results.add(count);
        }

        //System.out.println(results);
        return lcm(results);
    }

    public static long gcd(long a, long b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    public static long lcm(List<Long> arr) {
        long lcm = arr.get(0);
        for (int i = 1; i < arr.size(); i++) {
            long currentNumber = arr.get(i);
            lcm = (lcm * currentNumber) / gcd(lcm, currentNumber);
        }
        return lcm;
    }
}