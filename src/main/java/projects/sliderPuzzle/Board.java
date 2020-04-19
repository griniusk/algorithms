package projects.sliderPuzzle;

import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Board {

    private final int[][] tiles;
    private Board twin;
    private Board goal;
    private int blankM;
    private final int d;

    public Board(int[][] tiles) {
        if (tiles == null) {
            throw new IllegalArgumentException("Board cannot be null");
        }
        if (!isDimensionsValid(tiles)) {
            throw new IllegalArgumentException("Invalid board dimensions: sides of the " +
                    "board must be equal.");
        }

        this.d = tiles.length;
        this.tiles = copyBoard(tiles, true);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(d).append("\n");

        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                sb.append(tiles[i][j]);
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // board dimension n
    public int dimension() {
        return this.d;
    }

    // number of tiles out of place
    public int hamming() {
        int[][] goalTiles = getGoal().getTiles();
        int outOfPlaceCount = 0;
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                if (this.tiles[i][j] != goalTiles[i][j] && this.tiles[i][j] != 0)
                    outOfPlaceCount++;
            }
        }
        return outOfPlaceCount;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int sum = 0;
        int[][] coordinates = resolveGoalCoordinates(d);

        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                int number = tiles[i][j];
                if (number != 0) {
                    int i1 = coordinates[number][0];
                    int j1 = coordinates[number][1];
                    sum += Math.abs(i - i1) + Math.abs(j - j1);
                }
            }
        }
        return sum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return this.equals(getGoal());
    }

    // does this board equal y?
    @Override
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if ((this.getClass() != y.getClass())) {
            return false;
        }
        Board that = (Board) y;
        return Arrays.deepEquals(this.tiles, that.tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Collection<Board> neighborList = new ArrayList<>();
        // locate blank square
        int m = Integer.MAX_VALUE;
        int n = Integer.MAX_VALUE;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j] == 0) {
                    m = i;
                    n = j;
                    break;
                }
            }
        }

        // exchange with up to four neighbours and put board to list
        if (n != 0) neighborList.add(exch(m, n, m, n - 1));
        if (m != 0) neighborList.add(exch(m, n, m - 1, n));
        if (m != tiles.length - 1) neighborList.add(exch(m, n, m + 1, n));
        if (n != tiles.length - 1) neighborList.add(exch(m, n, m, n + 1));
        return neighborList;
    }

    /**
     * Returns a board that is obtained by exchanging any pair of tiles apart of an empty tile.
     */
    public Board twin() {
        // Here we make sure that a twin board is created only once and then reused if called multiple times afterwards.
        if (this.twin == null) {
            int[][] newBoard = copyBoard(this.tiles, false);

            int m0;
            int n0 = StdRandom.uniform(d);

            do {
                m0 = StdRandom.uniform(d);
            } while (m0 == blankM);

            if (n0 != 0)
                swap(newBoard, m0, n0, m0, n0 - 1);
            else
                swap(newBoard, m0, n0, m0, n0 + 1);

            this.twin = new Board(newBoard);
            return this.twin;
        }
        return this.twin;
    }

    // unit testing (not graded)
    public static void main(String[] args) {

        // The twin test
        Board board1 = new Board(new int[][]{{1, 0, 3}, {4, 2, 5}, {7, 8, 6}});
        printBoard(board1, "Original board");
        printBoard(board1.twin(), "Twin board");
        printBoard(board1.twin(), "expect same twin board as before");


    }

    // ***********UTILITY METHODS********************************

    private boolean isDimensionsValid(int[][] t) {
        int dim = t.length;
        for (int[] row : t) {
            if (row.length != dim)
                return false;
        }
        return true;
    }

    private int[][] getTiles() {
        return this.tiles;
    }

    private int[][] copyBoard(int[][] t, boolean findZero) {
        int[][] boardCopy = new int[t.length][t.length];
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[i].length; j++) {
                boardCopy[i][j] = t[i][j];
                if (boardCopy[i][j] == 0 && findZero) {
                    this.blankM = i;
                }
            }
        }
        return boardCopy;
    }

    private Board exch(int m0, int n0, int m1, int n1) {
        int[][] tilesCopy = copyBoard(this.tiles, false);

        // exchange fields
        int temp = tilesCopy[m0][n0];
        tilesCopy[m0][n0] = tilesCopy[m1][n1];
        tilesCopy[m1][n1] = temp;

        return new Board(tilesCopy);
    }

    private void swap(int[][] t, int m0, int n0, int m1, int n1) {
        int temp = t[m0][n0];
        t[m0][n0] = t[m1][n1];
        t[m1][n1] = temp;
    }

    private static void printTiles(int[][] t, String comment) {
        System.out.printf("\nTiles for the test: %s: \n", comment);
        for (int i = 0; i < t.length; i++) {
            for (int tile : t[i])
                System.out.print(tile + " ");
            System.out.println();
        }
    }

    private static void printBoard(Board board, String comment) {
        printTiles(board.tiles, comment);
    }

    /**
     * Creates and returns a goal board to be reused.
     */
    private Board getGoal() {
        if (this.goal == null) {
            int[][] t = new int[d][d];

            int idx = 1;
            for (int i = 0; i < d; i++) {
                for (int j = 0; j < d; j++) {
                    if (i == d - 1 && j == d - 1) {
                        t[i][j] = 0;
                        break;
                    }
                    t[i][j] = idx++;
                }
            }
            return new Board(t);
        }
        return this.goal;
    }

    /**
     * Calculates all the coordinates of tiles within goal board and returns an array. Mind, first element in the array
     * is sentinel and should not be used
     */
    private static int[][] resolveGoalCoordinates(int dimension) {
        int minNum = 1;
        int maxNum = dimension * dimension;
        int[][] goalCoordinates = new int[maxNum][2];  // will go from 1 to maxNum, 0 remains sentinel
        int x = 0;
        int y = 0;
        for (int i = minNum; i < maxNum; i++) {
            if (i % dimension == 0) {
                goalCoordinates[i] = new int[]{x++, y};
                y = 0;
            } else {
                goalCoordinates[i] = new int[]{x, y++};
            }
        }
        return goalCoordinates;
    }
}

