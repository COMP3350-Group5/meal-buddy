package comp3350.mealbuddy.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

import comp3350.mealbuddy.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NavFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SELECTED = "seclected";

    // TODO: Rename and change types of parameters
    private String mSelected;

    public NavFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param selected Parameter 1.
     * @return A new instance of fragment NavFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NavFragment newInstance(String selected) {
        NavFragment fragment = new NavFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SELECTED, selected);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSelected = getArguments().getString(ARG_SELECTED);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nav, container, false);
    }

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