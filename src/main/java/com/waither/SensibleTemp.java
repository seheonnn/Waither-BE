package com.waither;

import java.time.LocalDate;

public class SensibleTemp {


    // t : 기온(섭씨), v : 풍속(m/s), r : 상대습도(%)
    public static double calculateTemp(double t, double v, double r) {

        LocalDate now = LocalDate.now();
        int m = now.getMonthValue();

        if (4 < m && m < 10) // 여름철 불쾌지수
        {
            double tw = t * Math.atan(0.151977 * Math.pow(r + 8.313659, 1/2)) + Math.atan(t + r) - Math.atan(r - 1.67633) + 0.00391838 * Math.pow(r, 3 / 2) * Math.atan(0.023101 * r) - 4.686035;
            return Math.round(-0.2442 + 0.55399 * tw + 0.45535 * t - 0.0022 * Math.pow(tw,2) + 0.00278 * tw * t + 3.0);
        }
        else    // 겨울철 체감온도
            return Math.round(13.12 + (0.6215 * t) - (11.37 * Math.pow(v, 0.16)) + (0.3965 * Math.pow(v, 0.16) * t));
    }
}
