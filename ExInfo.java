package com.example.mayweather;

import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class ExInfo {
    Locations locations = new Locations();
    ExInfo(){
        locations.setLocation();
    }
    void getUV(TextView weather_info4, ImageView weather_icon3){

        JSONArray jsonArray;
        ArrayList<String> valueList;
        valueList = new ArrayList<>();

        String uvData;

        String url = "http://api.openweathermap.org/data/2.5/uvi/forecast?"
                +"lat="
                +locations.get_lat()
                +"&lon="
                +locations.get_lon()
                + "&appid=59286b1f1fa10ded6b089e7892a4f0bf";

        InputStream is = null;
        try {

            is = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuffer buffer = new StringBuffer();
            String str;
            while ((str = rd.readLine()) != null) {
                buffer.append(str);
            }
            uvData = buffer.toString();
            jsonArray = new JSONArray(uvData);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            String value = jsonObject.getString("value");
            valueList.add(value);

            String s_uv = valueList.get(0);
            double u = Double.parseDouble(s_uv);

            if (u > 0 && u <= 6) {
                weather_info4.setText("썬크림 \n필요없어요!");
                weather_icon3.setImageResource(R.drawable.suncreamx);
            } else if (u > 6) {
                weather_info4.setText("썬크림 \n 챙겨요!");
                weather_icon3.setImageResource(R.drawable.suncream);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    void getDust(ImageView airView, ImageView weather_icon2, TextView dust_info, TextView weather_info2){
        JSONObject jsonObject;
        String airData;

        String url = "http://api.airvisual.com/v2/nearest_city?lat="
                +locations.get_lat()
                +"&lon="
                +locations.get_lon()
                +"&rad=500&key=66654bd6-bae4-42eb-87fc-43bb8bcebca7";

        InputStream is = null;
        try {
            is = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuffer buffer = new StringBuffer();
            String str ;
            while ((str= rd.readLine()) != null) {
                buffer.append(str);
            }
            airData = buffer.toString();

            jsonObject = new JSONObject(airData);

            JSONObject jsonO = jsonObject.getJSONObject("data");
            System.out.println(jsonO.toString());
            JSONObject jsonO2 = jsonO.getJSONObject("current");
            System.out.println(jsonO2.toString());
            JSONObject jsonO3 = jsonO2.getJSONObject("pollution");
            int aqius = jsonO3.getInt("aqius");
            String dust = Integer.toString(aqius);

            if(aqius <= 30 && aqius >=0){
                airView.setImageResource(R.drawable.m1);
                weather_icon2.setImageResource(R.drawable.nomask);
                dust_info.setText("미세먼지 \n아주 좋음 \n 수치 : "+dust);
                weather_info2.setText("마스크 \n필요없어요!");
            }
            else if(aqius < 60 && aqius >= 30){
                airView.setImageResource(R.drawable.m2);
                weather_icon2.setImageResource(R.drawable.nomask);
                dust_info.setText("미세먼지\n좋음 \n 수치 : "+dust);
                weather_info2.setText("마스크 \n필요없어요!");
            }
            else if(aqius < 90 && aqius >= 60){
                airView.setImageResource(R.drawable.m3);
                weather_icon2.setImageResource(R.drawable.nomask);
                dust_info.setText("미세먼지 \n보통 \n 수치 : "+dust);
                weather_info2.setText("마스크 \n필요없어요!");
            }
            else if(aqius < 120 && aqius >= 90){
                airView.setImageResource(R.drawable.m4);
                weather_icon2.setImageResource(R.drawable.mask);
                dust_info.setText("미세먼지 \n나쁨 \n 수치 : "+dust);
                weather_info2.setText("마스크 \n 챙겨요 !");
            }
            else if(aqius >= 120){
                airView.setImageResource(R.drawable.m5);
                weather_icon2.setImageResource(R.drawable.mask);
                dust_info.setText("미세먼지\n매우나쁨 \n 수치 : "+dust);
                weather_info2.setText("마스크 \n 챙겨요 !");
            }


        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println(e);
        } catch (NullPointerException e) {
            System.out.println("dddd\n"+e);
        } catch (Exception e) {
            System.out.println("TTTTT\n"+e);
        }
    }

}
