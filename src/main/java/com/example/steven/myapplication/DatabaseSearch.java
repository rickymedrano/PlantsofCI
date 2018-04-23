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
    public List<XMLParser.Entry> database = new ArrayList<>();

    public DatabaseSearch(InputStream in, XMLParser xml) throws XmlPullParserException, IOException {
        // InputStream needs to be called in activity that accesses the database
        this.database = xml.parse(in);
    }

    public List<XMLParser.Entry> getFullDatabase(){
        return database;
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

    public List<XMLParser.Entry> getAllEntriesWithValue(EntryValue value){
        List<XMLParser.Entry> entries = null;

        // TODO: Write function that returns a List of Entries with equal Entry Values



        return entries;
    }
}
