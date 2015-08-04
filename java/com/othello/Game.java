package com.othello;

import android.util.Log;

import java.util.ArrayList;

public class Game {
    int[][] matrix = new int[10][10];
    int player;
    MyGLSurfaceView mGLView;

    public Game(MyGLSurfaceView mGLView) {
        this.mGLView = mGLView;
        player = 1;
        matrix[4][4] = 1;
        matrix[4][5] = -1;
        matrix[5][4] = -1;
        matrix[5][5] = 1;
        sendMatrix(null);
    }

    private void sendMatrix(int[][] help) {
        int[][] send = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                send[i][j] = matrix[i + 1][j + 1];
            }
        }
        mGLView.mRenderer.setPiece(send);
        if (help != null) {
            int[][] senda = new int[8][8];
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (help[i + 1][j + 1] != 100) {
                        senda[i][j] = -1;
                    } else {
                        senda[i][j] = 0;
                    }
                }
            }
            mGLView.mRenderer.setHelp(senda);
        }
        mGLView.requestRender();
    }

    jjucbr2 jjucbr = new jjucbr2();
    boolean ai = true;
    boolean help = true;
    int level = 3;

    public void start() {
        if (ai) {
//            level = gui.getLevel();
            if (level > 10) {
                level = 10;
            }
        } else {
            level = 1;
        }

//        gui.updateLabel(player * -1);
        boolean finish = false;
        int noMove = 1;
        boolean valid = false;
        while (!finish) {
            int[][] countMatrix = new int[10][10];
            if (help || ai) {
                if (player == 1) {
                    countMatrix = jjucbr.getAlphaBetaMatrix(matrix, player, 1);
                } else {
                    countMatrix = jjucbr.getAlphaBetaMatrix(matrix, player, level);
                }
            }

            // change turn if cant move
            if (noMove == 0) {
                noMove = 1;
            }
            for (int i = 1; i < 9; i++) {
                for (int j = 1; j < 9; j++) {
                    if (countMatrix[i][j] != 100) {
                        noMove = 0;
                    }
                }
            }
            if (noMove == 1) {
                String[] pl = {"Black", "White"};
//                gui.msg("Player " + pl[(player + 1) / 2] + " no moves");
//                gui.updateLabel(player);
                noMove = 2;
                player = player * -1;
                continue;
            } else if (noMove == 2) {
                int count = 0;
                for (int x = 0; x < 8; x++) {
                    for (int y = 0; y < 8; y++) {

                        if (matrix[x + 1][y + 1] == 1) {
                            count++;
                        } else if (matrix[x + 1][y + 1] == -1) {
                            count--;
                        }

                    }
                }
                if (count == 0) {
//                    gui.msg("Game over, draw");
                } else if (count < 0) {
//                    gui.msg("Game over, Black won with " + -count);
                } else {
//                    gui.msg("Game over, White won with " + count);
                }
                finish = true;
                continue;
            }
            // Wait until player pushes a button which is a valid

            int x = 0, y = 0;
            if (player == 1 || !ai) {
                sendMatrix(countMatrix);
                while (!valid) {

                    while (mGLView.b < 0) {

                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
//                    gui.updateLabel(player);
                    x = mGLView.b / 10;
                    y = mGLView.b % 10;

                    valid = turn(x, y, player);
                    mGLView.b = -1;
                }
                x = -1;
                y = -1;
            } else {
                int min = 1000;
                for (int i = 1; i < 9; i++) {
                    for (int j = 1; j < 9; j++) {
                        int t = countMatrix[i][j];
                        if (t < min && t != 100) {
                            min = countMatrix[i][j];
                            x = i - 1;
                            y = j - 1;
                        }
                    }
                }
//                gui.updateLabel(player);
                valid = turn(x, y, player);
            }
            valid = false;
//            gui.paint(matrix, x, y);
            sendMatrix(null);

            if (ai && player == -1) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public boolean turn(int x, int y, int player) {

        boolean valid = false;
        x++;
        y++;
        int a = 0;
        int b = 0;

        if (matrix[x][y] == 0) {

            ArrayList<Integer> change = new ArrayList<Integer>(16);
            if (matrix[x - 1][y - 1] == player * -1) {
                int tx = x - 1, ty = y - 1;
                while (matrix[tx][ty] == player * -1) {
                    change.add(tx);
                    change.add(ty);
                    tx--;
                    ty--;
                }
                if (matrix[tx][ty] == player) {
                    for (int i = 0; i < change.size(); i++) {
                        a = change.get(i);
                        i++;
                        b = change.get(i);
                        matrix[a][b] = player;
                    }
                    valid = true;
                }
                change.clear();
            }

            if (matrix[x][y - 1] == player * -1) {
                int tx = x, ty = y - 1;
                while (matrix[tx][ty] == player * -1) {
                    change.add(tx);
                    change.add(ty);
                    ty--;
                }
                if (matrix[tx][ty] == player) {
                    for (int i = 0; i < change.size(); i++) {
                        a = change.get(i);
                        i++;
                        b = change.get(i);
                        matrix[a][b] = player;
                    }
                    valid = true;
                }
                change.clear();
            }

            if (matrix[x + 1][y - 1] == player * -1) {
                int tx = x + 1, ty = y - 1;
                while (matrix[tx][ty] == player * -1) {
                    change.add(tx);
                    change.add(ty);
                    ty--;
                    tx++;
                }
                if (matrix[tx][ty] == player) {
                    for (int i = 0; i < change.size(); i++) {
                        a = change.get(i);
                        i++;
                        b = change.get(i);
                        matrix[a][b] = player;
                    }
                    valid = true;
                }
                change.clear();

            }

            if (matrix[x + 1][y] == player * -1) {
                int tx = x + 1, ty = y;
                while (matrix[tx][ty] == player * -1) {
                    change.add(tx);
                    change.add(ty);
                    tx++;
                }
                if (matrix[tx][ty] == player) {
                    for (int i = 0; i < change.size(); i++) {
                        a = change.get(i);
                        i++;
                        b = change.get(i);
                        matrix[a][b] = player;
                    }
                    valid = true;
                }
                change.clear();

            }

            if (matrix[x + 1][y + 1] == player * -1) {
                int tx = x + 1, ty = y + 1;
                while (matrix[tx][ty] == player * -1) {
                    change.add(tx);
                    change.add(ty);
                    ty++;
                    tx++;
                }
                if (matrix[tx][ty] == player) {
                    for (int i = 0; i < change.size(); i++) {
                        a = change.get(i);
                        i++;
                        b = change.get(i);
                        matrix[a][b] = player;
                    }
                    valid = true;
                }
                change.clear();
            }

            if (matrix[x][y + 1] == player * -1) {
                int tx = x, ty = y + 1;
                while (matrix[tx][ty] == player * -1) {
                    change.add(tx);
                    change.add(ty);
                    ty++;
                }
                if (matrix[tx][ty] == player) {
                    for (int i = 0; i < change.size(); i++) {
                        a = change.get(i);
                        i++;
                        b = change.get(i);
                        matrix[a][b] = player;
                    }
                    valid = true;
                }
                change.clear();
            }

            if (matrix[x - 1][y + 1] == player * -1) {
                int tx = x - 1, ty = y + 1;
                while (matrix[tx][ty] == player * -1) {
                    change.add(tx);
                    change.add(ty);
                    ty++;
                    tx--;
                }
                if (matrix[tx][ty] == player) {
                    for (int i = 0; i < change.size(); i++) {
                        a = change.get(i);
                        i++;
                        b = change.get(i);
                        matrix[a][b] = player;
                    }
                    valid = true;
                }
                change.clear();
            }

            if (matrix[x - 1][y] == player * -1) {
                int tx = x - 1, ty = y;
                while (matrix[tx][ty] == player * -1) {
                    change.add(tx);
                    change.add(ty);
                    tx--;
                }
                if (matrix[tx][ty] == player) {
                    for (int i = 0; i < change.size(); i++) {
                        a = change.get(i);
                        i++;
                        b = change.get(i);
                        matrix[a][b] = player;
                    }
                    valid = true;
                }
                change.clear();

            }

            if (valid) {
                matrix[x][y] = player;
                this.player = player * -1;
            }
        }

        return valid;
    }
}