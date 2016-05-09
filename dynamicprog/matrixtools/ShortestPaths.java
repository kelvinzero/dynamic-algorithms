package dynamicprog.matrixtools;

import dynamicprog.matrixtools.node.Node;
import dynamicprog.utilities.MatrixUtils;

import java.awt.*;
import java.util.*;

/**
 *
 * @author Joshua Cotes - Fall 2015
 *
*/
public class ShortestPaths {

    /**
     * alculates the shortest path through weighted nodes in a matrix recursively, starting at
     * the point W[j][k], to the W[M][N] point, then returns the sum of those weights.
     * The algorithm can only travel to it's right neightbor, bottom right diagonal neighbor,
     * and bottom neighbor. Restrictions: j <= M and k <= N
     * T(n) = O(n^3)
     *
     * @param W - the nxn weighted matrix
     * @param j - start X
     * @param k - start Y
     * @param M - end X
     * @param N - end Y
     * @return - the shortest path summed weights
     */
    public static int lowest_sum_path(Node[][] W, int j, int k, int M, int N) { // - - - T(n)

        if (j == M && k == N) return W[j][k].weight; // base case the lower right corner - - - - c1

        int smallest = Integer.MAX_VALUE; // - - - - - - - - c2
        // if next node exists, compares recursive call to current
        // smallest each time, keeping the smallest of the two // 
        if (j < M) smallest = Math.min(smallest, lowest_sum_path(W, j + 1, k, M, N)); // - - - - - - - - n^3
        if (k < N) smallest = Math.min(smallest, lowest_sum_path(W, j, k + 1, M, N)); // - - - - - - - n^3
        if (j < M && k < N) smallest = Math.min(smallest, lowest_sum_path(W, j + 1, k + 1, M, N)); // - - - - n^3

        return W[j][k].weight + smallest; // - - - - - - - - c4
    }


    /**
     * calculates the shortest path through weighted nodes in a matrix, starting at the
     * point W[0][0], to the W[M]N point, then returns the sum of those weights. The
     * algorithm can only travel to it's right neightbor, bottom right diagonal neightbor,
     * and bottom neighbor. Uses a tabular, iterative, dynamic solution to solve the problem.
     * T(n) = O(m^n)
     *
     * @param W - the nxn weighted matrix
     * @return - the shortest path summed weights
     */
    public static int lowest_sum_path_dynamic(Node[][] W) { // shortest path from 0,0 to M,N

        int M = W.length; // - - - - - c1
        int N = W[0].length; // - - -/

        int total_weight[][] = new int[M][N]; // - - - - c2
        total_weight[0][0] = W[0][0].weight; // - - -/

        for (int r = 1; r < M; r++) // - - - M
            total_weight[r][0] = total_weight[r - 1][0] + W[r][0].weight; // - - c3
        for (int c = 1; c < N; c++) // - - - N
            total_weight[0][c] = total_weight[0][c-1] + W[0][c].weight; // - - - c4
        
        for (int r = 1; r < M; r++){ // - - - - - - - - - M^N (n^2 if M=N)
            for(int c = 1; c < N; c++){ // - - - - /
                total_weight[r][c] = Math.min(
                        Math.min(total_weight[r-1][c-1],total_weight[r-1][c])
                        , total_weight[r][c-1]) + W[r][c].weight;// - - - - - - - - c5
            }
        }
        return total_weight[M-1][N-1]; // - - - - c6
    }

    /**
     * calculates the shortest path through weighted nodes in a matrix, starting at the
     * point W[startRow][startColumn], to the W[endRow][endColumn] point, then returns all of the Points along the path.
     * Running time and restrictions are the same as shortestPath()
     * T(n) = O(n^3)
     *
     * @param W - the weighted nxn matrix
     * @param startRow - the start row
     * @param startColumn - the start column
     * @param endRow - the end row
     * @param endColumn - the end column
     * @return - the list of points in the shortest path
     */
    public static ArrayList<Point> shortestCoordinates(Node[][] W, int startRow, int startColumn, int endRow, int endColumn) {

        ArrayList<Point> retList = new ArrayList<>(); // - - - - - c1
        ArrayList<Point> lowestPath = new ArrayList<>(); // - - - c2
        ArrayList<Point> holder; // - - - - - - - c3

        // if the end of the path is reached, return the shortest path list
        if (startRow == endRow && startColumn == endColumn) {  // - - - - - c4
            retList.add(new Point(startRow, startColumn)); // - - - - - c5
            return retList; // - - - - - - - - c6
        }

        // if start row hasn't reached end row, recursively call this method on next row
        if(startRow < endRow) // - - - - - c7
            lowestPath = shortestCoordinates(W, startRow+1, startColumn, endRow, endColumn); // - - - - - - T(n^3 + c)

        // if start column hasn't reach end column
        if(startColumn < endColumn){ // - - - - - - c8

            // if the lowestpath list isn't empty, call method again on next column
            if(!lowestPath.isEmpty()) { // - - - - - - - - - - - - c9
                holder = shortestCoordinates(W, startRow, startColumn + 1, endRow, endColumn); // - - - - - - - - - \

                // if next column weight is less than best weight, swap values
                if (MatrixUtils.pathWeight(W, holder) < MatrixUtils.pathWeight(W, lowestPath)) // - - c10       \
                    lowestPath = holder;  // - - - - - c11                               |- - - T(n^3 + c)

                // if the lowest path list was empty, make it the next row
            }else // - - - - - c12                                                      /
                lowestPath = shortestCoordinates(W, startRow, startColumn+1, endRow, endColumn); // - - - - - - - - /
        }

        // if start and end points are not yet reached
        if(startColumn < endColumn && startRow < endRow){ // - - - - - - - - c13

            // if the lowestpath list is not empty
            if(!lowestPath.isEmpty()) {  // - - - - - c14
                holder = shortestCoordinates(W, startRow + 1, startColumn + 1, endRow, endColumn); // - - - - - - T(n^3 + c)

                // if if next row and column weight is less than lowest path weight
                if (MatrixUtils.pathWeight(W, holder) < MatrixUtils.pathWeight(W, lowestPath)) // - - - - c15
                    lowestPath = holder;

            // if lowestpath list is empty
            }else // - - - - - - c16
                lowestPath = shortestCoordinates(W, startRow+1, startColumn+1, endRow, endColumn); // - - - - - - T(n^3 + c)
        }

        // add the current start point to the beginning of the lowest path list
        lowestPath.add(0, new Point(startRow, startColumn)); // - - - - c17

        return lowestPath; // - - - - c18
    }



      /*
     /**
     * builds an adjacency list from a binary search tree
     * 
     * @param root - - the root node to search from
     * @param adjacency_matrix - - An array of ArrayLists
     *
    public static void create_adjacency_list(BST_Node root, ArrayList<BST_Node>[] adjacency_list) {

        if (root == null) // - - - c1
            return; // - - - /

        adjacency_list[root.key] = new ArrayList<BST_Node>();
        if(root.leftchild != null)
            adjacency_list[root.key].add(root.leftchild);
        if(root.rightchild != null)
            adjacency_list[root.key].add(root.rightchild);

        create_adjacency_list(root.leftchild, adjacency_list); // - - - O(n)
        create_adjacency_list(root.rightchild, adjacency_list);// - - - O(n)
    }*/
 }










