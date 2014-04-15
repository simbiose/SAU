/**
 * Copyright 2013-2014
 * Ademar Alves de Oliveira <ademar111190@gmail.com /> Simbio.se
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package simbio.se.sau.encryption;

import android.R.string;
import android.annotation.TargetApi;
import android.os.Build;
import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import simbio.se.sau.API;
import simbio.se.sau.log.SimbiLog;

/**
 * A class to make more easy simple encripty rotines
 *
 * @author Ademar Alves de Oliveira (ademar111190@gmail.com)
 * @date Nov 28, 2013 12:33:43 PM
 * @since {@link API#Version_3_0_0}
 */
@TargetApi(Build.VERSION_CODES.FROYO)
public class Encryption {

    private String charsetName = "UTF8";
    private String algorithm = "DES";
    private int base64Mode = Base64.DEFAULT;

    /**
     * @return the charset name
     * @since {@link API#Version_3_0_0}
     */
    public String getCharsetName() {
        return charsetName;
    }

    /**
     * @param charsetName the new charset name
     * @since {@link API#Version_3_0_0}
     */
    public void setCharsetName(String charsetName) {
        this.charsetName = charsetName;
    }

    /**
     * @return the algorithm used
     * @since {@link API#Version_3_0_0}
     */
    public String getAlgorithm() {
        return algorithm;
    }

    /**
     * @param algorithm the algorithm to be used
     * @since {@link API#Version_3_0_0}
     */
    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    /**
     * @return the Base 64 mode
     * @since {@link API#Version_3_0_0}
     */
    public int getBase64Mode() {
        return base64Mode;
    }

    /**
     * @param base64Mode set the base 64 mode
     * @since {@link API#Version_3_0_0}
     */
    public void setBase64Mode(int base64Mode) {
        this.base64Mode = base64Mode;
    }

    /**
     * Encrypt a {@link string}
     *
     * @param key  the {@link String} key
     * @param data the {@link String} to be encrypted
     * @return the encrypted {@link String} or <code>null</code> if occur some error
     */
    public String encrypt(String key, String data) {
        if (key == null || data == null)
            return null;
        try {
            DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(charsetName));
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(algorithm);
            SecretKey secretKey = secretKeyFactory.generateSecret(desKeySpec);
            byte[] dataBytes = data.getBytes(charsetName);
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.encodeToString(cipher.doFinal(dataBytes), base64Mode);
        } catch (Exception e) {
            SimbiLog.printException(e);
            return null;
        }
    }

    /**
     * Decrypt a {@link string}
     *
     * @param key  the {@link String} key
     * @param data the {@link String} to be decrypted
     * @return the decrypted {@link String} or <code>null</code> if occur some error
     */
    public String decrypt(String key, String data) {
        if (key == null || data == null)
            return null;
        try {
            byte[] dataBytes = Base64.decode(data, base64Mode);
            DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(charsetName));
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(algorithm);
            SecretKey secretKey = secretKeyFactory.generateSecret(desKeySpec);
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] dataBytesDecrypted = (cipher.doFinal(dataBytes));
            return new String(dataBytesDecrypted);
        } catch (Exception e) {
            SimbiLog.printException(e);
            return null;
        }
    }
}
