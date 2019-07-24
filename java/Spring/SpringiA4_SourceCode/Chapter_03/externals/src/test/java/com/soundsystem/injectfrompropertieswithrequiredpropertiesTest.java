package com.soundsystem;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

public class injectfrompropertieswithrequiredpropertiesTest {

  @Test(expected=BeanCreationException.class)
  public void assertBlankDiscProperties() {
    new AnnotationConfigApplicationContext(EnvironmentConfigWithRequiredProperties.class);
  }

}
