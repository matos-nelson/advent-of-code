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
        long total = 0;
        for(int i = 0; i < inputs.length; i++) {

            List<Long> baseNumbers = Arrays.stream(inputs[i].split(" ")).mapToLong(Long::parseLong).boxed().toList();
            List<List<Long>> pyramid = new ArrayList<>();
            pyramid.add(baseNumbers);

            for(int j = 0; j < pyramid.size(); j++) {
                List<Long> results = new ArrayList<>();
                for(int k = 0; k < pyramid.get(j).size() - 1; k++) {
                    results.add(pyramid.get(j).get(k+1) - pyramid.get(j).get(k));
                }
                boolean isSaturated = results.stream().allMatch(x -> x == 0);
                if(!isSaturated) {
                    pyramid.add(results);
                }
            }

            //System.out.println(pyramid);
            for(int j = pyramid.size() - 1; j >= 0; j--) {
                total += pyramid.get(j).get(pyramid.get(j).size()-1);
            }
        }

        return total;
    }

    public static long problem_p2(String [] inputs) {
        System.out.println("Part 2....");
        long total = 0;
        for(int i = 0; i < inputs.length; i++) {

            List<Long> baseNumbers = Arrays.stream(inputs[i].split(" ")).mapToLong(Long::parseLong).boxed().toList();
            List<List<Long>> pyramid = new ArrayList<>();
            pyramid.add(baseNumbers);

            for(int j = 0; j < pyramid.size(); j++) {
                List<Long> results = new ArrayList<>();
                for(int k = pyramid.get(j).size() - 1; k > 0; k--) {
                    results.add(pyramid.get(j).get(k) - pyramid.get(j).get(k-1));
                }
                boolean isSaturated = results.stream().allMatch(x -> x == 0);
                if(!isSaturated) {
                    Collections.reverse(results);
                    pyramid.add(results);
                }
            }

            //System.out.println(pyramid);
            long sum = 0;
            for(int j = pyramid.size() - 1; j >= 0; j--) {
                sum = pyramid.get(j).get(0) - sum;
            }
            total += sum;
        }

        return total;
    }
}