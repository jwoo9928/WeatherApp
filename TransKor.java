package com.example.mayweather;

public class TransKor {
        //시간 한글화 !
        public String koreanTime (String time, int indexT){
            indexT = time.indexOf("T");
            String koreanTime = time.substring(indexT + 1);
            koreanTime = koreanTime.replace(":00:00","");
            indexT = Integer.parseInt(koreanTime);
            return indexT + "시";
        }

        public String koreandate (String time){ //날짜를 가져와요!
            String month = time.split("-")[1];
            String day = time.split("-")[2];
            String date = month + "/" + day;
            date = date.split("T")[0];
            return date;
        }
}