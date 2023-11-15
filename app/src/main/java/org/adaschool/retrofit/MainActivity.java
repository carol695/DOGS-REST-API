package org.adaschool.retrofit;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.bumptech.glide.Glide;
import org.adaschool.retrofit.databinding.ActivityMainBinding;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DogApiService dogApiService = RetrofitInstance.getRetrofitInstance().create(DogApiService.class);

        Call<BreedDto> call = dogApiService.getBreed();

        call.enqueue(new Callback<BreedDto>() {
            @Override
            public void onResponse(Call<BreedDto> call, Response<BreedDto> response) {

                if (response.isSuccessful()) {
                    BreedDto breed = response.body();
                    int startIndex = breed.getMessage().indexOf("/breeds/") + "/breeds/".length();
                    int endIndex = breed.getMessage().indexOf("/", startIndex);
                    String nameBreed = breed.getMessage().substring(startIndex, endIndex);
                    Log.d(TAG, "Response URL IMAGE : " + breed.getMessage());
                    Log.d(TAG, "Response NAME BREED : " + nameBreed);
                    loadDogInfo(breed.getMessage(), nameBreed);
                } else {
                    Log.e(TAG, "Error en la respuesta de la API");
                }
            }

            @Override
            public void onFailure(Call<BreedDto> call, Throwable t) {
                Log.e(TAG, "Error al llamar a la API", t);
            }

        });
    }

    private void loadDogInfo(String dogImageUrl, String dogName) {
        binding.textView.setText(dogName);
        Glide.with(this)
                .load(dogImageUrl)
                .into(binding.imageView);

        Log.d(TAG, "HOLA ESTA ES MI APP");
    }


}