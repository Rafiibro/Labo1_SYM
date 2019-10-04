/*
 * File     : MainActivity.java
 * Project  : TemplateActivity
 * Author   : Rafael Da Cunha Garcia, Gaetan Bacso, Remy Vuagniaux
 *            IICT / HEIG-VD
 *                                       
 * mailto:fabien.dutoit@heig-vd.ch
 * 
 * This piece of code reads a [email_account / password ] combination.
 * It is used as a template project for the SYM module element given at HEIG-VD
 * Target audience : students IL, TS, IE [generally semester 1, third bachelor year]
 *   
 * THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY 
 * AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL 
 * THE REGENTS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, 
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED 
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 */
package ch.heigvd.sym.template;

import android.content.res.Configuration;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // For logging purposes
    private static final String TAG = MainActivity.class.getSimpleName();

    Map<String, String> mapEmail= new HashMap<>();

    // Just for test purposes : please destroy !
	private static final String validEmail      = "a";
	private static final String validPassword   = "b";

    // GUI elements
	private EditText email      = null;
    private Button   signIn     = null;
    private EditText password 	= null;

	@Override
	protected void onStart() {
		super.onStart();
		Log.i("Info labo 1: ", "onStart()");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.i("Info labo 1: ", "onResume()");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.i("Info labo 1: ", "onPause()");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.i("Info labo 1: ", "onStop()");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.i("Info labo 1: ", "onDestroy()");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

		Log.i("Info labo 1: ", "onCreate()");

		// Pour mettre le programme en Hindi, indiqué le langage dans languageToLoad
		String languageToLoad = "en";
		Locale locale = new Locale(languageToLoad);
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		getResources().updateConfiguration(config,getResources().getDisplayMetrics());

		// Show the welcome screen / login authentication dialog
		setContentView(R.layout.authent);

		// Emails valide pour le login
		mapEmail.put("a@b.ch", "ab");
		mapEmail.put("c@d.ch", "cd");

		// Link to GUI elements
        this.email      = findViewById(R.id.email);
		this.password   = findViewById(R.id.password);
        this.signIn     = findViewById(R.id.buttOk);

		// Then program action associated to "Ok" button
		signIn.setOnClickListener((v) -> {

			// Récuperation du mail et mdp
			String mail = email.getText().toString();
			String passwd = password.getText().toString(); //TODO read password from EditText

			// Detecte la presence d'un @
			if (!mail.contains("@")) {
				Toast.makeText(MainActivity.this, getResources().getString(R.string.noat), Toast.LENGTH_LONG).show();
				email.getText().clear();
				password.getText().clear();
			}
			// test la validité des données user
			else if (isValid(mail, passwd)) {
				// affiche le toast, lance la deuxième activité
				Toast.makeText(MainActivity.this, getResources().getString(R.string.good), Toast.LENGTH_LONG).show();
				Intent intent = new Intent(MainActivity.this, AfterConnectedActivity.class);
				intent.putExtra("mail",mail);
				startActivity(intent);
			} else {
				// Wrong combination, display pop-up dialog and stay on login screen
				showErrorDialog(mail, passwd);
			}
		});
	}

	private boolean isValid(String mail, String passwd) {
        if(mail == null || passwd == null) {
            Log.w(TAG, "isValid(mail, passwd) - mail and passwd cannot be null !");
            return false;
        }
		// Return true if combination valid, false otherwise
		return (mapEmail.containsKey(mail) && mapEmail.get(mail).equals(passwd));
	}

	private void showErrorDialog(String mail, String passwd) {
		/*
		 * Pop-up dialog to show error
		 */
		email.getText().clear();
		password.getText().clear();
		AlertDialog.Builder alertbd = new AlertDialog.Builder(this);
		alertbd.setIcon(R.drawable.ic_icon_cat);
		alertbd.setTitle(R.string.wronglogin);
	    alertbd.setMessage(R.string.wrong);
	    alertbd.setPositiveButton(android.R.string.ok, (dialog, which) -> {
			// we do nothing...
			// dialog close automatically
	    });
	    alertbd.create().show();
	}
	
}
