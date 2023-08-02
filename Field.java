package lesson5.Battleships;

import java.util.ArrayList;
import java.util.List;

public class Field {

    private char[][] field;
    private char emptyCell = '•';
    private char shipCell = '■';
    private char destroyedShip = 'X';
    private char missedShotCell = '□';

    private char nonPlaceableCell = '–';
    private List<Ship> ships = new ArrayList<>();
    // private Ship[] ships = new Ship[10]


    public Field() {
        field = new char[10][10];
        init();
    }

    private void init() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                field[i][j] = emptyCell;
            }
        }
    }

    public void repaint(boolean forOpponent, boolean[][] busyCells) {
        // Если forOpponent, то не отображаем неподбитые корабли и nonPlaceableCell
        // Если нет, то отображаем все символы
        int iteratorAuxilia = 1;
        System.out.println("   A B C D E F G H I J");
        for (int i = 0; i < field.length; i++) {
            String space = "  ";
            if (iteratorAuxilia == 10) space = " ";
            System.out.print(iteratorAuxilia + space);
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] == shipCell && forOpponent) {
                    System.out.print(Character.toString(emptyCell) + " ");
                }
                if (field[i][j] == shipCell) {
                System.out.print(Character.toString(shipCell) + " ");
                    }
                else if (isBusyCell(busyCells, i, j) && !forOpponent) {
                        System.out.print(Character.toString(nonPlaceableCell) + " ");
                    }
                else {
                    System.out.print(Character.toString(field[i][j]) + " ");
                }
            }
            iteratorAuxilia++;
            System.out.println();
        }
    }

    public void repaint(boolean forOpponent) {
        // Если forOpponent, то не отображаем неподбитые корабли и nonPlaceableCell
        // Если нет, то отображаем все символы
        int iteratorAuxilia = 1;
        System.out.println("   A B C D E F G H I J");
        for (int i = 0; i < field.length; i++) {
            String space = "  ";
            if (iteratorAuxilia == 10) space = " ";
            System.out.print(iteratorAuxilia + space);
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] == shipCell && forOpponent) {
                    System.out.print(Character.toString(emptyCell) + " ");
                }
                else if (field[i][j] == shipCell) {
                    System.out.print(Character.toString(shipCell) + " ");
                }
                else {
                    System.out.print(Character.toString(field[i][j]) + " ");
                }
            }
            iteratorAuxilia++;
            System.out.println();
        }
    }

    public boolean isBusyCell(boolean[][] busyCells, int row, int col) {
        return busyCells[row][col];
    }


    public char getShipCell() {
        return shipCell;
    }

    public void setShipCell(int row, int col, char shipCell) {
        field[row][col] = shipCell;
    }

    public char getCell(int row, int col) {
        return field[row][col];
    }

    public void setCell(int row, int col, char value) {
        field[row][col] = value;
    }

    public char getMissedShotCell() {
        return missedShotCell;
    }

    public char getDestroyedShip() {
        return destroyedShip;
    }

    public char getNonPlaceableCell() {
        return nonPlaceableCell;
    }

    public char getEmptyCell() {
        return emptyCell;
    }
}
