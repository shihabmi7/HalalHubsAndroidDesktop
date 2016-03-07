package network;

/*
 * Simple async task template
 * Points of improvement: 
 * 		Orientation Check
 * 		retain non configuretion
 * 		some sort of joining mechaninsm
 * 
 * Author: Sayeed Mahmud
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

abstract class BaseTask extends AsyncTask<Void, String, Boolean> {
	Context ctx;
	// private ProgressDialog pd;
	boolean displayProgress;
	CustomListener listener = null;
	CustomListener fail_listener = null;
	String alertMessage = "";

	// Dialog customDialog;
	private ProgressDialog progress;
	
	abstract boolean task();
	abstract boolean taskOnComplete();

	public BaseTask(Context ctx, boolean displayProgress) {
		this.ctx = ctx;
		alertMessage = ("Updating");
		this.displayProgress = displayProgress;

		// LayoutInflater inflater = (LayoutInflater) ctx
		// .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//
		// View customView = inflater.inflate(R.layout.item_progress, null);
		// // Build the dialog
		// customDialog = new Dialog(ctx, R.style.CustomDialog);
		// customDialog.setContentView(customView);

		progress = new ProgressDialog(ctx);
		progress.setTitle("Loading...");
		progress.setMessage("Please wait ");
		progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progress.setIndeterminate(true);
		// progress.setCancelable(true);

	}

	/* the first param of generic is the parameter */
	/* the third param of generic is the return val */
	@Override
	protected Boolean doInBackground(Void... max) {

		return task();

	}

	@Override
	protected void onPreExecute() {

		progress.show();

		// customDialog.setOnCancelListener(new OnCancelListener() {
		//
		// @Override
		// public void onCancel(DialogInterface dialog) {
		// BaseTask.this.cancel(true);
		// }
		// });
		//
		// if (displayProgress)
		// this.customDialog.show();

	}

	/* this Void is the return type of doInBackground */
	@Override
	protected void onPostExecute(final Boolean success) {

		if (success) {
			taskOnComplete();
			if (listener != null)
				listener.ModificationMade();
		} else {
			taskOnFailure();
			if (fail_listener != null)
				fail_listener.ModificationMade();
		}

		// if (customDialog.isShowing())
		// customDialog.dismiss();

		if (progress.isShowing()) {
			progress.dismiss();
		}

	}

	@Override
	protected void onProgressUpdate(String... args) {
		super.onProgressUpdate(args);
		// pd.setMessage(args[0]);
	}



	public void setListener(CustomListener cust) {
		listener = cust;
	}

	public void setFailListener(CustomListener cust) {
		fail_listener = cust;
	}

	abstract boolean taskOnFailure();

	public void setMsg(String msg) {
		// this.pd.setMessage(msg);
	}

}
