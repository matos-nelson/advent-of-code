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
        for(int i = 0; i < inputs.length; i++) {

            int wins = getWins(inputs[i]);
            //System.out.println("Wins: " + wins);
            total += wins != -1 ? Math.pow(2,wins) : 0;
        }

        return total;
    }

    public static int problem_p2(String [] inputs) {
        System.out.println("Part 2....");
        int [] cards = new int[inputs.length + 1];
        Arrays.fill(cards, 1);
        cards[0] = 0;
        for(int i = 0; i < inputs.length; i++) {
            int gameCardNum = Integer.parseInt(inputs[i].split(":")[0].replaceAll("\\D+",""));

            int wins = getWins(inputs[i]) + 1;
            for(int j = gameCardNum + 1; j <= gameCardNum + wins; j++) {
                cards[j] += cards[gameCardNum];
            }
            //System.out.println("Game: " + gameCardNum + " Win: " + wins);
            //System.out.println("Wins: " + wins);
        }

        //System.out.println(Arrays.toString(cards));
        return Arrays.stream(cards).sum();
    }

    public static int getWins(String gameCard) {
        String [] numbers = gameCard.split(": ")[1].split(" \\| ");

        String [] playerNumbers = numbers[0].replaceAll("\s+", " ").split(" ");
        String [] winningNumbers = numbers[1].replaceAll("\s+", " ").split(" ");

        int wins = -1;
        for(int j = 0; j < playerNumbers.length; j++) {
            for(int k = 0; k < winningNumbers.length; k++) {
                if(playerNumbers[j] != "" && playerNumbers[j].equals(winningNumbers[k])) {
                    //System.out.println(playerNumbers[j] + " " + winningNumbers[k]);
                    wins++;
                    break;
                }
            }
        }

        return wins;
    }
}