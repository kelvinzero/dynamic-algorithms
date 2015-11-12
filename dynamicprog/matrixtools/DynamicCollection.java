package dynamicprog.matrixtools;

import java.awt.*;
import java.util.*;

/**
* 
 * @author Kelvinzero House of Shiva
 *         Armiger and Standard Bearer for the Justiciar,
 *         and Vicar to Karth the Falconer
 *
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
    
    /**
     * recursively checks all possible solutions for the queens problem.
     * the integer array parameter represents the columns, and the value in
     * each column of the array is the numbered position of the queen in
     * that row.
     *
     * @param C     - the vertical axis of the chess board
     * @param queen - queen i always goes on row i
     */
    public static void queens(int[] C, int queen) { // queen i goes in queen i4

        // first make sure this queen isn't in conflict with previously placed queens,
        // method call backs out if this queen doesn't fit here
        if (safespace(C, queen)) { // - - - n
            // if a safespace has been found in the last row a solution set is complete,
            // print the chess board
            if (queen == C.length - 1) { // - - - c1
                System.out.println(); // - - - c2
                printSolution(C); // - - -  c3
            }
            // if this safespace is not in last row, try to add a queen in the next row
            else {  // - - - - - c4
                for (int column = 0; column < C.length; column++) { // - - - - c5
                    C[queen + 1] = column;  // - - - - c6
                    queens(C, queen + 1); // - - - - n!
                }
            }
        }
    }
    
    /**
     * checks columns 0 to q-1 that they are not in conflict with the queen
     * in the current row.
     *
     * @param C     - the vertical axis of the chess board
     * @param queen - queen i always goes on row i
     */
    public static boolean safespace(int[] C, int queen) {

        boolean safespace = true;

        for (int i = 0; i < queen; i++) {
            if (C[i] == C[queen])
                return false;
            if (Math.abs(C[queen] - C[i]) == queen - i)
                return false;
        }
        return true;
    }
    
    /* prints the chess board */
    static void printBoard(int C[]) {

        System.out.println();

        for (int r = 0; r < C.length; r++) { // rows
            for (int c = 0; c < C.length; c++) { // columns
                if (C[r] == c)
                    System.out.print(" Q ");
                else
                    System.out.print(" * ");
            }
            System.out.println();
        }
    }
 }
