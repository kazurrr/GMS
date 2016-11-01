import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//ToDo implement this http://eduinf.waw.pl/inf/alg/001_search/0141.php

public class SpanningTree {
    public static class Vertex {
        private int index;
        private List<Node> nodes = new ArrayList<Node>();

        public Vertex(int index) {
            this.index = index;
        }

        public void addNode(Vertex next, int weight) {
            nodes.add(new Node(this, next, weight));
        }

        public List<Node> getNodes() {
            return nodes;
        }

        public int getIndex() {
            return index;
        }
    }

    public static class Node implements Comparable<Node> {
        private Vertex from;
        private Vertex to;
        private int weight;

        public Node(Vertex from, Vertex to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        public Vertex getTo() {
            return to;
        }

        public Vertex getFrom() {
            return from;
        }

        public int getWeight() {
            return weight;
        }

        @Override
        public int compareTo(Node o) {
            return Double.compare(this.getWeight(), o.getWeight());
        }
    }

    public static class Graph {
        private int numberOfVertices;
        private int numberOfNodes;
        private Vertex[] vertices;

        public Graph(int numberOfVertices, int numberOfNodes) {
            this.numberOfVertices = numberOfVertices;
            this.numberOfNodes = numberOfNodes;
            vertices = new Vertex[numberOfVertices];
            createVertices();
        }

        public void createVertices() {
            for (int i = 0; i < vertices.length; i++) {
                vertices[i] = new Vertex(i);
            }
        }

        public void addNode(int from, int to, int weight) {
            getVertex(from).addNode(getVertex(to), weight);
        }

        public Vertex getVertex(int index) {
            return vertices[index];
        }

        private Vertex[] getVertices() {
            return vertices;
        }
    }

    public static class GraphBuilder {
        private Graph graph;

        public Graph createGraph(String[] info, String data) {
            int numberOfVertices = Integer.parseInt(info[0].replaceAll("\\D+", ""));
            int numberOfNodes = Integer.parseInt(info[1].replaceAll("\\D+", ""));

            graph = new Graph(numberOfVertices, numberOfNodes);
            addNodes(data);
            return graph;
        }

        private void addNodes(String data) {
            String[] verticesToAdd = data.split(" ");
            for (String vertex : verticesToAdd) {
                addNode(vertex);
            }
        }

        private void addNode(String vertex) {
            int from = Integer.parseInt(vertex.substring(1, vertex.lastIndexOf("}")).split(",")[0]);
            int to = Integer.parseInt(vertex.substring(1, vertex.lastIndexOf("}")).split(",")[1]);
            int weight = Integer.parseInt(vertex.substring(vertex.lastIndexOf("}") + 1));

            graph.addNode(from, to, weight);
        }
    }

    private BufferedReader reader;
    private Graph graph;
    private List<Node> allNodesSorted = new ArrayList<Node>();
    private List<Node> spanningTreeNodes = new ArrayList<Node>();
    private List<Vertex> spanningTreeVertices = new ArrayList<Vertex>();
    int totalMinimumWeight;

    public SpanningTree() throws IOException {
//        reader = new BufferedReader(new InputStreamReader(System.in));
        reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/karol/Projekty/PG/GMS/GMS/Java/spanningTreeTest.txt"))));
        startTests();
    }

    private void startTests() throws IOException {
        int howManyTests = Integer.parseInt(reader.readLine());
        for (int i = 0; i < howManyTests; i++) {
            String[] info = reader.readLine().split(",");
            String data = reader.readLine();

            graph = new GraphBuilder().createGraph(info, data);
            calculateMinimumSpanningTree();
        }
    }

    private void calculateMinimumSpanningTree() {
        Vertex startVertex = graph.getVertex(0);
        getAllNodes();
        calculateNodes();
    }

    private void getAllNodes() {
        for (Vertex vertex : graph.getVertices()) {
            for (Node node : vertex.getNodes()) {
                allNodesSorted.add(node);
            }
        }
        Collections.sort(allNodesSorted);
    }

    private void calculateNodes() {
        for (Node node : allNodesSorted) {
            if (nodeCanBeAdded(node)) {
                addNodeToSpanningTree(node);
            }
        }
    }

    private boolean nodeCanBeAdded(Node node) {
        if (spanningTreeVertices.contains(node.from) && spanningTreeNodes.contains(node.to)) {
            return false;
        }
        return true;
    }

    private void addNodeToSpanningTree(Node node) {
        spanningTreeVertices.add(node.from);
        spanningTreeVertices.add(node.to);
        spanningTreeNodes.add(node);
    }

    public static void main(String[] args) throws IOException {
        new SpanningTree();
    }
}
