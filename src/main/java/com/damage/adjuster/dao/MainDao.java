package com.damage.adjuster.dao;


import com.damage.adjuster.dto.damageInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MainDao {

   int docalculrateDao(damageInfo info);
}
