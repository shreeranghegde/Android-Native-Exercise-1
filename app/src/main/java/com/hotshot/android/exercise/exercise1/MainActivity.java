package com.hotshot.android.exercise.exercise1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
//    String[] stocks={"Apple", "Microsoft", "Robinhood","Vanguard", "Bitcoin", "Ethereum"};
    public static final String TAG= "MainActivityTAG";
    RecyclerView recyclerView;
    StockListAdapter adapter;
    EditText filterEditText;
    String filter;

    private List<Stock> stocksList = new ArrayList<>();
    String url = "https://gist.githubusercontent.com/dns-mcdaid/b248c852b743ad960616bac50409f0f0" +
            "/raw/6921812bfb76c1bea7868385adf62b7f447048ce/instruments.json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getStocks();
        Log.d(TAG, stocksList.toString());

        adapter = new StockListAdapter(this, stocksList);
        filterEditText = findViewById(R.id.filterEditText);
        recyclerView = findViewById(R.id.stockList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        filterEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        (event.getAction() == KeyEvent.ACTION_DOWN &&
                                event.getKeyCode()==KeyEvent.KEYCODE_ENTER)) {
                    filter = filterEditText.getText().toString();
                    List<Stock> updatedStocks = stocksList.stream()
                            .filter((item) -> item.getType().equals(filter))
                            .collect(Collectors.toList());
                    Log.d(TAG, updatedStocks.toString());
                    adapter.setItems(updatedStocks);
                    adapter.notifyDataSetChanged();
                }

                return true;
            }
        });
    }

    private void updateStockList(List<Stock> stocks) {
        stocksList.addAll(stocks);
    }

    private void getStocks() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "Network Call failed.", e);
            }

            @Override public void onResponse(@NonNull Call call, @NonNull Response response)
                    throws IOException {

                List<Stock> stocks = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    Log.d(TAG, jsonArray.toString());
                    for(int i = 0; i<jsonArray.length(); i++) {
                        JSONObject element = (JSONObject) jsonArray.get(i);
                        Stock stock = new Stock(element.get("name").toString(),
                                                element.get("instrument_type").toString());
                        stocks.add(stock);
                    }
                    updateStockList(stocks);
                    runOnUiThread(new Runnable() {
                        @Override public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}