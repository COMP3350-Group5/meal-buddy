/****************************************
 * HomeActivity
 * landing page UI
 ****************************************/
package comp3350.mealbuddy.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import comp3350.mealbuddy.R;
import comp3350.mealbuddy.application.Main;
import comp3350.mealbuddy.business.AccessAccount;
import comp3350.mealbuddy.objects.Account;


public class HomeActivity extends AppCompatActivity {
    private AccessAccount accessAccount;

    /*
     * onCreate
     * called when the activity is initially created
     * Parameters:
     *     @param savedInstanceState
     */
    @Override
    protected synchronized void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        copyDatabaseToDevice();
        Main.startUp();
        accessAccount = new AccessAccount();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Main.shutDown();
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

    private synchronized void copyDatabaseToDevice() {
        final String DB_PATH = "db";

        String[] assetNames;
        Context context = getApplicationContext();
        File dataDirectory = context.getDir(DB_PATH, Context.MODE_PRIVATE);
        AssetManager assetManager = getAssets();

        try {

            assetNames = assetManager.list(DB_PATH);
            for (int i = 0; i < assetNames.length; i++) {
                assetNames[i] = DB_PATH + "/" + assetNames[i];
            }

            copyAssetsToDirectory(assetNames, dataDirectory);

            Main.setDBPathName(dataDirectory.toString() + "/" + Main.DATABASE_NAME);
        } catch (IOException ioe) {
            Messages.warning(this, "Unable to access application data: " + ioe.getMessage());
        }
    }

    public synchronized void copyAssetsToDirectory(String[] assets, File directory) throws IOException {
        AssetManager assetManager = getAssets();

        for (String asset : assets) {
            String[] components = asset.split("/");
            String copyPath = directory.toString() + "/" + components[components.length - 1];
            char[] buffer = new char[1024];
            int count;

            File outFile = new File(copyPath);

            if (!outFile.exists()) {
                InputStreamReader in = new InputStreamReader(assetManager.open(asset));
                FileWriter out = new FileWriter(outFile);

                count = in.read(buffer);
                while (count != -1) {
                    out.write(buffer, 0, count);
                    count = in.read(buffer);
                }

                out.close();
                in.close();
            }
        }
    }
}
