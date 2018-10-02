package com.wickedknight.fiona.aspect;

import com.wickedknight.fiona.audio.FileNameFilter;
import java.awt.Component;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;

public class Playlist
{
  protected static ArrayList list = new ArrayList();

  public static void addFile(Component paramComponent)
  {
    FileNameFilter localFileNameFilter = new FileNameFilter("ogg,mp3");
    JFileChooser localJFileChooser = new JFileChooser("/home/jason");
    localJFileChooser.setDialogTitle("Fiona Music Select");
    localJFileChooser.setDialogType(0);
    localJFileChooser.setFileHidingEnabled(true);
    localJFileChooser.setFileFilter(localFileNameFilter);
    localJFileChooser.setMultiSelectionEnabled(true);
    localJFileChooser.showOpenDialog(paramComponent);
    addFiles(localJFileChooser.getSelectedFiles());
  }

  public static void addFile(File paramFile)
  {
    if (paramFile != null)
      list.add(createPLI(paramFile));
  }

  public static void addFiles(File[] paramArrayOfFile)
  {
    for (int i = 0; i < paramArrayOfFile.length; i++)
      addFile(paramArrayOfFile[i]);
  }

  public static PlaylistItem[] getList()
  {
    return (PlaylistItem[])list.toArray(new PlaylistItem[list.size()]);
  }

  public static void setAllSelected()
  {
    for (int i = 0; i < list.size(); i++)
      ((PlaylistItem)list.get(i)).setFocused(true);
  }

  public static void setSelected(int paramInt)
  {
    for (int i = 0; i < list.size(); i++)
      ((PlaylistItem)list.get(i)).setHighlighted(i == paramInt);
  }

  public static void setFocus(int paramInt)
  {
    for (int i = 0; i < list.size(); i++)
      ((PlaylistItem)list.get(i)).setFocused(i == paramInt);
  }

  public static void setSpecificSelect(int paramInt, boolean paramBoolean)
  {
    if (paramInt <= list.size())
      ((PlaylistItem)list.get(paramInt)).setHighlighted(paramBoolean);
  }

  public static void inverseSelection()
  {
    for (int i = 0; i < list.size(); i++)
    {
      boolean bool = ((PlaylistItem)list.get(i)).isHighlighted();
      ((PlaylistItem)list.get(i)).setHighlighted(!bool);
    }
  }

  public static int size()
  {
    return list.size();
  }

  private static PlaylistItem createPLI(File paramFile)
  {
    return new PlaylistItem(paramFile);
  }
}

/* Location:           C:\Users\jabail\Desktop\work_folder\jad\fiona.jar
 * Qualified Name:     com.wickedknight.fiona.aspect.Playlist
 * JD-Core Version:    0.6.2
 */