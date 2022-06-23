package com.example.bank.ui.screenshot;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bank.R;
import com.example.bank.databinding.FragmentScreenBinding;
import com.example.bank.ui.auth.login.LoginFragment;
import com.example.bank.ui.home.BackListener;
import com.example.bank.ui.home.HomeFragment;
import com.example.bank.utils.SharedModel;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;


public class ScreenFragment extends Fragment implements BackListener  {

   FragmentScreenBinding binding;
    public static BackListener listener;

    private static final int REQUEST_EXTERNAL_STORAGE=1;

    private  static String[] PERMISSION_STORAGE={
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentScreenBinding.bind(view);

        verifyStoragePermission(requireActivity());

        binding.codeReservation.setText(SharedModel.getUpcomingModel().getCode());
        binding.dateReservation.setText(SharedModel.getUpcomingModel().getDate());
        binding.timeReservation.setText(SharedModel.getUpcomingModel().getTime());
        binding.des.setText(SharedModel.getUpcomingModel().getDes());


        binding.takeScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeScreenshot();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        listener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        listener = this;
    }

    @Override
    public void onPause() {
        super.onPause();
        listener = null;
    }

    private void takeScreenshot() {
        Date now = new Date();


        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = "";

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1) {
                mPath= getActivity().getExternalFilesDir(Environment.DIRECTORY_DCIM) + "/" + now.getTime() + ".jpeg";
            }
            else
            {
                mPath= Environment.getExternalStorageDirectory().toString() + "/" + now.getTime() + ".jpeg";
            }

            // create bitmap screen capture
            View v1 = getActivity().getWindow().getDecorView().getRootView();

            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

           // openScreenshot(imageFile);
            Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
            openScreenshot(imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
    }

    public static void verifyStoragePermission(Activity activity){
        int permission= ActivityCompat.checkSelfPermission(activity,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity,PERMISSION_STORAGE,REQUEST_EXTERNAL_STORAGE);
        }
    }
    private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        listener =null;
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount()-1; ++i) {
            fm.popBackStack();
        }
        requireActivity().onBackPressed();
    }
}