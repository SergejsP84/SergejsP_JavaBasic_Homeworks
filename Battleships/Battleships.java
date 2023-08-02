package lesson5.Battleships;

/*
Консольное приложение.
Одновременно в игре могут участвовать только два человека.
Игроки вводят имена.
У каждого игрока есть своё поле - квадрат 10х10 клеток
По очереди расставляют свои корабли.
4 однопалубных корабля,
3 двухпалубных,
2 трехпалубных,
1 четырёхпалубный.
Корабли можно располагать только по горизонтали или по вертикали.
Между кораблями должна быть минимум одна клетка
Игроки не видят расположение кораблей друг друга.
Начинается игра. Первый игрок делает выстрел, сообщая нашему приложению координаты предполагаемой цели - номер клетки по горизонтали и номер клетки по вертикали.
Если выстрел первого игрока оказался удачным, и он поразил цель, то возможно два варианта развития событий.
Первый вариант: в указанной игроком клетки находится корабль, то, если корабль однопалубный, игрок "убил" корабль, если не однопалубный, то ранил. Следующий ход за первым игроком.
Второй вариант: игрок не попал ни в какой корабль, ход переходит второму игроку.
Таким образом, возвращаемся в пункт 8, передавая ход друг другу, игроки пытаются как можно раньше уничтожить корабли друг друга. Тот, кто первым, уничтожит все корабли - победитель. Печатаем поздравление и выход из игры.
*/

import java.util.*;

public class Battleships {
    private static Ship[] oppShipArray;
    public static void main(String[] args) {
//        System.setProperty("console.encoding", "UTF-8");
        // 1. Создать игроков (запросить их имена в консоли
        // 2. Создание кораблей и их расстановка по полям
        // 3. Сам процесс игры (до тех пор, пока у одного из игроков не закончатся корабли).
        // 4. Объявление победителя

        // 1. Во время расстановки корабли не должны соприкасаться или перпесекаться друг с другом, даже углами.
        // 2. Во время расстановки корабли не должны выходить за пределы поля.
        // 3. В процессе игры координаты выстрела должны проверяться на корректность (не вышли ли за пределы поля, не стреляли ли туда раньше

//      ======================= Entering names and creating objects =======================

        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your name:");
        String playersName = sc.nextLine();
        System.out.println("Please enter your opponent's name:");
        String oppName = sc.nextLine();

        Field humanField = new Field();
        Field oppField = new Field();

        Player humanPlayer = new Player(playersName, humanField);
        Player oppPlayer = new Player(oppName, oppField);

        boolean isVertical = false;
        boolean[][] busyCells = new boolean[10][10];
        boolean[][] oppBusyCells = new boolean[10][10];
        Ship[] submarines = new Ship[4];
        Ship[] destroyers = new Ship[3];
        Ship[] battlecruisers = new Ship[2];
        Ship[] carrier = new Ship[1];
        Ship[] oppSubmarines = new Ship[4];
        Ship[] oppDestroyers = new Ship[3];
        Ship[] oppBattlecruisers = new Ship[2];
        Ship[] oppCarrier = new Ship[1];
        byte humanHitPoints = 20;
        byte oppHitPoints = 20;
        oppShipArray = new Ship[10];
        int[][] oppCarrierCells = new int[4][4];
        int [][][] oppBattlecruiserCells = new int [2][3][3];
        int [][][] oppDestroyerCells = new int [3][2][2];
        int [][][] oppSubmarineCells = new int [4][1][1];
        char[][] oppShipContainingCells = new char[10][10];
        boolean[][] oppDestroyedCells = new boolean[10][10];
        boolean[][] humanDestroyedCells = new boolean[10][10];


        // Да, то что следует дальше, все эти переменные есть невероятно топорно, примитивно, посконно,
        // кондово, домоткано, избяно и сермяжно. Но я уже каждый метод по нескольку раз переделывал, ибо
        // ничего не работает так, как надо :( Двумерные массивы присутствуют, но когда я полез в трёхмерные,
        // началась невиданная хрень (из серии "метод распознавания попадания в 4-палубный вохвращает true
        // вне зависимости от того, куда именно попал").

        // Поэтому, чтобы наконец это закончить, я собираюсь далее ввести 40 переменных с интами. Грешен есмь :(




        humanField.repaint(false, busyCells);

        //      ======================= Placing ships for the human player =======================

        System.out.println("Place your ships! Please start with the Carrier (4 decks). Enter HOR for horizontal orientation, or VER for vertical");
        humanField = placeHumanCarrier(humanField, busyCells, isVertical);
        carrier[0] = new Ship(4, isVertical);

        for (int i = 1; i <= 2; i++) {
            System.out.println("Place your battlecruiser " + i + " (3 decks). First of all, enter HOR for horizontal orientation, or VER for vertical");
            humanField = placeHumanBattlecruisers(humanField, busyCells, isVertical);
            battlecruisers[i - 1] = new Ship(3, isVertical);
        }

        for (int i = 1; i <= 3; i++) {
            System.out.println("Now, place your destroyer " + i + " (2 decks). First of all, enter HOR for horizontal orientation, or VER for vertical");
            humanField = placeHumanDestroyers(humanField, busyCells, isVertical);
            destroyers[i - 1] = new Ship(2, isVertical);
        }

        for (int i = 1; i <= 4; i++) {
            System.out.println("Please enter the coordinates of your Submarine " + i + " (one deck)");
            humanField = placeHumanSubmarines(humanField, busyCells);
            submarines[i - 1] = new Ship(1);
        }

        //      ======================= Placing ships for the AI player =======================
        oppField = placeOppShips(oppField, oppBusyCells, oppShipContainingCells, oppCarrierCells, oppBattlecruiserCells, oppDestroyerCells, oppDestroyers, oppBattlecruisers, oppCarrier);
        oppField = placeOppSubmarines(oppField, oppBusyCells, oppShipContainingCells,oppSubmarineCells, oppSubmarines);
        System.out.println(oppName + " says: OK, I have placed my ships as well! Let the game begin!");




        //      ======================= Game process =======================
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (oppField.getCell(i, j) == oppField.getNonPlaceableCell()) oppField.setCell(i, j, oppField.getEmptyCell());
            }
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (humanField.getCell(i, j) == humanField.getNonPlaceableCell()) humanField.setCell(i, j, humanField.getEmptyCell());
            }
        }


        while (oppHitPoints > 0 && humanHitPoints > 0) {
            humanTurn(oppField, oppHitPoints, oppShipArray, oppCarrierCells, oppBattlecruiserCells, oppDestroyerCells, oppSubmarineCells, oppDestroyedCells);
            // opponent's turn will be added here later
        }

    }

    //      ======================= Main method ends here ===========================

    //      ======================= Human player's turn processing method ===========

    public static void humanTurn(Field oppField, byte oppHitPoints, Ship[] shipArray, int[][] oppCarrierCells, int[][][]oppBattlecruiserCells, int[][][]oppDestroyerCells, int[][][] oppSubmarineCells, boolean[][] oppDestroyedCells) {
        System.out.println("oppCarrierCells:");
        for (int i = 0; i < oppCarrierCells.length; i++) {
            for (int j = 0; j < oppCarrierCells[i].length; j++) {
                System.out.print(oppCarrierCells[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("oppBattlecruiserCells:");
        for (int i = 0; i < oppBattlecruiserCells.length; i++) {
            for (int j = 0; j < oppBattlecruiserCells[i].length; j++) {
                for (int k = 0; k < oppBattlecruiserCells[i][j].length; k++) {
                    System.out.print(oppBattlecruiserCells[i][j][k] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
        oppField.repaint(false); // CHANGE IT TO TRUE LATER!!!!!
        System.out.println("It's your move! Enter the coordinates to strike at");
        Scanner sc = new Scanner(System.in);
        String strikeCoordinate = sc.nextLine();
        InputValidator validator = new InputValidator();
                while (!validator.isValidInput(strikeCoordinate)) {
            System.out.println("Erroneous input! Your input should consist of 2 characters (a letter and a digit) only. Please re-enter the coordinates.");
            strikeCoordinate = sc.nextLine();
        }
        List<Integer> numberRange = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        List<String> letterRange = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J"));
        int letterIndex = -1;
        int numberIndex = -1;
        int letterPosition = -1;
        int numberPosition = -1;

        if (letterRange.contains(Character.toString(strikeCoordinate.charAt(0)).toUpperCase())) {
            letterPosition = 0;
            numberPosition = 1;
        } else {
            letterPosition = 1;
            numberPosition = 0;
        }

        letterIndex = letterRange.indexOf(Character.toString(strikeCoordinate.charAt(letterPosition)).toUpperCase());
        if (numberPosition + 1 < strikeCoordinate.length() && strikeCoordinate.charAt(numberPosition) == '1' && strikeCoordinate.charAt(numberPosition + 1) == '0') {
            numberIndex = 9;
        } else {
            numberIndex = Integer.parseInt(strikeCoordinate.substring(numberPosition)) - 1;
        }

        char cell = oppField.getCell(numberIndex, letterIndex);
//        if (isHitInSubmarine(oppField, numberIndex, letterIndex, oppSubmarineCells)) {
//            oppHitPoints--;
//            oppDestroyedCells[numberIndex][letterIndex] = true;
//
//
//        } else if (isHitInDestroyer(oppField, numberIndex, letterIndex, oppDestroyerCells)) {
//            oppHitPoints--;
//            oppDestroyedCells[numberIndex][letterIndex] = true;
//
//
//        } else if (isHitInBattlecruiser(oppField, numberIndex, letterIndex, oppBattlecruiserCells)) {
//            oppHitPoints--;
//            oppDestroyedCells[numberIndex][letterIndex] = true;
//
//        }

       if (isHitInCarrier(oppField, numberIndex, letterIndex, oppCarrierCells)) {
            oppHitPoints--;
            oppDestroyedCells[numberIndex][letterIndex] = true;
            oppShipArray[0].setDecks(oppShipArray[0].getDecks() - 1);
            oppField.setCell(numberIndex, letterIndex, oppField.getDestroyedShip());
            if (oppShipArray[0].getDecks() == 0) {
                markAround(oppField, oppDestroyedCells);
                System.out.println("Great! You have destroyed the opponent's carrier!");
                humanTurn(oppField, oppHitPoints, oppShipArray, oppCarrierCells, oppBattlecruiserCells, oppDestroyerCells, oppSubmarineCells, oppDestroyedCells);
            } else {
                System.out.println("You have scored a hit on an enemy carrier! Barrage around to finish it off!"); //CHANGE THIS LATER
                humanTurn(oppField, oppHitPoints, oppShipArray, oppCarrierCells, oppBattlecruiserCells, oppDestroyerCells, oppSubmarineCells, oppDestroyedCells);
            }
        } else if (isHitInBattlecruiser(oppField, numberIndex, letterIndex, oppBattlecruiserCells)) {
               oppHitPoints--;
               oppDestroyedCells[numberIndex][letterIndex] = true;
                                    if (oppDestroyedCells[numberIndex][letterIndex] == true) System.out.println("Destroyed cell setting OK");
               int humanBattlecruiserIndex = -1;
               if (oppField.getCell(numberIndex, letterIndex) == oppBattlecruiserCells[0][0][0] ||
                       oppField.getCell(numberIndex, letterIndex) == oppBattlecruiserCells[0][1][1] ||
                       oppField.getCell(numberIndex, letterIndex) == oppBattlecruiserCells[0][2][2]) humanBattlecruiserIndex = 1;
               if (oppField.getCell(numberIndex, letterIndex) == oppBattlecruiserCells[1][0][0] ||
                       oppField.getCell(numberIndex, letterIndex) == oppBattlecruiserCells[1][1][1] ||
                       oppField.getCell(numberIndex, letterIndex) == oppBattlecruiserCells[1][2][2]) humanBattlecruiserIndex = 2;
                                    if (humanBattlecruiserIndex == 1) System.out.println("Index successfully set to 1");
                                    if (humanBattlecruiserIndex == 2) System.out.println("Index successfully set to 2");
                                    if (!(humanBattlecruiserIndex==1) && !(humanBattlecruiserIndex==2)) System.out.println("INDEX NOT SET!!!!");
                                    System.out.println("Current indexed battlecruiser deck count is " + oppShipArray[humanBattlecruiserIndex].getDecks());
               oppShipArray[humanBattlecruiserIndex].setDecks(oppShipArray[humanBattlecruiserIndex].getDecks() - 1);
                                    System.out.println("Deck count after removal is " + oppShipArray[humanBattlecruiserIndex].getDecks());
               oppField.setCell(numberIndex, letterIndex, oppField.getDestroyedShip());
               if (oppShipArray[humanBattlecruiserIndex].getDecks() == 0) {
                   markAround(oppField, oppDestroyedCells);
                   System.out.println("Great! You have destroyed the opponent's battlecruiser!");
                   humanTurn(oppField, oppHitPoints, oppShipArray, oppCarrierCells, oppBattlecruiserCells, oppDestroyerCells, oppSubmarineCells, oppDestroyedCells);
               } else {
                   System.out.println("You have scored a hit on an enemy battlecruiser! Barrage around to finish it off!"); //CHANGE THIS LATER
                   humanTurn(oppField, oppHitPoints, oppShipArray, oppCarrierCells, oppBattlecruiserCells, oppDestroyerCells, oppSubmarineCells, oppDestroyedCells);
               }

        }   else {
            oppField.setCell(numberIndex, letterIndex, oppField.getMissedShotCell());
            System.out.println("Oops, you missed!");
        }
    }

    // ====================== Marking cells around a destroyed ship as misses =========================
    public static void markAround(Field oppField, boolean[][] oppDestroyedCells) {

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (oppDestroyedCells[i][j]) {
                    if (i - 1 >= 0 && j - 1 >= 0 && !(oppField.getCell(i - 1, j - 1) == oppField.getDestroyedShip()))
                        oppField.setCell(i - 1, j - 1, oppField.getMissedShotCell());
                    if (i - 1 >= 0 && !(oppField.getCell(i - 1, j) == oppField.getDestroyedShip()))
                        oppField.setCell(i - 1, j, oppField.getMissedShotCell());
                    if (i - 1 >= 0 && j + 1 < 10 && !(oppField.getCell(i - 1, j + 1) == oppField.getDestroyedShip()))
                        oppField.setCell(i - 1, j + 1, oppField.getMissedShotCell());
                    if (j - 1 >= 0 && !(oppField.getCell(i, j - 1) == oppField.getDestroyedShip()))
                        oppField.setCell(i, j - 1, oppField.getMissedShotCell());
                    if (j + 1 < 10 && !(oppField.getCell(i, j + 1) == oppField.getDestroyedShip()))
                        oppField.setCell(i, j + 1, oppField.getMissedShotCell());
                    if (i + 1 < 10 && j - 1 >= 0 && !(oppField.getCell(i + 1, j - 1) == oppField.getDestroyedShip()))
                        oppField.setCell(i + 1, j - 1, oppField.getMissedShotCell());
                    if (i + 1 < 10 && !(oppField.getCell(i + 1, j) == oppField.getDestroyedShip()))
                        oppField.setCell(i + 1, j, oppField.getMissedShotCell());
                    if (i + 1 < 10 && j + 1 < 10 && !(oppField.getCell(i + 1, j + 1) == oppField.getDestroyedShip()))
                        oppField.setCell(i + 1, j + 1, oppField.getMissedShotCell());
                }
            }
        }
    }


    //      ======================= Hit verification methods for the human player =======================

    public static boolean isHitInCarrier(Field oppField, int numberIndex, int letterIndex, int[][] oppCarrierCells) {
        // Этот и следующий методы в полном отчаянии спёрты мною у ChatGРT, но и они ни черта не работают
        // Iterate through all potential starting cells of the carrier configuration
        for (int i = 0; i < oppCarrierCells.length; i++) {
            for (int j = 0; j < oppCarrierCells[i].length; j++) {
                // Check if the current cell is within the bounds of the field (10x10)
                int currentRow = numberIndex - i + j;
                int currentCol = letterIndex - j;
                if (currentRow >= 0 && currentRow < 10 && currentCol >= 0 && currentCol < 10) {
                    // Check if the cell at (currentRow, currentCol) matches the carrier configuration
                    if (oppField.getCell(currentRow, currentCol) != oppCarrierCells[i][j]) {
                        // If any cell does not match, move on to the next potential starting cell
                        break;
                    }
                } else {
                    // If any cell is out of bounds, move on to the next potential starting cell
                    break;
                }
                // If all cells match the carrier configuration, return true
                if (j == oppCarrierCells[i].length - 1) {
                    return true;
                }
            }
        }
        // If no match is found, return false
        return false;
    }





    public static boolean isHitInBattlecruiser(Field oppField, int numberIndex, int letterIndex, int[][][] oppBattlecruiserCells) {
        // Iterate through all potential starting cells of the battlecruiser configuration
        for (int i = 0; i < oppBattlecruiserCells.length; i++) {
            for (int j = 0; j < oppBattlecruiserCells[i].length; j++) {
                // Check if the current cell is within the bounds of the field (10x10)
                int currentRow = numberIndex - i + j;
                int currentCol = letterIndex - j;
                if (currentRow >= 0 && currentRow < 10 && currentCol >= 0 && currentCol < 10) {
                    // Check if the cell at (currentRow, currentCol) matches the battlecruiser configuration
                    if (oppField.getCell(currentRow, currentCol) != oppBattlecruiserCells[i][j][j]) {
                        // If any cell does not match, move on to the next potential starting cell
                        break;
                    }
                } else {
                    // If any cell is out of bounds, move on to the next potential starting cell
                    break;
                }
                // If all cells match the battlecruiser configuration, return true
                if (j == oppBattlecruiserCells[i].length - 1) {
                    return true;
                }
            }
        }
        // If no match is found, return false
        return false;
    }







    public static boolean isHitInDestroyer(Field oppField, int numberIndex, int letterIndex, int[][][] oppDestroyerCells) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                if (oppField.getCell(numberIndex, letterIndex) == oppDestroyerCells[i][j][j]) {
                    return true;
                }
            }
        }
        return false;
    }


    public static boolean isHitInSubmarine(Field oppField, int numberIndex, int letterIndex, int[][][] oppSubmarineCells) {
        for (int i = 0; i < 4; i++) {
            if (oppField.getCell(numberIndex, letterIndex) == oppSubmarineCells[i][0][0]) {
                return true;
            }
        }
        return false;
    }


    public static boolean isKill(Field oppField, Ship[] shipArray, int numberIndex, int letterIndex) {
        char destroyedShip = oppField.getDestroyedShip();

        for (Ship ship : shipArray) {
            int shipSize = ship.getDecks();
            boolean isVertical = ship.isVertical();
            if (isVertical) {
                if (numberIndex + shipSize < 10) {
                    boolean hasNonDestroyedCellsBelow = false;
                    for (int i = 1; i < shipSize; i++) {
                        if (oppField.getCell(numberIndex + i, letterIndex) != destroyedShip) {
                            hasNonDestroyedCellsBelow = true;
                            break;
                        }
                    }
                    if (hasNonDestroyedCellsBelow) {
                        return false;
                    }
                }

                if (numberIndex - 1 >= 0) {
                    boolean hasNonDestroyedCellsAbove = false;
                    for (int i = 1; i < shipSize; i++) {
                        if (oppField.getCell(numberIndex - i, letterIndex) != destroyedShip) {
                            hasNonDestroyedCellsAbove = true;
                            break;
                        }
                    }
                    if (hasNonDestroyedCellsAbove) {
                        return false;
                    }
                }
            } else {
                if (letterIndex + shipSize < 10) {
                    boolean hasNonDestroyedCellsLeft = false;
                    for (int i = 1; i < shipSize; i++) {
                        if (oppField.getCell(numberIndex, letterIndex + i) != destroyedShip) {
                            hasNonDestroyedCellsLeft = true;
                            break;
                        }
                    }
                    if (hasNonDestroyedCellsLeft) {
                        return false;
                    }
                }
                if (letterIndex - 1 >= 0) {
                    boolean hasNonDestroyedCellsRight = false;
                    for (int i = 1; i < shipSize; i++) {
                        if (oppField.getCell(numberIndex, letterIndex - i) != destroyedShip) {
                            hasNonDestroyedCellsRight = true;
                            break;
                        }
                    }
                    if (hasNonDestroyedCellsRight) {
                        return false;
                    }
                }
            }
        }

        return true;
    }



    // метод обозначения уничтоженного корабля, но я от него уже отказался, заменив его на markAround, так как он
    // никак не проставлял одну из клеток. Пока не удалял, мало ли придётся вернуться к чему-то подобному
    public static void markAsKilled(Field oppField, int numberIndex, int letterIndex) {
        char destroyedShip = oppField.getDestroyedShip();
        oppField.setCell(numberIndex, letterIndex, destroyedShip);
        if (letterIndex + 1 < 10 && oppField.getCell(numberIndex, letterIndex + 1) != destroyedShip) {
            oppField.setCell(numberIndex, letterIndex + 1, oppField.getMissedShotCell());
        }
        if (letterIndex - 1 >= 0 && oppField.getCell(numberIndex, letterIndex - 1) != destroyedShip) {
            oppField.setCell(numberIndex, letterIndex - 1, oppField.getMissedShotCell());
        }
        if (numberIndex + 1 < 10 && oppField.getCell(numberIndex + 1, letterIndex) != destroyedShip) {
            oppField.setCell(numberIndex + 1, letterIndex, oppField.getMissedShotCell());
        }
        if (numberIndex - 1 >= 0 && oppField.getCell(numberIndex - 1, letterIndex) != destroyedShip) {
            oppField.setCell(numberIndex - 1, letterIndex, oppField.getMissedShotCell());
        }
        if (numberIndex + 1 < 10 && letterIndex + 1 < 10 && oppField.getCell(numberIndex + 1, letterIndex + 1) != destroyedShip) {
            oppField.setCell(numberIndex + 1, letterIndex + 1, oppField.getMissedShotCell());
        }
        if (numberIndex + 1 < 10 && letterIndex - 1 >= 0 && oppField.getCell(numberIndex + 1, letterIndex - 1) != destroyedShip) {
            oppField.setCell(numberIndex + 1, letterIndex - 1, oppField.getMissedShotCell());
        }
        if (numberIndex - 1 >= 0 && letterIndex - 1 >= 0 && oppField.getCell(numberIndex - 1, letterIndex - 1) != destroyedShip) {
            oppField.setCell(numberIndex - 1, letterIndex - 1, oppField.getMissedShotCell());
        }
        if (numberIndex - 1 >= 0 && letterIndex + 1 < 10 && oppField.getCell(numberIndex - 1, letterIndex + 1) != destroyedShip) {
            oppField.setCell(numberIndex - 1, letterIndex + 1, oppField.getMissedShotCell());
        }
        //A crutch to resolve the one-cell-left-unmarked issue

        if (numberIndex - 1 >= 0) {
            if (oppField.getCell(numberIndex - 1, letterIndex) == destroyedShip) {
                if (numberIndex - 2 >= 0) {
                    if (oppField.getCell(numberIndex - 2, letterIndex) == destroyedShip) {
                        if (numberIndex - 3 >= 0 && oppField.getCell(numberIndex - 3, letterIndex) == destroyedShip) {
                            if (numberIndex - 4 >= 0) oppField.setCell(numberIndex - 4, letterIndex, oppField.getMissedShotCell());
                        } else {
                            oppField.setCell(numberIndex - 3, letterIndex, oppField.getMissedShotCell());
                        }
                    } else {
                        oppField.setCell(numberIndex - 2, letterIndex, oppField.getMissedShotCell());
                    }
                }
            } else if (oppField.getCell(numberIndex - 1, letterIndex) == oppField.getEmptyCell()) {
                oppField.setCell(numberIndex - 2, letterIndex, oppField.getMissedShotCell());
            }
        }

        if (numberIndex + 1 < 10) {
            if (oppField.getCell(numberIndex + 1, letterIndex) == destroyedShip) {
                if (numberIndex + 2 < 10) {
                    if (oppField.getCell(numberIndex + 2, letterIndex) == destroyedShip) {
                        if (numberIndex + 3 < 10 && oppField.getCell(numberIndex + 3, letterIndex) == destroyedShip) {
                            if (numberIndex + 4 < 10) oppField.setCell(numberIndex + 4, letterIndex, oppField.getMissedShotCell());
                        } else {
                            oppField.setCell(numberIndex + 3, letterIndex, oppField.getMissedShotCell());
                        }
                    } else {
                        oppField.setCell(numberIndex + 2, letterIndex, oppField.getMissedShotCell());
                    }
                }
            } else if (oppField.getCell(numberIndex + 1, letterIndex) == oppField.getEmptyCell()) {
                oppField.setCell(numberIndex + 2, letterIndex, oppField.getMissedShotCell());
            }
        }

        if (letterIndex - 1 >= 0) {
            if (oppField.getCell(numberIndex, letterIndex  - 1) == destroyedShip) {
                if (letterIndex - 2 >= 0) {
                    if (oppField.getCell(numberIndex, letterIndex  - 2) == destroyedShip) {
                        if (letterIndex - 3 >= 0 && oppField.getCell(numberIndex, letterIndex - 3) == destroyedShip) {
                            if (letterIndex - 4 >= 0) oppField.setCell(numberIndex, letterIndex - 4, oppField.getMissedShotCell());
                        } else {
                            oppField.setCell(numberIndex, letterIndex - 3, oppField.getMissedShotCell());
                        }
                    } else {
                        oppField.setCell(numberIndex, letterIndex - 2, oppField.getMissedShotCell());
                    }
                }
            } else if (oppField.getCell(numberIndex, letterIndex - 1) == oppField.getEmptyCell()) {
                oppField.setCell(numberIndex, letterIndex - 2, oppField.getMissedShotCell());
            }
        }

        if (letterIndex + 1 < 10) {
            if (oppField.getCell(numberIndex, letterIndex + 1) == destroyedShip) {
                if (letterIndex + 2 < 10) {
                    if (oppField.getCell(numberIndex, letterIndex + 2) == destroyedShip) {
                        if (letterIndex + 3 < 10 && oppField.getCell(numberIndex, letterIndex + 3) == destroyedShip) {
                            if (letterIndex + 4 < 10) oppField.setCell(numberIndex, letterIndex + 4, oppField.getMissedShotCell());
                        } else {
                            oppField.setCell(numberIndex, letterIndex + 3, oppField.getMissedShotCell());
                        }
                    } else {
                        oppField.setCell(numberIndex, letterIndex + 2, oppField.getMissedShotCell());
                    }
                }
            } else if (oppField.getCell(numberIndex, letterIndex + 1) == oppField.getEmptyCell()) {
                oppField.setCell(numberIndex, letterIndex + 2, oppField.getMissedShotCell());
            }
        }

    }

    //      ======================= 1-decker placement method =======================
    public static Field placeHumanSubmarines(Field humanField, boolean[][] busyCells) {
        char[][] humanShipContainingCells = new char[10][10];
        Scanner sc = new Scanner(System.in);
        String shipCoordinate = sc.nextLine();
        InputValidator validator = new InputValidator();
        while (!validator.isValidInput(shipCoordinate)) {
            System.out.println("Erroneous input! Your input should consist of 2 characters (a letter and a digit) only. Please re-enter the coordinates.");
            shipCoordinate = sc.nextLine();
        }

        List<Integer> numberRange = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        List<String> letterRange = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J"));
        int letterIndex = -1;
        int numberIndex = -1;
        int letterPosition = -1;
        int numberPosition = -1;

        if (letterRange.contains(Character.toString(shipCoordinate.charAt(0)).toUpperCase())) {
            letterPosition = 0;
            numberPosition = 1;
        } else {
            letterPosition = 1;
            numberPosition = 0;
        }

        letterIndex = letterRange.indexOf(Character.toString(shipCoordinate.charAt(letterPosition)).toUpperCase());
        if (numberPosition + 1 < shipCoordinate.length() && shipCoordinate.charAt(numberPosition) == '1' && shipCoordinate.charAt(numberPosition + 1) == '0') {
            numberIndex = 9;
        } else {
            numberIndex = Integer.parseInt(shipCoordinate.substring(numberPosition)) - 1;
        }

        if (humanShipContainingCells[numberIndex][letterIndex] == humanField.getShipCell() ||
                busyCells[numberIndex][letterIndex]) {
            System.out.println("This cell is unavailable. Please enter a different one (ships may not touch or overlap)");
            return placeHumanSubmarines(humanField, busyCells);
        }

        humanShipContainingCells[numberIndex][letterIndex] = humanField.getShipCell();
        if (numberIndex - 1 >= 0) busyCells[numberIndex - 1][letterIndex] = true;
        if (numberIndex + 1 < 10) busyCells[numberIndex + 1][letterIndex] = true;
        if (letterIndex - 1 >= 0) busyCells[numberIndex][letterIndex - 1] = true;
        if (letterIndex + 1 < 10) busyCells[numberIndex][letterIndex + 1] = true;
        if (numberIndex - 1 >= 0 && letterIndex - 1 >= 0) busyCells[numberIndex - 1][letterIndex - 1] = true;
        if (numberIndex + 1 < 10 && letterIndex - 1 >= 0) busyCells[numberIndex + 1][letterIndex - 1] = true;
        if (numberIndex + 1 < 10 && letterIndex + 1 < 10) busyCells[numberIndex + 1][letterIndex + 1] = true;
        if (numberIndex - 1 >= 0 && letterIndex + 1 < 10) busyCells[numberIndex - 1][letterIndex + 1] = true;

        humanField.setShipCell(numberIndex, letterIndex, humanField.getShipCell());
        humanField.repaint(false, busyCells);
        return humanField;
    }

    //      ======================= 3-decker placement method =======================

    public static Field placeHumanBattlecruisers(Field humanField, boolean[][] busyCells, boolean isVertical) {
        Scanner scPlace = new Scanner(System.in);
        String shipOrientation = scPlace.nextLine();
        String shipCoordinate = "";
        char[][] humanShipContainingCells = new char[10][10];
        while (!shipOrientation.toUpperCase().equals("HOR") && !shipOrientation.toUpperCase().equals("VER")) {
            System.out.println("Please enter HOR for horizontal or VER for vertical");
            shipOrientation = scPlace.nextLine();
        }
        humanField.repaint(false, busyCells);
        if (shipOrientation.toUpperCase().equals("HOR")) {
            System.out.println("Please specify the LEFTMOST cell of your ship (letter within the range of A to I and digit");
            Scanner sc = new Scanner(System.in);
            shipCoordinate = sc.nextLine();
            InputValidator validator = new InputValidator();
            while (!validator.isValidInput(shipCoordinate)) {
                System.out.println("Erroneous input! Your input should consist of 2 characters (a letter and a digit) only. Please re-enter the coordinates.");
                shipCoordinate = sc.nextLine();
            }
            while (shipCoordinate.charAt(0) == 'J' || shipCoordinate.charAt(0) == 'j') {
                System.out.println("Erroneous input! Leftmost cell of a 3-decker cannot be in column J. Please re-enter");
                shipCoordinate = sc.nextLine();
            }
            while (shipCoordinate.charAt(0) == 'I' || shipCoordinate.charAt(0) == 'i') {
                System.out.println("Erroneous input! Leftmost cell of a 3-decker cannot be in column I. Please re-enter");
                shipCoordinate = sc.nextLine();
            }

        }
        if (shipOrientation.toUpperCase().equals("VER")) {
            System.out.println("Please specify the UPPERMOST cell of your ship (letter and digit within the range of 1 to 9");
            Scanner sc = new Scanner(System.in);
            shipCoordinate = sc.nextLine();
            InputValidator validator = new InputValidator();
            while (!validator.isValidInput(shipCoordinate)) {
                System.out.println("Erroneous input! Your input should consist of 2 characters (a letter and a digit) only. Please re-enter the coordinates.");
                shipCoordinate = sc.nextLine();
            }
            if (shipCoordinate.length() > 2) {
                while (Integer.parseInt(shipCoordinate.substring(1)) == 10) {
                    System.out.println("Erroneous input! Uppermost cell of a 3-decker cannot be in line 10. Please re-enter");
                    shipCoordinate = sc.nextLine();
                }
            } else while (shipCoordinate.charAt(1) == '9') {
                System.out.println("Erroneous input! Uppermost cell of a 3-decker cannot be in line 9. Please re-enter");
                shipCoordinate = sc.nextLine();
            }
        }

        isVertical = (shipOrientation.equalsIgnoreCase("VER"));

        List<Integer> numberRange = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        List<String> letterRange = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J"));
        int letterIndex = -1;
        int numberIndex = -1;
        int letterPosition = -1;
        int numberPosition = -1;

        if (letterRange.contains(Character.toString(shipCoordinate.charAt(0)).toUpperCase())) {
            letterPosition = 0;
            numberPosition = 1;
        } else {
            letterPosition = 1;
            numberPosition = 0;
        }

        letterIndex = letterRange.indexOf(Character.toString(shipCoordinate.charAt(letterPosition)).toUpperCase());
        if (numberPosition + 1 < shipCoordinate.length() && shipCoordinate.charAt(numberPosition) == '1' && shipCoordinate.charAt(numberPosition + 1) == '0') {
            numberIndex = 9;
        } else {
            numberIndex = Integer.parseInt(shipCoordinate.substring(numberPosition)) - 1;
        }

        if (humanShipContainingCells[numberIndex][letterIndex] == humanField.getShipCell() ||
                busyCells[numberIndex][letterIndex]) {
            System.out.println("This cell is unavailable. Please enter a different one (ships may not touch or overlap)");
            return placeHumanBattlecruisers(humanField, busyCells, isVertical);
        }
        if (shipOrientation.toUpperCase().equals("HOR")) {
            if (humanShipContainingCells[numberIndex][letterIndex + 1] == humanField.getShipCell() ||
                    busyCells[numberIndex][letterIndex + 1]) {
                System.out.println("This cell is unavailable. Please enter a different one (ships may not touch or overlap)");
                return placeHumanBattlecruisers(humanField, busyCells, isVertical);
            }
            if (humanShipContainingCells[numberIndex][letterIndex + 2] == humanField.getShipCell() ||
                    busyCells[numberIndex][letterIndex + 2]) {
                System.out.println("This cell is unavailable. Please enter a different one (ships may not touch or overlap)");
                return placeHumanBattlecruisers(humanField, busyCells, isVertical);
            }
            if (letterIndex - 1 >= 0 && (humanShipContainingCells[numberIndex][letterIndex - 1] == humanField.getShipCell())) {
                System.out.println("This cell is unavailable. Please enter a different one (ships may not touch or overlap)");
                return placeHumanBattlecruisers(humanField, busyCells, isVertical);
            }
            if (letterIndex + 3 < 10 && humanShipContainingCells[numberIndex][letterIndex + 3] == humanField.getShipCell()) {
                System.out.println("This cell is unavailable. Please enter a different one (ships may not touch or overlap)");
                return placeHumanBattlecruisers(humanField, busyCells, isVertical);
            }
        }
        if (shipOrientation.toUpperCase().equals("VER")) {
            if (humanShipContainingCells[numberIndex + 1][letterIndex] == humanField.getShipCell() ||
                    busyCells[numberIndex + 1][letterIndex]) {
                System.out.println("This cell is unavailable. Please enter a different one (ships may not touch or overlap)");
                return placeHumanBattlecruisers(humanField, busyCells, isVertical);
            }
            if (humanShipContainingCells[numberIndex + 2][letterIndex] == humanField.getShipCell() ||
                    busyCells[numberIndex + 2][letterIndex]) {
                System.out.println("This cell is unavailable. Please enter a different one (ships may not touch or overlap)");
                return placeHumanBattlecruisers(humanField, busyCells, isVertical);
            }
            if (numberIndex - 1 >= 0 && (humanShipContainingCells[numberIndex - 1][letterIndex] == humanField.getShipCell())) {
                System.out.println("This cell is unavailable. Please enter a different one (ships may not touch or overlap)");
                return placeHumanBattlecruisers(humanField, busyCells, isVertical);
            }
            if (numberIndex + 3 < 10 && humanShipContainingCells[numberIndex + 3][letterIndex] == humanField.getShipCell()) {
                System.out.println("This cell is unavailable. Please enter a different one (ships may not touch or overlap)");
                return placeHumanBattlecruisers(humanField, busyCells, isVertical);
            }
        }


        humanShipContainingCells[numberIndex][letterIndex] = humanField.getShipCell();
        if (shipOrientation.toUpperCase().equals("VER")) {
            humanShipContainingCells[numberIndex + 1][letterIndex] = humanField.getShipCell();
            humanShipContainingCells[numberIndex + 2][letterIndex] = humanField.getShipCell();
        }
        if (shipOrientation.toUpperCase().equals("HOR")) {
            humanShipContainingCells[numberIndex][letterIndex + 1] = humanField.getShipCell();
            humanShipContainingCells[numberIndex][letterIndex + 2] = humanField.getShipCell();
        }

        if (shipOrientation.toUpperCase().equals("HOR")) {
            if (numberIndex - 1 >= 0) {
                busyCells[numberIndex - 1][letterIndex] = true;
                busyCells[numberIndex - 1][letterIndex + 1] = true;
            }
            if (numberIndex + 1 < 10) {
                busyCells[numberIndex + 1][letterIndex] = true;
                busyCells[numberIndex + 1][letterIndex + 1] = true;
            }
            if (letterIndex - 1 >= 0) busyCells[numberIndex][letterIndex - 1] = true;
            if (letterIndex + 3 < 10) busyCells[numberIndex][letterIndex + 3] = true;
            if (numberIndex - 1 >= 0 && letterIndex - 1 >= 0) busyCells[numberIndex - 1][letterIndex - 1] = true;
            if (numberIndex + 1 < 10 && letterIndex - 1 >= 0) busyCells[numberIndex + 1][letterIndex - 1] = true;
            if (numberIndex + 1 < 10 && letterIndex + 2 < 10) busyCells[numberIndex + 1][letterIndex + 2] = true;
            if (numberIndex - 1 >= 0 && letterIndex + 2 < 10) busyCells[numberIndex - 1][letterIndex + 2] = true;
            if (numberIndex + 1 < 10 && letterIndex + 3 < 10) busyCells[numberIndex + 1][letterIndex + 3] = true;
            if (numberIndex - 1 >= 0 && letterIndex + 3 < 10) busyCells[numberIndex - 1][letterIndex + 3] = true;
        }

        if (shipOrientation.toUpperCase().equals("VER")) {
            if (letterIndex - 1 >= 0) busyCells[numberIndex][letterIndex - 1] = true;
            if (letterIndex + 1 < 10) busyCells[numberIndex][letterIndex + 1] = true;
            if (letterIndex - 1 >= 0) busyCells[numberIndex + 1][letterIndex - 1] = true;
            if (letterIndex + 1 < 10) busyCells[numberIndex + 1][letterIndex + 1] = true;

            if (numberIndex - 1 >= 0) {
                busyCells[numberIndex - 1][letterIndex] = true;
                if (letterIndex < 9) busyCells[numberIndex - 1][letterIndex + 1] = true;
            }
            if (numberIndex + 3 < 10) {
                busyCells[numberIndex + 3][letterIndex] = true;
                if (letterIndex < 9) busyCells[numberIndex + 3][letterIndex + 1] = true;
            }

            if (numberIndex - 1 >= 0 && letterIndex - 1 >= 0) busyCells[numberIndex - 1][letterIndex - 1] = true;
            if (numberIndex + 2 < 10 && letterIndex - 1 >= 0) busyCells[numberIndex + 2][letterIndex - 1] = true;
            if (numberIndex + 2 < 10 && letterIndex + 1 < 10) busyCells[numberIndex + 2][letterIndex + 1] = true;
            if (numberIndex + 3 < 10 && letterIndex - 1 >= 0) busyCells[numberIndex + 3][letterIndex - 1] = true;
            if (numberIndex + 3 < 10 && letterIndex + 1 < 10) busyCells[numberIndex + 3][letterIndex + 1] = true;
            if (numberIndex - 1 >= 0 && letterIndex + 1 < 10) busyCells[numberIndex - 1][letterIndex + 1] = true;
        }

        humanField.setShipCell(numberIndex, letterIndex, humanField.getShipCell());
        if (shipOrientation.toUpperCase().equals("VER")) {
            humanField.setShipCell(numberIndex + 1, letterIndex, humanField.getShipCell());
            humanField.setShipCell(numberIndex + 2, letterIndex, humanField.getShipCell());
        }
        if (shipOrientation.toUpperCase().equals("HOR")) {
            humanField.setShipCell(numberIndex, letterIndex + 1, humanField.getShipCell());
            humanField.setShipCell(numberIndex, letterIndex + 2, humanField.getShipCell());
        }
        humanField.repaint(false, busyCells);
        return humanField;
    }

    //      ======================= 4-decker placement method =======================

    public static Field placeHumanCarrier(Field humanField, boolean[][] busyCells, boolean isVertical) {
        Scanner scPlace = new Scanner(System.in);
        String shipOrientation = scPlace.nextLine();
        String shipCoordinate = "";
        char[][] humanShipContainingCells = new char[10][10];
        while (!shipOrientation.toUpperCase().equals("HOR") && !shipOrientation.toUpperCase().equals("VER")) {
            System.out.println("Please enter HOR for horizontal or VER for vertical");
            shipOrientation = scPlace.nextLine();
        }
        humanField.repaint(false, busyCells);
        if (shipOrientation.toUpperCase().equals("HOR")) {
            System.out.println("Please specify the LEFTMOST cell of your ship (letter within the range of A to I and digit");
            Scanner sc = new Scanner(System.in);
            shipCoordinate = sc.nextLine();
            InputValidator validator = new InputValidator();
            while (!validator.isValidInput(shipCoordinate)) {
                System.out.println("Erroneous input! Your input should consist of 2 characters (a letter and a digit) only. Please re-enter the coordinates.");
                shipCoordinate = sc.nextLine();
            }
            while (shipCoordinate.charAt(0) == 'H' || shipCoordinate.charAt(0) == 'h') {
                System.out.println("Erroneous input! Leftmost cell of a 4-decker cannot be in column H. Please re-enter");
                shipCoordinate = sc.nextLine();
            }
            while (shipCoordinate.charAt(0) == 'J' || shipCoordinate.charAt(0) == 'j') {
                System.out.println("Erroneous input! Leftmost cell of a 4-decker cannot be in column J. Please re-enter");
                shipCoordinate = sc.nextLine();
            }
            while (shipCoordinate.charAt(0) == 'I' || shipCoordinate.charAt(0) == 'i') {
                System.out.println("Erroneous input! Leftmost cell of a 4-decker cannot be in column I. Please re-enter");
                shipCoordinate = sc.nextLine();
            }

        }
        if (shipOrientation.toUpperCase().equals("VER")) {
            System.out.println("Please specify the UPPERMOST cell of your ship (letter and digit within the range of 1 to 9");
            Scanner sc = new Scanner(System.in);
            shipCoordinate = sc.nextLine();
            InputValidator validator = new InputValidator();
            while (!validator.isValidInput(shipCoordinate)) {
                System.out.println("Erroneous input! Your input should consist of 2 characters (a letter and a digit) only. Please re-enter the coordinates.");
                shipCoordinate = sc.nextLine();
            }
            if (shipCoordinate.length() > 2) {
                while (Integer.parseInt(shipCoordinate.substring(1)) == 10) {
                    System.out.println("Erroneous input! Uppermost cell of a 4-decker cannot be in line 10. Please re-enter");
                    shipCoordinate = sc.nextLine();
                }
            } else while (shipCoordinate.charAt(1) == '9' || shipCoordinate.charAt(1) == '8') {
                System.out.println("Erroneous input! Uppermost cell of a 4-decker cannot be in line 8 or 9. Please re-enter");
                shipCoordinate = sc.nextLine();
            }
        }

        List<Integer> numberRange = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        List<String> letterRange = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J"));
        int letterIndex = -1;
        int numberIndex = -1;
        int letterPosition = -1;
        int numberPosition = -1;

        if (letterRange.contains(Character.toString(shipCoordinate.charAt(0)).toUpperCase())) {
            letterPosition = 0;
            numberPosition = 1;
        } else {
            letterPosition = 1;
            numberPosition = 0;
        }

        letterIndex = letterRange.indexOf(Character.toString(shipCoordinate.charAt(letterPosition)).toUpperCase());
        if (numberPosition + 1 < shipCoordinate.length() && shipCoordinate.charAt(numberPosition) == '1' && shipCoordinate.charAt(numberPosition + 1) == '0') {
            numberIndex = 9;
        } else {
            numberIndex = Integer.parseInt(shipCoordinate.substring(numberPosition)) - 1;
        }

        if (humanShipContainingCells[numberIndex][letterIndex] == humanField.getShipCell() ||
                busyCells[numberIndex][letterIndex]) {
            System.out.println("This cell is unavailable. Please enter a different one (ships may not touch or overlap)");
            return placeHumanCarrier(humanField, busyCells, isVertical);
        }

        isVertical = (shipOrientation.equalsIgnoreCase("VER"));

        humanShipContainingCells[numberIndex][letterIndex] = humanField.getShipCell();
        if (shipOrientation.toUpperCase().equals("VER")) {
            humanShipContainingCells[numberIndex + 1][letterIndex] = humanField.getShipCell();
            humanShipContainingCells[numberIndex + 2][letterIndex] = humanField.getShipCell();
            humanShipContainingCells[numberIndex + 3][letterIndex] = humanField.getShipCell();
        }
        if (shipOrientation.toUpperCase().equals("HOR")) {
            humanShipContainingCells[numberIndex][letterIndex + 1] = humanField.getShipCell();
            humanShipContainingCells[numberIndex][letterIndex + 2] = humanField.getShipCell();
            humanShipContainingCells[numberIndex][letterIndex + 3] = humanField.getShipCell();
        }

        if (shipOrientation.toUpperCase().equals("HOR")) {
            if (numberIndex - 1 >= 0) {
                busyCells[numberIndex - 1][letterIndex] = true;
                busyCells[numberIndex - 1][letterIndex + 1] = true;
            }
            if (numberIndex + 1 < 10) {
                busyCells[numberIndex + 1][letterIndex] = true;
                busyCells[numberIndex + 1][letterIndex + 1] = true;
            }
            if (letterIndex - 1 >= 0) busyCells[numberIndex][letterIndex - 1] = true;
            if (letterIndex + 4 < 10) busyCells[numberIndex][letterIndex + 4] = true;
            if (numberIndex - 1 >= 0 && letterIndex - 1 >= 0) busyCells[numberIndex - 1][letterIndex - 1] = true;
            if (numberIndex + 1 < 10 && letterIndex - 1 >= 0) busyCells[numberIndex + 1][letterIndex - 1] = true;
            if (numberIndex + 1 < 10 && letterIndex + 2 < 10) busyCells[numberIndex + 1][letterIndex + 2] = true;
            if (numberIndex - 1 >= 0 && letterIndex + 2 < 10) busyCells[numberIndex - 1][letterIndex + 2] = true;
            if (numberIndex + 1 < 10 && letterIndex + 3 < 10) busyCells[numberIndex + 1][letterIndex + 3] = true;
            if (numberIndex - 1 >= 0 && letterIndex + 3 < 10) busyCells[numberIndex - 1][letterIndex + 3] = true;
            if (numberIndex + 1 < 10 && letterIndex + 4 < 10) busyCells[numberIndex + 1][letterIndex + 4] = true;
            if (numberIndex - 1 >= 0 && letterIndex + 4 < 10) busyCells[numberIndex - 1][letterIndex + 4] = true;
        }

        if (shipOrientation.toUpperCase().equals("VER")) {
            if (letterIndex - 1 >= 0) busyCells[numberIndex][letterIndex - 1] = true;
            if (letterIndex + 1 < 10) busyCells[numberIndex][letterIndex + 1] = true;
            if (letterIndex - 1 >= 0) busyCells[numberIndex + 1][letterIndex - 1] = true;
            if (letterIndex + 1 < 10) busyCells[numberIndex + 1][letterIndex + 1] = true;

            if (numberIndex - 1 >= 0) {
                busyCells[numberIndex - 1][letterIndex] = true;
                if (letterIndex < 9) busyCells[numberIndex - 1][letterIndex + 1] = true;
            }
            if (numberIndex + 4 < 10) {
                busyCells[numberIndex + 4][letterIndex] = true;
                if (letterIndex < 9) busyCells[numberIndex + 4][letterIndex + 1] = true;
            }

            if (numberIndex - 1 >= 0 && letterIndex - 1 >= 0) busyCells[numberIndex - 1][letterIndex - 1] = true;
            if (numberIndex + 2 < 10 && letterIndex - 1 >= 0) busyCells[numberIndex + 2][letterIndex - 1] = true;
            if (numberIndex + 2 < 10 && letterIndex + 1 < 10) busyCells[numberIndex + 2][letterIndex + 1] = true;
            if (numberIndex + 3 < 10 && letterIndex - 1 >= 0) busyCells[numberIndex + 3][letterIndex - 1] = true;
            if (numberIndex + 3 < 10 && letterIndex + 1 < 10) busyCells[numberIndex + 3][letterIndex + 1] = true;
            if (numberIndex + 4 < 10 && letterIndex - 1 >= 0) busyCells[numberIndex + 4][letterIndex - 1] = true;
            if (numberIndex + 4 < 10 && letterIndex + 1 < 10) busyCells[numberIndex + 4][letterIndex + 1] = true;
            if (numberIndex - 1 >= 0 && letterIndex + 1 < 10) busyCells[numberIndex - 1][letterIndex + 1] = true;
        }

        humanField.setShipCell(numberIndex, letterIndex, humanField.getShipCell());
        if (shipOrientation.toUpperCase().equals("VER")) {
            humanField.setShipCell(numberIndex + 1, letterIndex, humanField.getShipCell());
            humanField.setShipCell(numberIndex + 2, letterIndex, humanField.getShipCell());
            humanField.setShipCell(numberIndex + 3, letterIndex, humanField.getShipCell());
        }
        if (shipOrientation.toUpperCase().equals("HOR")) {
            humanField.setShipCell(numberIndex, letterIndex + 1, humanField.getShipCell());
            humanField.setShipCell(numberIndex, letterIndex + 2, humanField.getShipCell());
            humanField.setShipCell(numberIndex, letterIndex + 3, humanField.getShipCell());
        }
        humanField.repaint(false, busyCells);
        return humanField;
    }


    //      ======================= 2-decker placement method =======================

    public static Field placeHumanDestroyers(Field humanField, boolean[][] busyCells, boolean isVertical) {
        Scanner scPlace = new Scanner(System.in);
        String shipOrientation = scPlace.nextLine();
        String shipCoordinate = "";
        char[][] humanShipContainingCells = new char[10][10];
        while (!shipOrientation.toUpperCase().equals("HOR") && !shipOrientation.toUpperCase().equals("VER")) {
            System.out.println("Please enter HOR for horizontal or VER for vertical");
            shipOrientation = scPlace.nextLine();
        }
        humanField.repaint(false, busyCells);
        if (shipOrientation.toUpperCase().equals("HOR")) {
            System.out.println("Please specify the LEFTMOST cell of your ship (letter within the range of A to I and digit");
            Scanner sc = new Scanner(System.in);
            shipCoordinate = sc.nextLine();
            InputValidator validator = new InputValidator();
            while (!validator.isValidInput(shipCoordinate)) {
                System.out.println("Erroneous input! Your input should consist of 2 characters (a letter and a digit) only. Please re-enter the coordinates.");
                shipCoordinate = sc.nextLine();
            }
            while (shipCoordinate.charAt(0) == 'J' || shipCoordinate.charAt(0) == 'j') {
                System.out.println("Erroneous input! Leftmost cell of a 2-decker cannot be in column J. Please re-enter");
                shipCoordinate = sc.nextLine();
            }

        }
        if (shipOrientation.toUpperCase().equals("VER")) {
            System.out.println("Please specify the UPPERMOST cell of your ship (letter and digit within the range of 1 to 9");
            Scanner sc = new Scanner(System.in);
            shipCoordinate = sc.nextLine();
            InputValidator validator = new InputValidator();
            while (!validator.isValidInput(shipCoordinate)) {
                System.out.println("Erroneous input! Your input should consist of 2 characters (a letter and a digit) only. Please re-enter the coordinates.");
                shipCoordinate = sc.nextLine();
            }
            if (shipCoordinate.length() > 2) {
                while (Integer.parseInt(shipCoordinate.substring(1)) == 10) {
                    System.out.println("Erroneous input! Uppermost cell of a 2-decker cannot be in line 10. Please re-enter");
                    shipCoordinate = sc.nextLine();
                }
            }
        }

        List<Integer> numberRange = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        List<String> letterRange = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J"));
        int letterIndex = -1;
        int numberIndex = -1;
        int letterPosition = -1;
        int numberPosition = -1;

        if (letterRange.contains(Character.toString(shipCoordinate.charAt(0)).toUpperCase())) {
            letterPosition = 0;
            numberPosition = 1;
        } else {
            letterPosition = 1;
            numberPosition = 0;
        }

        letterIndex = letterRange.indexOf(Character.toString(shipCoordinate.charAt(letterPosition)).toUpperCase());
        if (numberPosition + 1 < shipCoordinate.length() && shipCoordinate.charAt(numberPosition) == '1' && shipCoordinate.charAt(numberPosition + 1) == '0') {
            numberIndex = 9;
        } else {
            numberIndex = Integer.parseInt(shipCoordinate.substring(numberPosition)) - 1;
        }

        if (humanShipContainingCells[numberIndex][letterIndex] == humanField.getShipCell() ||
                busyCells[numberIndex][letterIndex]) {
            System.out.println("This cell is unavailable. Please enter a different one (ships may not touch or overlap)");
            return placeHumanDestroyers(humanField, busyCells, isVertical);
        }
        if (shipOrientation.toUpperCase().equals("HOR")) {
            if (humanShipContainingCells[numberIndex][letterIndex + 1] == humanField.getShipCell() ||
                    busyCells[numberIndex][letterIndex + 1]) {
                System.out.println("This cell is unavailable. Please enter a different one (ships may not touch or overlap)");
                return placeHumanDestroyers(humanField, busyCells, isVertical);
            }
            if (letterIndex - 1 >= 0 && (humanShipContainingCells[numberIndex][letterIndex - 1] == humanField.getShipCell())) {
                System.out.println("This cell is unavailable. Please enter a different one (ships may not touch or overlap)");
                return placeHumanDestroyers(humanField, busyCells, isVertical);
            }
            if (letterIndex + 2 < 10 && humanShipContainingCells[numberIndex][letterIndex + 2] == humanField.getShipCell()) {
                System.out.println("This cell is unavailable. Please enter a different one (ships may not touch or overlap)");
                return placeHumanDestroyers(humanField, busyCells, isVertical);
            }
        }
        if (shipOrientation.toUpperCase().equals("VER")) {
            if (humanShipContainingCells[numberIndex + 1][letterIndex] == humanField.getShipCell() ||
                    busyCells[numberIndex + 1][letterIndex]) {
                System.out.println("This cell is unavailable. Please enter a different one (ships may not touch or overlap)");
                return placeHumanDestroyers(humanField, busyCells, isVertical);
            }
            if (numberIndex - 1 >= 0 && (humanShipContainingCells[numberIndex - 1][letterIndex] == humanField.getShipCell())) {
                System.out.println("This cell is unavailable. Please enter a different one (ships may not touch or overlap)");
                return placeHumanDestroyers(humanField, busyCells, isVertical);
            }
            if (numberIndex + 2 < 10 && humanShipContainingCells[numberIndex + 2][letterIndex] == humanField.getShipCell()) {
                System.out.println("This cell is unavailable. Please enter a different one (ships may not touch or overlap)");
                return placeHumanDestroyers(humanField, busyCells, isVertical);
            }
        }

        isVertical = (shipOrientation.equalsIgnoreCase("VER"));

        humanShipContainingCells[numberIndex][letterIndex] = humanField.getShipCell();
        if (shipOrientation.toUpperCase().equals("VER"))
            humanShipContainingCells[numberIndex + 1][letterIndex] = humanField.getShipCell();
        if (shipOrientation.toUpperCase().equals("HOR"))
            humanShipContainingCells[numberIndex][letterIndex + 1] = humanField.getShipCell();

        if (shipOrientation.toUpperCase().equals("HOR")) {
            if (numberIndex - 1 >= 0) {
                busyCells[numberIndex - 1][letterIndex] = true;
                busyCells[numberIndex - 1][letterIndex + 1] = true;
            }
            if (numberIndex + 1 < 10) {
                busyCells[numberIndex + 1][letterIndex] = true;
                busyCells[numberIndex + 1][letterIndex + 1] = true;
            }
            if (letterIndex - 1 >= 0) busyCells[numberIndex][letterIndex - 1] = true;
            if (letterIndex + 2 < 10) busyCells[numberIndex][letterIndex + 2] = true;
            if (numberIndex - 1 >= 0 && letterIndex - 1 >= 0) busyCells[numberIndex - 1][letterIndex - 1] = true;
            if (numberIndex + 1 < 10 && letterIndex - 1 >= 0) busyCells[numberIndex + 1][letterIndex - 1] = true;
            if (numberIndex + 1 < 10 && letterIndex + 2 < 10) busyCells[numberIndex + 1][letterIndex + 2] = true;
            if (numberIndex - 1 >= 0 && letterIndex + 2 < 10) busyCells[numberIndex - 1][letterIndex + 2] = true;

        }

        if (shipOrientation.toUpperCase().equals("VER")) {
            if (letterIndex - 1 >= 0) busyCells[numberIndex][letterIndex - 1] = true;
            if (letterIndex + 1 < 10) busyCells[numberIndex][letterIndex + 1] = true;
            if (letterIndex - 1 >= 0) busyCells[numberIndex + 1][letterIndex - 1] = true;
            if (letterIndex + 1 < 10) busyCells[numberIndex + 1][letterIndex + 1] = true;

            if (numberIndex - 1 >= 0) {
                busyCells[numberIndex - 1][letterIndex] = true;
                if (letterIndex < 9) busyCells[numberIndex - 1][letterIndex + 1] = true;
            }
            if (numberIndex + 2 < 10) {
                busyCells[numberIndex + 2][letterIndex] = true;
                if (letterIndex < 9) busyCells[numberIndex + 2][letterIndex + 1] = true;
            }
            if (numberIndex - 1 >= 0 && letterIndex - 1 >= 0) busyCells[numberIndex - 1][letterIndex - 1] = true;
            if (numberIndex + 2 < 10 && letterIndex - 1 >= 0) busyCells[numberIndex + 2][letterIndex - 1] = true;
            if (numberIndex + 2 < 10 && letterIndex + 1 < 10) busyCells[numberIndex + 2][letterIndex + 1] = true;
            if (numberIndex - 1 >= 0 && letterIndex + 1 < 10) busyCells[numberIndex - 1][letterIndex + 1] = true;
        }

        humanField.setShipCell(numberIndex, letterIndex, humanField.getShipCell());
        if (shipOrientation.toUpperCase().equals("VER"))
            humanField.setShipCell(numberIndex + 1, letterIndex, humanField.getShipCell());
        if (shipOrientation.toUpperCase().equals("HOR"))
            humanField.setShipCell(numberIndex, letterIndex + 1, humanField.getShipCell());
        humanField.repaint(false, busyCells);
        return humanField;
    }

    //      ======================= AI ship placement method =======================

    public static Field placeOppShips(Field oppField, boolean[][] oppBusyCells, char[][] oppShipContainingCells, int[][] oppCarrierCells, int [][][] oppBattlecruiserCells, int [][][] oppDestroyerCells, Ship[] oppDestroyers, Ship[] oppBattlecruisers, Ship[] oppCarrier) {
        boolean isVertical;
        Random rHorVer = new Random();
        Random rLeftLetter = new Random();
        Random rUpperNumber = new Random();
        int letterIndex;
        int numberIndex;

        //      ======================= AI 4-decker placement method =======================

        isVertical = rHorVer.nextBoolean();
        if (isVertical) {
            letterIndex = rLeftLetter.nextInt(0, 10);
            numberIndex = rUpperNumber.nextInt(0, 7);
        } else {
            letterIndex = rLeftLetter.nextInt(0, 7);
            numberIndex = rUpperNumber.nextInt(0, 10);
        }

        while (oppShipContainingCells[numberIndex][letterIndex] == oppField.getShipCell() ||
               oppBusyCells[numberIndex][letterIndex]) {
            isVertical = rHorVer.nextBoolean();
            if (isVertical) {
                letterIndex = rLeftLetter.nextInt(0, 10);
                numberIndex = rUpperNumber.nextInt(0, 7);
            } else {
                letterIndex = rLeftLetter.nextInt(0, 7);
                numberIndex = rUpperNumber.nextInt(0, 10);
            }
        }

        oppCarrier[0] = new Ship(4);
        oppCarrier[0].setVertical(isVertical);
        oppShipArray[0] = oppCarrier[0];

        oppShipContainingCells[numberIndex][letterIndex] = oppField.getShipCell();
        oppCarrierCells[0][0] = oppShipContainingCells[numberIndex][letterIndex];
        if (isVertical) {
            oppShipContainingCells[numberIndex + 1][letterIndex] = oppField.getShipCell();
            oppCarrierCells[1][0] = oppShipContainingCells[numberIndex + 1][letterIndex];
            oppShipContainingCells[numberIndex + 2][letterIndex] = oppField.getShipCell();
            oppCarrierCells[2][0] = oppShipContainingCells[numberIndex + 2][letterIndex];
            oppShipContainingCells[numberIndex + 3][letterIndex] = oppField.getShipCell();
            oppCarrierCells[3][0] = oppShipContainingCells[numberIndex + 3][letterIndex];
        } else {
            oppShipContainingCells[numberIndex][letterIndex + 1] = oppField.getShipCell();
            oppCarrierCells[0][1] = oppShipContainingCells[numberIndex][letterIndex + 1];
            oppShipContainingCells[numberIndex][letterIndex + 2] = oppField.getShipCell();
            oppCarrierCells[0][2] = oppShipContainingCells[numberIndex][letterIndex + 2];
            oppShipContainingCells[numberIndex][letterIndex + 3] = oppField.getShipCell();
            oppCarrierCells[0][3] = oppShipContainingCells[numberIndex][letterIndex + 3];
        }
        if (!isVertical) {
            if (numberIndex - 1 >= 0) {
                oppBusyCells[numberIndex - 1][letterIndex] = true;
                if (letterIndex < 9) oppBusyCells[numberIndex - 1][letterIndex + 1] = true;
            }
            if (numberIndex + 1 < 10) {
                oppBusyCells[numberIndex + 1][letterIndex] = true;
                if (letterIndex < 9) oppBusyCells[numberIndex + 1][letterIndex + 1] = true;
            }
            if (letterIndex - 1 >= 0) oppBusyCells[numberIndex][letterIndex - 1] = true;
            if (letterIndex + 4 < 10) oppBusyCells[numberIndex][letterIndex + 4] = true;
            if (numberIndex - 1 >= 0 && letterIndex - 1 >= 0) oppBusyCells[numberIndex - 1][letterIndex - 1] = true;
            if (numberIndex + 1 < 10 && letterIndex - 1 >= 0) oppBusyCells[numberIndex + 1][letterIndex - 1] = true;
            if (numberIndex + 1 < 10 && letterIndex + 2 < 10) oppBusyCells[numberIndex + 1][letterIndex + 2] = true;
            if (numberIndex - 1 >= 0 && letterIndex + 2 < 10) oppBusyCells[numberIndex - 1][letterIndex + 2] = true;
            if (numberIndex + 1 < 10 && letterIndex + 3 < 10) oppBusyCells[numberIndex + 1][letterIndex + 3] = true;
            if (numberIndex - 1 >= 0 && letterIndex + 3 < 10) oppBusyCells[numberIndex - 1][letterIndex + 3] = true;
            if (numberIndex + 1 < 10 && letterIndex + 4 < 10) oppBusyCells[numberIndex + 1][letterIndex + 4] = true;
            if (numberIndex - 1 >= 0 && letterIndex + 4 < 10) oppBusyCells[numberIndex - 1][letterIndex + 4] = true;
        }

        if (isVertical) {
            if (letterIndex - 1 >= 0) oppBusyCells[numberIndex][letterIndex - 1] = true;
            if (letterIndex + 1 < 10) oppBusyCells[numberIndex][letterIndex + 1] = true;
            if (letterIndex - 1 >= 0) oppBusyCells[numberIndex + 1][letterIndex - 1] = true;
            if (letterIndex + 1 < 10) oppBusyCells[numberIndex + 1][letterIndex + 1] = true;

            if (numberIndex - 1 >= 0) {
                oppBusyCells[numberIndex - 1][letterIndex] = true;
                if (letterIndex < 9) oppBusyCells[numberIndex - 1][letterIndex + 1] = true;
            }
            if (numberIndex + 4 < 10) {
                oppBusyCells[numberIndex + 4][letterIndex] = true;
                if (letterIndex < 9) oppBusyCells[numberIndex + 4][letterIndex + 1] = true;
            }

            if (numberIndex - 1 >= 0 && letterIndex - 1 >= 0) oppBusyCells[numberIndex - 1][letterIndex - 1] = true;
            if (numberIndex + 2 < 10 && letterIndex - 1 >= 0) oppBusyCells[numberIndex + 2][letterIndex - 1] = true;
            if (numberIndex + 2 < 10 && letterIndex + 1 < 10) oppBusyCells[numberIndex + 2][letterIndex + 1] = true;
            if (numberIndex + 3 < 10 && letterIndex - 1 >= 0) oppBusyCells[numberIndex + 3][letterIndex - 1] = true;
            if (numberIndex + 3 < 10 && letterIndex + 1 < 10) oppBusyCells[numberIndex + 3][letterIndex + 1] = true;
            if (numberIndex + 4 < 10 && letterIndex - 1 >= 0) oppBusyCells[numberIndex + 4][letterIndex - 1] = true;
            if (numberIndex + 4 < 10 && letterIndex + 1 < 10) oppBusyCells[numberIndex + 4][letterIndex + 1] = true;
            if (numberIndex - 1 >= 0 && letterIndex + 1 < 10) oppBusyCells[numberIndex - 1][letterIndex + 1] = true;
        }

        oppField.setShipCell(numberIndex, letterIndex, oppField.getShipCell());
        if (isVertical) {
            oppField.setShipCell(numberIndex + 1, letterIndex, oppField.getShipCell());
            oppField.setShipCell(numberIndex + 2, letterIndex, oppField.getShipCell());
            oppField.setShipCell(numberIndex + 3, letterIndex, oppField.getShipCell());
        }
        if (!isVertical) {
            oppField.setShipCell(numberIndex, letterIndex + 1, oppField.getShipCell());
            oppField.setShipCell(numberIndex, letterIndex + 2, oppField.getShipCell());
            oppField.setShipCell(numberIndex, letterIndex + 3, oppField.getShipCell());
        }

        //      ======================= AI 3-decker placement method =======================
        for (int i = 0; i < 2; i++) {
            isVertical = rHorVer.nextBoolean();
            if (isVertical) {
                letterIndex = rLeftLetter.nextInt(0, 10);
                numberIndex = rUpperNumber.nextInt(0, 8);
            } else {
                letterIndex = rLeftLetter.nextInt(0, 8);
                numberIndex = rUpperNumber.nextInt(0, 10);
            }

            if (!isVertical) {
                while (oppShipContainingCells[numberIndex][letterIndex] == oppField.getShipCell() ||
                        oppBusyCells[numberIndex][letterIndex] ||
                        oppShipContainingCells[numberIndex][letterIndex + 1] == oppField.getShipCell() ||
                        oppBusyCells[numberIndex][letterIndex + 1] ||
                        oppShipContainingCells[numberIndex][letterIndex + 2] == oppField.getShipCell() ||
                        oppBusyCells[numberIndex][letterIndex + 2] ||
                        (letterIndex - 1 >= 0 && (oppShipContainingCells[numberIndex][letterIndex - 1] == oppField.getShipCell())) ||
                        (letterIndex + 3 < 10 && oppShipContainingCells[numberIndex][letterIndex + 3] == oppField.getShipCell())
                        ) {
                        letterIndex = rLeftLetter.nextInt(0, 8);
                        numberIndex = rUpperNumber.nextInt(0, 10);
                        }
            }

            if (isVertical) {
                while (oppShipContainingCells[numberIndex][letterIndex] == oppField.getShipCell() ||
                        oppBusyCells[numberIndex][letterIndex] ||
                        oppShipContainingCells[numberIndex + 1][letterIndex] == oppField.getShipCell() ||
                        oppBusyCells[numberIndex + 1][letterIndex] ||
                        oppShipContainingCells[numberIndex + 2][letterIndex] == oppField.getShipCell() ||
                        oppBusyCells[numberIndex + 2][letterIndex] ||
                        (numberIndex - 1 >= 0 && (oppShipContainingCells[numberIndex - 1][letterIndex] == oppField.getShipCell())) ||
                        (numberIndex + 3 < 10 && oppShipContainingCells[numberIndex + 3][letterIndex] == oppField.getShipCell())
                ) {
                    letterIndex = rLeftLetter.nextInt(0, 10);
                    numberIndex = rUpperNumber.nextInt(0, 8);
                }
            }

            oppBattlecruisers[i] = new Ship(3);
            oppBattlecruisers[i].setVertical(isVertical);
            oppShipArray[i+1] = oppBattlecruisers[i];

            oppShipContainingCells[numberIndex][letterIndex] = oppField.getShipCell();
            oppBattlecruiserCells[i][0][0] = oppShipContainingCells[numberIndex][letterIndex];

            if (isVertical) {
                oppShipContainingCells[numberIndex + 1][letterIndex] = oppField.getShipCell();
                oppBattlecruiserCells[i][1][0] = oppShipContainingCells[numberIndex + 1][letterIndex];
                oppShipContainingCells[numberIndex + 2][letterIndex] = oppField.getShipCell();
                oppBattlecruiserCells[i][2][0] = oppShipContainingCells[numberIndex + 2][letterIndex];
            } else {
                oppShipContainingCells[numberIndex][letterIndex + 1] = oppField.getShipCell();
                oppBattlecruiserCells[i][0][1] = oppShipContainingCells[numberIndex][letterIndex + 1];
                oppShipContainingCells[numberIndex][letterIndex + 2] = oppField.getShipCell();
                oppBattlecruiserCells[i][0][2] = oppShipContainingCells[numberIndex][letterIndex + 2];
            }
            if (!isVertical) {
                if (numberIndex - 1 >= 0) {
                    oppBusyCells[numberIndex - 1][letterIndex] = true;
                    if (letterIndex < 9) oppBusyCells[numberIndex - 1][letterIndex + 1] = true;
                }
                if (numberIndex + 1 < 10) {
                    oppBusyCells[numberIndex + 1][letterIndex] = true;
                    if (letterIndex < 9) oppBusyCells[numberIndex + 1][letterIndex + 1] = true;
                }
                if (letterIndex - 1 >= 0) oppBusyCells[numberIndex][letterIndex - 1] = true;
                if (letterIndex + 3 < 10) oppBusyCells[numberIndex][letterIndex + 3] = true;
                if (numberIndex - 1 >= 0 && letterIndex - 1 >= 0) oppBusyCells[numberIndex - 1][letterIndex - 1] = true;
                if (numberIndex + 1 < 10 && letterIndex - 1 >= 0) oppBusyCells[numberIndex + 1][letterIndex - 1] = true;
                if (numberIndex + 1 < 10 && letterIndex + 2 < 10) oppBusyCells[numberIndex + 1][letterIndex + 2] = true;
                if (numberIndex - 1 >= 0 && letterIndex + 2 < 10) oppBusyCells[numberIndex - 1][letterIndex + 2] = true;
                if (numberIndex + 1 < 10 && letterIndex + 3 < 10) oppBusyCells[numberIndex + 1][letterIndex + 3] = true;
                if (numberIndex - 1 >= 0 && letterIndex + 3 < 10) oppBusyCells[numberIndex - 1][letterIndex + 3] = true;
            }

            if (isVertical) {
                if (letterIndex - 1 >= 0) oppBusyCells[numberIndex][letterIndex - 1] = true;
                if (letterIndex + 1 < 10) oppBusyCells[numberIndex][letterIndex + 1] = true;
                if (letterIndex - 1 >= 0) oppBusyCells[numberIndex + 1][letterIndex - 1] = true;
                if (letterIndex + 1 < 10) oppBusyCells[numberIndex + 1][letterIndex + 1] = true;

                if (numberIndex - 1 >= 0) {
                    oppBusyCells[numberIndex - 1][letterIndex] = true;
                    if (letterIndex < 9) oppBusyCells[numberIndex - 1][letterIndex + 1] = true;
                }
                if (numberIndex + 3 < 10) {
                    oppBusyCells[numberIndex + 3][letterIndex] = true;
                    if (letterIndex < 9) oppBusyCells[numberIndex + 3][letterIndex + 1] = true;
                }

                if (numberIndex - 1 >= 0 && letterIndex - 1 >= 0) oppBusyCells[numberIndex - 1][letterIndex - 1] = true;
                if (numberIndex + 2 < 10 && letterIndex - 1 >= 0) oppBusyCells[numberIndex + 2][letterIndex - 1] = true;
                if (numberIndex + 2 < 10 && letterIndex + 1 < 10) oppBusyCells[numberIndex + 2][letterIndex + 1] = true;
                if (numberIndex + 3 < 10 && letterIndex - 1 >= 0) oppBusyCells[numberIndex + 3][letterIndex - 1] = true;
                if (numberIndex + 3 < 10 && letterIndex + 1 < 10) oppBusyCells[numberIndex + 3][letterIndex + 1] = true;
                if (numberIndex - 1 >= 0 && letterIndex + 1 < 10) oppBusyCells[numberIndex - 1][letterIndex + 1] = true;
            }

            oppField.setShipCell(numberIndex, letterIndex, oppField.getShipCell());
            if (isVertical) {
                oppField.setShipCell(numberIndex + 1, letterIndex, oppField.getShipCell());
                oppField.setShipCell(numberIndex + 2, letterIndex, oppField.getShipCell());
            }
            if (!isVertical) {
                oppField.setShipCell(numberIndex, letterIndex + 1, oppField.getShipCell());
                oppField.setShipCell(numberIndex, letterIndex + 2, oppField.getShipCell());
            }
        }


        //      ======================= AI 2-decker placement method =======================
                for (int i = 0; i < 3; i++) {
            isVertical = rHorVer.nextBoolean();
            if (isVertical) {
                letterIndex = rLeftLetter.nextInt(0, 10);
                numberIndex = rUpperNumber.nextInt(0, 9);
            } else {
                letterIndex = rLeftLetter.nextInt(0, 9);
                numberIndex = rUpperNumber.nextInt(0, 10);
            }

            if (!isVertical) {
                while (oppShipContainingCells[numberIndex][letterIndex] == oppField.getShipCell() ||
                        oppBusyCells[numberIndex][letterIndex] ||
                        oppShipContainingCells[numberIndex][letterIndex + 1] == oppField.getShipCell() ||
                        oppBusyCells[numberIndex][letterIndex + 1] ||
                        (letterIndex - 1 >= 0 && (oppShipContainingCells[numberIndex][letterIndex - 1] == oppField.getShipCell())) ||
                        (letterIndex + 2 < 10 && oppShipContainingCells[numberIndex][letterIndex + 2] == oppField.getShipCell())
                ) {
                    letterIndex = rLeftLetter.nextInt(0, 9);
                    numberIndex = rUpperNumber.nextInt(0, 10);
                }
            }

            if (isVertical) {
                while (oppShipContainingCells[numberIndex][letterIndex] == oppField.getShipCell() ||
                        oppBusyCells[numberIndex][letterIndex] ||
                        oppShipContainingCells[numberIndex + 1][letterIndex] == oppField.getShipCell() ||
                        oppBusyCells[numberIndex + 1][letterIndex] ||
                        (numberIndex - 1 >= 0 && (oppShipContainingCells[numberIndex - 1][letterIndex] == oppField.getShipCell())) ||
                        (numberIndex + 2 < 10 && oppShipContainingCells[numberIndex + 2][letterIndex] == oppField.getShipCell())
                ) {
                    letterIndex = rLeftLetter.nextInt(0, 10);
                    numberIndex = rUpperNumber.nextInt(0, 9);
                }
            }

            oppDestroyers[i] = new Ship(2);
            oppDestroyers[i].setVertical(isVertical);
            oppShipArray[i+3] = oppDestroyers[i];

            oppShipContainingCells[numberIndex][letterIndex] = oppField.getShipCell();
            oppDestroyerCells[i][0][0] = oppShipContainingCells[numberIndex][letterIndex];
            if (isVertical) {
                oppShipContainingCells[numberIndex + 1][letterIndex] = oppField.getShipCell();
                oppDestroyerCells[i][1][1] = oppShipContainingCells[numberIndex + 1][letterIndex];
            }
            if (!isVertical) {
                oppShipContainingCells[numberIndex][letterIndex + 1] = oppField.getShipCell();
                oppDestroyerCells[i][1][1] = oppShipContainingCells[numberIndex][letterIndex + 1];
            }
            if (!isVertical) {
                if (numberIndex - 1 >= 0) {
                    oppBusyCells[numberIndex - 1][letterIndex] = true;
                    if (letterIndex < 9) oppBusyCells[numberIndex - 1][letterIndex + 1] = true;
                }
                if (numberIndex + 1 < 10) {
                    oppBusyCells[numberIndex + 1][letterIndex] = true;
                    if (letterIndex < 9) oppBusyCells[numberIndex + 1][letterIndex + 1] = true;
                }
                if (letterIndex - 1 >= 0) oppBusyCells[numberIndex][letterIndex - 1] = true;
                if (letterIndex + 2 < 10) oppBusyCells[numberIndex][letterIndex + 2] = true;
                if (numberIndex - 1 >= 0 && letterIndex - 1 >= 0) oppBusyCells[numberIndex - 1][letterIndex - 1] = true;
                if (numberIndex + 1 < 10 && letterIndex - 1 >= 0) oppBusyCells[numberIndex + 1][letterIndex - 1] = true;
                if (numberIndex + 1 < 10 && letterIndex + 2 < 10) oppBusyCells[numberIndex + 1][letterIndex + 2] = true;
                if (numberIndex - 1 >= 0 && letterIndex + 2 < 10) oppBusyCells[numberIndex - 1][letterIndex + 2] = true;
            }

            if (isVertical) {
                if (letterIndex - 1 >= 0) oppBusyCells[numberIndex][letterIndex - 1] = true;
                if (letterIndex + 1 < 10) oppBusyCells[numberIndex][letterIndex + 1] = true;
                if (letterIndex - 1 >= 0) oppBusyCells[numberIndex + 1][letterIndex - 1] = true;
                if (letterIndex + 1 < 10) oppBusyCells[numberIndex + 1][letterIndex + 1] = true;

                if (numberIndex - 1 >= 0) {
                    oppBusyCells[numberIndex - 1][letterIndex] = true;
                    if (letterIndex < 9) oppBusyCells[numberIndex - 1][letterIndex + 1] = true;
                }
                if (numberIndex + 2 < 10) {
                    oppBusyCells[numberIndex + 2][letterIndex] = true;
                    if (letterIndex < 9) oppBusyCells[numberIndex + 2][letterIndex + 1] = true;
                }

                if (numberIndex - 1 >= 0 && letterIndex - 1 >= 0) oppBusyCells[numberIndex - 1][letterIndex - 1] = true;
                if (numberIndex + 2 < 10 && letterIndex - 1 >= 0) oppBusyCells[numberIndex + 2][letterIndex - 1] = true;
                if (numberIndex + 2 < 10 && letterIndex + 1 < 10) oppBusyCells[numberIndex + 2][letterIndex + 1] = true;
                if (numberIndex - 1 >= 0 && letterIndex + 1 < 10) oppBusyCells[numberIndex - 1][letterIndex + 1] = true;
            }

            oppField.setShipCell(numberIndex, letterIndex, oppField.getShipCell());
            if (isVertical) {
                oppField.setShipCell(numberIndex + 1, letterIndex, oppField.getShipCell());
            }
            if (!isVertical) {
                oppField.setShipCell(numberIndex, letterIndex + 1, oppField.getShipCell());
            }
        }

//        oppField.repaint(false, oppBusyCells);
        return oppField;
    }

    public static Field placeOppSubmarines(Field oppField, boolean[][] oppBusyCells, char[][] oppShipContainingCells, int [][][] oppSubmarineCells, Ship[] oppSubmarines) {
        //      ======================= AI 1-decker placement method =======================
        Random rLeftLetter = new Random();
        Random rUpperNumber = new Random();
        int letterIndex;
        int numberIndex;
        for (int i = 0; i < 4; i++) {
            letterIndex = rLeftLetter.nextInt(0, 10);
            numberIndex = rUpperNumber.nextInt(0, 10);

//            if (oppShipContainingCells[numberIndex][letterIndex] == oppField.getShipCell() ||
//                    oppBusyCells[numberIndex][letterIndex]) {
//                return placeOppSubmarines(oppField, oppBusyCells, oppShipContainingCells);
//            }

                while (oppShipContainingCells[numberIndex][letterIndex] == oppField.getShipCell() ||
                        oppBusyCells[numberIndex][letterIndex]
                ) {
                    letterIndex = rLeftLetter.nextInt(0, 10);
                    numberIndex = rUpperNumber.nextInt(0, 10);
                }

            oppSubmarines[i] = new Ship(1);
            oppShipArray[i+6] = oppSubmarines[i];

            oppShipContainingCells[numberIndex][letterIndex] = oppField.getShipCell();
            oppSubmarineCells[i][0][0] = oppShipContainingCells[numberIndex][letterIndex];
            if (numberIndex - 1 >= 0) oppBusyCells[numberIndex - 1][letterIndex] = true;
            if (numberIndex + 1 < 10) oppBusyCells[numberIndex + 1][letterIndex] = true;
            if (letterIndex - 1 >= 0) oppBusyCells[numberIndex][letterIndex - 1] = true;
            if (letterIndex + 1 < 10) oppBusyCells[numberIndex][letterIndex + 1] = true;
            if (numberIndex - 1 >= 0 && letterIndex - 1 >= 0) oppBusyCells[numberIndex - 1][letterIndex - 1] = true;
            if (numberIndex + 1 < 10 && letterIndex - 1 >= 0) oppBusyCells[numberIndex + 1][letterIndex - 1] = true;
            if (numberIndex + 1 < 10 && letterIndex + 1 < 10) oppBusyCells[numberIndex + 1][letterIndex + 1] = true;
            if (numberIndex - 1 >= 0 && letterIndex + 1 < 10) oppBusyCells[numberIndex - 1][letterIndex + 1] = true;
            oppField.setShipCell(numberIndex, letterIndex, oppField.getShipCell());
        }
        oppField.repaint(false, oppBusyCells);
        return oppField;
    }
}

