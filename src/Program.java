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
    /** Проверка строк и столбцов
     * @param c
     * @return true в случае победы иначе false
     */
    static boolean checkLines(char c) {

        for (int col=0; col< fieldSizeX; col++) {
            int colCounter = 0;
            int rowCounter = 0;
            for (int row=0; row<fieldSizeY; row++) {
                if (field[col][row] == c) rowCounter++;
                if (field[row][col] == c) colCounter++;
           }
            // Проверка колонки и столбца на наличие победы
                if (rowCounter >= WIN_COUNT || colCounter >= WIN_COUNT) return true;
            }
        return false;
    }
    /** Проверка диагоналей
     * @param c
     * @return true в случае победы иначе false
     **/
    static boolean checkDiagonal(char c) {
        // проверка пересекающихся в центре диагоналей
        int rightdownCounter1 = 0;
        int leftdownCounter1 = 0;
        for (int i=0; i< fieldSizeX; i++) {
            if (field[i][i] == c) rightdownCounter1++;
            if (field[fieldSizeY-i-1][i] == c) leftdownCounter1++;
            if (rightdownCounter1 >= WIN_COUNT || leftdownCounter1 >= WIN_COUNT)
            {System.out.println("diagonal1");
            return true;}
        }
        // смещение окна проверки на ячейку вниз
        int rightdownCounter2 = 0;
        int leftdownCounter2 = 0;
        for (int i=0; i< WIN_COUNT; i++) {
            if (field[i][i+1] == c) rightdownCounter2++;
            if (field[fieldSizeY-i-1][i+1] == c) leftdownCounter2++;
            if (rightdownCounter2 >= WIN_COUNT || leftdownCounter2 >= WIN_COUNT)
            {System.out.println("diagonal2");
                return true;}
        }

        // смещение окна проверки на ячейку вправо
        int rightdownCounter3 = 0;
        int leftdownCounter3 = 0;
        for (int i=0; i< WIN_COUNT; i++) {
            if (field[i+1][i] == c) rightdownCounter3++;
            if (field[fieldSizeY-i-1][i] == c) leftdownCounter3++;
            if (rightdownCounter3 >= WIN_COUNT || leftdownCounter3 >= WIN_COUNT)
            {System.out.println("diagonal3");
                return true;}
        }

        return false;
    }
    /**
     * Проверка победы
     * @param c
     * @return true в случае победы иначе false
     */
    static boolean checkWin(char c){
        if(checkDiagonal(c) || checkLines(c)) {
            return true;
        }
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

    }
