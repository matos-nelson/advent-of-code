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

    static long dfs(String node, String end, Map<String, Set<String>> graph, Set<String> visited) {

        if(visited.contains(node)) {
            return 0L;
        }

        if(node.equals(end)) {
            return 1L;
        }

        visited.add(node);
        long count = 0L;
        for(String v : graph.get(node)) {

            if(!visited.contains(v)) {
                count += dfs(v, end, graph, visited);
            }
        }

        visited.remove(node);
        return count;
    }

    public static long problem_p1(String [] inputs) {
        System.out.println("Part 1....");

        Map<String, Set<String>> graph = new HashMap<>();
        for(String input : inputs) {

            String [] nodeToEdges = input.split(":");
            String node = nodeToEdges[0].trim();

            String [] edges = nodeToEdges[1].trim().split(" ");
            Set<String> set = new HashSet<>(List.of(edges));

            graph.put(node, set);
        }

        long result = dfs("you", "out", graph, new HashSet<>());
        return result;
    }

    public static long problem_p2(String [] inputs) {
        System.out.println("Part 2....");
        return 0L;
    }
}