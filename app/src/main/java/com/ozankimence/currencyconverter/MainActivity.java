package com.ozankimence.currencyconverter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    EditText ed1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = (TextView) findViewById(R.id.t1);
        textView2 = (TextView) findViewById(R.id.t2);
        textView3 = (TextView) findViewById(R.id.t3);
        textView4 = (TextView) findViewById(R.id.t4);
        ed1 = (EditText) findViewById(R.id.editText);




    }
    public void tıkla(View view) {

        DownloadData downloadData = new DownloadData();

        try {

            String url = "http://api.fixer.io/latest?base=";
            String chosenBase = ed1.getText().toString();

            downloadData.execute(url+chosenBase);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class DownloadData extends AsyncTask<String,Void,String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                JSONObject jsonObject = new JSONObject(s);
                String base = jsonObject.getString("base");
                System.out.println(base);
                String date = jsonObject.getString("date");
                System.out.println(date);
                String rates = jsonObject.getString("rates");
                System.out.println(rates);

                JSONObject jsonObject1 = new JSONObject(rates);
                String chf = jsonObject1.getString("CHF");
                System.out.println(chf);
                String czk = jsonObject1.getString("CZK");
                System.out.println(czk);
                String tl = jsonObject1.getString("TRY");

                textView1.setText("CHF: "+chf);
                textView2.setText("CZK: "+czk);
                textView3.setText("TRY: "+tl);


            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            URL url;
            HttpURLConnection httpURLConnection;

            try {

                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int data = inputStreamReader.read();

                while (data > 0) {

                    char character = (char) data;
                    result += character;

                    data = inputStreamReader.read();
                }

                return result;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }
    }
}
