package com.example.mayweather;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends Activity {
    private static final int REQUEST_CODE_PERMISSIONS = 1000;
    private FusedLocationProviderClient mFusedLocationClient;
    public static double lat;
    public static double lon;
    public static String weather;
    public static int indexT;
    static int umbrellaCheck = 0;


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
                setLocation();
                getXmlData();//아래 메소드를 호출하여 XML data를 파싱해서 String 객체로 얻어오기
                getForecast();
                uv();
                dust();
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

    //현재 날씨 받기
    void getXmlData() {

        String queryUrl = "http://api.openweathermap.org/data/2.5/weather?lat="
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
                        }

                        else if (tag.equals("weather")){
                            weatherid = xpp.getAttributeValue(null,"number");
                            weather = koreanWeather(weatherid);
                            setWeatherImageview(weatherid , weatherImageview);
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
    //URL에 넣을 lat /lon 값 계산
    public void setLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {  // 위치접근 권한 유무 확인

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_CODE_PERMISSIONS); // 사용자에게 위치접근 요청
            return;
        }
        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
//                    LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());  //위도, 경도 잡음
                    lat = location.getLatitude();
                    lon = location.getLongitude();  // 위도, 경도를 변수에 저장
                }
            }
        });
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
                            koreanTime = koreanTime(datetime);
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
                                    day1.setText(koreandate(datetime));
                                    break;

                                case 15:
                                    day2.setText(koreandate(datetime));
                                    break;

                                case 22:
                                    day3.setText(koreandate(datetime));
                                    break;

                                case 29:
                                    day4.setText(koreandate(datetime));
                                    break;

                                case 36:
                                    day5.setText(koreandate(datetime));
                                    break;

                                default:
                                    break;
                            }
                        }

                        else if (tag.equals("symbol")) {
                            weather = xpp.getAttributeValue(null, "number");
                            switch (i){
                                case 1 :
                                    setWeatherImageview(weather,time1_img);
                                    umbrellaCheck += umbrella(weather);
                                    break;

                                case 2:
                                    setWeatherImageview(weather,time2_img);
                                    umbrellaCheck += umbrella(weather);
                                    break;

                                case 3:
                                    setWeatherImageview(weather,time3_img);
                                    umbrellaCheck += umbrella(weather);
                                    break;

                                case 4:
                                    setWeatherImageview(weather,time4_img);
                                    umbrellaCheck += umbrella(weather);
                                    break;

                                case 5:
                                    setWeatherImageview(weather,time5_img);
                                    umbrellaCheck += umbrella(weather);
                                    break;

                                case 8:
                                    setWeatherImageview(weather,day1_img);
                                    break;

                                case 15:
                                    setWeatherImageview(weather,day2_img);
                                    break;

                                case 22:
                                    setWeatherImageview(weather,day3_img);
                                    break;

                                case 29:
                                    setWeatherImageview(weather,day4_img);
                                    break;

                                case 36:
                                    setWeatherImageview(weather,day5_img);
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

    //날씨 한글화 작업!!
    String koreanWeather(String id){
        if ((id.equals("200")) || (id.equals("230")) || (id.equals("231")) || (id.equals("232"))){
            return "번개/여우비";
        }
        else if (id.equals("201")){
            return "번개/비";
        }
        else if (id.equals("202")){
            return "번개/폭우";
        }
        else if ((id.equals("210")) || (id.equals("211")) || (id.equals("212"))){
            return "천둥 번개";
        }
        else if (id.equals("221")){
            return "폭풍우";
        }
        else if((id.equals("300")) || (id.equals("301"))|| (id.equals("302"))|| (id.equals("310"))|| (id.equals("311"))|| (id.equals("312"))|| (id.equals("313"))|| (id.equals("314"))|| (id.equals("321"))){
            return "여우비";
        }
        else if(id.equals("800")){
            return "맑음";
        }
        else if((id.equals("701")) || (id.equals("721")) || (id.equals("741"))){
            return "안개";
        }
        else if((id.equals("731")) || (id.equals("751")) || (id.equals("761"))){
            return "황사";
        }
        else if(id.equals("711")){
            return "스모그";
        }
        else if(id.equals("762")){
            return "화산재";
        }
        else if((id.equals("781")) || (id.equals("771"))){
            return "태풍";
        }
        else if(id.equals("500")) {
            return "여우비";
        }
        else if(id.equals("501") || id.equals("520") || id.equals("521")) {
            return "비";
        }
        else if(id.equals("502") || id.equals("503") || id.equals("504") || id.equals("522") || (id.equals("531"))){
            return "폭우";
        }
        else if(id.equals("511")){
            return "우빙";
        }
        else if(id.equals("600") || id.equals("601") || id.equals("621")){
            return "눈";
        }
        else if(id.equals("602") || id.equals("622")){
            return "폭설";
        }
        else if(id.equals("611") || id.equals("612") || id.equals("613") || id.equals("615") || id.equals("616") || id.equals("620")){
            return "진눈깨비";
        }
        else if(id.equals("801")){
            return "구름 조금";
        }
        else if (id.equals("802") || id.equals("803")){
            return "구름";
        }
        else if (id.equals("804")){
            return "구름 많음";
        }
        else {
            return "Error";
        }
    }

    //시간 한글화 !
    String koreanTime (String time){
        indexT = time.indexOf("T");
        String koreanTime = time.substring(indexT + 1);
        koreanTime = koreanTime.replace(":00:00","");
        indexT = Integer.parseInt(koreanTime);
        return indexT + "시";
    }

    String koreandate (String time){ //날짜를 가져와요!
        String month = time.split("-")[1];
        String day = time.split("-")[2];
        String date = month + "/" + day;
        date = date.split("T")[0];
        return date;
    }

    //현재 날씨 이미지 설정
    void setWeatherImageview(String id , ImageView view){
        if ((id.equals("200")) || (id.equals("230")) || (id.equals("231")) || (id.equals("232"))){
            view.setImageResource(R.drawable.rain); //번개 & 여우비
        }
        else if (id.equals("201")){
            view.setImageResource(R.drawable.thuntherstorm);
            //return "번개/비";
        }
        else if (id.equals("202")){
            view.setImageResource(R.drawable.thuntherstorm);
            // return "번개/폭우";
        }
        else if ((id.equals("210")) || (id.equals("211")) || (id.equals("212"))){
            view.setImageResource(R.drawable.thuntherstorm);
            //return "천둥 번개";
        }
        else if (id.equals("221")){
            view.setImageResource(R.drawable.rain);
            //return "폭풍우";
        }
        else if((id.equals("300")) || (id.equals("301"))|| (id.equals("302"))|| (id.equals("310"))|| (id.equals("311"))|| (id.equals("312"))|| (id.equals("313"))|| (id.equals("314"))|| (id.equals("321"))){
            view.setImageResource(R.drawable.rain);
            // return "여우비";
        }
        else if(id.equals("800")){
            view.setImageResource(R.drawable.clear_sky);
            //return "맑음";
        }
        else if((id.equals("701")) || (id.equals("721")) || (id.equals("741"))){
            view.setImageResource(R.drawable.mist);
            // return "안개";
        }
        else if((id.equals("731")) || (id.equals("751")) || (id.equals("761"))){
            view.setImageResource(R.drawable.dust);
            //황사
        }
        else if(id.equals("711")){
            view.setImageResource(R.drawable.dust);
            // return "스모그";
        }

        else if(id.equals("762")){
            view.setImageResource(R.drawable.dust);
            //return "화산재";
        }
        else if((id.equals("781")) || (id.equals("771"))){
            view.setImageResource(R.drawable.thuntherstorm);
            //return "태풍";
        }
        else if(id.equals("500")) {
            view.setImageResource(R.drawable.rain);
            // return "여우비";
        }
        else if(id.equals("501") || id.equals("520") || id.equals("521")) {
            view.setImageResource(R.drawable.rain);
            // return "비";
        }
        else if(id.equals("502") || id.equals("503") || id.equals("504") || id.equals("522") || (id.equals("531"))){
            view.setImageResource(R.drawable.rain);
            //return "폭우";
        }
        else if(id.equals("511")){
            view.setImageResource(R.drawable.snow);
            // return "우빙";
        }
        else if(id.equals("600") || id.equals("601") || id.equals("621")){
            view.setImageResource(R.drawable.snow);
            // return "눈";
        }
        else if(id.equals("602") || id.equals("622")){
            view.setImageResource(R.drawable.snow);
            // return "폭설";
        }
        else if(id.equals("611") || id.equals("612") || id.equals("613") || id.equals("615") || id.equals("616") || id.equals("620")){
            view.setImageResource(R.drawable.rain);
            // return "진눈깨비";
        }
        else if(id.equals("801")){
            view.setImageResource(R.drawable.sun_cloud);
            //  return "구름 조금";
        }
        else if (id.equals("802") || id.equals("803")){
            view.setImageResource(R.drawable.sun_cloud);
            //  return "구름";
        }
        else if (id.equals("804")){
            view.setImageResource(R.drawable.cloud);
            // return "구름 많음";
        }
    }
    int umbrella(String id){
        if ((id.equals("200")) || (id.equals("230")) || (id.equals("231")) || (id.equals("232"))){
            return 1;
        }
        else if (id.equals("201")){
            return 1;
        }
        else if (id.equals("202")){
            return 1;
        }
        else if ((id.equals("210")) || (id.equals("211")) || (id.equals("212"))){
            return 1;
        }
        else if (id.equals("221")){
            return 1;
        }
        else if((id.equals("300")) || (id.equals("301"))|| (id.equals("302"))|| (id.equals("310"))|| (id.equals("311"))|| (id.equals("312"))|| (id.equals("313"))|| (id.equals("314"))|| (id.equals("321"))){
            return 1;
        }
        else if(id.equals("800")){
            return 0;
        }
        else if((id.equals("701")) || (id.equals("721")) || (id.equals("741"))){
            return 0;
        }
        else if((id.equals("731")) || (id.equals("751")) || (id.equals("761"))){
            return 0;
        }
        else if(id.equals("711")){
            return 0;
        }
        else if(id.equals("762")){
            return 0;
        }
        else if((id.equals("781")) || (id.equals("771"))){
            return 1;
        }
        else if(id.equals("500")) {
            return 1;
        }
        else if(id.equals("501") || id.equals("520") || id.equals("521")) {
            return 1;
        }
        else if(id.equals("502") || id.equals("503") || id.equals("504") || id.equals("522") || (id.equals("531"))){
            return 1;
        }
        else if(id.equals("511")){
            return 0;
        }
        else if(id.equals("600") || id.equals("601") || id.equals("621")){
            return 0;
        }
        else if(id.equals("602") || id.equals("622")){
            return 0;
        }
        else if(id.equals("611") || id.equals("612") || id.equals("613") || id.equals("615") || id.equals("616") || id.equals("620")){
            return 1;
        }
        else if(id.equals("801")){
            return 0;
        }
        else if (id.equals("802") || id.equals("803")){
            return 0;
        }
        else if (id.equals("804")){
            return 0;
        }
        else {
            return -10000;
        }
    }
    void uv(){

        JSONArray jsonArray;
        ArrayList<String> valueList;
        valueList = new ArrayList<>();

        String uvData;

                String url = "http://api.openweathermap.org/data/2.5/uvi/forecast?"
                        +"lat="
                        +lat
                        +"&lon="
                        +lon
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

    void dust(){
            JSONObject jsonObject;
            String airData;

                String url = "http://api.airvisual.com/v2/nearest_city?lat="
                        +lat
                        +"&lon="
                        +lon
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