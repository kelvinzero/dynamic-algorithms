#DynamicCollection.java

**int lowest_sum_path(Node[][] W, int j, int k, int M, int N)** - *calculates the shortest path through weighted nodes in a matrix, 
starting at the point W[j][k], to the W[M][N] point, then returns the sum of those weights. The algorithm can only travel to
it's right neightbor, bottom right diagonal neightbor, and bottom neighbor. Restrictions: j <= M and k <= N*

**int lowest_sum_path_dynamic(Node[][] W)** - *calculates the shortest path through weighted nodes in a matrix, 
starting at the point W[0][0], to the W[M][N] (bottom right corner) point, then returns the sum of those weights. The algorithm can only travel to it's right neightbor, bottom right diagonal neightbor, and bottom neighbor. Uses a tabular, iterative, dynamic solution to solve the problem.*

**void queens(int[] C, int queen)** - 
*recursively checks all possible solutions for the queens problem.
the integer array parameter represents the columns, and the value in
each column of the array is the numbered position of the queen in
that row.*

**void queens_first_solution_only(int[] C, int queen)** - 
*recursively checks all possible solutions for the queens problem.
the integer array parameter represents the columns, and the value in
each column of the array is the numbered position of the queen in
that row. Added check to stop after first solution set found.*

**ArrayList<Java.awt.Point> shortestCoordinates(Node[][] W, int j, int k, int M, int N)** - *calculates the shortest path through weighted nodes in a matrix, 
starting at the point W[j][k], to the W[M][N] point, then returns all of the Points along the path. Running time and restrictions
are the same as shortestPath()*

**boolean safespace(int[] C, int queen)** - * checks columns 0 to queen-1 that they are not in conflict with the queen
in the current row. *

**void printBoard(int C[])** - *prints the chess board*
