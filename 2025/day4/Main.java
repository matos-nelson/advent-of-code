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

    static int [][] moves = new int[][] {
        {1, 0},
        {0, 1},
        {-1, 0},
        {0, -1},
        {1, 1},
        {-1, 1},
        {1, -1},
        {-1, -1},
    };

    public static long problem_p1(String [] inputs) {
        System.out.println("Part 1....");

        int m = inputs.length;
        int n = inputs[0].length();

        int [][] grid = new int[m][n];
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {

                for(int [] move : moves) {
                    int row = move[0] + i;
                    int col = move[1] + j;

                    if(row >= 0 && row < m && col >= 0 && col < n && inputs[row].charAt(col) == '@') {
                        grid[i][j]++;
                    }
                }
            }
        }

        long count = 0L;
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                if(inputs[i].charAt(j) == '@' && grid[i][j] < 4) {
                    count++;
                }
            }
        }

        return count;
    }

    public static long problem_p2(String [] inputs) {
        System.out.println("Part 2....");

        int m = inputs.length;
        int n = inputs[0].length();

        List<StringBuilder> graph = new ArrayList<>();
        for(String input : inputs) {
            graph.add(new StringBuilder(input));
        }

        long count = 0L;
        while(true) {

            boolean canbeRemoved = false;
            int [][] grid = new int[m][n];
            for(int i = 0; i < m; i++) {
                for(int j = 0; j < n; j++) {
                    for(int [] move : moves) {
                        int row = move[0] + i;
                        int col = move[1] + j;
                        if(row >= 0 && row < m && col >= 0 && col < n && graph.get(row).charAt(col) == '@') {
                            grid[i][j]++;
                        }
                    }
                }
            }

            for(int i = 0; i < m; i++) {
                for(int j = 0; j < n; j++) {
                    if(graph.get(i).charAt(j) == '@' && grid[i][j] < 4) {
                        graph.get(i).setCharAt(j, '.');
                        canbeRemoved = true;
                        count++;
                    }
                }
            }

            if(!canbeRemoved) {
                break;
            }
        }

        return count;
    }
}