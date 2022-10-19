package com.damage.adjuster.dto;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class damageInfo {

    private String birthdate;  // 생년월일
    private String accidentDate; // 사고발생일
    private String hospitalPeriod; // 입원일수
    private String treatmentNum; // 통원치료 횟수
    private String errorRate; // 과실율
    private String incomeClass; // 소득구분
    private String incomeMonth; // 월소득
    private String treatmentWeek; // 진단수주
    private String accidentType; // 사고부위
    private String accidentContent; // 피해내용
    private int afterTreatmentFee; // 항우치료비
    private Date createDate;
    private Date updateDate;
    private int face     ; // 얼굴
    private int waist   ; // 허리(척추)
    private int neck    ; // 목
    private int hand    ; // 팔(손)
    private int belly    ; // 복부(가슴)
    private int leg    ; // 다리(발)
    private int teeth  ; // 치아
    private int shoulder    ; // 어깨
    private int sprain    ; // 염좌
    private int bruise    ; // 타박상
    private int fracture    ; // 부러짐(골절)
    private int ripped    ; // 찢어짐(창상)

    private int bohumlossofbusiness    ; // 보험사휴업손해액
    private int bubumlossofbusiness    ; // 법원휴업손해액
    private int bohumsolatium    ; //  보험사 위자료
    private int bubumsolatium    ; //  법원 위자료
    private int transportcost    ; //  공통 교통비

}
