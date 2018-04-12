package com.example.steven.myapplication;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
* Created by Steven on 4/1/2018.
*/

public class XMLParser {
    private final String ns = null;

    public List<Entry> parse(InputStream in) throws XmlPullParserException, IOException {
        System.out.print("before try");
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    private List<Entry> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<Entry> entries = new ArrayList<>();

        parser.require(XmlPullParser.START_TAG, ns, "entry");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("entry")) {
                entries.add(readEntry(parser));
            } else {
                skip(parser);
            }
        }
        return entries;
    }

    public static class Entry
    {
        public final EntryValue plantid;
        public final EntryValue commonname;
        public final EntryValue speciesname;
        public final EntryValue origin;
        public final EntryValue flowercolor;
        public final EntryValue bloomseason;
        public final EntryValue width;
        public final EntryValue height;
        public final EntryValue drought;
        public final EntryValue location;
        public final EntryValue gps;
        public final EntryValue pictureid;
        public final EntryValue summary;

        private Entry(EntryValue plantid, EntryValue commonname, EntryValue speciesname, EntryValue origin,
                      EntryValue flowercolor, EntryValue bloomseason, EntryValue width, EntryValue height, EntryValue drought,
                      EntryValue location, EntryValue gps, EntryValue pictureid, EntryValue summary)
        {
            this.plantid = plantid;
            this.commonname = commonname;
            this.speciesname = speciesname;
            this.origin = origin;
            this.flowercolor = flowercolor;
            this.bloomseason = bloomseason;
            this.width = width;
            this.height = height;
            this.drought = drought;
            this.location = location;
            this.gps = gps;
            this.pictureid = pictureid;
            this.summary = summary;

        }

        public EntryValue<Integer> getPlantID() { return this.plantid; }
        public EntryValue<String> getCommonName(){ return this.plantid; }
        public EntryValue<String> getSpeciesName() { return this.speciesname;}
        public EntryValue<String> getOrigin(){ return this.origin; }
        public EntryValue<String> getFlowerColor(){ return this.flowercolor;}
        public EntryValue<String> getBloomSeason(){ return this.bloomseason; }
        public EntryValue<String> getPlantWidth(){ return this.width; }
        public EntryValue<String> getPlantHeight(){ return this.height; }
        public EntryValue<String> getDrought(){ return this.drought; }


    }

    // Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
// to their respective "read" methods for processing. Otherwise, skips the tag.
    private Entry readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "entry");
        EntryValue plantid = null;
        EntryValue commonname = null;
        EntryValue speciesname = null;
        EntryValue origin = null;
        EntryValue flowercolor = null;
        EntryValue bloomseason = null;
        EntryValue width = null;
        EntryValue height = null;
        EntryValue drought = null;
        EntryValue location = null;
        EntryValue gps = null;
        EntryValue pictureid = null;
        EntryValue summary = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            switch (name) {
                case "plantid":
                    plantid = readPlantID(parser);
                    break;
                case "commonname":
                    commonname = readCommonName(parser);
                    break;
                case "speciesname":
                    speciesname = readSpeciesName(parser);
                    break;
                case "origin":
                    origin = readOrigin(parser);
                    break;
                case "flowercolor":
                    flowercolor = readFlowerColor(parser);
                    break;
                case "bloomseason":
                    bloomseason = readBloomSeason(parser);
                    break;
                case "width":
                    width = readWidth(parser);
                    break;
                case "height":
                    width = readHeight(parser);
                    break;
                case "drought":
                    drought = readDrought(parser);
                    break;
                case "location":
                    location = readLocation(parser);
                    break;
                case "gps":
                    gps = readGPS(parser);
                    break;
                case "pictureid":
                    pictureid = readPicture(parser);
                    break;
                case "summary":
                    pictureid = readSummary(parser);
                    break;
                default:
                    skip(parser);
                    break;
            }
        }
        return new Entry(plantid, commonname, speciesname, origin,
                flowercolor, bloomseason, width,height, drought, location,
                gps, pictureid, summary);
    }

    // Processes plantid tags in the feed.
    private EntryValue<Integer> readPlantID(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "plantid");

        String rawText = readText(parser);
        EntryValue<Integer> plantid = new EntryValue<>(Integer.parseInt(rawText));

        parser.require(XmlPullParser.END_TAG, ns, "plantid");

        return plantid;
    }
    // Processes commonName tags in the feed.
    private EntryValue<String> readCommonName(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "commonname");

        String rawText = readText(parser);
        EntryValue<String> commonname = new EntryValue<>(rawText);

        parser.require(XmlPullParser.END_TAG, ns, "commonname");
        return commonname;
    }
    // Processes speciesname tags in the feed.
    private EntryValue<String> readSpeciesName(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "speciesname");

        String rawText = readText(parser);
        EntryValue<String> speciesname = new EntryValue<>(rawText);

        parser.require(XmlPullParser.END_TAG, ns, "speciesname");
        return speciesname;
    }
    // Processes origin tags in the feed.
    private EntryValue<String> readOrigin(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "origin");

        String rawText = readText(parser);
        EntryValue<String> origin = new EntryValue<>(rawText);

        parser.require(XmlPullParser.END_TAG, ns, "origin");
        return origin;
    }
    // Processes flowercolor tags in the feed.
    private EntryValue<String> readFlowerColor(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "flowercolor");

        String rawText = readText(parser);
        EntryValue<String> flowercolor = new EntryValue<>(rawText);

        parser.require(XmlPullParser.END_TAG, ns, "flowercolor");
        return flowercolor;
    }
    // Processes bloomseason tags in the feed.
    private EntryValue<String> readBloomSeason(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "bloomseason");

        String rawText = readText(parser);
        EntryValue<String> bloomseason = new EntryValue<>(rawText);

        parser.require(XmlPullParser.END_TAG, ns, "bloomseason");
        return bloomseason;
    }
    // Processes width tags in the feed.
    private EntryValue<String> readWidth(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "width");

        String rawText = readText(parser);
        EntryValue<String> width = new EntryValue<>(rawText);

        parser.require(XmlPullParser.END_TAG, ns, "width");
        return width;
    }
    // Processes height tags in the feed.
    private EntryValue<String> readHeight(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "height");

        String rawText = readText(parser);
        EntryValue<String> height = new EntryValue<>(rawText);

        parser.require(XmlPullParser.END_TAG, ns, "height");
        return height;
    }
    // Processes drought tags in the feed.
    private EntryValue<String> readDrought(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "drought");

        String rawText = readText(parser);
        EntryValue<String> drought = new EntryValue<>(rawText);

        parser.require(XmlPullParser.END_TAG, ns, "drought");
        return drought;
    }
    // Processes location tags in the feed.
    private EntryValue<String[]> readLocation(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "location");

        String rawText = readText(parser);
        String[] rawToArray = rawText.split(",");
        EntryValue<String[]> location = new EntryValue<>(rawToArray);

        parser.require(XmlPullParser.END_TAG, ns, "location");
        return location;
    }
    // Processes gps tags in the feed.
    private EntryValue<Float[]> readGPS(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "gps");

        String rawText = readText(parser);
        String[] rawToArray = rawText.split(",");
        Float[] rawToArrayFloat = new Float[2];

        for (int index = 0; index < rawText.length(); index++){
            rawToArrayFloat[index] = Float.parseFloat(rawToArray[index]);
        }

        EntryValue<Float[]> gps = new EntryValue<>(rawToArrayFloat);

        parser.require(XmlPullParser.END_TAG, ns, "gps");
        return gps;
    }
    // Processes pictureid tags in the feed.
    private EntryValue<String[]> readPicture(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "pictureid");

        String rawText = readText(parser);
        String[] rawToArray = rawText.split(",");
        EntryValue<String[]> pictureid = new EntryValue<>(rawToArray);

        parser.require(XmlPullParser.END_TAG, ns, "pictureid");
        return pictureid;
    }
    // Processes summary tags in the feed.
    private EntryValue<String> readSummary(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "summary");

        String rawText = readText(parser);
        EntryValue<String> summary = new EntryValue<>(rawText);

        parser.require(XmlPullParser.END_TAG, ns, "summary");
        return summary;
    }


    // For the tags plantid, commonname, speciesname, origin,
    //flowercolor, bloomseason, width, drought, location,
    //gps, pictureid, summary, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}



