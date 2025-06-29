package com.ftalaveram.habbbits.presentation.fragments;

import static android.view.View.GONE;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ftalaveram.habbbits.R;
import com.ftalaveram.habbbits.databinding.FragmentCreateHabitBinding;
import com.ftalaveram.habbbits.presentation.viewmodels.CreateHabitsViewModel;
import com.ftalaveram.habbbits.repositories.models.CreateResponse;
import com.ftalaveram.habbbits.repositories.models.UpdateResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

public class CreateHabitFragment extends Fragment {

    private FragmentCreateHabitBinding binding;
    private CreateHabitsViewModel createHabitsViewModel;
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
            name = getArguments().getString(NAME);
            description = getArguments().getString(DESCRIPTION);
            isUpdate = getArguments().getBoolean(IS_UPDATE);
            id = getArguments().getLong(ID);
            isPublic = getArguments().getBoolean(PUBLIC);
        }
        createHabitsViewModel = new ViewModelProvider(this).get(CreateHabitsViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateHabitBinding.inflate(inflater, container, false);
        
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (isUpdate == null || name == null || description == null || isPublic == null){
            setupCreate();
        }else if (isUpdate){
            setupUpdate();
        }else{
            setupCreate();
        }
    }

    private void setupUpdate(){
        binding.title.setText(getString(R.string.update_title_right_space, binding.title.getText()));
        binding.btnSend.setText(getString(R.string.update_title));

        binding.nameInput.setText(name);
        binding.descriptionInput.setText(description);
        binding.publicSwitch.setChecked(isPublic);

        binding.frequencyLabel.setVisibility(GONE);
        binding.frequencyInputs.setVisibility(GONE);
        binding.dateLabel.setVisibility(GONE);
        binding.dateInputLayout.setVisibility(GONE);

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = "";

                if (validName()){
                    nombre = String.valueOf(binding.nameInput.getText());
                }

                String descripcion = "";

                if (validDescription()){
                    descripcion = String.valueOf(binding.descriptionInput.getText());
                }

                boolean publico = binding.publicSwitch.isChecked();

                if (validUpdate(nombre, descripcion)){
                    createHabitsViewModel.updateHabit(nombre, descripcion, publico, id);

                    createHabitsViewModel.updateLiveData.observe(getViewLifecycleOwner(), new Observer<UpdateResponse>() {
                        @Override
                        public void onChanged(UpdateResponse updateResponse) {
                            mostrarDialogCompleto(getString(R.string.updated), v);
                            Navigation.findNavController(v).navigateUp();
                        }
                    });
                }else{
                    Toast.makeText(getContext(), getText(R.string.update_not_valid), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void setupCreate(){

        binding.title.setText(getString(R.string.create_title_right_space, binding.title.getText()));
        binding.btnSend.setText(getString(R.string.create_title));

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

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = "";

                if (validName()){
                    nombre = String.valueOf(binding.nameInput.getText());
                }

                String descripcion = "";

                if (validDescription()){
                    descripcion = String.valueOf(binding.descriptionInput.getText());
                }

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

                String creadoEn = sdf.format(new Date());

                String fechaOriginal = String.valueOf(binding.dateInput.getText());
                SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                Date date = null;
                String fechaInicio = "";

                if (validInitialDate()){
                    try {
                        date = formatoEntrada.parse(fechaOriginal);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                    SimpleDateFormat formatoSalida = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                    formatoSalida.setTimeZone(TimeZone.getTimeZone("UTC"));
                    assert date != null;
                    fechaInicio = formatoSalida.format(date);
                }

                int horasIntervalo = 0;

                if (validNumber()){
                    horasIntervalo = Integer.parseInt(String.valueOf(binding.numberFrequencyInput.getText()));
                    if (String.valueOf(binding.frequencySpinner.getSelectedItem()).equals(getString(R.string.days))){
                        horasIntervalo *= 24;
                    }
                }else{
                    binding.numberFrequencyInput.setError("Required");
                }

                boolean publico = binding.publicSwitch.isChecked();

                if (validCreate(nombre, descripcion, creadoEn, fechaInicio, horasIntervalo)){
                    createHabitsViewModel.createHabit(nombre, descripcion, creadoEn, fechaInicio, horasIntervalo, publico);

                    createHabitsViewModel.createLiveData.observe(getViewLifecycleOwner(), new Observer<CreateResponse>() {
                        @Override
                        public void onChanged(CreateResponse createResponse) {
                            mostrarDialogCompleto(getString(R.string.created), v);
                            Navigation.findNavController(v).navigateUp();
                        }
                    });
                }else{
                    Toast.makeText(getContext(), getText(R.string.update_not_valid), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void setupSpinner(){

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                requireContext(),
                R.layout.custom_spinner_item,
                new String[]{getString(R.string.hours), getString(R.string.days)}
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

        Calendar calendar = Calendar.getInstance();
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH);
        int anio = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, year, month, dayOfMonth) -> {
                    calendar.set(year, month, dayOfMonth);

                    int hora = calendar.get(Calendar.HOUR_OF_DAY);
                    int minuto = calendar.get(Calendar.MINUTE);

                    TimePickerDialog timePickerDialog = new TimePickerDialog(
                            requireContext(),
                            (timeView, hourOfDay, minute) -> {
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);

                                String fechaHora = String.format("%02d/%02d/%d %02d:%02d", dayOfMonth, month + 1, year, hourOfDay, minute);

                                binding.dateInput.setText(fechaHora);
                            },
                            hora, minuto, true //true para formato 24 horas
                    );
                    timePickerDialog.show();
                },
                anio, mes, dia
        );

        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }

    private void mostrarDialogCompleto(String mensaje, View view) {
        Context context = view.getContext();

        Dialog dialog = new Dialog(context, R.style.DialogNoTitle);
        dialog.setContentView(R.layout.dialog_habito_completado);

        TextView tvTitulo = dialog.findViewById(R.id.tvTitulo);
        ImageView ivIcono = dialog.findViewById(R.id.ivIcono);
        Button btnAceptar = dialog.findViewById(R.id.btnAceptar);

        tvTitulo.setText(mensaje);

        Animation anim = AnimationUtils.loadAnimation(context, R.anim.bounce);
        ivIcono.startAnimation(anim);

        btnAceptar.setOnClickListener(v -> dialog.dismiss());

        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        dialog.show();
    }

    private boolean validUpdate(String name, String description){
        if (name != null && description != null){
            return !name.isEmpty() && !description.isEmpty();
        }
        return false;
    }

    private boolean validCreate(String name, String description, String creadoEn, String fechaInicio, int horasIntervalo){
        if (name != null && description != null && creadoEn != null && fechaInicio != null){
            return !name.isEmpty() && !description.isEmpty() && !creadoEn.isEmpty() && !fechaInicio.isEmpty() && horasIntervalo > 0;
        }
        return false;
    }

    private boolean validName(){
        if (Objects.equals(Objects.requireNonNull(binding.nameInput.getText()).toString(), "")){
            binding.nameInput.setError(getString(R.string.required));
            return false;
        }
        return true;
    }

    private boolean validDescription(){
        if (Objects.equals(Objects.requireNonNull(binding.descriptionInput.getText()).toString(), "")){
            binding.descriptionInput.setError(getString(R.string.required));
            return false;
        }
        return true;
    }

    private boolean validNumber(){
        if (Objects.equals(Objects.requireNonNull(binding.numberFrequencyInput.getText()).toString(), "")){
            binding.numberFrequencyInput.setError(getString(R.string.required));
            return false;
        }
        return true;
    }

    private boolean validInitialDate(){
        if (Objects.equals(Objects.requireNonNull(binding.dateInput.getText()).toString(), "")){
            binding.dateInput.setError(getString(R.string.required));
            return false;
        }
        return true;
    }
}