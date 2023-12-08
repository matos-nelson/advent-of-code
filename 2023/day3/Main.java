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

    public static int problem_p1(String [] inputs) {
        System.out.println("Part 1....");

        int total = 0;
        for(int i = 0; i < inputs.length; i++) {

            String line = inputs[i];
            int digit = 0;
            boolean isAdjacentToSymbol = false;
            for(int j = 0; j < line.length(); j++) {

                char ch = line.charAt(j);
                if(Character.isDigit(ch)) {
                    digit *= 10;
                    digit += ch - '0';
                    if(!isAdjacentToSymbol) {
                        //System.out.println("Determining if adj: " + digit);
                        isAdjacentToSymbol = isAdjacentToSymbol(inputs, i, j);
                    }
                } else if(!Character.isDigit(ch) && digit != 0) {
                    if(isAdjacentToSymbol) {
                        total += digit;
                    }
                    digit = 0;
                    isAdjacentToSymbol = false;
                }
            }

            if(digit != 0 && isAdjacentToSymbol) {
                total += digit;
            }
        }

        return total;
    }

    public static long problem_p2(String [] inputs) {
        System.out.println("Part 2....");
        long total = 0;
        for(int i = 0; i < inputs.length; i++) {

            String line = inputs[i];
            for(int j = 0; j < line.length(); j++) {

                char ch = line.charAt(j);
                if(ch == '*') {
                    //System.out.println("Star Index: " + i + " " + j);
                    total += computeAdjacentNums(inputs, i, j);
                }
            }
        }

        return total;
    }

    public static boolean isAdjacentToSymbol(String [] inputs, int row, int col) {
        // dul
        if(row > 0 && col > 0) {
            char ch = inputs[row-1].charAt(col-1);
            if(!Character.isDigit(ch) && ch != '.') {
                //System.out.println("DUL Row: " + (row-1) + " Col: " + (col-1) + " Symbol: " + ch);
                return true;
            }
        }

        // u
        if( row > 0) {
            char ch = inputs[row-1].charAt(col);
            if(!Character.isDigit(ch) && ch != '.') {
                //System.out.println("U Row: " + (row-1) + " Col: " + (col) + " Symbol: " + ch);
                return true;
            }
        }

        // dur
        if(row > 0 && col < inputs[row].length() - 1) {
            char ch = inputs[row-1].charAt(col + 1);
            if(!Character.isDigit(ch) && ch != '.') {
                //System.out.println("DUL Row: " + (row-1) + " Col: " + (col+1) + " Symbol: " + ch);
                return true;
            }
        }

        // l
        if(col > 0) {
            char ch = inputs[row].charAt(col - 1);
            if(!Character.isDigit(ch) && ch != '.') {
                //System.out.println("L Row: " + (row) + " Col: " + (col-1) + " Symbol: " + ch);
                return true;
            }
        }

        // r
        if(col < inputs[row].length() - 1) {
            char ch = inputs[row].charAt(col + 1);
            if(!Character.isDigit(ch) && ch != '.') {
                //System.out.println("R Row: " + (row) + " Col: " + (col+1) + " Symbol: " + ch);
                return true;
            }
        }

        // ddl
        if(row < inputs[row].length() - 1 && col > 0) {
            char ch = inputs[row + 1].charAt(col - 1);
            if(!Character.isDigit(ch) && ch != '.') {
                //System.out.println("DDL Row: " + (row+1) + " Col: " + (col-1) + " Symbol: " + ch);
                return true;
            }
        }

        // d
        if(row < inputs[row].length() - 1) {
            char ch = inputs[row + 1].charAt(col);
            if(!Character.isDigit(ch) && ch != '.') {
                //System.out.println("D Row: " + (row+1) + " Col: " + (col) + " Symbol: " + ch);
                return true;
            }
        }

        // ddr
        if(row < inputs[row].length() - 1 && col < inputs[row].length() - 1) {
            char ch = inputs[row + 1].charAt(col + 1);
            if(!Character.isDigit(ch) && ch != '.') {
                //System.out.println("DDR Row: " + (row+1) + " Col: " + (col+1) + " Symbol: " + ch);
                return true;
            }
        }

        return false;
    }

    public static long getAdjacentNum(String [] inputs, int row, int col) {
        //System.out.println(row + " " + col);
        int minCol = 0;
        int maxCol = 0;

        int index = col;
        while(index >= 0 && Character.isDigit(inputs[row].charAt(index))) {
            minCol = index;
            index--;
        }

        index = col;
        while(index < inputs[row].length() && Character.isDigit(inputs[row].charAt(index))) {
            maxCol = index;
            index++;
        }

        long digit = 0;
        //System.out.println("Min: " + minCol + " Max: " + maxCol);
        for(int i = minCol; i <= maxCol; i++) {
            digit *= 10;
            digit += inputs[row].charAt(i) - '0';
        }

        //System.out.println(digit);
        return digit;
    }

    public static long computeAdjacentNums(String [] inputs, int row, int col) {

        Set<Long> adjNums = new HashSet<Long>();

        // dul
        if(row > 0 && col > 0 && Character.isDigit(inputs[row-1].charAt(col-1))) {
            adjNums.add(getAdjacentNum(inputs, row - 1, col - 1));
        }

        // u
        if( row > 0 && Character.isDigit(inputs[row-1].charAt(col))) {
            adjNums.add(getAdjacentNum(inputs, row - 1, col));
        }

        // dur
        if(row > 0 && col < inputs[row].length() - 1 && Character.isDigit(inputs[row-1].charAt(col+1))) {
            adjNums.add(getAdjacentNum(inputs, row - 1, col + 1));
        }

        // l
        if(col > 0) {
            char ch = inputs[row].charAt(col - 1);
            if(Character.isDigit(ch)) {
                adjNums.add(getAdjacentNum(inputs, row, col - 1));
            }
        }

        // r
        if(col < inputs[row].length() - 1) {
            char ch = inputs[row].charAt(col + 1);
            if(Character.isDigit(ch)) {
                adjNums.add(getAdjacentNum(inputs, row, col + 1));
            }
        }

        // ddl
        if(row < inputs[row].length() - 1 && col > 0 && Character.isDigit(inputs[row+1].charAt(col-1))) {
            adjNums.add(getAdjacentNum(inputs, row + 1, col - 1));
        }

        // d
        if(row < inputs[row].length() - 1 && Character.isDigit(inputs[row+1].charAt(col))) {
            adjNums.add(getAdjacentNum(inputs, row + 1, col));
        }

        // ddr
        if(row < inputs[row].length() - 1 && col < inputs[row].length() - 1 && Character.isDigit(inputs[row+1].charAt(col+1))) {
            adjNums.add(getAdjacentNum(inputs, row + 1, col + 1));
        }


        //System.out.println(adjNums.toString());
        if(adjNums.size() == 2) {
            List<Long> nums = new ArrayList<Long>(adjNums);
            return nums.get(0) * nums.get(1);
        }

        return 0;
    }
}