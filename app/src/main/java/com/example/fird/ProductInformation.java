package com.example.fird;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fird.entities.Product;

public class ProductInformation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_information);

        Product product = (Product) getIntent().getSerializableExtra("prod");

        //initialisation des composant graphique
        String[] info = {"Nom du produit: "+product.name,"Description du produit:\n"+product.description,"Prix :"+ String.valueOf(product.price),"Quantité en stock :\n"+ String.valueOf(product.quantityInStock),"Quantité alerte: "+ String.valueOf(product.alertQuantity)};
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.list_viewdetailed, info);

        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);
        Log.e("detail", "onCreate: " + product);




    }

   /** private void buildSimpleAdapterData() {

        binding.ourListView.setAdapter(new SimpleAdapter(this, mapList, R.layout.regular_product_item,
                new String[]{"name", "quantity", "price"}, new int[]{R.id.name, R.id.quantity_in_stock, R.id.price}));
    }
*/
}