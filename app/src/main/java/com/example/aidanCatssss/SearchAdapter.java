package com.example.aidanCatssss;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aidanCatssss.APIClasses.Cat;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<CatViewHolder> implements Filterable {

    ArrayList<Cat> catsList;
    ArrayList<Cat> mFilteredList;
    Context context;

    public SearchAdapter(ArrayList<Cat> catsList) {
        this.catsList = catsList;
        this.mFilteredList = new ArrayList<>();
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
        if (mFilteredList.isEmpty()){
            mFilteredList = catsList;
        }
        final Cat catAtPosition = mFilteredList.get(position);
        System.out.println("Filtered Cat List: "  +catAtPosition.getName()+" "+ catAtPosition.getId());

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
        if (mFilteredList.isEmpty()){
            return catsList.size();
        } else {
            return mFilteredList.size();
        }

    }

    public void setData(ArrayList<Cat> cats) {
        this.catsList = cats;
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String searchString = charSequence.toString();

                if (searchString.isEmpty()) {
                    mFilteredList = catsList;
                } else {
                    ArrayList<Cat> filteredList = new ArrayList<>();
                    for (Cat cat : catsList) {
                        if (cat.getName().toLowerCase().contains(searchString)) {
                            filteredList.add(cat);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<Cat>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
