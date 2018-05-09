package com.example.steven.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import static com.example.steven.myapplication.DatabaseSearch.*;


import com.squareup.picasso.Picasso;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Tyler on 3/5/2018.
 */

public class PlantSearch extends AppCompatActivity implements OpenDatabase {
    //Declare required databases
    DatabaseSearch database;
    List<XMLParser.Entry> sampleDatabase;
    List<XMLParser.Entry> filteredDatabase;
    //RecyclerView Adapter object
    DataItemAdapter adapter;
    //Search edit box for narrowing plant list
    private EditText searchBox;
    //Declare the toolbar
    Toolbar toolbar;
    @Override
    //Necessary method for populating the List of plant objects
    public DatabaseSearch database() throws XmlPullParserException, IOException {
        InputStream inputStream = this.getResources().openRawResource(R.raw.database);
        XMLParser xmlParser = new XMLParser();
        DatabaseSearch dataSearch = new DatabaseSearch(inputStream, xmlParser);
        return dataSearch;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent recievedIntent = getIntent();

        // check to see if the intent is coming from the interactive map
        setContentView(R.layout.plant_list);
        //Toolbar implementation
        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("  Master Plant List");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setLogo(R.drawable.toolbarlogo);


        //This is so the keyboard doesn't auto pop-up on Plant List page
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        try {
            //Create new database of plant entries
            sampleDatabase = new ArrayList<>();
            //Pull in all entries from XML database
            database = database();
            sampleDatabase = database.getFullDatabase();
            //Create new database for filtered plant entries
            filteredDatabase = new ArrayList<>();
            filteredDatabase.addAll(sampleDatabase);
            //Retrieve search editText
            searchBox = findViewById(R.id.search_box);

            if (recievedIntent.getStringExtra("buildingName") != null) {
                // set the filter to building
                String buildingName = recievedIntent.getStringExtra("buildingName");
                Log.d("building name", buildingName);
                sampleDatabase.clear();
                sampleDatabase.add(1, filteredDatabase.get(1));
                for (int i = 0; i < sampleDatabase.size(); i++)
                {

//                    if(sampleDatabase.get(i).getLocation().getObj().equals(buildingName))
//                    {
//                        sampleDatabase.remove(i);
//                    }
                }
                //Retrieve RecyclerView resource
                RecyclerView recyclerView = findViewById(R.id.rvItems);
                //Make RecyclerView a linear layout
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                //Populate the dividing lines between the list of plants
                recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
                assert recyclerView != null;
                adapter = new DataItemAdapter(this, sampleDatabase);
                recyclerView.setAdapter(adapter);
            }
            else {
                //Retrieve RecyclerView resource
                RecyclerView recyclerView = findViewById(R.id.rvItems);
                //Make RecyclerView a linear layout
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                //Populate the dividing lines between the list of plants
                recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
                assert recyclerView != null;
                //Create the adapter object and pass it the plant database
                adapter = new DataItemAdapter(this, filteredDatabase);
                //Bind adapter to RecyclerView object
                recyclerView.setAdapter(adapter);
            }
            //Search box functionality
            searchBox.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    adapter.getFilter().filter(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        } catch (XmlPullParserException error) {
            error.printStackTrace();
        } catch (IOException error) {
            error.printStackTrace();
        }
        //Sort ArrayList in alphabetical order by Common Name
    }

    //inflate the menu in the Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_name, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        RecyclerView recyclerView = findViewById(R.id.rvItems);
        switch (item.getItemId()) {
            case R.id.sortdrought:
                for (int i = 0; i < filteredDatabase.size(); i++)
                {
                    if(filteredDatabase.get(i).getDrought().getObj().equals("Yes"))
                    {
                        filteredDatabase.remove(i);
                    }
                }
                adapter = new DataItemAdapter(this, filteredDatabase);
                recyclerView.setAdapter(adapter);
                return true;
            case R.id.sortcommonname:
                Collections.sort(filteredDatabase, new Comparator<XMLParser.Entry>() {
                    @Override
                    public int compare(XMLParser.Entry o1, XMLParser.Entry o2) {
                        return o1.getCommonName().getObj().compareTo(o2.getCommonName().getObj());
                    }
                });
                adapter = new DataItemAdapter(this, filteredDatabase);
                recyclerView.setAdapter(adapter);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class DataItemAdapter extends RecyclerView.Adapter<DataItemAdapter.ViewHolder> implements
            Filterable {
        //Create filtered plant database and custom filter
        private List<XMLParser.Entry> plantItems;
        private DataItemAdapter.CustomFilter mFilter;
        Context mContext;

        public DataItemAdapter(Context context, List<XMLParser.Entry> items) {
            this.plantItems = items;
            mContext = context;
            mFilter = new CustomFilter(DataItemAdapter.this);
        }

        @Override
        public DataItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,
                    null);
            DataItemAdapter.ViewHolder
                    viewHolder = new DataItemAdapter.ViewHolder(itemView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(DataItemAdapter.ViewHolder holder, int position) {
            //get current Entry
            final XMLParser.Entry item = plantItems.get(position);

            //Set name and icon
            holder.tvName.setText(item.getCommonName().getObj());
            Picasso.get().load("file:///android_asset/PlantIcons/" + Integer.toString(position + 1)
                    + "_2_1.png").placeholder(R.drawable.cast_album_art_placeholder).into(
                    holder.imageView);
            //holder.imageView.setImageResource(R.drawable.african_tulip_tree_icon);

            //Go to prospective plant page based on plantID
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int itemID = item.getPlantID().getObj();
                    Intent intent = new Intent(mContext, PlantSpecification.class);
                    intent.putExtra("plantID", itemID);
                    mContext.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return plantItems.size();
        }

        @Override
        public Filter getFilter() {
            return mFilter;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView tvName;
            public ImageView imageView;
            public View mView;
            public XMLParser.Entry mItem;

            public ViewHolder(View itemView) {
                super(itemView);

                tvName = (TextView) itemView.findViewById(R.id.itemNameText);
                imageView = (ImageView) itemView.findViewById(R.id.imageView);
                mView = itemView;
            }
        }

        public class CustomFilter extends Filter {
            DataItemAdapter mAdapter;

            private CustomFilter(DataItemAdapter mAdapter) {
                super();
                this.mAdapter = mAdapter;
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                filteredDatabase.clear();
                final FilterResults results = new FilterResults();
                if (constraint.length() == 0) {
                    filteredDatabase.addAll(sampleDatabase);
                } else {
                    final String filterPattern = constraint.toString().toLowerCase().trim();
                    for (final XMLParser.Entry mWords : sampleDatabase) {
                        if (mWords.getCommonName().getObj().toLowerCase().startsWith(
                                filterPattern)) {
                            filteredDatabase.add(mWords);
                        }
                    }
                }
                System.out.println("Count Number " + filteredDatabase.size());
                results.values = filteredDatabase;
                results.count = filteredDatabase.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                System.out.println(
                        "Count Number 2 " + ((List<XMLParser.Entry>) results.values).size());
                this.mAdapter.notifyDataSetChanged();
            }
        }
    }
}

