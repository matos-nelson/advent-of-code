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

        List<String> orderRules = new ArrayList<>();
        List<String> updates = new ArrayList<>();
        boolean isFirstSection = true;

        for(String input : inputs) {
            if(input.equals("")) {
                isFirstSection = false;
                continue;
            }

            if(isFirstSection) {
                orderRules.add(input);
            } else {
                updates.add(input);
            }
        }

        Map<String, Set<String>> map = new HashMap<>();
        for(String rule : orderRules) {
            String [] pages = rule.split("\\|");

            Set<String> set = map.getOrDefault(pages[0], new HashSet<>());
            set.add(pages[1]);
            map.put(pages[0], set);
        }

        long result = 0;
        for(String update : updates) {

            String [] pages = update.split(",");
            Set<String> seen = new HashSet<>();

            boolean isValidOrder = validate(pages, map);
            if(isValidOrder) {
                int middleIndex = pages.length / 2;
                result += Long.parseLong(pages[middleIndex]);
            }
        }

        return result;
    }

    static class PageDegree {
        private int degree;
        private String page;

        public PageDegree(String page, int degree) {
            this.degree = degree;
            this.page = page;
        }

        public int getDegree() {
            return this.degree;
        }

        public String getPage() {
            return this.page;
        }
    }

    public static String [] sort(String [] pages, Map<String, Set<String>> orderRules) {

        List<PageDegree> p = new ArrayList<>();
        for(int i = 0; i < pages.length; i++) {

            String page = pages[i];
            int degree = 0;
            Set<String> rules = orderRules.getOrDefault(page, new HashSet<>());
            for(int j = 0; j < pages.length; j++) {
                if(i != j) {
                    if(rules.contains(pages[j])) {
                        degree++;
                    }
                }
            }

            p.add(new PageDegree(page, degree));
        }

        Collections.sort(p, Comparator.comparing(PageDegree::getDegree, Collections.reverseOrder()));

        String [] result = new String[p.size()];
        for(int i = 0; i < p.size(); i++) {
            result[i] = p.get(i).getPage();
        }

        return result;
    }

    public static boolean validate(String [] pages, Map<String, Set<String>> orderRules) {

        Set<String> seen = new HashSet<>();
        for(String page : pages) {
            Set<String> order = orderRules.getOrDefault(page, new HashSet<>());

            boolean anyMatch = seen.stream().anyMatch(order::contains);
            if(anyMatch) {
                return false;
            }

            seen.add(page);
        }

        return true;
    }

    public static long problem_p2(String [] inputs) {
        System.out.println("Part 2....");

        List<String> orderRules = new ArrayList<>();
        List<String> updates = new ArrayList<>();
        boolean isFirstSection = true;

        for(String input : inputs) {
            if(input.equals("")) {
                isFirstSection = false;
                continue;
            }

            if(isFirstSection) {
                orderRules.add(input);
            } else {
                updates.add(input);
            }
        }

        Map<String, Set<String>> map = new HashMap<>();
        for(String rule : orderRules) {
            String [] pages = rule.split("\\|");

            Set<String> set = map.getOrDefault(pages[0], new HashSet<>());
            set.add(pages[1]);
            map.put(pages[0], set);
        }

        long result = 0;
        for(String update : updates) {

            String [] pages = update.split(",");
            Set<String> seen = new HashSet<>();
            boolean isValidOrder = validate(pages, map);

            if(!isValidOrder) {
                String [] p = sort(pages, map);
                int middleIndex = p.length / 2;
                result += Long.parseLong(p[middleIndex]);
            }
        }

        return result;
    }
}