package com.uncc.prateek.midterm800966178;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Prateek on 06-02-2017.
 */

public class JSONUtil {

    static public class ProductParser {
        static ArrayList<Product> parseProducts(String jsonString) throws JSONException {
            ArrayList<Product> productList = new ArrayList<Product>();
            JSONObject jsonObject = null;
            try {
                //jsonObject = new JSONObject(jsonString);
                //Log.d("JSONUtil", jsonObject.toString());

                //JSONArray articles = (JSONArray) jsonObject.get("articles");
                JSONArray articles = new JSONArray(jsonString);
                //Log.d("Articles", articles.toString());
                for (int i = 0; i < articles.length(); i++) {
                    JSONObject newsJSONObject = articles.getJSONObject(i);
                    Product news = new Product(newsJSONObject);


                    try {
                        JSONObject imageObject = newsJSONObject.getJSONObject("image_urls").getJSONArray("300x400").getJSONObject(0);
                        String imageStr = imageObject.getString("url").trim();
                        news.setImageLink(imageStr);
                    } catch (Exception e) {
                        Log.d("JSONexc", e.getMessage());
                    }


                    try {
                        JSONObject imageObject = newsJSONObject.getJSONArray("skus").getJSONObject(0);
                        double price= imageObject.getDouble("msrp_price");
                        double sale_price= imageObject.getDouble("sale_price");
                        double discount=0;
                        if(price!=0){
                           discount = ((price-sale_price)*100)/price;
                        }

                        news.setPrice(Math.round(sale_price * 100.0) / 100.0);
                        news.setDiscount(Math.round(discount * 100.0) / 100.0);
                    } catch (Exception e) {
                        Log.d("JSONexc", e.getMessage());
                    }
                    Log.d("JSONUtil", news.toString());
                    productList.add(news);

                }

            } catch (Exception e) {
                Log.d("JSONUtilExc", e.getMessage());
            }
            return productList;
        }
    }
}
