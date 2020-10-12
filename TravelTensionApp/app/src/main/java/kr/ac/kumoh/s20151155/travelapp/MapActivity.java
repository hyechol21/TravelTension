package kr.ac.kumoh.s20151155.travelapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Camera;
import android.graphics.Color;
import android.graphics.PointF;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;

import java.io.IOException;
import java.util.List;


public class MapActivity extends Activity implements OnMapReadyCallback {
    private MapView mapView;
    //마커 객체
    Marker marker = new Marker();

    //위치
    double lat=37.566535;
    double lon=126.97796;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        mapView = findViewById(R.id.map_view);

        mapView.getMapAsync(this);

        //현재위치
        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

        //mainActivity로 위치 보내기

        Button okBtn = (Button)findViewById(R.id.okBtn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lat==0||lon==0)
                    finish();
                else {
                    Intent intent = new Intent();
                    intent.putExtra("location_lat", lat);
                    intent.putExtra("location_lon", lon);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

    }

    //현재위치

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)){
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void onMapReady(@NonNull final NaverMap naverMap) {

        final EditText location_edit= (EditText)findViewById(R.id.location_edit);
        Button search_btn = (Button)findViewById(R.id.search_button);
        final Geocoder geocoder = new Geocoder(this);

        //현재위치
        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.None);
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(true);

        naverMap.addOnLocationChangeListener(new NaverMap.OnLocationChangeListener() {
            @Override
            public void onLocationChange(@NonNull Location location) {
                lat=location.getLatitude();
                lon=location.getLongitude();

                marker.setIconTintColor(Color.BLUE);
                marker.setPosition(new LatLng(lat,lon));
                marker.setMap((naverMap));
            }
        });

        //지도 지점 클릭
        naverMap.setOnMapClickListener(new NaverMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull PointF pointF, @NonNull LatLng latLng) {

                lat = latLng.latitude;
                lon = latLng.longitude;

                marker.setIconTintColor(Color.BLUE);
                marker.setPosition(latLng);
                marker.setMap((naverMap));

                Toast.makeText(MapActivity.this,lat + ", " + lon ,Toast.LENGTH_LONG).show();
            }
        });

        search_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                List<Address> list = null;

                String str = location_edit.getText().toString();
                try {
                    list = geocoder.getFromLocationName(str, 10);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(list != null){
                    if(list.size() == 0){
                        Toast.makeText(MapActivity.this,"해당되는 주소 정보는 없습니다.", Toast.LENGTH_SHORT).show();
                    }else{
                        Address addr = list.get(0);
                        lat = addr.getLatitude();
                        lon = addr.getLongitude();

                        String sss = String.format("geo:%f,%f", lat, lon);

                        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(lat, lon));
                        naverMap.moveCamera(cameraUpdate);

                        Toast.makeText(MapActivity.this,lat + ", " + lon, Toast.LENGTH_LONG).show();
                        marker.setIconTintColor(Color.BLUE);
                        marker.setPosition(new LatLng(lat,lon));
                        marker.setMap((naverMap));
                    }
                }
            }
        });
    }
}