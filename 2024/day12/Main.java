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

    public static int [][] moves = new int [][] {
        {  1, 0  },
        { -1, 0  },
        {  0, 1  },
        {  0, -1 }
    };

    public static class Cell {
        public int row;
        public int col;
        public char plot;

        public Cell(int row, int col, char plot) {
            this.row = row;
            this.col = col;
            this.plot = plot;
        }
    }

    public static boolean isValid(int n, int m, int row, int col) {
        if(row < 0 || row >= n || col < 0 || col >= m) {
            return false;
        }

        return true;
    }

    public static long [] bfs(int row, int col, int n, int m, char [][] grid, boolean [][] visited) {

        LinkedList<Cell> queue = new LinkedList();
        queue.add(new Cell(row, col, grid[row][col]));

        long area = 0;
        long perimeter = 0;

        while(!queue.isEmpty()) {

            int size = queue.size();
            for(int i = 0; i < size; i++) {

                Cell cell = queue.poll();
                if(visited[cell.row][cell.col]) {
                    continue;
                }

                visited[cell.row][cell.col] = true;
                area++;

                for(int [] move : moves) {
                    int r = cell.row + move[0];
                    int c = cell.col + move[1];

                    boolean isValid = isValid(n, m, r, c);
                    if(!isValid) {
                        perimeter++;
                    } else if(isValid && grid[r][c] != cell.plot) {
                        perimeter++;
                    } else if(isValid && grid[r][c] == cell.plot) {
                        queue.add(new Cell(r, c, grid[r][c]));
                    }
                }
            }
        }

        return new long [] { area, perimeter };
    }

    public static long problem_p1(String [] inputs) {
        System.out.println("Part 1....");

        int n = inputs.length;
        int m = inputs[0].toCharArray().length;

        char [][] grid = new char[n][m];
        for(int i = 0; i < n; i++) {
            grid[i] = inputs[i].toCharArray();
        }

        long result = 0;
        boolean [][] visited = new boolean [n][m];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {

                if(!visited[i][j]) {
                    long [] region = bfs(i, j , n, m, grid, visited);
                    result += region[0] * region[1];
                }

            }
        }

        return result;
    }

    public static long problem_p2(String [] inputs) {
        System.out.println("Part 2....");
        return 0L;
    }
}