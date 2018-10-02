package com.wickedknight.fiona.util;

import java.awt.Point;

public class ObjConversion
{
  public static String convert(boolean paramBoolean)
  {
    return String.valueOf(paramBoolean);
  }

  public static String convert(int paramInt)
  {
    return String.valueOf(paramInt);
  }

  public static String convert(Point paramPoint)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("{ ");
    localStringBuffer.append(paramPoint.getX());
    localStringBuffer.append(" , ");
    localStringBuffer.append(paramPoint.getY());
    localStringBuffer.append(" }");
    return localStringBuffer.toString();
  }

  public static Point toPoint(String paramString)
  {
    int i = paramString.indexOf('{');
    int j = paramString.indexOf(',');
    int k = paramString.indexOf('}');
    int m = Integer.parseInt(paramString.substring(i + 1, j));
    int n = Integer.parseInt(paramString.substring(j + 1, k));
    return new Point(m, n);
  }
}

/* Location:           C:\Users\jabail\Desktop\work_folder\jad\fiona.jar
 * Qualified Name:     com.wickedknight.fiona.util.ObjConversion
 * JD-Core Version:    0.6.2
 */