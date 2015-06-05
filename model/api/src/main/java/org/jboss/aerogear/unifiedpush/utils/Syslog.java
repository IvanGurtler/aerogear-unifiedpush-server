package org.jboss.aerogear.unifiedpush.utils;

import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Syslog {
	private static final AeroGearLogger logger = AeroGearLogger.getInstance(Syslog.class);
	
    public static void auditToSyslog(String eventId, String description, String facility) {

    	DateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    	String time = format.format(new Date());
    	  
        try {
            String auditMessage = time + "|" + eventId + "||" + InetAddress.getLocalHost().getHostName() + "|"
                    + InetAddress.getLocalHost().getHostAddress() + "|CZ||1|5||" + description + "|";

            NativeLogger.PUSHSRVLog("LOG_NDELAY", facility, "LOG_CRIT", "PUSHSRV", auditMessage);
        } catch (Error err) {
            logger.warning(err.getMessage());
        } catch (Exception ex) {
            logger.warning(ex.getMessage());
        }
    }
}
