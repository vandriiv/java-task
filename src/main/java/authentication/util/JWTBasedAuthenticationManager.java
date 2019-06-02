package authentication.util;

import token.util.JWTProvider;
import token.util.UserTokenClaimsData;
import com.google.gson.Gson;

public class JWTBasedAuthenticationManager {

    public UserTokenClaimsData getUsetDataFromAuthHeader(String header){

        UserTokenClaimsData tokenModel = null;

        if(header!=null) {
            if (header.contains("Bearer ")) {
                Gson jsonFormatter = new Gson();
                String token = header.substring("Bearer ".length());
                try {
                    String subject = JWTProvider.getSubjectFromToken(token);
                    tokenModel = jsonFormatter.fromJson(subject, UserTokenClaimsData.class);
                } catch (Exception ex) {

                }
            }
        }
        return  tokenModel;
    }
}
