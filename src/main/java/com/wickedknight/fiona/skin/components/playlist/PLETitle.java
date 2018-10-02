package com.wickedknight.fiona.skin.components.playlist;

import com.wickedknight.fiona.skin.Skin;
import com.wickedknight.fiona.skin.components.ActiveComponent;
import com.wickedknight.geom.Rectangle;
import java.awt.Graphics2D;
import java.awt.Image;

public class PLETitle extends ActiveComponent
{
  private static final int UPPER_LEFT = 0;
  private static final int UPPER_MIDDLE = 1;
  private static final int UPPER_RIGHT = 2;
  private static final int UPPER_LEFT_SHADED = 3;
  private static final int UPPER_MIDDLE_SHADED = 4;
  private static final int UPPER_RIGHT_SHADED = 5;
  private static final int TITLE = 6;
  private static final int TITLE_SHADED = 7;
  private Rectangle[] source = { new Rectangle(0, 0, 25, 20), new Rectangle(127, 0, 25, 20), new Rectangle(153, 0, 25, 20), new Rectangle(0, 0, 25, 20), new Rectangle(127, 0, 25, 20), new Rectangle(153, 0, 25, 20), new Rectangle(0, 42, 25, 38), new Rectangle(126, 72, 150, 38) };

  public PLETitle(Skin paramSkin)
  {
    this.panel = paramSkin.getPlaylistPanel();
    this.boundary = new Rectangle(0, 0, paramSkin.getMainPanel().getWidth(null), paramSkin.getMainPanel().getHeight(null));
  }

  public void paint(Graphics2D paramGraphics2D)
  {
  }
}

/* Location:           C:\Users\jabail\Desktop\work_folder\jad\fiona.jar
 * Qualified Name:     com.wickedknight.fiona.skin.components.playlist.PLETitle
 * JD-Core Version:    0.6.2
 */