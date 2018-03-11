package ru.nsu.fit.g15203.sushko.models;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class ImageBmp {
    private BitMapFileHeader fileHeader;
    private BitMapInfo imageHeader;
    private BodyData pixelData;

    public static class ImageRGB {
        public int red;
        public int green;
        public int blue;

        public ImageRGB(){}

        public ImageRGB(int r, int g, int b) {
            red = r;
            green = g;
            blue = b;
        }
    }

    protected class BitMapFileHeader {
        private short bfType;
        private int bfSize;
        private short bfReserved1;
        private short bfReserved2;
        private int bfOffBits;

        public BitMapFileHeader(){}

        public BitMapFileHeader(BitMapFileHeader header) {
            this.bfOffBits = header.bfOffBits;
            this.bfReserved2 = header.bfReserved2;
            this.bfReserved1 = header.bfReserved1;
            this.bfSize= header.bfSize;
            this.bfType = header.bfType;
        }

        public BitMapFileHeader(InputStream in) throws IOException {
            read(in);
        }

        public void write(OutputStream out) throws IOException {
            out.write(invertBytes(shortToBytes(bfType)));
            out.write(invertBytes(intToBytes(bfSize)));
            out.write(invertBytes(shortToBytes(bfReserved1)));
            out.write(invertBytes(shortToBytes(bfReserved2)));
            out.write(invertBytes(intToBytes(bfOffBits)));
        }

        public void read(InputStream in) throws IOException {
            byte[] byte2 = new byte[2];
            byte[] byte4 = new byte[4];

            if(-1 == in.read(byte2))
                throw new IOException("in.read() = -1");
            bfType = bytesToShort(invertBytes(byte2));

            if(-1 == in.read(byte4))
                throw new IOException("in.read() = -1");
            bfSize = bytesToInt(invertBytes(byte4));

            if(-1 == in.read(byte2))
                throw new IOException("in.read() = -1");
            bfReserved1 = bytesToShort(invertBytes(byte2));

            if(-1 == in.read(byte2))
                throw new IOException("in.read() = -1");
            bfReserved2 = bytesToShort(invertBytes(byte2));

            if(-1 == in.read(byte4))
                throw new IOException("in.read() = -1");
            bfOffBits = bytesToInt(invertBytes(byte4));
        }
    }

    protected class BitMapInfo {
        private int biSize;
        private int biWidth;
        private int biHeight;

        private short biPlanes;
        private short biBitCount;

        private int biCompression;
        private int biSizeImage;
        private int biXPelsPerMeter;
        private int biYPelsPerMeter;
        private int biClrUsed;
        private int biClrImportant;

        public BitMapInfo() {}

        public BitMapInfo(BitMapInfo header) {
            this.biSize = header.biSize;
            this.biWidth = header.biWidth;
            this.biHeight = header.biHeight;

            this.biPlanes = header.biPlanes;
            this.biBitCount = header.biBitCount;

            this.biCompression = header.biCompression;
            this.biSizeImage = header.biSizeImage;
            this.biXPelsPerMeter = header.biXPelsPerMeter;
            this.biYPelsPerMeter = header.biYPelsPerMeter;
            this.biClrUsed = header.biClrUsed;
            this.biClrImportant = header.biClrImportant;

        }

        public BitMapInfo(InputStream in) throws IOException {
            read(in);
        }

        public void write(OutputStream out) throws IOException {
            out.write(invertBytes(intToBytes(biSize)));
            out.write(invertBytes(intToBytes(biWidth)));
            out.write(invertBytes(intToBytes(biHeight)));

            out.write(invertBytes(shortToBytes(biPlanes)));
            out.write(invertBytes(shortToBytes(biBitCount)));

            out.write(invertBytes(intToBytes(biCompression)));
            out.write(invertBytes(intToBytes(biSizeImage)));
            out.write(invertBytes(intToBytes(biXPelsPerMeter)));
            out.write(invertBytes(intToBytes(biYPelsPerMeter)));
            out.write(invertBytes(intToBytes(biClrUsed)));
            out.write(invertBytes(intToBytes(biClrImportant)));
        }

        public void read(InputStream in) throws IOException{
            byte[] byte2 = new byte[2];
            byte[] byte4 = new byte[4];
            in.read(byte4);
            biSize = bytesToInt(invertBytes(byte4));

            in.read(byte4);
            biWidth = bytesToInt(invertBytes(byte4));

            in.read(byte4);
            biHeight = bytesToInt(invertBytes(byte4));

            in.read(byte2);
            biPlanes = bytesToShort(invertBytes(byte2));

            in.read(byte2);
            biBitCount = bytesToShort(invertBytes(byte2));

            if(biBitCount != 24) {
                throw new IOException("only 24-bit bmp support, " + biBitCount + "-bit");
            }

            in.read(byte4);
            biCompression = bytesToInt(invertBytes(byte4));

            if(biCompression != 0) {
                throw new IOException("compression bmp image");
            }

            in.read(byte4);
            biSizeImage = bytesToInt(invertBytes(byte4));

            in.read(byte4);
            biXPelsPerMeter = bytesToInt(invertBytes(byte4));

            in.read(byte4);
            biYPelsPerMeter = bytesToInt(invertBytes(byte4));

            in.read(byte4);
            biClrUsed = bytesToInt(invertBytes(byte4));

            in.read(byte4);
            biClrImportant = bytesToInt(invertBytes(byte4));
        }
    }

    protected class BodyData {
        private ImageRGB bitmap[][];

        public BodyData(){}

        public BodyData(BodyData data) {
            int n = data.bitmap.length;
            int m = data.bitmap[0].length;

            bitmap = new ImageRGB[n][m];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    bitmap[i][j] = new ImageRGB(data.bitmap[i][j].red, data.bitmap[i][j].green, data.bitmap[i][j].blue);
                }
            }
        }

        public BodyData(InputStream in) throws IOException {
            read(in);
        }

        public void write(OutputStream out) throws IOException {
            int width = imageHeader.biWidth;
            int height = imageHeader.biHeight;
            int nullBytePad = (4 - (width * 3) % 4) % 4;

            if (height < 0) {
                height = -height;
                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        out.write(bitmap[i][j].blue);
                        out.write(bitmap[i][j].green);
                        out.write(bitmap[i][j].red);
                    }
                    for (int j = 0; j < nullBytePad; j++) {
                        out.write(0);
                    }
                }
            }
            else {
                for (int i = height - 1; i >= 0; i--) {
                    for (int j = 0; j < width; j++) {
                        out.write(bitmap[i][j].blue);
                        out.write(bitmap[i][j].green);
                        out.write(bitmap[i][j].red);
                    }
                    for (int j = 0; j < nullBytePad; j++) {
                        out.write(0);
                    }
                }
            }
        }

        public void read (InputStream in) throws IOException {
            int width = imageHeader.biWidth;
            int height = imageHeader.biHeight;
            int nullBytePad = (4 - (width * 3) % 4) % 4;
            bitmap = new ImageRGB[height][width];

            if (height < 0) {
                height = -height;
                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        bitmap[i][j] = new ImageRGB();
                        int b = in.read();
                        if(b == -1)
                            throw new IOException("in.read() = -1");
                        bitmap[i][j].blue = b;

                        b = in.read();
                        if(b == -1)
                            throw new IOException("in.read() = -1");
                        bitmap[i][j].green = b;

                        b = in.read();
                        if(b == -1)
                            throw new IOException("in.read() = -1");

                        bitmap[i][j].red = b;
                    }
                    for(int j = 0; j < nullBytePad; j++) {
                        in.read();
                    }
                }
            }
            else {
                for (int i = height - 1; i >= 0; i--) {
                    for (int j = 0; j < width; j++) {
                        bitmap[i][j] = new ImageRGB();

                        int b = in.read();
                        if(b == -1) {
                            throw new IOException("in.read() = -1");
                        }
                        bitmap[i][j].blue = b;

                        b = in.read();
                        if(b == -1) {
                            throw new IOException("in.read() = -1");
                        }
                        bitmap[i][j].green = b;

                        b = in.read();
                        if(b == -1) {
                            throw new IOException("in.read() = -1");
                        }

                        bitmap[i][j].red = b;
                    }
                    for(int j = 0; j < nullBytePad; j++) {
                        in.read();
                    }
                }
            }
        }
    }

    public ImageBmp() {
        fileHeader = new BitMapFileHeader();
        imageHeader = new BitMapInfo();
        pixelData = new BodyData();
    }

    public ImageBmp(ImageBmp image) {
        fileHeader = new BitMapFileHeader(image.fileHeader);
        imageHeader = new BitMapInfo(image.imageHeader);
        pixelData = new BodyData(image.pixelData);
    }

    public ImageBmp(InputStream in) throws IOException {
        fileHeader = new BitMapFileHeader(in);
        imageHeader = new BitMapInfo(in);
        pixelData = new BodyData(in);
    }

    public void read(InputStream in) throws IOException {
        fileHeader.read(in);
        imageHeader.read(in);
        pixelData.read(in);
    }

    public void write(OutputStream out) throws IOException {
        fileHeader.write(out);
        imageHeader.write(out);
        pixelData.write(out);
    }

    public ImageRGB[][] getBitMap() {
        return  pixelData.bitmap;
    }

    public ImageBmp copyFragment(int x, int y, int width, int height) {
        int fileHeaderSize = 14;
        int imageHeaderSize = 40;

        ImageBmp image = new ImageBmp(this);
        image.imageHeader.biHeight = height;
        image.imageHeader.biWidth = width;
        image.fileHeader.bfSize = fileHeaderSize + imageHeaderSize + height * width;

        ImageRGB[][] bitmap = new ImageRGB[height][width];
        if (height > pixelData.bitmap.length) {
            height = pixelData.bitmap.length;
        }
        if (width > pixelData.bitmap[0].length) {
            width = pixelData.bitmap[0].length;
        }

        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                bitmap[j][i] = pixelData.bitmap[y + j][x + i];
            }
        }
        image.pixelData.bitmap = bitmap;

        return image;
    }

    public int getWidth() {
        return imageHeader.biWidth;
    }

    public int getHeight() {
        return imageHeader.biHeight;
    }

    private static byte[] shortToBytes(short value) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.putShort(value);
        buffer.flip();

        return buffer.array();
    }

    private static byte[] intToBytes(int value) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(value);
        buffer.flip();

        return buffer.array();
    }

    private static short bytesToShort(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);

        return byteBuffer.getShort();
    }

    private static int bytesToInt(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);

        return byteBuffer.getInt();
    }

    //invert endianness
    private static byte[] invertBytes(byte[] bytes) {
        int length = bytes.length;
        byte b;

        for (int i = 0; i < length / 2; i++) {
            b = bytes[length - 1 - i];
            bytes[length - 1 - i] = bytes[i];
            bytes[i] = b;
        }
        return bytes;
    }
}
