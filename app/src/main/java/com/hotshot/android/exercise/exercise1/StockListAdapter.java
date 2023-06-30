package com.hotshot.android.exercise.exercise1;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StockListAdapter extends RecyclerView.Adapter {
    private List<Stock> stocks = new ArrayList<>();
    private Context context;

    public StockListAdapter(Context context, List<Stock> stocks) {
        this.context = context;
        this.stocks = stocks;
        Log.d("STOCKADAPTER", stocks.toString());
    }

    public static class StockViewHolder extends RecyclerView.ViewHolder {
        TextView stockTextView;
        public StockViewHolder(View view) {
            super(view);
            stockTextView = view.findViewById(R.id.stockNameTextView);
        }
    }

    @NonNull @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View stockListView = LayoutInflater.from(this.context).inflate(R.layout.stock_list, parent,
                                                                   false);
        return new StockViewHolder(stockListView);
    }

    @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        Log.d("INSIDE ADAPTER", stocks.get(position));
        if (holder instanceof StockViewHolder) {
           StockViewHolder viewHolder = (StockViewHolder) holder;
           viewHolder.stockTextView.setText(stocks.get(position).getName().toString());
        }
    }

    @Override public int getItemCount() {
        return stocks.size();
    }

    public void setItems(List<Stock> stocks) {
        this.stocks = stocks;
    }
}
