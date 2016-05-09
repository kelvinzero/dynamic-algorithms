package dynamicprog.utilities;

import dynamicprog.matrixtools.node.Node;

import java.awt.*;
import java.util.ArrayList;

/**
 * Assistant utilities for the matrix classes
 */
public class MatrixUtils {

    /**
     * calculates the path weight given the points and weighted matrix
     *
     * @param W - the weighted matrix
     * @param path - the list of points on the path
     * @return - the sum of the path weights
     */
    public static double pathWeight(Node[][] W, ArrayList<Point> path){

        double sum = 0;
        for(Point point : path){
            sum += W[(int)point.getX()][(int)point.getY()].weight;
        }
        return sum;
    }


}
