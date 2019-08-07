package com.panickapps.javaasynctask;

import com.sun.javafx.application.PlatformImpl;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

//TODO Template params as in https://developer.android.com/reference/android/os/AsyncTask
public abstract class AsyncTask <Parameter, Progress, Result> {

    private enum Status {
        FINISHED,
        PENDING,
        RUNNING
    }

    private static final String ASYNCTASK_THREAD_NAME_IDENTIFIER = "com.panickapps.javaasynctask-Thread";
    private static long ASYNCTASK_THREAD_ID = 0;
    private static JFXPanel FX_PANEL_INIT;

    private Status status;
    private Thread taskThread;

    protected AsyncTask() {
        if (FX_PANEL_INIT == null) {
            FX_PANEL_INIT = new JFXPanel();
        }
        ASYNCTASK_THREAD_ID++;
        status = Status.PENDING;
    }

    public Status getStatus() {
        return status;
    }

    protected final void publishProgress(Progress... progresses) {
        //Override this method if you want to publish a progress in your doInBackground() method.
    }

    protected void onProgressUpdate(Progress... progresses) {
        //Override this method if you want to perform an operation when progress is updated.
    }

    protected abstract void onPreExecute();

    protected abstract Result doInBackground(Parameter... parameters);

    protected abstract void onPostExecute(Result result);

    @SafeVarargs
    public final AsyncTask<Parameter, Progress, Result> execute(Parameter... parameters) {
        onPreExecute();
        taskThread = new Thread(new Runnable() {
            @Override
            public void run() {
                status = Status.RUNNING;
                final Result result = doInBackground(parameters);
                status = Status.FINISHED;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        onPostExecute(result);
                        PlatformImpl.tkExit(); //TODO - This causes problems, only the first thread executes the onPostExecute method. Change so that all threads execute the onPostExecute method while still terminating when done.
                    }
                });
            }
        });
        taskThread.setName(ASYNCTASK_THREAD_NAME_IDENTIFIER + ASYNCTASK_THREAD_ID);
        ASYNCTASK_THREAD_ID++;
        taskThread.start();
        return this;
    }

    public static void execute(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setName(ASYNCTASK_THREAD_NAME_IDENTIFIER + ASYNCTASK_THREAD_ID);
        ASYNCTASK_THREAD_ID++;
        thread.start();
    }

}
