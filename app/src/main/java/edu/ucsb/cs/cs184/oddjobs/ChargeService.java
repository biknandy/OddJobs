package edu.ucsb.cs.cs184.oddjobs;

import android.util.Log;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ChargeService {
    @POST("/chargeForCookie")
    Call<Void> charge(@Body ChargeRequest request);

    class ChargeErrorResponse {
        String errorMessage;
    }

    class ChargeRequest {
        final String nonce;
        final String amount;
        ChargeRequest(String nonce) {
            this.nonce = nonce;
            this.amount = CheckoutActivity.paymentAmount;
            Log.d("CHARGE",this.amount);
        }
    }
}