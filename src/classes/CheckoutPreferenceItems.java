package classes;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * @author amuract
 */
public class CheckoutPreferenceItems {

    String id;
    String title;
    String description;
    Long quantity;
    BigDecimal unit_price;
    String currency_id;
    String picture_url;

    public String getCurrencyId() {
        return currency_id;
    }

    public void setCurrencyId(String currency_id) {
        this.currency_id = currency_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPictureUrl() {
        return picture_url;
    }

    public void setPictureUrl(String picture_url) {
        this.picture_url = picture_url;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getUnitPrice() {
        return unit_price;
    }

    public void setUnitPrice(BigDecimal unit_price) {
        this.unit_price = unit_price;
    }

    @Override
    public String toString() {
        try {
            return this.toJson().toString(4);
        } catch (JSONException ex) {
            Logger.getLogger(CheckoutPreferenceItems.class.getName()).log(Level.SEVERE, null, ex);
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
        json.put("title", this.title);
        json.put("description", this.description);
        json.put("quantity", this.quantity);
        json.put("unit_price", this.unit_price);
        json.put("currency_id", this.currency_id);
        json.put("picture_url", this.picture_url);

        return json;
    }
}
