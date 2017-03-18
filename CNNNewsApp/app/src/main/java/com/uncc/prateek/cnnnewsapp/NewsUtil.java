package com.uncc.prateek.cnnnewsapp;
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

public class NewsUtil {
    static String logText = "NewsUtil";
    ArrayList<News> newsList = null;
    String MEDIA_NAMESPACE = "http://search.yahoo.com/mrss/";
    //String DATASERVICES_NAMESPACE = "http://schemas.microsoft.com/ado/2007/08/dataservices";


   /* static public class NewsJSONParser{

        static ArrayList<News> parseNews(String jsonIn) throws JSONException{

            ArrayList<News> newsList = new ArrayList<News>();

            JSONArray personsArray = new JSONArray(jsonIn);


            for(int i=0; i<personsArray.length(); i++){
                JSONObject personJSON = personsArray.getJSONObject(i);
                News News = new News();
                News.setName(personJSON.getString("name"));
                News.setAge(personJSON.getInt("age"));
                News.setDept(personJSON.getString("department"));
                newsList.add(News);
            }


            return newsList;
        }
    }
*/

    static public class NewsXMLPullParser {


        static ArrayList<News> parseNews(InputStream xmlIn) throws XmlPullParserException, IOException {
            boolean done = false;
            Log.d(logText, "parseNews");

            //XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            //parser.setInput(xmlIn, "UTF-8");
            InputStreamReader isr = new InputStreamReader(xmlIn);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder xmlAsString = new StringBuilder(512);
            String line;
            try {
                while ((line = br.readLine()) != null) {
                    xmlAsString.append(line.replace("<<", "*").replace(">>", "*"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

            factory.setNamespaceAware(false);

            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xmlAsString.toString()));
            //parser.setInput(xmlIn, "UTF-8");
            News news = null;
            ArrayList<News> newsList=null;
            int event = parser.getEventType();
            while ((event != XmlPullParser.END_DOCUMENT) && (!done)) {
                Log.d(logText, "while loop for " + parser.getName());
                switch (event) {
                    case XmlPullParser.START_DOCUMENT: {
                        newsList = new ArrayList<News>();
                        break;
                    }
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("item")) {
                            Log.d(logText, "item");
                            news = new News();
                            Log.d(logText, "news obj created");
                        } else if (parser.getName().equalsIgnoreCase("title")) {
                            if (parser.nextText() != null) {
                                Log.d(logText, "title:parser.nextText()"+parser.nextText());
                            } else {
                                Log.d(logText, "parser.nextText() is null" + parser.nextText());
                            }

                        } else if (parser.getName().equalsIgnoreCase("link")) {
                            if (parser.nextText() != null) {
                                //news.setTitle(parser.nextText());
                                //String text = parser.nextText();
                                try {
                                    news.setLink(parser.nextText().trim());
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

                    case XmlPullParser.END_TAG: {
                        String name = parser.getName();
                        if (name.equalsIgnoreCase("item")) {
                            newsList.add(news);
                            } else if (name.equalsIgnoreCase("channel")) {
                            done = true;
                            }
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

/*

    static public class NewsSAXParser extends DefaultHandler{
        private News News = null;
        private StringBuilder xmlInnerText;

        public News getPerson() {
            return News;
        }
        static News parsePerson(InputStream xmlIn) throws IOException, SAXException{
            NewsSAXParser parser = new NewsSAXParser();
            Xml.parse(xmlIn, Xml.Encoding.UTF_8, parser);
            return parser.getPerson();
        }

        @Override
        public void startDocument() throws SAXException {
            xmlInnerText = new StringBuilder();
        }

        @Override
        public void startElement(String uri, String localName, String qName,
                                 Attributes attributes) throws SAXException {
            if(localName.equals("News")){
                News = new News();
                try{
                    News.setId(Integer.parseInt(attributes.getValue("id").trim()));
                } catch (NumberFormatException ex){
                    //Invalid integer.
                }
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
            if(localName.equals("name")){
                News.setName(xmlInnerText.toString().trim());
            } else if(localName.equals("department")){
                News.setDept(xmlInnerText.toString().trim());
            } else if(localName.equals("age")){
                try{
                    News.setAge(Integer.parseInt(xmlInnerText.toString().trim()));
                } catch(NumberFormatException ex){
                    //Invalid integer.
                }
            }
            xmlInnerText.setLength(0);
        }

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
        }
    }*/

    static public class NewsesSAXParser extends DefaultHandler{
        private ArrayList<News> newses=new ArrayList<News>();
        private News news = new News();
        private StringBuilder xmlInnerText;

        public ArrayList<News> getNews() {
            return newses;
        }
        static ArrayList<News> parseNews(InputStream xmlIn) throws IOException, SAXException{
            NewsesSAXParser parser = new NewsesSAXParser();
            Xml.parse(xmlIn, Xml.Encoding.UTF_8, parser);
            return parser.getNews();
        }

        @Override
        public void startDocument() throws SAXException {
            xmlInnerText = new StringBuilder();
            newses = new ArrayList<News>();
        }

        @Override
        public void startElement(String uri, String localName, String qName,
                                 Attributes attributes) throws SAXException {
            if(localName.equals("item")){
                Log.d("ddfg","news obj craeted");
                 news = new News();
            }
            else if(localName.equals("content") && qName.equals("media:content")){
                String image = attributes.getValue("url").trim();
                String height = attributes.getValue("height").trim();
                String width = attributes.getValue("width").trim();
                Log.d(logText,"image URL"+image +" height="+height+" width="+width);
                if(height.equals("300") && width.equals("300")){

                    if(news!=null){
                        news.setImage(image);Log.d(logText,"Setting image" + image);
                    }

                }
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
                xmlText = xmlInnerText.toString();

                Log.d(logText,"uri="+uri+" localName:"+localName+" qName "+qName + " xmlText "+xmlText);
            }
            if(xmlText==null){
               Log.d("hi","xmlinnertext null");
            }else{
                Log.d(logText,"localName : "+localName+" and xml text is : "+xmlText);
                //news= new News();
                if(localName.equals("title")){
                    news.setTitle(xmlText);
                    Log.d(logText,"news getTitle"+news.getTitle());
                } else if(localName.equals("link")){
                    news.setLink(xmlText);
                } else if(localName.equals("pubDate")){
                    news.setPubdate(xmlText);
                }
                else if(localName.equals("image")){
                    news.setImage(xmlText);
                }
                else if(localName.equals("description")){
                    news.setDescription(xmlText);
                }else if(localName.equals("content") && qName.equals("media:content")) {

                    Log.d(logText,xmlText);
                }

                else if(localName.equals("item")){
                    newses.add(news);
                    Log.d(logText,news.toString());
                    Log.d("ddd","news added to list");
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
    }
}