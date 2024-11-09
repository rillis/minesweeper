package br.com.rillis.board;


import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Board {
    public boolean GAMEOVER = false;
    public House[][] houses;
    int width;
    int height;

    boolean win = false;

    boolean isPopulated = false;

    public Board(int size) {
        this.width = size;
        this.height = size;

        houses = new House[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                houses[x][y] = new House(House.TYPE_NORMAL, new Coord(x, y), this);
            }
        }
    }

    public void populate(Coord c, int bombs){

        ArrayList<String> used = new ArrayList<>();
        used.add(c.toString());

        for (House[] hs : houses) {
            for (House h : hs) {
                h.close();
            }
        }

        for (int i = 0; i < bombs; i++) {
            int randomX = c.x;
            int randomY = c.y;
            Coord randomCoord = new Coord(randomX, randomY);
            while(used.contains(randomCoord.toString())){
                randomX = ThreadLocalRandom.current().nextInt(0, width);
                randomY = ThreadLocalRandom.current().nextInt(0, height);
                randomCoord = new Coord(randomX, randomY);
            }
            used.add(randomCoord.toString());
            houses[randomX][randomY].setAsBomb();

            //houses[randomX][randomY].open(); //DEBUG

            for (Coord around : getAround(randomCoord)){
                if(!houses[around.x][around.y].isBomb()){
                    houses[around.x][around.y].addValue();
                }
            }
        }
        isPopulated = true;
    }

    public Coord[] getAround(Coord c) {
        ArrayList<Coord> around = new ArrayList<>();

        int[] dx = {-1, -1, -1, 0, 1, 1, 1, 0};
        int[] dy = {-1, 0, 1, 1, 1, 0, -1, -1};

        for (int i = 0; i < 8; i++) {
            int newX = c.x + dx[i];
            int newY = c.y + dy[i];

            if (newX >= 0 && newX < width && newY >= 0 && newY < height) {
                around.add(new Coord(newX, newY));
            }
        }
        return around.toArray(new Coord[0]);
    }

}
