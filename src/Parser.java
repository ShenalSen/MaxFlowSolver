import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Parser {

    /**
     * Parse an input file and create a flow network Graph with optional source and sink
     *
     * @param filePath path to the input file
     * @return GraphWithSourceSink containing the graph and optional source/sink nodes
     * @throws IOException if file cannot be read
     */
    public static GraphWithSourceSink parseFile(String filePath) throws IOException {
        // Verify file exists
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            throw new IOException("File does not exist: " + filePath);
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Read the number of nodes
            String firstLine = reader.readLine();
            if (firstLine == null) {
                throw new IOException("Empty file");
            }

            // Check if the first line contains source and sink information
            String[] parts = firstLine.trim().split("\\s+");
            int sourceNode = -1;
            int sinkNode = -1;
            int lineToParseForNumNodes = 1;

            if (parts.length == 2) {
                // First line contains source and sink
                try {
                    sourceNode = Integer.parseInt(parts[0]);
                    sinkNode = Integer.parseInt(parts[1]);
                    // Read next line for number of nodes
                    firstLine = reader.readLine();
                    if (firstLine == null) {
                        throw new IOException("Incomplete file");
                    }
                    lineToParseForNumNodes = 2; // We've now read 2 lines
                } catch (NumberFormatException e) {
                    // If not valid integers, assume first line is number of nodes
                    sourceNode = -1;
                    sinkNode = -1;
                    lineToParseForNumNodes = 1;
                }
            }

            // Parse the number of nodes
            int numNodes;
            try {
                numNodes = Integer.parseInt(firstLine.trim());
                if (numNodes <= 0) {
                    throw new IOException("Invalid number of nodes: must be positive");
                }
            } catch (NumberFormatException e) {
                throw new IOException("Invalid format for number of nodes: " + firstLine);
            }
            
            Graph graph = new Graph(numNodes);
            
            String line;
            int lineNumber = lineToParseForNumNodes + 1; // Start at next line after nodes count
            // Read each edge
            while ((line = reader.readLine()) != null) {
                // Skip empty lines
                if (line.trim().isEmpty()) {
                    lineNumber++;
                    continue;
                }

                String[] edgeParts = line.trim().split("\\s+");
                if (edgeParts.length != 3) {
                    throw new IOException("Invalid edge format at line " + lineNumber + 
                                         ". Expected 'source destination capacity'");
                }

                try {
                    int source = Integer.parseInt(edgeParts[0]);
                    int destination = Integer.parseInt(edgeParts[1]);
                    int capacity = Integer.parseInt(edgeParts[2]);
                    
                    // Validate node indices
                    if (source < 0 || source >= numNodes) {
                        throw new IOException("Invalid source node at line " + lineNumber + 
                                             ": " + source + " (must be between 0 and " + (numNodes-1) + ")");
                    }
                    if (destination < 0 || destination >= numNodes) {
                        throw new IOException("Invalid destination node at line " + lineNumber + 
                                             ": " + destination + " (must be between 0 and " + (numNodes-1) + ")");
                    }
                    if (capacity < 0) {
                        throw new IOException("Negative capacity at line " + lineNumber + ": " + capacity);
                    }
                    
                    graph.addEdge(source, destination, capacity);
                } catch (NumberFormatException e) {
                    throw new IOException("Invalid number format at line " + lineNumber + ": " + line);
                }
                
                lineNumber++;
            }

            // Then return a structure that includes source and sink if provided
            GraphWithSourceSink result = new GraphWithSourceSink(graph, sourceNode, sinkNode);
            return result; // Return just the graph
        }
    }
}
