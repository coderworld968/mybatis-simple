package com.coderworld968.mapper;

import com.coderworld968.model.Country;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CountryMapper {

    @Select("select id,countryname,countrycode from country")
    List<Country> selectAll();
}
