package com.damage.adjuster.service;


import com.damage.adjuster.dao.MainDao;
import com.damage.adjuster.dto.damageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainService {

    @Autowired
    MainDao mainDao;

//    휴업손해액
//    보험사 - (월소득/30)*입원기간(일)*0.85
//    법원 - (월소득/30)*입원기간(일) *0.95
//
//    개인사업자, 프리랜서는 월 급여 2,881,388원(수정가능)
//    단, 법원계산시 3,267,220원으로 계산(무조건)
//(이 경우 월소득 입력 X?)
//
//    무직자는 월급여 2,529,090원
//
//    미성년자는 0원
//            향후치료비
//    보험사, 법원 둘다 왼쪽에 작성된 내용 그대로 삽입
//
//    위자료
//    보험사 –  15만원 통일
//
//    법원 – 사고부위, 피해내용에 따라 다름
//    척추 – 염좌 선택시 120만원
//    전체 – 찢어짐 선택시 120만원
//    나머지는 기본 50만원
//
//    교통비
//    보험사, 법원 동일
//    통원치료 횟수 * 8000

    public damageInfo docalculrateService(damageInfo info){

//soleproprietor  개인사업자(프리랜서,일용직)
//salaried  급여소득자(사대보험가입)
//jobless무직자(전업주부,학생)
//minor   미성년자

         if(info.getIncomeClass().equals("jobless")){
            //무직자는 월급여 2,529,090원
            info.setIncomeMonth("2529090");
        }else if(info.getIncomeClass().equals("minor")){
            //    미성년자는 0원
            info.setIncomeMonth("0");
        }
        if(info.getHospitalCheck().equals("1")){
            //일반병원일 경우
            info.setAfterTreatmentFee((Integer.parseInt(info.getManualTherapy())*114750)+(Integer.parseInt(info.getShockwaveTherapy())*72000)+
                    (Integer.parseInt(info.getChunaTherapy())*20000)+(Integer.parseInt(info.getGeneralTherapy())*10000));
        }else if(info.getHospitalCheck().equals("2")){
            //한방병원일 경우
            info.setAfterTreatmentFee((Integer.parseInt(info.getManualTherapy())*150000)+(Integer.parseInt(info.getShockwaveTherapy())*85000)+
                    (Integer.parseInt(info.getChunaTherapy())*20000)+(Integer.parseInt(info.getGeneralTherapy())*10000));
        }else if(info.getHospitalCheck().equals("3")){
            //한의원일 경우
            info.setAfterTreatmentFee((Integer.parseInt(info.getManualTherapy())*114750)+(Integer.parseInt(info.getShockwaveTherapy())*74000)+
                    (Integer.parseInt(info.getChunaTherapy())*20000)+(Integer.parseInt(info.getGeneralTherapy())*10000));
        }


        if(info.getIncomeMonth() !=null || !info.getIncomeMonth().equals("")) {
            // 휴업손해액 보험사 - (월소득/30)*입원기간(일)*0.85
            info.setBohumlossofbusiness((int) ((Long.parseLong(info.getIncomeMonth())*Long.parseLong(info.getHospitalPeriod()) / 30) * 0.85));
            //    법원 - (월소득/30)*입원기간(일) *0.95  단, 법원계산시 3,267,220원으로 계산(무조건)
            if(info.getIncomeClass().equals("soleproprietor")){
                info.setBubumlossofbusiness((int) ((Long.parseLong("3267220")*Long.parseLong(info.getHospitalPeriod()) / 30) * 0.95));
            }else{
                info.setBubumlossofbusiness((int) ((Long.parseLong(info.getIncomeMonth())*Long.parseLong(info.getHospitalPeriod()) / 30) * 0.95));
            }
            // 위자료 보험사 –  15만원 통일
            info.setBohumsolatium(150000);
            // 법원 – 척추 - 염좌 선택시 120만원
            System.out.println(info.getWaist() + " 허리.");
            System.out.println(info.getSprain() + " 염좌.");
            System.out.println(info.getRipped()+ " 찢어짐");
            if(info.getWaist() ==1 && info.getSprain() ==1){
                System.out.println( " 척추 염좌 선택.");
                info.setBubumsolatium(1200000);
            }
            //전체 – 찢어짐 선택시 120만원
           else if(info.getRipped() ==1){
                System.out.println( " 찢어짐 선택.");
                info.setBubumsolatium(1200000);
            }
            // 나머지는 기본 50만원
            else{
                System.out.println( " 해당 선택 없음.");
                info.setBubumsolatium(500000);
            }
            System.out.println(info.getBohumlossofbusiness() + "    보험사 휴업손해액 입니다.");
            System.out.println(info.getBohumsolatium() + "    보험사 위자료 입니다.");
            System.out.println(info.getBubumlossofbusiness() + "    법원 휴업손해액 입니다.");
            System.out.println(info.getBubumsolatium() + "    법원 위자료 입니다.");
        }
        if(info.getTreatmentNum() !=null && !info.getTreatmentNum().equals("")){
            info.setTransportcost(Integer.parseInt(info.getTreatmentNum()) *8000);
        }
        info.setBohumSum(info.getBohumlossofbusiness() +info.getAfterTreatmentFee()+info.getBohumsolatium() +info.getTransportcost());
        info.setBubumSum(info.getBubumlossofbusiness() +info.getAfterTreatmentFee()+info.getBubumsolatium() +info.getTransportcost());
        System.out.println(info.getTransportcost() + "    교통비 입니다.");

        int inforRsult = mainDao.docalculrateDao(info);
        System.out.println("서비스 시작");
        System.out.println(info + "   결과값");

        return info;
    }

    public List<damageInfo> mainListService(){

        List<damageInfo> inforRsult = mainDao.mainListDao();

        String accidentTypeResult = "";

        String accidentContentResult = "";
//        for(int i =0 ; i<inforRsult.size();i++){
//            System.out.println(inforRsult.get(i) + "         서비스 값");
//        }
        int accidentTypeSucheck =0;
        int accidentContentSucheck =0;

        for(int i =0 ; i<inforRsult.size();i++){
            if(inforRsult.get(i).getFace() ==1){
                if( accidentTypeSucheck <2){
                    accidentTypeResult +="얼굴, ";
                }
                accidentTypeSucheck++;
            } if(inforRsult.get(i).getWaist() ==1){
                if( accidentTypeSucheck <2) {
                    accidentTypeResult += "허리(척추), ";
                }
                accidentTypeSucheck++;
            } if(inforRsult.get(i).getNeck() ==1){
                if( accidentTypeSucheck <2) {
                    accidentTypeResult += "목, ";
                }
                accidentTypeSucheck++;
            } if(inforRsult.get(i).getHand() ==1){
                if( accidentTypeSucheck <2) {
                    accidentTypeResult += "팔(손), ";
                }
                accidentTypeSucheck++;
            } if(inforRsult.get(i).getBelly() ==1){
                if( accidentTypeSucheck <2) {
                    accidentTypeResult += "복부(가슴), ";
                }
                accidentTypeSucheck++;
            } if(inforRsult.get(i).getLeg() ==1){
                if( accidentTypeSucheck <2) {
                    accidentTypeResult += "다리(발), ";
                }
                accidentTypeSucheck++;
            } if(inforRsult.get(i).getTeeth() ==1){
                if( accidentTypeSucheck <2) {
                    accidentTypeResult += "어깨, ";
                }
                accidentTypeSucheck++;
            } if(inforRsult.get(i).getShoulder() ==1){
                if( accidentTypeSucheck <2) {
                    accidentTypeResult += "치아, ";
                }
                accidentTypeSucheck++;
            }

             if(inforRsult.get(i).getSprain() ==1){
                 if( accidentContentSucheck <2) {
                     accidentContentResult += "염좌, ";
                 }
                 accidentContentSucheck++;
            } if(inforRsult.get(i).getBruise() ==1){
                if( accidentContentSucheck <2) {
                    accidentContentResult += "타박상, ";
                }
                accidentContentSucheck++;
            } if(inforRsult.get(i).getFracture() ==1){
                if( accidentContentSucheck <2) {
                    accidentContentResult += "부러짐(골절), ";
                }
                accidentContentSucheck++;
            } if(inforRsult.get(i).getRipped() ==1){
                if( accidentContentSucheck <2) {
                    accidentContentResult += "찢어짐(창상), ";
                }
                accidentContentSucheck++;
            }

            if(accidentTypeSucheck >=3){
                accidentTypeResult +="...";
            }
            if( accidentContentSucheck >=3) {
                accidentContentResult +="...";
            }
            accidentTypeSucheck=0;
            accidentContentSucheck =0;
            inforRsult.get(i).setAccidentType(accidentTypeResult);
            inforRsult.get(i).setAccidentContent(accidentContentResult);
            accidentTypeResult = "";
            accidentContentResult = "";
        }

//        for(damageInfo dgInfo :inforRsult){
//            if(dgInfo.getFace() ==1){
//                accidentTypeResult +="얼굴, ";
//            }else if(dgInfo.getWaist() ==1){
//                accidentTypeResult +="허리(척추), ";
//            }else if(dgInfo.getNeck() ==1){
//                accidentTypeResult +="목, ";
//            }else if(dgInfo.getHand() ==1){
//                accidentTypeResult +="팔(손), ";
//            }else if(dgInfo.getBelly() ==1){
//                accidentTypeResult +="복부(가슴), ";
//            }else if(dgInfo.getLeg() ==1){
//                accidentTypeResult +="다리(발), ";
//            }else if(dgInfo.getTeeth() ==1){
//                accidentTypeResult +="어깨, ";
//            }else if(dgInfo.getShoulder() ==1){
//                accidentTypeResult +="치아, ";
//            }
//
//            else if(dgInfo.getSprain() ==1){
//                accidentContentResult += "염좌, ";
//            }else if(dgInfo.getBruise() ==1){
//                accidentContentResult += "염좌, ";
//            }else if(dgInfo.getFracture() ==1){
//                accidentContentResult += "염좌, ";
//            }else if(dgInfo.getRipped() ==1){
//                accidentContentResult += "염좌, ";
//            }
//            dgInfo.setAccidentType(accidentTypeResult);
//            dgInfo.setAccidentContent(accidentContentResult);
//        }


        return inforRsult;
    }
}
