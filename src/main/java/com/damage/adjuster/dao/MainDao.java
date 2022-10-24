package com.damage.adjuster.dao;


import com.damage.adjuster.dto.damageInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MainDao {

   int docalculrateDao(damageInfo info);

   List<damageInfo> mainListDao();
}
