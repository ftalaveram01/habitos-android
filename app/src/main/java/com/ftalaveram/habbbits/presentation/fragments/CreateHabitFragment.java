package com.ftalaveram.habbbits.presentation.fragments;

import static android.view.View.GONE;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;

import com.ftalaveram.habbbits.R;
import com.ftalaveram.habbbits.databinding.FragmentCreateHabitBinding;
import com.ftalaveram.habbbits.databinding.FragmentMyHabitsBinding;

import java.util.Calendar;

public class CreateHabitFragment extends Fragment {

    private FragmentCreateHabitBinding binding;
    private ArrayAdapter<String> spinnerAdapter;
    private String selected = "";

    private static final String IS_UPDATE = "isUpdate";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String PUBLIC = "isPublic";

    private Boolean isUpdate;
    private Long id;
    private String name;
    private String description;
    private Boolean isPublic;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isUpdate = getArguments().getBoolean(IS_UPDATE);
            id = getArguments().getLong(ID);
            name = getArguments().getString(NAME);
            description = getArguments().getString(DESCRIPTION);
            isPublic = getArguments().getBoolean(PUBLIC);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateHabitBinding.inflate(inflater, container, false);
        
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (isUpdate != null && name != null && description != null && isPublic != null){
            if (isUpdate){
                setupUpdate();
            }
        }else{
            setupCreate();
        }
    }

    private void setupUpdate(){
        binding.title.setText("UPDATE " + binding.title.getText());
        binding.btnSend.setText("UPDATE");

        binding.nameInput.setText(name);
        binding.descriptionInput.setText(description);
        binding.publicSwitch.setChecked(isPublic);

        binding.frequencyLabel.setVisibility(GONE);
        binding.frequencyInputs.setVisibility(GONE);
        binding.dateLabel.setVisibility(GONE);
        binding.dateInputLayout.setVisibility(GONE);
    }

    public void setupCreate(){

        binding.title.setText("CREATE " + binding.title.getText());
        binding.btnSend.setText("CREATE");

        if (name != null){
            binding.nameInput.setText(name);
        }
        if (description != null){
            binding.descriptionInput.setText(description);
        }

        setupSpinner();
        binding.dateInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    mostrarDatePicker();
                }
            }
        });

        binding.dateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDatePicker();
            }
        });
    }

    private void setupSpinner(){

        spinnerAdapter = new ArrayAdapter<>(
                requireContext(),
                R.layout.custom_spinner_item,
                new String[]{"Hours", "Days"}
        );

        binding.frequencySpinner.setAdapter(spinnerAdapter);

        binding.frequencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = (String) parent.getItemAtPosition(position);
                Log.d("DEBUG DE SPINNER", selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void mostrarDatePicker() {
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int anio, int mes, int dia) {
                binding.dateInput.setText(dia + "/" + (mes + 1) + "/" + anio);
            }
        };

        Calendar calendar = Calendar.getInstance();
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH);
        int anio = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), listener, anio, mes, dia);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }
}