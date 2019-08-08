import com.panickapps.javaasynctask.AsyncTask;

import java.io.FileWriter;

/**
 * This task is a simple example on how to implement an AsyncTask.
 * It prints simple messages and writes data to files asynchronously.
 */
public class MyAsyncTask extends AsyncTask<Void, Integer, Void> {

    private final String name;
    private final StringBuilder stringBuilder = new StringBuilder();

    public MyAsyncTask(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    protected void onPreExecute() {
        stringBuilder.append("[" + name + "] -> " + "OnPreExecute" + System.lineSeparator());
    }

    @Override
    protected Void doInBackground(Void... voids) {
        for (long i = 0; i < 1000000; i++) {
            stringBuilder.append("[" + name + "] -> " + i + System.lineSeparator());
            if (i % 100000 == 0) {
                publishProgress((int) i);
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... integers) {
        super.onProgressUpdate(integers);
        System.out.println("[" + name + "] -> " + integers[0] + System.lineSeparator());
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        stringBuilder.append("[" + name + "] -> " + "OnPostExecute" + System.lineSeparator());

        //Also write it to file:
        try {
            FileWriter fw = new FileWriter(System.currentTimeMillis() + "-" + name + ".txt");
            fw.write(stringBuilder.toString());
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

}