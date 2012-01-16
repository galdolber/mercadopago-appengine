package services;

import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

import classes.AccessData;

/**
 * @author amuract
 */
public class AuthService {

  URLFetchService service = URLFetchServiceFactory.getURLFetchService();

  /**
   * This Service receives the client_credentials and starts the request to
   * oAuth API. On success returns an accessData Object, otherwise returns an
   * exception.
   * 
   * @param client_id
   * @param client_secret
   * @return
   * @throws Exception
   */
  public AccessData createAccessData(String client_id, String client_secret) throws Exception {
    // set url format to data
    String url =
        String
            .format(
                "https://api.mercadolibre.com/oauth/token?grant_type=client_credentials&client_id=%s&client_secret=%s",
                URLEncoder.encode(client_id, "UTF-8"), URLEncoder.encode(client_secret, "UTF-8"));

    HTTPRequest request = new HTTPRequest(new URL(url), HTTPMethod.POST);
    request.addHeader(new HTTPHeader("Accept", "application/json"));

    HTTPResponse response = service.fetch(request);

    String oauthContent = new String(response.getContent(), "UTF-8");

    if (response.getResponseCode() != 200) {
      throw new Exception("Error in " + AuthService.class.getName() + "\n" + oauthContent);
    }
    // returns an AccessData Object
    return jsonToDto(new JSONObject(oauthContent));
  }

  /**
   * This Service receives the client_credentials and refresh_token to start the
   * request to oAuth API. On success returns an accessData Object with
   * autorization data(included the access_token refreshed), otherwise returns
   * an exception.
   * 
   * @param clientId
   * @param clientSecret
   * @param refreshToken
   * @return
   * @throws Exception
   */
  public AccessData refreshAccessToken(String clientId, String clientSecret, String refreshToken)
      throws Exception {
    // set url format to data
    String url =
        String
            .format(
                "https://api.mercadolibre.com/oauth/token?refresh_token=%s&client_id=%s&client_secret=%s&grant_type=refresh_token",
                URLEncoder.encode(refreshToken, "UTF-8"), URLEncoder.encode(clientId, "UTF-8"),
                URLEncoder.encode(clientSecret, "UTF-8"));

    HTTPRequest request = new HTTPRequest(new URL(url), HTTPMethod.POST);
    request.addHeader(new HTTPHeader("Accept", "application/json"));
    request.addHeader(new HTTPHeader("Content-Type", "application/x-www-form-urlencoded"));

    HTTPResponse response = service.fetch(request);

    String oauthContent = new String(response.getContent(), "UTF-8");

    if (response.getResponseCode() != 200) {
      throw new Exception("Error in " + AuthService.class.getName() + "\n" + oauthContent);
    }
    return jsonToDto(new JSONObject(oauthContent));
  }

  /**
   * Given a JSON that contains the information of AccessData, we obtain an
   * object thereof AccessData
   * 
   * @param json
   * @return
   * @throws Exception
   */
  private AccessData jsonToDto(JSONObject json) throws Exception {
    // AccessData class instance
    AccessData dto = new AccessData();
    try {
      // set object properties
      dto.setAccessToken(json.getString("access_token"));
      dto.setExpiresIn(json.getString("expires_in"));
      dto.setRefreshToken(json.getString("refresh_token"));
      dto.setScope(json.getString("scope"));
      dto.setTokenType(json.getString("token_type"));
      // returns an AccessData Object
      return dto;
    } catch (JSONException ex) {
      Logger.getLogger(AuthService.class.getName()).log(Level.SEVERE, null, ex);
      throw new Exception("Error in " + AuthService.class.getName() + ". Data=[" + json + "]", ex);
    }
  }

}
