import com.wickedknight.fiona.audio.OggAudio;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;

public class OggTest
{
  public static void main(String[] paramArrayOfString)
  {
    OggAudio localOggAudio = new OggAudio();
    if (paramArrayOfString.length < 1)
    {
      System.out.println("no file specified");
      System.exit(1);
    }
    File localFile = new File(paramArrayOfString[0]);
    BufferedInputStream localBufferedInputStream = null;
    try
    {
      localBufferedInputStream = new BufferedInputStream(new FileInputStream(localFile));
    }
    catch (Exception localException)
    {
      System.out.println(localException);
      System.exit(1);
    }
    localOggAudio.setInputStream(localBufferedInputStream);
    localOggAudio.play_sound();
  }
}

/* Location:           C:\Users\jabail\Desktop\work_folder\jad\fiona.jar
 * Qualified Name:     OggTest
 * JD-Core Version:    0.6.2
 */