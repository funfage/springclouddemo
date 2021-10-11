package com.example.zrf.test;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    public static final String SECRET = "Abcd1234Abcd1234Abcd1234Abcd1234";

    public static final String iss = "BO1";

    public static final String profileId = "trackerProfile";

    private static Date getExpiryDateTime(int value, Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.MINUTE, value);
        return ca.getTime();
    }

    private static Date getCurrentDateTime(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        return ca.getTime();
    }

    public static String createToken(String uetr) {
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        Date date = new Date();
        return JWT.create()
                .withHeader(map)
                .withClaim("iss", iss)
                .withClaim("profileId", profileId)
                .withClaim("absPath", "https://sandbox.swift.com/swift-apitracker-transactions-and-cancellations/v5/payments/changed/transactions?from_date_time=2020-04-25T06:26:02.750Z&to_date_time=2020-06-16T14:19:54.604Z&maximum_number=10")
                .withClaim("jti", uetr)
                .withClaim("nr", false)
                .withIssuedAt(getCurrentDateTime(date))
                .withExpiresAt(getExpiryDateTime(30, date))
                .sign(Algorithm.HMAC256(SECRET));
    }

    public static void main(String[] args) {
        System.out.println(createToken("123456"));
    }

}
