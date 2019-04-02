import java.awt.*;
import java.util.Random;

public class GameOfLife {
    private byte[][] cells;
    private byte[][] cellsNew;

    private int arraySizeX = 1920;
    private int arraySizeY = 1080;

    private int canvasWidth = 1920;
    private int canvasHeight = 1080;

    private int fillingPercent = 50;

    public GameOfLife() {
        // setting up
        cells = new byte[arraySizeX][arraySizeY];

//        createTestGlider(cells);

        fillRandomly(cells);

        StdDraw.enableDoubleBuffering();

        StdDraw.setCanvasSize(canvasWidth, canvasHeight);
        StdDraw.setXscale(0, arraySizeX - 1);
        StdDraw.setYscale(0, arraySizeY - 1);
        StdDraw.clear(Color.WHITE);
        StdDraw.setPenColor(Color.BLACK);
    }

    private void createTestGlider(byte[][] arr) {
        arr[2][3] = 1;
        arr[3][4] = 1;
        arr[4][2] = 1;
        arr[4][3] = 1;
        arr[4][4] = 1;
    }

    public static void main(String[] args) {
        GameOfLife game = new GameOfLife();

        for (; ; ) {
            long tStart = System.currentTimeMillis();

            game.draw();
            game.nextEpoch();

            long tFrame = System.currentTimeMillis() - tStart;
            String time = "frame: " + tFrame + "ms";
            String fps = "fps: " + (1000.0 / tFrame);

            System.out.println(time + " " + fps);
        }
    }

    public void fillRandomly(byte[][] arr) {
        Random rnd = new Random();

        for (int i = 0; i < arr.length; i += 1) {
            for (int j = 0; j < arr[0].length; j += 1) {
                int p = rnd.nextInt(101);
                if (p > 100 - fillingPercent)
                {
                    arr[i][j] = 1;
                }
            }
        }
    }

    public void nextEpoch() {
        cellsNew = new byte[arraySizeX][arraySizeY];

        for (int i = 1; i < cells.length - 1; i++) {
            for (int j = 1; j < cells[0].length - 1; j++) {
                int count = 0;

                for (int k = -1; k <= 1; k++) {
                    for (int l = -1; l <= 1; l++) {
                        count += cells[i + k][j + l];
                    }
                }

                count -= cells[i][j];

                if (count == 3 && cells[i][j] == 0) {
                    cellsNew[i][j] = 1;
                } else {
                    if ((count == 2 || count == 3) && cells[i][j] == 1) {
                        cellsNew[i][j] = 1;
                    } else {
                        cellsNew[i][j] = 0;
                    }
                }
            }
        }

        cells = cellsNew;
    }

    public void draw() {
        StdDraw.clear(Color.WHITE);

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                if (cells[i][j] == 1) {
                    StdDraw.filledSquare(i, j, 0.5f);
                }
            }
        }

        StdDraw.show();
    }
}
