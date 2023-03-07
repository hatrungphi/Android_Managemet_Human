package vn.edu.stu.quanlynhansu;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.mapbox.geojson.Point;
import com.mapbox.geojson.Polygon;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.EdgeInsets;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;
import com.mapbox.maps.plugin.annotation.AnnotationConfig;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.AnnotationPluginImplKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManagerKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;

import java.util.ArrayList;
import java.util.List;


public class About extends AppCompatActivity {
    TextView mPhoneNoEt;
    static int PERMISSION_CODE= 100;
    MapView mapView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        addControls();
         mPhoneNoEt =findViewById(R.id.tvsdt);
         mPhoneNoEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(About.this,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){

                    ActivityCompat.requestPermissions(About.this,new String[]{Manifest.permission.CALL_PHONE},PERMISSION_CODE);

                }
                String phoneNo = mPhoneNoEt.getText().toString();
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+phoneNo));
                startActivity(callIntent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.example_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                Intent i = new Intent(About.this, About.class);
                startActivity(i);
                return true;
            case R.id.item2:
                System.exit(0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void addControls() {
        mapView = findViewById(R.id.mapView);
        mapView.getMapboxMap().loadStyleUri(
                Style.SATELLITE,
                new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        xuLyNapBanDo();
                    }
                }
        );
    }

    private void xuLyNapBanDo() {
        // STU: 10.738242625107251, 106.67794694095484
        Point pointSTU = Point.fromLngLat(106.67794694095484, 10.738242625107251);

        AnnotationPlugin annotationPlugin = AnnotationPluginImplKt.getAnnotations(mapView);
        PointAnnotationManager manager = PointAnnotationManagerKt.createPointAnnotationManager(
                annotationPlugin,
                new AnnotationConfig()
        );
        PointAnnotationOptions optionsSTU = new PointAnnotationOptions()
                .withPoint(pointSTU)
                .withTextField("STU nè")
                .withIconImage(BitmapFactory.decodeResource(this.getResources(), R.drawable.red_marker));
        manager.create(optionsSTU);


//        CameraOptions cameraOptions = new CameraOptions.Builder()
//                .center(pointSTU)
//                .zoom(15.0)
//                .pitch(0.0)
//                .bearing(0.0)
//                .build();
//        mapView.getMapboxMap().setCamera(cameraOptions);

        List<Point> points = new ArrayList<>();
        points.add(pointSTU);
        List<List<Point>> coordinates = new ArrayList<>();
        coordinates.add(points);
        Polygon polygon = Polygon.fromLngLats(coordinates);
        EdgeInsets edgeInsets = new EdgeInsets(100.0, 100.0, 100.0, 100.0);
        CameraOptions cameraOptions = mapView.getMapboxMap().cameraForGeometry(
                polygon,
                edgeInsets,
                0.0,
                0.0
        );
        mapView.getMapboxMap().setCamera(cameraOptions);
    }

}