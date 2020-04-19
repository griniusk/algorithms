package projects.sliderPuzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class responsible for solving a slider puzzle of arbitrary size or proving that it is not solvable otherwise.
 */
public class Solver {

    private final List<Node> gameTree;
    private boolean solvable;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board board) {
        if (board == null)
            throw new IllegalArgumentException();

        MinPQ<Node> minPq = new MinPQ<>(new PriorityOrder());
        MinPQ<Node> minTwinPq = new MinPQ<>(new PriorityOrder());
        this.gameTree = new ArrayList<>();

        solve(minPq, minTwinPq, board);
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board
    public int moves() {
        if (gameTree.size() < 1) {
            return 0;
        }
        return gameTree.get(gameTree.size() - 1).numberOfMoves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        return solvable ? gameTree.stream().map(e -> e.board).collect(Collectors.toList()) : null;
    }

    private void solve(MinPQ<Node> minPQ, MinPQ<Node> minTwinPQ, Board initial) {
        minPQ.insert(new Node(initial, 0, null));
        minTwinPQ.insert(new Node(initial.twin(), 0, null));

        Node nextNode = minPQ.delMin();
        Node nextTwinNode = minTwinPQ.delMin();

        gameTree.add(nextNode);

        while (!isGoal(nextNode)) {
            nextNode = getNextNode(nextNode, minPQ);
            gameTree.add(nextNode);

            nextTwinNode = getNextNode(nextTwinNode, minTwinPQ);
            if (isGoal(nextTwinNode)) {
                return;
            }
        }

        this.solvable = true;
    }

    private Node getNextNode(Node min, MinPQ<Node> minPQ) {
        for (Board board : min.board.neighbors()) {
            if (min.previous != null) {
                if (!board.equals(min.previous.board)) {
                    minPQ.insert(new Node(board, min.numberOfMoves + 1, min));
                }
            } else {
                minPQ.insert(new Node(board, min.numberOfMoves + 1, min));
            }
        }

        return minPQ.delMin();
    }

    private boolean isGoal(Node node) {
        return node.board.isGoal();
    }

    private static class Node {
        private final Board board;
        private final int numberOfMoves;
        private final Node previous;
        private final int manhattan;

        private final int priority;

        public Node(final Board board, final int moves, final Node previousNode) {
            this.board = board;
            this.manhattan = board.manhattan();
            this.priority = manhattan + moves;
            this.numberOfMoves = moves;
            this.previous = previousNode;
        }

        public int getPriority() {
            return this.priority;
        }
    }

    private static class PriorityOrder implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            if (o1.getPriority() - o2.getPriority() < 0) {
                return -1;
            } else if (o1.getPriority() - o2.getPriority() > 0) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    // test client (see below)
    public static void main(String[] args) {
        // empty
    }
}
