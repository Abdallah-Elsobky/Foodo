package iti.student.foodo.data.network.retrofit;

import io.reactivex.rxjava3.core.Single;
import iti.student.foodo.data.model.dto.MealsResponse;
import retrofit2.http.GET;

public interface ApiService {
    @GET("search.php?f=a")
    Single<MealsResponse> getMeals();
}
