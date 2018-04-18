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


    public DatabaseSearch(InputStream in, XMLParser xml) throws XmlPullParserException, IOException {
        // InputStream needs to be called in activity that accesses the database
        this.database = xml.parse(in);
    }

    public List<XMLParser.Entry> getFullDatabase(){
        return database;
    }

    // For Sorting

    // set one of these first (common is set to true by default)
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

    // then call this (always call this after you do anything with database)
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

    private List<XMLParser.Entry> sortByCommon(List<XMLParser.Entry> entries){
        List<String> getCommonnames = getAllCommonnames(entries);
        List<XMLParser.Entry> sortedEntries = new ArrayList<>();
        EntryValue<String> currentCommonname;

        for(int i = 0; i < getCommonnames.size(); i++){
            currentCommonname = new EntryValue<>(getCommonnames.get(i));
            sortedEntries.set(i, searchDatabase(entries, currentCommonname).get(0));
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

    // for filtering:
    public List<XMLParser.Entry> filter(List<XMLParser.Entry> currentDatabase, String tag, String filterValue){
        // String tag = the tag in database you are filtering by (right now only supports drought and location)
        // String filterValue = value of tag you are searching:

        EntryValue entryValue = new EntryValue<>(filterValue);
        return searchDatabase(currentDatabase, entryValue, tag);
    }

    private boolean compareEntryValues(EntryValue databaseValue, EntryValue compare){
        boolean check = false;

        if((databaseValue.getObj() instanceof String && compare.getObj() instanceof String) ||
                (databaseValue.getObj() instanceof Integer && compare.getObj() instanceof Integer)){
            check = databaseValue.equals(compare);
        }

        return check;
    }

    // if database is filtered, use that version of the database

    // use this version of searchDatabase for search activity
    public List<XMLParser.Entry> searchDatabase(List<XMLParser.Entry> currentDatabase, EntryValue value){
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

    // don't worry about this (for filtering only)
    private List<XMLParser.Entry> searchDatabase(List<XMLParser.Entry> currentDatabase, EntryValue value, String tag){
        List<XMLParser.Entry> entries = new ArrayList<>();

        for (int i = 0; i < currentDatabase.size(); i++){
            switch (tag){
                case "drought":
                    if(compareEntryValues(currentDatabase.get(i).getDrought(), value)){
                        entries.add(currentDatabase.get(i));
                    }
                case "location":
                    if(compareEntryValues(currentDatabase.get(i).getLocation(), value)){
                        entries.add(currentDatabase.get(i));
                    }
                default:
                    break;
            }
        }

        return entries;
    }

    /*
    * Filter --> sorted list of filtered database
    * set filter (Common, species, drought, location)
    * - set filter --> search database based on filters --> return sorted search
    * */
}
