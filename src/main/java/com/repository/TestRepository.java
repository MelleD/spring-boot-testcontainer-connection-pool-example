package com.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

@Transactional( readOnly = true )
public class TestRepository {

   private JdbcTemplate jdbcTemplate;

   public TestRepository( JdbcTemplate jdbcTemplate ) {
      this.jdbcTemplate = jdbcTemplate;
   }

   @Transactional
   public int count() {
      return jdbcTemplate.queryForObject( "SELECT COUNT(*) FROM entity", Integer.class );
   }
}

