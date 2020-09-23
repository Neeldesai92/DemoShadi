package in.neel.desai.Demo.retrofitcall;


import in.neel.desai.Demo.Model.Matches;
import in.neel.desai.Demo.utils.ConstantData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IApiService {

    @GET(ConstantData.GET_MATCHES_LIST)
    Call<Matches> getMatchesList(@Query("results") String page);


}
