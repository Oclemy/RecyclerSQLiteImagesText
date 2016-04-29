package com.tutorials.hp.recyclersqliteimagestext.mRecycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tutorials.hp.recyclersqliteimagestext.R;
import com.tutorials.hp.recyclersqliteimagestext.mDataObject.TVShow;
import com.tutorials.hp.recyclersqliteimagestext.mPicasso.PicassoClient;

import java.util.ArrayList;

/**
 * Created by Oclemmy on 4/29/2016 for ProgrammingWizards Channel and http://www.Camposha.com.
 */
public class MyAdapter  extends RecyclerView.Adapter<MyHolder> {

   Context c;
    ArrayList<TVShow> tvShows;

    public MyAdapter(Context c, ArrayList<TVShow> tvShows) {
        this.c = c;
        this.tvShows = tvShows;
    }

    //INITIALIZE VH
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.model,parent,false);
        MyHolder holder=new MyHolder(v);

        return holder;
    }

    //BIND DATA
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
         holder.nameTxt.setText(tvShows.get(position).getName());
        PicassoClient.loadImage(c,tvShows.get(position).getImageUrl(),holder.img);
    }

    //TOTAL NUM TVSHOWS
    @Override
    public int getItemCount() {
        return tvShows.size();
    }
}
