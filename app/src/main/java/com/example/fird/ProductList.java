package com.example.fird;

import static android.widget.Toast.LENGTH_SHORT;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fird.Dao.DataBaseRoom;
import com.example.fird.Dao.ProductRoomDao;
import com.example.fird.adapter.CustomAdapter;
import com.example.fird.entities.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ProductList extends AppCompatActivity {
    Product product=new Product();
    ProductRoomDao productRoomDao;
    public final String TAG="Products";
    public List<Product> products=new ArrayList<>();
    public ListView simpleList;
    public CustomAdapter adapter;
    public FloatingActionButton fab;
   public  String message=new String();
   public ProductWebService productWebService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        simpleList=(ListView)findViewById(R.id.listViewP);
        adapter=new CustomAdapter(this,products);

        productWebService=new ProductWebService();
       // products=(List<Product>) productWebService.getProducts();
        //adapter=new CustomAdapter(this,productWebService.getProducts());
        simpleList.setAdapter(adapter);
        productRoomDao= DataBaseRoom.getInstance(this).productRoomDao();
        //envoie des informations d'un produit vers lactivité ProductInformation
        simpleList.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent=new Intent(this,ProductInformation.class);
            intent.putExtra("prod",products.get(i));
            startActivity(intent);
                   });
        Log.d(TAG,""+product);

       // Toast.makeText(getApplicationContext(), ""+product, LENGTH_SHORT).show();

        Thread thread=new Thread(new Runnable() {
            final List<Product> local = new ArrayList<>();
            @Override
            public void run() {
                local.addAll(productWebService.getProducts());
                System.out.println(productWebService.getProducts());
                Log.i("rODI ET LUC", String.valueOf(productWebService.getProducts()));
                runOnUiThread(()->{

                    products.addAll(local);

                });
            }
        });
        thread.start();
        registerForContextMenu(simpleList);
        fab=findViewById(R.id.add_fab);
        fab.setOnClickListener(view -> openSomeActivityForResult());
        Toast.makeText(getApplicationContext(),message, LENGTH_SHORT).show();
        Log.d("Base de données", message);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        // you can set menu header with title icon etc
        //menu.setHeaderTitle("Choose an option");
        // add menu items
        menu.add(0, v.getId(), 0, "modifier");
        menu.add(0, v.getId(), 0, "supprimer");

    }

    // menu item select listener
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if (item.getTitle() == "modifier") {
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
        } else if (item.getTitle() == "supprimer") {
            Thread thread=new Thread(new Runnable() {
                final List<Product> local = new ArrayList<>();
                @Override
                public void run() {
                  //  local.addAll(productRoomDao.findAll());
                    productWebService.deleteProduct(product);
                    productRoomDao.delete(product);
                    runOnUiThread(()->{
                        products.addAll(local);
                    });
                }
            });
            thread.start();
        }

        return true;
    }




    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // There are no request codes
                    Intent data = result.getData();
                    assert data != null;
                    product= (Product) data.getSerializableExtra("oki");
                    Log.d(TAG,""+product);
                   // products.add(product);
                    Thread thread=new Thread(new Runnable() {
                        final List<Product> local = new ArrayList<>();
                        @Override
                        public void run() {
                            local.addAll(productRoomDao.findAll());
                            productWebService.createProduct(product);
                            productRoomDao.insert(product);
                            //productRoomDao.delete(product);
                            runOnUiThread(()->{
                                products.addAll(local);
                            });
                        }
                    });
                    thread.start();
                }
            });



    public void openSomeActivityForResult() {
        Intent intent = new Intent(this, MainActivity.class);
        someActivityResultLauncher.launch(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.s_menu:
                openSomeActivityForResult();
                return true;
            case R.id.delete_All:
                products.clear();
                adapter.notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "The onStart() event");
    }

    /**
     * Called when the activity has become visible.
     */
    @Override
    protected void onResume() {
        super.onResume();

    Toast.makeText(getApplicationContext(), ""+products.size(), LENGTH_SHORT).show();

        Log.d(TAG, "The onResume() event");
    }

    /**
     * Called when another activity is taking focus.
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "The onPause() event");
    }

    /**
     * Called when the activity is no longer visible.
     */
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "The onStop() event");
    }

    /**
     * Called just before the activity is destroyed.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "The onDestroy() event");
    }


}