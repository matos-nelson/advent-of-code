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

    public static List<String> numberWords = new ArrayList<>(Arrays.asList(
        "one", "two", "three", "four",
        "five", "six", "seven", "eight", "nine"
    ));

    public static int problem_p1(String [] inputs) {
        System.out.println("Part 1....");

        int count = 0;
        for(String line : inputs) {
            int first = -1;
            int second = first;
            for(int i = 0; i < line.length(); i++) {
                if(Character.isDigit(line.charAt(i))) {
                    if(first == -1) {
                        first = line.charAt(i) - '0';
                        second = first;
                    } else {
                        second = line.charAt(i) - '0';
                    }
                }
            }

            int result = first;
            result *= 10;
            result += second;

            count += result;
        }

        return count;
    }

    public static int problem_p2(String [] inputs) {
        System.out.println("Part 2....");
        int count = 0;
        Pattern pattern = Pattern.compile("(?=(\\d|one|two|three|four|five|six|seven|eight|nine))");
        for(String line : inputs) {
            
            Matcher matcher = pattern.matcher(line);
            int first = -1;
            int second = first;
            while(matcher.find()) {
                String match = matcher.group(1);
                if(match.length() == 1) {
                    if(first == -1) {
                        first = match.charAt(0) - '0';
                        second = first;
                    } else {
                        second = match.charAt(0) - '0';
                    }
                } else {
                    if(first == -1) {
                        first = getValueFromWord(match);
                        second = first;
                    } else {
                        second = getValueFromWord(match);
                    }
                }
            }

            int result = first;
            result *= 10;
            result += second;

            count += result;
        }

        return count;
    }
    
    public static int getValueFromWord(String numWord) {
        if(numWord.equals(numberWords.get(0))) {
            return 1;
        } else if(numWord.equals(numberWords.get(1))) {
            return 2;
        } else if(numWord.equals(numberWords.get(2))) {
            return 3;
        } else if(numWord.equals(numberWords.get(3))) {
            return 4;
        } else if(numWord.equals(numberWords.get(4))) {
            return 5;
        } else if(numWord.equals(numberWords.get(5))) {
            return 6;
        } else if(numWord.equals(numberWords.get(6))) {
            return 7;
        } else if(numWord.equals(numberWords.get(7))) {
            return 8;
        } else if(numWord.equals(numberWords.get(8))) {
            return 9;
        }

        System.out.println("Could not match word " + numWord + " with number!!");
        return -1;
    }
}