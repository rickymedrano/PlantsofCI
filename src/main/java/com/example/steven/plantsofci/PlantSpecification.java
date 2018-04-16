package com.example.steven.plantsofci;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.steven.myapplication.R;
import com.squareup.picasso.Picasso;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.io.InputStream;


public class PlantSpecification extends AppCompatActivity implements OpenDatabase {
    DatabaseSearch database;

    @Override
    public DatabaseSearch database() throws XmlPullParserException, IOException {
        InputStream inputStream = getResources().openRawResource(R.raw.database);
        XMLParser xmlParser = new XMLParser();
        DatabaseSearch dataSearch = new DatabaseSearch(inputStream, xmlParser);
        return dataSearch;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent recievedIntent = getIntent();
        int plantID = recievedIntent.getIntExtra("plantID", 0);

        try {
            database = database();

        } catch (XmlPullParserException error) {
            error.printStackTrace();
        } catch (IOException error) {
            error.printStackTrace();
        }

        setContentView(R.layout.plant_specification);
        String plantName = database.getFullDatabase().get(plantID).getCommonName().getObj();
        TextView plantTitle = (TextView)findViewById(R.id.plantTitle);
        plantTitle.setText(plantName);
        ImageView imageView = (ImageView)findViewById(R.id.imageView2);
        Picasso.get().load("https://docs.google.com/uc?id=1_NroIB9reMuUEJyxPVDN7ZjR4qG5DALO").into(imageView);
    }
}
