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

    static int min = Integer.MAX_VALUE;
    static void backtrack(List<List<Integer>> buttons, int index, int count, String target, StringBuilder result) {

        if(count >= min) {
            return;
        }

        if(result.toString().equals(target)) {
            min = Math.min(min, count);
            return;
        }

        for(int i = 0; i < buttons.size(); i++) {

            // do
            for(int button : buttons.get(i)) {

                char ch = result.charAt(button);
                if(ch == '.') {
                    result.setCharAt(button, '#');
                } else {
                    result.setCharAt(button, '.');
                }

            }

            backtrack(buttons, i + 1, count + 1, target, result);

            // undo
            for(int button : buttons.get(i)) {

                char ch = result.charAt(button);
                if(ch == '.') {
                    result.setCharAt(button, '#');
                } else {
                    result.setCharAt(button, '.');
                }
            }
        }
    }

    public static long problem_p1(String [] inputs) {
        System.out.println("Part 1....");
        long result = 0L;
        for(String input : inputs) {
            String [] parts = input.split(" ");

            String target = parts[0].replace("[", "").replace("]", "");
            List<List<Integer>> buttonPresses = new ArrayList<>();
            for(int i = 1; i < parts.length - 1; i++) {
                String [] buttons = parts[i].replace("(", "").replace(")", "").split(",");
                List<Integer> sequence = new ArrayList<>();
                for(String button : buttons) {
                    sequence.add(Integer.parseInt(button));
                }

                buttonPresses.add(sequence);
            }

            StringBuilder currentState = new StringBuilder();
            for(int i = 0; i < target.length(); i++) {
                currentState.append(".");
            }

            min = buttonPresses.size() * 2;
            backtrack(buttonPresses, 0, 0, target, currentState);
            result += min;
        }

        return result;
    }

    public static long problem_p2(String [] inputs) {
        System.out.println("Part 2....");
        return 0L;
    }
}