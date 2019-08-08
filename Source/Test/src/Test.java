import java.util.ArrayList;


/**
 * This example instantiates and then executes a number of sample {@link MyAsyncTask} tasks.
 */
public class Test {

    public static void main(String[] args) {

        ArrayList<MyAsyncTask> asyncTaskArrayList = new ArrayList<>();
        final int NUM_OF_TASKS = 10; //Change the number of tasks here.

        //Create tasks:
        for (int i = 0; i < NUM_OF_TASKS; i++) {
            asyncTaskArrayList.add(new MyAsyncTask("Task-" + i));
        }

        //Run the tasks:
        for (MyAsyncTask a : asyncTaskArrayList) {
            a.execute();
        }

    }

}
