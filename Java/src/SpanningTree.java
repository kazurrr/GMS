import java.io.*;
import java.util.*;

/**
 * http://eduinf.waw.pl/inf/alg/001_search/0141.php
 * Spanning tree
 * Algorithm: Kruskal
 *
 * eg input:
 * 2
 * n=6,m=9
 * {0,1}1 {0,5}3 {1,2}9 {1,3}7 {1,5}5 {2,3}8 {3,4}5 {3,5}2 {4,5}4
 * n=7,m=12
 * {0,1}2 {0,2}1 {0,3}2 {0,4}1 {0,5}2 {0,6}1 {1,2}4 {1,6}4 {2,3}3 {3,4}4 {4,5}6 {5,6}8
 */

public class SpanningTree { //ToDo when posting on SPOX change class name to Main
    public static class Vertex implements Comparable<Vertex> {
        public int index;

        public Vertex(int index) {
            this.index = index;
        }

        @Override
        public int compareTo(Vertex vertex) {
            return Double.compare(this.index, vertex.index);
        }
    }

    public static class Node implements Comparable<Node> {
        public Vertex from;
        public Vertex to;
        public int weight;

        public Node(Vertex from, Vertex to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int compareTo(Node o) {
            return Double.compare(this.weight, o.weight);
        }
    }

    public static class Graph {
        private int numberOfVertices;
        private int numberOfNodes;
        private Vertex[] vertices;
        private List<Node> nodes = new ArrayList<Node>();

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
            nodes.add(new Node(getVertex(from), getVertex(to), weight));
        }

        public Vertex getVertex(int index) {
            return vertices[index];
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

    public static class Vertices {
        public SortedSet<Vertex> vertices = new TreeSet<Vertex>();

        public void add(Vertex vertex) {
            vertices.add(vertex);
        }

        public void add(Vertices vertices) {
            this.vertices.addAll(vertices.vertices);
        }

        public boolean contains(Vertex vertex) {
            return vertices.contains(vertex);
        }
    }

    public static class SpanningTreeSolution {
        private BufferedReader reader;
        private Graph graph;
        private List<Node> allNodesSorted = new ArrayList<Node>();
        private List<Node> spanningTreeNodes = new ArrayList<Node>();
        private List<Vertices> verticesSet = new ArrayList<Vertices>();
        private int totalMinimumWeight = 0;

        public SpanningTreeSolution() throws IOException {
            reader = new BufferedReader(new InputStreamReader(System.in));
//        reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/karol/Projekty/PG/GMS/GMS/Java/spanningTreeTest.txt"))));
            startTests();
        }

        private void startTests() throws IOException {
            int howManyTests = Integer.parseInt(reader.readLine());
            for (int i = 0; i < howManyTests; i++) {
                cleanUp();
                String[] info = reader.readLine().split(",");
                String data = reader.readLine();

                graph = new GraphBuilder().createGraph(info, data);
                calculateMinimumSpanningTree();
            }
        }

        private void cleanUp() {
            allNodesSorted = new ArrayList<Node>();
            spanningTreeNodes = new ArrayList<Node>();
            verticesSet = new ArrayList<Vertices>();
            totalMinimumWeight = 0;
        }

        private void calculateMinimumSpanningTree() {
            getAllNodes();
            calculateNodes();
            System.out.println(totalMinimumWeight);
        }

        private void getAllNodes() {
            for (Node node : graph.nodes) {
                allNodesSorted.add(node);
            }
            Collections.sort(allNodesSorted);
        }

        private void calculateNodes() {
            for (Node node : allNodesSorted) {
                addNodeToSpanningTree(node);
            }
        }

        private void addNodeToSpanningTree(Node node) {
            if (doesNodeCreateCycle(node))
                return;

            if (getSetWithVertex(node.from) == null) {
                if (getSetWithVertex(node.to) == null) {
                    Vertices toAdd = new Vertices();
                    toAdd.add(node.from);
                    toAdd.add(node.to);
                    verticesSet.add(toAdd);
                    addNode(node);
                } else {
                    getSetWithVertex(node.to).add(node.from);
                    addNode(node);
                }
            } else {
                if (getSetWithVertex(node.to) == null) {
                    getSetWithVertex(node.from).add(node.to);
                    addNode(node);
                } else {
                    mergeTwoSets(node);
                    addNode(node);
                }
            }
        }

        private void mergeTwoSets(Node node) {
            Vertices setFrom = getSetWithVertex(node.from);
            Vertices setTo = getSetWithVertex(node.to);

            setFrom.add(setTo);
            verticesSet.remove(setTo);
        }

        private boolean doesNodeCreateCycle(Node node) {
            Vertices setToCheck = getSetWithVertex(node.from);

            if (setToCheck == null)
                return false;

            return setToCheck.contains(node.to);
        }

        private Vertices getSetWithVertex(Vertex vertex) {
            for (Vertices vertices : verticesSet) {
                if (vertices.vertices.contains(vertex))
                    return vertices;
            }
            return null;
        }

        private void addNode(Node node) {
            spanningTreeNodes.add(node);
            totalMinimumWeight += node.weight;
        }
    }


    public static void main(String[] args) throws IOException {
        new SpanningTreeSolution();
    }
}
