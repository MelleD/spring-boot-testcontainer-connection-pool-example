package com.test.issue4;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.test.config.SQLContainerConfiguration;
import com.test.config.TestConfig;
import com.web.TestWebConfig;

@ExtendWith( SpringExtension.class )
@SpringBootTest( classes = { TestConfig.class, SQLContainerConfiguration.class,
      TestWebConfig.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
@ActiveProfiles( "test" )
@EnableAutoConfiguration
class ConnectionPoolIssueTest6 {

   @Autowired
   private WebTestClient webTestClient;

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

}
