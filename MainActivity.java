package com.example.mayweather;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class MainActivity extends Activity {
    public static final int REQUEST_CODE_PERMISSIONS = 1000;
    public FusedLocationProviderClient mFusedLocationClient;
    public static double lat;
    public static double lon;
    public static String weather;
    public static int indexT;
    static int umbrellaCheck = 0;
    ExInfo exInfo = new ExInfo();
    ForecastData forecastData = new ForecastData();
    TransKor kor = new TransKor();
    skyinfo sInfo = new skyinfo();

    TextView degree;
    TextView weatherText;
    ImageView weatherImageview;
    TextView time1;
    TextView time2;
    TextView time3;
    TextView time4;
    TextView time5;
    TextView day1;
    TextView day2;
    TextView day3;
    TextView day4;
    TextView day5;
    TextView time1_temp;
    TextView time2_temp;
    TextView time3_temp;
    TextView time4_temp;
    TextView time5_temp;
    TextView day1_temp;
    TextView day2_temp;
    TextView day3_temp;
    TextView day4_temp;
    TextView day5_temp;
    TextView weather_info4;
    TextView weather_info2;
    TextView weather_info1;
    TextView dust_info;
    ImageView time1_img;
    ImageView time2_img;
    ImageView time3_img;
    ImageView time4_img;
    ImageView time5_img;
    ImageView day1_img;
    ImageView day2_img;
    ImageView day3_img;
    ImageView day4_img;
    ImageView day5_img;
    ImageView weather_icon1;
    ImageView weather_icon2;
    ImageView weather_icon3;
    ImageView airView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        Intent intent = new Intent(this, LodingActivity.class);
        startActivity(intent);

        weatherImageview = (ImageView)findViewById(R.id.weatherImage);
        degree = (TextView) findViewById(R.id.degree);
        weather_info4 = (TextView)findViewById(R.id.weather_info4);
        weather_info2 = (TextView)findViewById(R.id.weather_info2);
        weather_info1 = (TextView)findViewById(R.id.weather_info1);
        dust_info = (TextView)findViewById(R.id.dust_info);

        time1 = (TextView)findViewById(R.id.time1);
        time2 = (TextView)findViewById(R.id.time2);
        time3 = (TextView)findViewById(R.id.time3);
        time4 = (TextView)findViewById(R.id.time4);
        time5 = (TextView)findViewById(R.id.time5);

        day1 = (TextView)findViewById(R.id.day1);
        day2 = (TextView)findViewById(R.id.day2);
        day3 = (TextView)findViewById(R.id.day3);
        day4 = (TextView)findViewById(R.id.day4);
        day5 = (TextView)findViewById(R.id.day5);

        day1_img = (ImageView)findViewById(R.id.day1_img);
        day2_img = (ImageView)findViewById(R.id.day2_img);
        day3_img = (ImageView)findViewById(R.id.day3_img);
        day4_img = (ImageView)findViewById(R.id.day4_img);
        day5_img = (ImageView)findViewById(R.id.day5_img);

        day1_temp = (TextView)findViewById(R.id.day1_temp);
        day2_temp = (TextView)findViewById(R.id.day2_temp);
        day3_temp = (TextView)findViewById(R.id.day3_temp);
        day4_temp = (TextView)findViewById(R.id.day4_temp);
        day5_temp = (TextView)findViewById(R.id.day5_temp);

        time1_temp = (TextView)findViewById(R.id.time1_temp);
        time2_temp = (TextView)findViewById(R.id.time2_temp);
        time3_temp = (TextView)findViewById(R.id.time3_temp);
        time4_temp = (TextView)findViewById(R.id.time4_temp);
        time5_temp = (TextView)findViewById(R.id.time5_temp);

        time1_img = (ImageView) findViewById(R.id.time1_img);
        time2_img = (ImageView)findViewById(R.id.time2_img);
        time3_img = (ImageView)findViewById(R.id.time3_img);
        time4_img = (ImageView)findViewById(R.id.time4_img);
        time5_img = (ImageView)findViewById(R.id.time5_img);

        weather_icon1 = (ImageView)findViewById(R.id.weather_icon1);
        weather_icon2 = (ImageView)findViewById(R.id.weather_icon2);
        weather_icon3 = (ImageView)findViewById(R.id.weather_icon3);
        airView = (ImageView)findViewById(R.id.airpolutionView);

    }

    @Override
    protected void onStart() {
        ImageView foreImage1 = (ImageView)findViewById(R.id.day1_img);
        ImageView foreImage2 = (ImageView)findViewById(R.id.day2_img);
        ImageView foreImage3 = (ImageView)findViewById(R.id.day3_img);
        ImageView foreImage4 = (ImageView)findViewById(R.id.day4_img);
        ImageView foreImage5 = (ImageView)findViewById(R.id.day5_img);

        foreImage1.setImageResource(R.drawable.mist);
        foreImage2.setImageResource(R.drawable.thuntherstorm);
        foreImage3.setImageResource(R.drawable.cloud);
        foreImage4.setImageResource(R.drawable.snow);
        foreImage5.setImageResource(R.drawable.rain);

        super.onStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //Android 4.0 이상 부터는 네트워크를 이용할 때 반드시 Thread 사용해야 함
                // TODO Auto-generated method stub
                forecastData.getXmlData(degree, weatherImageview, weatherText);//아래 메소드를 호출하여 XML data를 파싱해서 String 객체로 얻어오기
                getForecast();
                exInfo.getUV(weather_info4, weather_icon3);
                exInfo.getDust(airView, weather_icon2, dust_info, weather_info2);
                if(umbrellaCheck > 0){
                    weather_icon1.setImageResource(R.drawable.umbrella);
                    weather_info1.setText("우산챙겨요!");
                }
                else{
                    weather_icon1.setImageResource(R.drawable.noumb);
                    weather_info1.setText("우산\n필요없어요!");
                }

            }
        }).start();
    }

    //위치 권한 요청
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) { // 요청을 처리함
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {  // 권한 수락시 다시 체크
            case REQUEST_CODE_PERMISSIONS:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "권한 체크 거부 됨", Toast.LENGTH_SHORT).show();
                }
        }
    }

    //5일간 예보 받기
    void getForecast(){
        int i = -3;

        String queryUrl = "http://api.openweathermap.org/data/2.5/forecast?lat="
                +lat
                + "&lon="
                +lon
                + "&APPID=59286b1f1fa10ded6b089e7892a4f0bf&mode=xml&units=metric";

        try {
            URL url = new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8")); //inputstream 으로부터 xml 입력받기

            String tag;
            String datetime;
            String koreanTime;
            String maxTemperature;
            xpp.next();
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();//테그 이름 얻어오기

                        if (tag.equals("time")) {   //temperature 태그
                            datetime = xpp.getAttributeValue(null, "to");
                            koreanTime = kor.koreanTime(datetime,indexT);
                           i++;

                            switch (i) {
                                case 1:
                                    time1.setText(koreanTime);
                                    break;

                                case 2:
                                    time2.setText(koreanTime);
                                    break;

                                case 3:
                                    time3.setText(koreanTime);
                                    break;

                                case 4:
                                    time4.setText(koreanTime);
                                    break;

                                case 5:
                                    time5.setText(koreanTime);
                                    break;

                                case 8:
                                    day1.setText(kor.koreandate(datetime));
                                    break;

                                case 15:
                                    day2.setText(kor.koreandate(datetime));
                                    break;

                                case 22:
                                    day3.setText(kor.koreandate(datetime));
                                    break;

                                case 29:
                                    day4.setText(kor.koreandate(datetime));
                                    break;

                                case 36:
                                    day5.setText(kor.koreandate(datetime));
                                    break;

                                default:
                                    break;
                            }
                        }

                        else if (tag.equals("symbol")) {
                            weather = xpp.getAttributeValue(null, "number");
                            switch (i){
                                case 1 :
                                    sInfo.setWeatherImageview(weather,time1_img);
                                    umbrellaCheck += sInfo.umbrella(weather);
                                    break;

                                case 2:
                                    sInfo.setWeatherImageview(weather,time2_img);
                                    umbrellaCheck += sInfo.umbrella(weather);
                                    break;

                                case 3:
                                    sInfo.setWeatherImageview(weather,time3_img);
                                    umbrellaCheck += sInfo.umbrella(weather);
                                    break;

                                case 4:
                                    sInfo.setWeatherImageview(weather,time4_img);
                                    umbrellaCheck += sInfo.umbrella(weather);
                                    break;

                                case 5:
                                    sInfo.setWeatherImageview(weather,time5_img);
                                    umbrellaCheck +=sInfo. umbrella(weather);
                                    break;

                                case 8:
                                    sInfo.setWeatherImageview(weather,day1_img);
                                    break;

                                case 15:
                                    sInfo.setWeatherImageview(weather,day2_img);
                                    break;

                                case 22:
                                    sInfo.setWeatherImageview(weather,day3_img);
                                    break;

                                case 29:
                                    sInfo.setWeatherImageview(weather,day4_img);
                                    break;

                                case 36:
                                    sInfo.setWeatherImageview(weather,day5_img);
                                    break;

                                default:
                                    break;
                            }
                        }

                        else if (tag.equals("temperature")){
                            maxTemperature = xpp.getAttributeValue(null,"value");
                            switch (i){
                                case 1 :
                                    time1_temp.setText(maxTemperature + "도"); //maxTemperature 아님...
                                    break;

                                case 2:
                                    time2_temp.setText(maxTemperature + "도");
                                    break;

                                case 3:
                                    time3_temp.setText(maxTemperature + "도");
                                    break;

                                case 4:
                                    time4_temp.setText(maxTemperature + "도");
                                    break;

                                case 5:
                                    time5_temp.setText(maxTemperature+ "도");
                                    break;

                                case 8:
                                    day1_temp.setText(maxTemperature + "도");
                                    break;

                                case 15:
                                    day2_temp.setText(maxTemperature + "도");
                                    break;

                                case 22:
                                    day3_temp.setText(maxTemperature + "도");
                                    break;

                                case 29:
                                    day4_temp.setText(maxTemperature + "도");
                                    break;

                                case 36:
                                    day5_temp.setText(maxTemperature + "도");
                                    break;

                                default:
                                    break;
                            }
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