
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

/************************************************************
 * @Author Ralph Harris
 * This is a very useful class for storing passwords. It will 
 * encrypt a string. 
 * usage:
 *
 * String encryptedString = new encryption().encrypt("secret message or password");
 *
 * note: I have never had a need to decrypt a pass as I authenticate by simply re-encrypting 
 * the entered pass. However, if you have a need for this, contact me at rahaprogramming@gmail.com
 * and i'll write it for you.
 ***********************************************************/



public class encryption {

    public static String encrypt(String message) throws Exception {
        String username = "a username";
        String password = "A password";
        String SALT2 = "custom salt or imported";

        byte[] key = (message+SALT2 + username +message+ password).getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 128); // use only first 128 bit - can be changed if need be
        String thang = "";
       for(int i=0;i<key.length;i++){
           thang += byteToHex(key[i]);
       }
        return thang;
    }

    static private String byteToHex(byte b) {
        // Returns hex String representation of byte b
        char hexDigit[] = {
                '0', '1', '2', '3', '4', '5', '6', '7',
                '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
        };
        char[] array = { hexDigit[(b >> 4) & 0x0f], hexDigit[b & 0x0f] };
        return new String(array);
    }

    static public String charToHex(char c) {
        // Returns hex String representation of char c
        byte hi = (byte) (c >>> 8);
        byte lo = (byte) (c & 0xff);
        return byteToHex(hi) + byteToHex(lo);
    }
}
