package com.wickedknight.geom;

import java.awt.geom.Point2D;

public class Point
{
  protected int x;
  protected int y;

  public Point()
  {
  }

  public Point(int paramInt1, int paramInt2)
  {
    this.x = paramInt1;
    this.y = paramInt2;
  }

  public Point(Point2D paramPoint2D)
  {
    this.x = ((int)paramPoint2D.getX());
    this.y = ((int)paramPoint2D.getY());
  }

  public int getX()
  {
    return this.x;
  }

  public int getY()
  {
    return this.y;
  }

  public void setX(int paramInt)
  {
    this.x = paramInt;
  }

  public void setY(int paramInt)
  {
    this.y = paramInt;
  }

  public void update(int paramInt1, int paramInt2)
  {
    this.x = paramInt1;
    this.y = paramInt2;
  }

  public void update(Point2D paramPoint2D)
  {
    this.x = ((int)paramPoint2D.getX());
    this.y = ((int)paramPoint2D.getY());
  }

  public void update(Point paramPoint)
  {
    this.x = paramPoint.getX();
    this.y = paramPoint.getY();
  }
}

/* Location:           C:\Users\jabail\Desktop\work_folder\jad\fiona.jar
 * Qualified Name:     com.wickedknight.geom.Point
 * JD-Core Version:    0.6.2
 */