/**
 * JBoss, Home of Professional Open Source
 * Copyright Red Hat, Inc., and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.aerogear.unifiedpush.service.metrics;

import java.io.File;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;

import org.jboss.aerogear.unifiedpush.utils.AeroGearLogger;

@Singleton
public class DeleteOldPushMessageInformationScheduler {

    @Inject
    private PushMessageMetricsService service;

    private final AeroGearLogger logger = AeroGearLogger.getInstance(DeleteOldPushMessageInformationScheduler.class);
    
    /**
     * Job that triggers a delete of outdated metric information from the Server.
     *
     * Note: Occurring every day at midnight in the default time zone associated with the container
     * in which the application is executing. These are the default values from the @Schedule annotation.
     */
    @Schedule
    public void deleteOutdatedMetrics(){    	
    	logger.finest("start delete service");
    	
    	String server_log_dir = "empty";
    	String server_temp_dir = "empty";
    	String server_data_dir = "empty";
    	try{
    		server_log_dir = System.getenv("jboss.server.log.dir");
    		server_temp_dir = System.getenv("jboss.server.temp.dir");  
    		server_data_dir = System.getenv("jboss.server.data.dir");   
    		
    		logger.finest("server_log_dir" + server_log_dir);
    		logger.finest("server_temp_dir" + server_temp_dir);
    		logger.finest("server_data_dir" + server_data_dir);
    	}catch(Exception e){
    		logger.warning("exception " + e.getMessage());
    	}    
    	
    	File f = new File("/jboss/servers/jbmaster");
    	if ((f.exists() && f.isDirectory()) || server_log_dir.contains("server-1") 
    			|| server_temp_dir.contains("server-1") || server_data_dir.contains("server-1")) {
    		logger.finest("I am master or server-1. I start delete outdated push information data.");
    		service.deleteOutdatedPushInformationData();    		    		
    	}
    	else
    	{
    		logger.finest("I am not master. I do nothing.");
    	}
    }
}
