import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private int[][] tiles;
    private int n;
    private int hamming = -1;
    private int manhattanSum = -1;
    private List<Board> neighbor = null;
    private Board twin;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles){
        if(tiles == null) throw new IllegalArgumentException();
       this.tiles = tiles;
       this.n = tiles.length;
    }

    // string representation of this board
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n);
        stringBuilder.append('\n');
        for(int[] tilearr : tiles){
            for(int tile : tilearr){
                stringBuilder.append(tile);
                stringBuilder.append(' ');
            }
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }

    // board dimension n
    public int dimension(){
        return n;
    }

    // number of tiles out of place
    public int hamming(){
        if(hamming == - 1) {
            hamming = 0;
            int counter = 1;
            for (int i = 0; i < n * n - 1; i++) {
                if (counter != tiles[i / n][i % n]) {
                    hamming++;
                }
                counter++;
            }
        }
       return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan(){
        if(manhattanSum == -1) {
            manhattanSum = 0;
            for (int i = 0; i < n * n; i++) {
                if (!(tiles[i / n][i % n] == 0))
                    manhattanSum += Math.abs(i / n - ((tiles[i / n][i % n] - 1) / n)) + Math.abs(i % n - ((tiles[i / n][i % n] - 1) % n));
            }
        }
        return manhattanSum;
    }

    // is this board the goal board?
    public boolean isGoal(){
        if(manhattanSum == -1) manhattan();
        if(manhattanSum == 0) return true;
        return false;
    }

    // does this board equal y?
    public boolean equals(Object y){
        if(this == y) return true;
        if(! (y instanceof Board)) return false;
        Board that = (Board) y;
        if(!(this.n == that.n)) return false;
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j ++){
                if(!(this.tiles[i][j] == that.tiles[i][j]))
                    return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors(){
        if(neighbor == null) {
            int emptyTile = getEmptyTile();
            Integer[] neighbours = getNeighbour(emptyTile, n);
            neighbor  = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                Integer neighbour = neighbours[i];
                if (neighbour == null) break;
                Board clone = (Board) this.cloneBoard();
                swap(clone, emptyTile, neighbour, n);
                neighbor.add(clone);
            }
        }
        return neighbor;
    }

    private int getEmptyTile() {
        int emptyTile = - 1;
        for(int row = 0; row < n; row++){
            int[] arr = tiles[row];
            int found = search(arr,0);
            if(found != -1) {
                emptyTile = n*row + found;
                break;
            }
        }
        return emptyTile;
    }

    private void swap(Board clone, int emptyTile, Integer neighbour, int n) {
         int temp = clone.tiles[emptyTile/n][emptyTile %n];
         clone.tiles[emptyTile / n][emptyTile % n] = clone.tiles[neighbour / n][neighbour % n];
         clone.tiles[neighbour / n][neighbour % n] = temp;
    }

    private Integer[] getNeighbour(int emptyTile, int n) {
        Integer[] neighbour = new Integer[4];
        int i = 0;
        int row = emptyTile / n;
        int col = emptyTile % n;
        if(col > 0){
           neighbour[i] = n * row + col - 1;
           i++;
        }
        if(col < n - 1){
            neighbour[i] = n * row + col + 1;
            i++;
        }
        if(row > 0){
            neighbour[i] = n * (row - 1) + col;
            i++;
        }
        if(row < n - 1){
            neighbour[i] = n * (row + 1) + col;
            i++;
        }
        if(i < 4) neighbour[i] = null;
        return neighbour;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        if(twin == null) {
            Board clone = (Board) this.cloneBoard();
            int random1 = -1;
            while (random1 == -1 || tiles[random1 / n][random1 % n] == 0 || tiles[(random1 + 1) / n][(random1 + 1) % n] == 0 || random1 % n != 0) {
                random1 = StdRandom.uniform(n * n - 1);
            }
            swap(clone, random1, random1 + 1, n);
            twin = clone;
        }
        return twin;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int [][]tiles = {{1,0,8},{3,5,4},{7,2,6}};
        Board testBoard = new Board(tiles);
        assert (testBoard.hamming() == 5);
        assert (testBoard.manhattan() == 10);
        int [][]inittiles = {{1,2,3},{4,0,5},{7,8,6}};
        Board anotherTestBoard = new Board(inittiles);
        for(Board b : anotherTestBoard.neighbors()){
            System.out.println(b);
        }
    }

    private int search(int[] arr, int key){
        for(int i = 0 ; i < arr.length; i++){
            if(key == arr[i]) return i;
        }
        return -1;
    }


    private Board cloneBoard() {
        int [][] tiles = new int[n][n];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n;j ++){
                tiles[i][j] = this.tiles[i][j];
            }
        }
        return new Board(tiles);
    }
}
