package com.damage.adjuster.controller;

import com.damage.adjuster.dto.damageInfo;
import com.damage.adjuster.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/api/v1/app")
public class mainContoller {

    @Autowired
    MainService mainservice;

    @RequestMapping(value = "/")
    public ModelAndView home(@RequestParam Map<String, Object> reqParam, HttpServletRequest request, ModelAndView mv
    ) {
        System.out.println("시작");

        mv.setViewName("index");
//        damageInfo damageInfoDTO = new damageInfo();
        List<damageInfo> infoResult = mainservice.mainListService();



        mv.addObject("allcall",infoResult);


        System.out.println(infoResult.get(0) + "         컨트롤러 리턴값");
        System.out.println(infoResult.get(1) + "         컨트롤러 리턴값");
        System.out.println(infoResult.get(2) + "         컨트롤러 리턴값");

        return mv;

    }

    @RequestMapping(value = "/calculrate")
    public ResponseEntity<?> docalculrate(@RequestParam Map<String, Object> reqParam, HttpServletRequest request, ModelAndView mv, damageInfo damageInfoDTO
            ) {
        System.out.println("시작");

//        damageInfo damageInfoDTO = new damageInfo();
        System.out.println(damageInfoDTO);
        System.out.println(damageInfoDTO.getAccidentDate());
        System.out.println(damageInfoDTO.getAfterTreatmentFee());
        System.out.println(damageInfoDTO.getIncomeMonth());
            System.out.println(reqParam.get("birthdate"));
        System.out.println(reqParam.get("hospitalPeriod"));
        System.out.println(reqParam.get("incomeMonth"));
        damageInfo infoResult = mainservice.docalculrateService(damageInfoDTO);

        System.out.println(infoResult + "         컨트롤러 리턴값");

            return ResponseEntity.status(HttpStatus.OK).body(infoResult);

        }

}
