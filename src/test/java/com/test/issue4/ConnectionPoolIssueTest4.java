package com.test.issue4;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.support.TransactionTemplate;

import com.test.RecreateLiquibaseContext;
import com.test.config.SQLContainerConfiguration;
import com.test.config.TestConfig;

@ExtendWith( SpringExtension.class )
@SpringBootTest( classes = { TestConfig.class, SQLContainerConfiguration.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
@ActiveProfiles( "test" )
@EnableAutoConfiguration
@RecreateLiquibaseContext
class ConnectionPoolIssueTest4 {

   @Autowired
   private JdbcTemplate jdbcTemplate;

   @Autowired
   private TransactionTemplate transactionTemplate;

   @BeforeEach
   void setup() throws InterruptedException {
      Thread.sleep( 2000 );
      // give the pool some time to initialize
   }

   @Test
   void testConnection() throws SQLException {
      for ( int i = 0; i < 50; i++ ) {
         transactionTemplate.execute( transactionTemplate -> {
            int result = jdbcTemplate.queryForObject(
                  "SELECT COUNT(*) FROM entity", Integer.class );
            assertThat( result ).isZero();
            return null;
         } );
      }
   }

}
