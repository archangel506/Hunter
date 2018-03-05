package ru.nsu.fit.g15203.sushko.field;

import ru.nsu.fit.g15203.sushko.hex.HexField;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileMng {
    private static final int NEED_LOAD = -1;
    private HexField field;
    private String lastParseFile = null;


    public FileMng(HexField field) {
        this.field = field;
    }


    public void saveState(File file) {
        if (file == null) {
            return;
        }

        int width = field.getWidthCount();
        int height = field.getHeightCount();
        int radius = field.getRadiusHex();
        int widthLine = field.getWidthLine();
        ArrayList<Point> arrayList = field.getLifeHex();

        if (file.exists()) {
            file.delete();
        }

        try (Writer writer = new FileWriter(file)) {
            file.createNewFile();
            writer.write(width + " " + height + "\n");
            writer.write(widthLine + "\n");
            writer.write(radius + "\n");
            writer.write(arrayList.size() + "\n");
            for (int i = 0; i < arrayList.size(); ++i) {
                Point point = arrayList.get(i);
                if (i != arrayList.size() - 1) {
                    writer.write(point.y + " " + point.x + "\n");
                } else {
                    writer.write(point.y + " " + point.x);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadState(File file) throws ParseFileException {
        if(file == null){
            return;
        }


        ArrayList<Point> arrayList = new ArrayList<>();
        int width = -1;
        int height = -1;
        int radius = -1;
        int widthLine = -1;
        int sizeList = -1;
        try (FileReader reader = new FileReader(file);
             Scanner scanner = new Scanner(reader)) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (width == NEED_LOAD) {
                    Point size = parseTwoSymb(line);
                    if(size != null) {
                        height = size.y;
                        width = size.x;
                    }
                } else {
                    if (widthLine == NEED_LOAD) {
                        widthLine = parseOneSymb(line);
                    } else{
                        if(radius == NEED_LOAD){
                            radius = parseOneSymb(line);
                        } else{
                            if(sizeList == NEED_LOAD) {
                                sizeList = parseOneSymb(line);
                            } else{
                                Point point = parseTwoSymb(line);
                                if(point != null) {
                                    arrayList.add(point);
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(width < 1 || height < 1 || widthLine < 1 || radius < 1){
            throw new ParseFileException();
        }

        for(Point point: arrayList){
            if(point.x >= width || point.y >= height){
                throw new ParseFileException();
            }
        }

        lastParseFile = file.getAbsolutePath();


        field.resetField();
        field.setSizeField(width, height);
        field.setWidthLine(widthLine);
        field.setRadiusHex(radius);
        for(Point point: arrayList){
            field.setLifeHex(point.y, point.x);
        }
        field.reCalculateField();
    }

    public String getLastParseFile() {
        return lastParseFile;
    }

    private Point parseTwoSymb(String line) throws ParseFileException {
        int index = line.indexOf("//");
        int len;
        if (index != -1) {
            len = index;
        } else {
            len = line.length();
        }
        boolean wasChar = false;
        StringBuilder stringBuilder = new StringBuilder();
        Point point = new Point();
        point.x = NEED_LOAD;
        point.y = NEED_LOAD;
        for (int i = 0; i < len; ++i) {
            if (Character.isDigit(line.charAt(i))) {
                stringBuilder.append(line.charAt(i));
            } else {
                if (line.charAt(i) != ' ') {
                    throw new ParseFileException();
                }

                if (stringBuilder.length() != 0) {
                    wasChar = true;
                    if (point.x == NEED_LOAD) {
                        point.x = Integer.parseInt(stringBuilder.toString());
                        if (point.x < 0) throw new ParseFileException();
                    } else {
                        if (point.y == NEED_LOAD) {
                            point.y = Integer.parseInt(stringBuilder.toString());
                            if (point.y < 0) throw new ParseFileException();
                        } else {
                            throw new ParseFileException();
                        }
                    }
                    stringBuilder = new StringBuilder();
                }
            }
        }

        if(point.y == NEED_LOAD && stringBuilder.length() != 0){
            point.y = Integer.parseInt(stringBuilder.toString());
            if (point.y < 0) throw new ParseFileException();
        }
        if ((point.x == NEED_LOAD || point.y == NEED_LOAD) && wasChar) {
            throw new ParseFileException();
        }else {
            if (!wasChar){
                return null;
            } else{
                return point;
            }
        }
    }

    private int parseOneSymb(String line) throws ParseFileException {
        int index = line.indexOf("//");
        int len;
        if (index != -1) {
            len = index;
        } else {
            len = line.length();
        }
        boolean wasChar = false;
        int result = NEED_LOAD;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < len; ++i) {
            if (Character.isDigit(line.charAt(i))) {
                stringBuilder.append(line.charAt(i));
            } else {
                if (line.charAt(i) != ' ') {
                    throw new ParseFileException();
                }
                if (stringBuilder.length() != 0) {
                    wasChar = true;
                    result = Integer.parseInt(stringBuilder.toString());
                    if (result < 0) throw new ParseFileException();
                }
            }
        }

        if(result == NEED_LOAD && stringBuilder.length() != 0){
            result = Integer.parseInt(stringBuilder.toString());
            if (result < 0) throw new ParseFileException();
        }

        if (result == NEED_LOAD && wasChar) {
            throw new ParseFileException();
        } else{
            return result;
        }
    }
}
