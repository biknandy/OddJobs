package edu.ucsb.cs.cs184.oddjobs;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.TransactionInfo;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import sqip.CardEntry;
import sqip.GooglePay;
import sqip.InAppPaymentsSdk;


public class CheckoutActivity extends AppCompatActivity {
    public static String paymentAmount = "12";
    private static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 1;

    private final Handler handler = new Handler(Looper.getMainLooper());

    private GooglePayChargeClient googlePayChargeClient;
    private PaymentsClient paymentsClient;
    private OrderSheet orderSheet;

    private DatabaseReference ref;

    private String totalReward;
    public String userName;
    public String location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookie);



        Intent paymentIntent = getIntent();
        totalReward = paymentIntent.getStringExtra("price");
        userName = paymentIntent.getStringExtra("name");
        location = paymentIntent.getStringExtra("loc");

        TextView paymentView = (TextView)findViewById(R.id.paymentAmount);
        paymentView.setText(totalReward);
        googlePayChargeClient = (GooglePayChargeClient) getLastCustomNonConfigurationInstance();
        if (googlePayChargeClient == null) {
            googlePayChargeClient = ExampleApplication.createGooglePayChargeClient(this);
        }
        googlePayChargeClient.onActivityCreated(this);

        paymentsClient = Wallet.getPaymentsClient(this,
                new Wallet.WalletOptions.Builder()
                        .setEnvironment(WalletConstants.ENVIRONMENT_TEST)
                        .build());

        orderSheet = new OrderSheet();

        enableGooglePayButton(orderSheet);

        orderSheet.setOnPayWithCardClickListener(this::startCardEntryActivity);
        orderSheet.setOnPayWithGoogleClickListener(this::startGooglePayActivity);

        View buyButton = findViewById(R.id.buy_button);
        buyButton.setOnClickListener(v -> {
            if (InAppPaymentsSdk.INSTANCE.getSquareApplicationId().equals("REPLACE_ME")) {
                showMissingSquareApplicationIdDialog();
            } else {
                showOrderSheet();
                TextView edit = (TextView)findViewById(R.id.paymentAmount);
                Double amount = Double.parseDouble(edit.getText().toString());
                amount *= 100;
                paymentAmount = Integer.toString(amount.intValue());
            }
        });

        View completeButton = findViewById(R.id.complete_button);
        completeButton.setVisibility(View.INVISIBLE);

        completeButton.setOnClickListener(v -> {
            location = location.replaceAll("\\s+","");
            ref = FirebaseDatabase.getInstance()
                    .getReference("listings").child(userName).child(location);
            ref.child("status").setValue("complete");
            finish();
        });

    }

    @Override
    public GooglePayChargeClient onRetainCustomNonConfigurationInstance() {
        return googlePayChargeClient;
    }

    private void enableGooglePayButton(OrderSheet orderSheet) {
        Task<Boolean> readyToPayTask =
                paymentsClient.isReadyToPay(GooglePay.createIsReadyToPayRequest());
        readyToPayTask.addOnCompleteListener(this,
                (task) -> orderSheet.setPayWithGoogleEnabled(task.isSuccessful()));
    }

    private void startCardEntryActivity() {
        CardEntry.startCardEntryActivity(this);
    }

    private void startGooglePayActivity() {
        TextView edit = (TextView)findViewById(R.id.paymentAmount);

        TransactionInfo transactionInfo = TransactionInfo.newBuilder()
                .setTotalPriceStatus(WalletConstants.TOTAL_PRICE_STATUS_FINAL)
                .setTotalPrice(edit.getText().toString())
                .setCurrencyCode("USD")
                .build();

        PaymentDataRequest paymentDataRequest =
                GooglePay.createPaymentDataRequest(ConfigHelper.GOOGLE_PAY_MERCHANT_ID,
                        transactionInfo);

        Task<PaymentData> googlePayActivityTask = paymentsClient.loadPaymentData(paymentDataRequest);

        AutoResolveHelper.resolveTask(googlePayActivityTask, this, LOAD_PAYMENT_DATA_REQUEST_CODE);
    }

    private void showMissingSquareApplicationIdDialog() {
        new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert)
                .setTitle(R.string.missing_application_id_title)
                .setMessage(Html.fromHtml(getString(R.string.missing_application_id_message)))
                .setPositiveButton(android.R.string.ok, (dialog, which) -> showOrderSheet())
                .show();
    }

    private void showOrderSheet() {
        orderSheet.show(CheckoutActivity.this);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        CardEntry.handleActivityResult(data, cardEntryActivityResult -> {
            if (cardEntryActivityResult.isSuccess()) {
                if (!ConfigHelper.serverHostSet()) {
                    showServerHostNotSet();
                } else {
                    showSuccessCharge();


                }
            } else if (cardEntryActivityResult.isCanceled()) {
                Resources res = getResources();
                int delayMs = res.getInteger(R.integer.card_entry_activity_animation_duration_ms);
                handler.postDelayed(this::showOrderSheet, delayMs);
            }
        });

        if (requestCode == LOAD_PAYMENT_DATA_REQUEST_CODE) {
            handleGooglePayActivityResult(resultCode, data);
        }
    }

    private void handleGooglePayActivityResult(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (!ConfigHelper.merchantIdSet()) {
                showMerchantIdNotSet();
                return;
            }
            PaymentData paymentData = PaymentData.getFromIntent(data);
            if (paymentData != null && paymentData.getPaymentMethodToken() != null) {
                String googlePayToken = paymentData.getPaymentMethodToken().getToken();
                googlePayChargeClient.charge(googlePayToken);
            }
        } else {
            // The customer canceled Google Pay or an error happened, show the order sheet again.
            showOrderSheet();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!isChangingConfigurations()) {
            googlePayChargeClient.cancel();
        }
        googlePayChargeClient.onActivityDestroyed();
    }

    public void showError(String message) {
        showOkDialog(R.string.unsuccessful_order, message);
    }

    public void showSuccessCharge() {
        showOkDialog(R.string.successful_order_title, getString(R.string.successful_order_message));
        View buyButton = findViewById(R.id.buy_button);
        buyButton.setVisibility(View.INVISIBLE);
        View completeButton = findViewById(R.id.complete_button);
        completeButton.setVisibility(View.VISIBLE);
    }

    public void showServerHostNotSet() {
        showOkDialog(R.string.server_host_not_set_title, Html.fromHtml(getString(R.string.server_host_not_set_message)));
    }

    private void showMerchantIdNotSet() {
        showOkDialog(R.string.merchant_id_not_set_title, Html.fromHtml(getString(R.string.merchant_id_not_set_message)));
    }

    private void showOkDialog(int titleResId, CharSequence message) {
        new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert)
                .setTitle(titleResId)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    public void showNetworkErrorRetryPayment(Runnable retry) {
        new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert)
                .setTitle(R.string.network_failure_title)
                .setMessage(getString(R.string.network_failure))
                .setPositiveButton(R.string.retry, (dialog, which) -> retry.run())
                .show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        orderSheet.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        orderSheet.onRestoreInstanceState(this, savedInstanceState);
    }
}
