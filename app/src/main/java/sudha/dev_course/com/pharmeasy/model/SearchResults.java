package sudha.dev_course.com.pharmeasy.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import sudha.dev_course.com.pharmeasy.ui.MainActivity;

/**
 * Created by sudharshi on 16/4/16.
 */
public class SearchResults implements Parcelable
{
    private ArrayList<Question> mQuestions;
    private boolean mHasMore;
    private int mQuotaMax;
    private int mQuotaRemaining;
    
    public interface ParseHandler
    {
        public void onParse(SearchResults results);
    }

    public SearchResults()
    {

    }
    protected SearchResults(Parcel in) {
        mQuestions = in.createTypedArrayList(Question.CREATOR);
        mHasMore = in.readByte() != 0;
        mQuotaMax = in.readInt();
        mQuotaRemaining = in.readInt();
    }

    public static final Creator<SearchResults> CREATOR = new Creator<SearchResults>() {
        @Override
        public SearchResults createFromParcel(Parcel in) {
            return new SearchResults(in);
        }

        @Override
        public SearchResults[] newArray(int size) {
            return new SearchResults[size];
        }
    };

    public ArrayList<Question> getmQuestions() {
        return mQuestions;
    }

    public void setQuestions(ArrayList<Question> mQuestions) {
        this.mQuestions = mQuestions;
    }

    public boolean isHasMore() {
        return mHasMore;
    }

    public void setHasMore(boolean mHasMore) {
        this.mHasMore = mHasMore;
    }

    public int getQuotaMax() {
        return mQuotaMax;
    }

    public void setQuotaMax(int mQuotaMax) {
        this.mQuotaMax = mQuotaMax;
    }

    public int getQuotaRemaining() {
        return mQuotaRemaining;
    }

    public void setQuotaRemaining(int mQuotaRemaining) {
        this.mQuotaRemaining = mQuotaRemaining;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mQuestions);
        dest.writeByte((byte) (mHasMore ? 1 : 0));
        dest.writeInt(mQuotaMax);
        dest.writeInt(mQuotaRemaining);
    }
    
    public void fetchQuestions(Context context, String keyword, final ParseHandler handler)
    {
        APIRequest.fetchQuestions(context, keyword, new APIRequest.ResponseHandler() {
            @Override
            public void onResponse(JSONObject response)
            {
                if (response != null)
                {
                    Log.d("API", response.toString());

                    SearchResults results = new SearchResults();
                    try 
                    {
                        JSONArray jsonQuestion = response.getJSONArray("items");
                        ArrayList<Question> Questions = new ArrayList<Question>();
                        for (int i= 0; i< jsonQuestion.length(); i++)
                        {
                            Questions.add(new Question(jsonQuestion.getJSONObject(i)));
                        }
                        results.setQuestions(Questions);
                        results.setHasMore(response.getBoolean("has_more"));
                        results.setQuotaMax(response.getInt("quota_max"));
                        results.setQuotaRemaining(response.getInt("quota_remaining"));

                        if (handler != null)
                        {
                            handler.onParse(results);
                        }
                    } 
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
