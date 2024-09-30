package com.abpatil1.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class DoctorDetailsActivity extends AppCompatActivity {

    // Arrays holding doctor details for different categories
    private String[][] doctor_details1 = {
            {"Doctor Name: Dr. Anil Kumar", "Hospital Address: New Delhi", "Exp: 15 years", "Mobile No: +91 98765 43210", "₹700"},
            {"Doctor Name: Dr. Sunita Sharma", "Hospital Address: Mumbai", "Exp: 20 years", "Mobile No: +91 98765 43211", "₹800"},
            {"Doctor Name: Dr. Rajesh Gupta", "Hospital Address: Bengaluru", "Exp: 10 years", "Mobile No: +91 98765 43212", "₹600"},
            {"Doctor Name: Dr. Priya Mehta", "Hospital Address: Chennai", "Exp: 12 years", "Mobile No: +91 98765 43213", "₹650"},
            {"Doctor Name: Dr. Amit Patel", "Hospital Address: Ahmedabad", "Exp: 18 years", "Mobile No: +91 98765 43214", "₹750"}
    };

    private String[][] doctor_details2 = {
            {"Doctor Name: Dr. Neha Desai", "Hospital Address: Kolkata", "Exp: 22 years", "Mobile No: +91 98765 43215", "₹900"},
            {"Doctor Name: Dr. Vinod Rao", "Hospital Address: Pune", "Exp: 9 years", "Mobile No: +91 98765 43216", "₹650"},
            {"Doctor Name: Dr. Kavita Iyer", "Hospital Address: Hyderabad", "Exp: 16 years", "Mobile No: +91 98765 43217", "₹700"},
            {"Doctor Name: Dr. Suresh Nair", "Hospital Address: Kochi", "Exp: 14 years", "Mobile No: +91 98765 43218", "₹750"},
            {"Doctor Name: Dr. Rekha Singh", "Hospital Address: Jaipur", "Exp: 11 years", "Mobile No: +91 98765 43219", "₹650"}
    };

    private String[][] doctor_details3 = {
            {"Doctor Name: Dr. Arjun Joshi", "Hospital Address: Lucknow", "Exp: 17 years", "Mobile No: +91 98765 43220", "₹700"},
            {"Doctor Name: Dr. Meera Kulkarni", "Hospital Address: Nagpur", "Exp: 13 years", "Mobile No: +91 98765 43221", "₹650"},
            {"Doctor Name: Dr. Pankaj Jain", "Hospital Address: Indore", "Exp: 8 years", "Mobile No: +91 98765 43222", "₹600"},
            {"Doctor Name: Dr. Aarti Chawla", "Hospital Address: Chandigarh", "Exp: 20 years", "Mobile No: +91 98765 43223", "₹800"},
            {"Doctor Name: Dr. Manish Verma", "Hospital Address: Bhopal", "Exp: 18 years", "Mobile No: +91 98765 43224", "₹750"}
    };

    private String[][] doctor_details4 = {
            {"Doctor Name: Dr. Sneha Reddy", "Hospital Address: Visakhapatnam", "Exp: 12 years", "Mobile No: +91 98765 43225", "₹650"},
            {"Doctor Name: Dr. Ravi Shankar", "Hospital Address: Patna", "Exp: 10 years", "Mobile No: +91 98765 43226", "₹600"},
            {"Doctor Name: Dr. Nisha Thakur", "Hospital Address: Vadodara", "Exp: 15 years", "Mobile No: +91 98765 43227", "₹700"},
            {"Doctor Name: Dr. Kiran Bhatt", "Hospital Address: Surat", "Exp: 13 years", "Mobile No: +91 98765 43228", "₹650"},
            {"Doctor Name: Dr. Rajiv Malhotra", "Hospital Address: Kanpur", "Exp: 21 years", "Mobile No: +91 98765 43229", "₹800"}
    };

    private String[][] doctor_details5 = {
            {"Doctor Name: Dr. Shalini Saxena", "Hospital Address: Ludhiana", "Exp: 14 years", "Mobile No: +91 98765 43230", "₹700"},
            {"Doctor Name: Dr. Vikram Agarwal", "Hospital Address: Agra", "Exp: 16 years", "Mobile No: +91 98765 43231", "₹750"},
            {"Doctor Name: Dr. Anjali Mishra", "Hospital Address: Nashik", "Exp: 19 years", "Mobile No: +91 98765 43232", "₹800"},
            {"Doctor Name: Dr. Sanjay Das", "Hospital Address: Ranchi", "Exp: 11 years", "Mobile No: +91 98765 43233", "₹650"},
            {"Doctor Name: Dr. Pooja Shetty", "Hospital Address: Mangalore", "Exp: 20 years", "Mobile No: +91 98765 43234", "₹800"}
    };

    // UI elements
    TextView tv;
    Button btn;
    String[][] doctor_details = {};
    HashMap<String, String> item;
    ArrayList list;
    SimpleAdapter sa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doctor_details);

        // Initialize UI elements
        tv = findViewById(R.id.textViewBMTitle);
        btn = findViewById(R.id.buttonBMCartCheckout);

        // Retrieve the title from the intent
        Intent it = getIntent();
        String title = it.getStringExtra("title");
        tv.setText(title);

        // Select the appropriate doctor details array based on the title
        if(title.compareTo("Family Physicians")==0)
            doctor_details=doctor_details1;
        else
        if(title.compareTo("Dietician")==0)
            doctor_details=doctor_details2;
        else
        if(title.compareTo("Dentist")==0)
            doctor_details=doctor_details3;
        else
        if(title.compareTo("Surgeon")==0)
            doctor_details=doctor_details4;
        else
            doctor_details=doctor_details5;

            // Set up the back button click listener
        btn.setOnClickListener(v -> startActivity(new Intent(DoctorDetailsActivity.this, FindDoctorActivity.class)));

        // Populate the list with doctor details
        list = new ArrayList();
        for (int i=0;i<doctor_details.length;i++) {
            item = new HashMap<String, String>();
            item.put("line1", doctor_details[i][0]);
            item.put("line2", doctor_details[i][1]);
            item.put("line3", doctor_details[i][2]);
            item.put("line4", doctor_details[i][3]);
            item.put("line5", "Cons Fees: " + doctor_details[i][4] + "/-");
            list.add(item);
        }

        // Set up the SimpleAdapter
        sa = new SimpleAdapter(this, list,
                R.layout.multi_lines,
                new String[]{"line1", "line2", "line3", "line4", "line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});
        ListView lst = findViewById(R.id.listViewDD);
        lst.setAdapter(sa);

        // Set up the item click listener for the list view
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(DoctorDetailsActivity.this, BookAppointmentActivity.class);
                it.putExtra("text1", title);
                it.putExtra("text2", doctor_details[i][0]);
                it.putExtra("text3", doctor_details[i][1]);
                it.putExtra("text4", doctor_details[i][3]);
                it.putExtra("text5", doctor_details[i][4]);
                startActivity(it);
            }
        });

        // Adjust the layout to account for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
