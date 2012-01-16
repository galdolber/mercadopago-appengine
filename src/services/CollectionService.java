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

import classes.Collection;
import classes.CollectionFiltersSearch;
import classes.CollectionPayer;
import classes.CollectionPayerPhone;
import classes.ListCollection;
import utils.NumberUtils;
import utils.TimeUtils;

/**
 * @author amuract
 */
public class CollectionService {

  URLFetchService service = URLFetchServiceFactory.getURLFetchService();

  /**
   * This Service receives an array of filters and an access token to execute a
   * Search request to Collection API. On success return a list of collection
   * Objects, otherwise returns an exception.
   * 
   * @param filters
   * @param accessToken
   * @return
   * @throws Exception
   */
  public ListCollection searchCollection(CollectionFiltersSearch filters, String accessToken)
      throws Exception {

    // set url format to data
    String searchCollectionURL =
        String
            .format(
                "https://api.mercadolibre.com/collections/search?site_id=%s&id=%s&external_reference=%s&access_token=%s&offset=%s&limit=%s",
                filters.getSite_id(), filters.getId() == null ? "" : filters.getId(), filters
                    .getExternal_reference(), accessToken, filters.getOffset(), filters.getLimit());

    HTTPRequest request = new HTTPRequest(new URL(searchCollectionURL), HTTPMethod.GET);
    request.setHeader(new HTTPHeader("Accept", "application/json"));
    HTTPResponse response = service.fetch(request);

    String collectionContent = new String(response.getContent(), "UTF-8");

    if (response.getResponseCode() != 200) {
      throw new Exception("Error in " + CollectionService.class.getName() + "\n"
          + collectionContent);
    }

    return jsonArrayToDto(new JSONObject(collectionContent));

  }

  /**
   * Given a JSON, is mapped to an object that contains the list of collections,
   * as well as the total, the limit and offset
   * 
   * @param json
   * @return
   * @throws JSONException
   */
  private ListCollection jsonArrayToDto(JSONObject json) throws JSONException {
    // Obtenemos el listado de collections
    JSONArray jarray = json.getJSONArray("results");
    List<Collection> listDtos = new ArrayList<Collection>();
    for (int i = 0; i < jarray.length(); i++) {
      listDtos.add(jsonToDto(jarray.getJSONObject(i).getJSONObject("collection")));
    }
    ListCollection listCollectionDto = new ListCollection();
    listCollectionDto.setTotal(json.getJSONObject("paging").getLong("total"));
    listCollectionDto.setLimit(json.getJSONObject("paging").getLong("limit"));
    listCollectionDto.setOffset(json.getJSONObject("paging").getLong("offset"));

    listCollectionDto.setListCollection(listDtos);

    return listCollectionDto;
  }

  /**
   * Given a JSON that contains the information of collection, we obtain an
   * object thereof Collection
   * 
   * @param json
   * @return
   * @throws JSONException
   */
  private Collection jsonToDto(JSONObject json) throws JSONException {
    Collection dto = new Collection();

    try {
      // Mapeamos el json al objeto
      dto.setId(json.getLong("id"));
      dto.setSiteId(json.isNull("site_id") ? "" : json.getString("site_id"));
      dto.setDateCreated(TimeUtils.getDate(json.getString("date_created")));
      dto.setDateApproved(TimeUtils.getDate(json.getString("date_approved")));
      dto.setLastModified(TimeUtils.getDate(json.getString("last_modified")));
      dto.setMoneyReleaseDate(TimeUtils.getDate(json.isNull("money_release_date") ? null : json
          .getString("money_release_date")));
      dto.setCollectorId(json.isNull("collector") ? null : json.getJSONObject("collector").getLong(
          "id"));
      dto.setOperationType(json.isNull("operation_type") ? "" : json.getString("operation_type"));

      // Mapeamos los datos del payer
      if (!json.isNull("payer")) {
        JSONObject jsonPayer = json.getJSONObject("payer");
        CollectionPayer payer = new CollectionPayer();
        payer.setId(jsonPayer.getLong("id"));
        payer.setNickname(jsonPayer.isNull("nickname") ? "" : jsonPayer.getString("nickname"));
        payer.setFirstName(jsonPayer.isNull("first_name") ? "" : jsonPayer.getString("first_name"));
        payer.setLastName(jsonPayer.isNull("last_name") ? "" : jsonPayer.getString("last_name"));
        payer.setEmail(jsonPayer.isNull("email") ? "" : jsonPayer.getString("email"));

        if (!jsonPayer.isNull("phone")) {
          JSONObject jsonPhone = jsonPayer.getJSONObject("phone");
          CollectionPayerPhone phone = new CollectionPayerPhone();
          phone.setAreaCode(jsonPhone.isNull("area_code") ? "" : jsonPhone.getString("area_code"));
          phone.setNumber(jsonPhone.isNull("number") ? "" : jsonPhone.getString("number"));
          phone.setExtension(jsonPhone.isNull("extension") ? "" : jsonPhone.getString("extension"));
          payer.setPhone(phone);
        }

        dto.setPayer(payer);
      }

      dto.setExternalReference(json.isNull("external_reference") ? "" : json
          .getString("external_reference"));
      dto.setReason(json.isNull("reason") ? "" : json.getString("reason"));
      dto.setTransactionAmount(NumberUtils.getBigDecimal(json.getString("transaction_amount")));
      dto.setCurrencyId(json.isNull("currency_id") ? "" : json.getString("currency_id"));
      dto.setShippingCost(NumberUtils.getBigDecimal(json.isNull("shipping_cost") ? "" : json
          .getString("shipping_cost")));
      dto.setTotalPaidAmount(NumberUtils.getBigDecimal(json.isNull("total_paid_amount") ? "" : json
          .getString("total_paid_amount")));
      dto.setMercadopagoFee(NumberUtils.getBigDecimal(json.isNull("mercadopago_fee") ? "" : json
          .getString("mercadopago_fee")));
      dto.setNetReceivedAmount(NumberUtils.getBigDecimal(json.isNull("net_received_amount") ? ""
          : json.getString("net_received_amount")));
      dto.setStatus(json.isNull("status") ? "" : json.getString("status"));
      dto.setStatusDetail(json.isNull("status_detail") ? "" : json.getString("status_detail"));
      dto.setReleased(json.isNull("released") ? "" : json.getString("released"));
      dto.setPaymentMethodId(json.isNull("payment_method_id") ? "" : json
          .getString("payment_method_id"));
      dto.setMarketplace(json.isNull("marketplace") ? "" : json.getString("marketplace"));

      return dto;

    } catch (Exception ex) {
      Logger.getLogger(AuthService.class.getName()).log(Level.SEVERE, null, ex);
      throw new JSONException("Error al mapear el json. Data=[" + json + "]");
    }
  }
}
