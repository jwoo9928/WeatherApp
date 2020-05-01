package com.example.mayweather;

import android.widget.ImageView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class ForecastData {
    Locations locations = new Locations();
    skyinfo sinfo = new skyinfo();
    TransKor transkor = new TransKor();

    public ForecastData() {
        locations.setLocation();
    }

    //현재 날씨
    public void getXmlData(TextView degree, ImageView weatherImageview, TextView weatherText) {

        String queryUrl = "http://api.openweathermap.org/data/2.5/weather?lat="
                + locations.get_lat()
                + "&lon="
                + locations.get_lon()
                + "&APPID=59286b1f1fa10ded6b089e7892a4f0bf&mode=xml&units=metric";

        try {
            URL url = new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.

            InputStream is = url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8")); //inputstream 으로부터 xml 입력받기

            String tag;
            String recentTem;
            String weather;
            String weatherid;

            xpp.next();
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();//테그 이름 얻어오기

                        if (tag.equals("temperature")) {//temperature 태그
                            recentTem = xpp.getAttributeValue(null, "value");
                            degree.setText(recentTem + "도");
                        } else if (tag.equals("weather")) {
                            weatherid = xpp.getAttributeValue(null, "number");
                            weather = sinfo.setWeatherImageview(weatherid, weatherImageview);
                            weatherText.setText(weather);
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        break;
                }

                eventType = xpp.next();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch blocke.printStackTrace();
        }
    }
}