import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * GMS
 * Created by karol on 04.11.2016.
 */
public class Euler {

    public static class GraphBuilder {
        private Graph graph;

        public Graph createGraph(String[] info, String data) {
            int numberOfVertices = Integer.parseInt(info[0].replaceAll("\\D+", ""));
            int numberOfNodes = Integer.parseInt(info[1].replaceAll("\\D+", ""));

            graph = new Graph(numberOfVertices);
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

            graph.addNode(from, to);
        }
    }

    public static class Graph {
        int[][] matrix;

        public Graph(int size) {
            matrix = new int[size][size];
        }

        public void addNode(int from, int to) {
            matrix[from][to] = 1;
            matrix[to][from] = 1;
        }

        public void removeNode(int from, int to) {
            matrix[from][to] = 0;
            matrix[to][from] = 0;
        }

        @Override
        public String toString() {
            String toReturn = "";
            for (int[] row : matrix) {
                for (int cell : row) {
                    toReturn += Integer.toString(cell) + " ";
                }
                toReturn += "\n";
            }
            return toReturn;
        }
    }

    public static class EulerSolution {
        private BufferedReader reader;
        private Graph graph;
        private List<Integer> vertices = new ArrayList<Integer>();
        private Stack<Integer> eulerCycleVertices = new Stack<Integer>();


        public EulerSolution() throws IOException {
//            reader = new BufferedReader(new InputStreamReader(System.in));
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/karol/Projekty/PG/GMS/GMS/Java/euler.txt"))));
            startTests();
        }

        private void startTests() throws IOException {
            int howManyTests = Integer.parseInt(reader.readLine());
            for (int i = 0; i < howManyTests; i++) {

                String[] info = reader.readLine().split(",");
                String data = reader.readLine();

                graph = new GraphBuilder().createGraph(info, data);
                System.out.println(getEulerCycle());
            }
        }

        private String getEulerCycle() {
            return "";
        }
    }

    public static void main(String[] args) throws IOException {
        new EulerSolution();
    }
}
