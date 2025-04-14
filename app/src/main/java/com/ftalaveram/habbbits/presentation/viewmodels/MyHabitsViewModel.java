package com.ftalaveram.habbbits.presentation.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ftalaveram.habbbits.repositories.models.UserHabit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyHabitsViewModel extends ViewModel {
    private final MutableLiveData<List<UserHabit>> habitsLiveData = new MutableLiveData<>();

    public MyHabitsViewModel() {
        loadHabits();
    }

    public LiveData<List<UserHabit>> getHabits() {
        return habitsLiveData;
    }

    private void loadHabits() {
        // TODO: traerlos de la api por medio del repositorio de habitos
        List<UserHabit> habits = new ArrayList<>();
        habits.add(new UserHabit("comer", "comer todos los dias hasta reventar para tener el estomago bien lleno hasta reventar y ver que no es el destino es que no puedes mas ya con todo lo que me llevas encima ff fffffffff ffffffff fffffff ffff fffffffff ff ffff ff ffff f ffffffff f ffffff f fffffffff ffffff f fff ff  ffff ff f ffffff", 1L, new Date(), true));
        habits.add(new UserHabit("beber agua", "beber agua cada hora", 2L, new Date(), false));
        habits.add(new UserHabit("beber agua", "beber agua cada hora", 2L, new Date(), false));
        habits.add(new UserHabit("beber agua", "beber agua cada hora", 2L, new Date(), false));
        habits.add(new UserHabit("beber agua", "beber agua cada hora", 2L, new Date(), false));
        habits.add(new UserHabit("beber agua", "beber agua cada hora", 2L, new Date(), false));
        habits.add(new UserHabit("beber agua", "beber agua cada hora", 2L, new Date(), false));
        habits.add(new UserHabit("beber agua", "beber agua cada hora", 2L, new Date(), false));
        habits.add(new UserHabit("beber agua", "beber agua cada hora", 2L, new Date(), false));

        habitsLiveData.postValue(habits);
    }

    public void addHabit(UserHabit habit) {
        List<UserHabit> currentHabits = habitsLiveData.getValue();
        if (currentHabits == null) {
            currentHabits = new ArrayList<>();
        }
        currentHabits.add(habit);
        habitsLiveData.postValue(currentHabits);
    }
}