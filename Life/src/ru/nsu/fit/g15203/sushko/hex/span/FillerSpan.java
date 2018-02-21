package ru.nsu.fit.g15203.sushko.hex.span;

import ru.nsu.fit.g15203.sushko.hex.FillerHex;
import ru.nsu.fit.g15203.sushko.hex.HexField;

import java.awt.image.BufferedImage;
import java.util.Stack;

public class FillerSpan implements FillerHex {

    @Override
    public void fillHex(int x, int y, int color, BufferedImage bufferedImage) {
        int currentColor = bufferedImage.getRGB(x,y);
        if(currentColor == HexField.COLOR_LINE || currentColor == color
                || !inHex(x,y, bufferedImage)){
            return;
        }
        Stack<Span> stack = new Stack<>();
        Span span = createSpan(x,y,  bufferedImage);
        stack.push(span);

        while(!stack.empty()){
            boolean fillTop = false;
            boolean fillDown = false;
            span = stack.pop();
            int startX = span.getStart();
            int endX = span.getEnd();
            int line = span.getLine();

            for(int i = startX; i <= endX; ++i){
                bufferedImage.setRGB(i, line, color);

                if(bufferedImage.getRGB(i, line + 1) != HexField.COLOR_LINE && bufferedImage.getRGB(i, line + 1) != color ){
                    if(!fillTop){
                        stack.push(createSpan(i, line + 1, bufferedImage));
                        fillTop = true;
                    }
                } else{
                    fillTop = false;
                }

                if(bufferedImage.getRGB(i, line - 1) != HexField.COLOR_LINE && bufferedImage.getRGB(i, line - 1) != color ){
                    if(!fillDown){
                        stack.push(createSpan(i, line - 1,  bufferedImage));
                        fillDown = true;
                    }
                } else {
                    fillDown = false;
                }

            }
        }
    }

    private boolean inHex(int x, int y, BufferedImage bufferedImage){
        boolean haveLineLeft = false;
        boolean haveLineRight = false;
        boolean haveLineTop = false;
        boolean haveLineDown = false;
        for(int left = x; left >= 0;  --left){
            if(bufferedImage.getRGB(left, y) == HexField.COLOR_LINE){
                haveLineLeft = true;
                break;
            }
        }
        for(int right = x; right < bufferedImage.getWidth();  ++right){
            if(bufferedImage.getRGB(right, y) == HexField.COLOR_LINE){
                haveLineRight = true;
                break;
            }
        }
        for(int top = y; top >= 0;  --top){
            if(bufferedImage.getRGB(x, top) == HexField.COLOR_LINE){
                haveLineTop = true;
                break;
            }
        }
        for(int down = y; down < bufferedImage.getHeight();  ++down){
            if(bufferedImage.getRGB(x, down) == HexField.COLOR_LINE){
                haveLineDown = true;
                break;
            }
        }

        return haveLineDown && haveLineTop && haveLineLeft && haveLineRight;

    }

    private Span createSpan(int x, int line, BufferedImage bufferedImage){
        int firstX = x;

        while(bufferedImage.getRGB(x,line) != HexField.COLOR_LINE){
            --x;
        }
        int start = x + 1;
        x = firstX;

        while(bufferedImage.getRGB(x,line) != HexField.COLOR_LINE){
            ++x;
        }
        int end = x - 1;

        return new Span(start, end, line);
    }

    private class Span{
        private int start;
        private int end;
        private int line;

        public Span(int start, int end, int line){
            this.start = start;
            this.end = end;
            this.line = line;
        }

        public int getStart(){
            return start;
        }

        public int getEnd(){
            return end;
        }

        public int getLine(){
            return line;
        }
    }
}
