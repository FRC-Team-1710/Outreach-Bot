package frc.utilities.util;

public class GetHighest {
    public static double getHighest(double a, double b) {
        if (a>=b) {
            return a;
        } else {
            return b;
        }
    }

    public static double getHighest(double a, double b, double c) {
        if (a>=b && a>=c) {
            return a;
        } else if (b>=a && b>=c) {
            return b;
        } else {
            return c;
        }
    }

    public static double getHighest(double a, double b, double c, double d) {
        if (a>=b && a>=c && a>=d) {
            return a;
        } else if (b>=a && b>=c && b>=d) {
            return b;
        } else if (c>=a && c>=b && c>=d) {
            return c;
        } else {
            return d;
        }
    }

    public static double getHighest(double a, double b, double c, double d, double e, double f, double g, double h) {
        if (a>=b && a>=c && a>=d && a>=e && a>=f && a>=g && a>=h) {
            return a;
        } else if (b>=a && b>=c && b>=d && b>=e && b>=f && b>=g && b>=h) {
            return b;
        } else if (c>=b && c>=a && c>=d && c>=e && c>=f && c>=g && c>=h) {
            return c;
        } else if (d>=b && d>=c && d>=a && d>=e && d>=f && d>=g && d>=h) {
            return d;
        } else if (e>=b && e>=c && e>=d && e>=a && e>=f && e>=g && e>=h) {
            return e;
        } else if (f>=a && f>=c && f>=d && f>=e && f>=b && f>=g && f>=h) {
            return f;
        } else if (g>=b && g>=a && g>=d && g>=e && g>=f && g>=c && g>=h) {
            return g;
        } else {
            return h;
        }
    }
}
