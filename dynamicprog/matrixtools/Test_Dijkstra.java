package prog2;

import java.io.*;
import java.util.Iterator;

/**
 * Dijkstra path algs
 *
 * @author Josh Cotes
 */
public class Test_Dijkstra {

    static boolean [][] TC_matrix;

    public static void main(String[] args) {

        int start_vertex; // file parameter 2 (args[1])
        Adjacency_List[] adjacency_list;  // an adjacency list

        if (args.length != 2)
            usage();
        else {
            try {
                adjacency_list = create_adjacency_list(args[0]); // create adjacency list from file
                start_vertex = Integer.parseInt(args[1]); // start vertex from main parameter
                TC_matrix = new boolean[adjacency_list.length][adjacency_list.length];
                dijkstraWithClosure(adjacency_list, start_vertex);

                for(boolean[] B : TC_matrix){
                    for(boolean b : B){
                        System.out.print(b + " ");
                    }
                    System.out.println();
                }

            } catch (IOException e) {
                usage();
            } catch (ArrayIndexOutOfBoundsException g) {
                System.out.println("Start vertex '" + args[1] + "' does not exist.");
            } catch (Exception f) {
                System.out.println("File contents are wrong format.");
            }
        }
    }

    /**
     * Calculates shortest distances using an array of adjacency list and Dijkstra's algorithm
     *
     * @param adjacency_list - array of adjacency lists
     * @param start - start point
     * @return
     */
    private static MinHeap dijkstraWithClosure(Adjacency_List[] adjacency_list, int start) {

        Integer infty = Integer.MAX_VALUE;

        MinHeap Q, S, TC; // heaps
        Vertex u;  // holds the current vertex
        Iterator<Edge> adj_u;  // Linked list edge iterator  - @see MinHeap.edge_iterator()
        Edge edge; // holds the current edge
        Vertex adj_v; // holds an adjacent vertex
        Vertex start_vertex; // the starting point from @param - start

        S = new MinHeap(adjacency_list.length); // shortest path of vertices and weights
        Q = new MinHeap(adjacency_list.length); // all nodes in the graph

        start_vertex = adjacency_list[start].subject_vertex; // start point
        start_vertex.weight = 0; // start weight to zero so it gets picked first


        for (Adjacency_List A : adjacency_list) {
            if(!A.subject_vertex.equals(start_vertex))
                A.subject_vertex.weight = infty;
            Q.push(A.subject_vertex);
        }

        while (!Q.isEmpty()) { // visit every node   // - - - - - - O(V)
;
            u = Q.pop(); // O(log V)
            S.push(u); // O(log V)
            TC_matrix[u.id][u.id] = true;

            adj_u = adjacency_list[u.id].edge_iterator(); // current vertices adjacency list

            while (adj_u.hasNext()) {  // for each v in Adj[u]   // - - - - O(E)                    O(EVlogV)
;
                edge = adj_u.next(); // next edge in Adj[u]
                adj_v = edge.vertex;  // next edge vertex in Adj[u]

                if (u.weight != infty && adj_v.weight > u.weight + edge.edge_weight && !S.contains(adj_v)) {
                    Q.decreaseKey(adj_v.location, u.weight + edge.edge_weight);
                    adj_v.previous_hop = u; // set neighbor pre-hop to subject vertex 'u'
                    TC_matrix[start][adj_v.id] = true; // build the transitive closure list for start node
                }
            }
            adjacency_list[u.id].edge_iterator = null; // reset the iterator
        }
        if(start < adjacency_list.length-1)
            dijkstraWithClosure(adjacency_list, start + 1);

        return S;
    }




    /**
     * Displays the shortest path of the start vertex to every other vertex in the graph.
     *
     * @param A     -  The adjacency list with nodes and distances
     * @param start -  The integer id of the starting vertex
     */
    private static void display(Adjacency_List[] A, int start) {

        for (Adjacency_List a : A) {
            if (a.subject_vertex.id != start) {
                System.out.print("[" + a.subject_vertex.id + "]");
                if (a.subject_vertex.weight == Integer.MAX_VALUE)
                    System.out.println("unreachable");
                else {
                    System.out.print("shortest path:(");
                    print_hops(a.subject_vertex);
                    System.out.println(") shortest distance:" + a.subject_vertex.weight);
                }
            }
        }
    }

    /**
     * A recursive method to print the hops to a node in the correct order
     *
     * @param current -  the node to print hops to
     */
    public static void print_hops(Vertex current) {

        if (current.previous_hop == null) {
            System.out.print(current.id);
            return;
        }
        print_hops(current.previous_hop);
        System.out.print("," + current.id);
    }

    /**
     * Builds a MinHeap containing the shortest path from the start vertex to every other vertex in the
     * graph. Returns a shortest path heap with all vertices, weights, and previous hops.
     *
     * @param adjacency_list - the adjacency list of a graph
     * @param start          - the id of the node of where to start the algorithm
     * @return - the shortest path with vertices and weights in a MinHeap data structure
     */
    private static MinHeap dijkstra(Adjacency_List[] adjacency_list, int start) {

        MinHeap Q, S; // heaps
        Vertex u;  // holds the current vertex
        Iterator<Edge> adj_u;  // Linked list edge iterator  - @see MinHeap.edge_iterator()
        Edge edge; // holds the current edge
        Vertex adj_v; // holds an adjacent vertex
        Vertex start_vertex; // the starting point from @param - start

        S = new MinHeap(adjacency_list.length); // shortest path of vertices and weights
        Q = new MinHeap(adjacency_list.length); // all nodes in the graph

        start_vertex = adjacency_list[start].subject_vertex; // start point
        start_vertex.weight = 0; // start weight to zero so it gets picked first


        // build a MinHeap containing every vertex, each having weight of Integer.MAX_VALUE
        for (Adjacency_List A : adjacency_list) {
            Q.push(A.subject_vertex);

        }

        while (!Q.isEmpty()) { // visit every node

            u = Q.pop(); // turn node black  // pop always gets shortest distance vertex
            S.push(u); // add to final heap
            adj_u = adjacency_list[u.id].edge_iterator(); // current vertices adjacency list

            while (adj_u.hasNext()) {  // for each v in Adj[u]

                edge = adj_u.next(); // next edge in Adj[u]
                adj_v = edge.vertex;  // next edge vertex in Adj[u]

                if (u.weight != Integer.MAX_VALUE && !S.contains(adj_v) && adj_v.weight > u.weight + edge.edge_weight) {  // if (subject vertex 'u' weight + edge to neighbor weight) is less than neighbor node weight
                    Q.decreaseKey(adj_v.location, u.weight + edge.edge_weight); // set neighbor weight to edge + subject vertex 'u' weight
                    adj_v.previous_hop = u; // set neighbor pre-hop to subject vertex 'u'
                }
            }
            adjacency_list[u.id].edge_iterator = null; // reset the iterator
        }
        return S;
    }

    /**
     * debugging method to print the adjacency list
     *
     * @param adjacency_list -  the adjacency list to print
     */
    private static void print_list(Adjacency_List[] adjacency_list) {
        for (Adjacency_List A : adjacency_list) {
            Iterator<Edge> it = A.edge_iterator();
            System.out.print("Vertex: [" + A.subject_vertex.id + "] ");

            while (it.hasNext()) {
                Edge next = it.next();
                System.out.print("[" + next.vertex.id + ", " + next.edge_weight + "] ");
            }
            System.out.println();
            A.edge_iterator = null;
        }
    }

    /**
     * reads and parses a specified file and uses the data to create an Adjacency_List[]
     *
     * @param fname - the file name
     * @return - - a new adjacency list with the specifications from the graph file
     * @throws FileNotFoundException
     * @throws IOException
     */
    private static Adjacency_List[] create_adjacency_list(String fname) throws FileNotFoundException, IOException {

        BufferedReader reader = new BufferedReader(new FileReader(new File(fname)));

        String buffer;
        int vertex_count = 0;
        buffer = reader.readLine();

        // count the vertices
        while (buffer != null) {
            if (!buffer.equals(" ") && !buffer.equals(""))
                vertex_count++;
            buffer = reader.readLine();
        }
        reader.close();

        Adjacency_List[] adjacency_list = new Adjacency_List[vertex_count];

        reader = new BufferedReader(new FileReader(new File(fname)));
        buffer = reader.readLine();

        // create vertices and give each adjacency_list[] location a subject node
        while (buffer != null) {
            if (!buffer.equals(" ") && !buffer.equals("")) {
                String[] first_split = buffer.split(":"); // splits line into vertex : adjacent nodes
                int vertex_id = Integer.parseInt(first_split[0]); // get the number before the ':', the vertex id
                Vertex vertex = new Vertex(vertex_id);
                adjacency_list[vertex_id] = new Adjacency_List(vertex);
            }
            buffer = reader.readLine();
        }
        reader.close();

        reader = new BufferedReader(new FileReader(new File(fname)));
        buffer = reader.readLine();

        // create the adjacency list for the vertices
        while (buffer != null) {
            if (!buffer.equals(" ") && !buffer.equals("")) {
                String[] first_split = buffer.split(":"); // splits line into vertex : adjacent nodes
                int vertex_id = Integer.parseInt(first_split[0]); // get the number before the ':', the vertex id

                if (first_split.length > 1) {
                    String[] second_split = first_split[1].split(";"); // get each adjacent nodes and edge length

                    for (String S : second_split) { // visit each node/edge length and adds it

                        String[] vertex_and_edge = S.split(","); // splits into

                        int adjacent_vertex_id = Integer.parseInt(vertex_and_edge[0]); // gets the vertex id
                        int adjacent_edge_length = Integer.parseInt(vertex_and_edge[1]); // gets its edge length
                        Vertex adjacent_vertex = adjacency_list[adjacent_vertex_id].subject_vertex; // gets the adjacent vertex
                        Edge edge = new Edge(adjacent_vertex, adjacent_edge_length); // creates new Adjacency_node with vertex and edge length
                        adjacency_list[vertex_id].add(edge); // adds the new Edge to current vertices Adjacency_List
                    }
                }
            }
            buffer = reader.readLine();
        }

        reader.close();
        return adjacency_list;
    }

    /**
     * command line usage statement
     */
    private static void usage() {
        System.out.println("usage: java Test_Dijkstra 'filename' 'vertex number'");
    }
}

/**
 * A structure describing a single vertex containing integer values: id and weight.
 */
class Vertex {

    public int id;
    public int weight;
    public int location;
    public Vertex previous_hop;

    public Vertex(int id) {
        this.id = id;
        this.weight = Integer.MAX_VALUE;
        this.location = -1;
    }

}

/**
 * Provides the structure of an Edge held by the Adjacency_List.
 * Has a Vertex and an integer value of its edge length to the subject node.
 */
class Edge {

    Vertex vertex;
    int edge_weight;
    Edge next;
    Edge previous;

    public Edge(Vertex vertex, int edge_weight) {
        this.vertex = vertex;
        this.edge_weight = edge_weight;
        next = null;
        previous = null;
    }

    public Edge() {
        vertex = null;
        edge_weight = 0;
        next = null;
        previous = null;
    }
}

/**
 * Provides the structure of an Adjacency_List using a linked-list data structure.
 * Has a reference to a subject node and methods to support the linked list of
 * Adjacency_Nodes.
 */
class Adjacency_List {

    Vertex subject_vertex;
    Edge head;
    public Iterator<Edge> edge_iterator;

    public Adjacency_List(Vertex subject_vertex) {
        this.subject_vertex = subject_vertex;
        this.head = null;
        edge_iterator = null;
    }

    /**
     * an iterator to move across this nodes adjacency list
     *
     * @return - the iterator for this list
     */
    public Iterator<Edge> edge_iterator() {

        if (edge_iterator == null) {

            edge_iterator = new Iterator<Edge>() {

                Edge this_node;
                boolean sw = false;

                @Override
                public boolean hasNext() {
                    if (sw == false) {
                        return head != null;
                    }
                    return this_node.next != null;
                }

                @Override
                public Edge next() {
                    if (sw == false) {
                        sw = true;
                        this_node = head;
                        return this_node;
                    }
                    if (this_node.next == null) {
                        return null;
                    } else {
                        this_node = this_node.next;
                        return this_node;
                    }
                }
            };
        }
        return edge_iterator;
    }

    /**
     * Stores Adjacency_Nodes in the list in order of their edge weight
     *
     * @param edge - the new adjacency node
     */
    public void add(Edge edge) {

        if (head == null)
            head = edge;

        else if (edge.edge_weight < head.edge_weight) {
            edge.next = head;
            head = edge;
        } else {
            Edge node = head;
            while (node != edge) {
                if (node.next == null) {
                    node.next = edge;
                } else if (edge.edge_weight < node.next.edge_weight) {
                    edge.next = node.next;
                    node.next = edge;
                }
                node = node.next;
            }
        }
    }
}

/**
 * Provides the structure of a minheap that contains a tree of
 * vertices, their weights, and their locations in the heap.
 */
class MinHeap {

    public Vertex[] heap;
    private int size;
    private int maxSize;
    private int minIndex;
    private int arrayLength;

    public MinHeap(int maxSize) {
        this.maxSize = maxSize;
        this.heap = new Vertex[maxSize];
        this.size = 0;
        minIndex = 0;
        arrayLength = 0;
    }

    public int push(Vertex new_vertex) {
        size++;
        int index = size - 1;
        heap[index] = new_vertex;
        heap[index].location = index;
        while (index > 0 && heap[parent(index)].weight > heap[index].weight) {
            swap(parent(index), index);
            index = parent(index);
        }
        return index;
    }

    public void remove(int index) {

        if (index >= size) return;
        swap(index, size - 1);
        heap[size - 1] = null;
        size--;
        minHeapify(index);

    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Vertex pop() {
        Vertex root = heap[0];
        remove(0);
        return root;
    }

    public Vertex peek() {
        return heap[0];
    }

    public boolean contains(Vertex v) {
        return contains(v, 0);
    }

    public boolean contains(Vertex v, int index) {

        int left = leftChild(index + 1) - 1;
        int right = rightchild(index + 1) - 1;

        if (index >= size)
            return false;
        if (heap[index].equals(v))
            return true;

        if (contains(v, left))
            return true;

        else if (contains(v, right))
            return true;

        else
            return false;

    }

    public void buildMinHeap() {

        for (int i = size / 2; i >= 0; i--)
            minHeapify(i);
    }

    public void decreaseKey(int index, int value) {

        if (value >= heap[index].weight) {
            return;
        }
        heap[index].weight = value;
        while (index > 0 && heap[parent(index)].weight > heap[index].weight) {
            swap(index, parent(index));
            index = parent(index);
        }
    }

    public void minHeapify(int index) {

        int left = leftChild(index + 1) - 1;
        int right = rightchild(index + 1) - 1;
        int smallest;

        if (left < size &&
                heap[index].weight >= heap[left].weight)
            smallest = left;
        else
            smallest = index;

        if (right < size &&
                heap[index].weight >= heap[right].weight && heap[right].weight < heap[left].weight)
            smallest = right;

        if (smallest != index) {
            swap(index, smallest);
            minHeapify(smallest);
        }
    }

    public int parent(int index) {
        if (index == 0) return -1;
        return index / 2;
    }

    public int leftChild(int index) {
        return 2 * index;
    }

    public int rightchild(int index) {
        return 2 * index + 1;
    }

    public void swap(int a, int b) {
        heap[a].location = b;
        heap[b].location = a;

        Vertex temp = heap[a];
        heap[a] = heap[b];
        heap[b] = temp;
    }

    public void printArray() {
        System.out.print("[");
        for (Vertex i : heap) {
            System.out.print(i.weight + ", ");
        }
        System.out.println("]");
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        int cnt = 0;
        int mark = 2;
        for (Vertex i : heap) {
            ret.append("[");
            ret.append(i.id);
            ret.append(", ");
            ret.append(i.weight);
            ret.append("] ");
            if (cnt == 0)
                ret.append("\n");
            else if (cnt == mark) {
                ret.append("\n");
                mark = mark * 2;
                cnt = 0;
            }
            cnt++;
        }
        return ret.toString();
    }
}
