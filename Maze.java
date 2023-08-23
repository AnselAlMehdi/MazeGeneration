import java.awt.*;
import java.util.Random;

public class Maze {

    Random randomVar = new Random();
    private int r = 50;
    private int g = 0;
    private int b = 100;
    private final int cols, rows;   // dimension of maze
    private boolean[][] wallUp;      // is there a wall to north of cell (col, row)
    private boolean[][] wallRight;
    private boolean[][] wallDown;
    private boolean[][] wallLeft;
    private boolean[][] visited;
    private boolean isDone = false;

    public Maze(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
        int height = 800/2;
        int width = 800/2;
        StdDraw.setCanvasSize(width, height);

        StdDraw.setXscale(0, cols + 2);
        StdDraw.setYscale(0, rows + 2);
        setUp();
        create();
    }

    private void setUp() {
        // initialize border cells as already visited
        visited = new boolean[cols + 2][rows + 2];
        for (int col = 0; col < cols + 2; col++) {
            visited[col][0] = true;
            visited[col][rows + 1] = true;
        }
        for (int row = 0; row < rows + 2; row++) {
            visited[0][row] = true;
            visited[cols + 1][row] = true;
        }


        // initialize all walls as present
        wallUp = new boolean[cols + 2][rows + 2];
        wallRight = new boolean[cols + 2][rows + 2];
        wallDown = new boolean[cols + 2][rows + 2];
        wallLeft = new boolean[cols + 2][rows + 2];
        for (int col = 0; col < cols + 2; col++) {
            for (int row = 0; row < rows + 2; row++) {
                wallUp[col][row] = true;
                wallRight[col][row] = true;
                wallDown[col][row] = true;
                wallLeft[col][row] = true;
            }
        }
    }



    private void create(int col, int row) {
        visited[col][row] = true;

        // while any touching tiles are unvisited
        while (!visited[col][row + 1] || !visited[col + 1][row]
                || !visited[col][row - 1] || !visited[col - 1][row]) {

            // pick random neighbor
            while (true) {

                double r = randomVar.nextInt(4-0);
                if (r == 0 && !visited[col][row + 1]) {
                    wallUp[col][row] = false;
                    wallDown[col][row + 1] = false;
                    create(col, row + 1);
                    break;
                }
                else if (r == 1 && !visited[col + 1][row]) {
                    wallRight[col][row] = false;
                    wallLeft[col + 1][row] = false;
                    create(col + 1, row);
                    break;
                }
                else if (r == 2 && !visited[col][row - 1]) {
                    wallDown[col][row] = false;
                    wallUp[col][row - 1] = false;
                    create(col, row - 1);
                    break;
                }
                else if (r == 3 && !visited[col - 1][row]) {
                    wallLeft[col][row] = false;
                    wallRight[col - 1][row] = false;
                    create(col - 1, row);
                    break;
                }
            }
        }
    }

    // generate the maze starting from lower left
    private void create() {
        create(1, 1);

    }


    // solve the maze using depth-first search
    private void solve(int col, int row) {
        if (isDone) {
            return;
        }
        if (col == 0 || row == 0 || col == cols + 1 || row == rows + 1) {
            return;
        }
        if (visited[col][row]) return;
        visited[col][row] = true;


        StdDraw.setPenColor(100,0,100); // was blue
        StdDraw.filledSquare(col + 0.5, row + 0.5, 0.251);
        StdDraw.show();


        // reached middle
        if (col == cols / 2 && row == rows / 2) isDone = true;

        if (!wallUp[col][row]) {
            solve(col, row + 1);
        }
        if (!wallRight[col][row]) {
            solve(col + 1, row);
        }
        if (!wallDown[col][row]) {
            solve(col, row - 1);
        }
        if (!wallLeft[col][row]) {
            solve(col - 1, row);
        }

        if (isDone) {
            return;
        }

        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledSquare(col + 0.5, row + 0.5, 0.27);
        StdDraw.show();
        //StdDraw.pause(5);
    }

    // solve the maze starting from the start state
    public void solve() {
        for (int col = 1; col <= cols; col++) {
            for (int row = 1; row <= rows; row++) {
                visited[col][row] = false;
            }
        }
        isDone = false;
        solve(1, 1);
    }

    // draw the maze
    public void draw() {
        int targetCol = cols / 2;
        int targetRow = rows / 2;
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.filledSquare(targetCol + 0.5, targetRow + 0.5, 0.49);
        StdDraw.filledSquare(1.5, 1.5, 0.49);

        //StdDraw.filledCircle(1.5, 1.5, 0.375);

        StdDraw.setPenColor(StdDraw.BLACK);
        for (int col = 1; col <= cols; col++) {
            for (int row = 1; row <= rows; row++) {
                if (wallDown[col][row]) {
                    StdDraw.line(col, row, col + 1, row);
                }
                if (wallUp[col][row]) {
                    StdDraw.line(col, row + 1, col + 1, row + 1);
                }
                if (wallLeft[col][row]) {
                    StdDraw.line(col, row, col, row + 1);
                }
                if (wallRight[col][row]) {
                    StdDraw.line(col + 1, row, col + 1, row + 1);
                }
            }
        }
        StdDraw.show();
        StdDraw.pause(1000);
    }



}

