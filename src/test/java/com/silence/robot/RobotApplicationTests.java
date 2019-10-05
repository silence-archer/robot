package com.silence.robot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RobotApplicationTests {

    @Autowired(required = false)
    private DataSource dataSource;

    @Test
    public void contextLoads() {
        try {
            System.out.printf(dataSource.getConnection().getSchema());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
