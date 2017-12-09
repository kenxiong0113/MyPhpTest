package com.fbee.myphptest;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    EditText name;
    EditText password;
    Button login_button;

    private static String url = "http://120.79.57.174/php/commitpost";
    //private static String url = "http://10.0.2.2/test.php";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name=(EditText)findViewById(R.id.name);
        password=(EditText)findViewById(R.id.password);
        login_button=(Button)findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    new Up().execute();
                }
            }
        });
    }
    private boolean validate()
    {
        String name1 = name.getText().toString(). trim();
        if (name1.equals(""))
        {
            DialogUtil.showDialog(this, "输入用户名", false);
            return false;
        }
        String password1 = password.getText().toString().trim();
        if (password1.equals(""))
        {
            DialogUtil.showDialog(this, "输入密码", false);
            return false;
        }

        return true;
    }

    class Up extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("正在上传..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... args) {
            String name1 = name.getText().toString();
            String password1 = password.getText().toString();
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", name1));
            params.add(new BasicNameValuePair("password", password1));
            try{
                JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);
                String message = json.getString(TAG_MESSAGE);
                return message;
            }catch(Exception e){
                e.printStackTrace();
                return "";
            }
        }
        @Override
        protected void onPostExecute(String message) {
            pDialog.dismiss();
            //message 为接收doInbackground的返回值
            Toast.makeText(getApplicationContext(), message+"是什么", Toast.LENGTH_LONG).show();
        }
    }

    public static final String removeBOM(String data) {

        if (TextUtils.isEmpty(data)) {

            return data;

        }


        if (data.startsWith("\ufeff")) {

            return data.substring(1);

        } else {

            return data;

        }

    }

}