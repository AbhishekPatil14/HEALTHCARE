package com.abpatil1.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class BuyMedicineActivity extends AppCompatActivity {

    private String[][] packages=
            {
                    {"Uprise-03 1000IU Capsule","","","","50"},
                    {"Uprise-D3 60000IU Softgel Capsule","","","","10"},
                    {"Calcium Sandoz 500mg Tablet","","","","20"},
                    {"Evion 400mg Capsule","","","","30"},
                    {"Becosules Z Capsule","","","","15"},
                    {"Shelcal 500mg Tablet","","","","25"},
                    {"Supradyn Daily Multivitamin Tablet","","","","12"},
                    {"D-Rise 60000IU Sachet","","","","18"},
                    {"Neurobion Forte Tablet","","","","35"}
            };


    private String[] package_details={
            "Building and keeping the bones & teeth strong\n" +
                    "Reducing Fatigue/stress and muscular pains\n" +
                    "Boosting immunity and increasing resistance against infection",
            "Helps in calcium absorption\n" +
                    "Supports bone health",
            "Strengthens bones and teeth\n"+
                    "Prevents calcium deficiency\n"+
                    "Supports proper muscle function",
            "Acts as an antioxidant\n +" +
                           "Promotes skin health\n" +
                           "Supports heart health",
            "Supports metabolic functions\n" +
                     "Improves energy levels\n"+
                     "Enhances nerve function",
            "Supports bone density\n"+
                     "Prevents osteoporosis\n"+
                     "Maintains heart rhythm",
            "Boosts overall energy\n"+
                     "Improves mental alertness\n"+
                     "Enhances immunity",
            "Prevents vitamin D deficiency\n"+
                    "Supports bone and teeth health\n"+
                    "Enhances muscle function",
            "Supports nerve health\n"+
                     "Reduces nerve pain\n"+
                     "Improves overall energy levels"
    };

    HashMap<String,String> item;
    ArrayList list;
    SimpleAdapter sa;
    ListView lst;
    Button btnBack,btnGoToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_buy_medicine);

        lst=findViewById(R.id.imageViewHAD);
        btnBack=findViewById(R.id.buttonHADBack);
        btnGoToCart=findViewById(R.id.buttonBMCartCheckout);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BuyMedicineActivity.this, HomeActivity.class));
            }
        });

        btnGoToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BuyMedicineActivity.this, CartBuyMedicineActivity.class));
            }
        });

        list = new ArrayList();
        for (int i=0;i<packages.length;i++) {
            item = new HashMap<String,String>();
            item.put("line1", packages[i][0]);
            item.put("line2", packages[i][1]);
            item.put("line3", packages[i][2]);
            item.put("line4", packages[i][3]);
            item.put("line5","Total Cost:"+packages[i][4]+"/-");
            list.add(item);
        }

        sa = new SimpleAdapter(this, list,
                R.layout.multi_lines,
                new String[]{"line1", "line2", "line3", "line4", "line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});
        lst.setAdapter(sa);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(BuyMedicineActivity.this, BuyMedicineDetailsActivity.class);
                it.putExtra("text1", packages[i][0]);
                it.putExtra("text2", package_details[i]);
                it.putExtra("text3", packages[i][4]);
                startActivity(it);
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}