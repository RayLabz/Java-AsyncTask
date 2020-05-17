package main.java.com.raylabz.javaasynctask;

import com.sun.javafx.application.PlatformImpl;
import javafx.application.Platform;

/**
 * Implements the Android SDK AsyncTask for Java FX applications, as found in
 * https://developer.android.com/reference/android/os/AsyncTask.
 *
 * Important note:
 * This package is destined for use in Java FX applications ONLY - Other Java applications are NOT supported.
 *
 * Created by RayLabz - 2019
 * @version 1.1.0
 * Visit http://www.RayLabz.com
 *
 * Java AsyncTask - A port of Android SDK's AsyncTask for plain Java.
 * Perform background operations and publish results on the UI thread without working with threads and handlers.
 * Repository: https://github.com/RayLabz/Java-AsyncTask
 * Guide: https://RayLabz.github.io/Java-AsyncTask
 */

/**
 * Enables running background operations and publishing their results on the main (UI) thread.
 * @param <Parameter> The type of parameters.
 * @param <Progress> The type of progress updates.
 * @param <Result> The type of result.
 */
public abstract class AsyncTask <Parameter, Progress, Result> {

    /**
     * Indicates the status of an {@link AsyncTask}.
     */
    private enum Status {
        FINISHED,
        PENDING,
        RUNNING
    }

    /**
     * Used to identify a AsyncTask thread. Mainly used for debugging purposes.
     */
    private static final String ASYNCTASK_THREAD_NAME_IDENTIFIER = AsyncTask.class.getName() + "-Thread";

    /**
     * Identifies an AsyncTask thread using an ID.
     */
    private static long ASYNCTASK_THREAD_NEXT_ID = 0;

    /**
     * Models the AsyncTask status.
     */
    private Status status;

    /**
     * The task's thread.
     */
    private Thread taskThread;

    /**
     * Creates an {@link AsyncTask}.
     */
    protected AsyncTask() {
        PlatformImpl.startup(() -> { /*Do nothing*/ });
        ASYNCTASK_THREAD_NEXT_ID++;
        status = Status.PENDING;
    }

    /**
     * Retrieves the {@link AsyncTask.Status} of an {@link AsyncTask}.
     * @return Status
     */
    public final Status getStatus() {
        return status;
    }

    /**
     * Used to publish the current progress of an {@link AsyncTask} from within the doInBackground() method.
     * @param progresses An array of progress variables.
     */
    protected final void publishProgress(Progress... progresses) {
        onProgressUpdate(progresses);
    }

    /**
     * Executes when the publishProgress() method is invoked and carries an action with the given Progress data.
     * @param progresses An array of progresses, as provided in publishProgress()
     */
    protected void onProgressUpdate(Progress... progresses) {}

    /**
     * Executes a set of commands, on the UI thread, prior to doInBackground().
     */
    protected abstract void onPreExecute();

    /**
     * Executes a set of commands, on a non-UI thread.
     * @param parameters A set of parameters.
     * @return A result type, to be used on the UI thread.
     */
    protected abstract Result doInBackground(Parameter... parameters);

    /**
     * Executes a set of commands after doInBackground(), using the method's result.
     * @param result The result provided by doInBackground().
     */
    protected abstract void onPostExecute(Result result);

    /**
     * Creates an {@link AsyncTask with the given types of Parameter, Progress and Result.}
     * @param parameters A set of parameters for use by the AsyncTask.
     * @return Returns itself.
     */
    @SafeVarargs
    public final AsyncTask<Parameter, Progress, Result> execute(Parameter... parameters) {
        onPreExecute();
        taskThread = new Thread(new Runnable() {
            @Override
            public void run() {
                status = Status.RUNNING;
                final Result result = doInBackground(parameters);
                status = Status.FINISHED;
                Platform.runLater(() -> onPostExecute(result));
            }
        });
        taskThread.setName(ASYNCTASK_THREAD_NAME_IDENTIFIER + ASYNCTASK_THREAD_NEXT_ID);
        ASYNCTASK_THREAD_NEXT_ID++;
        taskThread.start();
        return this;
    }

    /**
     * Alternative AsyncTask method for easy execution of non-result based tasks - e.g. Runnables.
     * @param runnable The runnable object to execute.
     */
    public static void execute(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setName(ASYNCTASK_THREAD_NAME_IDENTIFIER + ASYNCTASK_THREAD_NEXT_ID);
        ASYNCTASK_THREAD_NEXT_ID++;
        thread.start();
    }

}
