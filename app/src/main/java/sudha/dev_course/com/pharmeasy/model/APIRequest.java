package sudha.dev_course.com.pharmeasy.model;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import sudha.dev_course.com.pharmeasy.R;

/**
 * Created by sudharshi on 16/4/16.
 */
public class APIRequest
{
    private static final String BASE_URL = "https://api.stackexchange.com/2.2/";
    private static final String SEARCH_END_URL = "search/advanced";
    private static Context mContext;


    public interface ResponseHandler
    {
        public void onResponse(JSONObject response);
    }

    public static void fetchQuestions(Context context, String keyword, ResponseHandler handler)
    {
        mContext = context;
        Uri uri = Uri.parse(BASE_URL + SEARCH_END_URL).buildUpon()
                .appendQueryParameter("order", "desc")
                .appendQueryParameter("sort", "activity")
                .appendQueryParameter("accepted", "False")
                .appendQueryParameter("answers", "0")
                .appendQueryParameter("tagged", keyword)
                .appendQueryParameter("site", "stackoverflow")
                .build();

        APIFetchTask fetchTask = new APIFetchTask(handler);
        Log.d("API", uri.toString());
        fetchTask.execute(uri.toString());
    }

    private static class APIFetchTask extends AsyncTask<String, Void, String>
    {
        private ResponseHandler mResponseHandler;

        public APIFetchTask(ResponseHandler handler) {
            mResponseHandler = handler;
        }

        @Override
        protected String doInBackground(String... params)
        {
            BufferedReader bufferedReader = null;
            HttpURLConnection urlConnection = null;
            StringBuffer buffer = new StringBuffer();
            String line = null;

            try
            {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();

                if (inputStream == null)
                {
                    buffer = null;
                }
                InputStreamReader reader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(reader);

                while ((line = bufferedReader.readLine()) != null) {
                    buffer.append(line);
                    buffer.append("\n");
                }

            } catch (IOException e) {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                e.printStackTrace();
            }
            finally
            {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (bufferedReader != null)
                {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return buffer.toString();
        }

        @Override
        protected void onPostExecute(String result)
        {
            if (result != null)
            {
                try
                {
                    JSONObject response = new JSONObject(result);
                    if (mResponseHandler != null)
                    {
                        mResponseHandler.onResponse(response);
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
            super.onPostExecute(result);
        }
    }
}