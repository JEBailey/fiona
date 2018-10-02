package com.wickedknight.fiona.skin.components;

import com.wickedknight.fiona.skin.Skin;
import com.wickedknight.geom.Rectangle;
import java.awt.Graphics2D;
import java.awt.Image;

public class Main extends ActiveComponent
{
  public Main(Skin paramSkin)
  {
    this.panel = paramSkin.getMainPanel();
    this.boundary = new Rectangle(0, 0, this.panel.getWidth(null), this.panel.getHeight(null));
  }

  public void paint(Graphics2D paramGraphics2D)
  {
    if (this.visible)
      paramGraphics2D.drawImage(this.panel, 0, 0, this.boundary.getWidth(), this.boundary.getHeight(), null);
  }
}

/* Location:           C:\Users\jabail\Desktop\work_folder\jad\fiona.jar
 * Qualified Name:     com.wickedknight.fiona.skin.components.Main
 * JD-Core Version:    0.6.2
 */