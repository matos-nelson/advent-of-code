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

    static class Beam {
        public int row;
        public int col;
        public long count;

        public Beam(int row, int col, long count) {
            this.row = row;
            this.col = col;
            this.count = count;
        }

        public Beam(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public String toString() {
            return "[" + this.row + "," + this.col + "]";
        }
    }

    public static long problem_p1(String [] inputs) {
        System.out.println("Part 1....");

        int m = inputs.length;
        int n = inputs[0].length();

        int startRow = -1;
        int startCol = -1;
        for(int i = 0; i < m && startRow == -1; i++) {
            for(int j = 0; j < n && startRow == -1; j++) {
                if(inputs[i].charAt(j) == 'S') {
                    startRow = i;
                    startCol = j;
                }
            }
        }

        boolean [][] visited = new boolean[m][n];
        LinkedList<Beam> queue = new LinkedList<>();
        queue.add(new Beam(startRow, startCol));
        while(!queue.isEmpty()) {

            int size = queue.size();
            for(int i = 0; i < size; i++) {

                Beam b = queue.poll();
                char ch = inputs[b.row].charAt(b.col);
                if(ch == '^' && !visited[b.row][b.col]) {
                    visited[b.row][b.col] = true;
                    int leftCol = b.col - 1;
                    if(leftCol >= 0) {
                        Beam lBeam = new Beam(b.row, leftCol);
                        queue.add(lBeam);
                    }

                    int rightCol = b.col + 1;
                    if(rightCol < n) {
                        Beam rBeam = new Beam(b.row, rightCol);
                        queue.add(rBeam);
                    }

                } else if (ch != '^') {

                    int downRow = b.row + 1;
                    if(downRow < m) {
                        queue.add(new Beam(downRow, b.col));
                    }
                }
            }
        }

        long result = 0L;
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                if(visited[i][j]) {
                    result++;
                }
            }
        }

        return result;
    }

    public static long problem_p2(String [] inputs) {
        System.out.println("Part 2....");

        int m = inputs.length;
        int n = inputs[0].length();

        int startRow = -1;
        int startCol = -1;
        for(int i = 0; i < m && startRow == -1; i++) {
            for(int j = 0; j < n && startRow == -1; j++) {
                if(inputs[i].charAt(j) == 'S') {
                    startRow = i;
                    startCol = j;
                }
            }
        }

        long result = 0L;
        LinkedList<Beam> queue = new LinkedList<>();
        queue.add(new Beam(startRow, startCol, 1));
        while(!queue.isEmpty()) {

            int size = queue.size();
            Map<String, Beam> map = new HashMap<>();
            for(int i = 0; i < size; i++) {

                Beam b = queue.poll();
                char ch = inputs[b.row].charAt(b.col);
                if(b.row == m - 1) {
                    result += b.count;
                } else if(ch == '^') {

                    int leftCol = b.col - 1;
                    if(leftCol >= 0) {

                        String leftKey = b.row + "-" + leftCol;
                        if(map.containsKey(leftKey)) {
                            Beam lb = map.get(leftKey);
                            lb.count += b.count;
                            map.put(leftKey, lb);
                        } else {
                            map.put(leftKey, new Beam(b.row, leftCol, b.count));
                        }
                    }

                    int rightCol = b.col + 1;
                    if(rightCol < n) {

                        String rightKey = b.row + "-" + rightCol;
                        if(map.containsKey(rightKey)) {
                            Beam rb = map.get(rightKey);
                            rb.count += b.count;
                            map.put(rightKey, rb);
                        } else {
                            map.put(rightKey, new Beam(b.row, rightCol, b.count));
                        }
                    }

                } else if (ch != '^') {

                    int downRow = b.row + 1;
                    String downKey = downRow + "-" + b.col;
                    if(map.containsKey(downKey)) {
                        Beam db = map.get(downKey);
                        db.count += b.count;
                        map.put(downKey, db);
                    } else {
                        map.put(downKey, new Beam(downRow, b.col, b.count));
                    }
                }
            }

            queue.addAll(map.values());
        }

        return result;
    }
}