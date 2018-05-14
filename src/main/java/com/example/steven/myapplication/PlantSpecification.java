package com.example.steven.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.io.InputStream;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


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

        //Set Common Name
        TextView plantTitle = findViewById(R.id.plantTitle);
        plantTitle.setText(database.getFullDatabase().get(plantID).getCommonName().getObj());
        //Set Species Name
        TextView plantSpecies = findViewById(R.id.speciesname);
        plantSpecies.setText(database.getFullDatabase().get(plantID).getSpeciesName().getObj());
        //Set Location
        TextView plantLocation = findViewById(R.id.location);
        String location = database.getFullDatabase().get(plantID).getLocation().getObj();
        plantLocation.setText(location);
        //Set Flower Color
        TextView plantFlowerColor = findViewById(R.id.flowercolor);
        plantFlowerColor.setText(database.getFullDatabase().get(plantID).getFlowerColor().getObj());
        //Set Origin
        TextView plantOrigin = findViewById(R.id.origin);
        plantOrigin.setText(database.getFullDatabase().get(plantID).getOrigin().getObj());
        //Set Bloom Season
        TextView plantBloomSeason = findViewById(R.id.bloomseason);
        plantBloomSeason.setText(database.getFullDatabase().get(plantID).getBloomSeason().getObj());
        //Set Drought Tolerance
        TextView plantDroughtTolerance = findViewById(R.id.drought);
        plantDroughtTolerance.setText(database.getFullDatabase().get(plantID).getDrought().getObj());
        //Set Height
        TextView plantHeight = findViewById(R.id.plantHeight);
        plantHeight.setText(database.getFullDatabase().get(plantID).getPlantHeight().getObj());
        //Set Width
        TextView plantWidth = findViewById(R.id.plantWidth);
        plantWidth.setText(database.getFullDatabase().get(plantID).getPlantWidth().getObj());
        //Set Image
        ImageView imageView = (ImageView)findViewById(R.id.imageView2);
        Picasso.get().load("file:///android_asset/PlantPictures/" + Integer.toString(plantID+1) + "-1.png").placeholder(R.drawable.cast_album_art_placeholder).transform(new RoundedCornersTransformation(10,10)).resize(350,250).into(imageView);

    }
}