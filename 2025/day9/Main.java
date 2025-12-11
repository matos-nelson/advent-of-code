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
        public long x;
        public long y;

        public Point(long x, long y) {
            this.x = x;
            this.y = y;
        }

        public String toString() {
            return "[" + this.x + "," + this.y + "]";
        }
    }

    static boolean isPointInside(long rMinX, long rMinY, List<List<Long>> verticalEdges) {

        int intersections = 0;
        for(List<Long> edge : verticalEdges) {

            Long vx = edge.get(0);
            Long minY = edge.get(1);
            Long maxY = edge.get(2);

            if(vx > rMinX) {
                if(minY <= rMinY && rMinY < maxY) {
                    intersections++;
                }
            }
        }

        return intersections % 2 == 1;
    }

    static boolean intersectsRectangle(Long rMinX, Long rMinY, Long rMaxX, Long rMaxY, List<List<Long>> verticalEdges, List<List<Long>> horizontalEdges) {

        for(List<Long> edges : verticalEdges) {

            Long vx = edges.get(0);
            Long vyMin = edges.get(1);
            Long vyMax = edges.get(2);

            if(rMinX < vx && vx < rMaxX) {

                long overlapMin = Math.max(vyMin, rMinY);
                long overlapMax = Math.min(vyMax, rMaxY);

                if(overlapMin < overlapMax) {
                    return true;
                }
            }
        }

        for(List<Long> edges : horizontalEdges) {

            Long hxMin = edges.get(0);
            Long hxMax = edges.get(1);
            Long hy = edges.get(2);

            if(rMinY < hy && hy < rMaxY) {
                long overlapMin = Math.max(hxMin, rMinX);
                long overlapMax = Math.min(hxMax, rMaxX);

                if(overlapMin < overlapMax) {
                    return true;
                }
            }
        }

        return false;
    }

    public static long problem_p1(String [] inputs) {
        System.out.println("Part 1....");

        List<Point> points = new ArrayList<>();
        for(String input : inputs) {
            String [] point = input.split(",");
            long x = Long.parseLong(point[0]);
            long y = Long.parseLong(point[1]);

            points.add(new Point(x, y));
        }

        long result = 0;
        for(int i = 0; i < points.size(); i++) {
            long x1 = points.get(i).x;
            long y1 = points.get(i).y;
            for(int j = 0; j < points.size(); j++) {

                if(i == j) {
                    continue;
                }

                long x2 = points.get(j).x;
                long y2 = points.get(j).y;

                long area = (Math.abs(x2 - x1) + 1) * (Math.abs(y2 - y1) + 1);
                result = Math.max(result, area);
            }
        }

        return result;
    }

    public static long problem_p2(String [] inputs) {
        System.out.println("Part 2....");

        List<Point> points = new ArrayList<>();
        for(String input : inputs) {
            String [] point = input.split(",");
            long x = Long.parseLong(point[0]);
            long y = Long.parseLong(point[1]);

            points.add(new Point(x, y));
        }

        int n = points.size();
        List<List<Long>> verticalEdges = new ArrayList<>();
        List<List<Long>> horizontalEdges = new ArrayList<>();
        for(int i = 0; i < n; i++) {

            Point a = points.get(i);
            Point b = points.get((i + 1) % n);
            if(a.x == b.x) {
                long yMin = Math.min(a.y, b.y);
                long yMax = Math.max(a.y, b.y);
                verticalEdges.add(List.of(a.x, yMin, yMax));
            } else {
                long xMin = Math.min(a.x, b.x);
                long xMax = Math.max(a.x, b.x);
                horizontalEdges.add(List.of(xMin, xMax, a.y));
            }
        }

        long result = 0;
        for(int i = 0; i < points.size(); i++) {
            long x1 = points.get(i).x;
            long y1 = points.get(i).y;
            for(int j = 0; j < points.size(); j++) {

                if(i == j) {
                    continue;
                }

                long x2 = points.get(j).x;
                long y2 = points.get(j).y;

                long area = (Math.abs(x2 - x1) + 1) * (Math.abs(y2 - y1) + 1);
                if(area <= result) {
                    continue;
                }

                long rMinX = Math.min(x1, x2);
                long rMaxX = Math.max(x1, x2);
                long rMinY = Math.min(y1, y2);
                long rMaxY = Math.max(y1, y2);

                if(isPointInside(rMinX + 1L, rMinY + 1L, verticalEdges)) {

                    if(!intersectsRectangle(rMinX, rMinY, rMaxX, rMaxY, verticalEdges, horizontalEdges)) {
                        result = area;
                    }
                }
            }
        }

        return result;
    }
}