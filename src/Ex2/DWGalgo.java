package Ex2;

import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.EdgeData;
import api.NodeData;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DWGalgo implements DirectedWeightedGraphAlgorithms {
    DirectedWeightedGraph graph;



    @Override
    public void init(DirectedWeightedGraph g) {
        this.graph = g;
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return this.graph;
    }

    @Override
    // testing pull with leead
    public DirectedWeightedGraph copy() {
        DirectedWeightedGraph copy_graph = new DWG();
        Iterator<NodeData> itr_node = this.graph.nodeIter();
        while(itr_node.hasNext()) {
            NodeData temp_node = new GNode(itr_node.next());
            copy_graph.addNode(temp_node);
        }
        Iterator<EdgeData> itr_edge = this.graph.edgeIter();
        while(itr_edge.hasNext()) {
            EdgeData temp_edge = new Edge(itr_edge.next());
            copy_graph.connect(temp_edge.getSrc(), temp_edge.getDest(), temp_edge.getWeight());
        }
        return copy_graph;
    }


    @Override
    public boolean isConnected() {
        // testing leead commit is gay
        int nodes = 0;
        Iterator<NodeData> itr_node = this.graph.nodeIter();
        while(itr_node.hasNext()){
            itr_node.next();
            nodes++;
        }
        for(int i = 0; i< nodes; i++){
            boolean[] visited = new boolean[nodes];
            DFS(this.graph, i, visited);
            for (boolean b: visited)
            {
                if (!b) {
                    return false;
                }
            }
        }
        return true;
        }

        // Function to perform DFS traversal on the graph on a graph
        public static void DFS(DirectedWeightedGraph graph, int v, boolean[] visited) {
            visited[v] = true;
            Iterator<EdgeData> itr_edge = graph.edgeIter(v);
            while (itr_edge.hasNext()) {
                EdgeData temp_edge = new Edge(itr_edge.next());
                if (!visited[temp_edge.getDest()]) {
                    DFS(graph, temp_edge.getDest(), visited);
                }
            }
        }




    @Override
    public double shortestPathDist(int src, int dest) {
        return find_shortestPathDist(src, dest, this.graph);
    }

    /** This function receives the src, dest and graph. It returns the shortest path between src and dest nodes.
     * Return: Double. */
    private double find_shortestPathDist(int src, int dest, DirectedWeightedGraph graph) {
        //finding the highest Node ID.
        int i, j;
        int max_id = 0;
        Iterator<NodeData> itr = graph.nodeIter();
        while (itr.hasNext()) {
            NodeData temp = itr.next();
            if (temp.getKey() > max_id)
                max_id = temp.getKey();
        }
        max_id += 1;

        //initializing the matrix:
        Iterator<NodeData> itr2 = graph.nodeIter();
        double[][] node_matrix = new double[max_id][max_id];
        for (i = 0; i < max_id; i++) {
            for (j = 0; j < max_id; j++) {
                try{
                    if(graph.getNode(i)==null || graph.getNode(j)==null){
                        node_matrix[i][j] = -1.0;
                        continue;
                    }
                }
                catch (NullPointerException e){
                    node_matrix[i][j] = -1.0;
                    continue;
                }

                //initializing the indexes that edges don't exist with MAX:
                try {
                    if (graph.getEdge(i, j) == null && graph.getNode(i)!=null && graph.getNode(j)!=null ) {
                        node_matrix[i][j] = 0.0;
                        continue;
                    }
                }
                catch (NullPointerException d){
                        node_matrix[i][j] = 0.0;
                        continue;
                }

                //initializing the main diagonal with zeros:
                if (i == j) { // same node.
                    node_matrix[i][j] = 0.0;
                    continue;
                }
                //else the edge exists,  initialize the index with its weight.
                node_matrix[i][j] = graph.getEdge(i, j).getWeight();
            }
        }
        System.out.println("Starting array: "+Arrays.deepToString(node_matrix));
        return warshall(src, dest, node_matrix);
    }

    /** This is the Floyed Warshall Algorith, function. It calculates the shortest path between two indexes.
     * Return: Double. */
    private double warshall(int src, int dest, double[][] node_matrix) {
        int len = node_matrix.length;
        for(int k=0; k<len; k++)
            for(int i=0; i<len; i++)
                for(int j=0; j<len; j++) {
                    if(node_matrix[i][j] == -1.0)
                        continue;
                    if (node_matrix[i][k] > 0.0 && node_matrix[k][j] > 0.0) {
                        if (node_matrix[i][j] == 0.0 && i != j) {
                            node_matrix[i][j] = node_matrix[i][k] + node_matrix[k][j];
                            continue;
                        }
                        if (node_matrix[i][k] + node_matrix[k][j] < node_matrix[i][j])
                            node_matrix[i][j] = node_matrix[i][k] + node_matrix[k][j];
                    }
                }

        System.out.println("Finish array: "+Arrays.deepToString(node_matrix));
        return node_matrix[src][dest];
    }


    @Override
    public List<NodeData> shortestPath ( int src, int dest){
        return null;
    }

    @Override
    public NodeData center () {
        return null;
    }

    @Override
    public List<NodeData> tsp (List < NodeData > cities) {
        return null;
    }

    @Override
    public boolean save (String file){
        return false;
    }

    @Override
    public boolean load (String file){
        return false;
    }
}