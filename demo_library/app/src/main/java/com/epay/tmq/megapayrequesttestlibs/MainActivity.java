package com.epay.tmq.megapayrequesttestlibs;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.epay.example.tmq.megapayexamplerequest.megapay.MgpRequest;
import com.epay.example.tmq.megapayexamplerequest.megapay.MgpRequestBuilder;
import com.epay.example.tmq.megapayexamplerequest.megapay.MgpRequestHandler;
import com.epay.example.tmq.megapayexamplerequest.megapay.entity.ResultRequest;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RESULT_REQUEST = 1111;
    private static final String RESULT = "result";

    private EditText edtSerial, edtCodeCard;
    private Spinner spTypeCard;

    /**
     * Handler
     * Nhan su kien khi response duoc tra ve
     * -> Thong bao, cap nhan UI, ...
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RESULT_REQUEST:
                    ResultRequest result = (ResultRequest) msg.getData().getSerializable(RESULT);

                    Log.i(TAG, "result: " + result.getResultRaw());
                    TextView txtResponse = (TextView) findViewById(R.id.txt_response);
                    txtResponse.setText("Response:\n Status: " + result.getStatus() + "\n Amount: " + result.getAmount());
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }


    private void initViews() {
        spTypeCard = (Spinner) findViewById(R.id.sp_type_card);
        edtCodeCard = (EditText) findViewById(R.id.edt_code_card);
        edtSerial = (EditText) findViewById(R.id.edt_serial);
        Button btnRequest = (Button) findViewById(R.id.btn_pay);

        btnRequest.setOnClickListener(this);
        // Set data
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.list_type_card, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTypeCard.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_pay:

                String provider = CommonVls.LIST_PROVIDERS[spTypeCard.getSelectedItemPosition()].getCode();
                String code = edtCodeCard.getText().toString();
                String serial = edtSerial.getText().toString();

                /**
                 * Request toi server
                 * Listener: nhan su kien khi response tra ve -> gui sang Handler
                 * sendRequest(): gui request
                 */
                MgpRequest request = new MgpRequestBuilder()
                        .withUserInfo("101792", "phuongvd", "test1995")
                        .withCardInfo(provider, code, serial)
                        .withResultListener(new MgpRequestHandler.OnResultListener() {
                            @Override
                            public void onResultListener(ResultRequest result) {
                                Message msg = new Message();
                                msg.what = RESULT_REQUEST;
                                Bundle bundle = new Bundle();
                                bundle.putSerializable(RESULT, result);
                                msg.setData(bundle);
                                handler.sendMessage(msg);
                            }
                        })
                        .build();
                request.sendRequest();
                break;
        }
    }
}
