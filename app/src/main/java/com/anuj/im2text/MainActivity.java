package com.anuj.im2text;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText editText=findViewById(R.id.image_url_field);
        Button b=findViewById(R.id.search);
        final Button T=findViewById(R.id.text_check);
        final ImageView imageView=findViewById(R.id.image_holder);
        View.OnClickListener a=new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String url=editText.getText().toString();
                Picasso.get().load(url).into(imageView);
            }
        };
        b.setOnClickListener(a);
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.id.image_holder);
        private void runTextRecognition()
        {
            FirebaseVisionImage image=FirebaseVisionImage.fromBitmap(bitmap);
        FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance().getOnDeviceTextRecognizer();
        T.setEnabled(false);
        detector.processImage(image).addOnSuccessListener(
                new OnSuccessListener<FirebaseVisionText>()
                {
                    @Override
                    public void onSuccess(FirebaseVisionText firebaseVisionText)
                    {
                        T.setEnabled(true);
                    }
                    }
                ).addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        T.setEnabled(true);
                        e.printStackTrace();
                    }
                });
        }

    }

}
