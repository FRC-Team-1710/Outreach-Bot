package frc.utilities.math;

public class TempConvert {
    //** Returns in Celcius */
    public static double FtoC(double Ferinheight) {
        return (((Ferinheight-32)*5)/9);
    }

    //** Returns in Fahrenheit */
    public static double CtoF(double Celcius) {
        return (((Celcius*9)/5)+32);
    }
}
