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

    public static String south = "|JL";
    public static String north = "|7F";
    public static String east = "7J-";
    public static String west = "FL-";

    public static boolean containsCycle = false;
    public static int count = 1;
    public static boolean [][] visited;
    public static boolean [][] marked;
    public static char [][] map;

    public static void buildMap(String [] inputs) {
        map = new char[inputs.length][inputs[0].length()];
        for(int i = 0; i < inputs.length; i++) {
            for(int j = 0; j < inputs[i].length(); j++) {
                map[i][j] = inputs[i].charAt(j);
            }
        }

        visited = new boolean[map.length][map.length];
        marked = new boolean[map.length][map.length];
    }
    public static int problem_p1(String [] inputs) {
        System.out.println("Part 1....");

        buildMap(inputs);

        int i = 0;
        int j = 0;
        boolean isStartingPointFound = false;
        for(i = 0; i < map.length && !isStartingPointFound; i++) {
            for(j = 0; j < map[i].length && !isStartingPointFound; j++) {
                if(map[i][j] == 'S') {
                    isStartingPointFound = true;
                }
            }
        }

        dfs(map, i-1, j-1);
        return count / 2;
    }

    public static void dfs(char[][] map, int row, int col) {
        Deque<String> stack = new ArrayDeque<>();

        stack.push(row + " " + col);
        //System.out.println("Starting at: " + row + " " + col);
        while (!stack.isEmpty()) {

            String [] temp = stack.pop().split(" ");
            int x = Integer.parseInt(temp[0]);
            int y = Integer.parseInt(temp[1]);
            //System.out.println("Processing: " + x + " " + y);

            if (!visited[x][y]) {
                visited[x][y] = true;
                marked[x][y] = true;
            } else {
                marked[x][y] = false;
                stack.pop();
            }

            List<List<Integer>> edges = getEdges(map, x, y);

            boolean isCycleFound = edges.stream()
                .allMatch(e -> marked[e.get(0)][e.get(1)]);
            if(isCycleFound) {
                containsCycle = true;
                return;
            }

            Optional<List<Integer>> edge = edges.stream()
            .filter(e -> !visited[e.get(0)][e.get(1)])
            .findFirst();

            if(edge.isEmpty()) {
                return;
            }

            int a = edge.get().get(0);
            int b = edge.get().get(1);

            count++;
            if(!visited[a][b]) {
                //System.out.println("Visiting " + a + " " + b);
                stack.push(a + " " + b);
            } else if(marked[a][b]) {
                containsCycle = true;
                return;
            }
        }
    }

    public static List<List<Integer>> getEdges(char[][] map, int row, int col) {
        List<List<Integer>> edges = new ArrayList<>();
        if(map[row][col] == 'S' || map[row][col] == '|' || map[row][col] == 'L' || map[row][col] == 'J') {
            if(row > 0 && north.contains(""+map[row-1][col])) {
                edges.add(Arrays.asList(row-1, col));
            }
        }

        if(map[row][col] == 'S' || map[row][col] == 'F' || map[row][col] == '|' || map[row][col] == '7') {
            if(row + 1 < map.length && south.contains(""+map[row+1][col])) {
                edges.add(Arrays.asList(row+1, col));
            }
        }

        if(map[row][col] == 'S' || map[row][col] == 'L' || map[row][col] == 'F' || map[row][col] == '-') {
            if(col < map[row].length-1 && east.contains(""+map[row][col+1])) {
                edges.add(Arrays.asList(row, col+1));
            }
        }

        if(map[row][col] == 'S' || map[row][col] == 'J' || map[row][col] == '-' || map[row][col] == '7') {
            if(col > 0 && west.contains(""+map[row][col-1])) {
                edges.add(Arrays.asList(row, col-1));
            }
        }

        //System.out.println(edges);
        return edges;
    }

    public static int problem_p2(String [] inputs) {
        System.out.println("Part 2....");
        return 0;
    }
}