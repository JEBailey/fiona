package com.wickedknight.graphics;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

public class BufferedGraphics
{
  protected Component parent;
  protected Image buffer;

  protected BufferedGraphics()
  {
    this.parent = null;
    this.buffer = null;
  }

  public BufferedGraphics(Component paramComponent)
  {
    this.parent = paramComponent;
    createBuffer();
  }

  public final Image getBuffer()
  {
    return this.buffer;
  }

  public Graphics getValidGraphics()
  {
    if (!isValid())
      createBuffer();
    return this.buffer.getGraphics();
  }

  protected void createBuffer()
  {
    Dimension localDimension = this.parent.getSize();
    this.buffer = this.parent.createVolatileImage(localDimension.width, localDimension.height);
  }

  protected boolean isValid()
  {
    if (this.parent == null)
      return false;
    Dimension localDimension = this.parent.getSize();
    return (this.buffer != null) && (this.buffer.getWidth(null) == localDimension.width) && (this.buffer.getHeight(null) == localDimension.height);
  }
}

/* Location:           C:\Users\jabail\Desktop\work_folder\jad\fiona.jar
 * Qualified Name:     com.wickedknight.graphics.BufferedGraphics
 * JD-Core Version:    0.6.2
 */