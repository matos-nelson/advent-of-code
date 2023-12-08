import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

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
        //System.out.println(problem_p1(arr));
        System.out.println(problem_p2(arr));
    }

    public static long problem_p1(String [] inputs) {
        System.out.println("Part 1....");
        String [] seeds = inputs[0].split(": ")[1].split(" ");

        long minLocation = Long.MAX_VALUE;
        for(int k = 0; k < seeds.length; k++) {

            long seedValue = Long.parseLong(seeds[k]);
            for(int i = 1; i < inputs.length; i++) {
                if(inputs[i].trim().length() == 0) {
                    continue;
                }

                if(inputs[i].contains("map:")) {
                    int index = i + 1;
                    List<String> map = new ArrayList<>();
                    while(index < inputs.length && inputs[index].trim().length() != 0) {
                        map.add(inputs[index]);
                        index++;
                    }

                    for(int n = 0; n < map.size(); n++) {
                        String [] entries = map.get(n).split(" ");
                        long [] ranges = new long[entries.length];

                        for(int j = 0; j < entries.length; j++) {
                            ranges[j] = Long.parseLong(entries[j]);
                        }

                        if(seedValue <= ranges[1] + ranges[2] - 1 && seedValue >= ranges[1]) {
                            seedValue = Math.abs(seedValue - ranges[1] + ranges[0]);
                            break;
                        }
                    }

                    i = index;
                }
            }

            if(seedValue < minLocation) {
                minLocation = seedValue;
            }
        }

        return minLocation;
    }

    public static long problem_p2(String [] inputs) {
        System.out.println("Part 2....");

        int mapKey = 0;
        List<List<String>> almanac = new ArrayList<>(7);
        for(int i = 1; i < inputs.length; i++) {
            if(inputs[i].trim().length() == 0) {
                continue;
            }

            if(inputs[i].contains("map:")) {
                int index = i + 1;
                List<String> map = new ArrayList<>();
                while(index < inputs.length && inputs[index].trim().length() != 0) {
                    map.add(inputs[index]);
                    index++;
                }

                almanac.add(map);
                i = index;
            }
        }

        //System.out.println(almanac);

        String [] seedValues = inputs[0].split(": ")[1].split(" ");
        long [] seeds = new long[seedValues.length];

        for(int i = 0; i < seedValues.length; i++) {
            seeds[i] = Long.parseLong(seedValues[i]);
        }

        long minLocation = Long.MAX_VALUE;
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Callable<Long>> tasks = new ArrayList<>();
        for(int k = 0; k < seeds.length; k+=2) {

            long batchCnt = 0;
            for(long v = seeds[k]; v < seeds[k]+seeds[k+1]; v++) {
                final long seed = v;
                tasks.add(() -> compute(seed, almanac));

                if (tasks.size() == 100) {
                    try {
                        List<Future<Long>> results = executorService.invokeAll(tasks);
                        for(Future<Long> result : results) {
                            Long resultValue = result.get();
                            if(resultValue < minLocation) {
                                minLocation = resultValue;
                            }
                        }
                        tasks.clear();
                        System.out.println("Computed Batch: " + batchCnt++);
                    } catch (InterruptedException | ExecutionException e) {
                        System.out.println("Something went wrong here...");
                    }
                }
            }
        }

        try {
            executorService.shutdown();
            if(!executorService.awaitTermination(-1, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            System.out.println("Something went wrong here shut down...");
        }
        System.out.println("kjls");
        return minLocation;
    }

    public static long compute(Long seedValue, List<List<String>> almanac) {

        for(List<String> map : almanac) {
            for(int n = 0; n < map.size(); n++) {
                String [] entries = map.get(n).split(" ");
                long [] ranges = new long[entries.length];

                for(int j = 0; j < entries.length; j++) {
                    ranges[j] = Long.parseLong(entries[j]);
                }

                if(seedValue <= ranges[1] + ranges[2] - 1 && seedValue >= ranges[1]) {
                    seedValue = Math.abs(seedValue - ranges[1] + ranges[0]);
                    break;
                }
            }
        }

       return seedValue;
    }
}