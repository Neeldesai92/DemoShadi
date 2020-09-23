package in.neel.desai.Demo.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import in.neel.desai.Demo.R;
import in.neel.desai.Demo.fragment.MatchesListFragment;
import in.neel.desai.Demo.utils.CommonMethod;



public class HomeActivity extends AppCompatActivity {


    private Toolbar mToolbar;
    public Activity activity;
    private FrameLayout mFrameContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        setToolbar("Matches");
        initView();


    }

    /**
     * Set toolbar
     */
    private void setToolbar(String Title) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setContentInsetStartWithNavigation(getResources().getInteger(R.integer.dimen_spacing_between_toolbar_icon_and_title));
        setSupportActionBar(mToolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mToolbar.setTitleTextColor(ContextCompat.getColor(HomeActivity.this, android.R.color.white));
        mToolbar.setTitle(Title);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * initialization Views
     */
    private void initView() {

        mFrameContainer = (FrameLayout) findViewById(R.id.frame_container);
        switchContent(new MatchesListFragment(), null);

    }


    /**
     * Switch Fragments
     */
    @SuppressLint("NewApi")
    public void switchContent(Fragment fragment, Bundle bundle) {

        if (bundle != null) {
            fragment.setArguments(bundle);
        }

        mToolbar.setTitle("Matches");
        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slide_from_right, R.anim.slide_to_left, R.anim.slide_to_right);
        fragmentTransaction.replace(R.id.frame_container, fragment).commit();

    }

    /**
     * Resume
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        CommonMethod.hideKeyboard(this);


        showAlertForLogout(false);


    }

    /**
     * LogOut/Exit dialog
     */
    private void showAlertForLogout(final boolean Isforlogout) {
        String msg = "";
        if (Isforlogout) {
            msg = "Are you sure you want to Logout?";

        } else {
            msg = "Are you sure you want to exit?";
        }

        String Title = "";
        if (Isforlogout) {
            Title = "Confirm Logout";

        } else {
            Title = "Confirm Exit";
        }
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                HomeActivity.this);
        alertDialogBuilder.setTitle(Title);
        alertDialogBuilder

                .setMessage(msg)
                .setCancelable(true)
                .setPositiveButton(getString(R.string.yes),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                finish();

                            }
                        })
                .setNegativeButton(getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
