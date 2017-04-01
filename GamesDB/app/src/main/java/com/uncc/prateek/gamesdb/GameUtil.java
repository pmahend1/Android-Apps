package com.uncc.prateek.gamesdb;
/*
    a. Assignment #. Homework 05
	b. File Name. GameUtil.java
	c. Full name of all students in your group. Prateek Mahendrakar , Siva Ram Praneeth Vemulapalli
*/
/**
 * Created by Prateek on 13-02-2017.
 */

import android.util.Log;
import android.util.Xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class GameUtil {
    static String logText = "GameUtil";
    ArrayList<Game> gamesList = null;
    String MEDIA_NAMESPACE = "http://search.yahoo.com/mrss/";


    static public class GamesSAXParser extends DefaultHandler {
        private ArrayList<Game> gamesList = new ArrayList<Game>();
        private Game newGame = new Game();
        private StringBuilder xmlInnerText;

        static ArrayList<Game> parseGamesData(InputStream xmlIn) throws IOException, SAXException {
            GamesSAXParser parser = new GamesSAXParser();
            Xml.parse(xmlIn, Xml.Encoding.UTF_8, parser);
            return parser.getGamesList();
        }

        public ArrayList<Game> getGamesList() {
            return gamesList;
        }

        @Override
        public void startDocument() throws SAXException {
            xmlInnerText = new StringBuilder();
            gamesList = new ArrayList<Game>();
        }

        @Override
        public void startElement(String uri, String localName, String qName,
                                 Attributes attributes) throws SAXException {
            if (localName.equals("Game")) {
                Log.d(logText, "Game obj craeted");
                newGame = new Game();
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
            String xmlText = "";

            if (xmlInnerText.toString() == null) {
                Log.d(logText, "xmlInnerText NULL value");
            } else {
                xmlText = xmlInnerText.toString().trim();
            }
            if (xmlText == null) {
                Log.d(logText, "xmlinnertext null");
            } else {
                Log.d(logText, "localName : " + localName + " and xml text is : " + xmlText);
                //newGame. new Game();
                if (localName.equals("id")) {
                    newGame.setId(xmlText);
                    //Log.d(logText,"Game getTitle"+newGame.getId());
                } else if (localName.equals("GameTitle")) {
                    newGame.setGameTitle(xmlText);
                    //Log.d(logText,"Game getTitle"+newGame.getGameTitle());
                } else if (localName.equals("ReleaseDate")) {
                    newGame.setReleaseDate(xmlText);
                    Log.d(logText, "Game getTitle" + newGame.getReleaseDate());
                } else if (localName.equals("Platform")) {
                    newGame.setPlatForm(xmlText);
                    // Log.d(logText,"Game getTitle"+newGame.getPlatForm());
                } else if (localName.equals("Game")) {
                    gamesList.add(newGame);
                    // Log.d(logText,newGame.toString());
                    //Log.d(logText,"Game added to list");
                } else {
                    Log.d(logText, "else " + xmlText);
                }
                xmlInnerText.setLength(0);
            }


        }

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
        }
    }

    static public class GameDetailsSAXParser extends DefaultHandler {
        //String images[] = new String[10];
        ArrayList<String> images = new ArrayList<String>();
        //String genres[] = new String[10];
        ArrayList<String> genres = new ArrayList<String>();
        ArrayList<Game> similar = new ArrayList<Game>();
        private static String URLBase = "http://thegamesdb.net/api/GetGame.php?id=";//http://wiki.thegamesdb.net/index.php/GetGamesList";
        private static String URL = "";
        private String searchKey = "";


        int i = 0;
        private Game newGame = null;
        private String baseImgUrl;
        private StringBuilder xmlInnerText;
        private Game similarGame = null;

        static ArrayList<Game> parseSimilarGames(String[] identifier){
            ArrayList<Game> similarGamesSub = new ArrayList<Game>();
            for(String s : identifier){
                try {
                    URL url = new URL(URLBase+s);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.connect();
                    int statusCode = con.getResponseCode();
                    if (statusCode == HttpURLConnection.HTTP_OK) {
                        InputStream in = con.getInputStream();
                        //ArrayList<Game> gameList = GameUtil.GamesSAXParser.parseGamesData(in);
                        Game game = parseGameDetailsData(in);
                        similarGamesSub.add(game);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return similarGamesSub;



            }
            return similarGamesSub;
        }

        static Game parseGameDetailsData(InputStream xmlIn) throws IOException, SAXException {
            GameDetailsSAXParser parser = new GameDetailsSAXParser();
            Xml.parse(xmlIn, Xml.Encoding.UTF_8, parser);
            return parser.getGameDetails();
        }

        public Game getGameDetails() {
            return newGame;
        }

        @Override
        public void startDocument() throws SAXException {
            xmlInnerText = new StringBuilder();
            newGame = new Game();
            i = 0;
        }

        @Override
        public void startElement(String uri, String localName, String qName,
                                 Attributes attributes) throws SAXException {
            if (localName.equals("Game")) {
                if(i>0){
                    similarGame = new Game();
                Log.d(logText, "Game details obj created");}
            } else if (localName.equals("baseImgUrl")) {
                baseImgUrl = xmlInnerText.toString().trim();
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
            String xmlText = "";

            if (xmlInnerText.toString() == null) {
                Log.d(logText, "xmlInnerText NULL value");
            } else {
                xmlText = xmlInnerText.toString().trim();
            }
            if (xmlText == null) {
                Log.d(logText, "xmlinnertext null");
            } else {
                Log.d(logText, "uri=" + uri + " localName:" + localName + " qName " + qName + " xmlText " + xmlText);
                if (localName.equals("id")) {
                    if(i>0){
                        similarGame.setId(xmlText);
                    }else{
                        newGame.setId(xmlText);
                        Log.d(logText, "Game getTitle" + newGame.getId());
                    }

                } else if (localName.equals("baseImgUrl")) {
                    baseImgUrl = xmlInnerText.toString().trim();
                } else if (localName.equals("GameTitle")) {
                    newGame.setGameTitle(xmlText);
                    Log.d(logText, "Game getTitle" + newGame.getGameTitle());
                } else if (localName.equals("PlatformId")) {
                    if(i>0){
                        similarGame.setPlatFormId(xmlText);
                    }else{
                        newGame.setPlatFormId(xmlText);
                        Log.d(logText, "Game getPlatFormId" + newGame.getPlatFormId());
                    }

                } else if (localName.equals("ReleaseDate")) {
                    newGame.setReleaseDate(xmlText);
                    Log.d(logText, "Game getPlatFormId" + newGame.getPlatFormId());
                } else if (localName.equals("Platform")) {
                    newGame.setPlatForm(xmlText);
                    Log.d(logText, "Game getPlatForm" + newGame.getPlatForm());
                } else if (localName.equals("Overview")) {
                    newGame.setOverView(xmlText);
                    Log.d(logText, "Game getOverView" + newGame.getOverView());
                } else if (localName.equals("ESRB")) {
                    newGame.setESRB(xmlText);
                    Log.d(logText, "Game getESRB" + newGame.getESRB());
                } else if (localName.equals("Genres")) {
                    newGame.setESRB(xmlText);
                    Log.d(logText, "Game getESRB" + newGame.getESRB());
                } else if (localName.equals("Co-op")) {
                    newGame.setCoOp(xmlText);
                    Log.d(logText, "newGame.getCoOp()  " + newGame.getCoOp());
                } else if (localName.equals("Youtube")) {
                    newGame.setYouTube(xmlText);
                    Log.d(logText, "newGame.getYouTube() " + newGame.getYouTube());
                } else if (localName.equals("Developer")) {
                    newGame.setDeveloper(xmlText);
                    Log.d(logText, "newGame.getDeveloper() " + newGame.getDeveloper());
                } else if (localName.equals("Rating")) {
                    newGame.setRating(xmlText);
                    Log.d(logText, "newGame.getRating() " + newGame.getRating());
                } else if (localName.equals("Similar")) {
                    Game similarGame = new Game();
                    Log.d(logText, "Similar" + xmlText);
                } else if (localName.equals("Images")) {
                    Log.d(logText, "Images : " + xmlText);
                } else if (localName.equals("Publisher")) {
                    newGame.setPublisher(xmlText);
                    Log.d(logText, "Publisher : " + xmlText);
                } else if (localName.equals("original") && qName.equals("original")) {
                    images.add(baseImgUrl + xmlText);
                    Log.d(logText, "original image " + baseImgUrl + xmlText);
                    newGame.setImages(images.toArray(new String[images.size()]));

                } else if (localName.equals("genre")) {
                    Log.d(logText, "genre : " + xmlText);
                    genres.add(xmlText);
                    newGame.setGenres(genres.toArray(new String[genres.size()]));
                } else if (localName.equals("SimilarCount")) {
                    try {
                        i = Integer.parseInt(xmlText);
                    } catch (Exception e) {
                        Log.d(logText, "Exception in SimilarCount");
                    }
                }
                else if (localName.equals("Game")) {
                    if(i>0){
                        if(similar.size()<i){
                            similar.add(similarGame);
                        }

                        Log.d(logText, "Game added to similar game list" + "Size now  is "+ similar.size());
                        Log.d(logText,"Similar games " + similar.toString());
                        newGame.setSimilar(similar);

                    }else{
                        Log.d(logText, newGame.toString());
                        Log.d(logText, "Game added to list");
                    }

                } else {
                    Log.d(logText, "else " + xmlText);
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