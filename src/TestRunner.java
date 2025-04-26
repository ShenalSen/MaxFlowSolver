import java.io.File;
import java.io.IOException;

public class TestRunner {
    public static void main(String[] args) {
        System.out.println("==== MaxFlowSolver Test Suite ====\n");
        
        // Test directory
        String testDir = "tests";
        File directory = new File(testDir);
        
        // Create tests directory if it doesn't exist
        if (!directory.exists()) {
            directory.mkdir();
            System.out.println("Created tests directory. Please place test files there.");
            createSampleTests(testDir);
            System.out.println("Sample test files created in the tests directory.");
            return;
        }
        
        // Get all test files
        File[] testFiles = directory.listFiles((dir, name) -> name.endsWith(".txt"));
        
        if (testFiles == null || testFiles.length == 0) {
            System.out.println("No test files found in the tests directory.");
            createSampleTests(testDir);
            System.out.println("Sample test files created in the tests directory.");
            return;
        }
        
        // Run tests on each file
        for (File file : testFiles) {
            runTest(file.getPath());
        }
        
        System.out.println("\n==== All tests completed ====");
    }
    
    private static void runTest(String filePath) {
        System.out.println("\n---- Testing file: " + filePath + " ----");
        try {
            // Run the main algorithm with the test file
            String[] args = {filePath};
            Main.main(args);
        } catch (Exception e) {
            System.err.println("Error running test on " + filePath + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void createSampleTests(String testDir) {
        try {
            // Create sample test files
            createTestFile(testDir + "/small_graph.txt", 
                "6\n" +
                "0 1 16\n" +
                "0 2 13\n" +
                "1 2 10\n" +
                "1 3 12\n" +
                "2 1 4\n" +
                "2 4 14\n" +
                "3 2 9\n" +
                "3 5 20\n" +
                "4 3 7\n" +
                "4 5 4");
            
            createTestFile(testDir + "/small_graph_with_source_sink.txt", 
                "0 5\n" +
                "6\n" +
                "0 1 16\n" +
                "0 2 13\n" +
                "1 2 10\n" +
                "1 3 12\n" +
                "2 1 4\n" +
                "2 4 14\n" +
                "3 2 9\n" +
                "3 5 20\n" +
                "4 3 7\n" +
                "4 5 4");
            
            createTestFile(testDir + "/diamond_graph.txt", 
                "4\n" +
                "0 1 10\n" +
                "0 2 10\n" +
                "1 3 10\n" +
                "2 3 10");
            
            createTestFile(testDir + "/complex_graph.txt", 
                "8\n" +
                "0 1 10\n" +
                "0 2 5\n" +
                "0 3 15\n" +
                "1 4 9\n" +
                "1 5 15\n" +
                "2 5 8\n" +
                "2 6 5\n" +
                "3 6 10\n" +
                "4 7 10\n" +
                "5 7 15\n" +
                "6 7 10");
        } catch (IOException e) {
            System.err.println("Error creating test files: " + e.getMessage());
        }
    }
    
    private static void createTestFile(String path, String content) throws IOException {
        java.nio.file.Files.write(java.nio.file.Paths.get(path), content.getBytes());
    }
}