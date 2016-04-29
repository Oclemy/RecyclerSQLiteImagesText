package com.tutorials.hp.recyclersqliteimagestext;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.tutorials.hp.recyclersqliteimagestext.mDataObject.TVShow;
import com.tutorials.hp.recyclersqliteimagestext.mDatabase.DBAdapter;
import com.tutorials.hp.recyclersqliteimagestext.mRecycler.MyAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv;
    MyAdapter adapter;
    EditText nameEditText,urlEditText;
    ArrayList<TVShow> tvShows=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        //RECYCLERVIEW
        rv= (RecyclerView) findViewById(R.id.myRecyclerID);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setItemAnimator(new DefaultItemAnimator());

        //ADAPTER
        adapter=new MyAdapter(this,tvShows);

//        rv.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayDialog();
            }
        });
    }

    private void displayDialog()
    {
        Dialog d=new Dialog(this);
        d.setTitle("Save To DB");
        d.setContentView(R.layout.dialog_layout);

        nameEditText= (EditText) d.findViewById(R.id.nameEditTxt);
        urlEditText= (EditText) d.findViewById(R.id.urlEditTxt);
        Button saveBtn= (Button) d.findViewById(R.id.saveBtn);
        Button retrieveBtn= (Button) d.findViewById(R.id.retrieveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(nameEditText.getText().toString(),urlEditText.getText().toString());
            }
        });
        retrieveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 retrieve();
            }
        });

        d.show();


    }

    private void save(String name,String url)
    {
        DBAdapter db=new DBAdapter(this);
        db.openDB();
        long result=db.add(name,url);
        if(result==1)
        {
            nameEditText.setText("");
            urlEditText.setText("");

        }else
        {
            Snackbar.make(nameEditText,"Unable to save",Snackbar.LENGTH_SHORT).show();
        }

        db.closeDB();
    }

    private void retrieve()
    {
        tvShows.clear();
        DBAdapter db=new DBAdapter(this);
        db.openDB();
        Cursor c=db.getTVShows();
        while (c.moveToNext())
        {
            String name=c.getString(1);
            String url=c.getString(2);

            TVShow tv=new TVShow();
            tv.setName(name);
            tv.setImageUrl(url);

            tvShows.add(tv);
        }

        if(tvShows.size()>0)
        {
            rv.setAdapter(adapter);
        }

        db.closeDB();
    }




}
