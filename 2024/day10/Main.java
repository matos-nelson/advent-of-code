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

    public static class Cell {

        public int row;
        public int col;
        public int value;

        public Cell(int row, int col, int value) {
            this.row = row;
            this.col = col;
            this.value = value;
        }
    }

    public static boolean isValid(int n, int m, int row, int col) {
        if(row < 0 || row >= n || col < 0 || col >= m) {
            return false;
        }

        return true;
    }

    public static long dfs(int row, int col, int value, int n, int m, int [][] grid, boolean [][] visited) {

        if(visited[row][col] == true) {
            return 0;
        }

        if(isValid(n, m, row, col) && value == 9 && grid[row][col] == value) {
            return 1;
        }

        visited[row][col] = true;

        long result = 0L;
        for(int [] move : moves) {
            int r = row + move[0];
            int c = col + move[1];
            if(isValid(n, m, r, c) && !visited[r][c] && grid[r][c] == value + 1) {
                result += dfs(r, c, grid[r][c], n, m, grid, visited);
            }
        }

        visited[row][col] = false;
        return result;
    }

    public static int [][] moves = new int [][] {
        { 1, 0},
        {-1, 0},
        {0,  1},
        {0, -1}
    };

    public static long problem_p1(String [] inputs) {
        System.out.println("Part 1....");

        int n = inputs.length;
        int m = inputs[0].toCharArray().length;

        int [][] grid = new int[n][m];

        List<Cell> starts = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                int value = inputs[i].charAt(j) == '.' ? -1 : inputs[i].charAt(j) - '0';
                grid[i][j] = value;

                if(value == 0) {
                    starts.add(new Cell(i, j, value));
                }
            }
        }

        long result = 0;
        for(Cell start : starts) {

            boolean [][] visited = new boolean [n][m];
            LinkedList<Cell> queue = new LinkedList();
            queue.add(start);
            while(!queue.isEmpty()) {

                int size = queue.size();
                for(int i = 0; i < size; i++) {

                    Cell u = queue.poll();
                    if(visited[u.row][u.col]) {
                        continue;
                    }

                    visited[u.row][u.col] = true;

                    if(grid[u.row][u.col] == 9) {
                        result++;
                        continue;
                    }

                    for(int [] move : moves) {
                        int r = u.row + move[0];
                        int c = u.col + move[1];

                        if(isValid(n, m, r, c) && !visited[r][c] && grid[r][c] == u.value+1) {
                            queue.add(new Cell(r, c, grid[r][c]));
                        }
                    }
                }
            }

        }

        return result;
    }

    public static long problem_p2(String [] inputs) {
        System.out.println("Part 2....");

        int n = inputs.length;
        int m = inputs[0].toCharArray().length;

        int [][] grid = new int[n][m];

        List<Cell> starts = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                int value = inputs[i].charAt(j) == '.' ? -1 : inputs[i].charAt(j) - '0';
                grid[i][j] = value;

                if(value == 0) {
                    starts.add(new Cell(i, j, value));
                }
            }
        }

        long result = 0;
        for(Cell start : starts) {

            boolean [][] visited = new boolean[n][m];
            result += dfs(start.row, start.col, start.value, n, m, grid, visited);

        }

        return result;
    }
}