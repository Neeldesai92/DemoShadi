package in.neel.desai.Demo.retrofitcall;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import in.neel.desai.Demo.utils.CommonMethod;
import in.neel.desai.Demo.utils.ConstantData;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RestClient {
    public static String apiTenantUrl = ConstantData.BASE_URL;
    private static Retrofit retrofitApp;


    private static OkHttpClient.Builder httpClientApp = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS);

    private static Retrofit.Builder builderApp = new Retrofit.Builder()
            .baseUrl(apiTenantUrl)
            .addConverterFactory(GsonConverterFactory.create());


    private RestClient() {
    }


    public static <S> S createServiceApp(
            Class<S> serviceClass) {

        httpClientApp.addInterceptor(logging);

        builderApp.client(httpClientApp.build());
        if (retrofitApp == null)
            retrofitApp = builderApp.build();

        return retrofitApp.create(serviceClass);
    }





    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {

            if (message.contains("{\"") || message.contains("FAILED") || message.contains(ConstantData.BASE_URL)) {
                if (message.startsWith("-->")) {

                    Log.i("API","------------------------------------\n");
                    Log.i("API","Date " + new CommonMethod().getCurrentDateTime());
                    message = message.replace("--> POST", "-- INPUT : \n");
                } else if (message.startsWith("<-- 200 OK")) {
                    message = "-- OUTPUT : ";
                }
                Log.i("API",message);
            }

        }
    }).setLevel(HttpLoggingInterceptor.Level.BODY);

}
