package org.opencpn;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

import ar.com.daidalos.afiledialog.FileChooserDialog;

// Callback interface

// File chooser dialog
public class OCPNFileDialog {

    public interface Callback {
        void onFileChosen(String path);
    }

    public static void showFileDialog(Activity activity, String startDir,
                                      final Callback cb, boolean allowCreate)
    {
        activity.runOnUiThread(() -> {

            final FileChooserDialog dialog = new FileChooserDialog(activity, startDir);

            dialog.setShowFullPath(true);
            dialog.setFolderMode(true);
            dialog.setCanCreateFiles(false);
            dialog.setTitle("This is title");

            dialog.addListener(new FileChooserDialog.OnFileSelectedListener() {

                @Override
                public void onFileSelected(Dialog source, File file) {
                    source.dismiss();
                    cb.onFileChosen("file:" + file.getPath());
                }

                @Override
                public void onFileSelected(Dialog source, File folder, String name) {

                    File newFolder = new File(folder.getPath() + File.separator + name);

                    boolean success = true;
                    if (!newFolder.exists())
                        success = newFolder.mkdirs();

                    if (!success) {
                        Toast.makeText(source.getContext(),
                                "Could not create file in: " + folder.getPath(),
                                Toast.LENGTH_LONG).show();
                    }

                    dialog.loadFolder(folder.getPath());
                }
            });

            dialog.setOnCancelListener(d -> cb.onFileChosen("cancel"));

            dialog.show();
        });
    }

// Proxy to call C++ via JNI
    public class FileDialogCallbackProxy implements OCPNFileDialog.Callback {
        //static { System.loadLibrary("yournative"); }

        @Override
        public void onFileChosen(String path) {
            nativeFileDialogFinished(path);
        }

        private native void nativeFileDialogFinished(String path);
    }
}


