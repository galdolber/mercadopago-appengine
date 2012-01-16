package classes;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import utils.TimeUtils;

/**
 * @author amuract
 */
public class CheckoutPreference {

    String id;
    String external_reference;
    CheckoutPreferencePayer payer;
    List<CheckoutPreferenceItems> items;
    CheckoutPreferenceBackUrls back_urls;
    CheckoutPreferencePaymentMethods payment_methods;
    Long collector_id;
    Long subscription_plan_id;
    boolean expires;
    Date expiration_date_from;
    Date expiration_date_to;
    Date date_created;
    String init_point;

    public CheckoutPreferenceBackUrls getBackUrls() {
        return back_urls;
    }

    public void setBackUrls(CheckoutPreferenceBackUrls back_urls) {
        this.back_urls = back_urls;
    }

    public Long getCollectorId() {
        return collector_id;
    }

    public void setCollectorId(Long collector_id) {
        this.collector_id = collector_id;
    }

    public Date getDateCreated() {
        return date_created;
    }

    public void setDateCreated(Date date_created) {
        this.date_created = date_created;
    }

    public Date getExpirationDateFrom() {
        return expiration_date_from;
    }

    public void setExpirationDateFrom(Date expiration_date_from) {
        this.expiration_date_from = expiration_date_from;
    }

    public Date getExpirationDateTo() {
        return expiration_date_to;
    }

    public void setExpirationDateTo(Date expiration_date_to) {
        this.expiration_date_to = expiration_date_to;
    }

    public boolean isExpires() {
        return expires;
    }

    public void setExpires(boolean expires) {
        this.expires = expires;
    }

    public String getExternalReference() {
        return external_reference;
    }

    public void setExternalReference(String external_reference) {
        this.external_reference = external_reference;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInitPoint() {
        return init_point;
    }

    public void setInitPoint(String init_point) {
        this.init_point = init_point;
    }

    public List<CheckoutPreferenceItems> getItems() {
        return items;
    }

    public void setItems(List<CheckoutPreferenceItems> items) {
        this.items = items;
    }

    public CheckoutPreferencePayer getPayer() {
        return payer;
    }

    public void setPayer(CheckoutPreferencePayer payer) {
        this.payer = payer;
    }

    public CheckoutPreferencePaymentMethods getPaymentMethods() {
        return payment_methods;
    }

    public void setPaymentMethods(CheckoutPreferencePaymentMethods payment_methods) {
        this.payment_methods = payment_methods;
    }

    public Long getSubscriptionPlanId() {
        return subscription_plan_id;
    }

    public void setSubscriptionPlanId(Long subscription_plan_id) {
        this.subscription_plan_id = subscription_plan_id;
    }

    @Override
    public String toString() {
        try {
            return this.toJson().toString(4);
        } catch (JSONException ex) {
            Logger.getLogger(CheckoutPreference.class.getName()).log(Level.SEVERE, null, ex);
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
        json.put("id", this.id);
        json.put("external_reference", this.external_reference);
        json.put("payer", this.payer != null ? this.payer.toJson():null);

        JSONArray jarray = new JSONArray();
        for (CheckoutPreferenceItems itemsDto : this.items) {
            jarray.put(itemsDto.toJson());
        }
        json.put("items", jarray);
        
        json.put("back_urls", this.back_urls != null ? this.back_urls.toJson():null);
        json.put("payment_methods", this.payment_methods != null ? this.payment_methods.toJson():null);
        json.put("collector_id", this.collector_id);
        json.put("subscription_plan_id", this.subscription_plan_id);
        json.put("expires", this.expires);
        json.put("expiration_date_from", TimeUtils.dateToString(this.expiration_date_from));
        json.put("expiration_date_to", TimeUtils.dateToString(this.expiration_date_to));
        json.put("date_created", TimeUtils.dateToString(this.date_created));
        json.put("init_point", this.init_point);

        return json;
    }


}
