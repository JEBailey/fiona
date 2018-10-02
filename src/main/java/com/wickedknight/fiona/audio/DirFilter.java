package com.wickedknight.fiona.audio;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class DirFilter extends FileFilter
{
  public boolean accept(File paramFile)
  {
    return !paramFile.isFile();
  }

  public String getDescription()
  {
    return "directory view";
  }
}

/* Location:           C:\Users\jabail\Desktop\work_folder\jad\fiona.jar
 * Qualified Name:     com.wickedknight.fiona.audio.DirFilter
 * JD-Core Version:    0.6.2
 */