import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
        System.out.println(problem_p1(arr));
        System.out.println(problem_p2(arr));
    }

    public static boolean isValid(char [][] grid, int row, int col) {
        if(row < 0 || row >= grid.length || col < 0 || col >= grid[row].length) {
            return false;
        }

        return true;
    }

    public static long searchWord(char [][] grid, int row, int col, String word) {

        int [][] moves = new int[][] {
            {  1, 0  }, // down
            { -1, 0  }, // up
            {  0, 1  }, // right
            {  0, -1 }, // left
            { -1, -1 }, // top left
            { -1,  1 }, // top right
            {  1, -1 }, // bottom left
            {  1,  1 }, // bottom right
        };

        long count = 0;
        for(int i = 0; i < moves.length; i++) {

            int r = row;
            int c = col;
            int index = 0;

            while(isValid(grid, r, c) && index < word.length() && grid[r][c] == word.charAt(index)) {
                r += moves[i][0];
                c += moves[i][1];
                index++;
            }

            if(index == word.length()) {
                //System.out.println(row + " " + col + " D: " + i);
                count++;
            }
        }

        return count;
    }

    public static long problem_p1(String [] inputs) {
        System.out.println("Part 1....");

        int n = inputs.length;
        int m = inputs[0].length();

        char [][] grid = new char[n][m];
        for(int i = 0; i < n; i++) {
            grid[i] = Arrays.copyOf(inputs[i].toCharArray(), m);
        }

        long result = 0;
        String word = "XMAS";
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                if(grid[i][j] == word.charAt(0)) {
                    result += searchWord(grid, i, j, word);
                }
            }
        }

        return result;
    }

    public static long problem_p2(String [] inputs) {
        System.out.println("Part 2....");

        int n = inputs.length;
        int m = inputs[0].length();

        char [][] grid = new char[n][m];
        for(int i = 0; i < n; i++) {
            grid[i] = Arrays.copyOf(inputs[i].toCharArray(), m);
        }

        long result = 0;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                if(grid[i][j] == 'A') {


                    // M on left S on right
                    if(
                        isValid(grid, i-1, j-1) && grid[i-1][j-1] == 'M' &&
                        isValid(grid, i+1, j-1) && grid[i+1][j-1] == 'M' &&

                        isValid(grid, i-1, j+1) && grid[i-1][j+1] == 'S' &&
                        isValid(grid, i+1, j+1) && grid[i+1][j+1] == 'S'
                    ) {
                        result++;
                    }

                    // S on left M on right
                    if(
                        isValid(grid, i-1, j-1) && grid[i-1][j-1] == 'S' &&
                        isValid(grid, i+1, j-1) && grid[i+1][j-1] == 'S' &&

                        isValid(grid, i-1, j+1) && grid[i-1][j+1] == 'M' &&
                        isValid(grid, i+1, j+1) && grid[i+1][j+1] == 'M'
                    ) {
                        result++;
                    }

                    // M on top S on bottom
                    if(
                        isValid(grid, i-1, j-1) && grid[i-1][j-1] == 'M' &&
                        isValid(grid, i+1, j-1) && grid[i+1][j-1] == 'S' &&

                        isValid(grid, i-1, j+1) && grid[i-1][j+1] == 'M' &&
                        isValid(grid, i+1, j+1) && grid[i+1][j+1] == 'S'
                    ) {
                        result++;
                    }

                    // S on top M on bottom
                    if(
                        isValid(grid, i-1, j-1) && grid[i-1][j-1] == 'S' &&
                        isValid(grid, i+1, j-1) && grid[i+1][j-1] == 'M' &&

                        isValid(grid, i-1, j+1) && grid[i-1][j+1] == 'S' &&
                        isValid(grid, i+1, j+1) && grid[i+1][j+1] == 'M'
                    ) {
                        result++;
                    }

                }
            }
        }

        return result;
    }
}