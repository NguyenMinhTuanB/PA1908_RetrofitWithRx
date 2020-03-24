package com.example.retrofitexample;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerPhotos;
    private PhotoAdapter mPhotoAdapter;

    private CompositeDisposable mComposite;

    private DataService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerPhotos = findViewById(R.id.recycler_photos);
        mPhotoAdapter = new PhotoAdapter(this);
        mRecyclerPhotos.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerPhotos.setAdapter(mPhotoAdapter);

        mComposite = new CompositeDisposable();

        service = APIClient.getInstance().create(DataService.class);

        Disposable disposable = service.getAllPhotos()
                //trả dữ liệu ở Main Thread
                .observeOn(AndroidSchedulers.mainThread())

                //load dữ liệu ở 1 thread khác
                .subscribeOn(Schedulers.io())
                //tạo ra những cái callback cho Observe
                .subscribe(this::handleResponse, this::handleError);

        mComposite.add(disposable);

        /*Call<List<Photo>> call = service.getAllPhotos();

        call.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                Toast.makeText(MainActivity.this, "SUCCESS " + response.body().size(), Toast.LENGTH_SHORT).show();
                mPhotoAdapter.setPhotos(response.body());
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "FAILURE " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    private void handleError(Throwable error) {
        Toast.makeText(this, "ERROR: "+error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void handleResponse(List<Photo> photos) {
        Toast.makeText(this, ""+photos.size(), Toast.LENGTH_SHORT).show();
        mPhotoAdapter.setPhotos(photos);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mComposite.clear();
    }
}
