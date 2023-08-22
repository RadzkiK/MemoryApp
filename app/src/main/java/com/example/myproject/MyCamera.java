package com.example.myproject;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ExperimentalGetImage;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.common.util.concurrent.ListenableFuture;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyCamera#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyCamera extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View thisView;
    private PreviewView previewView;
    private Button takePhotoButton;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private Context AppContext;
    private ImageCapture imageCapture;
    private ContentValues contentValues;
    private ImageView imageView;
    private Bitmap imageBitmap;

    public MyCamera() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Camera.
     */
    // TODO: Rename and change types and number of parameters
    public static MyCamera newInstance(String param1, String param2) {
        MyCamera fragment = new MyCamera();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppContext = getActivity().getApplicationContext();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        if(ContextCompat.checkSelfPermission(AppContext, "android.permission.CAMERA") == PackageManager.PERMISSION_GRANTED)
        {
            startCamera();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{"android.permission.CAMERA"},1001);
        }
        contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "MEMORIES_IMAGE" + new Date());
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_camera, container, false);

        thisView = view;

        takePhotoButton = view.findViewById(R.id.camera_takePhotoButton);
        previewView = view.findViewById(R.id.camera_previewView);
        imageView = view.findViewById(R.id.camera_imageView);
        imageView.setImageResource(R.drawable.ic_launcher_foreground);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == takePhotoButton.getId()) {
                    takePhoto();
                    //imageView.setImageBitmap(imageBitmap);
                }
            }
        };

        takePhotoButton.setOnClickListener(listener);

        return view;
    }

    public void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderListenableFuture = ProcessCameraProvider.getInstance(AppContext);

        cameraProviderListenableFuture.addListener(() -> {
            ProcessCameraProvider cameraProvider;
            try {
                cameraProvider = cameraProviderListenableFuture.get();
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }

            Preview preview = new Preview.Builder().build();
            preview.setSurfaceProvider(previewView.getSurfaceProvider());

            imageCapture = new ImageCapture.Builder().build();

            CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;
            try {
                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle(getActivity(), cameraSelector, preview, imageCapture);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }, ContextCompat.getMainExecutor(AppContext));
    }

    public void takePhoto() {
        ImageCapture imageCapture1 = this.imageCapture;

        ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.OutputFileOptions.Builder(
                getActivity().getContentResolver(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues).build();
        Context baseContext = AppContext;

        imageCapture1.takePicture(ContextCompat.getMainExecutor(AppContext),
                new ImageCapture.OnImageCapturedCallback() {
                    @Override
                    @ExperimentalGetImage
                    public void onCaptureSuccess(@NonNull ImageProxy imageProxy) {
                        Bundle bundle = new Bundle();
                        Date date = new Date();
                        Uri path;
                        Image image = imageProxy.getImage();
                        Bitmap bitmap = imageProxy.toBitmap();
                        Matrix matrix = new Matrix();
                        matrix.postRotate(90);
                        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                        MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap, "MEMORIES_IMAGES" + date.getTime(), "");
                        path = MediaStore.Images.Media.getContentUri("MEMORIES_IMAGES" + date.getTime());
                        imageView.setImageURI(path);
                        bundle.putString("imagePath" , path.toString());
                        Toast.makeText(baseContext, "Zrobiono zdjecie", Toast.LENGTH_SHORT).show();
                        if(path != null) {
                            //String temp;
                            //temp = bundle.getString("imagePath");
                            //Toast.makeText(baseContext, temp, Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(thisView).navigate(R.id.navigate_to_add_from_camera, bundle);
                        }
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        Toast.makeText(baseContext, "Blad", Toast.LENGTH_SHORT).show();
                        exception.printStackTrace();
                    }
                });
    }
}