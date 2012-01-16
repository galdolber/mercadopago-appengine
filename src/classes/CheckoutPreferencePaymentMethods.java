package classes;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * @author amuract
 */
public class CheckoutPreferencePaymentMethods {

    List<CheckoutPreferenceExcludedPaymentMethods> excluded_payment_methods;
    List<CheckoutPreferenceExcludedPaymentTypes> excluded_payment_types;
    Long installments;

    public List<CheckoutPreferenceExcludedPaymentMethods> getExcludePaymentMethods() {
        return excluded_payment_methods;
    }

    public void setExcludePaymentMethods(List<CheckoutPreferenceExcludedPaymentMethods> exclude_payment_methods) {
        this.excluded_payment_methods = exclude_payment_methods;
    }

    public List<CheckoutPreferenceExcludedPaymentTypes> getExcludePaymentTypes() {
        return excluded_payment_types;
    }

    public void setExcludePaymentTypes(List<CheckoutPreferenceExcludedPaymentTypes> exclude_payment_types) {
        this.excluded_payment_types = exclude_payment_types;
    }

    public Long getInstallments() {
        return installments;
    }

    public void setInstallments(Long installments) {
        this.installments = installments;
    }

    @Override
    public String toString() {
        try {
            return this.toJson().toString(4);
        } catch (JSONException ex) {
            Logger.getLogger(CheckoutPreferencePaymentMethods.class.getName()).log(Level.SEVERE, null, ex);
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

        JSONArray jarray = new JSONArray();
        for (CheckoutPreferenceExcludedPaymentMethods epm : this.excluded_payment_methods) {
            jarray.put(epm.toJson());
        }
        json.put("excluded_payment_methods", jarray);

        jarray = new JSONArray();
        for (CheckoutPreferenceExcludedPaymentTypes ept : this.excluded_payment_types) {
            jarray.put(ept.toJson());
        }
        json.put("excluded_payment_types", jarray);

        json.put("installments", this.installments);

        return json;
    }


}
