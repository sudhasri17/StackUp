package sudha.dev_course.com.pharmeasy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import sudha.dev_course.com.pharmeasy.R;
import sudha.dev_course.com.pharmeasy.model.Question;
import sudha.dev_course.com.pharmeasy.ui.MainActivity;
import sudha.dev_course.com.pharmeasy.utils.Utilities;

/**
 * Created by sudharshi on 16/4/16.
 */
public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.QuestionItemHolder>
{
    private Context mContext;
    private ArrayList<Question> mQuestions;

    public QuestionListAdapter(Context context, ArrayList<Question> questions)
    {
        mContext = context;
        mQuestions = questions;
    }

    public void setData(ArrayList<Question> questions)
    {
        mQuestions = questions;
    }

    @Override
    public QuestionItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        QuestionItemHolder vh = new QuestionItemHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(QuestionItemHolder holder, int position) {
        Question question = mQuestions.get(position);
        holder.mTitle.setText(question.getTitle());
        holder.mOwnerName.setText(question.getUser().getDisplayName());
        holder.mLastActivty.setText(question.getLastActivityDate().toString());
        holder.mScore.setText(question.getScore() + "");
        int imgSize = Utilities.dpToPixel(50, mContext);
        Picasso.with(mContext).
                load(question.getUser().getProfileImage()).
                placeholder(R.drawable.profile).resize(imgSize, imgSize).onlyScaleDown().
                into(holder.mOwnerImage);

        String tags = "";
        for (String tag :
                question.getTags()) {
            tags += tag + ", ";
        }
        tags = tags.substring(0, tags.length() - 1);
        holder.mTags.setText(tags);
    }

    @Override
    public int getItemCount() {
        if (mQuestions == null) {
            return 0;
        }
        else
        {
            return mQuestions.size();
        }
    }

    public static class QuestionItemHolder extends RecyclerView.ViewHolder
    {
        public ImageView mLike;
        public TextView mTitle;
        public TextView mTags;
        public TextView mOwnerName;
        public ImageView mOwnerImage;
        public TextView mLastActivty;
        public TextView mScore;

        public QuestionItemHolder(View itemView)
        {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.title);
            mTags  = (TextView) itemView.findViewById(R.id.tags);
            mOwnerName = (TextView) itemView.findViewById(R.id.owner_name);
            mOwnerImage = (ImageView) itemView.findViewById(R.id.owner_img);
            mLastActivty = (TextView) itemView.findViewById(R.id.last_updated);
            mScore = (TextView) itemView.findViewById(R.id.scores);
            mLike = (ImageView) itemView.findViewById(R.id.like);
        }
    }
}
