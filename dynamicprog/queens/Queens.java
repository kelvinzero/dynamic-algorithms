package dynamicprog.queens;

/**
 * contains methods to calculate the queens conflict solutions.
 */
public class Queens {

    /**
     * Prints the solution to the queens problem to stdout
     *
     * @param path - the shortest path array
     */
    private static void printSolution(int[] path){
        for(int i = 0; i < path.length; i++){
            for(int j = 0; j < path.length; j++){
                if(path[i] != j)
                    System.out.print(" * ");
                else
                    System.out.print(" Q ");
            }
            System.out.println();
        }
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
                for (int column = 0; column <= C.length; column++) { // - - - - c5
                    C[queen + 1] = column;  // - - - - c6
                    queens(C, queen + 1); // - - - - n!
                }
            }
        }
    }

    /* queens algorithm modified to stop searching after the first solution is found */
    public static boolean queens_first_solution_only(int[] C, int queen) {

        // first make sure this queen isn't in conflict with previously placed queens,
        // method call backs out if this queen doesn't fit here
        if (safespace(C, queen)) {
            // if a safespace has been found in the last row a solution set is complete,
            // print the chess board
            if (queen == C.length - 1) {
                printSolution(C);
                return false;
            }
            // if this safespace is not in last row, try to add a queen in the next row
            else {
                for (int column = 0; column <= C.length; column++) {
                    C[queen + 1] = column;
                    if(!queens_first_solution_only(C, queen + 1))
                        return false;
                }
            }
        }
        return true;
    }

    /**
     * checks columns 0 to q-1 that they are not in conflict with the queen
     * in the current row.
     *
     * @param C     - the vertical axis of the chess board
     * @param queen - queen i always goes on row i
     */
    // No modification from original until queens_first_solution_only
    // safespace included to complete code
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

}
