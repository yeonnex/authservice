package com.example.authservice.myexpectedreturn;

import com.example.authservice.AccountRunner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) 설정을 주었는데,
 * 원래 Test 의 경우, 스프링이 자동으로 H2의 인메모리 데이터베이스를 로드한다.
 *
 * 근데 그렇게 하기 싫은 경우, 이 설정을 오버라이드할 수 있다.
 */

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test") // H2 데이터 소스 사용하게 설정해놓음
public class ConnectedDatabasePropertyPrintTest {
    @Autowired
    private ApplicationContext context;

    @Test
    public void dataSourceInfo() throws SQLException {
        DataSource ds = context.getBean(DataSource.class);
        DatabaseMetaData metaData = ds.getConnection().getMetaData();
        System.out.println("DataSource info -> \nURL: " + metaData.getURL() + ", \ndriver: " + metaData.getDriverName() + ", \nusername: " + metaData.getUserName());
    }

}


