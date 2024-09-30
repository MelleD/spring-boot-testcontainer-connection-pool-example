package com.test.issue3;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.sql.SQLException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.support.TransactionTemplate;

import com.test.config.SQLContainerConfiguration;
import com.test.config.TestConfig;
import com.web.TestWebConfig;

@ExtendWith( SpringExtension.class )
@SpringBootTest( classes = { TestConfig.class, SQLContainerConfiguration.class,
      TestWebConfig.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
@ActiveProfiles( "test" )
@EnableAutoConfiguration
class ConnectionPoolIssueTest3 {

   @Autowired
   private WebTestClient webTestClient;

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
   void testConnection() {
      for ( int i = 0; i < 50; i++ ) {
         Integer count = webTestClient.get().uri( uriBuilder -> uriBuilder.path(
                           "/test" )
                     .build() )
               .exchange()
               .expectStatus().isOk().expectBody( Integer.class ).returnResult().getResponseBody();
         Assertions.assertThat( count ).isZero();
      }
   }

   @Test
   void testConnection2() throws SQLException {
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