package com.wickedknight.fiona.audio;


import com.jcraft.jogg.Packet;
import com.jcraft.jogg.Page;
import com.jcraft.jogg.StreamState;
import com.jcraft.jogg.SyncState;
import com.jcraft.jorbis.Block;
import com.jcraft.jorbis.Comment;
import com.jcraft.jorbis.DspState;
import com.jcraft.jorbis.Info;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Vector;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.*;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class OggAudio
  implements ActionListener, AudioComponent, Runnable
{
  private Thread player = null;
  InputStream bitStream = null;
  static final int BUFSIZE = 8192;
  static int convsize = 16384;
  static byte[] convbuffer = new byte[convsize];
  private int RETRY = 3;
  int retry = this.RETRY;
  String playlistfile = "playlist";
  private SyncState sync;
  private StreamState stream;
  private Page page;
  private Packet packet;
  private Info info;
  private Comment comment;
  private DspState state;
  private Block block;
  byte[] buffer = null;
  int bytes = 0;
  int format;
  int rate = 0;
  int channels = 0;
  int left_vol_scale = 100;
  int right_vol_scale = 100;
  SourceDataLine outputLine = null;
  String current_source = null;
  int frameSizeInBytes;
  int bufferLengthInBytes;
  Vector playlist = new Vector();
  JPanel panel;
  JComboBox cb;
  JButton start_button;
  JButton stats_button;

  public void init()
  {
  }

  void init_jorbis()
  {
    this.sync = new SyncState();
    this.stream = new StreamState();
    this.page = new Page();
    this.packet = new Packet();
    this.info = new Info();
    this.comment = new Comment();
    this.state = new DspState();
    this.block = new Block(this.state);
    this.buffer = null;
    this.bytes = 0;
    this.sync.init();
  }

  SourceDataLine getOutputLine(int paramInt1, int paramInt2)
  {
    if ((this.outputLine != null) || (this.rate != paramInt2) || (this.channels != paramInt1))
    {
      if (this.outputLine != null)
      {
        this.outputLine.drain();
        this.outputLine.stop();
        this.outputLine.close();
      }
      init_audio(paramInt1, paramInt2);
      this.outputLine.start();
    }
    return this.outputLine;
  }

  void init_audio(int paramInt1, int paramInt2)
  {
    try
    {
      AudioFormat localAudioFormat = new AudioFormat(paramInt2, 16, paramInt1, true, false);
      DataLine.Info localInfo = new DataLine.Info(SourceDataLine.class, localAudioFormat, -1);
      if (!AudioSystem.isLineSupported(localInfo))
        return;
      try
      {
        this.outputLine = ((SourceDataLine)AudioSystem.getLine(localInfo));
        this.outputLine.open(localAudioFormat);
      }
      catch (LineUnavailableException localLineUnavailableException)
      {
        System.out.println("Unable topacketen the sourceDataLine: " + localLineUnavailableException);
        return;
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        System.out.println("Illegal Argument: " + localIllegalArgumentException);
        return;
      }
      this.frameSizeInBytes = localAudioFormat.getFrameSize();
      int i = this.outputLine.getBufferSize() / this.frameSizeInBytes / 2;
      this.bufferLengthInBytes = (i * this.frameSizeInBytes);
      this.rate = paramInt2;
      this.channels = paramInt1;
    }
    catch (Exception localException)
    {
      System.out.println(localException);
      System.exit(1);
    }
  }

  public void run()
  {
    Thread localThread = Thread.currentThread();
    if (this.bitStream != null)
      play_stream(localThread);
    stop_sound();
  }

  private void play_stream(Thread paramThread)
  {
    init_jorbis();
    this.retry = this.RETRY;
    while (true)
    {
      int i = 0;
      int j = this.sync.buffer(8192);
      this.buffer = this.sync.data;
      try
      {
        this.bytes = this.bitStream.read(this.buffer, j, 8192);
      }
      catch (Exception localException2)
      {
        System.err.println(localException2);
        System.out.println("blah");
        System.exit(1);
        return;
      }
      this.sync.wrote(this.bytes);
      if (this.sync.pageout(this.page) != 1)
      {
        if (this.bytes < 8192)
          break;
        System.err.println("Input does not appear to be an Ogg bitstream.");
        return;
      }
      this.stream.init(this.page.serialno());
      this.stream.reset();
      this.info.init();
      this.comment.init();
      if (this.stream.pagein(this.page) < 0)
      {
        System.err.println("Error reading first page of Ogg bitstream data.");
        return;
      }
      this.retry = this.RETRY;
      if (this.stream.packetout(this.packet) != 1)
      {
        System.err.println("Error reading initial header packet.");
        break;
      }
      if (this.info.synthesis_headerin(this.comment, this.packet) < 0)
      {
        System.err.println("This Ogg bitstream does not contain Vorbis audio data.");
        return;
      }
      int k = 0;
      while (k < 2)
      {
        label359: 
        while (k < 2)
        {
          int m = this.sync.pageout(this.page);
          if (m > 0)
          {
            this.stream.pagein(this.page);
            while (true)
            {
              if (k >= 2)
                break label359;
              m = this.stream.packetout(this.packet);
              if (m == 0)
                break;
              if (m == -1)
              {
                System.err.println("Corrupt secondary header.  Exiting.");
                break label1317;
              }
              this.info.synthesis_headerin(this.comment, this.packet);
              k++;
            }
          }
        }
        j = this.sync.buffer(8192);
        this.buffer = this.sync.data;
        try
        {
          this.bytes = this.bitStream.read(this.buffer, j, 8192);
        }
        catch (Exception localException3)
        {
          System.err.println(localException3);
          System.out.println("sicko");
          System.exit(1);
          return;
        }
        if ((this.bytes == 0) && (k < 2))
        {
          System.err.println("End of file before finding all Vorbis headers!");
          return;
        }
        this.sync.wrote(this.bytes);
      }
      byte[][] localObject1 = this.comment.user_comments;
      float[][][] arrayOfFloat = null;
      for (int n = 0; (n < localObject1.length) && (localObject1[n] != null); n++)
      {
        System.err.println("Comment: " + new String(localObject1[n], 0, localObject1[n].length - 1));
        if (arrayOfFloat != null)
          arrayOfFloat.append(" " + new String(localObject1[n], 0, localObject1[n].length - 1));
      }
      System.err.println("Bitstream is " + this.info.channels + " channel, " + this.info.rate + "Hz");
      System.err.println("Encoded by: " + new String(this.comment.vendor, 0, this.comment.vendor.length - 1) + "\n");
      convsize = 8192 / this.info.channels;
      this.state.synthesis_init(this.info);
      this.block.init(this.state);
      double[][][] localObject1a = new double[1][][];
      arrayOfFloat = new float[1][][];
      int[] arrayOfInt = new int[this.info.channels];
      getOutputLine(this.info.channels, this.info.rate);
      while (i == 0)
      {
        while (i == 0)
        {
          if (this.player != paramThread)
          {
            try
            {
              System.out.println("closing stream");
              this.bitStream.close();
            }
            catch (Exception localException4)
            {
              System.out.println("number 1");
              System.exit(1);
            }
            return;
          }
          int i1 = this.sync.pageout(this.page);
          if (i1 == 0)
            break;
          if (i1 != -1)
          {
            this.stream.pagein(this.page);
            while (true)
            {
              i1 = this.stream.packetout(this.packet);
              if (i1 == 0)
                break;
              if (i1 != -1)
              {
                if (this.block.synthesis(this.packet) == 0)
                  this.state.synthesis_blockin(this.block);
                int i2;
                while ((i2 = this.state.synthesis_pcmout(arrayOfFloat, arrayOfInt)) > 0)
                {
                  Object localObject2 = localObject1[0];
                   local[[F = arrayOfFloat[0];
                  int i3 = 0;
                  int i4 = i2 < convsize ? i2 : convsize;
                  for (k = 0; k < this.info.channels; k++)
                  {
                    int i5 = k * 2;
                    int i6 = arrayOfInt[k];
                    for (int i7 = 0; i7 < i4; i7++)
                    {
                      int i8 = (int)(local[F[k][(i6 + i7)]] * 32767.0D);
                      if (i8 > 32767)
                      {
                        i8 = 32767;
                        i3 = 1;
                      }
                      if (i8 < -32768)
                      {
                        i8 = -32768;
                        i3 = 1;
                      }
                      if (i8 < 0)
                        i8 |= 32768;
                      convbuffer[i5] = ((byte)i8);
                      convbuffer[(i5 + 1)] = ((byte)(i8 >>> 8));
                      i5 += 2 * this.info.channels;
                    }
                  }
                  this.outputLine.write(convbuffer, 0, 2 * this.info.channels * i4);
                  this.state.synthesis_read(i4);
                }
              }
            }
            if (this.page.eos() != 0)
              i = 1;
          }
        }
        if (i == 0)
        {
          j = this.sync.buffer(8192);
          this.buffer = this.sync.data;
          try
          {
            this.bytes = this.bitStream.read(this.buffer, j, 8192);
          }
          catch (Exception localException5)
          {
            System.out.println("number 2");
            System.err.println(localException5);
            System.exit(1);
            return;
          }
          if (this.bytes == -1)
            break;
          this.sync.wrote(this.bytes);
          if (this.bytes == 0)
            i = 1;
        }
      }
      this.stream.clear();
      this.block.clear();
      this.state.clear();
      this.info.clear();
    }
    label1317: this.sync.clear();
    try
    {
      if (this.bitStream != null)
        this.bitStream.close();
    }
    catch (Exception localException1)
    {
      System.out.println("number 3");
    }
  }

  public void stop()
  {
    if (this.player == null)
      try
      {
        this.outputLine.drain();
        this.outputLine.stop();
        this.outputLine.close();
        if (this.bitStream != null)
          this.bitStream.close();
      }
      catch (Exception localException)
      {
        System.out.println("number 4");
      }
    this.player = null;
  }

  public void actionPerformed(ActionEvent paramActionEvent)
  {
    if (paramActionEvent.getSource() == this.stats_button);
  }

  public void play_sound()
  {
    if (this.player != null)
      return;
    this.player = new Thread(this);
    this.player.start();
  }

  public void stop_sound()
  {
    if (this.player == null)
      return;
    this.player = null;
  }

  private String readline(InputStream paramInputStream)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i;
    do
    {
      try
      {
        i = paramInputStream.read();
      }
      catch (Exception localException)
      {
        System.out.println("number 5");
        return null;
      }
      if (i == -1)
        return null;
      if ((i != 0) && (i != 10))
        localStringBuffer.append((char)i);
    }
    while (i != 10);
    return localStringBuffer.toString();
  }

  public boolean pressFfwd()
  {
    return true;
  }

  public AudioInformation getInformation()
  {
    return null;
  }

  public boolean pressPause()
  {
    return true;
  }

  public boolean pressPlay()
  {
    return true;
  }

  public boolean pressRwnd()
  {
    return true;
  }

  public boolean pressSeek(long paramLong)
  {
    return true;
  }

  public boolean pressStop()
  {
    return true;
  }

  public void setInputStream(InputStream paramInputStream)
  {
    this.bitStream = paramInputStream;
  }

  public void setPlayer(Thread paramThread)
  {
    this.player = paramThread;
  }
}

/* Location:           C:\Users\jabail\Desktop\work_folder\jad\fiona.jar
 * Qualified Name:     com.wickedknight.fiona.audio.OggAudio
 * JD-Core Version:    0.6.2
 */