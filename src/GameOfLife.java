import java.awt.*;
import java.util.Random;

// Conway's Game of life
public class GameOfLife {
    private byte[][] cells;
    private byte[][] cellsNew;

    private int arraySizeX = 1280;
    private int arraySizeY = 720;

    private int canvasWidth = 1280;
    private int canvasHeight = 720;

    private int fillingPercent = 15;

    private boolean reverted = false;

    public GameOfLife() {
        // setting up
        cells = new byte[arraySizeX][arraySizeY];
        cellsNew = new byte[arraySizeX][arraySizeY];

//        createTestGlider(cells);
//        createBlinker(cells);

        fillRandomly(cells);

        StdDraw.enableDoubleBuffering();

        StdDraw.setCanvasSize(canvasWidth, canvasHeight);
        StdDraw.setXscale(0, arraySizeX - 1);
        StdDraw.setYscale(0, arraySizeY - 1);
        StdDraw.clear(Color.WHITE);
        StdDraw.setPenColor(Color.BLACK);
    }

    private void createBlinker(byte[][] arr) {
        arr[2][1] = 1;
        arr[2][2] = 1;
        arr[2][3] = 1;
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


            if (!game.reverted) {
                game.draw(game.cells);
                game.nextEpoch(game.cells, game.cellsNew);
            } else {
                game.draw(game.cellsNew);
                game.nextEpoch(game.cellsNew, game.cells);
            }

            game.reverted = !game.reverted;

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
                if (p > (100 - fillingPercent))
                {
                    arr[i][j] = 1;
                }
            }
        }
    }

    private void nextEpoch(byte[][] oldCells, byte[][] newCells) {
        for (int i = 1; i < cells.length - 1; i++) {
            for (int j = 1; j < cells[0].length - 1; j++) {
                int count = 0;

                for (int k = -1; k <= 1; k++) {
                    for (int l = -1; l <= 1; l++) {
                        count += oldCells[i + k][j + l];
                    }
                }

                count -= oldCells[i][j];

                if ((count == 3) && (oldCells[i][j] == 0)) {
                    newCells[i][j] = 1;
                } else {
                    if (((count == 2) || (count == 3)) && (oldCells[i][j] == 1)) {
                        newCells[i][j] = 1;
                    } else {
                        newCells[i][j] = 0;
                    }
                }
            }
        }
    }


    public void draw(byte[][] arr) {
        StdDraw.clear(Color.WHITE);

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                if (arr[i][j] == 1) {
                    StdDraw.filledSquare(i, j, 0.5f);
                }
            }
        }

        StdDraw.show();
    }
}
