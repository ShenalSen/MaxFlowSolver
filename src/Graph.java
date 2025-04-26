import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private int numNodes;
    private Map<Integer, List<Edge>> adjacencyList;
    private List<Edge> edges;

    public Graph(int numNodes) {
        this.numNodes = numNodes;
        this.adjacencyList = new HashMap<>();
        this.edges = new ArrayList<>();

        // Initialize the adjacency list for each node
        for (int i = 0; i < numNodes; i++) {
            adjacencyList.put(i, new ArrayList<>());
        }
    }

    public void addEdge(int source, int destination, int capacity) {
        // Validate inputs
        if (source < 0 || source >= numNodes) {
            throw new IllegalArgumentException("Invalid source node: " + source);
        }
        if (destination < 0 || destination >= numNodes) {
            throw new IllegalArgumentException("Invalid destination node: " + destination);
        }
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity cannot be negative: " + capacity);
        }
        
        Edge edge = new Edge(source, destination, capacity);
        adjacencyList.get(source).add(edge);
        edges.add(edge);
    }

    public int getNumNodes() {
        return numNodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public List<Edge> getAdjacentEdges(int node) {
        return adjacencyList.get(node);
    }

    public List<Edge> getAllEdges() {
        return edges;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Graph with ").append(numNodes).append(" nodes and ").append(edges.size()).append(" edges:\n");

        for (Edge edge : edges) {
            sb.append(edge).append("\n");
        }

        return sb.toString();
    }
}
