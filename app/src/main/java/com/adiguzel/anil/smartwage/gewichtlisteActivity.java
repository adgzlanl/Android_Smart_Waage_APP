package com.adiguzel.anil.smartwage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class gewichtlisteActivity extends AppCompatActivity {

    private static final String URL_DATA="http://gewichtsdatenbank.herokuapp.com/lesen";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<liste> listItems;
    private Session session;
    String email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gewichtliste);


        recyclerView =
                (RecyclerView) findViewById(R.id.recycler_view);
        session=new Session(this);
        recyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        listItems=new ArrayList<>();
        loadRecyclerViewData();
    }




    private void loadRecyclerViewData()
    {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading data.....");
        progressDialog.show();

        SharedPreferences shared = getSharedPreferences("MyPref", 0);
        String SharedPreferencesEmail = (shared.getString("Useremail", ""));
        String SharedPreferencesPassword = (shared.getString("Userpassword", ""));
        email=SharedPreferencesEmail;
        password=SharedPreferencesPassword;

        // POST parameters
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password",password);
        JSONObject jsonObj = new JSONObject(params);



        JsonObjectRequest jsonObjRequest = new JsonObjectRequest
                (Request.Method.POST, URL_DATA, jsonObj, new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        progressDialog.dismiss();

                        try {


                            JSONArray array=response.getJSONArray("Gereatsinfo");

                            for (int i=0;i<array.length();i++)
                            {
                                JSONObject o =array.getJSONObject(i);

                                liste item=new liste(o.getString("Thema"),o.getString("Nahricht")+" kg",o.getString("Datum"),o.getString("GereatID"));

                                listItems.add(item);
                            }

                            adapter=new gewichtlisteadapter(listItems,getApplicationContext());
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        if(error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(getApplicationContext(),"Check your internet connection",Toast.LENGTH_SHORT).show();

                            // ...
                        }
                    }
                });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonObjRequest);
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

}


