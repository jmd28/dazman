package com.mygdx.game;

public class Map {

    Tetris model;
    char[][] map;

    void draw(char[][] cells) {
        // print cells
        for (char[] r : cells) {
            for (char c : r) {
                System.out.printf("%3c", c);
            }
            System.out.println();
        }
    }



    char[][] generate(int w, int h) {
        model = new Tetris();
        model.init(5,9);
        model.genModel();
        model.printModel();

        // convert model to actual map
        char[][] cells = model.upscale();
        char[][] mirror = model.mirrorred(cells);
        char[][] tunnelled = model.tunnelled(mirror);

        // give result
        draw(tunnelled);
        map = tunnelled;
        return tunnelled;
    }

    public static void main(String[] args) {

        // create tetris model of map
        Map map= new Map();
        map.generate(5,9);

    }


}