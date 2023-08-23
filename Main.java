public class Main {
    public static void main(String[] args) {
        Maze maze = new Maze(150,150);

        maze.draw();

        maze.solve();

        maze.draw();


    }
}
