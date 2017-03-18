package com.uncc.prateek.tedradiohour;
/*
    a) Assignment #. : InClass05
    b) Full name of the student. Prateek Mahendrakar
*/
/**
 * Created by Prateek on 13-02-2017.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;
import android.util.Xml;

public class RadioHourUtility {
    static String logText = "RadioHourUtility";
    ArrayList<Radio> newsList = null;
    //String MEDIA_NAMESPACE = "http://search.yahoo.com/mrss/";

    static public class RadioXMLPullParser {


        static ArrayList<Radio> parseRadio(InputStream xmlIn) throws XmlPullParserException, IOException {
            Log.d(logText, "parseRadio");

            InputStreamReader isr = new InputStreamReader(xmlIn);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder xmlAsString = new StringBuilder(512);
            String line;

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

            factory.setNamespaceAware(false);

            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xmlAsString.toString()));
            //parser.setInput(xmlIn, "UTF-8");
            Radio radio = null;
            ArrayList<Radio> newsList=null;
            int event = parser.getEventType();

            while ((event != XmlPullParser.END_DOCUMENT)) {
                Log.d(logText, "while loop for " + parser.getText());
                switch (event) {
                    case XmlPullParser.START_DOCUMENT: {
                        Log.d(logText,"START_DOCUMENT");
                        newsList = new ArrayList<Radio>();
                        break;
                    }
                    case XmlPullParser.START_TAG: {
                        Log.d(logText,"parser.getName():"+parser.getText());
                        if (parser.getName().equals("item")) {
                            Log.d(logText, "item");
                            radio = new Radio();
                            Log.d(logText, "Radio obj created");
                        } else if (parser.getName().equalsIgnoreCase("title")) {
                            if (parser.nextText() != null) {
                                Log.d(logText, "title:parser.nextText()" + parser.nextText());
                            } else {
                                Log.d(logText, "parser.nextText() is null" + parser.nextText());
                            }

                        } else if (parser.getName().equalsIgnoreCase("link")) {
                            if (parser.nextText() != null) {
                                try {
                                    radio.setImageLink(parser.nextText().trim());
                                    Log.d(logText, "link:parser.nextText()" + parser.nextText());
                                } catch (Exception e) {
                                    //e.getMessage();
                                    Log.d(logText, "Exce " + e.getMessage());
                                }

                            } else {
                                Log.d(logText, "parser.nextText() is null" + parser.nextText());
                            }

                        }
                        break;
                    }
                    case XmlPullParser.END_TAG: {
                        String name = parser.getName();

                            newsList.add(radio);

                        break;


                    }


                    default: {
                        if (parser.getName() != null) {
                            Log.d(logText, "default" + parser.getName());
                            break;
                        } else {
                            Log.d(logText, "default" + "NULL at default");
                            break;
                        }

                    }

                }
                event = parser.next();
            }
            return newsList;

        }

    }

    /*static public class RadioSAXParser extends DefaultHandler{
        private ArrayList<Radio> radioList=new ArrayList<Radio>();
        private Radio radio = null;//new Radio();
        private StringBuilder xmlInnerText;

        public ArrayList<Radio> getRadioList() {
            return radioList;
        }

        static ArrayList<Radio> parseRadio(InputStream xmlIn) throws IOException, SAXException{
            RadioSAXParser parser = new RadioSAXParser();
            Xml.parse(xmlIn, Xml.Encoding.UTF_8, parser);
            return parser.getRadioList();
        }




        @Override
        public void startDocument() throws SAXException {
            xmlInnerText = new StringBuilder();
            //radioList = new ArrayList<Radio>();
        }

        @Override
        public void startElement(String uri, String localName, String qName,
                                 Attributes attributes) throws SAXException {
            Log.d("startElement",localName+"+"+qName+"+"+attributes.toString());
            if(localName.equals("item")){
                Log.d("item","Radio obj created");
                 radio = new Radio();
            }
            if(localName.equals("image") && qName.equals("itunes:image")){
                String image = attributes.getValue("href").trim();

                radio.setImageLink(image);
                Log.d(logText,"image URL"+radio.getImageLink());
            }

        }
        @Override
        public void characters(char[] ch, int start, int length)
                throws SAXException {
            xmlInnerText.append(ch, start, length);
        }

        @Override
        public void endElement(String uri, String localName, String qName)
                throws SAXException {
            String xmlText="";

            if(xmlInnerText.toString()==null){
                Log.d(logText,"xmlInnerText NULL value");
            }else{
                xmlText = xmlInnerText.toString().trim();

                Log.d("endElement","uri="+uri+" localName:"+localName+" qName "+qName + " xmlText "+xmlText);
            }
            if(xmlText==null){
               Log.d("hi","xmlinnertext null");
            }else{
                Log.d(logText,"localName : "+localName+" and xml text is : "+xmlText);
                //Radio= new Radio();
                if(localName.equals("title")){
                    radio.setTitle(xmlText);
                    Log.d(logText,"Radio getTitle"+radio.getTitle());
                }
                *//*else if(localName.equals("link")){
                    radio.setImageLink(xmlText);
                }*//*
                else if(localName.equals("pubDate")){
                    radio.setPubDate(xmlText);
                }
                else if(localName.equals("image")){
                    radio.setImageLink(xmlText);
                }
                else if(localName.equals("description")){
                    radio.setDescription(xmlText);
                }else if(localName.equals("image") && qName.equals("itunes:image")) {
                    Log.d(logText,"image link ==>"+xmlText);
                    //String image = attributes.getValue("href").trim();

                   // Log.d(logText,"image URL"+image);
                    //radio.setImageLink(image);
                }

                else if(localName.equals("item")){
                    radioList.add(radio);
                    Log.d(logText,"radio.toString()==>"+radio.toString());
                    Log.d("ddd","Radio added to list");
                }
                else{
                    Log.d(logText,"else "+xmlText);

                }
                xmlInnerText.setLength(0);
            }


        }

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
        }
    }*/

    static public class RadioSAXParser extends DefaultHandler{
        private ArrayList<Radio> radioList=new ArrayList<Radio>();
        private Radio radio = null;//new Radio();
        private StringBuilder xmlInnerText;

        boolean bTitle = false;
        boolean bImageLink = false;
        boolean bPubDate = false;
        boolean bDescription = false;
        boolean bDuration = false;
        boolean bPodCastLink = false;
        String mImageStr = "";
        String mPodcastLink = "";


        public ArrayList<Radio> getRadioList() {
            return radioList;
        }

        static ArrayList<Radio> parseRadio(InputStream xmlIn) throws IOException, SAXException{
            RadioSAXParser parser = new RadioSAXParser();
            Xml.parse(xmlIn, Xml.Encoding.UTF_8, parser);
            return parser.getRadioList();
        }




        @Override
        public void startDocument() throws SAXException {
            xmlInnerText = new StringBuilder();
        }

        @Override
        public void startElement(String uri, String localName, String qName,
                                 Attributes attributes) throws SAXException {
            Log.d("startElement",localName+"+"+qName+"+"+attributes.toString());
            if(localName.equals("item")){
                Log.d("item","Radio obj created");
                radio = new Radio();
            }else if(localName.equals("title")){
                bTitle =true;
            }else if(localName.equals("pubDate")){
                bPubDate =true;
            }else if(localName.equals("image") && qName.equals("itunes:image")){
                bImageLink =true;
                mImageStr = attributes.getValue("href").trim();
            }else if(localName.equals("description")){
                bDescription=true;
            }else if(localName.equals("duration")&& qName.equals("itunes:duration")){
                bDuration=true;
            }
            else if(localName.equals("enclosure")){
                bPodCastLink=true;
                mPodcastLink = attributes.getValue("url").trim();
            }
        }
        @Override
        public void characters(char[] ch, int start, int length)
                throws SAXException {
            if(bTitle){
                String txt = new String(ch,start,length);
                Log.d("txt",txt);
                if(radio!=null){
                    radio.setTitle(txt);
                    bTitle=false;
                }
            }else if(bPubDate){
                radio.setPubDate(new String(ch,start,length));
                bPubDate=false;
            }else if(bImageLink){
                String image = new String(ch,start,length);
                Log.d("image","image link::"+image);
                radio.setImageLink(mImageStr);
                bImageLink=false;
                mImageStr="";
            }else if(bDescription && radio!=null){
                    String desc = new String(ch,start,length);
                    radio.setDescription(desc);
                    bDescription=false;
            }
            else if(bDuration){
                String txt = new String(ch,start,length);
                int duration=0;
                try {
                    duration=Integer.parseInt(txt);
                }catch (Exception e){
                    Log.d("ex","Exc at 315"+e.getMessage());
                }
                radio.setDuration(duration);
                bDuration=false;
            }else if(bPodCastLink){
                radio.setPodcastLink(mPodcastLink);
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName)
                throws SAXException {
            String xmlText="";

            if(xmlInnerText.toString()!=null){
                xmlText = xmlInnerText.toString().trim();
                Log.d("endElement","uri="+uri+" localName:"+localName+" qName "+qName + " xmlText "+xmlText);
            }


            if(xmlText==null){
                Log.d(logText,"xmlinnertext null");
            }else{
                if(localName.equals("item")){
                    radioList.add(radio);
                    Log.d(logText,"radio.toString() - "+radio.toString());
                    Log.d("ddd","Radio added to list");
                }
                xmlInnerText.setLength(0);
            }


        }

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
        }
    }
}