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

    static long dfs(String node, String end, Map<String, Set<String>> graph, Set<String> visited)  {

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

    static long dfs(String node, String end, int state, Map<String, Set<String>> graph, long [][][] memo, Map<String, Integer> nodeIds)  {

        int id = nodeIds.get(node);
        if(memo[id][state][0] == 1) {
            return memo[id][state][1];
        }

        long result = 0;
        if(node.equals(end)) {
            result = state == 3 ? 1 : 0;
        } else {

            boolean isDacSeen = state == 1 || state == 3;
            boolean isFftSeen = state == 2 || state == 3;

            if(node.equals("dac")) {
                isDacSeen = true;
            } else if(node.equals("fft")) {
                isFftSeen = true;
            }
            
            int newState = 0;
            if(!isDacSeen && !isFftSeen) {
                newState = 0;
            } else if(isDacSeen && !isFftSeen) {
                newState = 1;
            } else if(!isDacSeen && isFftSeen) {
                newState = 2;
            } else {
                newState = 3;
            }

            for(String v : graph.get(node)) {
                result += dfs(v, end, newState, graph, memo, nodeIds);
            }
        }

        memo[id][state][0] = 1;
        memo[id][state][1] = result;

        return result;
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

        String start = "you";
        String end = "out";
        if(!graph.containsKey(start)) {
            return 0L;
        }

        long result = dfs(start, end, graph, new HashSet<>());
        return result;
    }

    public static long problem_p2(String [] inputs) {
        System.out.println("Part 2....");
        Map<String, Set<String>> graph = new HashMap<>();
        for(String input : inputs) {

            String [] nodeToEdges = input.split(":");
            String node = nodeToEdges[0].trim();

            String [] edges = nodeToEdges[1].trim().split(" ");
            Set<String> set = new HashSet<>(List.of(edges));

            graph.put(node, set);
        }

        String start = "svr";
        String end = "out";
        if(!graph.containsKey(start)) {
            return 0L;
        }

        int id = 0;
        Map<String, Integer> nodeIds = new HashMap<>();
        for(String key : graph.keySet()) {
            if(!nodeIds.containsKey(key)) {
                nodeIds.put(key, id);
                id++;
            }

            for(String v : graph.get(key)) {
                if(!nodeIds.containsKey(v)) {
                    nodeIds.put(v, id);
                    id++;
                }
            }
        }

        long [][][] memo = new long[id][4][2];
        long result = dfs(start, end, 0, graph, memo, nodeIds);
        return result;
    }
}