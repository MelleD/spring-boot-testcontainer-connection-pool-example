/*
 * Copyright (c) 2024 Robert Bosch Manufacturing Solutions GmbH, Germany. All rights reserved.
 */
package com.test;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( { ElementType.TYPE, ElementType.METHOD } )
@Retention( RetentionPolicy.RUNTIME )
@Documented
@Inherited
public @interface RecreateLiquibaseContext {

}
