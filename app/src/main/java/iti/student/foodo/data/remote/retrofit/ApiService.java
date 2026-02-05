package iti.student.foodo.data.remote.retrofit;

import iti.student.foodo.data.model.MealsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("search.php?f=a")
    Call<MealsResponse> getMeals();
}
