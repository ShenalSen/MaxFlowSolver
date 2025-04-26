// Main.java
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Error: Missing input file argument");
            System.out.println("Usage: java Main <input_file> [source] [sink]");
            System.out.println("If source and sink are not provided, they will be read from the file or you'll be prompted");
            return;
        }

        String inputFile = args[0];
        int source = -1;
        int sink = -1;

        // Check if source and sink are provided as command-line arguments
        if (args.length >= 3) {
            try {
                source = Integer.parseInt(args[1]);
                sink = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                System.err.println("Error: Source and sink must be integers");
                return;
            }
        }

        try {
            // Parse the input file and create the graph
            GraphWithSourceSink graphWithSourceSink = Parser.parseFile(inputFile);
            Graph graph = graphWithSourceSink.getGraph();
            
            if (graph.getNumNodes() < 2) {
                System.err.println("Error: Graph must have at least 2 nodes");
                return;
            }

            System.out.println("Input graph:");
            System.out.println(graph);

            // Command-line args have highest priority
            // If not provided, use ones from file
            // If still not available, prompt user
            if (source == -1 && graphWithSourceSink.hasSource()) {
                source = graphWithSourceSink.getSource();
            }

            if (sink == -1 && graphWithSourceSink.hasSink()) {
                sink = graphWithSourceSink.getSink();
            }

            // If source and sink still weren't provided, ask user
            if (source == -1 || sink == -1) {
                // Default values
                int defaultSource = 0;
                int defaultSink = graph.getNumNodes() - 1;
                
                Scanner scanner = new Scanner(System.in);
                
                try {
                    System.out.println("Nodes are numbered from 0 to " + (graph.getNumNodes() - 1));
                    
                    if (source == -1) {
                        System.out.print("Enter source node (default: " + defaultSource + "): ");
                        String input = scanner.nextLine().trim();
                        if (input.isEmpty()) {
                            source = defaultSource;
                        } else {
                            try {
                                source = Integer.parseInt(input);
                            } catch (NumberFormatException e) {
                                System.err.println("Invalid input. Using default source: " + defaultSource);
                                source = defaultSource;
                            }
                        }
                    }
                    
                    if (sink == -1) {
                        System.out.print("Enter sink node (default: " + defaultSink + "): ");
                        String input = scanner.nextLine().trim();
                        if (input.isEmpty()) {
                            sink = defaultSink;
                        } else {
                            try {
                                sink = Integer.parseInt(input);
                            } catch (NumberFormatException e) {
                                System.err.println("Invalid input. Using default sink: " + defaultSink);
                                sink = defaultSink;
                            }
                        }
                    }
                } finally {
                    scanner.close(); // Close scanner to avoid resource leak
                }
            }

            // Validate source and sink
            int numNodes = graph.getNumNodes();
            if (source < 0 || source >= numNodes) {
                System.err.println("Error: Invalid source node: " + source);
                return;
            }
            
            if (sink < 0 || sink >= numNodes) {
                System.err.println("Error: Invalid sink node: " + sink);
                return;
            }
            
            if (source == sink) {
                System.err.println("Error: Source and sink cannot be the same node");
                return;
            }

            System.out.println("Running max flow algorithm with source=" + source + " and sink=" + sink);

            // Solve the maximum flow problem
            MaxFlowSolver solver = new MaxFlowSolver(graph);
            try {
                int maxFlow = solver.findMaxFlow(source, sink);
                
                System.out.println("\nMaximum Flow: " + maxFlow);
                System.out.println("\nFlow details:");

                // Print the flow on each edge
                for (Edge edge : graph.getAllEdges()) {
                    System.out.println("Edge " + edge.getSource() + "->" + edge.getDestination() +
                            ": Flow = " + edge.getFlow() + " / Capacity = " + edge.getCapacity());
                }

                // Print steps for explanation
                System.out.println("\nAlgorithm steps:");
                for (String step : solver.getSteps()) {
                    System.out.println(step);
                }
            } catch (IllegalArgumentException e) {
                System.err.println("Error in maximum flow algorithm: " + e.getMessage());
            }

        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
