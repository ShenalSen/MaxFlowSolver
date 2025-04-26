public class Edge {
    private int source;
    private int destination;
    private int capacity;
    private int flow;

    public Edge(int source, int destination, int capacity) {
        this.source = source;
        this.destination = destination;
        this.capacity = capacity;
        this.flow = 0;
    }

    public int getSource() {
        return source;
    }

    public int getDestination() {
        return destination;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getFlow() {
        return flow;
    }

    public void setFlow(int flow) {
        this.flow = flow;
    }

    public int getResidualCapacity() {
        return capacity - flow;
    }

    @Override
    public String toString() {
        return "Edge{" + source + "->" + destination + ", capacity=" + capacity + ", flow=" + flow + '}';
    }
}
