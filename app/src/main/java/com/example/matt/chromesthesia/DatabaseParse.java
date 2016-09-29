package com.example.matt.chromesthesia;

/**
 * Created by Isabelle on 9/29/2016.
 */

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Code sample from:
 * http://www.geeks.gallery/retrieve-data-from-mysql-database-using-php-and-displaying-it-by-tableview-in-android/
 * Courtesy of Nithya Govind, Android Developer at Energy Alternatives India
 * Will analyze and adjust code to fit the needs of Chromesthesia, taking information from a database via PHP
 * and parsing it.
 *
 *  UPDATE 9/29/16 4:35AM: apache httpcomponents are deprecated. I went through the code and updated
 *  certain areas where these components were used.
 */

    public class DatabaseParse extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		/* Button button = (Button) findViewById(R.id.button1);
button.setOnClickListener(new View.OnClickListener()
{
@SuppressWarnings(“deprecation”)
public void onClick(View view)
{*/
        String result = null;
        InputStream is = null;
        try {

            URL url = new URL("");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "");
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.connect();
            is = connection.getInputStream();


            Log.e("log_tag", "connection success");
            //   Toast.makeText(getApplicationContext(), “pass”, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("log_tag", "Error in http connection" + e.toString());
            Toast.makeText(getApplicationContext(), "Connection fail", Toast.LENGTH_SHORT).show();

        }
        //convert response to string
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
                //  Toast.makeText(getApplicationContext(), “Input Reading pass”, Toast.LENGTH_SHORT).show();
            }
            is.close();

            result = sb.toString();
        } catch (Exception e) {
            Log.e("log_tag", "Error converting result" + e.toString());
            Toast.makeText(getApplicationContext(), "Input reading fail", Toast.LENGTH_SHORT).show();

        }

        //parse json data
        try {
            JSONArray jArray = new JSONArray(result);
            TableLayout tv = (TableLayout) findViewById(R.id.table);
            tv.removeAllViewsInLayout();
            int flag = 1;
            for (int i = -1; i < jArray.length() - 1; i++) {
                TableRow tr = new TableRow(DatabaseParse.this);
                tr.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                if (flag == 1) {
                    TextView b6 = new TextView(DatabaseParse.this);
                    b6.setText("Id");
                    b6.setTextColor(Color.BLUE);
                    b6.setTextSize(15);
                    tr.addView(b6);
                    TextView b19 = new TextView(DatabaseParse.this);
                    b19.setPadding(10, 0, 0, 0);
                    b19.setTextSize(15);
                    b19.setText("Name");
                    b19.setTextColor(Color.BLUE);
                    tr.addView(b19);
                    TextView b29 = new TextView(DatabaseParse.this);
                    b29.setPadding(10, 0, 0, 0);
                    b29.setText("Status");
                    b29.setTextColor(Color.BLUE);
                    b29.setTextSize(15);
                    tr.addView(b29);
                    tv.addView(tr);
                    final View vline = new View(DatabaseParse.this);
                    vline.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 2));
                    vline.setBackgroundColor(Color.BLUE);
                    tv.addView(vline);
                    flag = 0;
                } else {
                    JSONObject json_data = jArray.getJSONObject(i);
                    Log.i("log_tag", "id: " + json_data.getInt("Id") + ", Username: " + json_data.getString("username") + ", No: " + json_data.getString("comment"));
                    TextView b = new TextView(DatabaseParse.this);
                    String stime = String.valueOf(json_data.getInt("Id"));
                    b.setText(stime);
                    b.setTextColor(Color.RED);
                    b.setTextSize(15);
                    tr.addView(b);
                    TextView b1 = new TextView(DatabaseParse.this);
                    b1.setPadding(10, 0, 0, 0);
                    b1.setTextSize(15);
                    String stime1 = json_data.getString("username");
                    b1.setText(stime1);
                    b1.setTextColor(Color.BLACK);
                    tr.addView(b1);
                    TextView b2 = new TextView(DatabaseParse.this);
                    b2.setPadding(10, 0, 0, 0);
                    String stime2 = json_data.getString("comment");
                    b2.setText(stime2);
                    b2.setTextColor(Color.BLACK);
                    b2.setTextSize(15);
                    tr.addView(b2);
                    tv.addView(tr);
                    final View vline1 = new View(DatabaseParse.this);
                    vline1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
                    vline1.setBackgroundColor(Color.WHITE);
                    tv.addView(vline1);
                }
            }
        } catch (JSONException e) {
            Log.e("log_tag", "Error parsing data" + e.toString());
            Toast.makeText(getApplicationContext(), "JsonArray fail", Toast.LENGTH_SHORT).show();
        }

    }

}