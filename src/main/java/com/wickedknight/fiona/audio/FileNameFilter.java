package com.wickedknight.fiona.audio;

import java.io.File;
import java.util.StringTokenizer;
import javax.swing.filechooser.FileFilter;

public class FileNameFilter extends FileFilter
{
  protected String[] extensions;

  public FileNameFilter(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ", ");
    int i = localStringTokenizer.countTokens();
    this.extensions = new String[i];
    for (int j = 0; j < i; j++)
      this.extensions[j] = localStringTokenizer.nextToken();
  }

  public boolean accept(File paramFile)
  {
    if (!paramFile.isFile())
      return true;
    for (int i = 0; i < this.extensions.length; i++)
      if (paramFile.getName().endsWith(this.extensions[i]))
        return true;
    return false;
  }

  public String getDescription()
  {
    return "default audio";
  }
}

/* Location:           C:\Users\jabail\Desktop\work_folder\jad\fiona.jar
 * Qualified Name:     com.wickedknight.fiona.audio.FileNameFilter
 * JD-Core Version:    0.6.2
 */