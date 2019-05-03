package AuthenticationUtil;

import TokenUtil.JWTProvider;
import TokenUtil.UserTokenModel;
import com.google.gson.Gson;

public class JWTBasedAuthenticationManager {

    public UserTokenModel getUsetDataFromAuthHeader(String header){

        UserTokenModel tokenModel = null;

        if(header!=null) {
            if (header.contains("Bearer ")) {
                Gson jsonFormatter = new Gson();
                String token = header.substring("Bearer ".length());
                try {
                    String subject = JWTProvider.getSubjectFromToken(token);
                    tokenModel = jsonFormatter.fromJson(subject, UserTokenModel.class);
                } catch (Exception ex) {

                }
            }
        }
        return  tokenModel;
    }
}
