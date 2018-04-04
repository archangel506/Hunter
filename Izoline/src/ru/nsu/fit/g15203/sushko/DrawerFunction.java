package ru.nsu.fit.g15203.sushko;

import lombok.Getter;
import lombok.Setter;
import lombok.val;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class DrawerFunction {
    private static final int TWO_CROSSED_BORDERS = 2;
    private static final int THREE_CROSSED_BORDERS = 3;
    private static final int FOUR_CROSSED_BORDERS = 4;
    private static final int NOT_CLICKED = -1;
    private final Color[] funcColors;
    private final Color izolineColor;
    private Function function;
    private Legend legend = new Legend();
    @Getter @Setter private int countLevels;
    @Getter @Setter private boolean isInterpolation = false;
    @Getter @Setter private boolean haveGrid = false;
    @Getter @Setter private boolean haveIzolines = false;
    private double[] criticalValues;
    private double[] points;
    @Getter private double[] legendValues;
    @Getter @Setter private double mouseValue = NOT_CLICKED;


    public DrawerFunction
            (
                    final ConfigField configField,
                    final Color[] funcColors,
                    final Color izolineColor,
                    final int countLevels
            )
    {
        function = new MyFunction(configField);
        this.funcColors = funcColors;
        this.izolineColor = izolineColor;
        this.countLevels = countLevels;
    }

    public void drawImageFunc(final BufferedImage imageFunc, final BufferedImage imageLegend)
    {
        drawImage(imageFunc, function);
        drawGrid(imageFunc);
        drawAllIzolines(imageFunc);
        if(mouseValue != NOT_CLICKED)
        {
            drawIzoline(imageFunc, mouseValue);
        }
        drawLegend(imageLegend);
    }

    private void drawLegend(final BufferedImage bufferedImage)
    {
        legendValues = new double[criticalValues.length - 2];
        System.arraycopy(criticalValues, 1, legendValues, 0, legendValues.length);
        drawImage(bufferedImage, legend);
    }

    private void drawAllIzolines(final BufferedImage bufferedImage)
    {
        if (haveIzolines)
        {
            for (int i = 1; i < criticalValues.length - 1; ++i)
            {
                drawIzoline(bufferedImage, criticalValues[i]);
            }
        }
    }

    private void drawIzoline(final BufferedImage bufferedImage, final double criticalValue)
    {

        val graphics = bufferedImage.getGraphics();
        graphics.setColor(izolineColor);

        val imageWidth = bufferedImage.getWidth();
        val imageHeight = bufferedImage.getHeight();

        val gridWidth = function.getConfigField().getWidth() - 1;
        val gridHeight = function.getConfigField().getHeight() - 1;

        val deltaX = imageWidth / gridWidth;
        val deltaY = imageHeight / gridHeight;

        val isCrossedBorder = new boolean[4];
        val crossingPoints = new int[4];

        for (int y = 0; y < imageHeight; y += deltaY) {
            if (imageHeight - y < deltaY) {
                break;
            }

            for (int x = 0; x < imageWidth; x += deltaX) {
                if (imageWidth - x < deltaX) {
                    break;
                }

                int count = countCrossedBorders(bufferedImage, isCrossedBorder, crossingPoints, criticalValue, x, y);

                switch (count) {
                    case TWO_CROSSED_BORDERS:
                        drawTwoCrossedBorders(isCrossedBorder, crossingPoints, x, y, deltaX, deltaY, graphics, imageWidth, imageHeight);
                        break;
                    case THREE_CROSSED_BORDERS:
                       drawThreeCrossedBorders(isCrossedBorder, crossingPoints, x, y, deltaX, deltaY, bufferedImage, criticalValue);

                    case FOUR_CROSSED_BORDERS:
                        drawFourCrossedBorders(crossingPoints, x, y, deltaX, deltaY, graphics, criticalValue);
                        break;
                    default:
                        break;
                }

            }
        }
    }

    private int countCrossedBorders
            (final BufferedImage bufferedImage,
             final boolean[] isCrossedBorder,
             final int[] crossPoints,
             final double criticalValue,
             final int x,
             final int y
            )
    {
        val imageWidth = bufferedImage.getWidth();
        val imageHeight = bufferedImage.getHeight();

        val configField = function.getConfigField();

        val gridWidth = configField.getWidth() - 1;
        val gridHeight = configField.getHeight() - 1;

        val deltaX = imageWidth / gridWidth;
        val deltaY = imageHeight / gridHeight;

        val domainX = (configField.getB() - configField.getA()) / (imageWidth);
        val domainY = (configField.getD() - configField.getC()) / (imageHeight);

        Arrays.fill(isCrossedBorder, true);
        Arrays.fill(crossPoints, -1);

        val valueTopLeft = function.f(x * domainX, y * domainY);
        val  valueTopRight = function.f((x + deltaX) * domainX, y * domainY);
        val valueDownLeft = function.f(x * domainX, (y + deltaY) * domainY);
        val valueDownRight = function.f((x + deltaX) * domainX, (y + deltaY) * domainY);


        if ((criticalValue < valueTopLeft && criticalValue < valueTopRight) || (criticalValue > valueTopLeft && criticalValue > valueTopRight))
        {
            isCrossedBorder[0] = false;
        } else
        {
            val temp1 = criticalValue - valueTopLeft;
            val temp2 = valueTopRight - valueTopLeft;

            crossPoints[0] = (int) (x + deltaX * temp1 / temp2);
        }

        if ((criticalValue < valueDownLeft && criticalValue < valueDownRight) || (criticalValue > valueDownLeft && criticalValue > valueDownRight))
        {
            isCrossedBorder[1] = false;
        } else
        {
            val domain1 = criticalValue - valueDownLeft;
            val domain2 = valueDownRight - valueDownLeft;

            crossPoints[1] = (int) (x + deltaX * domain1 / domain2);
        }

        if ((criticalValue < valueTopLeft && criticalValue < valueDownLeft) || (criticalValue > valueTopLeft && criticalValue > valueDownLeft))
        {
            isCrossedBorder[2] = false;
        } else
        {
            val domain1 = criticalValue - valueDownLeft;
            val domain2 = valueTopLeft - valueDownLeft;

            crossPoints[2] = (int) (y + deltaY - deltaY * domain1 / domain2);
        }

        if ((criticalValue < valueTopRight && criticalValue < valueDownRight) || (criticalValue > valueTopRight && criticalValue > valueDownRight))
        {
            isCrossedBorder[3] = false;
        } else
        {
            val domain1 = criticalValue - valueDownRight;
            val domain2 = valueTopRight - valueDownRight;

            crossPoints[3] = (int) (y + deltaY - deltaY * domain1 / domain2);
        }

        int count = 0;

        for (val value : isCrossedBorder)
        {
            if (value) ++count;
        }

        return count;
    }

    private void drawFourCrossedBorders
            (
                    final int[] crossPoints,
                    final int x,
                    final int y,
                    final double deltaX,
                    final double deltaY,
                    final Graphics graphics,
                    final double criticalValue
            )
    {
        boolean isCenterBigger = (function.f(x + deltaX / 2, y + deltaY / 2) > criticalValue);

        if (isCenterBigger)
        {
            graphics.drawLine(crossPoints[0], y, (int) Math.round(x + deltaX), crossPoints[3]);
            graphics.drawLine(crossPoints[1], (int) Math.round(y + deltaY),
                    x, crossPoints[2]);
        } else
        {
            graphics.drawLine(crossPoints[0], y, x, crossPoints[2]);
            graphics.drawLine(crossPoints[1], (int) Math.round(y + deltaY),
                    (int) Math.round(x + deltaX), crossPoints[3]);
        }
    }

    private void drawThreeCrossedBorders
            (
                    final boolean[] isCrossedBorder,
                    final int[] crossPoints,
                    final int x,
                    final int y,
                    final int deltaX,
                    final int deltaY,
                    final BufferedImage bufferedImage,
                    final double criticalValue
            )
    {
        val graphics = bufferedImage.getGraphics();

        for (int c = 0; c < 4; c++)
        {
            if (crossPoints[c] == -1) return;
        }


        val epsBorder = new boolean[4];
        val epsPoints = new int[4];
        val eps = 0.002;

        countCrossedBorders(bufferedImage, epsBorder, epsPoints, criticalValue - eps, x, y);

        val points = new Point[3];

        int index = 0;

        if (isCrossedBorder[0])
        {
            if (!epsBorder[0])
            {
                points[2].x = crossPoints[0];
                points[2].y = y;
            }
            else
            {
                points[index].x = crossPoints[0];
                points[index].y = y;
                index++;
            }
        }

        if (isCrossedBorder[1])
        {
            if (!epsBorder[1])
            {
                points[2].x = crossPoints[1];
                points[2].y = Math.round(y + deltaY);
            }
            else
            {
                points[index].x = crossPoints[1];
                points[index].y = Math.round(y + deltaY);
                index++;
            }
        }

        if (isCrossedBorder[2])
        {
            if (!epsBorder[2])
            {
                points[2].x = x;
                points[2].y = crossPoints[2];
            }
            else
            {
                points[index].x = x;
                points[index].y = crossPoints[2];
                index++;
            }
        }

        if (isCrossedBorder[3])
        {
            if (!epsBorder[3])
            {
                points[2].x = Math.round(x + deltaX);
                points[2].y = crossPoints[3];
            }
            else
            {
                points[index].x = Math.round(x + deltaX);
                points[index].y = crossPoints[3];
            }
        }

        graphics.drawLine(points[0].x, points[0].y, points[2].x, points[2].y);
        graphics.drawLine(points[2].x, points[2].y, points[1].x, points[1].y);
    }

    private void drawTwoCrossedBorders
            (
                    final boolean[] isCrossedBorder,
                    final int[] crossPoints,
                    final int x,
                    final int y,
                    final double shiftX,
                    final double shiftY,
                    final Graphics graphics,
                    final int width,
                    final int height
            )
    {
        val points = new Point[2];
        int index = 0;

        if (isCrossedBorder[0])
        {
            points[index].x = crossPoints[0];
            points[index].y = y;

            ++index;
        }

        if (isCrossedBorder[1])
        {
            points[index].x =  crossPoints[1];
            points[index].y = (int)Math.round(y + shiftY);

            ++index;
        }

        if (isCrossedBorder[2])
        {
            points[index].x =  x;
            points[index].y = crossPoints[2];

            index++;
        }

        if (isCrossedBorder[3])
        {
            points[index].x = (int)Math.round(x + shiftX);
            points[index].y = crossPoints[3];
        }

        for (int i = 0; i < points.length; ++i)
        {
            if (points[i].x >= width)
            {
                points[i].x = width - 1;
            }

            if (points[i].y >= height)
            {
                points[i].y = height - 1;
            }
        }
        graphics.drawLine(points[0].x, points[0].y, points[1].x, points[1].y);
    }



    private void drawGrid(final BufferedImage bufferedImage)
    {
        if(!haveGrid)
        {
            return;
        }

        Graphics graphics = bufferedImage.getGraphics();
        graphics.setColor(Color.BLACK);

        val imageWidth = bufferedImage.getWidth();
        val imageHeight = bufferedImage.getHeight();

        val gridWidth = function.getConfigField().getWidth() - 1;
        val gridHeight = function.getConfigField().getHeight() - 1;

        val shiftX = imageWidth / gridWidth;
        val shiftY = imageHeight / gridHeight;

        for (int i = 0; i < imageWidth; i += shiftX)
        {
            if (i > imageWidth - shiftX)
            {
                graphics.drawLine(imageWidth - 1, 0, imageWidth - 1, imageHeight - 1);
            } else
            {
                graphics.drawLine(i, 0, i, imageHeight - 1);
            }
        }

        for (int j = 0; j < imageHeight; j += shiftY)
        {
            if (j > imageHeight - shiftY)
            {
                graphics.drawLine(0, imageHeight - 1, imageWidth - 1, imageHeight - 1);
            } else
            {
                graphics.drawLine(0, j, imageWidth - 1, j);
            }
        }
    }

    private void drawImage(final BufferedImage bufferedImage, final Function function)
    {
        val width = bufferedImage.getWidth();
        val height = bufferedImage.getHeight();
        points = calculateValuePoints(width, height, function);
        calculateCriticalValues(function);
        drawField(bufferedImage);
    }

    private void drawField(final BufferedImage bufferedImage)
    {
        if (isInterpolation)
        {
            drawFieldWithInterpolation(bufferedImage);
        }
        else
        {
            drawFieldWithoutInterpolation(bufferedImage);
        }
    }

    private void drawFieldWithInterpolation(final BufferedImage bufferedImage){
        val width = bufferedImage.getWidth();
        val height = bufferedImage.getHeight();
        for (int y = 1; y < height - 1; y++)
        {
            for (int x = 1; x < width - 1; x++)
            {
                bufferedImage.setRGB(x, y, interpolate(points[y * width + x]));
            }
        }
    }



    private void drawFieldWithoutInterpolation(final BufferedImage bufferedImage){
        val width = bufferedImage.getWidth();
        val height = bufferedImage.getHeight();
        for (int x = 0; x < height; ++x)
        {
            for (int y = 0; y < width; ++y)
            {
                if (points[x * width + y] > criticalValues[criticalValues.length - 1])
                {
                    bufferedImage.setRGB(y, x, funcColors[criticalValues.length - 2].getRGB());
                } else
                {
                    findValue(bufferedImage, x, y, width);
                }
            }
        }
    }

    private int interpolate(final double value)
    {
        val index = findIndexForValues(value);
        return calculatePixValue(index, value);
    }

    private int findIndexForValues(final double value){
        for (int i = 1; i < criticalValues.length; i++)
        {
            if (value < criticalValues[i])
            {
                return i;
            }
        }
        return criticalValues.length - 1;
    }

    private int calculatePixValue(final int indexCritical, final double value)
    {
        val colorPrev = (indexCritical != 0)
                ? (funcColors[indexCritical - 1].getRGB())
                : (funcColors[funcColors.length - 1].getRGB());
        val colorNext = (indexCritical != criticalValues.length - 1)
                ? (funcColors[indexCritical].getRGB())
                : (funcColors[indexCritical - 1].getRGB());

        val valuePrev = (indexCritical != 0)
                ? (criticalValues[indexCritical - 1])
                : (criticalValues[criticalValues.length - 1]);
        val valueNext = criticalValues[indexCritical];

        int pixValue = 0;
        for (int i = 0; i < 3; i++)
        {
            val colorComponent = (((colorPrev >> i * 8) & 0xFF) * (valueNext - value) / (valueNext - valuePrev) +
                    ((colorNext >> i * 8) & 0xFF) * (value - valuePrev) / (valueNext - valuePrev));
            pixValue |= ((int) colorComponent << i * 8);
        }

        return pixValue;
    }


    private double[] calculateValuePoints(final int width, final int height, final Function function)
    {
        val configField = function.getConfigField();
        val points = new double[width * height];

        val domainX = (configField.getB() - configField.getA()) / width;
        val domainY = (configField.getD() - configField.getC()) / height;

        for (int x = 0; x < height; ++x) {
            for (int y = 0; y < width; ++y) {
                points[x * width + y] = function.f(x * domainX, y * domainY);
            }
        }

        return points;
    }

    private void calculateCriticalValues(final Function function)
    {
        val configField = function.getConfigField();
        criticalValues = new double[countLevels];

        val offset = (configField.getMax() - configField.getMin()) / countLevels;
        for (int i = 0; i < countLevels; i++) {
            criticalValues[i] = configField.getMin() + i * offset;
        }
    }

    private void findValue(final BufferedImage bufferedImage, final int x, final int y, final int width)
    {
        for (int i = 1; i < criticalValues.length; i++)
        {
            if (points[x * width + y] < criticalValues[i])
            {
                bufferedImage.setRGB(y, x, funcColors[i - 1].getRGB());
                break;
            }
        }
    }


}
