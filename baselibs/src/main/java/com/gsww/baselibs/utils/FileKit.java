//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gsww.baselibs.utils;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileKit {
    private static final String TAG = FileKit.class.getSimpleName();

    public FileKit() {
    }

    public static boolean save(Context context, String data, String filename) {
        return StringKit.isEmpty(data) ? false : save(context, data.getBytes(), filename);
    }

    public static boolean save(Context context, byte[] data, String filename) {
        boolean result = false;
        FileOutputStream fos = null;

        try {
            fos = context.openFileOutput(filename, 0);
            fos.write(data);
            fos.flush();
        } catch (Exception var14) {
            Logger.e(TAG, var14.getMessage(), var14);
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception var13) {
            }

        }

        return result;
    }

    public static String getString(Context context, String filename) {
        String result = null;
        FileInputStream fis = null;

        try {
            if (exists(context, filename)) {
                fis = context.openFileInput(filename);
                result = StringKit.stream2String(fis);
            }
        } catch (Exception var13) {
            Logger.e(TAG, var13.getMessage(), var13);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception var12) {
                Logger.e(TAG, var12.getMessage(), var12);
            }

        }

        return result;
    }

    public static boolean save(Context context, Object object, String filename) {
        boolean result = false;
        if (context != null && object != null && filename != null && filename.trim().length() != 0) {
            FileOutputStream fos = null;
            ObjectOutputStream oos = null;

            try {
                fos = context.openFileOutput(filename, 0);
                oos = new ObjectOutputStream(fos);
                oos.writeObject(object);
                oos.flush();
                result = true;
            } catch (Exception var15) {
                Logger.e(TAG, var15.getMessage(), var15);
            } finally {
                try {
                    if (oos != null) {
                        oos.close();
                    }

                    if (fos != null) {
                        fos.close();
                    }
                } catch (Exception var14) {
                    Logger.e(TAG, var14.getMessage(), var14);
                }

            }

            return result;
        } else {
            return result;
        }
    }

    public static Object getObject(Context context, String filename) {
        Object object = null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;

        try {
            if (exists(context, filename)) {
                fis = context.openFileInput(filename);
                ois = new ObjectInputStream(fis);
                object = ois.readObject();
            }
        } catch (Exception var14) {
            Logger.e(TAG, var14.getMessage(), var14);
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }

                if (fis != null) {
                    fis.close();
                }
            } catch (Exception var13) {
                Logger.e(TAG, var13.getMessage(), var13);
            }

        }

        return object;
    }

    public static boolean remove(Context context, String filename) {
        return context != null && filename != null ? context.deleteFile(filename) : false;
    }

    public static boolean exists(Context context, String filename) {
        File file = context.getFileStreamPath(filename);
        return file != null && file.exists();
    }
}
