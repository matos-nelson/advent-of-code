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

    static class Point {

        public int id;
        public long x;
        public long y;
        public long z;

        Point(int id, long x, long y, long z) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public String toString() {
            return "[" + this.id + ", " + this.x + ", " + this.y + ", " + this.z + "]";
        }
    }

    static class PointPair {

        public Point a;
        public Point b;
        private double distance;

        public PointPair(Point a, Point b) {
            this.a = a;
            this.b = b;
            setDistance();
        }

        public double getDistance() {
            return this.distance;
        }

        private void setDistance() {

            long x1 = a.x;
            long y1 = a.y;
            long z1 = a.z;

            long x2 = b.x;
            long y2 = b.y;
            long z2 = b.z;

            long x = (x2 - x1) * (x2-x1);
            long y = (y2 - y1) * (y2- y1);
            long z = (z2 - z1) * (z2 - z1);

            this.distance = Math.sqrt(x + y + z);
        }

        public String toString() {
            return this.a.toString() + "," + this.b.toString() + " D: " + this.distance;
        }
    }

    public static long problem_p1(String [] inputs) {
        System.out.println("Part 1....");

        int n = inputs.length;
        List<Point> points = new ArrayList<>();
        for(int i = 0; i < n; i++) {

            String [] point = inputs[i].split(",");
            long x = Long.parseLong(point[0]);
            long y = Long.parseLong(point[1]);
            long z = Long.parseLong(point[2]);

            points.add(new Point(i, x, y, z));
        }


        boolean [][] pairs = new boolean[n][n];
        PriorityQueue<PointPair> pq = new PriorityQueue<>(Comparator.comparing(PointPair::getDistance));
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {

                if(i != j && !pairs[i][j]) {
                    pq.add(new PointPair(points.get(i), points.get(j)));
                    pairs[i][j] = true;
                    pairs[j][i] = true;
                }
            }
        }

        int count = 0;
        int componentId = 0;
        int limit = 1000;
        Map<Integer, Integer> componentToSize = new HashMap<>();
        Map<Integer, Integer> pointToComponent = new HashMap<>();
        while(count < limit && !pq.isEmpty()) {

            PointPair pp = pq.poll();
            int p1ComponentId = pointToComponent.getOrDefault(pp.a.id, -1);
            int p2ComponentId = pointToComponent.getOrDefault(pp.b.id, -1);

            if(p1ComponentId != -1 && p2ComponentId != -1 && p1ComponentId == p2ComponentId) {
                // do nothing
            } else if(p1ComponentId == -1 && p2ComponentId == -1) {
                componentToSize.put(componentId, 2);

                pointToComponent.put(pp.a.id, componentId);
                pointToComponent.put(pp.b.id, componentId);
                componentId++;
            } else if(p1ComponentId != -1 && p2ComponentId == -1) {
                componentToSize.put(p1ComponentId, componentToSize.get(p1ComponentId) + 1);
                pointToComponent.put(pp.b.id, p1ComponentId);
            } else if(p1ComponentId == -1 && p2ComponentId != -1) {
                componentToSize.put(p2ComponentId, componentToSize.get(p2ComponentId) + 1);
                pointToComponent.put(pp.a.id, p2ComponentId);
            } else {

                // merge
                int targetComponentId = componentToSize.get(p1ComponentId) > componentToSize.get(p2ComponentId) ? p1ComponentId : p2ComponentId;
                int filterComponentId = componentToSize.get(p1ComponentId) <= componentToSize.get(p2ComponentId) ? p1ComponentId : p2ComponentId;
                List<Integer> pointIds = new ArrayList<>();
                for(Map.Entry<Integer, Integer> entry : pointToComponent.entrySet()) {
                    if(entry.getValue() == filterComponentId) {
                        pointIds.add(entry.getKey());
                    }
                }

                int freq = componentToSize.get(filterComponentId) - pointIds.size();
                if(freq == 0) {
                    componentToSize.remove(filterComponentId);
                } else {
                    componentToSize.put(filterComponentId, freq);
                }

                componentToSize.put(targetComponentId, componentToSize.get(targetComponentId) + pointIds.size());
                for(int id : pointIds) {
                    pointToComponent.put(id, targetComponentId);
                }
            }

            count++;
        }

        List<Integer> sizes = new ArrayList<>(componentToSize.values());
        sizes.sort(Collections.reverseOrder());
        return sizes.get(0) * sizes.get(1) * sizes.get(2);
    }

    public static long problem_p2(String [] inputs) {
        System.out.println("Part 2....");
        return 0L;
    }
}