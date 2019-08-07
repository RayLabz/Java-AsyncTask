import com.panickapps.javaasynctask.AsyncTask;

public class MyAsyncTask extends AsyncTask<Void, Void, Void> {

    private final String name;

    public MyAsyncTask(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    protected void onPreExecute() {
        System.out.println("[" + name + "] -> " + "OnPreExecute");
    }

    @Override
    protected Void doInBackground(Void... voids) {
        for (long i = 0; i < 15; i++) {
            System.out.println("[" + name + "] -> " + i);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        System.out.println("[" + name + "] -> " + "OnPostExecute");
    }

}