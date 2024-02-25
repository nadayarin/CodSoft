import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class task2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("***Enter text or provide the path to a file: ");
        String userInput = scanner.nextLine();

        if (isFilePath(userInput)) {
            // if it's a file
            File file = new File(userInput);
            if (!file.exists() || !file.isFile()) {
                System.out.println("The specified file does not exist or is invalid._.");
                return;
            }
            Map<String, Integer> wordOccurrences = countWordsInFile(file);
            System.out.println("Total number of words in this file:" + wordOccurrences.size());
            System.out.println("\nOccurrence of each word==>");
            for (Map.Entry<String, Integer> entry : wordOccurrences.entrySet()) {
                System.out.println(entry.getKey() + " = " + entry.getValue());
            }
        } else {
            // if it's only a text
            Map<String, Integer> wordOccurrences = countWords(userInput);
            System.out.println("Total number of words  : " + wordOccurrences.size());
            System.out.println("\nOccurrence of each word==>");
            for (Map.Entry<String, Integer> entry : wordOccurrences.entrySet()) {
                System.out.println(entry.getKey() + " = " + entry.getValue());
            }
        }
    }

    private static boolean isFilePath(String userInput) {
        // check if it contains a path separator character
        return userInput.contains("/") || userInput.contains("\\");
    }

    private static Map<String, Integer> countWords(String text) {
        String[] words = text.split("\\s+|\\p{Punct}");
        Map<String, Integer> wordOccurrences = new HashMap<>();
        for (String word : words) {
            if (!word.isEmpty()) {
                wordOccurrences.put(word.toLowerCase(), wordOccurrences.getOrDefault(word.toLowerCase(), 0) + 1);
            }
        }
        return wordOccurrences;
    }

    private static Map<String, Integer> countWordsInFile(File file) {
        Map<String, Integer> wordOccurrences = new HashMap<>();
        try {
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                String[] words = line.split("\\s+");
                for (String word : words) {
                    if (!word.isEmpty()) {
                        wordOccurrences.put(word.toLowerCase(), wordOccurrences.getOrDefault(word.toLowerCase(), 0) + 1);
                    }
                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error in reading this file: " + e.getMessage());
        }
        return wordOccurrences;
    }
}