package com.wickedknight.fiona.skin;

import java.awt.Image;
import java.io.PrintStream;
import java.util.Hashtable;

public class Skin
{
  private static final String main = "main.bmp";
  private static final String buttons = "cbuttons.bmp";
  private static final String titleBar = "titlebar.bmp";
  private static final String numbers = "numbers.bmp";
  private static final String numbers_ex = "nums_ex.bmp";
  private static final String stereoMode = "monoster.bmp";
  private static final String volume = "volume.bmp";
  private static final String playPause = "playpaus.bmp";
  private static final String playlist = "pledit.bmp";
  private static final String shuffleRepeat = "shufrep.bmp";
  private static final String positionBar = "posbar.bmp";
  private static final String balance = "balance.bmp";
  private static final String plinfo = "pledit.txt";
  private SkinLoader skl;
  private Hashtable images;

  private Skin()
  {
  }

  public Skin(String paramString)
  {
    this.skl = new SkinLoader(paramString);
    try
    {
      this.skl.loadImages();
      this.images = this.skl.toHash();
    }
    catch (Exception localException)
    {
      System.out.println(localException);
    }
    this.skl = null;
  }

  public Image getMainPanel()
  {
    return getImage("main.bmp");
  }

  public Image getButtonPanel()
  {
    return getImage("cbuttons.bmp");
  }

  public Image getTitlePanel()
  {
    return getImage("titlebar.bmp");
  }

  public Image getNumberPanel()
  {
    return getImage("numbers.bmp");
  }

  public Image getNumbersSmallPanel()
  {
    return getImage("nums_ex.bmp");
  }

  public Image getPositionBarPanel()
  {
    return getImage("posbar.bmp");
  }

  public Image getStereoPanel()
  {
    return getImage("monoster.bmp");
  }

  public Image getVolumePanel()
  {
    return getImage("volume.bmp");
  }

  public Image getPlayPanel()
  {
    return getImage("playpaus.bmp");
  }

  public Image getShuffleRepeatPanel()
  {
    return getImage("shufrep.bmp");
  }

  public Image getEqPlPanel()
  {
    return getImage("shufrep.bmp");
  }

  public Image getPlaylistPanel()
  {
    return getImage("pledit.bmp");
  }

  public Image getBalance()
  {
    return getImage("balance.bmp");
  }

  public String getPlaylistInfo()
  {
    return (String)getContent("pledit.txt");
  }

  private Image getImage(String paramString)
  {
    if (!this.images.containsKey(paramString))
      System.out.println("key " + paramString + " does not exist");
    return (Image)this.images.get(paramString);
  }

  private Object getContent(String paramString)
  {
    return this.images.get(paramString);
  }
}

/* Location:           C:\Users\jabail\Desktop\work_folder\jad\fiona.jar
 * Qualified Name:     com.wickedknight.fiona.skin.Skin
 * JD-Core Version:    0.6.2
 */