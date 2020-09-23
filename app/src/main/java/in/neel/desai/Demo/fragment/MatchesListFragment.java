package in.neel.desai.Demo.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.neel.desai.Demo.Adapter.MatchesListAdapter;
import in.neel.desai.Demo.Model.Matches;
import in.neel.desai.Demo.Model.Result;
import in.neel.desai.Demo.R;
import in.neel.desai.Demo.database.DatabaseClient;
import in.neel.desai.Demo.retrofitcall.IApiService;
import in.neel.desai.Demo.retrofitcall.RestClient;
import in.neel.desai.Demo.utils.CommonMethod;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MatchesListFragment extends Fragment {

    private View view;
    private RecyclerView RvDocketList;
    public MatchesListAdapter adapter;

    private ArrayList<Result> Matches = new ArrayList<>();


    public MatchesListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.matches_fragment, container, false);

        Matches = (ArrayList<Result>) DatabaseClient.getInstance(getActivity()).getAppDatabase()
                .taskDao().getAll();



        if (Matches != null && Matches.size() > 0) {
            setUpDeliveryRecyclerView();
        } else {

            if (CommonMethod.isNetworkConnected(getActivity())) {
                CommonMethod.showProgressDialog(getActivity());
                Call<Matches> call = RestClient.createServiceApp(IApiService.class)
                        .getMatchesList("10");
                call.enqueue(new Callback<Matches>() {
                    @Override
                    public void onResponse(Call<Matches> call, Response<Matches> response) {
                        CommonMethod.cancelProgressDialog();
                        if (response != null) {
                            if (response.isSuccessful()) {
                                Matches outputModel = response.body();
                                if (outputModel != null && outputModel.getResults().size() > 0) {

                                    for (Result data : outputModel.getResults()) {
                                        if (data.getId().getValue() == null) {
                                            data.getId().setValue("");
                                        }
                                        //adding to database
                                        DatabaseClient.getInstance(getActivity()).getAppDatabase()
                                                .taskDao()
                                                .insert(data);
                                    }


                                    Matches = (ArrayList<Result>) DatabaseClient.getInstance(getActivity()).getAppDatabase()
                                            .taskDao().getAll();



                                    setUpDeliveryRecyclerView();

                                } else {
                                    Toast.makeText(getActivity(), "There is no match available.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Matches> call, Throwable throwable) {
                        throwable.printStackTrace();
                        CommonMethod.cancelProgressDialog();
                    }
                });
            } else {
                CommonMethod.showConnectionAlert(getActivity());
            }

        }


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();


    }

    /**
     * get List from database and set with view
     */
    private void setUpDeliveryRecyclerView() {


        try {

            if (Matches != null && Matches.size() > 0) {
                VisibleNodataLayout(false);

                adapter = new MatchesListAdapter(Matches, this);
                RvDocketList.setAdapter(adapter);
            } else {
                VisibleNodataLayout(true);
            }
        } catch (Exception ex) {
            Log.i("Exception", ex.getMessage());
        } finally {

            CommonMethod.cancelProgressDialog();
        }


    }


    private void VisibleNodataLayout(boolean b) {
        RelativeLayout layout = view.findViewById(R.id.rl_no_data_found);
        if (b) {
            RvDocketList.setVisibility(View.GONE);
            layout.setVisibility(View.VISIBLE);
        } else {
            RvDocketList.setVisibility(View.VISIBLE);
            layout.setVisibility(View.GONE);

        }
    }

    /**
     * initialization Views
     */
    private void init() {
        RvDocketList = view.findViewById(R.id.rvDocketList);
        RvDocketList.setLayoutManager(new LinearLayoutManager(getActivity()));
        CommonMethod.showProgressDialog(getActivity());


        setUpDeliveryRecyclerView();


    }

    @Override
    public void onResume() {
        super.onResume();

    }


}