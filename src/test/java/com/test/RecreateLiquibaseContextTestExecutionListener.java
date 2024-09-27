/*
 * Copyright (c) 2024 Robert Bosch Manufacturing Solutions GmbH, Germany. All rights reserved.
 */

package com.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestContextAnnotationUtils;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import liquibase.integration.spring.SpringLiquibase;

public class RecreateLiquibaseContextTestExecutionListener extends AbstractTestExecutionListener {

   private final Log logger = LogFactory.getLog( getClass() );

   @Override
   public void beforeTestClass( final TestContext testContext ) throws Exception {
      final RecreateLiquibaseContext recreateLiquibaseContext = TestContextAnnotationUtils.findMergedAnnotation( testContext.getTestClass(),
            RecreateLiquibaseContext.class );
      if ( recreateLiquibaseContext == null ) {
         return;
      }
      logger.info( "Recreate liquibase context" );
      final SpringLiquibase springLiquibase = testContext.getApplicationContext().getBean( SpringLiquibase.class );
      springLiquibase.setDropFirst( true );
      springLiquibase.afterPropertiesSet();
      logger.info( "Recreated liquibase context" );
   }

}
