package com.example.ken.Ktube;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

/*
import android.widget.TextView;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

 */

public class MainActivity extends AppCompatActivity {


    ListView listview;
    com.example.ken.Ktube.Response responseObj;
    CustomAdapter adapter;
    Gson gson;

    private String urlwithapikey;
    private TextView mytxtvw;
    private String dataselected;

public String url(String qtxt) {

    String qtext = qtxt.replaceAll("\\s","+");
    String url="https://www.googleapis.com/youtube/v3/search?part=snippet&q='"+qtext+"'&maxResults=50&type=video&videoCaption=closedCaption&";
    String apikey="AIzaSyDP7AFI9OE2dJM77sB26C4isO1tiCEMlX4";
    String urlwithapikey=url+"key="+apikey;

    StringRequest request = new StringRequest(urlwithapikey, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.d("Results", response);


            String responsestr = new String(response);
            gson = new Gson();
            responseObj = gson.fromJson(responsestr, com.example.ken.Ktube.Response.class);
            adapter = new CustomAdapter(MainActivity.this, responseObj.getItems());
            listview.setAdapter(adapter);
            listview.setOnItemClickListener(onItemClickListener);
          //  mytxtvw = (TextView) findViewById(R.id.txtview);
          //  mytxtvw.setText(response);

        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError response) {
//                Log.e("Results",response.getMessage());
        }
    });
    Volley.newRequestQueue(this).add(request);


    return qtxt;
}
    private TextView vid;

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            String selectedItem = listview.getItemAtPosition(i).toString();
            TextView textview = (TextView) view.findViewById(R.id.videoid);
            String text = textview.getText().toString();

                Intent intent = new Intent(getApplicationContext(), Watch.class);
                intent.putExtra("data", text);
                Log.d("Youtube Video Id : ", text);
                startActivity(intent);
        }

    };



    public void buttonClickFunction(View v, String dataselected)
    {


        Intent intent = new Intent(getApplicationContext(), Watch.class);
        intent.putExtra("data", dataselected);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // adapter.getFilter().filter(newText);
         mytxtvw = (TextView) findViewById(R.id.txtview);
           mytxtvw.setText(newText);
                mytxtvw.setVisibility(View.INVISIBLE);
                url(newText);

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = (ListView) findViewById(R.id.listView);



    }




    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(getApplicationContext(), "WE MADE IT", Toast.LENGTH_LONG).show();

        return false;
    }
}
