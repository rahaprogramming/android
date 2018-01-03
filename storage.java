
import android.app.Activity;
import android.content.Context;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Ralph Harris
 * class used to read and write to device internal storage
 */

public class storage extends Activity {
    //usage: new storage().write("filename.txt", "write  this in file");
    public void write(String fileName,String writeMe ) throws IOException {

        FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
        fos.write(writeMe.getBytes());
        fos.close();
    }

    //usage: String result = new storage().read("filename.txt");
    //contents of the file will now be in the results variable
    public String read(String fileName ) throws IOException {
        FileInputStream fis = openFileInput(fileName);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader bufferedReader = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }
        fis.close();
        return line;
    }
}
