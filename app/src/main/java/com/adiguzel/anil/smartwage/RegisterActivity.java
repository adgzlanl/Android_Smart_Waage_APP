package com.adiguzel.anil.smartwage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends AppCompatActivity  {

    private static final String URL_DATA="http://gewichtsdatenbank.herokuapp.com/registieren";
    Session session;
    EditText editEmailAddress;
    EditText editPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editEmailAddress=(EditText)findViewById(R.id.email);
        editPassword=(EditText)findViewById(R.id.password);
        session=new Session(this);


    }


    public void RegisterButton(View v)

    {

        if(!emailValidate(editEmailAddress.getText().toString()))
        {
            editEmailAddress.setError(getResources().getString(R.string.error_invalid_email));
            editEmailAddress.requestFocus();
        }
        else if(isEmpty(editPassword))
        {
            editPassword.setError(getResources().getString(R.string.error_invalid_password));
            editPassword.requestFocus();
        }
        else

        {
            RegisterRequest();

        }


    }


    protected boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }

    protected boolean emailValidate(String email)
    {
        String emailPatern="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern=Pattern.compile(emailPatern);
        Matcher matcher= pattern.matcher(email);
        return matcher.matches();
    }





    private void RegisterRequest()

    {
        final String email=editEmailAddress.getText().toString();
        final String password=editPassword.getText().toString();


        // POST parameters
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password",password);
        JSONObject jsonObj = new JSONObject(params);

// Request a json response from the provided URL
        JsonObjectRequest jsonObjRequest = new JsonObjectRequest
                (Request.Method.POST, URL_DATA, jsonObj, new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {


                        try {


                            String message = response.getString("success");
                            Log.d("message",message);

                            if(message=="true")

                            {
                                session.setLoggedin(true);
                                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("Useremail", email);
                                editor.putString("Userpassword",password);
                                editor.commit();
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            }




                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {


                                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                            }
                        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjRequest);


        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


    }

}

