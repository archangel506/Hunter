package ru.nsu.fit.g15203.sushko.hex.bresenhem;

import ru.nsu.fit.g15203.sushko.hex.DrawerLine;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BresDrawLine implements DrawerLine {
    @Override
    public void drawLine(int xstart, int ystart, int xend, int yend, BufferedImage bufferedImage) {
        int x, y, dx, dy, incx, incy, pdx, pdy, es, el, err;

        dx = xend - xstart;
        dy = yend - ystart;

        incx = sign(dx);
        incy = sign(dy);

        dx = Math.abs(dx);
        dy = Math.abs(dy);

        if (dx > dy)
        {
            pdx = incx;
            pdy = 0;
            es = dy;
            el = dx;
        } else
        {
            pdx = 0;
            pdy = incy;
            es = dx;
            el = dy;
        }

        x = xstart;
        y = ystart;
        err = el / 2;
        bufferedImage.setRGB(x, y, Color.RED.getRGB());

        for (int t = 0; t < el; t++)
        {
            err -= es;
            if (err < 0) {
                err += el;
                x += incx;
                y += incy;
            } else {
                x += pdx;
                y += pdy;
            }

            try {

                bufferedImage.setRGB(x, y, Color.RED.getRGB());
            }catch (ArrayIndexOutOfBoundsException e){
                e.printStackTrace();
            }



        }
    }

    private int sign(int x) {
        return (x > 0) ? 1 : (x < 0) ? -1 : 0;
    }
}
