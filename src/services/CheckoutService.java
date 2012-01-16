package services;

import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import classes.CheckoutPreference;
import classes.CheckoutPreferenceBackUrls;
import classes.CheckoutPreferenceExcludedPaymentMethods;
import classes.CheckoutPreferenceExcludedPaymentTypes;
import classes.CheckoutPreferenceItems;
import classes.CheckoutPreferencePayer;
import classes.CheckoutPreferencePaymentMethods;
import utils.NumberUtils;
import utils.TimeUtils;

/**
 * @author amuract
 */
public class CheckoutService {

  URLFetchService service = URLFetchServiceFactory.getURLFetchService();

  /**
   * This Service receives an access token and checkoutPreferenceData Object to
   * execute a Post request to Preference API. On success returns a
   * CheckoutPreference Object, otherwise returns an exception. Once you created
   * the checkout preference, you can use init_point url to open checkout flow.
   * 
   * @param checkoutPreferenceData
   * @param accessToken
   * @return
   * @throws Exception
   */
  public CheckoutPreference createCheckoutPreference(CheckoutPreference checkoutPreferenceData,
      String accessToken) throws Exception {

    // set url format to data
    String postPreferenceURL =
        "https://api.mercadolibre.com/checkout/preferences?access_token=" + accessToken;

    HTTPRequest request = new HTTPRequest(new URL(postPreferenceURL), HTTPMethod.POST);
    request.addHeader(new HTTPHeader("Accept", "application/json"));
    request.addHeader(new HTTPHeader("Content-Type", "application/json"));
    request.setPayload(checkoutPreferenceData.toJson().toString().getBytes());

    HTTPResponse response = service.fetch(request);

    String preferenceContent = new String(response.getContent(), "UTF-8");

    if (response.getResponseCode() != 201) {
      throw new Exception("Error in " + CheckoutService.class.getName() + "\n" + preferenceContent);
    }
    // Request OK
    // returns an CheckoutPreference Object
    return jsonToDto(new JSONObject(preferenceContent));
  }

  /**
   * This Service receives an access token and a checkoutPreference Id and
   * starts a Get request to Preference API. On success returns a
   * CheckoutPreference Object, otherwise returns an exception.
   * 
   * @param preferenceId
   * @param accessToken
   * @return
   * @throws Exception
   */
  public CheckoutPreference getCheckoutPreference(String preferenceId, String accessToken)
      throws Exception {
    // set url format to data
    String getPreferenceURL =
        String.format("https://api.mercadolibre.com/checkout/preferences/%s?access_token=%s",
            preferenceId, accessToken);

    HTTPRequest request = new HTTPRequest(new URL(getPreferenceURL), HTTPMethod.GET);
    request.addHeader(new HTTPHeader("Accept", "application/json"));

    HTTPResponse response = service.fetch(request);

    String preferenceContent = new String(response.getContent(), "UTF-8");

    if (response.getResponseCode() != 200) {
      throw new Exception("Error in " + CheckoutService.class.getName() + "\n" + preferenceContent);
    }
    // Request OK
    // returns an CheckoutPreference Object
    return jsonToDto(new JSONObject(preferenceContent));
  }

  /**
   * This Service receives a checkoutPreferenceData Object and access token to
   * start a Put request to Preference API. On success returns a 201
   * status,otherwise returns an exception.
   * 
   * @param preferenceId
   * @param updateData
   * @param accessToken
   * @return
   * @throws Exception
   */
  public int updateCheckoutPreference(String preferenceId, CheckoutPreference updateData,
      String accessToken) throws Exception {

    // set url format to data
    String putPreferenceURL =
        String.format("https://api.mercadolibre.com/checkout/preferences/%s?access_token=%s",
            preferenceId, accessToken);

    HTTPRequest request = new HTTPRequest(new URL(putPreferenceURL), HTTPMethod.POST);
    request.addHeader(new HTTPHeader("Accept", "application/json"));
    request.addHeader(new HTTPHeader("Content-Type", "application/json"));
    request.setPayload(updateData.toJson().toString().getBytes());

    HTTPResponse response = service.fetch(request);

    if (response.getResponseCode() != 201) {
      throw new Exception("Error in " + CheckoutService.class.getName() + "\n"
          + new String(response.getContent(), "UTF-8"));
    }
    // Request OK
    return response.getResponseCode();
  }

  /**
   * Given a JSON that contains the information of checkout preference, we
   * obtain an object thereof CheckoutPreference
   * 
   * @param json
   * @return checkoutPreference Object
   * @throws JSONException
   */
  private CheckoutPreference jsonToDto(JSONObject json) throws JSONException {
    CheckoutPreference dto = new CheckoutPreference();
    try {
      // We mapped the list of items
      if (!json.isNull("items")) {
        JSONArray jarrayItems = json.getJSONArray("items");
        List<CheckoutPreferenceItems> items = new ArrayList<CheckoutPreferenceItems>();
        CheckoutPreferenceItems item;

        for (int i = 0; i < jarrayItems.length(); i++) {
          JSONObject jsonItem = jarrayItems.getJSONObject(i);
          item = new CheckoutPreferenceItems();
          item.setId(jsonItem.getString("id"));
          item.setTitle(jsonItem.getString("title"));
          item.setDescription(jsonItem.getString("description"));
          item.setQuantity(NumberUtils.getLong(jsonItem.getString("quantity")));
          item.setUnitPrice(NumberUtils.getBigDecimal(jsonItem.getString("unit_price")));
          item.setCurrencyId(jsonItem.getString("currency_id"));
          item.setPictureUrl(jsonItem.getString("picture_url"));
          items.add(item);
        }
        dto.setItems(items);
      }
      // Payer data mapped
      if (!json.isNull("payer")) {
        JSONObject jsonPayer = json.getJSONObject("payer");
        CheckoutPreferencePayer payer = new CheckoutPreferencePayer();
        payer.setName(jsonPayer.getString("name"));
        payer.setSurname(jsonPayer.getString("surname"));
        payer.setEmail(jsonPayer.getString("email"));

        dto.setPayer(payer);
      }
      // Map the return urls
      if (!json.isNull("back_urls")) {
        JSONObject jsonBackUrls = json.getJSONObject("back_urls");
        CheckoutPreferenceBackUrls backUrls = new CheckoutPreferenceBackUrls();
        backUrls.setSuccess(jsonBackUrls.getString("success"));
        backUrls.setPending(jsonBackUrls.getString("pending"));
        backUrls.setFailure(jsonBackUrls.getString("failure"));

        dto.setBackUrls(backUrls);
      }
      // We mapped the means of payment
      if (!json.isNull("payment_methods")) {

        CheckoutPreferencePaymentMethods pmDto = new CheckoutPreferencePaymentMethods();

        // We collect and we set the means of payment
        List<CheckoutPreferenceExcludedPaymentMethods> listEpm =
            new ArrayList<CheckoutPreferenceExcludedPaymentMethods>();
        if (!json.getJSONObject("payment_methods").isNull("excluded_payment_methods")) {
          JSONArray jarrayEpm =
              json.getJSONObject("payment_methods").getJSONArray("excluded_payment_methods");
          for (int i = 0; i < jarrayEpm.length(); i++) {
            JSONObject jsonEpm = jarrayEpm.getJSONObject(i);
            CheckoutPreferenceExcludedPaymentMethods epmDto =
                new CheckoutPreferenceExcludedPaymentMethods();
            epmDto.setId(jsonEpm.getString("id"));
            listEpm.add(epmDto);
          }
        }
        pmDto.setExcludePaymentMethods(listEpm);

        // We collect and we set the types of payment methods
        List<CheckoutPreferenceExcludedPaymentTypes> listEpt =
            new ArrayList<CheckoutPreferenceExcludedPaymentTypes>();
        if (!json.getJSONObject("payment_methods").isNull("excluded_payment_types")) {
          JSONArray jarrayEpt =
              json.getJSONObject("payment_methods").getJSONArray("excluded_payment_types");
          for (int i = 0; i < jarrayEpt.length(); i++) {
            JSONObject jsonEpt = jarrayEpt.getJSONObject(i);
            CheckoutPreferenceExcludedPaymentTypes eptDto =
                new CheckoutPreferenceExcludedPaymentTypes();
            eptDto.setId(jsonEpt.getString("id"));
            listEpt.add(eptDto);
          }
        }
        pmDto.setExcludePaymentTypes(listEpt);

        // We collect and we set the maximum amount of fees for this preference
        pmDto.setInstallments(json.getJSONObject("payment_methods").isNull("installments") ? null
            : json.getJSONObject("payment_methods").getLong("installments"));
        dto.setPaymentMethods(pmDto);
      }
      // To map the json object
      dto.setId(json.getString("id"));
      dto.setExternalReference(json.getString("external_reference"));
      dto.setCollectorId(json.getLong("collector_id"));
      dto.setSubscriptionPlanId(json.isNull("subscription_plan_id") ? null : json
          .getLong("subscription_plan_id"));
      dto.setInitPoint(json.getString("init_point"));
      dto.setExpires(json.getBoolean("expires"));

      dto.setExpirationDateFrom(TimeUtils.getDate(json.getString("expiration_date_from")));
      dto.setExpirationDateTo(TimeUtils.getDate(json.getString("expiration_date_to")));
      dto.setDateCreated(TimeUtils.getDate(json.getString("date_created")));

      // returns a checkoutPreference Object
      return dto;
    } catch (Exception ex) {
      Logger.getLogger(AuthService.class.getName()).log(Level.SEVERE, null, ex);
      throw new JSONException("Error al mapear el json. Data=[" + json + "]");
    }
  }
}
