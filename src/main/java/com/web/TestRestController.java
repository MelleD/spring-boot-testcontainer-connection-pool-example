package com.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.repository.TestRepository;

@RestController
public class TestRestController {

   private TestRepository testRepository;

   public TestRestController( final TestRepository testRepository ) {
      this.testRepository = testRepository;
   }
   
   @GetMapping( "/test" )
   ResponseEntity<Integer> count() {
      return ResponseEntity.ok( testRepository.count() );
   }
}
