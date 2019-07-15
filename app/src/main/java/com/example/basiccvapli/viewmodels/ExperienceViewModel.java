package com.example.basiccvapli.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.basiccvapli.R;
import com.example.basiccvapli.models.Experience;
import com.example.basiccvapli.repository.ExperienceRepository;
import com.google.firebase.database.DataSnapshot;

public class ExperienceViewModel extends ViewModel {

    public MutableLiveData<String> companyName = new MutableLiveData<>();
    public MutableLiveData<String> industry = new MutableLiveData<>();
    public MutableLiveData<String> designation = new MutableLiveData<>();
    public MutableLiveData<String> location = new MutableLiveData<>();
    public MutableLiveData<String> from = new MutableLiveData<>();
    public MutableLiveData<String> to = new MutableLiveData<>();

    private ExperienceRepository repository;

    private Context context;

    public void init(Context context) {
        this.context = context;
        repository = new ExperienceRepository(context);
    }

    public void save() {
        Experience experience = new Experience();
        experience.setCompanyName(companyName.getValue());
        experience.setIndustry(industry.getValue());
        experience.setDesignation(designation.getValue());
        experience.setLocation(location.getValue());
        experience.setFromDate(from.getValue());
        experience.setToDate(to.getValue());
        repository.save(experience);
    }

    public LiveData<DataSnapshot> getDetails() {
        return repository.getData();
    }

    public boolean anyBlank() {
        return (companyName == null || companyName.toString().isEmpty() || designation == null ||
                designation.toString().isEmpty() || industry == null || industry.toString().isEmpty() ||
                location == null || location.toString().isEmpty() || from == null ||
                from.toString().equals(context.getString(R.string.click_to_set_the_date)) ||
                to == null || to.toString().equals(context.getString(R.string.click_to_set_the_date)));
    }
}
