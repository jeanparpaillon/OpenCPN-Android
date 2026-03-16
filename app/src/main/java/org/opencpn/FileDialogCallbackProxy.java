package org.opencpn;

public class FileDialogCallbackProxy implements OCPNFileDialog.Callback {

    //static {
    //    System.loadLibrary("yournative");
    //}

    @Override
    public void onFileChosen(String path) {
        nativeFileDialogFinished(path);
    }

    private static native void nativeFileDialogFinished(String path);
}