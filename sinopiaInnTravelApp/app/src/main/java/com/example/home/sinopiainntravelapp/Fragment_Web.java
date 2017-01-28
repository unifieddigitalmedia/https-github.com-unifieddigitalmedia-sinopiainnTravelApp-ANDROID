package com.example.home.sinopiainntravelapp;


import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

import static android.R.attr.id;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Web extends Fragment {

    WebView webView;

    String request_token;

    AsyncHttpClient client;

    private static final String TOKENURL ="https://api.instagram.com/oauth/access_token";

    public static String tokenURLString = null;

    public static final String APIURL = "https://api.instagram.com/v1";

    public static String CALLBACKURL = "http://www.sinopiainn.com";

    SharedPreferences settings;

    String urlString;

    public Fragment_Web() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment__web, container, false);

        settings = getActivity().getSharedPreferences("checkinToken", 0);

        webView = (WebView) rootView.findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new AuthWebViewClient());

        webView.loadUrl(getArguments().getString("url"));



        return rootView;

    }

    private class AuthWebViewClient extends WebViewClient {

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request)
        {


          if (request.getUrl().toString().startsWith(CALLBACKURL))


            {

                Log.i("CALLBACKURL",request.getUrl().toString());

                String parts[] = request.getUrl().toString().split("=");

                request_token = parts[1];  //This is your request token.

                //getActivity().dismiss();

                //tokenURLString = TOKENURL + "?client_id=" + getString(R.string.Client_ID) + "&client_secret=" +getString(R.string.Client_Secret)+ "&redirect_uri=" + CALLBACKURL + "&grant_type=authorization_code&code=" + request_token;

                tokenURLString = TOKENURL + "?client_id=" + getString(R.string.Client_ID) + "&client_secret=" +getString(R.string.Client_Secret)+ "&redirect_uri=" + CALLBACKURL + "&grant_type=authorization_code";


                Log.i("tokenURLString",tokenURLString);


                RequestParams params = new RequestParams();



                params.put("client_id", getString(R.string.Client_ID));
                params.put("client_secret",getString(R.string.Client_Secret));
                params.put("redirect_uri",CALLBACKURL);
                params.put("grant_type","authorization_code");
                params.put("code",parts[1]);



                client = new AsyncHttpClient();

                client.setConnectTimeout(20000);


                client.post(TOKENURL, params , new JsonHttpResponseHandler() {

                    @Override
                    public void onStart() {


                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject json) {

                        Log.i("json",json.toString());

                        SharedPreferences.Editor editor = settings.edit();
                        try {
                            editor.putString("accessTokenString", json.getString("access_token"));

                            editor.putString("id", json.getJSONObject("user").getString("id"));
                            editor.putString("username", json.getJSONObject("user").getString("username"));
                            editor.commit();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        urlString = APIURL + "/users/"+  settings.getString("id","")  +"/media/recent/?access_token=" +  settings.getString("accessTokenString","");

                        Log.i("urlString",urlString);



                            new DownloadFilesTask().execute(tokenURLString);



                            /*
*/
                            //JSONObject mainImageJsonObject =  	jsonArray.getJSONObject(0).getJSONObject("images").getJSONObject("low_resolution");

                            //String imageUrlString = mainImageJsonObject.getString("url");



                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject e)  {


                        Log.i("json",e.toString());

                    }

                    @Override
                    public void onRetry(int retryNo) {


                    }

                });

                return true;
            }

            return false;

        }


    }


    private class DownloadFilesTask extends AsyncTask<String, Void,  ArrayList<String>> {




        @Override
        protected  ArrayList<String> doInBackground(String... strings) {

              String response = null;
            ArrayList<String> stringArray = null;


                try {
                    URL url = new URL(urlString);
                   /* HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                    httpsURLConnection.setRequestMethod("GET");
                    httpsURLConnection.setDoInput(true);
                    httpsURLConnection.setDoOutput(true);*/

                    InputStream inputStream = url.openConnection().getInputStream();

                    response = streamToString(inputStream);

                    JSONObject jsonObject = (JSONObject) new JSONTokener(response).nextValue();




                    stringArray = new ArrayList<String>();

                    JSONArray jsonArray = jsonObject.getJSONArray("data");


                    for(int i = 0, count = jsonArray.length(); i< count; i++)

                    {
                        try {

                            jsonObject = jsonArray.getJSONObject(i);


                            stringArray.add(jsonObject.getJSONObject("images").getJSONObject("low_resolution").getString("url"));
                        }
                        catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                    Log.i("mainImageJsonObject",jsonArray.toString());


                    Log.i("RESPONSE", response);


                } catch (Exception e) {
                    e.printStackTrace();
                }



            return stringArray;

        }

        protected void onPostExecute(ArrayList<String> result) {

            Log.i("ARRAY LIST", String.valueOf(result));

            Fragment_Photos new_fragment = new Fragment_Photos();

            Bundle bundle1 = new Bundle();

            bundle1.putInt("Menu", 24);

            bundle1.putStringArrayList("Image Item",result);

            bundle1.putBoolean("fromGridview",false);

            new_fragment.setArguments(bundle1);

            ((Activity_CheckIn) getActivity()).homePageFadeTransition(new_fragment,"");


        }

    }



    public static String streamToString(InputStream p_is)
    {
        try
        {
            BufferedReader m_br;
            StringBuffer m_outString = new StringBuffer();
            m_br = new BufferedReader(new InputStreamReader(p_is));
            String m_read = m_br.readLine();
            while(m_read != null)
            {
                m_outString.append(m_read);
                m_read =m_br.readLine();
            }
            return m_outString.toString();
        }
        catch (Exception p_ex)
        {
            p_ex.printStackTrace();
            return "";
        }
    }
}
