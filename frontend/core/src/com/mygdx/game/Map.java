package com.mygdx.game;

public class Map {

    Tetris model;
//    char[][] map;

    void draw(char[][] cells) {
        // print cells
        for (char[] r : cells) {
            for (char c : r) {
                System.out.printf("%1c", c);
            }
            System.out.println();
        }
    }

    char[][] generate(int w, int h) {
        Tetris tetris = new Tetris();
        tetris.init(5,9);
        tetris.genModel();
        tetris.printModel();

        // convert model to actual map
        var cells = tetris.upscale();
        var mirror = tetris.mirrorred(cells);
        var tunnelled = tetris.tunnelled(mirror);
        draw(tunnelled);
        return tunnelled;
    }

    public static void main(String[] args) {

        // create tetris model of map
        Map map= new Map();
        map.generate(5,9);

    }


}