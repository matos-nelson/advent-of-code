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

    public static double calculateArea(Point a, Point b) {
        return Math.sqrt((b.x - a.x) * (b.x - a.x) + (b.y - a.y) * (b.y - a.y)) + 1;
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
        return 0L;
    }
}