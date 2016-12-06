package com.example.home.sinopiainntravelapp;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_PersonalDetails extends Fragment {


    EditText name;

    EditText phone;

    EditText email;

    TextInputLayout nameError;
    TextInputLayout phoneError;
    TextInputLayout emailError;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    ImageView mImageView;

    static  String mCurrentPhotoPath;

    public Fragment_PersonalDetails() {
        // Required empty public constructor
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        setPic();
        galleryAddPic();




    }

    static final int REQUEST_TAKE_PHOTO = 1;

    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;

            try {

                photoFile = createImageFile();

            } catch (IOException ex) {


            }


            if (photoFile != null) {

                Uri photoURI = FileProvider.getUriForFile(getActivity(), "com.example.android.fileprovider", photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                getActivity().startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void galleryAddPic() {

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);

        File f = new File(mCurrentPhotoPath);

       ((Activity_Home) getActivity()).f = f;

        Uri contentUri = Uri.fromFile(f);

        mediaScanIntent.setData(contentUri);

        getActivity().sendBroadcast(mediaScanIntent);
    }

    private void setPic() {


        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;


        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

        ((Activity_Home) getActivity()).photofile = mCurrentPhotoPath;


        mImageView.setImageBitmap(bitmap);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_personal_details, container, false);

        mImageView = (ImageView) rootView.findViewById(R.id.image);

        nameError = (TextInputLayout) rootView.findViewById(R.id.nameError);
        nameError.setErrorEnabled(true);

        phoneError = (TextInputLayout) rootView.findViewById(R.id.phoneError);
        phoneError.setErrorEnabled(true);


        emailError = (TextInputLayout) rootView.findViewById(R.id.emailError);
        emailError.setErrorEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);

        collapsingToolbar.setTitle("Your Bill");

        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);

        Bundle bundle = this.getArguments();

        name = (EditText) rootView.findViewById(R.id.name);

        phone = (EditText) rootView.findViewById(R.id.phone);

        email = (EditText) rootView.findViewById(R.id.email);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dispatchTakePictureIntent();



            }
        });


        fab.getDrawable().mutate().setTint(ContextCompat.getColor(getActivity(), android.R.color.white));



        Button next = (Button) rootView.findViewById(R.id.goToCheckout);

        next.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                if (name.getText().toString().matches("") || phone.getText().toString().matches("") || email.getText().toString().matches(""))

                {


                   nameError.setError("All details are required");
                   phoneError.setError("All details are required");
                   emailError.setError("All details are required");



                } else {



                    String regExpn =
                            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                                    +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                    +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                                    +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                    +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                                    +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

                    CharSequence inputStr = email.getText();

                    Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);

                    Matcher matcher = pattern.matcher(inputStr);

                    if(matcher.matches()){


                        ((Activity_Home) getActivity()).name = name.getText().toString();
                        ((Activity_Home) getActivity()).phone = phone.getText().toString();
                        ((Activity_Home) getActivity()).email = email.getText().toString();
                        ((Activity_Home) getActivity()).onBraintreeSubmit();




                    }

                    else {


                        emailError.setError("Email looks invalid.");


                    }

                   /* StringBuilder builder = new StringBuilder();

                    String WEB_SERVICE_URL = builder.append("http://www.dragonbayinnjamaica.com/wp-content/plugins/DragonBayInnReserve/app_php_files/booking.php?resID=").append(resID).append("&firstname=").append(firstname.getText()).append("&lastname=").append(lastname.getText())
                            .append("&email=").append(email.getText()).toString();



                    WebAsyncTask Task = new WebAsyncTask();

                    Task.execute(WEB_SERVICE_URL.replaceAll(" ", "%20"), this, "PDETAILS");*/


                }


            }


        });

        return rootView;
    }




}
