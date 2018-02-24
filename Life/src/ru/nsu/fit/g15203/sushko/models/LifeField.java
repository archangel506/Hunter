package ru.nsu.fit.g15203.sushko.models;

import java.awt.*;
import java.util.ArrayList;

public class LifeField {
    public static final double FST_IMPACT = 1.0;
    public static final double SND_IMPACT = 0.3;
    public static final double LIVE_BEGIN = 2.0;
    public static final double LIVE_END = 3.3;
    public static final double BIRTH_BEGIN = 2.3;
    public static final double BIRTH_END = 2.9;


    private double[][] nextState;
    private double[][] impact;
    private boolean[][] liveState;

    public LifeField(int height, int width) {
        impact = new double[height][width];
        nextState = new double[height][width];
        liveState = new boolean[height][width];
        resetField();
    }

    public void nextStep() {
        for (int i = 0; i < impact.length; ++i) {
            boolean longLine = (i % 2 == 0);
            for (int j = 0; j < (longLine ? impact[i].length : impact[i].length - 1); ++j) {
                nextState[i][j]
                        = FST_IMPACT * countFirstNeighbor(i, j, longLine) + SND_IMPACT * countTwelveNeighbor(i, j, longLine);
            }
        }
        double[][] temp = nextState;
        nextState = impact;
        impact = temp;

        for (int i = 0; i < impact.length; ++i) {
            boolean longLine = (i % 2 == 0);
            for (int j = 0; j < (longLine ? impact[i].length : impact[i].length - 1); ++j) {
                if (liveState[i][j]) {
                    if (!continueLife(i, j)) {
                        liveState[i][j] = false;
                    }
                } else {
                    if (readyBirth(i, j)) {
                        liveState[i][j] = true;
                    }
                }
            }
        }
    }

    public boolean isLive(int x, int y) {
        return liveState[x][y];
    }

    private boolean continueLife(int x, int y) {
        return impact[x][y] >= LifeField.LIVE_BEGIN && impact[x][y] <= LifeField.LIVE_END;
    }

    private boolean readyBirth(int x, int y) {
        return impact[x][y] >= LifeField.BIRTH_BEGIN && impact[x][y] <= LifeField.BIRTH_END;
    }

    public void resetField() {
        for (int i = 0; i < impact.length; ++i) {
            for (int j = 0; j < impact[i].length; ++j) {
                impact[i][j] = 0;
                liveState[i][j] = false;
            }
        }
    }

    public int getWidth() {
        return impact[0].length;
    }

    public int getHeight() {
        return impact.length;
    }

    public void setState(int x, int y, boolean state) {
        liveState[x][y] = state;
        boolean longLine = (x % 2 == 0);

        ArrayList<Point> list = getFirstNeighbor(x, y, longLine);
        for (Point point : list) {
            boolean temp = point.x % 2 == 0;
            impact[point.x][point.y]
                    = FST_IMPACT * countFirstNeighbor(point.x, point.y, temp)
                    + SND_IMPACT * countTwelveNeighbor(point.x, point.y, temp);
        }
        ArrayList<Point> list2 = getTwelveNeighbor(x, y, longLine);
        for (Point point : list2) {
            boolean temp = point.x % 2 == 0;
            impact[point.x][point.y]
                    = FST_IMPACT * countFirstNeighbor(point.x, point.y, temp)
                    + SND_IMPACT * countTwelveNeighbor(point.x, point.y, temp);
        }

    }

    public boolean getLifeState(int x, int y) {
        return liveState[x][y];
    }

    public double getImpactState(int x, int y) {
        return impact[x][y];
    }

    private int countFirstNeighbor(int x, int y, boolean longLine) {
        int fst_count = 0;

        ArrayList<Point> list = getFirstNeighbor(x, y, longLine);
        for (Point point : list) {
            if (isLive(point.x, point.y)) {
                ++fst_count;
            }
        }

        return fst_count;
    }

    private ArrayList<Point> getFirstNeighbor(int x, int y, boolean longLine) {
        ArrayList<Point> arrayList = new ArrayList<>();
        boolean leftNeighbor = y > 0;
        if (leftNeighbor) {
            arrayList.add(new Point(x, y - 1));
        }
        boolean rightNeighbor = (longLine ? y < impact[0].length - 1 : y < impact[0].length - 2);
        if (rightNeighbor) {
            arrayList.add(new Point(x, y + 1));
        }
        boolean leftTopNeighbor = (longLine ? y > 0 : true) && x > 0;
        if (leftTopNeighbor) {
            arrayList.add(new Point(x - 1, longLine ? y - 1 : y));
        }
        boolean rightTopNeighbor = (longLine ? true : y < impact[0].length - 1) && x > 0;
        if (rightTopNeighbor) {
            arrayList.add(new Point(x - 1, longLine ? y : y + 1));
        }
        boolean leftDownNeighbor = (longLine ? y > 0 : true) && x < impact.length - 1;
        if (leftDownNeighbor) {
            arrayList.add(new Point(x + 1, longLine ? y - 1 : y));
        }
        boolean rightDownNeighbor = (longLine ? true : y < impact[0].length - 1)
                && x < impact.length - 1;
        if (rightDownNeighbor) {
            arrayList.add(new Point(x + 1, longLine ? y : y + 1));
        }

        return arrayList;
    }

    private int countTwelveNeighbor(int x, int y, boolean longLine) {
        int snd_count = 0;
        ArrayList<Point> list = getTwelveNeighbor(x, y, longLine);
        for (Point point : list) {
            if (isLive(point.x, point.y)) {
                ++snd_count;
            }
        }

        return snd_count;
    }

    private ArrayList<Point> getTwelveNeighbor(int x, int y, boolean longLine) {
        ArrayList<Point> arrayList = new ArrayList<>();
        boolean leftTopNeighbor = (longLine ? y > 1 : y > 0) && x > 0;
        if (leftTopNeighbor) {
            arrayList.add(new Point(x - 1, longLine ? y - 2 : y - 1));
        }
        boolean leftDownNeighbor = (longLine ? y > 1 : y > 0) && x < impact.length - 1;
        if (leftDownNeighbor) {
            arrayList.add(new Point(x + 1, longLine ? y - 2 : y - 1));
        }
        boolean rightTopNeighbor = (longLine ? y < impact[0].length - 1 : y < impact[0].length - 2)
                && x > 0;
        if (rightTopNeighbor) {
            arrayList.add(new Point(x - 1, longLine ? y + 1 : y + 2));
        }
        boolean rightDownNeighbor = (longLine ? y < impact[0].length - 1 : y < impact[0].length - 2)
                && x < impact.length - 1;
        if (rightDownNeighbor) {
            arrayList.add(new Point(x + 1, longLine ? y + 1 : y + 2));

        }
        boolean centerTopNeighbor = x > 1;
        if (centerTopNeighbor) {
            arrayList.add(new Point(x - 2, y));
        }
        boolean centerDownNeighbor = x < impact.length - 2;
        if (centerDownNeighbor) {
            arrayList.add(new Point(x + 2, y));
        }
        return arrayList;
    }
}
