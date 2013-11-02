package edu.columbia.w4156.ase.android;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SetupActivity extends Activity {

	private static final String TAG = SetupActivity.class.getSimpleName();
	private SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup);
		preferences = (SharedPreferences) getSharedPreferences("surveySays",
				Context.MODE_PRIVATE);
		if (!preferences.contains("serverUrl")) {
			Editor e = preferences.edit();
			e.putString("serverUrl", "http://localhost");
			e.commit();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		TextView tv = (TextView) findViewById(R.id.setupGreetingTextView);
		Button button = (Button) findViewById(R.id.setupButton);

		PackageManager pm = getApplicationContext().getPackageManager();
		final Intent scanBarcodeIntent =
				new Intent("com.google.zxing.client.android.SCAN");
		scanBarcodeIntent.setPackage("com.google.zxing.client.android");
		scanBarcodeIntent.putExtra("SCAN_FORMATS", "QR_CODE");

		final List<ResolveInfo> eligibleIntents =
				pm.queryIntentActivities(scanBarcodeIntent,
						PackageManager.MATCH_DEFAULT_ONLY);

		((EditText) findViewById(R.id.urlEditText))
			.setText(preferences.getString("serverUrl", ""));

		if (eligibleIntents.isEmpty()) {
			tv.setText(R.string.setup_barcode_scanner_missing);
			button.setText(R.string.setup_playstore_button_msg);
			button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent =
							new Intent(Intent.ACTION_VIEW,
									Uri.parse("https://play.google.com/store/apps/" +
									"details?id=com.google.zxing.client." +
									"android"));
					startActivity(intent);
				}
			});
		} else {
			if (!preferences.contains("authKey")) {
				tv.setText(R.string.setup_scan_barcode_msg);				
			} else {
				tv.setText(R.string.setup_rescan_barcode_msg);
			}

			button.setText(R.string.setup_scan_button_msg);
			button.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					startActivityForResult(scanBarcodeIntent, 1);
				}
			});
		}		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i(TAG, "onActivityResult(" + requestCode + ", " + resultCode + ")");
		switch (requestCode) {
		case 1:
			if (resultCode == Activity.RESULT_OK) {
				String qrData = data.getStringExtra("SCAN_RESULT");
				int split = qrData.lastIndexOf(":");
				String authKey = qrData.substring(split + 1);
				String url = qrData.substring(0, split);

				Editor editor = preferences.edit();
				editor.putString("authKey", authKey);
				editor.commit();
				((EditText) findViewById(R.id.urlEditText)).setText(url);
				saveButtonClicked(null);
			} else {
				Toast.makeText(getApplicationContext(), "Scan Unsuccessful",
						Toast.LENGTH_LONG).show();
			}
			break;
		default:
			throw new IllegalStateException();
		}
	}

	public void saveButtonClicked(final View view) {
		EditText url = (EditText) findViewById(R.id.urlEditText);
		Uri uri = Uri.parse(url.getText().toString());

		if ((!"http".equals(uri.getScheme()) &&
			 !"https".equals(uri.getScheme())) ||
			"".equals(uri.getAuthority()) ||
			uri.getAuthority() == null) {
			Toast.makeText(getApplicationContext(), "Invalid URL",
					Toast.LENGTH_SHORT).show();
			return;
		}

		Editor editor = preferences.edit();
		editor.putString("serverUrl", uri.toString());
		editor.commit();
		Toast.makeText(getApplicationContext(), "Saved!",
				Toast.LENGTH_SHORT).show();
	}
}
