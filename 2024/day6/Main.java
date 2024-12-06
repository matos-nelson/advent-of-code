import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
        System.out.println(problem_p1(arr));
        System.out.println(problem_p2(arr));
    }

    static int [][] moves = new int[][] {
        { -1, 0 },
        {  0, 1 },
        {  1, 0 },
        {  0, -1}
    };

    static boolean isInBound(int n, int m, int row, int col) {
        if(row < 0 || row >= n || col < 0 || col >= m) {
            return false;
        }

        return true;
    }

    public static long problem_p1(String [] inputs) {
        System.out.println("Part 1....");

        int n = inputs.length;
        int m = inputs[0].length();
        char [][] grid = new char[n][m];

        int row = -1;
        int col = -1;
        for(int i = 0; i < n; i++) {

            for(int j = 0; j < m; j++) {

                grid[i][j] = inputs[i].charAt(j);
                if(grid[i][j] == '^') {
                    row = i;
                    col = j;
                }
            }
        }

        int index = 0;
        while(isInBound(n, m, row, col)) {

            grid[row][col] = 'X';

            int r = row + moves[index][0];
            int c = col + moves[index][1];

            if(isInBound(n, m, r, c) && grid[r][c] == '#') {
                if(++index >= moves.length) {
                    index = 0;
                }
            } else {
                row = r;
                col = c;
            }
        }

        long result = 0;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                if(grid[i][j] == 'X') {
                    result++;
                }
            }
        }

        return result;
    }

    public static boolean isCycle(char [][] grid, int row, int col, int [][] visited, int n, int m) {

        int index = 0;
        while(isInBound(n, m, row, col)) {

            visited[row][col]++;
            if(visited[row][col] > moves.length) {
                return true;
            }

            int r = row + moves[index][0];
            int c = col + moves[index][1];

            if(isInBound(n, m, r, c) && grid[r][c] == '#') {
                if(++index >= moves.length) {
                    index = 0;
                }
            } else {
                row = r;
                col = c;
            }
        }

        return false;
    }

    public static long problem_p2(String [] inputs) {
        System.out.println("Part 2....");

        int n = inputs.length;
        int m = inputs[0].length();
        char [][] grid = new char[n][m];
        int [][] count = new int[n][m];

        int row = -1;
        int col = -1;
        for(int i = 0; i < n; i++) {

            for(int j = 0; j < m; j++) {

                grid[i][j] = inputs[i].charAt(j);
                if(grid[i][j] == '^') {
                    row = i;
                    col = j;
                }
            }
        }

        int index = 0;
        int startRow = row;
        int startCol = col;
        List<int []> cells = new ArrayList<>();
        while(isInBound(n, m, row, col)) {

            if(count[row][col] == 0) {
                count[row][col] = 1;
                cells.add(new int[]{row, col});
            }

            int r = row + moves[index][0];
            int c = col + moves[index][1];

            if(isInBound(n, m, r, c) && grid[r][c] == '#') {
                if(++index >= moves.length) {
                    index = 0;
                }
            } else {
                row = r;
                col = c;
            }
        }

        long result = 0;
        for(int [] cell : cells) {

            count = new int[n][m];
            int r = cell[0];
            int c = cell[1];

            grid[r][c] = '#';

            boolean isCycle = isCycle(grid, startRow, startCol, count, n, m);
            if(isCycle) {
                result++;
            }

            grid[r][c] = '.';

        }

        return result;
    }
}