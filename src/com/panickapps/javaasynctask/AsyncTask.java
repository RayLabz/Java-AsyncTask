package com.panickapps.javaasynctask;

import com.sun.javafx.application.PlatformImpl;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

//TODO Template params as in https://developer.android.com/reference/android/os/AsyncTask
public abstract class AsyncTask {

    private static JFXPanel FX_PANEL_INIT;

    protected AsyncTask() {
        if (FX_PANEL_INIT == null) {
            FX_PANEL_INIT = new JFXPanel();
        }
    }

    protected abstract void onPreExecute();

    protected abstract void doInBackground();

    //TODO https://developer.android.com/reference/android/os/AsyncTask.html#onProgressUpdate(Progress...)
    protected abstract void onProgressUpdate();

    protected abstract void onPostExecute();

    public void execute() {
        onPreExecute();
        new Thread(new Runnable() {
            @Override
            public void run() {
                doInBackground();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        onPostExecute();
                        PlatformImpl.tkExit();
                    }
                });
            }
        }).start();
    }

}
