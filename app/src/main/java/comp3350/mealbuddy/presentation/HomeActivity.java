package comp3350.mealbuddy.presentation;

import comp3350.mealbuddy.R;
import comp3350.mealbuddy.business.AccessAccount;
import comp3350.mealbuddy.objects.Account;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class HomeActivity extends AppCompatActivity {
    private AccessAccount accessAccount = new AccessAccount();
    private EditText username;
    private EditText password;
    private Button login;
    private TextView createAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //components
        username = (EditText) findViewById(R.id.etUsername);
        password = (EditText) findViewById(R.id.etPassword);
        login = (Button) findViewById(R.id.btnLogin);
        createAccount = (TextView) findViewById(R.id.tvCreateAccount);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin(username.getText(), password.getText());
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SignUpActivity.class);
                HomeActivity.this.startActivity(intent);
            }
        });
    }

    public void checkLogin(Editable user, Editable pass) {
        Account account = accessAccount.validateLogin(user.toString(), pass.toString());
        if(account != null){
            Intent intent = new Intent(HomeActivity.this, TimelineActivity.class);
            intent.putExtra("username", user.toString());
            HomeActivity.this.startActivity(intent);
        }
        else {
            //reset the input fields.
            pass.clear();
        }
    }
}
