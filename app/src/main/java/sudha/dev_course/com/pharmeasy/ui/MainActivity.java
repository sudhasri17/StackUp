package sudha.dev_course.com.pharmeasy.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONObject;

import sudha.dev_course.com.pharmeasy.R;
import sudha.dev_course.com.pharmeasy.adapters.QuestionListAdapter;
import sudha.dev_course.com.pharmeasy.model.APIRequest;
import sudha.dev_course.com.pharmeasy.model.SearchResults;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mListView;
    private QuestionListAdapter mListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mListView    = (RecyclerView) findViewById(R.id.list_view);
        mListView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mListView.setLayoutManager(layoutManager);
        SearchResults results = new SearchResults();
        results.fetchQuestions(MainActivity.this, "android", new SearchResults.ParseHandler() {
            @Override
            public void onParse(SearchResults results) {
                if (results != null)
                {
                    if(mListAdapter == null)
                    {
                        mListAdapter = new QuestionListAdapter(MainActivity.this, results.getmQuestions());
                        mListView.setAdapter(mListAdapter);
                    }
                    else
                    {
                        mListAdapter.setData(results.getmQuestions());
                        mListAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
