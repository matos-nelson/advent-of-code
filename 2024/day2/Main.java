import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
        System.out.println(problem_p1(arr));
        System.out.println(problem_p2(arr));
    }

    public static long problem_p1(String [] inputs) {
        System.out.println("Part 1....");

        int n = inputs.length;
        int increasing = 0;
        int decreasing = 1;
        long result = 0;
        for(int i = 0; i < n; i++) {

            int m = inputs[i].split(" ").length;
            long [] digits = new long[m];
            String [] d = inputs[i].trim().split(" ");
            for(int k = 0; k < d.length; k++) {
                digits[k] = Long.parseLong(d[k]);
            }

            boolean isSafe = true;
            int direction = digits.length > 1 && digits[1] > digits[0] ? increasing : decreasing;

            //System.out.println("Row: " + (i+1) + " D : " + direction);
            for(int j = 1; j < m; j++) {

                long value = Math.abs(digits[j] - digits[j-1]);
                if(value < 1L || value > 3L) {
                    isSafe =  false;
                    break;
                }

                if(direction == 0 && digits[j] < digits[j-1]) {
                    isSafe = false;
                    break;
                }

                if(direction == 1 && digits[j] > digits[j-1]) {
                    isSafe = false;
                    break;
                }
            }

            if(isSafe) {
                result++;
            }
        }

        return result;
    }

    public static boolean isSafe(long [] digits) {

        int increasing = 0;
        int decreasing = 1;
        boolean isSafe = true;
        int direction = digits.length > 1 && digits[1] > digits[0] ? increasing : decreasing;
        for(int j = 1; j < digits.length; j++) {

            long value = Math.abs(digits[j] - digits[j-1]);
            if(value < 1L || value > 3L) {
                isSafe =  false;
                break;
            }

            if(direction == 0 && digits[j] < digits[j-1]) {
                isSafe = false;
                break;
            }

            if(direction == 1 && digits[j] > digits[j-1]) {
                isSafe = false;
                break;
            }
        }

        return isSafe;
    }

    public static long problem_p2(String [] inputs) {
        System.out.println("Part 2....");

        int n = inputs.length;

        long result = 0;
        for(int i = 0; i < n; i++) {

            String [] d = inputs[i].trim().split(" ");
            long [] digits = new long[d.length];
            for(int j = 0; j < d.length; j++) {
                digits[j] = Long.parseLong(d[j]);
            }

            boolean isSafe = isSafe(digits);
            if(isSafe) {
                System.out.println("Row : " + (i+1) + " is safe.");
                result++;
                continue;
            }

            int safeCount = 0;
            System.out.println("Row : " + (i+1) + " is being processed.");
            for(int j = 0; j < d.length; j++) {
                long[] left = Arrays.copyOfRange(digits, 0, j);
                long[] right = Arrays.copyOfRange(digits, j+1, d.length);

                long[] row = new long[left.length + right.length];
                System.arraycopy(left, 0, row, 0, left.length);
                System.arraycopy(right, 0, row, left.length, right.length);

                isSafe = isSafe(row);
                System.out.println(Arrays.toString(row));
                System.out.println("Is Safe: " + isSafe);
                if(isSafe) {
                    safeCount++;
                }
            }

            if(safeCount > 0) {
                result++;
            }

            System.out.println();
        }

        return result;

   }
}