package ru.nsu.fit.g15203.sushko.models;

public class LifeField {
    public static final double FST_IMPACT = 1.9;
    public static final double SND_IMPACT = 0.3;
    public static final double LIVE_BEGIN = 2.3;
    public static final double LIVE_END = 5.0;
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
                nextState[i][j] = FST_IMPACT * countFirstNeighbor(i, j, longLine)
                        + SND_IMPACT * countTwelveNeighbor(i, j, longLine);
                if(liveState[i][j]){
                    if(!continueLife(i,j)){
                        liveState[i][j] = false;
                    }
                } else{
                     if(readyBirth(i,j)){
                         liveState[i][j] = true;
                     }
                }
            }
        }
        double[][] temp = nextState;
        nextState = impact;
        impact = temp;
    }

    public boolean isLive(int x, int y) {
        return liveState[x][y];
    }

    private boolean continueLife(int x, int y){
        return nextState[x][y] >= LifeField.LIVE_BEGIN&& nextState[x][y] <= LifeField.LIVE_END;
    }

    private boolean readyBirth(int x, int y){
        return nextState[x][y] >= LifeField.BIRTH_BEGIN&& nextState[x][y] <= LifeField.BIRTH_END;
    }

    public void resetField() {
        for (int i = 0; i < impact.length; ++i) {
            for (int j = 0; j < impact[i].length; ++j) {
                impact[i][j] = 0;
            }
        }
        impact[3][2] = 2.3;
        impact[3][3] = 4;
        impact[3][4] = 2.3;
        impact[4][3] = 3.3;
        impact[4][4] = 3.3;
        liveState[3][2] = true;
        liveState[3][3] = true;
        liveState[3][4] = true;
        liveState[4][3] = true;
        liveState[4][4] = true;
    }

    public int getWidth() {
        return impact[0].length;
    }

    public int getHeight() {
        return impact.length;
    }

    public void setState(int x, int y, double state) {
        impact[x][y] = state;
        liveState[x][y] = true;
    }

    public double getState(int x, int y) {
        return impact[x][y];
    }

    private int countFirstNeighbor(int x, int y, boolean longLine) {
        int fst_count = 0;
        boolean leftTopNeighbor = (longLine ? y > 0 : true) && x > 0;
        boolean rightTopNeighbor = (longLine ? true : y < impact[0].length - 1) && x > 0;
        boolean leftDownNeighbor = (longLine ? y > 0 : true) && x < impact.length - 1;
        boolean rightDownNeighbor = (longLine ? true : y < impact[0].length - 1)
                && x < impact.length - 1;
        boolean leftNeighbor = y > 0;
        boolean rightNeighbor = y < impact.length - 1;


        if (leftNeighbor && isLive(x, y - 1)) {
            ++fst_count;
        }
        if (rightNeighbor && isLive(x, y + 1)) {
            ++fst_count;
        }

        if (leftTopNeighbor && isLive(x - 1, longLine ? y - 1 : y)) {
            ++fst_count;
        }

        if (rightTopNeighbor && isLive(x - 1, longLine ? y : y + 1)) {
            ++fst_count;
        }

        if (leftDownNeighbor && isLive(x + 1, longLine ? y - 1 : y)) {
            ++fst_count;
        }

        if (rightDownNeighbor && isLive(x + 1, longLine ? y : y + 1)) {
            ++fst_count;
        }

        return fst_count;
    }

    private int countTwelveNeighbor(int x, int y, boolean longLine) {
        boolean leftTopNeighbor = (longLine ? y > 1 : y > 0) && x > 0;
        boolean leftDownNeighbor = (longLine ? y > 1 : y > 0) && x < impact.length - 1;
        boolean rightTopNeighbor = (longLine ? y < impact[0].length - 1 : y < impact[0].length - 2)
                && x > 0;
        boolean rightDownNeighbor = (longLine ? y < impact[0].length - 1 : y < impact[0].length - 2)
                && x < impact.length - 1;
        boolean centerTopNeighbor = x > 1;
        boolean centerDownNeighbor = x < impact.length - 2;

        int snd_count = 0;

        if (leftTopNeighbor && isLive(x - 1, longLine ? y - 2 : y - 1)) {
            ++snd_count;
        }

        if (leftDownNeighbor && isLive(x + 1, longLine ? y - 2 : y - 1)) {
            ++snd_count;
        }
        if (rightTopNeighbor && isLive(x - 1, longLine ? y + 1 : y + 2)) {
            ++snd_count;
        }
        if (rightDownNeighbor && isLive(x + 1, longLine ? y + 1 : y + 2)) {
            ++snd_count;
        }
        if (centerTopNeighbor && isLive(x - 2, y)) {
            ++snd_count;
        }
        if (centerDownNeighbor && isLive(x + 2, y)) {
            ++snd_count;
        }


        return snd_count;
    }
}
