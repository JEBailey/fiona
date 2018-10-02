package com.wickedknight.fiona.skin;

import com.wickedknight.image.BMP;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.Hashtable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class SkinLoader
{
  private Hashtable _images = null;
  private ZipInputStream _zis = null;

  public SkinLoader(String paramString)
  {
    try
    {
      if (paramString.toLowerCase().startsWith("http"))
        this._zis = new ZipInputStream(new URL(paramString).openStream());
      else
        this._zis = new ZipInputStream(new FileInputStream(paramString));
    }
    catch (Exception localException)
    {
      ClassLoader localClassLoader = getClass().getClassLoader();
      InputStream localInputStream = localClassLoader.getResourceAsStream("skins/metrix.wsz");
      if (localInputStream != null)
        this._zis = new ZipInputStream(localInputStream);
    }
  }

  public SkinLoader(InputStream paramInputStream)
  {
    this._zis = new ZipInputStream(paramInputStream);
  }

  public boolean loadImages()
    throws Exception
  {
    for (ZipEntry localZipEntry = this._zis.getNextEntry(); localZipEntry != null; localZipEntry = this._zis.getNextEntry())
    {
      String str = localZipEntry.getName().toLowerCase();
      int i = str.lastIndexOf("/");
      if (i != -1)
        str = str.substring(i + 1);
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      byte[] arrayOfByte = new byte[256];
      while (true)
      {
        int j = this._zis.read(arrayOfByte);
        if (j == -1)
          break;
        localByteArrayOutputStream.write(arrayOfByte, 0, j);
      }
      localByteArrayOutputStream.close();
      if (str.matches(".*[bmp|BMP]"))
      {
        BMP localBMP = new BMP();
        this._images.put(str, localBMP.getBMPImage(new ByteArrayInputStream(localByteArrayOutputStream.toByteArray())));
      }
      else if (str.matches(".*txt"))
      {
        this._images.put(str, new String(localByteArrayOutputStream.toByteArray()));
      }
    }
    this._zis.close();
    this._zis = null;
    return true;
  }

  public Image getImage(String paramString)
  {
    if (!this._images.containsKey(paramString))
      System.out.println("key " + paramString + " does not exist");
    return (Image)this._images.get(paramString);
  }

  public Object getContent(String paramString)
  {
    return this._images.get(paramString);
  }

  public Hashtable toHash()
  {
    return this._images;
  }
}

/* Location:           C:\Users\jabail\Desktop\work_folder\jad\fiona.jar
 * Qualified Name:     com.wickedknight.fiona.skin.SkinLoader
 * JD-Core Version:    0.6.2
 */