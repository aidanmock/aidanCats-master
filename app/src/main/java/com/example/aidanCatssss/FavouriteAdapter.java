package com.example.aidanCatssss;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aidanCatssss.APIClasses.Cat;

import java.util.ArrayList;

public class FavouriteAdapter extends RecyclerView.Adapter<CatViewHolder> {

    Context context;
    ArrayList<Cat> favouriteCats;

    public FavouriteAdapter(ArrayList<Cat> favouriteCats) {
        this.favouriteCats = favouriteCats;
    }

    @Override
    public CatViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat, parent, false);
        this.context = parent.getContext();
        CatViewHolder catViewHolder = new CatViewHolder(view);
        return catViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CatViewHolder holder, final int position){
        final Cat catAtPosition = favouriteCats.get(position);
        System.out.println("Favourite Cat List: "  +catAtPosition.getName()+" "+ catAtPosition.getId());
        holder.catName.setText(catAtPosition.getName());
        holder.linearCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                System.out.println(catAtPosition.getId());
                intent.putExtra("catID",catAtPosition.getId());
                v.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount(){
        return favouriteCats.size();
    }

    public void setData(ArrayList<Cat> newFavCats) {
        this.favouriteCats = newFavCats;
    }

}
