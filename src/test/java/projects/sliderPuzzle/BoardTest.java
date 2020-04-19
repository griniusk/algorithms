package projects.sliderPuzzle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;

public class BoardTest {

    @Test
    void hamming() {
        int[][] tiles = {
                {2, 5, 4, 7},
                {9, 1, 3, 8},
                {11, 10, 0, 6},
                {14, 13, 15, 12}};

        Board board = new Board(tiles);
        Assertions.assertEquals(board.hamming(), 12);
    }

    @Test
    public void hummingWhenOneMisplaced() {
        int[][] tiles = {
                {1, 2, 3, 4},
                {0, 6, 7, 8},
                {9, 10, 11, 12,},
                {13, 14, 15, 5}};

        Board board = new Board(tiles);
        Assertions.assertEquals(board.hamming(), 1);
    }

    @Test
    public void hummingWhenThreeMisplaced() {
        int[][] tiles = {
                {1, 2, 3, 10},
                {0, 6, 7, 8},
                {9, 4, 11, 12,},
                {13, 14, 15, 5}};

        Board board = new Board(tiles);
        Assertions.assertEquals(board.hamming(), 3);
    }

    @Test
    public void whenBoardWithHummingOneTestManhattan() {
        int[][] tiles = {
                {1, 0, 3},
                {4, 5, 6},
                {7, 8, 2}};

        Board board = new Board(tiles);
        Assertions.assertEquals(board.manhattan(), 3);
    }

    @Test
    public void isGoal() {
        int[][] tiles1 = {
                {1, 0, 3},
                {4, 5, 6},
                {7, 8, 2}};

        int[][] tiles2 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}};

        Board board1 = new Board(tiles1);
        Board board2 = new Board(tiles2);

        Assertions.assertFalse(board1.isGoal());
        Assertions.assertTrue(board2.isGoal());

    }

    @Test
    public void equals() {
        int[][] tiles1 = {
                {1, 0, 3},
                {4, 5, 6},
                {7, 8, 2}};

        int[][] tiles2 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}};

        int[][] tiles3 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}};

        Board board1 = new Board(tiles1);
        Board board2 = new Board(tiles2);
        Board board3 = new Board(tiles2);
        Board board4 = new Board(tiles3);
        Assertions.assertNotEquals(board2, board1);
        Assertions.assertEquals(board2, board3);
        Assertions.assertEquals(board3, board4);
    }

    @Test
    public void fourNeighbours3x3() {
        int[][] tiles = {
                {1, 2, 3},
                {4, 0, 6},
                {7, 8, 5}};

        Board board = new Board(tiles);
        int size = 0;
        if (board.neighbors() instanceof Collection) {
            size = ((Collection<Board>) board.neighbors()).size();
        }
        Assertions.assertEquals(size, 4);
    }

    @Test
    public void fourNeighbours4x4() {
        int[][] tiles = {
                {1, 2, 3, 10},
                {0, 6, 7, 8},
                {9, 4, 11, 12,},
                {13, 14, 15, 5}};

        Board board = new Board(tiles);
        int size = 0;
        if (board.neighbors() instanceof Collection) {
            size = ((Collection<Board>) board.neighbors()).size();
        }
        Assertions.assertEquals(size, 3);
    }

    @Test
    public void twin_test() {
        Board b = new Board(new int[][]{
                {1, 2, 3},
                {4, 0, 6},
                {7, 8, 5}});
        Board t1 = b.twin();
        Board t2 = b.twin();
        Assertions.assertEquals(t1, t2);
    }

    // UTILITY Methods
    private void printBoard(int[][] tiles) {
        for (int i = 0; i < tiles.length; i++) {
            for (int tile : tiles[i])
                System.out.print(tile + " ");
            System.out.println();
        }
    }
}