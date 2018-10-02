package com.wickedknight.util;

import com.wickedknight.geom.Rectangle;

public abstract class Slide
{
  protected Rectangle button;

  public void setButton(Rectangle paramRectangle)
  {
    this.button = paramRectangle;
  }

  public Rectangle getButton()
  {
    return this.button;
  }

  public abstract void setMax(int paramInt);

  public abstract void setMin(int paramInt);

  public abstract void setLimits(int paramInt1, int paramInt2);

  public abstract void setBegining();

  public abstract void setEnd();
}

/* Location:           C:\Users\jabail\Desktop\work_folder\jad\fiona.jar
 * Qualified Name:     com.wickedknight.util.Slide
 * JD-Core Version:    0.6.2
 */