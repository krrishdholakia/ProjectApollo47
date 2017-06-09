package com.example.krrishdholakia.projectapollox2v5;

import android.app.ActionBar;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;
import java.math.BigDecimal;

import org.apache.commons.math3.distribution.AbstractRealDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = "tESTING#MainActivity";

    Double User_Age;
    String User_Sex;
    Double User_Resting_BP;
    Double User_Cholestrol;
    Double User_Max_HR;
    String User_Thalas;

    EditText Age_Txt;
    Spinner Sex_Txt;
    EditText Resting_Bp_Txt;
    EditText Cholestrol_Txt;
    EditText Max_HR_Txt;
    Spinner Thalas_Txt;
    ProgressBar temp_bar;
    private int mProgressStatus = 0;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setLogo(R.drawable.ic_done_black_48dp);
        ab.setDisplayUseLogoEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);

        temp_bar = (ProgressBar) findViewById(R.id.progress_bar);

        temp_bar.setVisibility(View.INVISIBLE);

        getSupportActionBar();
        ActionBar actionBar = getActionBar();

        Spinner dropdown = (Spinner) findViewById(R.id.spinner_sex_check_up);
        String[] items = new String[]{"-", "Male", "Female"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        Spinner dropdown2 = (Spinner) findViewById(R.id.spinner_thal_check_up);
        String[] items2 = new String[]{"-","Three", "Six", "Seven"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        dropdown2.setAdapter(adapter2);

          Age_Txt = (EditText) findViewById(R.id.Age_Edt_Txt_check_up);
          Sex_Txt = (Spinner) findViewById(R.id.spinner_sex_check_up);
          Resting_Bp_Txt = (EditText) findViewById(R.id.RestingBP_Edt_Txt_check_up);
          Cholestrol_Txt = (EditText) findViewById(R.id.Cholestrol_Edt_Txt_check_up);
          Max_HR_Txt = (EditText) findViewById(R.id.MaxHR_Edt_Txt_check_up);
          Thalas_Txt = (Spinner) findViewById(R.id.spinner_thal_check_up);


        /*
        if(Age_Txt.getText().toString()!= null && Sex_Txt.getText().toString()!= null && Resting_Bp_Txt.getText().toString() != null && Cholestrol_Txt.getText().toString() != null && Max_HR_Txt.getText().toString() != null && Thalas_Txt.getText().toString() != null ) {

*/



        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.menu_done_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch(item.getItemId()) {

            case R.id.done_bar:
            {

                temp_bar.setIndeterminate(true);

                temp_bar.setVisibility(View.VISIBLE);
                User_Age = Double.parseDouble(Age_Txt.getText().toString());
                User_Sex = Sex_Txt.getSelectedItem().toString();
                User_Resting_BP = Double.parseDouble(Resting_Bp_Txt.getText().toString());
                User_Cholestrol = Double.parseDouble(Cholestrol_Txt.getText().toString());
                User_Max_HR = Double.parseDouble(Max_HR_Txt.getText().toString());
                User_Thalas = Thalas_Txt.getSelectedItem().toString();

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Probability");
                final List<Double> CA_HD_prob_of_occ = new LinkedList<Double>();
                final List<Double> CPT_HD_prob_of_occ= new LinkedList<Double>();
                final List<Double> EIA_HD_prob_of_occ= new LinkedList<Double>();
                final List<Double> FBS_HD_prob_of_occ = new LinkedList<Double>();
                final List<Double> R_EKG_HD_prob_of_occ = new LinkedList<Double>();
                final List<Double> SLOPE_HD_prob_of_occ = new LinkedList<Double>();
                final List<Double> OLDPEAK_HD_prob_of_occ = new LinkedList<Double>();

                final List<Double> CA_NO_HD_prob_of_occ = new LinkedList<Double>();
                final List<Double> CPT_NO_HD_prob_of_occ= new LinkedList<Double>();
                final List<Double> EIA_NO_HD_prob_of_occ= new LinkedList<Double>();
                final List<Double> FBS_NO_HD_prob_of_occ = new LinkedList<Double>();
                final List<Double> R_EKG_NO_HD_prob_of_occ = new LinkedList<Double>();
                final List<Double> SLOPE_NO_HD_prob_of_occ = new LinkedList<Double>();
                final List<Double> OLDPEAK_NO_HD_prob_of_occ = new LinkedList<Double>();

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        double CA_HD_prob = 0.0;
                        double CPT_prob= 0.0;
                        double EIA_prob= 0.0;
                        double FBS_prob= 0.0;
                        double R_EKG_prob= 0.0;
                        double Slope_prob= 0.0;
                        double Thal_prob= 0.0;
                        double Sex_prob= 0.0;


                        CA_HD_prob_of_occ.add(Double.parseDouble(dataSnapshot.child("HD_Bin").child("CA_HD").child("Zero").child("probability_of_occ").getValue().toString()));
                        CA_HD_prob_of_occ.add(Double.parseDouble(dataSnapshot.child("HD_Bin").child("CA_HD").child("One").child("probability_of_occ").getValue().toString()));
                        CA_HD_prob_of_occ.add(Double.parseDouble(dataSnapshot.child("HD_Bin").child("CA_HD").child("Two").child("probability_of_occ").getValue().toString()));
                        CA_HD_prob_of_occ.add(Double.parseDouble(dataSnapshot.child("HD_Bin").child("CA_HD").child("Three").child("probability_of_occ").getValue().toString()));

                        CPT_HD_prob_of_occ.add(Double.parseDouble(dataSnapshot.child("HD_Bin").child("CPT_HD").child("L1").child("probability_of_occ").getValue().toString()));
                        CPT_HD_prob_of_occ.add(Double.parseDouble(dataSnapshot.child("HD_Bin").child("CPT_HD").child("L2").child("probability_of_occ").getValue().toString()));
                        CPT_HD_prob_of_occ.add(Double.parseDouble(dataSnapshot.child("HD_Bin").child("CPT_HD").child("L3").child("probability_of_occ").getValue().toString()));
                        CPT_HD_prob_of_occ.add(Double.parseDouble(dataSnapshot.child("HD_Bin").child("CPT_HD").child("L4").child("probability_of_occ").getValue().toString()));

                        EIA_HD_prob_of_occ.add(Double.parseDouble(dataSnapshot.child("HD_Bin").child("Exercise_Induced_Angina_HD").child("Zero").child("probability_of_occ").getValue().toString()));
                        EIA_HD_prob_of_occ.add(Double.parseDouble(dataSnapshot.child("HD_Bin").child("Exercise_Induced_Angina_HD").child("One").child("probability_of_occ").getValue().toString()));

                        FBS_HD_prob_of_occ.add(Double.parseDouble(dataSnapshot.child("HD_Bin").child("Fasting_Blood_Sugar_HD").child("False").child("probability_of_occ").getValue().toString()));
                        FBS_HD_prob_of_occ.add(Double.parseDouble(dataSnapshot.child("HD_Bin").child("Fasting_Blood_Sugar_HD").child("True").child("probability_of_occ").getValue().toString()));


                        R_EKG_HD_prob_of_occ.add(Double.parseDouble(dataSnapshot.child("HD_Bin").child("Resting_ECG_HD").child("Zero").child("probability_of_occ").getValue().toString()));
                        R_EKG_HD_prob_of_occ.add(Double.parseDouble(dataSnapshot.child("HD_Bin").child("Resting_ECG_HD").child("One").child("probability_of_occ").getValue().toString()));
                        R_EKG_HD_prob_of_occ.add(Double.parseDouble(dataSnapshot.child("HD_Bin").child("Resting_ECG_HD").child("Two").child("probability_of_occ").getValue().toString()));

                        SLOPE_HD_prob_of_occ.add(Double.parseDouble(dataSnapshot.child("HD_Bin").child("Slope_HD").child("One").child("probability_of_occ").getValue().toString()));
                        SLOPE_HD_prob_of_occ.add(Double.parseDouble(dataSnapshot.child("HD_Bin").child("Slope_HD").child("Two").child("probability_of_occ").getValue().toString()));
                        SLOPE_HD_prob_of_occ.add(Double.parseDouble(dataSnapshot.child("HD_Bin").child("Slope_HD").child("Three").child("probability_of_occ").getValue().toString()));

                        CA_HD_prob = simple_probability(CA_HD_prob_of_occ)/Double.parseDouble(dataSnapshot.child("HD_Bin").child("CA_HD").child("Zero").child("total_number").getValue().toString());//total number is constant across all

                        CPT_prob = simple_probability(CA_HD_prob_of_occ)/Double.parseDouble(dataSnapshot.child("HD_Bin").child("CPT_HD").child("L1").child("probability_of_occ").getValue().toString());

                        EIA_prob = simple_probability(EIA_HD_prob_of_occ)/Double.parseDouble(dataSnapshot.child("HD_Bin").child("Exercise_Induced_Angina_HD").child("Zero").child("probability_of_occ").getValue().toString());

                        FBS_prob = simple_probability(FBS_HD_prob_of_occ)/Double.parseDouble(dataSnapshot.child("HD_Bin").child("Fasting_Blood_Sugar_HD").child("False").child("probability_of_occ").getValue().toString());

                        R_EKG_prob = simple_probability(R_EKG_HD_prob_of_occ)/Double.parseDouble(dataSnapshot.child("HD_Bin").child("Resting_ECG_HD").child("Zero").child("probability_of_occ").getValue().toString());

                        Slope_prob = simple_probability(SLOPE_HD_prob_of_occ)/Double.parseDouble(dataSnapshot.child("HD_Bin").child("Slope_HD").child("One").child("probability_of_occ").getValue().toString());

                        Sex_prob = Double.parseDouble(dataSnapshot.child("HD_Bin").child("Sex_HD").child(User_Sex).child("probability_of_occ").getValue().toString())/Double.parseDouble(dataSnapshot.child("HD_Bin").child("Sex_HD").child(User_Sex).child("total_number").getValue().toString());

                        Thal_prob = Double.parseDouble(dataSnapshot.child("HD_Bin").child("Thal_HD").child(User_Thalas).child("probability_of_occ").getValue().toString())/Double.parseDouble(dataSnapshot.child("HD_Bin").child("Thal_HD").child(User_Thalas).child("total_number").getValue().toString());

                        BigDecimal HD_BIN_simple_probability = BigDecimal.valueOf(CA_HD_prob*CPT_prob*EIA_prob*FBS_prob*R_EKG_prob*Slope_prob*Sex_prob*Thal_prob);

                        double age_mean = 0.0;
                        double age_std_deviation_biased = 0.0;
                        double age_std_deviation_unbiased = 0.0;
                        double resting_bp_level_mean = 0.0;
                        double resting_bp_level_std_deviation_biased = 0.0;
                        double resting_bp_level_std_deviation_unbiased = 0.0;
                        double cholestoral_mean = 0.0;
                        double cholestoral_std_deviation_biased = 0.0;
                        double cholestoral_std_deviation_unbiased = 0.0;
                        double max_hr_mean = 0.0;
                        double max_hr_std_deviation_biased = 0.0;
                        double max_hr_std_deviation_unbiased = 0.0;
                        double oldpeak_mean = 0.0;
                        double oldpeak_std_deviation_biased = 0.0;
                        double oldpeak_std_deviation_unbiased = 0.0;

                        age_mean = Double.parseDouble(dataSnapshot.child("HD_ND").child("Age_HD_ND").child("sum_of_Var").getValue().toString())/Double.parseDouble(dataSnapshot.child("HD_ND").child("Age_HD_ND").child("number_of_Var").getValue().toString());

                        age_std_deviation_biased = (Double.parseDouble(dataSnapshot.child("HD_ND").child("Age_HD_ND").child("sum_of_Var_squared").getValue().toString())/Double.parseDouble(dataSnapshot.child("HD_ND").child("Age_HD_ND").child("number_of_Var").getValue().toString())) - Math.pow(age_mean,2);

                        age_std_deviation_unbiased = (Double.parseDouble(dataSnapshot.child("HD_ND").child("Age_HD_ND").child("number_of_Var").getValue().toString())/(Double.parseDouble(dataSnapshot.child("HD_ND").child("Age_HD_ND").child("number_of_Var").getValue().toString())-1))*age_std_deviation_biased;

                        resting_bp_level_mean = Double.parseDouble(dataSnapshot.child("HD_ND").child("Rest_BP_Level_HD_ND").child("sum_of_Var").getValue().toString())/Double.parseDouble(dataSnapshot.child("HD_ND").child("Rest_BP_Level_HD_ND").child("number_of_Var").getValue().toString());
                        resting_bp_level_std_deviation_biased = (Double.parseDouble(dataSnapshot.child("HD_ND").child("Rest_BP_Level_HD_ND").child("sum_of_Var_squared").getValue().toString())/Double.parseDouble(dataSnapshot.child("HD_ND").child("Rest_BP_Level_HD_ND").child("number_of_Var").getValue().toString())) - Math.pow(resting_bp_level_mean,2);

                        resting_bp_level_std_deviation_unbiased = (Double.parseDouble(dataSnapshot.child("HD_ND").child("Age_HD_ND").child("number_of_Var").getValue().toString())/(Double.parseDouble(dataSnapshot.child("HD_ND").child("Rest_BP_Level_HD_ND").child("number_of_Var").getValue().toString())-1))*resting_bp_level_std_deviation_biased;

                        cholestoral_mean = Double.parseDouble(dataSnapshot.child("HD_ND").child("Cholestoral_HD_ND").child("sum_of_Var").getValue().toString())/Double.parseDouble(dataSnapshot.child("HD_ND").child("Cholestoral_HD_ND").child("number_of_Var").getValue().toString());
                        cholestoral_std_deviation_biased = (Double.parseDouble(dataSnapshot.child("HD_ND").child("Cholestoral_HD_ND").child("sum_of_Var_squared").getValue().toString())/Double.parseDouble(dataSnapshot.child("HD_ND").child("Cholestoral_HD_ND").child("number_of_Var").getValue().toString())) - Math.pow(cholestoral_mean,2);

                        cholestoral_std_deviation_unbiased = (Double.parseDouble(dataSnapshot.child("HD_ND").child("Cholestoral_HD_ND").child("number_of_Var").getValue().toString())/(Double.parseDouble(dataSnapshot.child("HD_ND").child("Cholestoral_HD_ND").child("number_of_Var").getValue().toString())-1))*cholestoral_std_deviation_biased;

                        max_hr_mean = Double.parseDouble(dataSnapshot.child("HD_ND").child("Max_HeartRate_HD_ND").child("sum_of_Var").getValue().toString())/Double.parseDouble(dataSnapshot.child("HD_ND").child("Max_HeartRate_HD_ND").child("number_of_Var").getValue().toString());
                        max_hr_std_deviation_biased = (Double.parseDouble(dataSnapshot.child("HD_ND").child("Max_HeartRate_HD_ND").child("sum_of_Var_squared").getValue().toString())/Double.parseDouble(dataSnapshot.child("HD_ND").child("Max_HeartRate_HD_ND").child("number_of_Var").getValue().toString())) - Math.pow(max_hr_mean, 2);

                        max_hr_std_deviation_unbiased = (Double.parseDouble(dataSnapshot.child("HD_ND").child("Max_HeartRate_HD_ND").child("number_of_Var").getValue().toString())/(Double.parseDouble(dataSnapshot.child("HD_ND").child("Cholestoral_HD_ND").child("number_of_Var").getValue().toString())-1))*max_hr_std_deviation_biased;

                        oldpeak_mean = Double.parseDouble(dataSnapshot.child("HD_ND").child("Oldpeak_HD_ND").child("sum_of_Var").getValue().toString())/Double.parseDouble(dataSnapshot.child("HD_ND").child("Oldpeak_HD_ND").child("number_of_Var").getValue().toString());
                        oldpeak_std_deviation_biased = (Double.parseDouble(dataSnapshot.child("HD_ND").child("Oldpeak_HD_ND").child("sum_of_Var_squared").getValue().toString())/Double.parseDouble(dataSnapshot.child("HD_ND").child("Oldpeak_HD_ND").child("number_of_Var").getValue().toString())) - Math.pow(oldpeak_mean, 2);

                        oldpeak_std_deviation_unbiased = (Double.parseDouble(dataSnapshot.child("HD_ND").child("Oldpeak_HD_ND").child("number_of_Var").getValue().toString())/(Double.parseDouble(dataSnapshot.child("HD_ND").child("Oldpeak_HD_ND").child("number_of_Var").getValue().toString())-1))*oldpeak_std_deviation_biased;

                        double data_prob_HD = Double.parseDouble(dataSnapshot.child("HD_Probability").child("probability_of_occ").getValue().toString())/Double.parseDouble(dataSnapshot.child("HD_Probability").child("total_number").getValue().toString());
                        BigDecimal probability_of_HD = BigDecimal.valueOf(Normal(age_std_deviation_unbiased,User_Age,age_mean)*(Normal(resting_bp_level_std_deviation_unbiased,User_Resting_BP,resting_bp_level_mean))*Normal(cholestoral_std_deviation_unbiased,User_Cholestrol,cholestoral_mean)*(Normal(max_hr_std_deviation_unbiased,User_Max_HR,max_hr_mean))).multiply(HD_BIN_simple_probability);


                        BigDecimal final_prob_hd = probability_of_HD.multiply(BigDecimal.valueOf(data_prob_HD));
                        Log.i(TAG,"MYTRIALS "+final_prob_hd);

                        double CA_NO_HD_prob = 0.0;
                        double CPT_NO_prob= 0.0;
                        double EIA_NO_prob= 0.0;
                        double FBS_NO_prob= 0.0;
                        double R_EKG_NO_prob= 0.0;
                        double Slope_NO_prob= 0.0;
                        double Thal_NO_prob= 0.0;
                        double Sex_NO_prob= 0.0;


                        CA_NO_HD_prob_of_occ.add(Double.parseDouble(dataSnapshot.child("NO_HD_Bin").child("CA_NO_HD").child("Zero").child("probability_of_occ").getValue().toString()));
                        CA_NO_HD_prob_of_occ.add(Double.parseDouble(dataSnapshot.child("NO_HD_Bin").child("CA_NO_HD").child("One").child("probability_of_occ").getValue().toString()));
                        CA_NO_HD_prob_of_occ.add(Double.parseDouble(dataSnapshot.child("NO_HD_Bin").child("CA_NO_HD").child("Two").child("probability_of_occ").getValue().toString()));
                        CA_NO_HD_prob_of_occ.add(Double.parseDouble(dataSnapshot.child("NO_HD_Bin").child("CA_NO_HD").child("Three").child("probability_of_occ").getValue().toString()));

                        CPT_NO_HD_prob_of_occ.add(Double.parseDouble(dataSnapshot.child("NO_HD_Bin").child("CPT_NO_HD").child("L1").child("probability_of_occ").getValue().toString()));
                        CPT_NO_HD_prob_of_occ.add(Double.parseDouble(dataSnapshot.child("NO_HD_Bin").child("CPT_NO_HD").child("L2").child("probability_of_occ").getValue().toString()));
                        CPT_NO_HD_prob_of_occ.add(Double.parseDouble(dataSnapshot.child("NO_HD_Bin").child("CPT_NO_HD").child("L3").child("probability_of_occ").getValue().toString()));
                        CPT_NO_HD_prob_of_occ.add(Double.parseDouble(dataSnapshot.child("NO_HD_Bin").child("CPT_NO_HD").child("L4").child("probability_of_occ").getValue().toString()));

                        EIA_NO_HD_prob_of_occ.add(Double.parseDouble(dataSnapshot.child("NO_HD_Bin").child("Exercise_Induced_Angina_NO_HD").child("Zero").child("probability_of_occ").getValue().toString()));
                        EIA_NO_HD_prob_of_occ.add(Double.parseDouble(dataSnapshot.child("NO_HD_Bin").child("Exercise_Induced_Angina_NO_HD").child("One").child("probability_of_occ").getValue().toString()));

                        FBS_NO_HD_prob_of_occ.add(Double.parseDouble(dataSnapshot.child("NO_HD_Bin").child("Fasting_Blood_Sugar_NO_HD").child("False").child("probability_of_occ").getValue().toString()));
                        FBS_NO_HD_prob_of_occ.add(Double.parseDouble(dataSnapshot.child("NO_HD_Bin").child("Fasting_Blood_Sugar_NO_HD").child("True").child("probability_of_occ").getValue().toString()));


                        R_EKG_NO_HD_prob_of_occ.add(Double.parseDouble(dataSnapshot.child("NO_HD_Bin").child("Resting_ECG_NO_HD").child("Zero").child("probability_of_occ").getValue().toString()));
                        R_EKG_NO_HD_prob_of_occ.add(Double.parseDouble(dataSnapshot.child("NO_HD_Bin").child("Resting_ECG_NO_HD").child("One").child("probability_of_occ").getValue().toString()));
                        R_EKG_NO_HD_prob_of_occ.add(Double.parseDouble(dataSnapshot.child("NO_HD_Bin").child("Resting_ECG_NO_HD").child("Two").child("probability_of_occ").getValue().toString()));

                        SLOPE_NO_HD_prob_of_occ.add(Double.parseDouble(dataSnapshot.child("NO_HD_Bin").child("Slope_NO_HD").child("One").child("probability_of_occ").getValue().toString()));
                        SLOPE_NO_HD_prob_of_occ.add(Double.parseDouble(dataSnapshot.child("NO_HD_Bin").child("Slope_NO_HD").child("Two").child("probability_of_occ").getValue().toString()));
                        SLOPE_NO_HD_prob_of_occ.add(Double.parseDouble(dataSnapshot.child("NO_HD_Bin").child("Slope_NO_HD").child("Three").child("probability_of_occ").getValue().toString()));

                        CA_HD_prob = simple_probability(CA_NO_HD_prob_of_occ)/Double.parseDouble(dataSnapshot.child("NO_HD_Bin").child("CA_NO_HD").child("Zero").child("total_number").getValue().toString());//total number is constant across all

                        CPT_NO_prob = simple_probability(CPT_NO_HD_prob_of_occ)/Double.parseDouble(dataSnapshot.child("NO_HD_Bin").child("CPT_NO_HD").child("L1").child("probability_of_occ").getValue().toString());

                        EIA_NO_prob = simple_probability(EIA_NO_HD_prob_of_occ)/Double.parseDouble(dataSnapshot.child("NO_HD_Bin").child("Exercise_Induced_Angina_NO_HD").child("Zero").child("probability_of_occ").getValue().toString());

                        FBS_NO_prob = simple_probability(FBS_NO_HD_prob_of_occ)/Double.parseDouble(dataSnapshot.child("NO_HD_Bin").child("Fasting_Blood_Sugar_NO_HD").child("False").child("probability_of_occ").getValue().toString());

                        R_EKG_NO_prob = simple_probability(R_EKG_NO_HD_prob_of_occ)/Double.parseDouble(dataSnapshot.child("NO_HD_Bin").child("Resting_ECG_NO_HD").child("Zero").child("probability_of_occ").getValue().toString());

                        Slope_NO_prob = simple_probability(SLOPE_NO_HD_prob_of_occ)/Double.parseDouble(dataSnapshot.child("NO_HD_Bin").child("Slope_NO_HD").child("One").child("probability_of_occ").getValue().toString());

                        Sex_NO_prob = Double.parseDouble(dataSnapshot.child("NO_HD_Bin").child("Sex_NO_HD").child(User_Sex).child("probability_of_occ").getValue().toString())/Double.parseDouble(dataSnapshot.child("NO_HD_Bin").child("Sex_NO_HD").child(User_Sex).child("total_number").getValue().toString());

                        Thal_NO_prob = Double.parseDouble(dataSnapshot.child("NO_HD_Bin").child("Thal_NO_HD").child(User_Thalas).child("probability_of_occ").getValue().toString())/Double.parseDouble(dataSnapshot.child("NO_HD_Bin").child("Thal_NO_HD").child(User_Thalas).child("total_number").getValue().toString());

                        BigDecimal NO_HD_BIN_simple_prob = BigDecimal.valueOf(CA_HD_prob*CPT_NO_prob*EIA_NO_prob*FBS_NO_prob*R_EKG_NO_prob*Slope_NO_prob*Sex_NO_prob*Thal_NO_prob);

                        double age_NO_mean = 0.0;
                        double age_std_deviation_biased_NO = 0.0;
                        double age_std_deviation_unbiased_NO = 0.0;
                        double resting_bp_level_mean_NO = 0.0;
                        double resting_bp_level_std_deviation_biased_NO = 0.0;
                        double resting_bp_level_std_deviation_unbiased_NO = 0.0;
                        double cholestoral_mean_NO = 0.0;
                        double cholestoral_std_deviation_biased_NO = 0.0;
                        double cholestoral_std_deviation_unbiased_NO = 0.0;
                        double max_hr_mean_NO = 0.0;
                        double max_hr_std_deviation_biased_NO = 0.0;
                        double max_hr_std_deviation_unbiased_NO = 0.0;
                        double oldpeak_mean_NO = 0.0;
                        double oldpeak_std_deviation_biased_NO = 0.0;
                        double oldpeak_std_deviation_unbiased_NO = 0.0;

                        age_NO_mean = Double.parseDouble(dataSnapshot.child("NO_HD_ND").child("Age_NO_HD_ND").child("sum_of_Var").getValue().toString())/Double.parseDouble(dataSnapshot.child("NO_HD_ND").child("Age_NO_HD_ND").child("number_of_Var").getValue().toString());

                        age_std_deviation_biased_NO = (Double.parseDouble(dataSnapshot.child("NO_HD_ND").child("Age_NO_HD_ND").child("sum_of_Var_squared").getValue().toString())/Double.parseDouble(dataSnapshot.child("NO_HD_ND").child("Age_NO_HD_ND").child("number_of_Var").getValue().toString())) - Math.pow(age_NO_mean,2);

                        age_std_deviation_unbiased_NO = (Double.parseDouble(dataSnapshot.child("NO_HD_ND").child("Age_NO_HD_ND").child("number_of_Var").getValue().toString())/(Double.parseDouble(dataSnapshot.child("NO_HD_ND").child("Age_NO_HD_ND").child("number_of_Var").getValue().toString())-1))*age_std_deviation_biased_NO;

                        resting_bp_level_mean_NO = Double.parseDouble(dataSnapshot.child("NO_HD_ND").child("Rest_BP_Level_NO_HD_ND").child("sum_of_Var").getValue().toString())/Double.parseDouble(dataSnapshot.child("NO_HD_ND").child("Rest_BP_Level_NO_HD_ND").child("number_of_Var").getValue().toString());
                        resting_bp_level_std_deviation_biased_NO = (Double.parseDouble(dataSnapshot.child("NO_HD_ND").child("Rest_BP_Level_NO_HD_ND").child("sum_of_Var_squared").getValue().toString())/Double.parseDouble(dataSnapshot.child("NO_HD_ND").child("Rest_BP_Level_NO_HD_ND").child("number_of_Var").getValue().toString())) - Math.pow(resting_bp_level_mean_NO,2) ;

                        resting_bp_level_std_deviation_unbiased_NO = (Double.parseDouble(dataSnapshot.child("NO_HD_ND").child("Rest_BP_Level_NO_HD_ND").child("number_of_Var").getValue().toString())/(Double.parseDouble(dataSnapshot.child("NO_HD_ND").child("Rest_BP_Level_NO_HD_ND").child("number_of_Var").getValue().toString())-1))*resting_bp_level_std_deviation_biased_NO;

                        cholestoral_mean_NO = Double.parseDouble(dataSnapshot.child("NO_HD_ND").child("Cholestoral_NO_HD").child("sum_of_Var").getValue().toString())/Double.parseDouble(dataSnapshot.child("NO_HD_ND").child("Cholestoral_NO_HD").child("number_of_Var").getValue().toString());
                        cholestoral_std_deviation_biased_NO = (Double.parseDouble(dataSnapshot.child("NO_HD_ND").child("Cholestoral_NO_HD").child("sum_of_Var_squared").getValue().toString())/Double.parseDouble(dataSnapshot.child("NO_HD_ND").child("Cholestoral_NO_HD").child("number_of_Var").getValue().toString())) - Math.pow(cholestoral_mean_NO ,2);

                        cholestoral_std_deviation_unbiased_NO = (Double.parseDouble(dataSnapshot.child("NO_HD_ND").child("Cholestoral_NO_HD").child("number_of_Var").getValue().toString())/(Double.parseDouble(dataSnapshot.child("NO_HD_ND").child("Cholestoral_NO_HD").child("number_of_Var").getValue().toString())-1))*cholestoral_std_deviation_biased_NO;

                        max_hr_mean_NO = Double.parseDouble(dataSnapshot.child("NO_HD_ND").child("Max_HeartRate_NO_HD_ND").child("sum_of_Var").getValue().toString())/Double.parseDouble(dataSnapshot.child("NO_HD_ND").child("Max_HeartRate_NO_HD_ND").child("number_of_Var").getValue().toString());
                        max_hr_std_deviation_biased =  (Double.parseDouble(dataSnapshot.child("NO_HD_ND").child("Max_HeartRate_NO_HD_ND").child("sum_of_Var_squared").getValue().toString())/Double.parseDouble(dataSnapshot.child("NO_HD_ND").child("Max_HeartRate_NO_HD_ND").child("number_of_Var").getValue().toString())) - Math.pow(max_hr_mean_NO, 2);

                        max_hr_std_deviation_unbiased_NO = (Double.parseDouble(dataSnapshot.child("NO_HD_ND").child("Max_HeartRate_NO_HD_ND").child("number_of_Var").getValue().toString())/(Double.parseDouble(dataSnapshot.child("NO_HD_ND").child("Cholestoral_NO_HD").child("number_of_Var").getValue().toString())-1))*max_hr_std_deviation_biased;

                        oldpeak_mean = Double.parseDouble(dataSnapshot.child("NO_HD_ND").child("Oldpeak_NO_HD_ND").child("sum_of_Var").getValue().toString())/Double.parseDouble(dataSnapshot.child("NO_HD_ND").child("Oldpeak_NO_HD_ND").child("number_of_Var").getValue().toString());
                        oldpeak_std_deviation_biased_NO = (Double.parseDouble(dataSnapshot.child("NO_HD_ND").child("Oldpeak_NO_HD_ND").child("sum_of_Var_squared").getValue().toString())/Double.parseDouble(dataSnapshot.child("NO_HD_ND").child("Oldpeak_NO_HD_ND").child("number_of_Var").getValue().toString())) - Math.pow(oldpeak_mean, 2);

                        oldpeak_std_deviation_unbiased_NO = (Double.parseDouble(dataSnapshot.child("NO_HD_ND").child("Oldpeak_NO_HD_ND").child("number_of_Var").getValue().toString())/(Double.parseDouble(dataSnapshot.child("NO_HD_ND").child("Oldpeak_NO_HD_ND").child("number_of_Var").getValue().toString())-1))*oldpeak_std_deviation_biased_NO;

                        double data_prob_NO_HD = Double.parseDouble(dataSnapshot.child("NO_HD_Probability").child("probability_of_occ").getValue().toString())/Double.parseDouble(dataSnapshot.child("NO_HD_Probability").child("total_number").getValue().toString());
                        //BigDecimal temp_value_oldpeak_Av = BigDecimal.valueOf(0.5);
                      //  BigDecimal true_data_prob_NO_HD = BigDecimal.valueOf(data_prob_HD);
                        BigDecimal probability_of_NO_HD = BigDecimal.valueOf(Normal(age_std_deviation_unbiased_NO,User_Age,age_NO_mean)*(Normal(resting_bp_level_std_deviation_unbiased_NO,User_Resting_BP,resting_bp_level_mean_NO))*Normal(cholestoral_std_deviation_unbiased_NO,User_Cholestrol,cholestoral_mean_NO)*(Normal(max_hr_std_deviation_unbiased_NO,User_Max_HR,max_hr_mean_NO))).multiply(NO_HD_BIN_simple_prob);

                        BigDecimal final_prob_NO_HD = probability_of_NO_HD.multiply(BigDecimal.valueOf(data_prob_NO_HD));
                        Log.i(TAG,"TRIALLERS: "+final_prob_NO_HD);


                        if(final_prob_hd.compareTo(final_prob_NO_HD) == 1)
                        {
                            Log.i(TAG,"Probability of disease exists: "+final_prob_hd);
                        }
                        else
                        {
                            Log.i(TAG, "YAY YOU LUCK!");
                        }




                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                break;
            }}
        return super.onOptionsItemSelected(item);
            }



    //for BIN_HD and BIN_NO_HD
    public Double simple_probability(List<Double> tempList)
    {
        double prob_of_occ=0;
        for(int count = 1; count < tempList.size(); count++)
        {
            if(tempList.get(count) > prob_of_occ)
            {
                prob_of_occ = tempList.get(count);
            }
        }

        return prob_of_occ;
    }

    public double Normal(double sigma_squared, double x, double mean ){
           /* BigDecimal base = BigDecimal.valueOf(1/Math.sqrt(2*Math.PI*(sigma*sigma)));
        double base1 = 0.0;
        if(base.setScale(5, RoundingMode.HALF_UP).compareTo(base.round(new MathContext(3, RoundingMode.HALF_UP))) >= 0)
        {
            base1 = Double.parseDouble(base.setScale(5, RoundingMode.HALF_UP).toString());
        }
        else{

            base1 = Double.parseDouble(base.round(new MathContext(3, RoundingMode.HALF_UP)).toString());
        }

        Log.i(TAG,"Krrish: "+base);

            BigDecimal pow = BigDecimal.valueOf(-(Math.pow((x-mean), 2)/2*(sigma*sigma)));
        double pow1 = 0.0;
        if(pow.setScale(5, RoundingMode.HALF_UP).compareTo(pow.round(new MathContext(3, RoundingMode.HALF_UP))) >= 0)
        {
            pow1 = Double.parseDouble(pow.setScale(5, RoundingMode.HALF_UP).toString());
        }
        else{
            pow1 = Double.parseDouble(pow.round(new MathContext(3, RoundingMode.HALF_UP)).toString());
        }
        Log.i(TAG,"Krrish2: "+pow);


/*
        String tem_var = pow.multiply(BigDecimalMath.log(base)).toString();
        Log.i(TAG,"brikribrikri: "+tem_var);
*/
        double probability1 = 0;

        double sigma = Math.sqrt(sigma_squared);
        Log.i(TAG,"MEAN: "+mean);
        Log.i(TAG,"STD_DEV: "+sigma);
        Log.i(TAG,"VARIABLE_VALUE: "+x);

        /*BigDecimal probability2 = BigDecimalMath.pow(base,pow).round(new MathContext(10,RoundingMode.HALF_UP));
        Log.i(TAG,"PDF: "+probability2.toString());
        if(probability2.setScale(5,RoundingMode.HALF_UP).compareTo(probability2.round(new MathContext(3,RoundingMode.HALF_UP))) >= 0)
        {
            probability = Double.parseDouble(probability2.setScale(5,RoundingMode.HALF_UP).toString());
        }
        else
        {
            probability = Double.parseDouble(probability2.round(new MathContext(3,RoundingMode.HALF_UP)).toString());
        }

*/
        NormalDistribution temp_Distribution = new NormalDistribution(mean,sigma);

        probability1 = temp_Distribution.density(x);

        Log.i(TAG,"Krrish4: "+probability1);
        return probability1;


        /*
        BigDecimal probability_of_NO_HD = Normal(age_std_deviation_unbiased_NO,User_Age,age_NO_mean).multiply(Normal(resting_bp_level_std_deviation_unbiased_NO,User_Resting_BP,resting_bp_level_mean_NO));
                        Log.i(TAG,"TRIALLERS: "+probability_of_NO_HD);
                        BigDecimal temp_long = Normal(cholestoral_std_deviation_unbiased_NO,User_Cholestrol,cholestoral_mean_NO).multiply(Normal(max_hr_std_deviation_unbiased_NO,User_Max_HR,max_hr_mean_NO));
                        BigDecimal final_prob = probability_of_NO_HD.multiply(true_data_prob_HD).multiply(temp_long);//add *0.5 here

         */
        }


    }






