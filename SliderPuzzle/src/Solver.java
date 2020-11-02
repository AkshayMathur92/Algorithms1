import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;


public class Solver {

    private Board init;
    private Board flipped;
    private boolean solvable = false;
    private int numMoves = -1;
    private Stack s = null;

    // find a solution to the initial boa2rd (using the A* algorithm)
    public Solver(Board initial) {
        if(initial == null) throw new IllegalArgumentException();
        this.init = initial;
        this.flipped = init.twin();
        AbstractFitnessFactory aff = new ManhattenStrategyFactory();

        MinPQ<SearchNode> q = new MinPQ<>();

        MinPQ<SearchNode> flippedq = new MinPQ<>();

        SearchNode current =  aff.getNewFitnessStrategy(null, initial, 0);
        SearchNode currentFlipped = aff.getNewFitnessStrategy(null, flipped, 0);

        for(; !(current.getBoard().isGoal() || currentFlipped.getBoard().isGoal()); current = q.delMin(), currentFlipped = flippedq.delMin()){
            for(Board nextMove: current.getBoard().neighbors()){
                if(current.getLastMove() == null || !current.getLastMove().getBoard().equals(nextMove)) {
                    insertToQ(aff, q, current, nextMove);
                }
            }
            for(Board nextMove : currentFlipped.getBoard().neighbors()){
                if(currentFlipped.getLastMove() == null || !currentFlipped.getLastMove().getBoard().equals(nextMove))
                    insertToQ(aff, flippedq, currentFlipped, nextMove);
            }
        }
        if(!current.getBoard().isGoal()){
            solvable = false;
            numMoves = -1;
        }else {
            s = new Stack();
            while (current != null) {
                s.push(current.getBoard());
                current = current.getLastMove();
            }
            solvable = true;
            numMoves = s.size() - 1;
        }
    }

    private void insertToQ(AbstractFitnessFactory aff, MinPQ<SearchNode> q, SearchNode current, Board nextMove) {
        q.insert(aff.getNewFitnessStrategy(current, nextMove, current.getMoves() + 1));
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return this.solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return numMoves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return s;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    private interface AbstractFitnessFactory{
        SearchNode getNewFitnessStrategy(SearchNode s, Board b, int moves);
    }

    private class HammingStrategyFactory implements AbstractFitnessFactory{

        @Override
        public SearchNode getNewFitnessStrategy(SearchNode s, Board b, int moves) {
            return new HammingStrategy((HammingStrategy)s,b,moves);
        }
    }

    private class ManhattenStrategyFactory implements AbstractFitnessFactory{

        @Override
        public SearchNode getNewFitnessStrategy(SearchNode s, Board b, int moves) {
            return new ManhattenStrategy((ManhattenStrategy) s, b, moves);

        }
    }

    private interface SearchNode extends Comparable{
        Board getBoard();
        SearchNode getLastMove();
        int getMoves();
    }

    private static class HammingStrategy implements SearchNode {
        public int movesDone;
        HammingStrategy lastSolve;
        Board board;
        int distancetoGoal;

        public HammingStrategy(HammingStrategy lastSolve, Board b, int alreadyMovesDone) {
            this.board = b;
            this.lastSolve = lastSolve;
            this.movesDone = alreadyMovesDone;
            distancetoGoal = alreadyMovesDone + b.hamming();
        }

        @Override
        public int compareTo(Object o) {
            return distancetoGoal - ((HammingStrategy) o).distancetoGoal;
        }

        @Override
        public Board getBoard() {
           return board;
        }

        @Override
        public SearchNode getLastMove() {
            return lastSolve;
        }

        @Override
        public int getMoves() {
            return movesDone;
        }
    }

    private static class ManhattenStrategy implements SearchNode {
        public int movesDone;
        ManhattenStrategy lastSolve;
        Board board;
        int distancetoGoal;

        public ManhattenStrategy(ManhattenStrategy lastSolve, Board b, int alreadyMovesDone) {
            this.board = b;
            this.lastSolve = lastSolve;
            this.movesDone = alreadyMovesDone;
            distancetoGoal = alreadyMovesDone + b.manhattan();
        }

        @Override
        public int compareTo(Object o) {
            return distancetoGoal - ((ManhattenStrategy) o).distancetoGoal;
        }

        @Override
        public Board getBoard() {
            return board;
        }

        @Override
        public SearchNode getLastMove() {
            return lastSolve;
        }

        @Override
        public int getMoves() {
            return movesDone;
        }
    }
}