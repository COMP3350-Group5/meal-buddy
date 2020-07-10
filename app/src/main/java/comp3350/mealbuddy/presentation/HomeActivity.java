/****************************************
 * HomeActivity
 * landing page UI
 ****************************************/
package comp3350.mealbuddy.presentation;

import comp3350.mealbuddy.R;
import comp3350.mealbuddy.business.AccessAccount;
import comp3350.mealbuddy.objects.Account;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    private AccessAccount accessAccount = new AccessAccount();

    /*
     * onCreate
     * called when the activity is initially created
     * Parameters:
     *     @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        EditText username = findViewById(R.id.etUsername);
        EditText password = findViewById(R.id.etPassword);
        Button login = findViewById(R.id.btnLogin);
        TextView createAccount = findViewById(R.id.btnCreateAccount);


        //link the on click listeners
        login.setOnClickListener((view) -> checkLogin(username.getText(), password.getText()));
        createAccount.setOnClickListener((view) -> {
                Intent intent = new Intent(HomeActivity.this, SignUpActivity.class);
                HomeActivity.this.startActivity(intent);
        });
    }

    /*
     * checkLogin
     * verify the entered username and password.
     * Parameters:
     *     @param user
     *     @param pass
     */
    public void checkLogin(Editable user, Editable pass) {
        Account account = accessAccount.validateLogin(user.toString(), pass.toString());
        if(account != null){
            Intent intent = new Intent(HomeActivity.this, TimelineActivity.class);
            intent.putExtra("username", user.toString());
            HomeActivity.this.startActivity(intent);
        }
        else {
            //reset the input fields.
            user.clear();
            pass.clear();
        }
    }
}
