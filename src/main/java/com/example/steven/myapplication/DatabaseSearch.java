package com.example.steven.myapplication;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by traviswight on 4/9/18.
 */

public class DatabaseSearch{
    private List<XMLParser.Entry> database = new ArrayList<>();

    public DatabaseSearch(InputStream in, XMLParser xml) throws XmlPullParserException, IOException {
        // InputStream needs to be called in activity that needs to access the database
        this.database = xml.parse(in);
    }

    public XMLParser.Entry getEntry(EntryValue<String> common){
        XMLParser.Entry result = null;

        for(int i = 0; i < database.size(); i++){
            if(common.compareObj(database.get(i).getCommonName().getObj())){
                result = database.get(i);
                break;
            }
        }

        return result;
    }

    public List<XMLParser.Entry> getAllEntriesWithTagValue(String tag, EntryValue value){
        List<XMLParser.Entry> entries = null;

        // TODO: Write function that returns a List of Entries with equal tag values

        switch (tag){
            case "plantid":

                break;
            case "commonname":

                break;
            case "speciesname":

                break;
            case "origin":

                break;
            case "flowercolor":

                break;
            case "bloomseason":

                break;
            case "width":

                break;
            case "height":

                break;
            case "drought":

                break;
            case "location":

                break;
            case "gps":

                break;
            case "pictureid":

                break;
            case "summary":

                break;
            default:

                break;
        }

        return entries;
    }
}
