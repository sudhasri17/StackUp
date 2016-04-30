package sudha.dev_course.com.pharmeasy.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by sudharshi on 16/4/16.
 */
public class Question implements Parcelable
{
    private ArrayList<String> mTags;
    private User mUser;
    private boolean mIsAnswered;
    private int mViewCount;
    private int mAnswerCount;
    private int mScore;
    private Date mLastActivityDate;
    private String mLink;
    private String mTitle;
    private long mQuestionId;

    protected Question(Parcel in) {
        mTags = in.createStringArrayList();
        mIsAnswered = in.readByte() != 0;
        mViewCount = in.readInt();
        mAnswerCount = in.readInt();
        mScore = in.readInt();
        mLink = in.readString();
        mTitle = in.readString();
        mQuestionId =in.readLong();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public Question(JSONObject jsonObject) {
        try {
            mAnswerCount = jsonObject.getInt("answer_count");
            mIsAnswered  = jsonObject.getBoolean("is_answered");
            Date date = new Date(jsonObject.getLong("last_activity_date"));
            mLastActivityDate = date;
            mLink = jsonObject.getString("link");
            mTitle = jsonObject.getString("title");
            mQuestionId = jsonObject.getLong("question_id");
            JSONArray jsonTags = jsonObject.getJSONArray("tags");
            ArrayList<String> tags = new ArrayList<>();
            for (int i = 0 ; i < jsonTags.length();i++)
            {
                tags.add(jsonTags.getString(i));
            }
            mTags = tags;
            mUser = new User(jsonObject.getJSONObject("owner"));
            mViewCount = jsonObject.getInt("view_count");
            mScore = jsonObject.getInt("score");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User mUser) {
        this.mUser = mUser;
    }

    public boolean isIsAnswered() {
        return mIsAnswered;
    }

    public void setIsAnswered(boolean mIsAnswered) {
        this.mIsAnswered = mIsAnswered;
    }

    public int getViewCount() {
        return mViewCount;
    }

    public void setViewCount(int mViewCount) {
        this.mViewCount = mViewCount;
    }

    public int getAnswerCount() {
        return mAnswerCount;
    }

    public void setAnswerCount(int mAnswerCount) {
        this.mAnswerCount = mAnswerCount;
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int mScore) {
        this.mScore = mScore;
    }

    public Date getLastActivityDate() {
        return mLastActivityDate;
    }

    public void setLastActivityDate(Date mLastActivityDate) {
        this.mLastActivityDate = mLastActivityDate;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String mLink) {
        this.mLink = mLink;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public ArrayList<String> getTags() {
        return mTags;
    }

    public void setTags(ArrayList<String> mTags) {
        this.mTags = mTags;
    }

    public long getQuestionId() {
        return mQuestionId;
    }

    public void setQuestionId(long mQuestionId) {
        this.mQuestionId = mQuestionId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(mTags);
        dest.writeByte((byte) (mIsAnswered ? 1 : 0));
        dest.writeInt(mViewCount);
        dest.writeInt(mAnswerCount);
        dest.writeInt(mScore);
        dest.writeString(mLink);
        dest.writeString(mTitle);
        dest.writeLong(mQuestionId);
    }


    public static class User implements Parcelable
    {
        private String mProfileImage;
        private String mDisplayName;

        protected User(Parcel in) {
            mProfileImage = in.readString();
            mDisplayName = in.readString();
        }

        public User (JSONObject jsonObject)
        {
            try
            {
                mProfileImage = jsonObject.getString("profile_image");
                mDisplayName  = jsonObject.getString("display_name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public static final Creator<User> CREATOR = new Creator<User>() {
            @Override
            public User createFromParcel(Parcel in) {
                return new User(in);
            }

            @Override
            public User[] newArray(int size) {
                return new User[size];
            }
        };

        public String getProfileImage() {
            return mProfileImage;
        }

        public void setProfileImage(String mProfileImage) {
            this.mProfileImage = mProfileImage;
        }

        public String getDisplayName() {
            return mDisplayName;
        }

        public void setDisplayName(String mDisplayName) {
            this.mDisplayName = mDisplayName;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(mProfileImage);
            dest.writeString(mDisplayName);
        }
    }
}