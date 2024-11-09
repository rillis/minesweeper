package br.com.rillis;

import br.com.rillis.board.Board;
import br.com.rillis.board.GUI;

public class MainClass {
    public static GUI gui;
    public static Board activeBoard;
    public static void main(String[] args) {
        activeBoard = new Board(Configs.SIZE);
        gui = new GUI(600, 20, activeBoard);
    }

    public static void replay(){
        activeBoard = new Board(Configs.SIZE);
        gui.setBoard(activeBoard);
    }
}
