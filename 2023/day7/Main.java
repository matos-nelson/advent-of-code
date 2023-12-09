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

        int [][] ranks = new int[inputs.length][2];
        for(int i = 0; i < inputs.length; i++) {
            String [] hand = inputs[i].split(" ");
            String cards = hand[0];

            char[] sortedCards = cards.toCharArray();
            Arrays.sort(sortedCards);

            StringBuilder sb = new StringBuilder();

            for(int j = 0; j < sortedCards.length; j++) {
                if(j < sortedCards.length - 1 && sortedCards[j] == sortedCards[j+1]) {
                    sb.append(sortedCards[j]);
                } else if(j != 0 && sortedCards[j] == sortedCards[j-1]) {
                    sb.append(sortedCards[j]);
                }
            }

            // 1 high card
            // 2 a pair
            // 3 two pair
            // 4 three of a kind
            // 5 full house
            // 6 four of a kind
            // 7 five of a kind
            int rank = -1;
            if(sb.length() == 0) {
                rank = 1;
            } else if (sb.length() == 2) {
                rank = 2;
            } else if (sb.length() == 4 && sb.charAt(0) != sb.charAt(sb.length() - 1)) {
                rank = 3;
            } else if(sb.length() == 3) {
                rank = 4;
            } else if(sb.length() == 5 && sb.charAt(0) != sb.charAt(sb.length() - 1)) {
                rank = 5;
            } else if(sb.length() == 4 && sb.charAt(0) == sb.charAt(sb.length() - 1)) {
                rank = 6;
            } else if(sb.length() == 5) {
                rank = 7;
            } else {
                System.out.println("Couldnt map...");
            }

            ranks[i] = new int[2];
            ranks[i][0] = rank;
            ranks[i][1] = i;
        }

        Arrays.sort(ranks, (a,b) -> {
            int value = Integer.compare(a[0], b[0]);
            if(value == 0) {
                for(int i = 0; i < inputs[a[1]].length(); i++) {
                    int v1 = getCardValue(inputs[a[1]].charAt(i));
                    int v2 = getCardValue(inputs[b[1]].charAt(i));
                    if(v1 > v2) {
                        value = 1;
                        break;
                    } else if(v1 == v2) {
                        value = 0;
                    } else {
                        value = -1;
                        break;
                    }
                }
            }

            return value;
        });
        //System.out.println(Arrays.deepToString(ranks));

        // 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 (15) -> type (16) -> rank
        /**
            if no other char exists then remove
                AAAAA -> AAAAA
                AA8AA -> AAAA
                23332 -> 22333
                TTT98 -> TTT
                23432 -> 2233
                A23A4 -> AA
        */

        // AAAAA
        int total = 0;
        for(int i = 0; i < ranks.length; i++) {
            total += (i + 1) * Integer.parseInt(inputs[ranks[i][1]].split(" ")[1].trim());
        }

        return total;
    }

    public static int getCardValue(char ch) {
        if(Character.isDigit(ch)) {
            return ch - '0';
        } else if (ch == 'T') {
            return 10;
        } else if (ch == 'J') {
            return 11;
        } else if (ch == 'Q') {
            return 12;
        } else if (ch == 'K') {
            return 13;
        } else if (ch == 'A') {
            return 14;
        } else {
            System.out.println("Could not mapp...");
        }


         return -1;
    }
    public static int problem_p2(String [] inputs) {
        System.out.println("Part 2....");
        return 0;
    }
}