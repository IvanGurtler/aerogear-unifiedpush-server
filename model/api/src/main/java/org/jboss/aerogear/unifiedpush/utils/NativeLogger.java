package org.jboss.aerogear.unifiedpush.utils;

public class NativeLogger {
	private static final AeroGearLogger logger = AeroGearLogger.getInstance(NativeLogger.class);
	
    static {            
        String libraryLoadedValue = System.getProperty("NATIVE_PUSHSRV_LIBRARY_LOADED");

        //URL url = Thread.currentThread().getContextClassLoader().getResource("NativeLoggerPUSHSRV.so");
        String libraryPath = "/app/otp/casecore/lib/NativeLoggerPUSHSRV.so";//url.getPath();
        
        if (libraryLoadedValue == null) {          
            System.load(libraryPath);
            System.setProperty("NATIVE_PUSHSRV_LIBRARY_LOADED", "true");
        }
    }

    //definice musi byt stejna jako v nativni knihovne
    public static native void PUSHSRVLog(String option, String facility, String level, String process, String message);
}
