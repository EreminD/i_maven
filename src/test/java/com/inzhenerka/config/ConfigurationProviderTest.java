package com.inzhenerka.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConfigurationProviderTest {

    @Test
    public void iCanLoadDefaultConfig(){
        Configuration config = ConfigurationProvider.load();

        assertNotNull(config);
    }

    @Test
    public void checkConfigValues(){
        Configuration config = ConfigurationProvider.load();
        
        assertEquals("src/path", config.getDst()); 
        assertEquals("dst/path", config.getDst()); 
        assertEquals(2, config.getDependencies().size());
        assertEquals(3, config.getRepositories().size());
    }   
    
    @Test
    public void checkDepsValues(){
        Configuration config = ConfigurationProvider.load();
        
        assertEquals("org.corp", config.getDependencies().get(0).getGroupId()); 
        assertEquals("art1", config.getDependencies().get(0).getArtifactId()); 
        assertEquals( "1.0.1", config.getDependencies().get(0).getVersion()); 
        
        assertEquals("ru.corp", config.getDependencies().get(0).getGroupId()); 
        assertEquals("art2", config.getDependencies().get(0).getArtifactId()); 
        assertEquals( "2.0.1", config.getDependencies().get(0).getVersion()); 
        
    }
}