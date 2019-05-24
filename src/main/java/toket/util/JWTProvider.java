package toket.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;

public class JWTProvider {

    private static Key key =  Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String generateToken(String subject){

        String jws = Jwts.builder()
                .setSubject(subject).signWith(key)
                .setExpiration(generateExpirationDate()).compact();

        return jws;
    }

    private static Date generateExpirationDate(){
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        dt = c.getTime();
        return  dt;
    }

    public static String getSubjectFromToken(String jws){

        String subject = Jwts.parser().setSigningKey(key).parseClaimsJws(jws).getBody().getSubject();

        return subject;
    }

}
