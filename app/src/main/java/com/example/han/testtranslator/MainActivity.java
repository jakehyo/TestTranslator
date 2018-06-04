package com.example.han.testtranslator;



import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Tag you're it";

    private FloatingActionButton openOverlayButton;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    List<String> dataset = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //I believe this gives permission for the app to write to storage the OCR traineddata file.

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        openOverlayButton = findViewById(R.id.floatingActionButton);

        openOverlayButton.setOnClickListener(this);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(dataset);
        mRecyclerView.setAdapter(mAdapter);

// The retrofit setup for WordsAPI, which provides use with definitions of a given word.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WordsAPI.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WordsAPI api = retrofit.create(WordsAPI.class);
        //Line below provides API with the word it needs to find definitions for.
        Call<DefinitionList> call = api.getDefinitions("base");

        call.enqueue(new Callback<DefinitionList>() {
            @Override
            public void onResponse(Call<DefinitionList> call, Response<DefinitionList> response) {
                List<Definition> defs = response.body().getDefinitions();
                for (Definition def: defs)
                {
                    dataset.add(def.getPartOfSpeech() + ": " + def.getDefinition());
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<DefinitionList> call, Throwable t) {
                Log.d(TAG, "onFailure: "+ t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent svc = new Intent(this, OverlayShowingService.class);
        startService(svc);
        finish();
    }
}
