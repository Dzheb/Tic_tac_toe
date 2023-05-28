import java.util.Random;
import java.util.Scanner;

public class Program {
    private static final  int WIN_COUNT = 4;
    private  static final char DOT_HUMAN = 'X';
    private  static final char DOT_AI = '0';
    private  static final char DOT_EMPTY = '.';
    private  static final Scanner SCANNER = new Scanner(System.in);
    private  static char [][] field;
    private  static final Random random = new Random();
    private  static int fieldSizeX;
    private  static int fieldSizeY;

    public static void main(String[] args) {
//          initialize();
//          printField();
        while (true){
            initialize();
            printField();
            while (true){
                humanTurn();
                printField();
                if (gameCheck(DOT_HUMAN, "Вы победили!"))
                    break;
                aiTurn();
                printField();
                if (gameCheck(DOT_AI, "Компьютер победил!"))
                    break;
            }
            System.out.println("Желаете сыграть еще раз? (Y - да)");
            if (!SCANNER.next().equalsIgnoreCase("Y"))
                break;
        }
    }
        private static void initialize () {
            fieldSizeX = 5;
        fieldSizeY = 5;
        field = new char[fieldSizeX][fieldSizeY];
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                field[x][y] = DOT_EMPTY;
            }
        }
    }
    /**
     * Отрисовка игрового поля
     */
    private static void printField() {
        System.out.print("+");
        for (int i = 0; i <fieldSizeX * 2 + 1 ; i++) {
            System.out.print((i%2 == 0) ? "-": i / 2 + 1);

        }
        System.out.println();
        for (int i = 0; i < fieldSizeX; i++) {
            System.out.print(i + 1 + "|");

            for (int j = 0; j < fieldSizeY; j++)
                System.out.print(field[i][j] + "|");
            System.out.println();
        }
        for (int i = 0; i < fieldSizeX * 2 + 2; i++){
            System.out.print("-");
        }
        System.out.println();
    }
    /**
     * Обработка хода игрока (человек)
     */
    private static void humanTurn(){
        int x, y;
        do
        {
            System.out.print("Введите координаты хода X и Y (от 1 до 5) через пробел >>> ");
            x = SCANNER.nextInt() - 1;
            y = SCANNER.nextInt() - 1;
        }
        while (!isCellValid(x, y) || !isCellEmpty(x, y));
        field[x][y] = DOT_HUMAN;
    }
    /**
     * Проверка, ячейка является пустой
     * @param x
     * @param y
     * @return
     */
    static boolean isCellEmpty(int x, int y){
        return field[x][y] == DOT_EMPTY;
    }

    /**
     * Проверка корректности ввода
     * (координаты хода не должны превышать размерность массива, игрового поля)
     * @param x
     * @param y
     * @return
     */
    static boolean isCellValid(int x, int y){
        return x >= 0 &&  x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    /**
     * Ход компьютера
     */
    private static void aiTurn(){
        int x, y;
        do
        {
            x = random.nextInt(fieldSizeX);
            y = random.nextInt(fieldSizeY);
        }
        while (!isCellEmpty(x, y));
        field[x][y] = DOT_AI;
    }
    /** Проверка строк и столбцов */
    static boolean checkLines(char c) {
        int colCounter = 0;
        int rowCounter = 0;
        for (int col=0; col< fieldSizeX; col++) {
            for (int row=0; row<fieldSizeY; row++) {
                if (field[col][row] == c) colCounter++;
                if (field[row][col] == c) rowCounter++;
            }
            // Проверка колонки и столбца на наличие победы
            if (colCounter >= WIN_COUNT || rowCounter >= WIN_COUNT) return true;
        }
        return false;
    }
    /** Проверка диагонали */
    static boolean checkDiagonal(char c, int x, int y) {
        int rightdownCounter = 0;
        int leftdownCounter = 0;
        for (int i=0; i< fieldSizeX; i++) {
            if (field[i][i] == c) rightdownCounter++;
            if (field[fieldSizeY-i-1][i] == c) leftdownCounter++;
            if (rightdownCounter >= WIN_COUNT || leftdownCounter >= WIN_COUNT) return true;
        }

        return false;
    }
    /**
     * Проверка победы
     * TODO: Переработать метод в домашнем задании
     * @param c
     * @return
     */
    static boolean checkWin(char c){
        boolean checkedLines = checkLines(c) || checkLines(c);
        boolean checkedDiagonal1 = checkDiagonal(c,0,0);
        boolean checkedDiagonal2 = checkDiagonal(c,1,0);
        boolean checkedDiagonal3 = checkDiagonal(c,0,1);
        boolean checkedDiagonal4 = checkDiagonal(c,1,1);
        boolean checkDiagonals = checkDiagonal(c,0,0);
        if(checkDiagonals || checkedLines) {
            return true;
        }

//        if(checkedLines) {
//            return true;
//        }
//        return checkLines(c,0) || checkLines(c,shift) || checkDiagonal(c,0,0) ||
//                checkDiagonal(c,1,0) || checkDiagonal(c,0,1) || checkDiagonal(c,1,1);

        // Проверка по 4 горизонталям
//        if (field[0][0] == c && field[0][1] == c && field[0][2] == c && field[0][3] == c) return true;
//        if (field[1][0] == c && field[1][1] == c && field[1][2] == c && field[1][3] == c) return true;
//        if (field[2][0] == c && field[2][1] == c && field[2][2] == c && field[2][3] == c) return true;
//        if (field[3][0] == c && field[3][1] == c && field[3][2] == c && field[3][3] == c) return true;

        // Проверка по диагоналям
        // сверху слева --- вниз направо
        //if (field[0][0] == c && field[1][1] == c && field[2][2] == c && field[3][3] == c) return true;
        // сверху справа --- вниз налево
        //if (field[0][3] == c && field[1][2] == c && field[2][1] == c && field[3][0] == c) return true;
        // Проверка по 4 вертикалям
        // if (field[0][0] == c && field[1][0] == c && field[2][0] == c && field[3][0] == c) return true;
        // if (field[0][1] == c && field[1][1] == c && field[2][1] == c && field[3][1] == c) return true;
//        if (field[0][2] == c && field[1][2] == c && field[2][2] == c && field[3][2] == c) return true;
//        if (field[0][3] == c && field[1][3] == c && field[2][3] == c && field[3][3] == c) return true;

        return false;
    }

    /**
     * Проверка на ничью
     * @return
     */
    static boolean checkDraw(){
        for (int x = 0; x < fieldSizeX; x++){
            for (int y = 0; y < fieldSizeY; y++)
                if (isCellEmpty(x, y)) return false;
        }
        return true;
    }

    /**
     * Метод проверки состояния игры
     * @param c
     * @param str
     * @return
     */
    static boolean gameCheck(char c, String str){
        if (checkWin(c)){
            System.out.println(str);
            return true;
        }
        if (checkDraw()){
            System.out.println("Ничья!");
            return true;
        }

        return false; // Игра продолжается
    }

// end program
    }
