package com.wickedknight.fiona.skin.components.playlist;

import com.wickedknight.fiona.aspect.Playlist;
import com.wickedknight.fiona.aspect.PlaylistItem;
import com.wickedknight.fiona.event.FionaEvent;
import com.wickedknight.fiona.skin.Skin;
import com.wickedknight.fiona.skin.components.ActiveDraggable;
import com.wickedknight.geom.Rectangle;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.ByteArrayInputStream;
import java.util.Properties;

public class PLE extends ActiveDraggable
{
  private static final int UPPER_LEFT = 0;
  private static final int UPPER_MIDDLE = 1;
  private static final int UPPER_RIGHT = 2;
  private static final int MIDDLE_LEFT = 3;
  private static final int MIDDLE_RIGHT = 4;
  private static final int BOTTOM_LEFT = 5;
  private static final int BOTTOM_MIDDLE = 6;
  private static final int BOTTOM_RIGHT = 7;
  private int width = 9;
  private int height = 2;
  private Color bgcolor;
  private Color normal;
  private Color current;
  private Color selbg;
  private Font font;
  private String fname;
  private static final String DEFAULT_FONT = "Serif";
  private int startNumber = 1;
  public static final Rectangle buttonAreaR1 = new Rectangle(14, 86, 22, 18);
  private static final Rectangle[] area = { new Rectangle(14, 20, 240, 14), new Rectangle(14, 33, 240, 14), new Rectangle(14, 46, 240, 14), new Rectangle(14, 59, 240, 14) };
  private static final Rectangle[] source = { new Rectangle(0, 0, 25, 20), new Rectangle(127, 0, 25, 20), new Rectangle(153, 0, 25, 20), new Rectangle(0, 42, 25, 29), new Rectangle(26, 42, 25, 29), new Rectangle(0, 72, 125, 38), new Rectangle(0, 42, 25, 38), new Rectangle(126, 72, 150, 38) };
  private static final Rectangle[][] unshaded = { { new Rectangle(0, 149, 22, 18), new Rectangle(0, 130, 22, 18), new Rectangle(0, 111, 22, 18) }, { new Rectangle(54, 149, 22, 18), new Rectangle(54, 130, 22, 18), new Rectangle(54, 111, 22, 18), new Rectangle(54, 168, 22, 18) }, { new Rectangle(104, 149, 22, 18), new Rectangle(104, 130, 22, 18), new Rectangle(104, 111, 22, 18) }, { new Rectangle(154, 149, 22, 18), new Rectangle(154, 130, 22, 18), new Rectangle(154, 111, 22, 18) }, { new Rectangle(204, 149, 22, 18), new Rectangle(204, 130, 22, 18), new Rectangle(204, 111, 22, 18) } };
  private static final Rectangle[][] shaded = { { new Rectangle(23, 149, 22, 18), new Rectangle(23, 130, 22, 18), new Rectangle(23, 111, 22, 18) }, { new Rectangle(77, 149, 22, 18), new Rectangle(77, 130, 22, 18), new Rectangle(77, 111, 22, 18), new Rectangle(77, 168, 22, 18) }, { new Rectangle(127, 149, 22, 18), new Rectangle(127, 130, 22, 18), new Rectangle(127, 111, 22, 18) }, { new Rectangle(177, 149, 22, 18), new Rectangle(177, 130, 22, 18), new Rectangle(177, 111, 22, 18) }, { new Rectangle(227, 149, 22, 18), new Rectangle(227, 130, 22, 18), new Rectangle(227, 111, 22, 18) } };
  private static final Rectangle[] bar = { new Rectangle(48, 111, 3, 54), new Rectangle(100, 111, 3, 72), new Rectangle(150, 111, 3, 54), new Rectangle(200, 111, 3, 54), new Rectangle(250, 111, 3, 54) };
  private static final Rectangle[] barArea = { new Rectangle(11, 50, 3, 54), new Rectangle(40, 32, 3, 72), new Rectangle(69, 50, 3, 54), new Rectangle(98, 50, 3, 54), new Rectangle(228, 50, 3, 54) };
  private int[] events = { 2011, 2003, 2003, 2003, 2003, 2003, 2003, 2003, 2003, 2003, 2003, 2003, 2003, 2003, 2003 };
  private static final int NONE_SELECTED = -1;
  private static final int SUB = 1;
  private int column;
  private int level;
  public int[] eventValue = { 0, 3, 7, 10, 13 };
  private static final Rectangle[][] buttonArea = { {buttonAreaR1 , new Rectangle(14, 68, 22, 18), new Rectangle(14, 50, 22, 18) }, { new Rectangle(43, 86, 22, 18), new Rectangle(43, 68, 22, 18), new Rectangle(43, 50, 22, 18), new Rectangle(43, 32, 22, 18) }, { new Rectangle(72, 86, 22, 18), new Rectangle(72, 68, 22, 18), new Rectangle(72, 50, 22, 18) }, { new Rectangle(101, 86, 22, 18), new Rectangle(101, 68, 22, 18), new Rectangle(101, 50, 22, 18) }, { new Rectangle(231, 86, 22, 18), new Rectangle(231, 68, 22, 18), new Rectangle(231, 50, 22, 18) } };
  private static final Rectangle buttonboundary = new Rectangle(11, 83, 244, 23);
  private static final Rectangle listboundary = new Rectangle(12, 20, 240, 56);
  Rectangle[] destpanel = { new Rectangle(0, 0, 25, 20), new Rectangle(25, 0, 25, 20), new Rectangle(0, 20, 25, 29), new Rectangle(0, 0, 125, 38), new Rectangle(0, 0, 150, 38) };

  public PLE(Skin paramSkin)
  {
    this.panel = paramSkin.getPlaylistPanel();
    setPleditInfo(paramSkin);
    this.column = -1;
    this.font = new Font(this.fname, 1, 10);
    this.boundary = new Rectangle(0, 0, paramSkin.getMainPanel().getWidth(null), paramSkin.getMainPanel().getHeight(null));
  }

  public int getDeltaX()
  {
    return 0;
  }

  public int getDeltaY()
  {
    return 0;
  }

  public void paint(Graphics2D paramGraphics2D)
  {
    if (this.visible)
    {
      paintMain(paramGraphics2D);
      paintList(paramGraphics2D);
      paintButtons(paramGraphics2D);
    }
  }

  private void paintMain(Graphics2D paramGraphics2D)
  {
    paramGraphics2D.setColor(this.bgcolor);
    paramGraphics2D.fillRect(0, 0, this.boundary.getWidth(), this.boundary.getHeight());
    copyImage(paramGraphics2D, this.destpanel[0], source[0]);
    for (int i = 0; i < this.width; i++)
      copyImage(paramGraphics2D, this.destpanel[1].translateX(25 * i), source[1]);
    copyImage(paramGraphics2D, this.destpanel[1].translateX(25 * this.width), source[2]);
    for (int i = 0; i < this.height; i++)
    {
      copyImage(paramGraphics2D, this.destpanel[2].translateY(29 * i), source[3]);
      copyImage(paramGraphics2D, this.destpanel[2].translate(25 * (this.width + 1), 29 * i), source[4]);
    }
    copyImage(paramGraphics2D, this.destpanel[3].translateY(20 + 29 * this.height), source[5]);
    copyImage(paramGraphics2D, this.destpanel[4].translate(125, 20 + 29 * this.height), source[7]);
  }

  private void paintList(Graphics2D paramGraphics2D)
  {
    if (this.visible)
    {
      paramGraphics2D.setFont(this.font);
      paramGraphics2D.setColor(this.normal);
      PlaylistItem[] arrayOfPlaylistItem = Playlist.getList();
      int i = arrayOfPlaylistItem.length > 4 ? 4 : arrayOfPlaylistItem.length;
      for (int j = 0; j < i; j++)
      {
        if (arrayOfPlaylistItem[j].isHighlighted())
        {
          paramGraphics2D.setColor(this.selbg);
          paramGraphics2D.fillRect(12, 20 + j * 13, 240, 14);
        }
        paramGraphics2D.setColor(arrayOfPlaylistItem[j].isFocused() ? this.current : this.normal);
        paramGraphics2D.drawString(formatString(arrayOfPlaylistItem[j], j), 12, 31 + j * 13);
      }
    }
  }

  private void paintButtons(Graphics2D paramGraphics2D)
  {
    if (this.column >= 0)
    {
      copyImage(paramGraphics2D, buttonArea[this.column][UPPER_LEFT], unshaded[this.column][UPPER_LEFT]);
      copyImage(paramGraphics2D, buttonArea[this.column][1], unshaded[this.column][1]);
      copyImage(paramGraphics2D, buttonArea[this.column][2], unshaded[this.column][2]);
      if (this.column == 1)
        copyImage(paramGraphics2D, buttonArea[this.column][3], unshaded[this.column][3]);
      copyImage(paramGraphics2D, barArea[this.column], bar[this.column]);
      if (this.level >= 0)
        copyImage(paramGraphics2D, buttonArea[this.column][this.level], shaded[this.column][this.level]);
    }
  }

  private void setPleditInfo(Skin paramSkin)
  {
    String str = paramSkin.getPlaylistInfo();
    str = str.toLowerCase();
    ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(str.getBytes());
    Properties localProperties = new Properties();
    try
    {
      localProperties.load(localByteArrayInputStream);
      this.bgcolor = parseColor(localProperties.getProperty("normalbg"));
      this.normal = parseColor(localProperties.getProperty("normal"));
      this.current = parseColor(localProperties.getProperty("current"));
      this.selbg = parseColor(localProperties.getProperty("selectedbg"));
      this.fname = localProperties.getProperty("font", "Serif");
    }
    catch (Exception localException)
    {
    }
  }

  private Color parseColor(String paramString)
    throws Exception
  {
    if (paramString.indexOf('#') == -1)
      throw new Exception("Can not parse color");
    paramString = paramString.substring(1);
    int i = Integer.parseInt(paramString.substring(0, 2), 16);
    int j = Integer.parseInt(paramString.substring(2, 4), 16);
    int k = Integer.parseInt(paramString.substring(4), 16);
    return new Color(i, j, k);
  }

  public void mousePressed(MouseEvent paramMouseEvent)
  {
    int i;
    if (buttonboundary.contains(paramMouseEvent.getPoint()))
    {
      for (i = 0; i < 5; i++)
        if (buttonArea[i][0].contains(paramMouseEvent.getPoint()))
        {
          this.column = i;
          this.level = 0;
          break;
        }
      fireEvent(new FionaEvent(this, 2001));
    }
    if (listboundary.contains(paramMouseEvent.getPoint()))
    {
      i = Playlist.size() > 4 ? 4 : Playlist.size();
      int j = paramMouseEvent.getModifiersEx();
      int k = (j & 0x1) != 0 ? 1 : 0;
      int m = (j & 0x2) != 0 ? 1 : 0;
      for (int n = 0; n < i; n++)
        if (area[n].contains(paramMouseEvent.getPoint()))
        {
          Playlist.setSelected(n);
          if (paramMouseEvent.getClickCount() == 2)
            Playlist.setFocus(n);
        }
      fireEvent(new FionaEvent(this, 2001));
    }
  }

  private String formatString(PlaylistItem paramPlaylistItem, int paramInt)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(this.startNumber + paramInt);
    localStringBuffer.append(". ");
    localStringBuffer.append(paramPlaylistItem.getSongName());
    return localStringBuffer.toString();
  }

  public void mouseReleased(MouseEvent paramMouseEvent)
  {
    if (this.column >= 0)
    {
      int i = this.eventValue[this.column] + this.level;
      this.column = -1;
      fireEvent(new FionaEvent(this, 2001));
      switch (i)
      {
      case 0:
        fireEvent(new FionaEvent(this, 2011));
      }
      fireEvent(new FionaEvent(this, 2001));
    }
  }

  public void mouseDragged(MouseEvent paramMouseEvent)
  {
      int j = -1;
    if (this.column >= 0)
    {
      int i = this.column == 1 ? 4 : 3;
      for (int k = 0; k < i; k++)
        if (buttonArea[this.column][k].contains(paramMouseEvent.getPoint()))
        {
          j = k;
          break label65;
        }
      
      label65: if (this.level != j)
      {
        this.level = j;
        fireEvent(new FionaEvent(this, 2001));
      }
    }
  }
}

/* Location:           C:\Users\jabail\Desktop\work_folder\jad\fiona.jar
 * Qualified Name:     com.wickedknight.fiona.skin.components.playlist.PLE
 * JD-Core Version:    0.6.2
 */