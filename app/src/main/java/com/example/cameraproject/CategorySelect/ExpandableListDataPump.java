package com.example.cameraproject.CategorySelect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData(){
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> furniture = new ArrayList<String>();
        furniture.add("가구 기타 품목");
        furniture.add("매트리스");
        furniture.add("서랍 및 협탁");
        furniture.add("소파");
        furniture.add("식탁 및 탁자");
        furniture.add("옷걸이");
        furniture.add("의자");
        furniture.add("장롱 및 옷장");
        furniture.add("장식장 및 진열대");
        furniture.add("책상 및 테이블");
        furniture.add("책장 및 책꽂이");
        furniture.add("침대");

        List<String> electronics = new ArrayList<>();
        electronics.add("가스 및 전자레인지");
        electronics.add("가습기");
        electronics.add("가전기타품목");
        electronics.add("게임기 및 오락기");
        electronics.add("난로");
        electronics.add("냉장고");
        electronics.add("다리미");
        electronics.add("밥솥");
        electronics.add("복사기 및 프린터");
        electronics.add("비디오 및 영상기기");
        electronics.add("세탁기 및 탈수기");
        electronics.add("스피커 및 오디오");
        electronics.add("식기 세척기 및 건조기");
        electronics.add("에어컨 및 온풍기");
        electronics.add("전화기");
        electronics.add("정수기");
        electronics.add("청소기");
        electronics.add("컴퓨터 및 주변기기");
        electronics.add("텔레비전 및 모니터");

        List<String> other = new ArrayList<>();
        other.add("간판");
        other.add("개집 및 캣타워");
        other.add("건전지");
        other.add("금고");
        other.add("기타 품목");
        other.add("목재 및 마대");
        other.add("문짝 및 창문");
        other.add("변기 및 세면대");
        other.add("병풍");
        other.add("보일러 및 물탱크");
        other.add("소화기");
        other.add("수족관 및 어항");
        other.add("스티로폼");
        other.add("싱크대");
        other.add("쌀통");
        other.add("욕조");
        other.add("유리");
        other.add("유모차 및 휠체어");
        other.add("유아용품");
        other.add("철판 및 보드");

        List<String> lifeGoods = new ArrayList<>();
        lifeGoods.add("가방");
        lifeGoods.add("거울");
        lifeGoods.add("다리미판");
        lifeGoods.add("다리미판");
        lifeGoods.add("보행기 및 유모차");
        lifeGoods.add("생활용품 기타 품목");
        lifeGoods.add("시계");
        lifeGoods.add("액자");
        lifeGoods.add("운동기구");
        lifeGoods.add("의류 및 이불");
        lifeGoods.add("인형 및 장난감");
        lifeGoods.add("자전거");
        lifeGoods.add("장판 및 돗자리");

        expandableListDetail.put("가구",furniture);
        expandableListDetail.put("가전", electronics);
        expandableListDetail.put("가타", other);
        expandableListDetail.put("생활용품", lifeGoods);

        return expandableListDetail;
    }


}
