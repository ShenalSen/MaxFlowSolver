/**
 * Wrapper class that holds a flow graph along with optional source and sink nodes
 */
public class GraphWithSourceSink {
    private final Graph graph;
    private final int source;
    private final int sink;
    
    /**
     * Creates a new GraphWithSourceSink
     * 
     * @param graph The flow network graph
     * @param source The source node (-1 if not specified)
     * @param sink The sink node (-1 if not specified)
     */
    public GraphWithSourceSink(Graph graph, int source, int sink) {
        this.graph = graph;
        this.source = source;
        this.sink = sink;
    }
    
    /**
     * @return The underlying graph
     */
    public Graph getGraph() {
        return graph;
    }
    
    /**
     * @return The source node, or -1 if not specified
     */
    public int getSource() {
        return source;
    }
    
    /**
     * @return The sink node, or -1 if not specified
     */
    public int getSink() {
        return sink;
    }
    
    /**
     * @return Whether the source node was specified
     */
    public boolean hasSource() {
        return source != -1;
    }
    
    /**
     * @return Whether the sink node was specified
     */
    public boolean hasSink() {
        return sink != -1;
    }
}