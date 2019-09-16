package com;

import com.entity.UserEntity;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException {
        //URL url = getClass().getClassLoader().getResource("file");
        String resource = "D:\\Program Files\\Java\\projects\\musclecoder\\openplateform-modules\\study-mybatis\\src\\main\\resources\\mybatis-config.xml";
        resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserEntity entity = sqlSession.selectOne("com.mapper.UserMapper.select", 1L);
        System.out.println(entity.getId() + "  ->  " + entity.getUserName());
        System.out.println("list :");
        List<UserEntity> list = sqlSession.selectList("com.mapper.UserMapper.selectAll");
        list.forEach(userEntity -> System.out.println(userEntity.getId() + "  ->  " + userEntity.getUserName()));

        sqlSession.close();
        inputStream.close();
    }
}
