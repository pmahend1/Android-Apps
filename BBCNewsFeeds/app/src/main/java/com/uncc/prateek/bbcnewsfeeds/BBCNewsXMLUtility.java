package com.uncc.prateek.bbcnewsfeeds;

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

public class BBCNewsXMLUtility {
    static String logText = "BBCNewsXMLUtility";
    ArrayList<Object> newsList = null;
    //String MEDIA_NAMESPACE = "http:// search.yahoo.com/mrss/";

    static public class ObjectSAXParser extends DefaultHandler{

        private ArrayList<BBCNews> objectList=new ArrayList<BBCNews>();
        private BBCNews object = null;//new BBCNews();
        private StringBuilder xmlInnerText;

        boolean bTitle = false;
        boolean bImageLink = false;
        boolean bPubDate = false;
        boolean bDescription = false;
        boolean bDuration = false;
        boolean bPodCastLink = false;
        String mImageStr = "";
        String mPodcastLink = "";


        public ArrayList<BBCNews> getObjectList() {
            return objectList;
        }

        static ArrayList<BBCNews> parseObject(InputStream xmlIn) throws IOException, SAXException{
            ObjectSAXParser parser = new ObjectSAXParser();
            Log.d("parseObject","parseObject");
            Log.d("parseObject",xmlIn.toString());

            Xml.parse(xmlIn, Xml.Encoding.UTF_8, parser);
            return parser.getObjectList();
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
                Log.d("item","BBCNews obj created");
                object = new BBCNews();
            }else if(localName.equals("title")){
                bTitle =true;
            }else if(localName.equals("pubDate")){
                bPubDate =true;
            }else if(localName.equals("thumbnail") && qName.equals("media:thumbnail")){

                String width =attributes.getValue("width").trim();
                String height = attributes.getValue("height").trim();
               // if(width.equals("144") && height.equals("81")){
                    bImageLink =true;
                    mImageStr = attributes.getValue("url").trim();
                //}
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
                if(object!=null){
                    object.setTitle(txt);
                    bTitle=false;
                }
            }else if(bPubDate){
                object.setNewsDateStr(new String(ch,start,length));
                bPubDate=false;
            }else if(bImageLink){
                String image = new String(ch,start,length);
                Log.d("image","image link::"+image);
                object.setImageLink(mImageStr);
                bImageLink=false;
                mImageStr="";
            }
            else if(bDuration){
                String txt = new String(ch,start,length);
                int duration=0;
                try {
                    duration=Integer.parseInt(txt);
                }catch (Exception e){
                    Log.d("ex","Exc at 315"+e.getMessage());
                }
                //object.setDuration(duration);
                bDuration=false;
            }else if(bPodCastLink){
                //object.setPodcastLink(mPodcastLink);
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
                    objectList.add(object);
                    Log.d(logText,"object.toString() - "+object.toString());
                    Log.d("ddd","BBCNews added to list");
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