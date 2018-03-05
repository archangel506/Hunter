package ru.nsu.fit.g15203.sushko.models;

import java.awt.*;
import java.util.ArrayList;

public class LifeField {
    private double FST_IMPACT = 1.0;
    private double SND_IMPACT = 0.3;
    private double LIVE_BEGIN = 2.0;
    private double LIVE_END = 3.3;
    private double BIRTH_BEGIN = 2.3;
    private double BIRTH_END = 2.9;


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
                        = FST_IMPACT * countFirstNeighbor(i, j) + SND_IMPACT * countSecondNeighbor(i, j, longLine);
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
        return impact[x][y] >= LIVE_BEGIN && impact[x][y] <= LIVE_END;
    }

    private boolean readyBirth(int x, int y) {
        return impact[x][y] >= BIRTH_BEGIN && impact[x][y] <= BIRTH_END;
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

        ArrayList<Point> list = getFirstNeighbor(x, y);
        for (Point point : list) {
            boolean temp = point.x % 2 == 0;
            impact[point.x][point.y]
                    = FST_IMPACT * countFirstNeighbor(point.x, point.y)
                    + SND_IMPACT * countSecondNeighbor(point.x, point.y, temp);
        }
        ArrayList<Point> list2 = getSecondNeighbor(x, y, longLine);
        for (Point point : list2) {
            boolean temp = point.x % 2 == 0;
            impact[point.x][point.y]
                    = FST_IMPACT * countFirstNeighbor(point.x, point.y)
                    + SND_IMPACT * countSecondNeighbor(point.x, point.y, temp);
        }

    }

    public ArrayList<Point> getLifeList(){

        ArrayList<Point> points = new ArrayList<>();
        for(int i = 0; i < liveState.length; ++i){
            for(int j = 0; j < liveState[0].length; ++j){
                if(liveState[i][j]){
                    points.add(new Point(i,j));
                }
            }
        }
        return points;
    }

    private void recalculateImpact(){
        for(int i = 0; i < impact.length; ++i){
            boolean longline = i % 2 == 0;
            for(int j = 0; j < (longline ? impact[0].length : impact[0].length - 1); ++j){
                setState(i, j, liveState[i][j]);
            }
        }
    }

    public boolean getLifeState(int x, int y) {
        return liveState[x][y];
    }

    public double getImpactState(int x, int y) {
        return impact[x][y];
    }

    public void resizeField(int width, int height){
        double[][] newImpact = new double[height][width];
        double[][] twelveBuffer = new double[height][width];
        boolean[][] livemass = new boolean[height][width];
        for(int i = 0; i < newImpact.length; ++i){
            for(int j = 0; j < newImpact[0].length; ++j){
                if(i >= impact.length || j >= impact[0].length){
                    newImpact[i][j] = 0;
                    twelveBuffer[i][j] = 0;
                    livemass[i][j] = false;
                } else{
                    newImpact[i][j] = impact[i][j];
                    twelveBuffer[i][j] = nextState[i][j];
                    livemass[i][j] = liveState[i][j];
                }
            }
        }
        impact = newImpact;
        nextState = twelveBuffer;
        liveState = livemass;
        recalculateImpact();
    }

    public double getFstImpact() {
        return FST_IMPACT;
    }

    public void setFstImpact(double impact){
        FST_IMPACT = impact;
    }

    public double getSndImpact(){
        return SND_IMPACT;
    }

    public void setSndImpact(double impact){
        SND_IMPACT = impact;
    }

    public double getLiveBegin() {
        return LIVE_BEGIN;
    }

    public void setLiveBegin(double live) {
        LIVE_BEGIN = live;
    }

    public double getLiveEnd() {
        return LIVE_END;
    }

    public void setLiveEnd(double live) {
        LIVE_END = live;
    }

    public double getBirthBegin() {
        return BIRTH_BEGIN;
    }

    public void setBirthBegin(double birth) {
        BIRTH_BEGIN = birth;
    }

    public double getBirthEnd() {
        return BIRTH_END;
    }
    public void setBirthEnd(double birth) {
       BIRTH_END = birth;
    }


    private int countFirstNeighbor(int x, int y) {
        int fst_count = 0;

        ArrayList<Point> list = getFirstNeighbor(x, y);
        for (Point point : list) {
            if (isLive(point.x, point.y)) {
                ++fst_count;
            }
        }

        return fst_count;
    }

    public ArrayList<Point> getFirstNeighbor(int x, int y) {
        ArrayList<Point> arrayList = new ArrayList<>();
        boolean longLine = x % 2 == 0;
        boolean leftNeighbor = inField(x, y - 1);
        if (leftNeighbor) {
            arrayList.add(new Point(x, y - 1));
        }
        boolean rightNeighbor = inField(x, y + 1);
        if (rightNeighbor) {
            arrayList.add(new Point(x, y + 1));
        }
        boolean leftTopNeighbor = inField(x - 1, longLine ? y - 1 : y);
        if (leftTopNeighbor) {
            arrayList.add(new Point(x - 1, longLine ? y - 1 : y));
        }
        boolean rightTopNeighbor = inField(x - 1, longLine ? y : y + 1);
        if (rightTopNeighbor) {
            arrayList.add(new Point(x - 1, longLine ? y : y + 1));
        }
        boolean leftDownNeighbor = inField(x + 1, longLine ? y - 1 : y);
        if (leftDownNeighbor) {
            arrayList.add(new Point(x + 1, longLine ? y - 1 : y));
        }
        boolean rightDownNeighbor = inField(x + 1, longLine ? y : y + 1);
        if (rightDownNeighbor) {
            arrayList.add(new Point(x + 1, longLine ? y : y + 1));
        }

        return arrayList;
    }

    private int countSecondNeighbor(int x, int y, boolean longLine) {
        int snd_count = 0;
        ArrayList<Point> list = getSecondNeighbor(x, y, longLine);
        for (Point point : list) {
            if (isLive(point.x, point.y)) {
                ++snd_count;
            }
        }

        return snd_count;
    }

    private boolean inField(int x, int y){
        boolean longLine = x % 2 == 0;
        boolean currectHeight = x >= 0 && x < impact.length;
        boolean currectWidth;
        if(longLine){
            currectWidth = y >= 0 && y < impact[0].length;
        } else {
            currectWidth = y >= 0 && y < impact[0].length - 1;
        }
        return currectHeight && currectWidth;
    }

    public ArrayList<Point> getSecondNeighbor(int x, int y, boolean longLine) {
        ArrayList<Point> arrayList = new ArrayList<>();
        boolean leftTopNeighbor = inField(x - 1, longLine ? y - 2 : y - 1);
        if (leftTopNeighbor) {
            arrayList.add(new Point(x - 1, longLine ? y - 2 : y - 1));
        }
        boolean leftDownNeighbor = inField(x + 1, longLine ? y - 2 : y - 1);
        if (leftDownNeighbor) {
            arrayList.add(new Point(x + 1, longLine ? y - 2 : y - 1));
        }
        boolean rightTopNeighbor = inField(x - 1, longLine ? y + 1 : y + 2);
        if (rightTopNeighbor) {
            arrayList.add(new Point(x - 1, longLine ? y + 1 : y + 2));
        }
        boolean rightDownNeighbor = inField(x + 1, longLine ? y + 1 : y + 2);
        if (rightDownNeighbor) {
            arrayList.add(new Point(x + 1, longLine ? y + 1 : y + 2));

        }
        boolean centerTopNeighbor = inField(x - 2, y);
        if (centerTopNeighbor) {
            arrayList.add(new Point(x - 2, y));
        }
        boolean centerDownNeighbor = inField(x + 2, y);
        if (centerDownNeighbor) {
            arrayList.add(new Point(x + 2, y));
        }
        return arrayList;
    }
}
