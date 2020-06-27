package comp3350.mealbuddy.presentation;

import comp3350.mealbuddy.R;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class HomeActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button login;
    private Button createAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //components
        username = (EditText) findViewById(R.id.etUsername);
        password = (EditText) findViewById(R.id.etPassword);
        login = (Button) findViewById(R.id.btnLogin);
        createAccount = (Button) findViewById(R.id.btnCreateAcc);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin(username.getText(), password.getText());
            }
        });
    }

    public void checkLogin(Editable user, Editable pass) {
        if((user.toString()).equals("Admin") && (pass.toString()).equals("1234")){
            Intent intent = new Intent(HomeActivity.this, TimelineActivity.class);
            HomeActivity.this.startActivity(intent);
        }
        else {
            //reset the input fields.
            pass.clear();
        }
    }
}