
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
    
    //here's another method. slower but safer
    public static Bitmap loadBitmap(Context context, String picName){ 
    Bitmap b = null; 
    FileInputStream fis; 
    try { 
        fis = context.openFileInput(picName); 
        b = BitmapFactory.decodeStream(fis);   
    }  
    catch (FileNotFoundException e) { 
        Log.d(TAG, "file not found"); 
        e.printStackTrace(); 
    }  
    catch (IOException e) { 
        Log.d(TAG, "io exception"); 
        e.printStackTrace(); 
    } finally {
        fis.close();
    }
    return b; 
    } 
    public static void saveFile(Context context, Bitmap b, String picName){ 
    FileOutputStream fos; 
    try { 
        fos = context.openFileOutput(picName, Context.MODE_PRIVATE); 
        b.compress(Bitmap.CompressFormat.PNG, 100, fos);  
    }  
    catch (FileNotFoundException e) { 
        Log.d(TAG, "file not found"); 
        e.printStackTrace(); 
    }  
    catch (IOException e) { 
        Log.d(TAG, "io exception"); 
        e.printStackTrace(); 
    } finally {
        fos.close();
    }
}
}
