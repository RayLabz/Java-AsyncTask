import main.java.com.raylabz.javaasynctask.AsyncTask;

import javax.swing.*;

public class Test {

    public static class AsyncTask1 extends AsyncTask<Void, Void, Integer> {
        @Override
        protected void onPreExecute() {
            System.out.println("AsyncTask1 -> onPreExecute()");
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            System.out.println("AsyncTask1 -> doInBackground()");
            return 1;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            System.out.println("AsyncTask1 -> onPostExecute() " + integer);
        }
    }

    public static class AsyncTask2 extends AsyncTask<Void, Void, Integer> {
        @Override
        protected void onPreExecute() {
            System.out.println("AsyncTask2 -> onPreExecute()");
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            System.out.println("AsyncTask2 -> doInBackground()");
            return 2;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            System.out.println("AsyncTask2 -> onPostExecute() " + integer);
        }
    }

    public static void main(String[] args) {
        new AsyncTask1().execute();
//        final JFrame jFrame = new JFrame();
//        jFrame.setVisible(true);
        new AsyncTask2().execute();

        System.out.println();
    }



}
