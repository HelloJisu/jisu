package com.reziena.user.reziena_1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class Signin2Activity extends AppCompatActivity {
    TextView okay;
    private EditText name;
    private String email;
    String password;
    RadioGroup gender;
    RadioButton genderresult;
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;
    LinearLayout signin;
    private Uri mImageCaptureUri;
    private int id_view;
    private String absolutePath;//
    CircleImageView profile;
    String month, year, genderstring, countrystring, day;
    public static Activity skinhistoryactivity;
    HomeActivity homeactivity = (HomeActivity)HomeActivity.homeactivity;
    String namestring, idstring, profileurl;
    private static final String DEFAULT_LOCAL = "Portugal";
    public int yearint, dayint, monthint;

    private String IP_Address = "52.32.36.182";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin2);
        skinhistoryactivity = Signin2Activity.this;

        Intent subintent = getIntent();

        namestring = subintent.getExtras().getString("name");
        idstring = subintent.getExtras().getString("id");
        profileurl = subintent.getExtras().getString("profile");
        password = subintent.getExtras().getString("password");

        name = findViewById(R.id.name);
        profile = findViewById(R.id.signinprofile);
        signin = findViewById(R.id.signin_signin2);

        Spinner birthday_year = findViewById(R.id.birthday_year);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        ArrayList<Integer> yearAdapter = new ArrayList<>();
        for(int i=0;i<100;i++){
            yearAdapter.add(year--);
        }
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item,yearAdapter);
        birthday_year.setAdapter(arrayAdapter);

        Spinner birthday_month = findViewById(R.id.birthday_month);
        ArrayAdapter monthAdapter = ArrayAdapter.createFromResource(this,
                R.array.birthday_month, android.R.layout.simple_spinner_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        birthday_month.setAdapter(monthAdapter);

        Spinner birthday_day = findViewById(R.id.birthday_day);
        ArrayList<Integer> birthAdapter = new ArrayList<>();
        for(int i=1;i<=31;i++){
            birthAdapter.add(i);
        }
        ArrayAdapter<Integer> birthdayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item,birthAdapter);
        birthday_day.setAdapter(birthdayAdapter);

        Spinner couuntry = findViewById(R.id.country);
        ArrayAdapter countryarray = ArrayAdapter.createFromResource(this,
                R.array.country, android.R.layout.simple_spinner_item);
        countryarray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        couuntry.setAdapter(countryarray);

        Spinner gender = findViewById(R.id.gender);
        ArrayAdapter genderAdapter = ArrayAdapter.createFromResource(this,
                R.array.gender, android.R.layout.simple_spinner_item);
        countryarray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(genderAdapter);


        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                genderstring = gender.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        birthday_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yearint = Integer.parseInt(String.valueOf(birthday_year.getItemAtPosition(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        birthday_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                monthint = Integer.parseInt(String.valueOf(birthday_month.getItemAtPosition(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        birthday_day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dayint = Integer.parseInt(String.valueOf(birthday_day.getItemAtPosition(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        couuntry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                countrystring = String.valueOf(couuntry.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        if(namestring!=null){
            name.setText(namestring);
        }
        if(profile!=null){
            Glide.with(this).load(profileurl).into(profile);
        }

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.signin:
                        String saveName = name.getText().toString();
                        String birth = yearint+"/"+monthint+"/"+dayint;

                        setData task = new setData();
                        task.execute("http://"+R.string.IP_Address+"/saveUser.php", idstring, password, saveName, genderstring, birth, profileurl, countrystring);

                        break;
                    case R.id.signinprofile:
                        doTakeAlbumAction();
                        break;
                }
            }
        };
        signin.setOnClickListener(onClickListener);
        profile.setOnClickListener(onClickListener);
    }

    class setData extends AsyncTask<String, Void, String> {
        String id, pw, name, gender, birth, profile, country;

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Log.e("sign-onPostExecute", "response - " + result);

            if (result == null){
                Log.e("onPostExecute", "erre");
            }
            else {
                settings(result);
            }
        }

        @Override
        protected String doInBackground(String... params) {
            String serverURL = params[0];

            id = params[1];
            pw = params[2];
            name = params[3];
            gender = params[4];
            birth = params[5];
            profile = params[6];
            country = params[7];

            String postParameters = "id="+id+"&pw="+pw+"&name="+name+"&gender="+gender+"&birth="+birth+"&profile="+profile+"&country="+country;
            Log.e("sign-postParameters", postParameters);

            try {
                URL url = new URL(serverURL);

                HttpURLConnection httpURLConnection= (HttpURLConnection)url.openConnection();
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);;

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                // response
                InputStream inputStream;
                int responseStatusCode = httpURLConnection.getResponseCode();
                String responseStatusMessage = httpURLConnection.getResponseMessage();
                Log.e("sign-response", "POST response Code - " + responseStatusCode);
                Log.e("sign-response", "POST response Message - "+ responseStatusMessage);

                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    // 정상적인 응답 데이터
                    Log.e("sign-inputstream: ", "정상적");
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    // error
                    Log.e("sign-inputstream: ", "비정상적: " + httpURLConnection.getErrorStream());
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
                bufferedReader.close();
                return sb.toString().trim();

            } catch (Exception e) {
                Log.e("sign-ERROR", "InsertDataError ", e);
            }
            return null;

        }

        private void settings(String result){
            if (result.contains("Error")) {
                Log.e("Error", "you have Error");
            } else if (result.contains("1 record added")){
                SharedPreferences sp_userName = getSharedPreferences("userName", MODE_PRIVATE);
                SharedPreferences sp_userID = getSharedPreferences("userID", MODE_PRIVATE);
                SharedPreferences.Editor editor1 = sp_userName.edit();
                SharedPreferences.Editor editor2 = sp_userID.edit();
                editor1.putString("userName", name);
                editor2.putString("userID", id);
                editor1.commit();
                editor2.commit();
                Log.e("Login ", name+"님 로그인");
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("name","skintypedialog");
                startActivity(intent);
                finish();
            }
        }
    }

    public void doTakeAlbumAction(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }


    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode!=RESULT_OK)
            return;

        switch(requestCode)
        {
            case PICK_FROM_ALBUM: {
                mImageCaptureUri = data.getData();
                Log.d("SmartWheel", mImageCaptureUri.getPath().toString());
            }
            case PICK_FROM_CAMERA:{
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri,"image/");

                intent.putExtra("outputX",200);
                intent.putExtra("outputY",200);
                intent.putExtra("aspectX",1);
                intent.putExtra("aspectY",1);
                intent.putExtra("scale",true);
                intent.putExtra("return-data",true);
                startActivityForResult(intent,CROP_FROM_IMAGE);
                break;
            }
            case CROP_FROM_IMAGE:{
                if(resultCode!=RESULT_OK){
                    return;
                }
                final Bundle extras = data.getExtras();

                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+
                        "/SmartWheel"+System.currentTimeMillis()+".jpg";

                if(extras!=null){
                    Bitmap photo = extras.getParcelable("data");
                    //Glide.with(this).load(filePath).bitmapTransform(new CropCircleTransformation(new CustomBitmapPool())).into(image);

                    profile.setImageBitmap(photo);

                    storeCropImage(photo,filePath);
                    absolutePath = filePath;
                    break;
                }
                File f = new File(mImageCaptureUri.getPath());
                if(f.exists()){
                    f.delete();
                }
            }
        }
    }
    private void storeCropImage(Bitmap bitmap, String filePath){
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/SmartWheel";
        File directory_smartWheel = new File(dirPath);

        if(!directory_smartWheel.exists()){
            directory_smartWheel.mkdir();
        }

        File copyFile = new File(filePath);
        BufferedOutputStream out = null;

        try{
            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100,out);

            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(copyFile)));

            out.flush();
            out.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}