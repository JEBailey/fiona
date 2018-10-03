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
