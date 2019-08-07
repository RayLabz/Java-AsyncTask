import com.panickapps.javaasynctask.AsyncTask;

import java.util.ArrayList;



public class Test {



    public static void main(String[] args) {

        ArrayList<MyAsyncTask> asyncTaskArrayList = new ArrayList<>();
        asyncTaskArrayList.add(new MyAsyncTask("Alpha"));
        asyncTaskArrayList.add(new MyAsyncTask("Bravo"));

        for (MyAsyncTask a : asyncTaskArrayList) {
            a.execute();
        }

    }

}
