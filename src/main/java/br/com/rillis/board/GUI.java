package br.com.rillis.board;

import br.com.rillis.Configs;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLOutput;

public class GUI extends JFrame {
    int w;
    int h;

    int border;
    int cell_size;

    Board board;

    JPanel contentPane;

    public GUI(int size, int border, Board board) {
        this.w = size;
        this.h = size;

        this.border = border;
        this.cell_size = (int) ((size - border * 2) / board.width);

        contentPane = new JPanel();
        contentPane.setBounds(0, 0, w, h);
        contentPane.setLayout(null);

        for (House[] houseRow : board.houses){
            for(House house : houseRow){
                house.label.setSize(cell_size, cell_size);
                house.label.setLocation(cell_size * house.coord.x + border + house.coord.x, cell_size * house.coord.y + border + house.coord.y);
                house.label.addMouseListener(new MouseAdapter() {
                    public void mouseReleased(MouseEvent e) {
                        if(e.getButton() == MouseEvent.BUTTON1){
                            house.clickLeft();
                        }else if(e.getButton() == MouseEvent.BUTTON3){
                            house.clickRight();
                        }

                    }
                });
                contentPane.add(house.label);
            }
        }


        setSize(w+border*2, h+border*2+20);
        setLayout(null);
        setBackground(Configs.BACKGROUND_COLOR);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(contentPane);
        setVisible(true);

        repaint();
        this.board = board;
    }

    public void setBoard(Board board) {
        contentPane.removeAll();

        for (House[] houseRow : board.houses) {
            for (House house : houseRow) {
                house.label.setSize(cell_size, cell_size);
                house.label.setLocation(cell_size * house.coord.x + border + house.coord.x, cell_size * house.coord.y + border + house.coord.y);
                house.label.addMouseListener(new MouseAdapter() {
                    public void mouseReleased(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            house.clickLeft();
                        } else if (e.getButton() == MouseEvent.BUTTON3) {
                            house.clickRight();
                        }

                    }
                });
                contentPane.add(house.label);
            }
        }
        repaint();
    }
}
