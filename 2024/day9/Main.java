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

    public static long problem_p1(String [] inputs) {
        System.out.println("Part 1....");

        String input = inputs[0];

        StringBuilder sb = new StringBuilder();
        long blockId = 0;
        boolean isEmpty = false;
        List<Long> blocks = new ArrayList<>();
        for(int i = 0; i < input.length(); i++) {

            int blockSize = input.charAt(i) - '0';
            for(int j = 0; j < blockSize; j++) {
                if(isEmpty) {
                    blocks.add(-1L);
                } else {
                    blocks.add(blockId);
                }
            }

            if(!isEmpty) {
                isEmpty = true;
                blockId++;
            } else {
                isEmpty = false;
            }
        }

        int left = 0;
        int right = blocks.size() - 1;

        while(left < right) {

            while(left < right && left < blocks.size() && blocks.get(left) != -1) {
                left++;
            }

            while(right > left && right > -1 && blocks.get(right) == -1) {
                right--;
            }

            long temp = blocks.get(left);
            blocks.set(left, blocks.get(right));
            blocks.set(right, temp);
        }

        blockId = 0;
        long result = 0;
        for(int i = 0; i < blocks.size(); i++) {
            if(blocks.get(i) == -1) {
                break;
            }

            result += blockId * blocks.get(i);
            blockId++;
        }

        return result;
    }

    public static long problem_p2(String [] inputs) {
        System.out.println("Part 2....");
        return 0L;
    }
}