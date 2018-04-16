package com.example.steven.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

public class PlantSearch extends Activity implements OpenDatabase {
    List<PlantItem> plantItemList = new ArrayList<>();
    DatabaseSearch database;

    @Override
    public DatabaseSearch database() throws XmlPullParserException, IOException {
        InputStream inputStream = this.getResources().openRawResource(R.raw.database);
        XMLParser xmlParser = new XMLParser();
        DatabaseSearch dataSearch = new DatabaseSearch(inputStream, xmlParser);
        return dataSearch;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plant_list);

        //Get plant name and id from xml database
        try {
            database = database();
            String photoID;
            int photoIndex = 1;
            for (int dataIndex = 0; dataIndex < database.getFullDatabase().size(); dataIndex++)
            //Add PlantItem objects to Arraylist to be used by RecyclerView
            {
                photoID = String.valueOf(photoIndex) + "-1.png";
                photoIndex++;
                plantItemList.add(new PlantItem(database.getFullDatabase().get(dataIndex).getPlantID().getObj(),
                        database.getFullDatabase().get(dataIndex).getCommonName().getObj(), photoID));
            }
        } catch (XmlPullParserException error) {
            error.printStackTrace();
        } catch (IOException error) {
            error.printStackTrace();
        }
        //Sort ArrayList in alphabetical order by Common Name
        Collections.sort(plantItemList, new Comparator<PlantItem>() {
            @Override
            public int compare(PlantItem o1, PlantItem o2) {
                return o1.getItemName().compareTo(o2.getItemName());
            }
        });

        DataItemAdapter adapter = new DataItemAdapter(this, plantItemList);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecor = new DividerItemDecoration(this, 1);
        recyclerView.addItemDecoration(itemDecor);
        recyclerView.setAdapter(adapter);
    }
}

