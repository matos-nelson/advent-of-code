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

    static long bfs(List<List<Integer>> buttonPresses, List<Integer> targetVoltages) {

        LinkedList<List<Integer>> queue = new LinkedList<>();
        List<Integer> start = new ArrayList<>();
        for(int i = 0; i < targetVoltages.size(); i++) {
            start.add(0);
        }

        queue.add(start);
        long min = Long.MAX_VALUE;
        long count = 0;
        while(!queue.isEmpty() && count < min) {

            count++;
            int size = queue.size();
            for(int i = 0; i < size; i++) {

                List<Integer> sequence = queue.poll();
                boolean isTargetReached = true;
                for(int j = 0; j < sequence.size() && isTargetReached; j++) {
                    if(sequence.get(j) != targetVoltages.get(j)) {
                        isTargetReached = false;
                        break;
                    }
                }

                if(isTargetReached) {
                    min = Math.min(min, count);
                    continue;
                }

                for(List<Integer> buttons : buttonPresses) {

                    List<Integer> current = new ArrayList<>(sequence);
                    boolean isValid = true;
                    for(Integer button : buttons) {

                        if(current.get(button) + 1 > targetVoltages.get(button)) {
                            isValid = false;
                            break;
                        }

                        current.set(button, current.get(button) + 1);
                    }

                    if(isValid) {
                        queue.add(current);
                    }
                }
            }
        }

        return min - 1;
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
        long result = 0L;
        for(String input : inputs) {
            String [] parts = input.split(" ");

            String [] target = parts[parts.length - 1].replace("{", "").replace("}", "").split(",");
            List<Integer> voltages = new ArrayList<>();
            for(String targetVoltage : target) {
                voltages.add(Integer.parseInt(targetVoltage));
            }

            List<List<Integer>> buttonPresses = new ArrayList<>();
            for(int i = 1; i < parts.length - 1; i++) {
                String [] buttons = parts[i].replace("(", "").replace(")", "").split(",");
                List<Integer> sequence = new ArrayList<>();
                for(String button : buttons) {
                    sequence.add(Integer.parseInt(button));
                }

                buttonPresses.add(sequence);
            }

            long minPresses = bfs(buttonPresses, voltages);
            result += minPresses;
        }

        return result;
    }
}