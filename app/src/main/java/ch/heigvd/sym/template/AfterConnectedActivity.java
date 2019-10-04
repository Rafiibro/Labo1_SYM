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

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.single.BasePermissionListener;
import java.io.File;

public class AfterConnectedActivity extends AppCompatActivity {

    // For logging purposes
    private static final String TAG = AfterConnectedActivity.class.getSimpleName();

    private String mail = "";
    private String imeiS = "";
    private TextView email      = null;
    private TextView emei       = null;
    private ImageView image = null;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Show the welcome screen / login authentication dialog
        setContentView(R.layout.afterauthent);

        Intent intent = getIntent();
        mail = intent.getStringExtra("mail");

        this.email      = findViewById(R.id.mail);
        this.emei      = findViewById(R.id.emei);
        this.image = findViewById(R.id.imageView);

        // Check les permissions
        if ( ContextCompat.checkSelfPermission( this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
            // Demande avec un pop-up
            Dexter.withActivity(this)
                    .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .withListener(new BasePermissionListener())
                    .check();
        }

        // Recupère l'image
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File imgFile = new File(path, "perso.jpg");

        // Test image
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            image.setImageBitmap(myBitmap);
        }else {
            Log.w(TAG, "Image not found");
        }

        // Check permission pour l'IMEI
        if ( ContextCompat.checkSelfPermission( this, Manifest.permission.READ_PHONE_STATE ) != PackageManager.PERMISSION_GRANTED ) {
            // Demande l'autorisation
            Dexter.withActivity(this)
                    .withPermission(Manifest.permission.READ_PHONE_STATE)
                    .withListener(new BasePermissionListener())
                    .check();
        } else {
            // Recupere l'IMEI
            TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
            imeiS = telephonyManager.getDeviceId();
            this.emei.setText(imeiS);
        }

        this.email.setText(mail);
    }
}
