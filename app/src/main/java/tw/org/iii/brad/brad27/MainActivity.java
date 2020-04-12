package tw.org.iii.brad.brad27;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView mesg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mesg = findViewById(R.id.mesg);
    }

    private MyAsyncTask myAsyncTask;
    public void test1(View view) {
        mesg.setText("");
        myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute("Brad", "Kevin","Mary");
    }

    public void test2(View view) {
        if (myAsyncTask != null){
            Log.v("brad", "status: " + myAsyncTask.getStatus().name());
            if (!myAsyncTask.isCancelled()){
                myAsyncTask.cancel(true);
            }
        }
    }

    private class MyAsyncTask extends AsyncTask<String, Object, String>{
        private int total, i;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.v("brad", "onPreExecute");
            mesg.append("Start...\n");
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.v("brad", "onPostExecute : " + result);
            mesg.append(result);
        }

        @Override
        protected void onProgressUpdate(Object... values) {
            super.onProgressUpdate(values);
            Log.v("brad", "onProgressUpdate:" + values[0]);
            mesg.append(values[1] + " -> " + values[0] + "%\n");
        }

        @Override
        protected void onCancelled(String result) {
            super.onCancelled(result);
            Log.v("brad", "onCancelled1:" + result);
            mesg.append(result);

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.v("brad", "onCancelled2");
        }

        @Override
        protected String doInBackground(String... names) {
            Log.v("brad", "doInBackground");
            total = names.length;
            for (String name : names){
                i++;
                try {
                    Thread.sleep(3 * 1000);
                }catch (Exception e){}
                Log.v("brad", name);

                //mesg.setText(name + "\n");
                publishProgress((int)Math.ceil(i*1.0/total*100), name);
            }
            if (isCancelled()){
                return "Cancel";
            }else{
                return "Game Over";
            }
        }
    }


}
