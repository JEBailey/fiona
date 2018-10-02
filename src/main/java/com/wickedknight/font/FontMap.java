package com.wickedknight.font;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Hashtable;

public class FontMap
{
  private Hashtable table;
  private Image defaultImage;

  public FontMap(Object[] paramArrayOfObject, Image[] paramArrayOfImage)
  {
    int i = paramArrayOfImage.length;
    this.table = new Hashtable(i);
    for (int j = 0; j < i; j++)
      this.table.put(paramArrayOfObject[j], paramArrayOfImage[j]);
    setDefaultImage(null);
  }

  public boolean putImage(Object paramObject, Image paramImage)
  {
    if (this.table != null)
    {
      this.table.put(paramObject, paramImage);
      return true;
    }
    return false;
  }

  public void setDefaultImage(Image paramImage)
  {
    this.defaultImage = paramImage;
  }

  public void drawString(String paramString, int paramInt1, int paramInt2, Graphics2D paramGraphics2D)
  {
    int i = paramString.length();
    for (int j = 0; j < i; j++)
    {
      Image localImage = (Image)this.table.get("" + paramString.charAt(j));
      if (localImage == null)
        localImage = this.defaultImage;
      if (localImage != null)
        paramGraphics2D.drawImage(localImage, paramInt1, paramInt2, null);
      paramInt1 += localImage.getWidth(null);
    }
  }
}

/* Location:           C:\Users\jabail\Desktop\work_folder\jad\fiona.jar
 * Qualified Name:     com.wickedknight.font.FontMap
 * JD-Core Version:    0.6.2
 */