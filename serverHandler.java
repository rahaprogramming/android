
import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/****************************************************
 * @Author Ralph Harris
 * 1/1/2018
 * This class posts information to a php file on a server using the post method.
 * usage:
 * String urlParameters = "name=" + urlname + "&email=" + urlemail + "&password=" + securePass+"&uuid="+uniqueID;
 * String response = new serverHandler().post("http://www.google.com/file.php", urlParameters);
 *
 * You will need to capture data on the recieving end with a php file such as the following:
 *
 <?php
 $name = $_POST["name"];
 $email = $_POST["email"];
 $pass = $_POST["password"];
 $uuid = $_POST["uuid"];
 $filename = $name.'.json';
 $file = fopen($filename, 'w+');
 $array = array( "name" => $name,  "email" => $email, "pass" => $pass, "uuid" => $uuid);
 $json = json_encode($array);
 fwrite($file, $json);
 print_r ($json);
 ?>

 This will save each variable to a file on the server called myName.json in json format and return
 a json formatted string which is easily converted to a JSONobject in java.
 **************************************************/
public class serverHandler extends Activity {
    //post to a php file
    public static String post(String targetURL, String urlParameters) {
        URL url;
        HttpURLConnection connection = null;
        try {
            //Create connection
            url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");// optionally change to GET
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            connection.setRequestProperty("Content-Length", "" +
                    Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();

        } catch (Exception e) {

            e.printStackTrace();

            return e.toString();

        } finally {

            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    //check connectivity
    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
}
