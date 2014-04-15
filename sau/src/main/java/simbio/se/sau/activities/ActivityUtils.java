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
package simbio.se.sau.activities;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources.NotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import simbio.se.sau.API;

/**
 * Utilities to activities
 *
 * @author Ademar Alves de Oliveira (ademar111190@gmail.com)
 * @date Nov 27, 2013 2:48:56 PM
 * @since {@link API#Version_3_0_0}
 */
public class ActivityUtils {

    /**
     * the {@link Activity} we need it
     */
    private Activity activity;

    /**
     * @param activity the {@link Activity}, not send null because it causes a
     *                 {@link NullPointerException} in some methods
     * @since {@link API#Version_3_0_0}
     */
    public ActivityUtils(Activity activity) {
        this.activity = activity;
    }

    /**
     * open the document with the correct activity
     *
     * @param documentPath the Path of document
     * @throws NotFoundException         if the file not existis
     * @throws ActivityNotFoundException if have no {@link Activity} associed with the file type
     * @throws NullPointerException      if the document path is <code>null</code>
     * @since {@link API#Version_3_0_0}
     */
    public void openDocumentWithCorrectActivity(String documentPath)
            throws NotFoundException, ActivityNotFoundException, NullPointerException {
        if (documentPath == null)
            throw new NullPointerException();
        File file = new File(documentPath);
        if (!file.exists())
            throw new NotFoundException(documentPath);
        MimeTypeMap map = MimeTypeMap.getSingleton();
        String ext = MimeTypeMap.getFileExtensionFromUrl(file.getName());
        String type = map.getMimeTypeFromExtension(ext);
        if (type == null)
            type = "*/*";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.fromFile(file);
        intent.setDataAndType(data, type);
        activity.startActivity(intent);
    }

    /**
     * Copy files on assest to a manipulable folder
     *
     * @param folderPath the {@link String} with path, recommended uses the
     *                   {@link Environment#getExternalStorageDirectory()} and
     *                   {@link File#getAbsolutePath()}
     * @throws IOException          problems with the {@link File}
     * @throws NullPointerException pointer problems
     * @since {@link API#Version_3_0_0}
     */
    public void copyAssetsFilesToFolder(String folderPath)
            throws IOException, NullPointerException {
        if (folderPath == null)
            throw new NullPointerException();

        File file = new File(folderPath);
        if (!file.exists())
            if (!file.mkdirs())
                throw new FileNotFoundException("Cannot make dirs");

        AssetManager assetManager = activity.getAssets();
        String[] files = assetManager.list("");
        InputStream in;
        OutputStream out;
        byte[] buffer;
        int read;
        for (String filename : files) {
            try {
                in = assetManager.open(filename);
                file = new File(folderPath, filename);
                out = new FileOutputStream(file);
                buffer = new byte[1024];
                while ((read = in.read(buffer)) != -1)
                    out.write(buffer, 0, read);
                in.close();
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
