package com.example.steven.myapplication;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by traviswight on 4/9/18.
 */

public class DatabaseSearch{
    public List<XMLParser.Entry> database;
    private boolean sortCommon = true; // true by default
    private boolean sortID = false;
    private boolean sortLocation = false;

    /* ***** IMPORTANT FOR FILTERING! READ THIS!!!! ***** */
    // to use these, add this line of code to the top of the java file:
    // import static com.example.steven.myapplication.DatabaseSearch.*;

    public final static int FILTER_DROUGHT = 0;
    public final static int FILTER_LOCATION = 1;

    //****************************************************************

    public DatabaseSearch(InputStream in, XMLParser xml) throws XmlPullParserException,
            IOException {
        // InputStream needs to be called in activity that accesses the database
        this.database = xml.parse(in);
    }

    public List<XMLParser.Entry> getFullDatabase(){
        return database;
    }

    // For Sorting
    /* ***** IMPORTANT FOR SORTING! READ THIS!!!! ***** */
    // (step 1) call one of these first (common is set to true by default)
    public void setSortByCommon(){
        sortCommon = true;
        sortID = false;
        sortLocation = false;

    }

    public void setSortByID(){
        sortCommon = false;
        sortID = true;
        sortLocation = false;
    }

    public void setSortByLocation(){
        sortCommon = false;
        sortID = false;
        sortLocation = true;
    }

    // (step 2) then call this (always call this after you do anything with database on your end)
    public List<XMLParser.Entry> sortCurrentDatabase(List<XMLParser.Entry> entries){
        List<XMLParser.Entry> sortedlist = new ArrayList<>();

        if (sortCommon){
            sortedlist = sortByCommon(entries);
        }
        else if(sortLocation){
            sortedlist = sortByLocation(entries);
        }
        else if(sortID){
            sortedlist = sortByID(entries);
        }

        return sortedlist;
    }

    /* ***** IMPORTANT FOR FILTERING! ***** */
    // just call this function. Returns sorted filtered database with all EntryValues containing desired value.
    public List<XMLParser.Entry> filter(List<XMLParser.Entry> currentDatabase, int tag, String filterValue){
        /* ***** IMPORTANT FOR FILTERING! READ THIS!!!! ***** */
        // int tag = the tag in database you are filtering by (use FILTER_DROUGHT or FILTER_LOCATION)
        // String filterValue = value of tag you are searching:

        EntryValue entryValue = new EntryValue<>(filterValue);
        return sortCurrentDatabase(searchDatabase(currentDatabase, entryValue, tag));
    }

    // if database is filtered, use that version of the database

    private boolean compareEntryValues(EntryValue databaseValue, EntryValue compare){
        //boolean check = false;

        /*if((databaseValue.getObj() instanceof String && compare.getObj() instanceof String) ||
                (databaseValue.getObj() instanceof Integer && compare.getObj() instanceof Integer)){
            check = databaseValue.equals(compare);
        }*/

        return databaseValue.equals(compare);
    }

    /* DON'T WORRY ABOUT THESE */
    //************************************************************************
    private List<XMLParser.Entry> sortByCommon(List<XMLParser.Entry> entries){
        List<String> getCommonnames = getAllCommonnames(entries);
        List<XMLParser.Entry> sortedEntries = new ArrayList<>();
        EntryValue<String> currentCommonname;

        for(int i = 0; i < getCommonnames.size(); i++){
            currentCommonname = new EntryValue<>(getCommonnames.get(i));
            sortedEntries.addAll(searchDatabase(entries, currentCommonname));
        }

        return sortedEntries;
    }

    private List<XMLParser.Entry> sortByLocation(List<XMLParser.Entry> entries){
        List<String> getLocations = getAllLocations(entries);
        List<XMLParser.Entry> sortedEntries = new ArrayList<>();
        EntryValue<String> currentLocations;

        for(int i = 0; i < getLocations.size(); i++){
            currentLocations = new EntryValue<>(getLocations.get(i));
            sortedEntries.addAll(searchDatabase(entries, currentLocations));
        }

        return sortedEntries;
    }

    private List<XMLParser.Entry> sortByID(List<XMLParser.Entry> entries){
        List<Integer> getIDs = getAllIDs(entries);
        List<XMLParser.Entry> sortedEntries = new ArrayList<>();
        EntryValue<Integer> currentCommonname;

        for(int i = 0; i < getIDs.size(); i++){
            currentCommonname = new EntryValue<>(getIDs.get(i));
            sortedEntries.set(i, searchDatabase(entries, currentCommonname).get(0));
        }

        return sortedEntries;
    }

    private List<String> getAllCommonnames(List<XMLParser.Entry> entries){
        List<String> commonnames = new ArrayList<>();

        for (int i = 0; i < entries.size(); i++){
            commonnames.add(entries.get(i).getCommonName().getObj());
        }

        Collections.sort(commonnames);

        return commonnames;
    }

    private List<String> getAllLocations(List<XMLParser.Entry> entries){
        List<String> locations = new ArrayList<>();
        String location;

        for (int i = 0; i < entries.size(); i++){
            location = entries.get(i).getLocation().getObj();

            if(locations.isEmpty() || !location.contains(location)){
                locations.add(location);
            }

        }

        Collections.sort(locations);

        return locations;
    }

    private List<Integer> getAllIDs(List<XMLParser.Entry> entries){
        List<Integer> ids = new ArrayList<>();

        for (int i = 0; i < entries.size(); i++){
            ids.add(entries.get(i).getPlantID().getObj());
        }

        Collections.sort(ids);

        return ids;
    }

    private List<XMLParser.Entry> searchDatabase(List<XMLParser.Entry> currentDatabase, EntryValue value){
        List<XMLParser.Entry> entries = new ArrayList<>();

        for (int i = 0; i < currentDatabase.size(); i++){
            if(compareEntryValues(currentDatabase.get(i).getPictureID(), value) ||
                    compareEntryValues(currentDatabase.get(i).getCommonName(), value) ||
                    compareEntryValues(currentDatabase.get(i).getSpeciesName(), value) ||
                    compareEntryValues(currentDatabase.get(i).getOrigin(), value) ||
                    compareEntryValues(currentDatabase.get(i).getFlowerColor(), value) ||
                    compareEntryValues(currentDatabase.get(i).getBloomSeason(), value) ||
                    compareEntryValues(currentDatabase.get(i).getPlantWidth(), value) ||
                    compareEntryValues(currentDatabase.get(i).getPlantWidth(), value) ||
                    compareEntryValues(currentDatabase.get(i).getDrought(), value) ||
                    compareEntryValues(currentDatabase.get(i).getLocation(), value)){
                entries.add(currentDatabase.get(i));
            }
        }

        return entries;
    }

    private List<XMLParser.Entry> searchDatabase(List<XMLParser.Entry> currentDatabase, EntryValue value, int tag){
        List<XMLParser.Entry> entries = new ArrayList<>();

        for (int i = 0; i < currentDatabase.size(); i++){
            switch (tag){
                case FILTER_DROUGHT:
                    if(compareEntryValues(currentDatabase.get(i).getDrought(), value)){
                        entries.add(currentDatabase.get(i));
                    }
                case FILTER_LOCATION:
                    if(compareEntryValues(currentDatabase.get(i).getLocation(), value)){
                        entries.add(currentDatabase.get(i));
                    }
                default:
                    break;
            }
        }

        return entries;
    }
    //************************************************************************
}