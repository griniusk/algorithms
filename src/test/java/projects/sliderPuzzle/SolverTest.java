package projects.sliderPuzzle;

import edu.princeton.cs.algs4.In;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolverTest {

    @Test
    void puzzle00_expectSolutionInZeroMoves() {
        In in = new In("puzzle00.txt");
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        Solver solver = new Solver(initial);
        Assertions.assertEquals(0, solver.moves());
        Assertions.assertTrue(solver.isSolvable());
    }

    @Test
    void puzzle07_expectSolutionInSevenMoves() {
        In in = new In("puzzle07.txt");
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        Solver s = new Solver(initial);
        Assertions.assertEquals(7, s.moves());
    }

    @Test
    void puzzle3x3_01_expectSolutionInOneMove() {
        In in = new In("puzzle3x3-01.txt");
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        Solver s = new Solver(initial);
        Assertions.assertEquals(1, s.moves());
    }

    @Test
    void puzzles_00to50() {
        StringBuilder uri = new StringBuilder("puzzle");
        In in;

        for (int f = 0; f <= 14; f++) {
            if (f < 10) {
                uri.append("0").append(f).append(".txt");
            } else {
                uri.append(f).append(".txt");
            }

            in = new In(uri.toString());
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    tiles[i][j] = in.readInt();
            Board initial = new Board(tiles);
            Solver s = new Solver(initial);
            assertTrue(s.isSolvable());
            Assertions.assertEquals(f, s.moves());

            uri = new StringBuilder("puzzle");
        }
    }

    @Test
    void puzzles3x3_00to31() {
        StringBuilder uri = new StringBuilder("puzzle3x3-");
        In in;

        for (int f = 0; f <= 31; f++) {
            if (f < 10) {
                uri.append("0").append(f).append(".txt");
            } else {
                uri.append(f).append(".txt");
            }

            in = new In(uri.toString());
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    tiles[i][j] = in.readInt();
            Board initial = new Board(tiles);
            Solver s = new Solver(initial);
            Assertions.assertEquals(f, s.moves());

            uri = new StringBuilder("puzzle3x3-");
        }
    }

    @Test
    void unsolvable2x2() {
        for (int k = 1; k < 4; k++) {
            In in = new In("puzzle2x2-unsolvable" + 1 + ".txt");
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    tiles[i][j] = in.readInt();
            Board initial = new Board(tiles);
            Solver s = new Solver(initial);
            assertFalse(s.isSolvable());
        }
    }

    @Test
    void unsolvable3x3() {
        for (int k = 1; k < 3; k++) {
            In in = new In("puzzle3x3-unsolvable" + k + ".txt");
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    tiles[i][j] = in.readInt();
            Board initial = new Board(tiles);
            Solver s = new Solver(initial);
            assertFalse(s.isSolvable());
        }
    }

    @Test
    void unsolvable4x4() {
        In in = new In("puzzle4x4-unsolvable.txt");
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        Solver s = new Solver(initial);
        assertFalse(s.isSolvable());
    }

    //
//    @Test
//    void puzzles4x4_00to50() {
//        StringBuilder uri = new StringBuilder("puzzle4x4-");
//        In in;
//
//        for (int f = 0; f <= 50; f++) {
//            if (f < 10) {
//                uri.append("0").append(f).append(".txt");
//            } else {
//                uri.append(f).append(".txt");
//            }
//
//            in = new In(uri.toString());
//            int n = in.readInt();
//            int[][] tiles = new int[n][n];
//            for (int i = 0; i < n; i++)
//                for (int j = 0; j < n; j++)
//                    tiles[i][j] = in.readInt();
//            Board initial = new Board(tiles);
//            Solver s = new Solver(initial);
//            Assertions.assertEquals(f, s.moves());
//
//            uri = new StringBuilder("puzzle4x4-");
//        }
//    }
//
//    @Test
//    void puzzle4x4_78() {
//        In in = new In("puzzle4x4-78.txt");
//        int n = in.readInt();
//        int[][] tiles = new int[n][n];
//        for (int i = 0; i < n; i++)
//            for (int j = 0; j < n; j++)
//                tiles[i][j] = in.readInt();
//        Board initial = new Board(tiles);
//        Solver s = new Solver(initial);
//        assertTrue(s.isSolvable());
//    }
//
//    @Test
//    void puzzle4x4_80() {
//        In in = new In("puzzle4x4-80.txt");
//        int n = in.readInt();
//        int[][] tiles = new int[n][n];
//        for (int i = 0; i < n; i++)
//            for (int j = 0; j < n; j++)
//                tiles[i][j] = in.readInt();
//        Board initial = new Board(tiles);
//        Solver s = new Solver(initial);
//        assertTrue(s.isSolvable());
//    }
}