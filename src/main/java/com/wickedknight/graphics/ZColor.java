package com.wickedknight.graphics;

import java.awt.Color;

public class ZColor
{
  protected ZColor(String paramString)
  {
  }

  public static Color getColor(String paramString)
    throws Exception
  {
    if (paramString.indexOf('#') == -1)
      throw new Exception("Can not parse color!");
    paramString = paramString.substring(1);
    int i = Integer.parseInt(paramString.substring(0, 2), 16);
    int j = Integer.parseInt(paramString.substring(2, 4), 16);
    int k = Integer.parseInt(paramString.substring(4), 16);
    return new Color(i, j, k);
  }
}

/* Location:           C:\Users\jabail\Desktop\work_folder\jad\fiona.jar
 * Qualified Name:     com.wickedknight.graphics.ZColor
 * JD-Core Version:    0.6.2
 */