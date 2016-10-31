import java.io.*;
import java.util.ArrayList;
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
            nodes.add(new Node(next, weight));
        }

        public List<Node> getNodes() {
            return nodes;
        }

        public int getIndex() {
            return index;
        }
    }

    public static class Node {
        private Vertex next;
        private int weight;

        public Node(Vertex next, int weight) {
            this.next = next;
            this.weight = weight;
        }

        public Vertex getNext() {
            return next;
        }

        public int getWeight() {
            return weight;
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

        private Vertex getVertex(int index) {
            return vertices[index];
        }
    }

    public static class GraphBuilder {
        private Graph graph;

        public Graph createGraph(String[] info, String data) {
            int numberOfVertices = Integer.parseInt(info[0].replaceAll("\\D+",""));
            int numberOfNodes = Integer.parseInt(info[1].replaceAll("\\D+",""));

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
    int totalMinimumWeight;

    public SpanningTree() throws IOException {
//        reader = new BufferedReader(new InputStreamReader(System.in));
        reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("spanningTreeTest.txt"))));
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
    }

    private Node getBestNode(Vertex vertex) {
        Node bestNode = null;
        for (Node currentNode : vertex.getNodes()) {
            if (bestNode == null || bestNode.getWeight() > currentNode.getWeight()) {
                bestNode = currentNode;
            }
        }
        return bestNode;
    }

    public static void main(String[] args) throws IOException {
        new SpanningTree();
    }
}
