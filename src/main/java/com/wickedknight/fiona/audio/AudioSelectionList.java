package com.wickedknight.fiona.audio;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class AudioSelectionList
{
  List files = new LinkedList();
  private static AudioSelectionList list;

  public static AudioSelectionList getInstance()
  {
    if (list == null)
      list = new AudioSelectionList();
    return list;
  }

  public void addFiles(File[] paramArrayOfFile)
  {
  }
}

/* Location:           C:\Users\jabail\Desktop\work_folder\jad\fiona.jar
 * Qualified Name:     com.wickedknight.fiona.audio.AudioSelectionList
 * JD-Core Version:    0.6.2
 */