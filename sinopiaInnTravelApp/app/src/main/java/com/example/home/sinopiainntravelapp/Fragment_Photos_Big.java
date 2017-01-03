package com.example.home.sinopiainntravelapp;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Photos_Big extends Fragment {


    public Fragment_Photos_Big() {
        // Required empty public constructor
    }
    File f;
    BuildString TextSetter ;
    ImageView imageView;
    EditText titleTextView;
    Bitmap bitmap1, bitmap2, bitmap;
    Canvas canvas;
    Paint paint;
    Resources resources;
    int BitmapSize = 30;
    int width, height;
    BitmapDrawable drawable;
    Bitmap bmp;
    String type = "image/*";
    String filename = "/myPhoto.jpg";
    String mediaPath;
    EditText location;
    private Bitmap src;
    ImageView effect_black;
    ImageView effect_boost_1;
    ImageView effect_boost_2;
    ImageView effect_boost_3;
    ImageView effect_brightness;
    ImageView effect_color_red;
    ImageView effect_color_green;
    ImageView effect_color_blue;
    ImageView effect_color_depth_64;
    ImageView effect_color_depth_32;
    ImageView effect_contrast;
    ImageView effect_emboss;
    ImageView effect_engrave;
    ImageView effect_flea;
    ImageView effect_gaussian_blue;

    ImageView effect_gamma;
    ImageView effect_grayscale;
    ImageView effect_hue;
    ImageView effect_invert;
    ImageView effect_reflaction;
    ImageView effect_round_corner;
    ImageView effect_saturation;
    ImageView effect_sepia;
    ImageView effect_sepia_green;
    ImageView effect_sepia_blue;
    ImageView effect_smooth;
    ImageView effect_sheding_yellow;
    ImageView effect_sheding_green;
    ImageView effect_tint;
    ImageView effect_watermark ;

    FileOutputStream fos;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment__photos, container, false);

        /*CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);

        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);*/

        imageView = (ImageView) rootView.findViewById(R.id.image);

        titleTextView = (EditText) rootView.findViewById(R.id.title);

       /* effect_black = (ImageView) rootView.findViewById(R.id.effect_black);
        effect_boost_1 = (ImageView) rootView.findViewById(R.id.effect_boost_1);
        effect_boost_2 = (ImageView) rootView.findViewById(R.id.effect_boost_2);
        effect_boost_3 = (ImageView) rootView.findViewById(R.id.effect_boost_3);
        effect_brightness = (ImageView) rootView.findViewById(R.id.effect_brightness);
        effect_color_red = (ImageView) rootView.findViewById(R.id.effect_color_red);
        effect_color_green = (ImageView) rootView.findViewById(R.id.effect_color_green);
        effect_color_blue = (ImageView) rootView.findViewById(R.id.effect_color_blue);
        effect_color_depth_64 = (ImageView) rootView.findViewById(R.id.effect_color_depth_64);
        effect_color_depth_32 = (ImageView) rootView.findViewById(R.id.effect_color_depth_32);
        effect_contrast = (ImageView) rootView.findViewById(R.id.effect_contrast);
        effect_emboss = (ImageView) rootView.findViewById(R.id.effect_emboss);
        effect_engrave = (ImageView) rootView.findViewById(R.id.effect_engrave);
        effect_flea = (ImageView) rootView.findViewById(R.id.effect_flea);
        effect_gaussian_blue = (ImageView) rootView.findViewById(R.id.effect_gaussian_blue);

        effect_gamma = (ImageView) rootView.findViewById(R.id.effect_gamma);
        effect_grayscale = (ImageView) rootView.findViewById(R.id.effect_grayscale);
        effect_hue = (ImageView) rootView.findViewById(R.id.effect_hue);
        effect_invert = (ImageView) rootView.findViewById(R.id.effect_invert);

        effect_round_corner= (ImageView) rootView.findViewById(R.id.effect_round_corner);
        effect_saturation= (ImageView) rootView.findViewById(R.id.effect_saturation);
        effect_sepia = (ImageView) rootView.findViewById(R.id.effect_sepia);
        effect_sepia_green = (ImageView) rootView.findViewById(R.id.effect_sepia_green);
        effect_sepia_blue = (ImageView) rootView.findViewById(R.id.effect_sepia_blue);
        effect_smooth = (ImageView) rootView.findViewById(R.id.effect_smooth);
        effect_sheding_yellow = (ImageView) rootView.findViewById(R.id.effect_sheding_yellow);
        effect_sheding_green = (ImageView) rootView.findViewById(R.id.effect_sheding_green);
        effect_tint = (ImageView) rootView.findViewById(R.id.effect_tint);
        effect_watermark  = (ImageView) rootView.findViewById(R.id.effect_watermark);*/

        location = (EditText) rootView.findViewById(R.id.promo);

        location.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                                Fragment_Photo_Map new_fragment = new Fragment_Photo_Map();

                                Bundle bundle1 = new Bundle();

                                bundle1.putDouble("latitude",18.166716);

                                bundle1.putDouble("longitude",-76.380764);

                                new_fragment.setArguments(bundle1);

                                ((Activity_CheckIn) getActivity()).homePageFadeTransition(new_fragment,"");

                    }
                });

        resources = getResources();


        if (getArguments().getString("tab") == "2") {



            CreateBitmap();

            //GetBitmapWidthHeight();

            File f = new File(getArguments().getString("image_path"));

            bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());

            bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

            Uri contentUri = Uri.fromFile(f);

            imageView.setImageBitmap(bitmap);

            /*effect_black.setImageBitmap(bitmap);
            effect_boost_1.setImageBitmap(bitmap);
            effect_boost_2.setImageBitmap(bitmap);
            effect_boost_3.setImageBitmap(bitmap);
            effect_brightness.setImageBitmap(bitmap);
            effect_color_red.setImageBitmap(bitmap);
            effect_color_green.setImageBitmap(bitmap);
            effect_color_blue.setImageBitmap(bitmap);
            effect_color_depth_64.setImageBitmap(bitmap);
            effect_color_depth_32.setImageBitmap(bitmap);
            effect_contrast.setImageBitmap(bitmap);
            effect_emboss.setImageBitmap(bitmap);
            effect_engrave.setImageBitmap(bitmap);
            effect_flea.setImageBitmap(bitmap);
            effect_gaussian_blue.setImageBitmap(bitmap);
            effect_gamma.setImageBitmap(bitmap);
            effect_grayscale.setImageBitmap(bitmap);
            effect_hue.setImageBitmap(bitmap);
            effect_invert.setImageBitmap(bitmap);
            effect_round_corner.setImageBitmap(bitmap);
            effect_saturation.setImageBitmap(bitmap);
            effect_sepia.setImageBitmap(bitmap);
            effect_sepia_green.setImageBitmap(bitmap);
            effect_sepia_blue.setImageBitmap(bitmap);
            effect_smooth.setImageBitmap(bitmap);
            effect_sheding_yellow.setImageBitmap(bitmap);
            effect_sheding_green.setImageBitmap(bitmap);
            effect_tint.setImageBitmap(bitmap);
            effect_watermark.setImageBitmap(bitmap);*/


            drawable = (BitmapDrawable) imageView.getDrawable();

            DrawCanvas();


        } else {


            bitmap = getArguments().getParcelable("image");

            imageView.setImageBitmap(bitmap);

            String title = getArguments().getString("title");

            titleTextView.setText(title);


        }

        Button next = (Button) rootView.findViewById(R.id.continueBtn);

        next.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {





                // mediaPath = photoFile.getAbsolutePath();

                //createInstagramIntent(type, mediaPath);

                // galleryAddPic();

                saveBitmap(bmp,"effect_watermark");



                ((Activity_CheckIn) getActivity()).uploadImageToTimeline(titleTextView.getText().toString(),((Activity_CheckIn) getActivity()).location);



            }

        });


        return rootView;

    }


    public void CreateBitmap() {


        BitmapFactory.Options bmOptions = new BitmapFactory.Options();




        bmOptions.inJustDecodeBounds = true;

        int photoW = 300;

        int photoH = 300;

        int scaleFactor = Math.min(photoW, photoH);

        bmOptions.inJustDecodeBounds = false;

        bmOptions.inSampleSize = scaleFactor;

        bmOptions.inPurgeable = true;

        bitmap1 = BitmapFactory.decodeResource(resources,R.drawable.logo_letterpress);



    }

    public void GetBitmapWidthHeight() {

        width = bitmap1.getWidth() + BitmapSize * 2;
        height = bitmap1.getHeight() + BitmapSize * 2;

    }

    public void DrawCanvas() {

        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        bmp = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), conf);
        //canvas = new Canvas(drawable.getBitmap());

        canvas = new Canvas(bmp);


        canvas.drawBitmap(
                bitmap,
                0,
                0,
                null
        );

        Rect dst = new Rect();

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;


        //Rect(int left, int top, int right, int bottom)

        //dst.set(1440/2, 2392 - 500 ,1440,2392);

        dst.set(canvas.getWidth()/2,  canvas.getHeight() - (canvas.getHeight() / 4) ,360,120);

        Paint paint = new Paint();

        paint.setColor(Color.GRAY);

        //canvas.drawRoundRect(dst, 6, 6, paint);

        int padding = 50;

        /*Rect rectangle = new Rect(
                canvas.getWidth()/2, // Left
                canvas.getHeight() - (canvas.getHeight() / 8) , // Top
                canvas.getWidth() , // Right
                canvas.getHeight()  // Bottom
        );*/

        Rect rectangle = new Rect(
                canvas.getWidth() - 120 ,
                canvas.getHeight() - 50 ,
                canvas.getWidth() ,
                canvas.getHeight()
        );

        //canvas.drawRect(rectangle, paint);

        canvas.drawBitmap(bitmap1,null,rectangle,null);

        imageView.setImageBitmap(bmp);


    }



    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );


        //mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }






    private void galleryAddPic() {

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);

        File f = new File(((Activity_CheckIn) getActivity()).media.getAbsolutePath());

        Uri contentUri = Uri.fromFile(f);

        mediaScanIntent.setData(contentUri);

        getActivity().sendBroadcast(mediaScanIntent);
    }


    @Override
    public void onResume(){

        super.onResume();


        TextSetter = new BuildString(getActivity());

        location.setText(((Activity_CheckIn) getActivity()).location);



    }


  /*  public void buttonClicked(View v){

        Toast.makeText(getActivity(),"Processing...",Toast.LENGTH_SHORT).show();

        ImageFilters imgFilter = new ImageFilters();

        if(v.getId() == R.id.effect_black)
            saveBitmap(imgFilter.applyBlackFilter(bitmap),"effect_black");
        else if(v.getId() == R.id.effect_boost_1)
            saveBitmap(imgFilter.applyBoostEffect(bitmap, 1, 40),"effect_boost_1");
        else if(v.getId() == R.id.effect_boost_2)
            saveBitmap(imgFilter.applyBoostEffect(bitmap, 2, 30),"effect_boost_2");
        else if(v.getId() == R.id.effect_boost_3)
            saveBitmap(imgFilter.applyBoostEffect(bitmap, 3, 67),"effect_boost_3");
        else if(v.getId() == R.id.effect_brightness)
            saveBitmap(imgFilter.applyBrightnessEffect(bitmap, 80),"effect_brightness");
        else if(v.getId() == R.id.effect_color_red)
            saveBitmap(imgFilter.applyColorFilterEffect(bitmap, 255, 0, 0),"effect_color_red");
        else if(v.getId() == R.id.effect_color_green)
            saveBitmap(imgFilter.applyColorFilterEffect(bitmap, 0, 255, 0),"effect_color_green");
        else if(v.getId() == R.id.effect_color_blue)
            saveBitmap(imgFilter.applyColorFilterEffect(bitmap, 0, 0, 255),"effect_color_blue");
        else if(v.getId() == R.id.effect_color_depth_64)
            saveBitmap(imgFilter.applyDecreaseColorDepthEffect(bitmap, 64),"effect_color_depth_64");
        else if(v.getId() == R.id.effect_color_depth_32)
            saveBitmap(imgFilter.applyDecreaseColorDepthEffect(bitmap, 32),"effect_color_depth_32");
        else if(v.getId() == R.id.effect_contrast)
            saveBitmap(imgFilter.applyContrastEffect(bitmap, 70),"effect_contrast");
        else if(v.getId() == R.id.effect_emboss)
            saveBitmap(imgFilter.applyEmbossEffect(bitmap),"effect_emboss");
        else if(v.getId() == R.id.effect_engrave)
            saveBitmap(imgFilter.applyEngraveEffect(bitmap),"effect_engrave");
        else if(v.getId() == R.id.effect_flea)
            saveBitmap(imgFilter.applyFleaEffect(bitmap),"effect_flea");
        else  if(v.getId() == R.id.effect_gaussian_blue)
            saveBitmap(imgFilter.applyGaussianBlurEffect(bitmap),"effect_gaussian_blue");
        else if(v.getId() == R.id.effect_gamma)
            saveBitmap(imgFilter.applyGammaEffect(bitmap, 1.8, 1.8, 1.8),"effect_gamma");
        else if(v.getId() == R.id.effect_grayscale)
            saveBitmap(imgFilter.applyGreyscaleEffect(bitmap),"effect_grayscale");
        else  if(v.getId() == R.id.effect_hue)
            saveBitmap(imgFilter.applyHueFilter(bitmap, 2),"effect_hue");
        else if(v.getId() == R.id.effect_invert)
            saveBitmap(imgFilter.applyInvertEffect(bitmap),"effect_invert");
        else if(v.getId() == R.id.effect_mean_remove)
            saveBitmap(imgFilter.applyMeanRemovalEffect(bitmap),"effect_mean_remove");
//        else if(v.getId() == R.id.effect_reflaction)
//            saveBitmap(imgFilter.applyReflection(src),"effect_reflaction");
        else if(v.getId() == R.id.effect_round_corner)
            saveBitmap(imgFilter.applyRoundCornerEffect(bitmap, 45),"effect_round_corner");
        else if(v.getId() == R.id.effect_saturation)
            saveBitmap(imgFilter.applySaturationFilter(bitmap, 1),"effect_saturation");
        else if(v.getId() == R.id.effect_sepia)
            saveBitmap(imgFilter.applySepiaToningEffect(bitmap, 10, 1.5, 0.6, 0.12),"effect_sepia");
        else if(v.getId() == R.id.effect_sepia_green)
            saveBitmap(imgFilter.applySepiaToningEffect(bitmap, 10, 0.88, 2.45, 1.43),"effect_sepia_green");
        else if(v.getId() == R.id.effect_sepia_blue)
            saveBitmap(imgFilter.applySepiaToningEffect(bitmap, 10, 1.2, 0.87, 2.1),"effect_sepia_blue");
        else if(v.getId() == R.id.effect_smooth)
            saveBitmap(imgFilter.applySmoothEffect(bitmap, 100),"effect_smooth");
        else if(v.getId() == R.id.effect_sheding_cyan)
            saveBitmap(imgFilter.applyShadingFilter(bitmap, Color.CYAN),"effect_sheding_cyan");
        else if(v.getId() == R.id.effect_sheding_yellow)
            saveBitmap(imgFilter.applyShadingFilter(bitmap, Color.YELLOW),"effect_sheding_yellow");
        else if(v.getId() == R.id.effect_sheding_green)
            saveBitmap(imgFilter.applyShadingFilter(bitmap, Color.GREEN),"effect_sheding_green");
        else if(v.getId() == R.id.effect_tint)
            saveBitmap(imgFilter.applyTintEffect(bitmap, 100),"effect_tint");
        else if(v.getId() == R.id.effect_watermark)
            saveBitmap(imgFilter.applyWaterMarkEffect(bitmap, "kpbird.com", 200, 200, Color.GREEN, 80, 24, false),"effect_watermark");

    }
*/
    private void saveBitmap(Bitmap bmp,String fileName){
        try {



            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = fileName+"_"+"JPEG_" + timeStamp + "_";
            File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(
                    imageFileName,
                    ".jpg",
                    storageDir
            );

            imageView.setImageBitmap(bmp);



            //f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + fileName+".png");


            f = new File(image.getAbsolutePath());


            try {
                 fos = new FileOutputStream(f);

                // Use the compress method on the BitMap object to write image to the OutputStream
                bmp.compress(Bitmap.CompressFormat.JPEG,90,fos);


                ((Activity_CheckIn) getActivity()).media = f;






            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

}