package com.mygdx.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

class Cell {

    int x;
    int y;
    boolean filled = false;
    int tetrominoID;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

// this may yet end up being entirely abstract
class Tetromino {

}

public class Tetris {

    String colours(int x) {
        switch (x%12) {
            case 0:  return "\u001B[31m";
            case 1:  return "\u001B[31;1m";
            case 2:  return "\u001B[32m";
            case 3:  return "\u001B[32;1m";
            case 4:  return "\u001B[33m";
            case 5:  return "\u001B[33;1m";
            case 6:  return "\u001B[34m";
            case 7:  return "\u001B[34;1m";
            case 8:  return "\u001B[35m";
            case 9:  return "\u001B[35;1m";
            case 10: return "\u001B[36m";
            case 11: return "\u001B[36;1m";
        }
        return "\u001B[36;1m";
    }
    String ANSI_RESET = "\u001B[0m";

    private Random ran = new Random();

    // gravity goes left don't question it

    private int WIDTH  = 5;
    private int HEIGHT = 11;

    private Cell[][] chimney;

    public static final char PATH = '•';
    public static final char WALL = '█';


    void init() {
        init(WIDTH,HEIGHT);
    }

    void init(int w, int h) {
        WIDTH=w;
        HEIGHT=h;

        chimney = new Cell[HEIGHT][WIDTH];
        for (int i = 0; i< HEIGHT; i++) for (int j = 0; j<WIDTH; j++)
            chimney[i][j]=new Cell(j,i);
    }

    void printModel() {
        for (Cell[] r : chimney) {
            for (Cell c : r) {
                System.out.printf("%s%2s%s",
                        colours(c.tetrominoID),
                        c.filled ? Integer.toString(c.tetrominoID) : "-",
                        ANSI_RESET
                );
            }
            System.out.println();
        }
    }

    boolean chimFull() {
//        return Arrays.stream(chimney).allMatch(r->
//                Arrays.stream(r).allMatch(c -> c.filled)
//        );
        for (Cell[] r : chimney) for (Cell c : r)
            if (!c.filled) return false;
        return true;
    }

//    boolean colFull(int i) {
//        return Arrays.stream(chimney).allMatch(r -> r[i].filled);
//    }

    void assign(Cell c, int tetromino) {
        c.filled=true;
        c.tetrominoID=tetromino;
    }

    List<Cell> freeNeighbours(Cell c) {
//        return Stream.of(
//                        Pair.of(c.y+1,c.x),
//                        Pair.of(c.y-1,c.x),
//                        Pair.of(c.y,c.x+1),
//                        Pair.of(c.y,c.x-1)
//                )
//                .filter(it -> it.fst>=0 && it.fst < HEIGHT && it.snd>=0 && it.snd < WIDTH)
//                .map(it -> chimney[it.fst][it.snd])
//                .filter(it -> !it.filled)
//                .collect(Collectors.toList());
        List<Pair<Integer,Integer>> indices = Arrays.asList(Pair.of(c.y+1,c.x),
                Pair.of(c.y-1,c.x),
                Pair.of(c.y,c.x+1),
                Pair.of(c.y,c.x-1));

        List<Cell> freeNeighbours = new ArrayList<>();
        for (Pair<Integer,Integer> i : indices)
            if (i.fst >= 0 && i.fst < HEIGHT && i.snd >= 0 && i.snd < WIDTH)
                if (!chimney[i.fst][i.snd].filled)
                    freeNeighbours.add(chimney[i.fst][i.snd]);

        return freeNeighbours;

    }

    void expand(Cell c, int tetromino, int size) {
        assign(c,tetromino);
        final int magicNumber = 3;
        if (size==magicNumber) return;
        List<Cell> choices = freeNeighbours(c);
        if (choices.isEmpty()) return;
        Cell chosen = choices.get(ran.nextInt(choices.size()));

        expand(chosen,tetromino,size+1);
    }

    void genModel() {

        // assign all the cells a tetromino
        int tetromino = 0;

//        // ghost pen lives in tetromino -1 (in middle)
//        assign(chimney[HEIGHT/2][WIDTH-1], -1);
//        assign(chimney[HEIGHT/2][WIDTH-2], -1);
//        assign(chimney[HEIGHT/2+1][WIDTH-1], -1);
//        assign(chimney[HEIGHT/2+1][WIDTH-2], -1);

        while (!chimFull()) {
            // the leftmost unfilled level
            int floor = leftmostUnfilled();
            // this is bad oops
            Cell chosenCell = chimney[ran.nextInt(HEIGHT)][floor];
            if (chosenCell.filled) continue;
            expand(chosenCell,tetromino++,0);
            System.out.println();
            printModel();
            System.out.println();
        }
    }

    private int leftmostUnfilled() {
        for (int i = 0; i<WIDTH; i++) {
            for (Cell[] r : chimney)
                if (!r[i].filled) return i;
        }
        // shouldn't end up here
        return -1;
    }

    char[][] upscale() {
        int fac = 2;
        int BIG_W = WIDTH*fac;
        int BIG_H = HEIGHT*fac+1;

        final int border = -1;

        //create big destination array
        char[][] cells = new char[BIG_H][BIG_W];
        for (int i = 0; i< BIG_H; i++) for (int j = 0; j<BIG_W; j++)
            cells[i][j]=WALL;

        //draw left edge
        for (int i = 0; i< BIG_H; i++) {
            cells[i][0] = PATH;
        }

        //draw top bottom edges
        for (int i = 0; i< BIG_W; i++) {
            cells[0][i] = PATH;
            cells[BIG_H-1][i] = PATH;
        }

        System.out.println("ayeaye");


        // mark cells w/ borders
        for (Cell[] r : chimney) {
            for (Cell c : r) {
                int row = c.y;
                int col = c.x;

                // if piece border above cell
                if (row > 0 && chimney[row - 1][col].tetrominoID != c.tetrominoID) {
                    for (int i = 0; i < fac; i++) {
                        cells[row * fac][col * fac + i] = PATH;
                    }
                }
                // if border left of cell
                if (col > 0 && chimney[row][col - 1].tetrominoID != c.tetrominoID) {
                    for (int i = 0; i < fac; i++) {
                        cells[row * fac + i][col * fac] = PATH;
                    }
                }
                // shitty border fix for corner pieces
                if (row>0 && col>0 &&
                        chimney[row - 1][col].tetrominoID == c.tetrominoID &&
                        chimney[row][col - 1].tetrominoID == c.tetrominoID &&
                        chimney[row-1][col-1].tetrominoID != c.tetrominoID
                ) {
                    cells[row * fac][col*fac]=PATH;
                }
            }
        }
        return cells;
    }

    char[][] mirrorred(char[][] half) {
        // tasty risky indexing
        int h = half.length;
        int w = half[0].length;

        //mirror about last column
        char[][] result = new char[h][w*2-1];
        for (int i = 0; i<w;i++) {
            for (int j = 0; j<h;j++) {
                result[j][i]=half[j][i];
                result[j][w*2-i-2]=half[j][i];
            }
        }
        return result;
    }

    char[][] tunnelled(char[][] map) {
        // tasty risky indexing
        int h = map.length;
        int w = map[0].length;

        char[][] result = new char[h+2][w+2];

        // draw border around map
        for (int i = 0; i<w+2;i++) {
            for (int j = 0; j<h+2;j++) {
                // add a border of walls
                if (i==0 || j==0 || i==w+1 || j==h+1) {
                    result[j][i]=WALL;
                    continue;
                }
                // else copy map over
                result[j][i]=map[j-1][i-1];
            }
        }

        // add sexy interdimensional portals
        int numPortals = h/11;
        int scalingFactor = h/3;
//        var candidateRows = ran
//                // force tunnels to be spaced out by using upscaling again
//                .ints(0,h/scalingFactor)
//                .distinct()
//                // upscale and translate onto center of each division
//                .map(i -> scalingFactor*i+scalingFactor/2)
//                .limit(numPortals);
        List<Integer> portalRows = new ArrayList<>();
        for (int i = 0; i<numPortals; i++) {
            int pos = ran.nextInt(h/scalingFactor);
            int scaledPos = scalingFactor*pos+scalingFactor/2;
            portalRows.add(scaledPos);
        }

        for (int r : portalRows) {
            result[r+1][0] = PATH; result[r+1][w+1] = PATH;
        }

        return result;
    }


}
