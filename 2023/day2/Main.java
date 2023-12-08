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
        int total = 0;
        for(String line : inputs) {
            final int maxRed = 12;
            final int maxGreen = 13;
            final int maxBlue = 14;
            String[] game = line.split(": ");
            int gameNum = Integer.parseInt(game[0].replaceAll("[^0-9]", ""));

            String [] sets = game[1].split("; ");
            boolean isGamePossible = true;
            for(String set : sets) {
                String [] marbles = set.split(", ");
                for(String marble : marbles) {
                    String [] str = marble.split(" ");

                    int marbleCount = Integer.parseInt(str[0]);
                    if("blue".equals(str[1]) && marbleCount > maxBlue) {
                        isGamePossible = false;
                        break;
                    } else if("red".equals(str[1]) && marbleCount > maxRed) {
                        isGamePossible = false;
                        break;
                    } else if("green".equals(str[1]) && marbleCount > maxGreen) {
                        isGamePossible = false;
                        break;
                    }
                }
            }

            if(isGamePossible) {
                total += gameNum;
            }
        }

        return total;
    }

    public static int problem_p2(String [] inputs) {
        System.out.println("Part 2....");
        int total = 0;
        for(String line : inputs) {
            int maxRed = 0;
            int maxGreen = 0;
            int maxBlue = 0;
            String[] game = line.split(": ");
            int gameNum = Integer.parseInt(game[0].replaceAll("[^0-9]", ""));

            String [] sets = game[1].split("; ");
            for(String set : sets) {
                String [] marbles = set.split(", ");
                for(String marble : marbles) {
                    String [] str = marble.split(" ");

                    int marbleCount = Integer.parseInt(str[0]);
                    if("blue".equals(str[1]) && marbleCount > maxBlue) {
                        maxBlue = marbleCount;
                    } else if("red".equals(str[1]) && marbleCount > maxRed) {
                        maxRed = marbleCount;
                    } else if("green".equals(str[1]) && marbleCount > maxGreen) {
                        maxGreen = marbleCount;
                    }
                }
            }
            total += (maxRed * maxBlue * maxGreen);
        }

        return total;
    }
}