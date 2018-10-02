package com.wickedknight.util;

public class HorizontalSlide extends Slide
{
  int min;
  int max;

  public HorizontalSlide()
  {
  }

  public HorizontalSlide(int paramInt1, int paramInt2)
  {
    setLimits(paramInt1, paramInt2);
  }

  public void setPos(int paramInt)
  {
    this.button.setX(paramInt > this.max ? this.max : paramInt < this.min ? this.min : paramInt);
  }

  public void setMax(int paramInt)
  {
    this.max = paramInt;
  }

  public void setMin(int paramInt)
  {
    this.min = paramInt;
  }

  public void setLimits(int paramInt1, int paramInt2)
  {
    this.min = paramInt1;
    this.max = paramInt2;
  }

  public void setBegining()
  {
    this.button.setX(this.min);
  }

  public void setEnd()
  {
    this.button.setX(this.max);
  }

  public double getPercentage()
  {
    int i = this.max - this.min;
    int j = this.button.getX() - this.min;
    return j / i;
  }

  public void setPercentage(int paramInt)
  {
    paramInt = paramInt > 100 ? 100 : paramInt < 0 ? 0 : paramInt;
    int i = (int)((this.max - this.min) / 100.0D * paramInt);
    System.out.println("offset :" + i);
    this.button.setX(i + this.min);
  }
}

/* Location:           C:\Users\jabail\Desktop\work_folder\jad\fiona.jar
 * Qualified Name:     com.wickedknight.util.HorizontalSlide
 * JD-Core Version:    0.6.2
 */