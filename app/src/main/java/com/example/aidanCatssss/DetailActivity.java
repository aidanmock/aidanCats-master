package com.example.aidanCatssss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.aidanCatssss.APIClasses.Cat;
import com.example.aidanCatssss.APIClasses.TheCatAPI;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

public class DetailActivity extends AppCompatActivity {

    Context context;

    TextView catName;
    ImageView catImage;
    ImageButton starBtn;
    TextView description;
    TextView origin;
    TextView temper;
    TextView lifespan;
    TextView dogFriend;
    TextView weight;
    TextView wikiLink;

    Cat currentCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        context = this;

        catName = findViewById(R.id.catName);
        catImage = findViewById(R.id.catImage);
        starBtn = findViewById(R.id.starBtn);
        description = findViewById(R.id.catDescription);
        origin = findViewById(R.id.origin);
        temper = findViewById(R.id.temper);
        lifespan = findViewById(R.id.lifeSpan);
        dogFriend = findViewById(R.id.dogF);
        weight = findViewById(R.id.weight);
        wikiLink = findViewById(R.id.wikiLink);

        Intent intent = getIntent();
        final String intentDetails = intent.getStringExtra("catID");

        String apikey = "8e23c073-a768-4a52-9bd4-4b486ae73f46";
        String url = "https://api.thecatapi.com/v1/images/search?api_key="+apikey+"&breed_id="+intentDetails;
        final RequestQueue requestQueue = Volley.newRequestQueue(this);


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);

                Gson gson = new Gson();
                Type collectionType = new TypeToken<Collection<TheCatAPI>>(){}.getType();
                ArrayList<TheCatAPI> catAPI =gson.fromJson(response, collectionType);
                requestQueue.stop();

                TheCatAPI currentApi = catAPI.get(0);

                currentCat = currentApi.getBreeds().get(0);

                catName.setText(currentCat.getName());

                Glide.with(context).load(currentApi.getUrl()).into(catImage);

                description.setText(currentCat.getDescription());
                lifespan.setText("Lifespan: "+currentCat.getLifeSpan());

                origin.setText("Origin: "+currentCat.getOrigin());
                temper.setText("Temperament: \n"+currentCat.getTemperament());

                weight.setText("Weight: "+currentCat.getWeight().getMetric());
                dogFriend.setText("Dog Friendliness: "+currentCat.getDogfriend());





                wikiLink.setText("Wikipedia Link: \n"+currentCat.getWikiUrl());

                Spanned Text = Html.fromHtml("Wikipedia Link: <br />" +
                        "<a href='"+currentCat.getWikiUrl()+"'>"+currentCat.getWikiUrl()+"</a>");

                wikiLink.setMovementMethod(LinkMovementMethod.getInstance());
                wikiLink.setText(Text);

                if (FavouriteDatabase.favouriteCatsMap.containsKey(intentDetails)){
                    starBtn.setImageResource(R.drawable.star_pressed);
                    starBtn.setTag(true);
                } else {
                    starBtn.setImageResource(R.drawable.star_unpressed);
                    starBtn.setTag(false);
                }


            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("GSON VOLLEY ERROR !");
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseListener, errorListener);
        requestQueue.add(stringRequest);
    }

    public void onToggleStar(View view){
        if (starBtn.getTag().equals(true)){
            FavouriteDatabase.removeFromFavourite(currentCat);
            starBtn.setTag(false);
            starBtn.setImageResource(R.drawable.star_unpressed);

            CharSequence text = currentCat.getName() + " removed from Favourites!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.getView().setBackgroundColor(getColor(R.color.colorPrimaryDark));
            TextView toastTextView = toast.getView().findViewById(android.R.id.message);
            toastTextView.setTextColor(getColor(R.color.white));
            toastTextView.setGravity(Gravity.CENTER_HORIZONTAL);
            toastTextView.setTextSize(14);
            toast.show();
        } else {
            FavouriteDatabase.addToFavourites(currentCat);
            starBtn.setTag(true);
            starBtn.setImageResource(R.drawable.star_pressed);

            CharSequence text = currentCat.getName() + " added to Favourites!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.getView().setBackgroundColor(getColor(R.color.colorPrimaryDark));
            TextView toastTextView = toast.getView().findViewById(android.R.id.message);
            toastTextView.setTextColor(getColor(R.color.white));
            toastTextView.setTextSize(14);
            toast.show();

        }

    }
}
