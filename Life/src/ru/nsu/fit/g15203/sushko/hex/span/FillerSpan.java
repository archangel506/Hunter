package ru.nsu.fit.g15203.sushko.hex.span;

import ru.nsu.fit.g15203.sushko.hex.FillerHex;
import ru.nsu.fit.g15203.sushko.hex.HexField;

import java.awt.image.BufferedImage;
import java.util.Stack;

public class FillerSpan implements FillerHex {

    @Override
    public void fillHex(int x, int y, int color, BufferedImage bufferedImage) {
        int currentColor = bufferedImage.getRGB(x,y);
        if(currentColor == HexField.COLOR_LINE){
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
