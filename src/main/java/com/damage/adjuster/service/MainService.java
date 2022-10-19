package com.damage.adjuster.service;


import com.damage.adjuster.dao.MainDao;
import com.damage.adjuster.dto.damageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public int docalculrateService(damageInfo info){

        if(info.getIncomeMonth() !=null || !info.getIncomeMonth().equals("")) {
            // 휴업손해액 보험사 - (월소득/30)*입원기간(일)*0.85
            info.setBohumlossofbusiness((int) ((Long.parseLong(info.getIncomeMonth())*Long.parseLong(info.getHospitalPeriod()) / 30) * 0.85));
            //    법원 - (월소득/30)*입원기간(일) *0.95
            info.setBubumlossofbusiness((int) ((Long.parseLong(info.getIncomeMonth())*Long.parseLong(info.getHospitalPeriod()) / 30) * 0.95));
            // 위자료 보험사 –  15만원 통일
            info.setBohumsolatium(15);
            // 법원 – 척추 - 염좌 선택시 120만원
            System.out.println(info.getWaist() + " 허리.");
            System.out.println(info.getSprain() + " 염좌.");
            System.out.println(info.getRipped()+ " 찢어짐");
            if(info.getWaist() ==1 && info.getSprain() ==1){
                System.out.println( " 척추 염좌 선택.");
                info.setBubumsolatium(120);
            }
            //전체 – 찢어짐 선택시 120만원
           else if(info.getRipped() ==1){
                System.out.println( " 찢어짐 선택.");
                info.setBubumsolatium(120);
            }
            // 나머지는 기본 50만원
            else{
                System.out.println( " 해당 선택 없음.");
                info.setBubumsolatium(50);
            }
            System.out.println(info.getBohumlossofbusiness() + "    보험사 휴업손해액 입니다.");
            System.out.println(info.getBohumsolatium() + "    보험사 위자료 입니다.");
            System.out.println(info.getBubumlossofbusiness() + "    법원 휴업손해액 입니다.");
            System.out.println(info.getBubumsolatium() + "    법원 위자료 입니다.");
        }
        if(info.getTreatmentNum() !=null && !info.getTreatmentNum().equals("")){
            info.setTransportcost(Integer.parseInt(info.getTreatmentNum()) *8000);
        }
        System.out.println(info.getTransportcost() + "    교통비 입니다.");

        int inforRsult = mainDao.docalculrateDao(info);
        System.out.println("서비스 시작");
        System.out.println(info + "   결과값");

        return inforRsult;
    }
}
