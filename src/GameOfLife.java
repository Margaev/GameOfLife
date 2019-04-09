import java.awt.*;
import java.util.Random;

/**
 * Conway's Game of life
 */

public class GameOfLife {
    public static final int ARRAY_SIZE_X = 1280;
    public static final int ARRAY_SIZE_Y = 720;

    public static final int CANVAS_WIDTH = 1280;
    public static final int CANVAS_HEIGHT = 720;

    public static final int FILLING_PERCENT = 15;

    private byte[][] cells;
    private byte[][] cellsNew;

    public GameOfLife() {
        // setting up
        cells = new byte[ARRAY_SIZE_X][ARRAY_SIZE_Y];
        cellsNew = new byte[ARRAY_SIZE_X][ARRAY_SIZE_Y];

//        createTestGlider(cells);
//        createBlinker(cells);

        fillRandomly(cells);

        StdDraw.enableDoubleBuffering();

        StdDraw.setCanvasSize(CANVAS_WIDTH, CANVAS_HEIGHT);
        StdDraw.setXscale(0, ARRAY_SIZE_X - 1);
        StdDraw.setYscale(0, ARRAY_SIZE_Y - 1);
        StdDraw.clear(Color.WHITE);
        StdDraw.setPenColor(Color.BLACK);
    }

    private void createBlinker(byte[][] arr) {
        arr[5][1] = 1;
        arr[5][2] = 1;
        arr[5][3] = 1;
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

        for (;;) {
            long tStart = System.currentTimeMillis();

            game.draw();
            game.nextEpoch();
            game.swap();

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
                int p = rnd.nextInt(100) + 1;
                if (p > (100 - FILLING_PERCENT))
                {
                    arr[i][j] = 1;
                }
            }
        }
    }

    public void nextEpoch() {
        for (int i = 1; i < cells.length - 1; i++) {
            for (int j = 1; j < cells[0].length - 1; j++) {
                int count = 0;

                for (int k = -1; k <= 1; k++) {
                    for (int l = -1; l <= 1; l++) {
                        count += cells[i + k][j + l];
                    }
                }

                count -= cells[i][j];

                if ((count == 3) && (cells[i][j] == 0)) {
                    cellsNew[i][j] = 1;
                } else {
                    if (((count == 2) || (count == 3)) && (cells[i][j] == 1)) {
                        cellsNew[i][j] = 1;
                    } else {
                        cellsNew[i][j] = 0;
                    }
                }
            }
        }
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

    public void swap() {
        byte[][] t = cells;
        cells = cellsNew;
        cellsNew = t;
    }
}
