package com.wickedknight.image;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.MemoryImageSource;
import java.io.IOException;
import java.io.InputStream;

public class BMP
{
  private InputStream is;
  private int curPos = 0;
  private int bitmapOffset;
  private int width;
  private int height;
  private short bitsPerPixel;
  private int compression;
  private int actualSizeOfBitmap;
  private int scanLineSize;
  private int actualColorsUsed;
  private byte[] r;
  private byte[] g;
  private byte[] b;
  private int noOfEntries;
  private byte[] byteData;
  private int[] intData;

  public Image getBMPImage(InputStream paramInputStream)
    throws Exception
  {
    read(paramInputStream);
    return Toolkit.getDefaultToolkit().createImage(getImageSource());
  }

  private int readInt()
    throws IOException
  {
    int i = this.is.read();
    int j = this.is.read();
    int k = this.is.read();
    int m = this.is.read();
    this.curPos += 4;
    return (m << 24) + (k << 16) + (j << 8) + (i << 0);
  }

  private short readShort()
    throws IOException
  {
    int i = this.is.read();
    int j = this.is.read();
    this.curPos += 4;
    return (short)((j << 8) + i);
  }

  void getFileHeader()
    throws IOException, Exception
  {
    int i = 19778;
    int k = 0;
    int m = 0;
    i = readShort();
    if (i != 19778)
      throw new Exception("Not a BMP file");
    int j = readInt();
    k = readShort();
    m = readShort();
    this.bitmapOffset = readInt();
  }

  void getBitmapHeader()
    throws IOException
  {
    int i = readInt();
    this.width = readInt();
    this.height = readInt();
    int j = readShort();
    this.bitsPerPixel = readShort();
    this.compression = readInt();
    int k = readInt();
    int m = readInt();
    int n = readInt();
    int i1 = readInt();
    int i2 = readInt();
    int i3 = this.height < 0 ? 1 : 0;
    int i4 = this.width * this.height;
    this.scanLineSize = ((this.width * this.bitsPerPixel + 31) / 32 * 4);
    if (k != 0)
      this.actualSizeOfBitmap = k;
    else
      this.actualSizeOfBitmap = (this.scanLineSize * this.height);
    if (i1 != 0)
      this.actualColorsUsed = i1;
    else if (this.bitsPerPixel < 16)
      this.actualColorsUsed = (1 << this.bitsPerPixel);
    else
      this.actualColorsUsed = 0;
  }

  void getPalette()
    throws IOException
  {
    this.noOfEntries = this.actualColorsUsed;
    if (this.noOfEntries > 0)
    {
      this.r = new byte[this.noOfEntries];
      this.g = new byte[this.noOfEntries];
      this.b = new byte[this.noOfEntries];
      for (int j = 0; j < this.noOfEntries; j++)
      {
        this.b[j] = ((byte)this.is.read());
        this.g[j] = ((byte)this.is.read());
        this.r[j] = ((byte)this.is.read());
        int i = this.is.read();
        this.curPos += 4;
      }
    }
  }

  void unpack(byte[] paramArrayOfByte, int paramInt1, int[] paramArrayOfInt, int paramInt2, int paramInt3)
  {
    int i = paramInt2;
    int j = paramInt1;
    int k = 255;
    for (int m = 0; m < paramInt3; m++)
    {
      int n = paramArrayOfByte[(j++)] & k;
      int i1 = (paramArrayOfByte[(j++)] & k) << 8;
      int i2 = (paramArrayOfByte[(j++)] & k) << 16;
      paramArrayOfInt[i] = (0xFF000000 | n | i1 | i2);
      i++;
    }
  }

  void unpack(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3, int paramInt4)
    throws Exception
  {
    int i = paramInt3;
    int j = paramInt1;
    int k;
    int m;
    switch (paramInt2)
    {
    case 1:
      k = 1;
      m = 8;
      break;
    case 4:
      k = 15;
      m = 2;
      break;
    case 8:
      k = -1;
      m = 1;
      break;
    default:
      throw new Exception("Unsupported bits-per-pixel value");
    }
    int n = 0;
    while (true)
    {
      int i1 = 8 - paramInt2;
      for (int i2 = 0; i2 < m; i2++)
      {
        int i3 = paramArrayOfByte1[j];
        i3 = (byte)(i3 >> i1);
        paramArrayOfByte2[i] = ((byte)(i3 & k));
        i++;
        n++;
        if (n == paramInt4)
          return;
        i1 -= paramInt2;
      }
      j++;
    }
  }

  void getPixelData()
    throws IOException, Exception
  {
    long l = this.bitmapOffset - this.curPos;
    if (l > 0L)
    {
      this.is.skip(l);
      this.curPos = ((int)(this.curPos + l));
    }
    int i = this.scanLineSize;
    if (this.bitsPerPixel > 8)
      this.intData = new int[this.width * this.height];
    else
      this.byteData = new byte[this.width * this.height];
    byte[] arrayOfByte = new byte[this.actualSizeOfBitmap];
    int j = 0;
    int k = (this.height - 1) * this.width;
    for (int m = this.height - 1; m >= 0; m--)
    {
      int n = this.is.read(arrayOfByte, j, i);
      if (n < i)
        throw new Exception("Scan line ended prematurely after " + n + " bytes");
      if (this.bitsPerPixel > 8)
        unpack(arrayOfByte, j, this.intData, k, this.width);
      else
        unpack(arrayOfByte, j, this.bitsPerPixel, this.byteData, k, this.width);
      j += i;
      k -= this.width;
    }
  }

  public void read(InputStream paramInputStream)
    throws IOException, Exception
  {
    this.is = paramInputStream;
    getFileHeader();
    getBitmapHeader();
    if (this.compression != 0)
      throw new Exception(" BMP Compression not supported");
    getPalette();
    getPixelData();
  }

  public MemoryImageSource getImageSource()
  {
    Object localObject;
    if (this.noOfEntries > 0)
      localObject = new IndexColorModel(this.bitsPerPixel, this.noOfEntries, this.r, this.g, this.b);
    else
      localObject = ColorModel.getRGBdefault();
    MemoryImageSource localMemoryImageSource;
    if (this.bitsPerPixel > 8)
      localMemoryImageSource = new MemoryImageSource(this.width, this.height, (ColorModel)localObject, this.intData, 0, this.width);
    else
      localMemoryImageSource = new MemoryImageSource(this.width, this.height, (ColorModel)localObject, this.byteData, 0, this.width);
    return localMemoryImageSource;
  }
}

/* Location:           C:\Users\jabail\Desktop\work_folder\jad\fiona.jar
 * Qualified Name:     com.wickedknight.image.BMP
 * JD-Core Version:    0.6.2
 */