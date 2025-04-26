import java.util.*;

public class MaxFlowSolver {
    private Graph graph;
    private List<String> steps; // To store steps for explanation

    public MaxFlowSolver(Graph graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        }
        this.graph = graph;
        this.steps = new ArrayList<>();
    }

    public int findMaxFlow(int source, int sink) {
        int numNodes = graph.getNumNodes();
        
        // Validate source and sink
        if (source < 0 || source >= numNodes) {
            throw new IllegalArgumentException("Invalid source node: " + source);
        }
        if (sink < 0 || sink >= numNodes) {
            throw new IllegalArgumentException("Invalid sink node: " + sink);
        }
        if (source == sink) {
            throw new IllegalArgumentException("Source and sink cannot be the same node");
        }
        
        // Create residual graph
        int[][] residualCapacity = new int[numNodes][numNodes];

        // Initialize residual capacities
        for (Edge edge : graph.getAllEdges()) {
            residualCapacity[edge.getSource()][edge.getDestination()] = edge.getCapacity();
        }

        // Store parent array for BFS path construction
        int[] parent = new int[numNodes];
        int maxFlow = 0;

        steps.add("Starting Edmonds-Karp algorithm from source " + source + " to sink " + sink);

        // Augment flow while there is a path from source to sink
        while (bfs(residualCapacity, source, sink, parent)) {
            // Find the maximum flow through the path found by BFS
            int pathFlow = Integer.MAX_VALUE;

            StringBuilder pathStr = new StringBuilder();
            pathStr.append("Found path: ");

            // Construct the augmenting path and find bottleneck capacity
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, residualCapacity[u][v]);
                pathStr.insert(11, "->" + v);

                if (v != sink) {
                    pathStr.insert(11, " ");
                }
            }
            pathStr.insert(11, source);

            steps.add(pathStr + " with bottleneck flow: " + pathFlow);

            // Update residual capacities of the edges and reverse edges
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                residualCapacity[u][v] -= pathFlow;
                residualCapacity[v][u] += pathFlow; // For the reverse edge
            }

            // Add path flow to overall flow
            maxFlow += pathFlow;
            steps.add("Increased max flow to: " + maxFlow);
        }

        // Update the flow values in the original graph edges
        updateOriginalGraphFlows(residualCapacity);

        return maxFlow;
    }

    // Use BFS to find an augmenting path
    private boolean bfs(int[][] residualCapacity, int source, int sink, int[] parent) {
        int numNodes = graph.getNumNodes();
        boolean[] visited = new boolean[numNodes];
        Arrays.fill(visited, false);

        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited[source] = true;
        parent[source] = -1;

        while (!queue.isEmpty()) {
            int u = queue.poll();

            // Explore all adjacent vertices
            for (int v = 0; v < numNodes; v++) {
                // If there's available capacity and vertex not visited yet
                if (!visited[v] && residualCapacity[u][v] > 0) {
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }

        // If we reached sink in BFS, then there is a path
        return visited[sink];
    }

    // Update the flow values in the original graph based on the residual graph
    private void updateOriginalGraphFlows(int[][] residualCapacity) {
        for (Edge edge : graph.getAllEdges()) {
            int u = edge.getSource();
            int v = edge.getDestination();
            int capacity = edge.getCapacity();

            // The flow is the capacity minus the residual capacity
            edge.setFlow(capacity - residualCapacity[u][v]);
        }
    }

    public List<String> getSteps() {
        return steps;
    }
}
