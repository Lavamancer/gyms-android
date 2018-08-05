/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.service.tool;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class StorageTool {


    public static <T extends Serializable> T load(Context context, String path) {
        File file = new File(context.getDir("data", Context.MODE_PRIVATE), path);
        System.out.println("Loading object " + path + " with " + file.length() + " bytes");
        if (file.length() > 0) {
            ObjectInputStream ois;
            try {
                ois = new ObjectInputStream(new FileInputStream(file));
                Object object = ois.readObject();
                ois.close();
                return (T) object;
            } catch (Exception e) {
                System.out.println("Serialized object cannot be readed: " + path);
                e.printStackTrace();
                file.delete();
            }
        }
        return null;
    }

    public static void store(Context context, Serializable serializable, String path) {
        File file = new File(context.getDir("data", Context.MODE_PRIVATE), path);
        System.out.println("Storing object " + path + " with " + file.length() + " bytes");
        ObjectOutputStream outputStream;
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(file));
            outputStream.writeObject(serializable);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            System.out.println("Serialized object cannot be stored: " + path);
            e.printStackTrace();
            file.delete();
        }
    }

}
