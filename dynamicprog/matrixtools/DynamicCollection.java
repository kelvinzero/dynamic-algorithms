package dynamicprog.matrixtools;

import java.awt.*;
import java.util.*;

/**
* See ReadMe for details
*/
public class DynamicCollection {

/* 
* returns the sum of weights in the shortest path from W[j][k] to W[M][N] 
* see readme for further information
*/
public static int shortestPath(Node[][] W, int j, int k, int M, int N) {

        if (j == M && k == N) return W[j][k].weight; // base case the lower right corner - - - - c1

        int smallest = Integer.MAX_VALUE; // - - - - - - - - c2
        // if next node exists, compares recursive call to current
        // smallest each time, keeping the smallest of the two //
        if (j < M) smallest = Math.min(smallest, shortestPath(W, j + 1, k, M, N)); // - - - - - - - - n^3
        if (k < N) smallest = Math.min(smallest, shortestPath(W, j, k + 1, M, N)); // - - - - - - - n^3
        if (j < M && k < N) smallest = Math.min(smallest, shortestPath(W, j + 1, k + 1, M, N)); // - - - - n^3

        return W[j][k].weight + smallest; // - - - - - - - - c4
    }
    
    /* 
    * returns the Point coordinates of the nodes in the shortest path from W[j][k] to W[M][N] 
    * see readme for further information
    */
    public static ArrayList<Point> shortestCoordinates(Node[][] W, int j, int k, int M, int N) {

        ArrayList<Point> retList = new ArrayList<>(); // - - - - - c1
        ArrayList<Point> lowestPath = new ArrayList<>(); // - - - c2
        ArrayList<Point> holder; // - - - - - - - c3

        if (j == M && k == N) {  // - - - - - c4
            retList.add(new Point(j, k)); // - - - - - c5
            return retList; // - - - - - - - - c6
        }
        if(j < M){ // - - - - - c7
            lowestPath = shortestCoordinates(W, j+1, k, M, N); // - - - - - - T(n^3 + c)
        }
        if(k < N){ // - - - - - - c8
            if(!lowestPath.isEmpty()) { // - - - - - - - - - - - - c9
                holder = shortestCoordinates(W, j, k + 1, M, N); // - - - - - - - - - \
                if (pathWeight(W, holder) < pathWeight(W, lowestPath)) // - - c10       \
                    lowestPath = holder;  // - - - - - c11                               |- - - T(n^3 + c)
            }else // - - - - - c12                                                      /
                lowestPath = shortestCoordinates(W, j, k+1, M, N); // - - - - - - - - / 
        }
        if(k < N && j < M){ // - - - - - - - - c13
            if(!lowestPath.isEmpty()) {  // - - - - - c14
                holder = shortestCoordinates(W, j + 1, k + 1, M, N); // - - - - - - T(n^3 + c)
                if (pathWeight(W, holder) < pathWeight(W, lowestPath)) // - - - - c15
                    lowestPath = holder;
            }else // - - - - - - c16
                lowestPath = shortestCoordinates(W, j+1, k+1, M, N); // - - - - - - T(n^3 + c)
        }
        lowestPath.add(0, new Point(j, k)); // - - - - c17
        return lowestPath; // - - - - c18
    }
  }
