package main;

import java.io.IOException;

import static constant.ServiceConstants.START_XAMPP_EXE;
import static constant.ServiceConstants.STOP_XAMPP_EXE;

public class SettingsProvider {

    public static String getRunXamppServer() {
        return START_XAMPP_EXE;
    }

    public static String getStopXamppServer() {
        return STOP_XAMPP_EXE;
    }

    public static void startXAMPP() {
        try {
            Runtime.getRuntime().exec(getRunXamppServer());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stopXAMPP() {
        try {
            Runtime.getRuntime().exec(getStopXamppServer());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
