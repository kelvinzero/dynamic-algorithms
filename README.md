# Collection of customized dynamic algorithms

**int shortestPath(Node[][] W, int j, int k, int M, int N)** - *calculates the shortest path through weighted nodes in a matrix, 
starting at the point W[j][k], to the W[M][N] point, then returns the sum of those weights. The algorithm can only travel to
it's right neightbor, bottom right diagonal neightbor, and bottom neighbor. Restrictions: j <= M and k <= N*

**ArrayList<Java.awt.Point> shortestCoordinates(Node[][] W, int j, int k, int M, int N)** - *calculates the shortest path through weighted nodes in a matrix, 
starting at the point W[j][k], to the W[M][N] point, then returns all of the Points along the path. Running time and restrictions
are the same as shortestPath()*
