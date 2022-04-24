package com.tfriends.keys;

public class PrivacyKey {
	
    //	지역별 좌표와 명칭을 리스트로 표현합니다. 이러한 것들은 이전것과 달리 for문으로 돌리기 때문에 형식에 맞춰서 했습니다.
    //							  강원      경기       경남		 경북      광주       대구		대전       부산      서울      세종       울산		인천      전남		 전북	   제주      충남		충북
        public double [] lat = 	{ 37.845  , 37.291  , 35.228  , 36.566  , 35.155  , 35.700  , 36.333  , 35.103  , 37.568  , 36.482  , 35.537  , 37.450  , 34.755  , 35.822  , 33.510  , 36.601  , 36.637  };
        public double [] lon = 	{ 127.734 , 127.009 , 128.681 , 128.725 , 126.916 , 128.550 , 127.417 , 129.040 , 126.978 , 127.287 , 129.317 , 126.416 , 127.660 , 127.149 , 126.522 , 126.665 , 127.490 };
        public String [] loc = 	{"gangwon","gyeonggi","gyeongnam","gyeongbuk","gwangju","daegu","daejeon","busan","seoul","sejong","ulsan","incheon","jeonnam","jeonbuk","jeju","chungnam","chungbuk"};
        public String [] lockr=	{"강원","경기","경남","경북","광주","대구","대전","부산","서울","세종","울산","인천","전남","전북","제주","충남","충북"};
}