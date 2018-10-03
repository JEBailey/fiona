package com.wickedknight.fiona.audio;

public abstract interface AudioComponent
{
  public abstract boolean pressPlay();

  public abstract boolean pressPause();

  public abstract boolean pressSeek(long paramLong);

  public abstract boolean pressFfwd();

  public abstract boolean pressRwnd();

  public abstract boolean pressStop();

  public abstract AudioInformation getInformation();
}
