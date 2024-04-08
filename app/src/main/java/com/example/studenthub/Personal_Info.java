package com.example.studenthub;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.model.Accommodation;
import com.example.model.PersonalInfo;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Personal_Info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        TextView logoutTextView = findViewById(R.id.logoutId);
        logoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement logout functionality here
                logoutUser();
            }
        });
        EditText nameEditText = findViewById(R.id.edName);
        EditText emailEditText = findViewById(R.id.edEmail);

        EditText phoneEditText = findViewById(R.id.edPhone);
        phoneEditText.addTextChangedListener(new TextWatcher() {
            private boolean isFormatting;
            private boolean deletingHyphen;
            private int hyphenStart;
            private boolean deletingBackward;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (count > 0 && s.charAt(start) == '-') {
                    deletingHyphen = true;
                    hyphenStart = start;
                } else {
                    deletingHyphen = false;
                }
                deletingBackward = count > after;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isFormatting) {
                    isFormatting = true;

                    // Remove all formatting characters (hyphens) before applying formatting
                    String phone = s.toString().replaceAll("[^\\d]", "");
                    StringBuilder formattphoneEditText = new StringBuilder();

                    // Format the phone number
                    for (int i = 0; i < phone.length(); i++) {
                        if (i == 0) {
                            formattphoneEditText.append("(");
                        } else if (i == 3) {
                            formattphoneEditText.append(") ");
                        } else if (i == 6) {
                            formattphoneEditText.append("-");
                        }
                        formattphoneEditText.append(phone.charAt(i));
                    }

                    // Set the formatted phone number to the EditText
                    phoneEditText.removeTextChangedListener(this);
                    phoneEditText.setText(formattphoneEditText.toString());
                    phoneEditText.setSelection(formattphoneEditText.length());
                    phoneEditText.addTextChangedListener(this);

                    isFormatting = false;

                    // Validate the length of the phone number
                    if (phone.length() > 10) {
                        phoneEditText.setError("Phone number cannot exceed 10 digits");
                    } else if (phone.length() < 10) {
                        phoneEditText.setError("Phone number must have 10 digits");
                    } else {
                        phoneEditText.setError(null);
                    }
                }
            }
        });
        EditText addressEditText = findViewById(R.id.editTexAddress);
        Button button = findViewById(R.id.button);

        Intent intent = getIntent();
        Accommodation hotel = intent.getParcelableExtra("hotelObject");


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String name = nameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();

                String address = addressEditText.getText().toString().trim();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(address)) {

                    new AlertDialog.Builder(Personal_Info.this)
                            .setTitle("Error")
                            .setMessage("Please enter all the fields.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }
                if (!isValidEmail(email)) {
                    emailEditText.setError("Invalid email address");
                } else {
                    Intent roomInfoIntent = new Intent(Personal_Info.this, Room_Info.class);

                    roomInfoIntent.putExtra("hotelObject", hotel);
                    PersonalInfo personalInfo = new PersonalInfo(name, email, address, phone);

                    roomInfoIntent.putExtra("personalInfo", personalInfo);

                    Log.d("hotel.getName()", hotel.getName());
                    startActivity(roomInfoIntent);
                }


            }
        });
    }

    public static boolean isValidEmail(String email) {
        // Regular expression to validate email address
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:gmail)\\.(?:com)$";

        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    private void logoutUser() {

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(Personal_Info.this, MainActivity.class);
        startActivity(intent);
        finish(); // Close the current activity after logout
    }
}
