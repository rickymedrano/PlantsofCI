package com.example.steven.plantsofci;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by traviswight on 4/11/18.
 */

public interface OpenDatabase {
    DatabaseSearch database() throws XmlPullParserException, IOException;

    /*
    * For all activities that use the database, you need to add "implements OpenDatabase" to your class
    * and add this method:
    *
    * public DatabaseSearch database() throws XmlPullParserException, IOException{
    *    InputStream in = getResources().openRawResource(R.xml.database);
    *    XMLParser xml = new XMLParser();
    *    DatabaseSearch search = new DatabaseSearch(in,xml);
    *
    *    return search;
    * }
    * */

}