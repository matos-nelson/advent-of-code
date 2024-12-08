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

    static private int [] convertDecimalToFraction(double x){
        if (x < 0) {
            int [] fraction = convertDecimalToFraction(-x);
            fraction[0] *= -1;
            return fraction;
        }

        double tolerance = 1.0E-6;
        double h1=1; double h2=0;
        double k1=0; double k2=1;
        double b = x;
        do {
            double a = Math.floor(b);
            double aux = h1; h1 = a*h1+h2; h2 = aux;
            aux = k1; k1 = a*k1+k2; k2 = aux;
            b = 1/(b-a);
        } while (Math.abs(x-h1/k1) > x*tolerance);

        return new int [] {(int)h1, (int)k1};
    }

    private static boolean isValid(int n, int m, int row, int col) {
        if(row < 0 || row >= n || col < 0 || col >= m) {
            return false;
        }

        return true;
    }

    public static long problem_p1(String [] inputs) {
        System.out.println("Part 1....");

        int n = inputs.length;
        int m = inputs[0].toCharArray().length;

        char [][] grid = new char[n][m];
        for(int i = 0; i < n; i++) {
            grid[i] = inputs[i].toCharArray();
        }

        Map<Character, List<List<Integer>>> map = new HashMap<>();
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                char cell = grid[i][j];
                if(Character.isLetterOrDigit(cell)) {
                    List<List<Integer>> locs = map.getOrDefault(cell, new ArrayList<>());
                    locs.add(List.of(i,j));
                    map.put(cell, locs);
                }
            }
        }

        int [][] antinodes = new int[n][m];
        for(Character key : map.keySet()) {

            List<List<Integer>> antennas = map.get(key);
            if(antennas.size() < 2) {
                continue;
            }

            for(int i = 0; i < antennas.size()-1; i++) {

                List<Integer> u = antennas.get(i);
                for(int j = i+1; j < antennas.size(); j++) {

                    List<Integer> v = antennas.get(j);

                    double slope = (v.get(1) - u.get(1)) * 1.0 / (v.get(0) - u.get(0));
                    int [] s = convertDecimalToFraction(slope);

                    int r1 = u.get(0) - s[1];
                    int c1 = u.get(1) - s[0];

                    int r2 = v.get(0) + s[1];
                    int c2 = v.get(1) + s[0];
                    if(isValid(n, m, r1, c1)) {
                        antinodes[r1][c1]++;
                    }

                    if(isValid(n, m, r2, c2)) {
                        antinodes[r2][c2]++;
                    }
                }
            }
        }

        long result = 0;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                if(antinodes[i][j] > 0) {
                    result++;
                }
            }
        }

        return result;
    }

    public static long problem_p2(String [] inputs) {
        System.out.println("Part 2....");

        int n = inputs.length;
        int m = inputs[0].toCharArray().length;

        char [][] grid = new char[n][m];
        for(int i = 0; i < n; i++) {
            grid[i] = inputs[i].toCharArray();
        }

        Map<Character, List<List<Integer>>> map = new HashMap<>();
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                char cell = grid[i][j];
                if(Character.isLetterOrDigit(cell)) {
                    List<List<Integer>> locs = map.getOrDefault(cell, new ArrayList<>());
                    locs.add(List.of(i,j));
                    map.put(cell, locs);
                }
            }
        }


        long result = 0;
        int [][] antinodes = new int[n][m];
        for(Character key : map.keySet()) {

            List<List<Integer>> antennas = map.get(key);
            if(antennas.size() < 2) {
                continue;
            }

            for(int i = 0; i < antennas.size()-1; i++) {

                List<Integer> u = antennas.get(i);
                for(int j = i+1; j < antennas.size(); j++) {

                    List<Integer> v = antennas.get(j);

                    double slope = (v.get(1) - u.get(1)) * 1.0 / (v.get(0) - u.get(0));
                    int [] s = convertDecimalToFraction(slope);

                    boolean isUpGood = true;
                    boolean isDownGood = true;
                    int r1 = u.get(0);
                    int c1 = u.get(1);
                    int r2 = u.get(0);
                    int c2 = u.get(1);
                    antinodes[r1][c1]++;

                    while(isUpGood || isDownGood) {

                        r1 -= s[1];
                        c1 -= s[0];

                        r2 += s[1];
                        c2 += s[0];

                        isUpGood = isUpGood && isValid(n, m, r1, c1);
                        if(isUpGood) {
                            antinodes[r1][c1]++;
                        }

                        isDownGood = isDownGood && isValid(n, m, r2, c2);
                        if(isDownGood) {
                            antinodes[r2][c2]++;
                        }
                    }
                }
            }
        }

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                if(antinodes[i][j] > 0) {
                    result++;
                }
            }
        }

        return result;
    }
}