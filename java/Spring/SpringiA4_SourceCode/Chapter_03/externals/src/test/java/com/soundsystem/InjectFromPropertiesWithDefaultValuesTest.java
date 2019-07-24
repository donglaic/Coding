package com.soundsystem;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=EnvironmentConfigWithDefaults.class)
public class InjectFromPropertiesWithDefaultValuesTest {

    @Autowired
    private BlankDisc blankDisc;

    @Test
    public void assertBlankDiscProperties() {
        assertEquals("U2", blankDisc.getArtist());
        assertEquals("Rattle and Hum", blankDisc.getTitle());
    }

}