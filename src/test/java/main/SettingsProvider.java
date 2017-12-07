package main;

public class SettingsProvider {

    private static final String START_XAMPP_EXE = "C:\\xampp\\xampp_start.exe";
    private static final String STOP_XAMPP_EXE = "C:\\xampp\\xampp_stop.exe";

    public static String getRunXamppServer() {
        return START_XAMPP_EXE;
    }

    public static String getStopXamppServer() {
        return STOP_XAMPP_EXE;
    }
}
