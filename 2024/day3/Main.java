import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
        System.out.println(problem_p1(arr));
        System.out.println(problem_p2(arr));
    }

    public static long problem_p1(String [] inputs) {
        System.out.println("Part 1....");

        List<String> muls = new ArrayList<>();
        String regex = "mul\\(\\d+,\\d+\\)";
        Pattern pattern = Pattern.compile(regex);
        for(String input : inputs) {
            //Matching the compiled pattern in the String
            Matcher matcher = pattern.matcher(input);
            while (matcher.find()) {
                muls.add(matcher.group());
           }
        }

        System.out.println(muls);
        regex = "\\d+";
        pattern = Pattern.compile(regex);
        long result = 0;
        for(String mul : muls) {
            Matcher matcher = pattern.matcher(mul);
            List<Long> results = new ArrayList<>();
            while (matcher.find()) {
                results.add(Long.parseLong(matcher.group()));
            }

            if(results.size() != 2) {
                throw new RuntimeException();
            }

            result += results.get(0) * results.get(1);
        }

        return result;
    }

    public static long problem_p2(String [] inputs) {
        System.out.println("Part 2....");

        List<String> muls = new ArrayList<>();
        String regex = "(do\\(\\))|(don't\\(\\))|mul\\(\\d+,\\d+\\)";
        Pattern pattern = Pattern.compile(regex);
        for(String input : inputs) {
            //Matching the compiled pattern in the String
            Matcher matcher = pattern.matcher(input);
            while (matcher.find()) {
                muls.add(matcher.group());
           }
        }

        System.out.println(muls);

        long result = 0;
        regex = "\\d+";
        pattern = Pattern.compile(regex);
        boolean isEnabled = true;
        for(String mul : muls) {

            if(mul.equals("do()")) {
                isEnabled = true;
            } else if(mul.equals("don't()")) {
                isEnabled = false;
            } else if(isEnabled) {

                Matcher matcher = pattern.matcher(mul);
                List<Long> results = new ArrayList<>();
               while (matcher.find()) {
                    results.add(Long.parseLong(matcher.group()));
                }

                if(results.size() != 2) {
                    throw new RuntimeException();
                }

                result += results.get(0) * results.get(1);
            }

        }

        return result;
    }
}