package classes;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * @author amuract
 */
public class AccessData {

    String access_token;
    String token_type;
    String expires_in;
    String scope;
    String refresh_token;

    public String getAccessToken() {
        return access_token;
    }

    public void setAccessToken(String access_token) {
        this.access_token = access_token;
    }

    public String getExpiresIn() {
        return expires_in;
    }

    public void setExpiresIn(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefreshToken() {
        return refresh_token;
    }

    public void setRefreshToken(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getTokenType() {
        return token_type;
    }

    public void setTokenType(String token_type) {
        this.token_type = token_type;
    }

    @Override
    public String toString() {
        try {
            return this.toJson().toString(4);
        } catch (JSONException ex) {
            Logger.getLogger(AccessData.class.getName()).log(Level.SEVERE, null, ex);
            return ex.toString();
        }
    }

    /**
     * Generates a JSONObject from the object 
     * @return
     * @throws JSONException
     */
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("access_token", this.access_token);
        json.put("token_type", this.token_type);
        json.put("expires_in", this.expires_in);
        json.put("refresh_token", this.refresh_token);

        return json;
    }
}
