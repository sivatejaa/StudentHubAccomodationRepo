package com.example.studenthub;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.adapters.CustomSpinnerAdapter;
import com.example.model.Accommodation;
import com.example.model.PersonalInfo;
import com.example.model.Room;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Room_Info extends AppCompatActivity {

    private EditText checkinDateEditText;
    private EditText checkoutDateEditText;
    private Calendar checkinCalendar;
    private Calendar checkoutCalendar;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_info);
        Button button = findViewById(R.id.button2);

        checkinCalendar = Calendar.getInstance();
        checkoutCalendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        Spinner roomTypeSpinner = findViewById(R.id.spinnerType);
        Spinner lease_termSprinner = findViewById(R.id.spinnerType1);
        String[] types = getResources().getStringArray(R.array.type);
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(this, android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomTypeSpinner.setAdapter(adapter);
        // Disable the dropdown items that are not available
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Accommodation/apartments");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String apartmentName = snapshot.getKey();
                    String availability = snapshot.getValue(String.class);
                    // Here you can process the data as needed
                    if (!availability.equalsIgnoreCase("Available")) {
                        // Disable the dropdown item if it's not available
                        adapter.disableItemByValue(apartmentName);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors here
            }
        });

        TextView logoutTextView = findViewById(R.id.logoutId);
        logoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement logout functionality here
                logoutUser();
            }
        });

        checkinDateEditText = findViewById(R.id.textViewCheckin);
        checkoutDateEditText = findViewById(R.id.textViewCheckout);

        checkinDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(checkinCalendar, checkinDateEditText);
            }
        });

        checkoutDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(checkoutCalendar, checkoutDateEditText);
            }
        });

        Intent intent = getIntent();
        Accommodation hotel = intent.getParcelableExtra("hotelObject");
        PersonalInfo personalInfo = intent.getParcelableExtra("personalInfo");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedRoomType = roomTypeSpinner.getSelectedItem().toString();
                String leaseTerm = lease_termSprinner.getSelectedItem().toString().trim();
                String checkinDate = checkinDateEditText.getText().toString().trim();
                String checkoutDate = checkoutDateEditText.getText().toString().trim();


                if (selectedRoomType.equals("Select Apartment type") || checkinDate.isEmpty() || checkoutDate.isEmpty()) {
                    if (selectedRoomType.equals("Select Apartment type")) {
                        showErrorDialog("Please select a room type.");
                    } else {
                        showErrorDialog("Please enter all the fields.");
                    }
                } else {
                    Intent intent = new Intent(Room_Info.this, Preview_Info.class);

                    hotel.setSelectedRoomType(selectedRoomType);
                    hotel.setCheckinDate(checkinDate);
                     hotel.setLeaseTerm(leaseTerm);
                    hotel.setCheckoutDate(checkoutDate);

                    Room room = new Room(selectedRoomType, leaseTerm, checkinDate, checkoutDate);
                   // double roomPrice = 100.0;
                   // hotel.setPrice(roomPrice);
                    calculatePrice(hotel);
                    //hotel.setPrice(roomPrice);

                    intent.putExtra("hotelObject", hotel);
                    intent.putExtra("personalInfo", personalInfo);
                    intent.putExtra("room", room);
                    intent.putExtra("roomPrice", hotel.getPrice());

                    startActivity(intent);
                }
            }
        });
    }

    private void showErrorDialog(String errorMessage) {
        new AlertDialog.Builder(Room_Info.this)
                .setTitle("Error")
                .setMessage(errorMessage)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void showDatePickerDialog(final Calendar calendar, final EditText dateEditText) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        dateEditText.setText(dateFormat.format(calendar.getTime()));
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private double calculatePrice(Accommodation hotel) {

        String roomType = hotel.getSelectedRoomType();
        String checkInDate = hotel.getCheckinDate();
        String checkoutDate = hotel.getCheckoutDate();

        double roomPrice = 0.0;
        if (roomType.contains("1 Bed")) {
            roomPrice = 100.0;
        } else if (roomType.contains("2 Bed")) {
            roomPrice = 200.0;
        } else if (roomType.contains("3 Bed")) {
            roomPrice = 300.0;
        }

        /*SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

        long differenceInDays = 0;
        try {
            Date startDate = format.parse(checkInDate);
            Date endDate = format.parse(checkoutDate);

            long differenceInMilliseconds = endDate.getTime() - startDate.getTime();
            differenceInDays = TimeUnit.MILLISECONDS.toDays(differenceInMilliseconds);

            System.out.println("Number of days between the dates: " + differenceInDays);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        roomPrice = (differenceInDays * roomPrice) * noOfRooms;*/

        String leaseTerm=hotel.getLeaseTerm();
        if (leaseTerm.equalsIgnoreCase("1 Year")) {
            roomPrice=roomPrice-(roomPrice*0.1);
        } else if (leaseTerm.equalsIgnoreCase("2 Years")) {
            roomPrice=roomPrice-(roomPrice*0.2);
        }
        else if (leaseTerm.equalsIgnoreCase("3 Years")) {
            roomPrice=roomPrice-(roomPrice*0.3);
        }

        hotel.setPrice(roomPrice);

        return roomPrice;
    }

    private void logoutUser() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(Room_Info.this, MainActivity.class);
        startActivity(intent);
        finish(); // Close the current activity after logout
    }
}
