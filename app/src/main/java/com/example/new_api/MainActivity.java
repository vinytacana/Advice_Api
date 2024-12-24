package com.example.new_api;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView tvAdvice;
    private ProgressBar progressBar;
    private Button btnGetAdvice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tvAdvice = findViewById(R.id.tv_advice);
        progressBar = findViewById(R.id.progress_bar);
        btnGetAdvice = findViewById(R.id.btn_get_advice);

      btnGetAdvice.setOnClickListener(v -> fetchRandomAdvice());


    }

    private void fetchRandomAdvice(){
        progressBar.setVisibility(View.VISIBLE);
        btnGetAdvice.setEnabled(false);

        AdviceApiService adviceApiService = RetrofitClient.getRetrofitInstance().create(AdviceApiService.class);
        Call<Advice> call = adviceApiService.getRandomAdvice();

        call.enqueue(new Callback<Advice>() {
            @Override
            public void onResponse(Call<Advice> call, Response<Advice> response) {
                progressBar.setVisibility(View.GONE);
                btnGetAdvice.setEnabled(true);
                if(response.isSuccessful()&&response.body()!=null){
                    String advice = response.body().getSlip().getAdvice();
                    tvAdvice.setText(advice);
                }else{
                    tvAdvice.setText("Failed to fetch advice");
                }
            }

            @Override
            public void onFailure(Call<Advice> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                btnGetAdvice.setEnabled(true);
                tvAdvice.setText("Error: "+t.getMessage());
                Toast.makeText(MainActivity.this,"Failed to fetch advice",Toast.LENGTH_SHORT).show();

            }
        });
    }
}