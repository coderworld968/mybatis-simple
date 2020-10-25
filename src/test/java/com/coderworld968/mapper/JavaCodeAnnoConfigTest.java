package com.coderworld968.mapper;

import com.coderworld968.model.Country;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.logging.log4j.Log4jImpl;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class JavaCodeAnnoConfigTest {

    private static SqlSessionFactory sqlSessionFactory;

    @BeforeClass
    public static void init(){
        try {
            UnpooledDataSource dataSource = new UnpooledDataSource(
                    "org.h2.Driver",
                    "jdbc:h2:mem:voice;INIT=RUNSCRIPT FROM 'classpath:/h2/schema-h2.sql'",
                    "sa",
                    "sa");
            TransactionFactory transactionFactory = new JdbcTransactionFactory();
            Environment environment = new Environment("Java", transactionFactory, dataSource);

            Configuration configuration = new Configuration(environment);
            configuration.getTypeAliasRegistry().registerAliases("com.coderworld968.model");
            configuration.setLogImpl(Log4jImpl.class);

            configuration.addMapper(CountryMapper.class);

            sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
    }

    @Test
    public void testSelectAll(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            List<Country> countryList = sqlSession.selectList("com.coderworld968.mapper.CountryMapper.selectAll");
            printCountryList(countryList);
        } finally {
            sqlSession.close();
        }
    }


    @Test
    public void testSelectAll2(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);
            List<Country> countryList = mapper.selectAll();
            printCountryList(countryList);
        } finally {
            sqlSession.close();
        }
    }

    private void printCountryList(List<Country> countryList){
        for(Country country : countryList){
            System.out.printf("%-4d%4s%4s\n",country.getId(), country.getCountryname(), country.getCountrycode());
        }
    }
}
