package ir.piana.dev.struts.data.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class TestDao {
    @Autowired
    private DataSource dataSource;

    @Transactional
    public String getAppParam() throws SQLException {
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from APP_PARAM where PARAM_CODE = 'GL_GROUP_LENGTH'");
        String paramValue = null;
        if(resultSet != null && resultSet.next()) {
            paramValue = resultSet.getString("param_value");
        }

        return paramValue;
    }
}
