package com.wickedknight.fiona.aspect;

import java.io.File;

public class PlaylistItem
{
  File file;
  boolean focus;
  boolean highlighted;

  public PlaylistItem(File paramFile)
  {
    this.file = paramFile;
  }

  public File getFile()
  {
    return this.file;
  }

  public void setFile(File paramFile)
  {
    this.file = paramFile;
  }

  public String getSongName()
  {
    return this.file.getName();
  }

  public String getSongLength()
  {
    return null;
  }

  public boolean isHighlighted()
  {
    return this.highlighted;
  }

  public boolean isFocused()
  {
    return this.focus;
  }

  public void setFocused(boolean paramBoolean)
  {
    this.focus = paramBoolean;
  }

  public void setHighlighted(boolean paramBoolean)
  {
    this.highlighted = paramBoolean;
  }
}

/* Location:           C:\Users\jabail\Desktop\work_folder\jad\fiona.jar
 * Qualified Name:     com.wickedknight.fiona.aspect.PlaylistItem
 * JD-Core Version:    0.6.2
 */