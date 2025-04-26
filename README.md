# MaxFlowSolver

A Java implementation of the Edmonds-Karp algorithm for solving the maximum flow problem in network graphs.

## Overview

This project implements the Edmonds-Karp algorithm, which is an efficient implementation of the Ford-Fulkerson method using breadth-first search to find augmenting paths. The algorithm determines the maximum amount of flow that can be sent through a flow network from a source node to a sink node.

## Features

- **Flexible Input Options**: Read graphs from text files or specify parameters via command line
- **Multiple Source/Sink Specification Methods**:
  - Command-line arguments
  - First line in input file
  - Interactive prompts with defaults
- **Detailed Output**:
  - Maximum flow value
  - Flow distribution across edges
  - Step-by-step algorithm execution
- **Thorough Error Handling**:
  - Input validation
  - Detailed error messages with line numbers
  - Resource management
- **Comprehensive Testing**:
  - Test suite for verifying algorithm correctness
  - Multiple test cases covering different graph structures

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher

### Compilation

```bash
javac -d out src/*.java
```

### Running the Program

```bash
java -cp out Main <input_file> [source] [sink]
```

Where:
- `<input_file>`: Path to the graph definition file
- `[source]`: Optional source node index (default is 0)
- `[sink]`: Optional sink node index (default is the last node)

### Running the Test Suite

```bash
java -cp out TestRunner
```

This will create sample test files in a tests directory and run the algorithm on each test case.

## Input File Format

There are two supported input file formats:

### Format 1: Without Source/Sink Specification

```
<number_of_nodes>
<source_node> <destination_node> <capacity>
<source_node> <destination_node> <capacity>
...
```

### Format 2: With Source/Sink Specification

```
<source_node> <sink_node>
<number_of_nodes>
<source_node> <destination_node> <capacity>
<source_node> <destination_node> <capacity>
...
```

## Example

For a graph with 4 nodes (diamond structure):

```
4
0 1 10
0 2 10
1 3 10
2 3 10
```

Expected output:
```
Maximum Flow: 20

Flow details:
Edge 0->1: Flow = 10 / Capacity = 10
Edge 0->2: Flow = 10 / Capacity = 10
Edge 1->3: Flow = 10 / Capacity = 10
Edge 2->3: Flow = 10 / Capacity = 10

Algorithm steps:
Starting Edmonds-Karp algorithm from source 0 to sink 3
Found path:0 ->1->3  with bottleneck flow: 10
Increased max flow to: 10
Found path:0 ->2->3  with bottleneck flow: 10
Increased max flow to: 20
```

## Algorithm Details

The Edmonds-Karp algorithm is an implementation of the Ford-Fulkerson method that:

1. Uses BFS to find augmenting paths (ensuring shortest paths are found first)
2. Creates a residual graph to track remaining capacities
3. Finds augmenting paths and increases flow until no more paths exist
4. Has a time complexity of O(VE²), where V is the number of vertices and E is the number of edges

## Project Structure

- `Main.java`: Entry point and user interaction
- `Graph.java`: Graph data structure implementation
- `Edge.java`: Edge representation with flow and capacity
- `Parser.java`: Handles input file parsing
- `MaxFlowSolver.java`: Implements the Edmonds-Karp algorithm
- `GraphWithSourceSink.java`: Wrapper for graph with source/sink information
- `TestRunner.java`: Test suite for verifying algorithm correctness

## License

This project is available under the MIT License.

## Acknowledgments

- Implementation based on the Edmonds-Karp algorithm as described in *Introduction to Algorithms* by Cormen, Leiserson, Rivest, and Stein.
