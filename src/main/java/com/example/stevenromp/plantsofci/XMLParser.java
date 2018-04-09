package com.example.stevenromp.plantsofci;

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
        List<Entry> entries = new ArrayList<Entry>();

        parser.require(XmlPullParser.START_TAG, ns, "feed");
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
            public final String plantid;
            public final String commonname;
            public final String speciesname;
            public final String origin;
            public final String flowercolor;
            public final String bloomseason;
            public final String width;
            public final String height;
            public final String drought;
            public final String location;
            public final String gps;
            public final String pictureid;
            public final String summary;

            private Entry(String plantid, String commonname, String speciesname, String origin,
                          String flowercolor, String bloomseason, String width,String height, String drought,
                          String location, String gps, String pictureid, String summary)
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
        }

        // Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
// to their respective "read" methods for processing. Otherwise, skips the tag.
        private com.example.stevenromp.plantsofci.XMLParser.Entry readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
            parser.require(XmlPullParser.START_TAG, ns, "entry");
            String plantid = null;
            String commonname = null;
            String speciesname = null;
            String origin = null;
            String flowercolor = null;
            String bloomseason = null;
            String width = null;
            String height = null;
            String drought = null;
            String location = null;
            String gps = null;
            String pictureid = null;
            String summary = null;

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
        private String readPlantID(XmlPullParser parser) throws IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, ns, "plantid");
            String plantid = readText(parser);
            parser.require(XmlPullParser.END_TAG, ns, "plantid");
            return plantid;
        }
        // Processes commonName tags in the feed.
        private String readCommonName(XmlPullParser parser) throws IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, ns, "commonname");
            String commonname = readText(parser);
            parser.require(XmlPullParser.END_TAG, ns, "commonname");
            return commonname;
        }
        // Processes speciesname tags in the feed.
        private String readSpeciesName(XmlPullParser parser) throws IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, ns, "speciesname");
            String speciesname = readText(parser);
            parser.require(XmlPullParser.END_TAG, ns, "speciesname");
            return speciesname;
        }
        // Processes origin tags in the feed.
        private String readOrigin(XmlPullParser parser) throws IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, ns, "origin");
            String origin = readText(parser);
            parser.require(XmlPullParser.END_TAG, ns, "origin");
            return origin;
        }
        // Processes flowercolor tags in the feed.
        private String readFlowerColor(XmlPullParser parser) throws IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, ns, "flowercolor");
            String flowercolor = readText(parser);
            parser.require(XmlPullParser.END_TAG, ns, "flowercolor");
            return flowercolor;
        }
        // Processes bloomseason tags in the feed.
        private String readBloomSeason(XmlPullParser parser) throws IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, ns, "bloomseason");
            String bloomseason = readText(parser);
            parser.require(XmlPullParser.END_TAG, ns, "bloomseason");
            return bloomseason;
        }
        // Processes width tags in the feed.
        private String readWidth(XmlPullParser parser) throws IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, ns, "width");
            String width = readText(parser);
            parser.require(XmlPullParser.END_TAG, ns, "width");
            return width;
        }
        // Processes height tags in the feed.
        private String readHeight(XmlPullParser parser) throws IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, ns, "height");
            String height = readText(parser);
            parser.require(XmlPullParser.END_TAG, ns, "height");
            return height;
        }
        // Processes drought tags in the feed.
        private String readDrought(XmlPullParser parser) throws IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, ns, "drought");
            String drought = readText(parser);
            parser.require(XmlPullParser.END_TAG, ns, "drought");
            return drought;
        }
        // Processes location tags in the feed.
        private String readLocation(XmlPullParser parser) throws IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, ns, "location");
            String location = readText(parser);
            parser.require(XmlPullParser.END_TAG, ns, "location");
            return location;
        }
        // Processes gps tags in the feed.
        private String readGPS(XmlPullParser parser) throws IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, ns, "gps");
            String gps = readText(parser);
            parser.require(XmlPullParser.END_TAG, ns, "gps");
            return gps;
        }
        // Processes pictureid tags in the feed.
        private String readPicture(XmlPullParser parser) throws IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, ns, "pictureid");
            String pictureid = readText(parser);
            parser.require(XmlPullParser.END_TAG, ns, "pictureid");
            return pictureid;
        }
        // Processes summary tags in the feed.
        private String readSummary(XmlPullParser parser) throws IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, ns, "summary");
            String summary = readText(parser);
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



