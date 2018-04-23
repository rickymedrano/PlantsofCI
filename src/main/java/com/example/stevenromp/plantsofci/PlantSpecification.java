package com.example.steven.plantsofci;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.steven.plantsofci.DatabaseSearch;
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
        String name = database.getFullDatabase().get(plantID).getCommonName().getObj();
        TextView plantTitle = (TextView)findViewById(R.id.plantTitle);
        plantTitle.setText(name);

        ImageView image = (ImageView)findViewById(R.id.imageView2);
        Picasso.get().load("https://docs.google.com/uc?id=1_NroIB9reMuUEJyxPVDN7ZjR4qG5DALO").into(image);

        String species = database.getFullDatabase().get(plantID).getCommonName().getObj();
        TextView plantSpecies = (TextView)findViewById(R.id.speciesname);
        plantSpecies.setText(species);

        String location = database.getFullDatabase().get(plantID).getCommonName().getObj();
        TextView plantLocation = (TextView)findViewById(R.id.location);
        plantLocation.setText(location);

        String flowerColor = database.getFullDatabase().get(plantID).getCommonName().getObj();
        TextView plantFlowerColor = (TextView)findViewById(R.id.flowercolor);
        plantFlowerColor.setText(flowerColor);

        String origin = database.getFullDatabase().get(plantID).getCommonName().getObj();
        TextView plantOrigin = (TextView)findViewById(R.id.origin);
        plantOrigin.setText(origin);

        String bloom = database.getFullDatabase().get(plantID).getCommonName().getObj();
        TextView plantBloom = (TextView)findViewById(R.id.bloomseason);
        plantBloom.setText(bloom);

        String drought = database.getFullDatabase().get(plantID).getCommonName().getObj();
        TextView plantDrought = (TextView)findViewById(R.id.drought);
        plantDrought.setText(drought);

        String height = database.getFullDatabase().get(plantID).getCommonName().getObj();
        TextView plantHeight = (TextView)findViewById(R.id.height);
        plantHeight.setText(height);

        String width = database.getFullDatabase().get(plantID).getCommonName().getObj();
        TextView plantWidth = (TextView)findViewById(R.id.width);
        plantWidth.setText(width);




    }
}
