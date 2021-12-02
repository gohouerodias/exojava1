package com.example.fird;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fird.entities.Product;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    private TextInputEditText designationEdittext;
    private TextInputEditText descriptionEdittext;
    private TextInputEditText priceEdittext;
    private TextInputEditText quantityInStockEdittext;
    private TextInputEditText quantityAlertEdittext;
    public Product product;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        designationEdittext = findViewById(R.id.designation);
        descriptionEdittext = findViewById(R.id.description);
        priceEdittext = findViewById(R.id.prix);
        quantityInStockEdittext = findViewById(R.id.quantité);
        quantityAlertEdittext = findViewById(R.id.stockalert);

        findViewById(R.id.MyBtn).setOnClickListener(this::saveProduct);
    }





    public void saveProduct(View view) {
        String TAG = "main";
        Log.d(TAG, "SaveProduct ");
        product = new Product();
        if (!Objects.requireNonNull(designationEdittext.getText()).toString().isEmpty()) {
            product.name = designationEdittext.getText().toString();
        } else {
            Toast.makeText(this, "plz enter the name ", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Objects.requireNonNull(descriptionEdittext.getText()).length() != 0) {
            product.description = descriptionEdittext.getText().toString();
        } else {
            Toast.makeText(this, "plz enter the description ", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Objects.requireNonNull(priceEdittext.getText()).toString().isEmpty()) {
            product.price = Double.parseDouble(priceEdittext.getText().toString());
        } else {
            Toast.makeText(this, "plz enter the price ", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Objects.requireNonNull(quantityInStockEdittext.getText()).toString().isEmpty()) {
            Toast.makeText(this, "plz enter the quantity ", Toast.LENGTH_SHORT).show();
            return;
        } else
            product.quantityInStock = Integer.parseInt(quantityInStockEdittext.getText().toString());

        if (Objects.requireNonNull(quantityAlertEdittext.getText()).toString().isEmpty()) {
            Toast.makeText(this, "plz enter the stockAlert ", Toast.LENGTH_SHORT).show();
            return;
        } else product.alertQuantity = Integer.parseInt(quantityAlertEdittext.getText().toString());

        Intent intent = new Intent();
        intent.putExtra("oki",product);
        setResult(RESULT_OK,intent);
        finish();

    }



}