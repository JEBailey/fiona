package com.wickedknight.geom;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Rectangle extends Point
{
  protected int w;
  protected int h;

  public Rectangle()
  {
  }

  public Rectangle(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super(paramInt1, paramInt2);
    this.w = paramInt3;
    this.h = paramInt4;
  }

  public Rectangle(Rectangle paramRectangle)
  {
    super(paramRectangle.getX(), paramRectangle.getY());
    this.w = paramRectangle.getWidth();
    this.h = paramRectangle.getHeight();
  }

  public int getWidth()
  {
    return this.w;
  }

  public int getHeight()
  {
    return this.h;
  }

  public int getMaxX()
  {
    return this.x + this.w;
  }

  public int getMaxY()
  {
    return this.y + this.h;
  }

  public void setHeight(int paramInt)
  {
    this.h = paramInt;
  }

  public void setWidth(int paramInt)
  {
    this.w = paramInt;
  }

  public Rectangle translate(int paramInt1, int paramInt2)
  {
    return new Rectangle(this.x + paramInt1, this.y + paramInt2, this.w, this.h);
  }

  public Rectangle translateX(int paramInt)
  {
    return new Rectangle(this.x + paramInt, this.y, this.w, this.h);
  }

  public Rectangle translateY(int paramInt)
  {
    return new Rectangle(this.x, this.y + paramInt, this.w, this.h);
  }

  public void update(Rectangle paramRectangle)
  {
    this.x = paramRectangle.getX();
    this.y = paramRectangle.getY();
    this.w = paramRectangle.getWidth();
    this.h = paramRectangle.getHeight();
  }

  public void update(Rectangle2D paramRectangle2D)
  {
    this.x = ((int)paramRectangle2D.getX());
    this.y = ((int)paramRectangle2D.getY());
    this.w = ((int)paramRectangle2D.getWidth());
    this.h = ((int)paramRectangle2D.getHeight());
  }

  public boolean contains(int paramInt1, int paramInt2)
  {
    return (paramInt1 > this.x) && (paramInt2 > this.y);
  }

  public boolean contains(Point2D paramPoint2D)
  {
    return contains((int)paramPoint2D.getX(), (int)paramPoint2D.getY());
  }

  public boolean contains(Point paramPoint)
  {
    return contains(paramPoint.getX(), paramPoint.getY());
  }
}

/* Location:           C:\Users\jabail\Desktop\work_folder\jad\fiona.jar
 * Qualified Name:     com.wickedknight.geom.Rectangle
 * JD-Core Version:    0.6.2
 */