import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class TSHPATH {
    public static class Vertex implements Comparable<Vertex> {
        private double distance = Double.POSITIVE_INFINITY;
        private int index;
        private String city;
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

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        @Override
        public int compareTo(Vertex o) {
            return Double.compare(this.distance, o.distance);
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

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public int getWeight() {
            return weight;
        }

    }

    public static class Graph {
        private Vertex[] vertices;

        public Graph(int size) {
            vertices = new Vertex[size];
            createVertices();
        }

        public void createVertices() {
            for (int i = 0; i < vertices.length; i++) {
                vertices[i] = new Vertex(i + 1);
            }
        }

        public void addNode(int from, int to, String weight) {
            addNode(from, to, Integer.parseInt(weight));
        }

        public void addNode(int from, int to, int weight) {
            getVertex(from).addNode(getVertex(to), weight);
        }

        public void setCityName(int index, String name) {
            getVertex(index).setCity(name);
        }

        private Vertex getVertex(int index) {
            return vertices[index - 1];
        }

        private Vertex getVertex(String cityName) {
            for (Vertex vertex : vertices) {
                if (vertex.getCity().equals(cityName))
                    return vertex;
            }
            return null;
        }

        public void cleanGraphDistance() {
            for (Vertex vertex : vertices) {
                vertex.setDistance(Double.POSITIVE_INFINITY);
            }
        }
    }

    public static class Dijkstra {
        PriorityQueue<Vertex> vertexQueue;

        public int calculateDistance(Vertex from, Vertex to) {
            computeGraph(from);
            return (int) to.getDistance();
        }

        private void computeGraph(Vertex from) {
            from.setDistance(0.);
            vertexQueue = new PriorityQueue<Vertex>();
            vertexQueue.add(from);

            while (!vertexQueue.isEmpty()) {
                goThroughVertices();
            }
        }

        private void goThroughVertices() {
            Vertex currentVortex = vertexQueue.poll();

            for (Node node : currentVortex.getNodes()) {
                double weight = node.getWeight();
                double distanceViaCurrentVortex = currentVortex.getDistance() + weight;
                Vertex nextVortex = node.getNext();

                if (distanceViaCurrentVortex < nextVortex.getDistance()) {
                    vertexQueue.remove(nextVortex);

                    nextVortex.setDistance(distanceViaCurrentVortex);
                    vertexQueue.add(nextVortex);
                }
            }
        }
    }

    public static class GraphSolution {
        private Graph graph;
        private int graphSize = 0;
        BufferedReader reader;

        public GraphSolution() throws IOException {
            reader = new BufferedReader(new InputStreamReader(System.in));
//            reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("TSHPATH.txt"))));
            startTests();
        }

        private void startTests() throws IOException {
            int howManyTests = Integer.parseInt(reader.readLine());
            for (int i = 0; i < howManyTests; i++) {
                buildGraph();
                startQueries();
                reader.readLine(); //ToDo verify if this is necessary, read blank line between tests
                System.out.println();
            }
        }

        private void buildGraph() throws IOException {
            graphSize = Integer.parseInt(reader.readLine());
            graph = new Graph(graphSize);

            for (int i = 1; i <= graphSize; i++) {
                addCity(i);
            }
        }

        private void addCity(int currentCity) throws IOException {
            graph.setCityName(currentCity, reader.readLine());
            int numberOfNeighbors = Integer.parseInt(reader.readLine());

            for (int i = 1; i <= numberOfNeighbors; i++) {
                String[] neighbors = reader.readLine().split(" ");
                graph.addNode(currentCity, Integer.parseInt(neighbors[0]), neighbors[1]);
            }
        }

        private void startQueries() throws IOException {
            int numberOfQueries = Integer.parseInt(reader.readLine());
            Dijkstra dijkstra = new Dijkstra();

            for (int i = 0; i < numberOfQueries; i++) {
                graph.cleanGraphDistance();
                String[] route = reader.readLine().split(" ");
                Vertex from = graph.getVertex(route[0]);
                Vertex to = graph.getVertex(route[1]);

                System.out.println(dijkstra.calculateDistance(from, to));
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new GraphSolution();
    }
}
