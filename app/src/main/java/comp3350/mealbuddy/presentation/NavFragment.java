package comp3350.mealbuddy.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

import comp3350.mealbuddy.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NavFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavFragment extends Fragment {


    /*
     * NavFragment
     * default constructor
     */
    public NavFragment() {
        // Required empty public constructor
    }

    /*
     * OnCreate
     * inits the nav bar
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    /*
     * OnCreateView
     * creates the view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nav, container, false);
    }

    /*
     * OnViewCreated
     * intializes the nav bar and sets the funcitonality of switching activities
     * Parameters
     *      @view - the current view for the nav bar
     *      @savedInstanceBundle - class holding info regarding the saved state
     */
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Activity activity = getActivity();
        Calendar calendar = Calendar.getInstance();
        int dayOfYear = activity.getIntent().getIntExtra("dayOfYear", calendar.get(Calendar.DAY_OF_YEAR));
        String username = activity.getIntent().getStringExtra("username");
        BottomNavigationView nav = getView().findViewById(R.id.bottom_navigation);
        nav.setOnNavigationItemSelectedListener((MenuItem item) -> {
            Intent intent;
            switch (item.getItemId()) {
                case R.id.action_goals:
                    ChangeActivityHelper.changeActivity(activity, GoalActivity.class, username, dayOfYear);
                    break;
                case R.id.action_stats:
                    ChangeActivityHelper.changeActivity(activity, ViewStatsActivity.class, username, dayOfYear);
                    break;
                case R.id.action_account:
                    ChangeActivityHelper.changeActivity(activity, AccountActivity.class, username, dayOfYear);
                    break;
                case R.id.action_timeline:
                    ChangeActivityHelper.changeActivity(activity, TimelineActivity.class, username, dayOfYear);
                    break;
            }
            return true;
        });
    }
}