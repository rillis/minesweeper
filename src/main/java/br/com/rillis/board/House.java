package br.com.rillis.board;

import br.com.rillis.Configs;
import br.com.rillis.MainClass;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class House {
    public static int TYPE_NORMAL = 0;
    public static int TYPE_BOMB = 1;

    public int value;
    public int type;
    public boolean isOpen = false;
    public String text = "";
    private Board board;
    public boolean isFlag = false;

    public JLabel label;
    HashMap<String, Color> colors;

    Coord coord;

    public House(int type, Coord coord, Board board) {
        colors = new HashMap<>();
        colors.put("X", Color.WHITE);
        colors.put("1", Color.CYAN);
        colors.put("2", Color.YELLOW);
        colors.put("3", Color.BLUE);
        colors.put("4", Color.GREEN);
        colors.put("5", Color.MAGENTA);
        colors.put("6", Color.PINK);
        colors.put("7", Color.WHITE);
        colors.put("8", Color.WHITE);

        this.type = type;
        this.value = 0;
        this.coord = coord;
        this.board = board;

        label = new JLabel();
        label.setOpaque(true);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setBackground(Configs.NOTOPEN_COLOR);
        //if(type == TYPE_BOMB) setText("X");
    }

    public void close(){
        label.setText("");
        value = 0;
        type = TYPE_NORMAL;
        label.setBackground(Configs.NOTOPEN_COLOR);
        isOpen = false;
    }

    public boolean isBomb(){
        return type == TYPE_BOMB;
    }

    public boolean hasValue(){
        return type == TYPE_NORMAL && value > 0;
    }

    public void setAsBomb(){
        type = TYPE_BOMB;
        setText("X");
        value = 0;
    }

    public void addValue(){
        if (value < 8){
            value++;
            setText(String.valueOf(value));
        }
    }

    void open(){
        if (!text.isEmpty()) {
            label.setForeground(colors.get(text));
            label.setText(text);
        }
        if(isBomb()){
            label.setBackground(Configs.OPEN_BOMB_COLOR);
        }else{
            label.setBackground(Configs.OPEN_COLOR);
        }
        isOpen = true;
    }

    private void setText(String text){
        this.text = text;
        if (isOpen) {
            label.setForeground(colors.get(text));
            label.setText(text);
        }
    }

    public void clickRight(){
        if(board.win) return;

        if(!isOpen){
            isFlag = true;
            isOpen = true;

            label.setText("#");
            label.setForeground(Color.WHITE);
            label.setBackground(Configs.FLAG_COLOR);
        } else if (isFlag) {
            isFlag = false;
            isOpen = false;

            label.setText("");
            label.setBackground(Configs.NOTOPEN_COLOR);
        }

        if(checkWin()) return;
    }

    public void clickLeft(){
        if(isFlag) return;
        if(board.win) return;

        if (!isOpen && !board.GAMEOVER){
            if(!board.isPopulated) board.populate(coord, Configs.BOMBS);
            open();

            if(checkWin()) return;

            if(isBomb()){
                board.GAMEOVER = true;
                open();

                for(House[] hs : board.houses){
                    for (House h : hs) {
                        if(h.isBomb() && !h.isOpen){
                            h.open();
                        } else if (h.isBomb() && h.isFlag) {
                            h.isOpen = false;
                            h.open();
                        }
                    }
                }

                JOptionPane.showMessageDialog(null, "GAMEOVER");
                MainClass.replay();
            }else{
                if(value == 0){
                    ArrayList<Coord> next = new ArrayList<>();
                    ArrayList<Coord> temp = new ArrayList<>();
                    ArrayList<String> checked = new ArrayList<>();

                    checked.add(coord.toString());

                    for (Coord c : board.getAround(coord)) {
                        if (!board.houses[c.x][c.y].isBomb() && board.houses[c.x][c.y].value == 0){
                            next.add(c);
                            checked.add(c.toString());
                        } else if (!board.houses[c.x][c.y].isBomb() && !board.houses[c.x][c.y].isFlag) {
                            board.houses[c.x][c.y].open();
                        }
                    }

                    if (!next.isEmpty()){
                        boolean ok = false;
                        while(!ok){
                            temp.clear();
                            for (Coord c : next){
                                board.houses[c.x][c.y].open();


                                for (Coord c2 : board.getAround(c)) {
                                    if (!board.houses[c2.x][c2.y].isBomb() && board.houses[c2.x][c2.y].value == 0 && !checked.contains(c2.toString())){
                                        temp.add(c2);
                                        checked.add(c2.toString());
                                    }else if (!board.houses[c2.x][c2.y].isBomb() && !board.houses[c2.x][c2.y].isFlag) {
                                        board.houses[c2.x][c2.y].open();
                                    }
                                }

                            }
                            next.clear();
                            next.addAll(temp);


                            if (next.isEmpty()) ok = true;
                        }
                    }
                    checkWin();
                }
            }


        }
    }

    public boolean isWin(){
        for (House[] hs : board.houses){
            for (House h : hs){
                if (h.isOpen && !h.isBomb() && !h.isFlag);
                else if(h.isFlag && h.isBomb());
                else return false;
            }
        }
        return true;
    }

    public boolean checkWin(){
        if(isWin()){
            board.win = true;
            for(House[] hs : board.houses){
                for (House h : hs){
                    if (h.isBomb()){
                        h.open();
                        h.label.setBackground(Color.GREEN);
                    }
                }
            }

            JOptionPane.showMessageDialog(null, "You win.");

            MainClass.replay();

            return true;
        }
        return false;

    }
}
