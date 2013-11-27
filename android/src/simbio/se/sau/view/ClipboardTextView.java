package simbio.se.sau.view;

import simbio.se.sau.API;
import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * @author Ademar Alves de Oliveira (ademar111190@gmail.com)
 * @date Nov 27, 2013 2:21:45 PM
 * @since {@link API#Version_3_0_0}
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ClipboardTextView extends TextView implements OnClickListener {

	/**
	 * @author Ademar Alves de Oliveira (ademar111190@gmail.com)
	 * @date Nov 27, 2013 2:21:45 PM
	 * @since {@link API#Version_3_0_0}
	 * 
	 */
	public interface CopiedInterface extends OnClickListener {

		/**
		 * @param clipboardTextView
		 *            the {@link ClipboardTextView} has copied
		 * @param stringCopied
		 *            the {@link String} with text copied
		 * @param hasSucces
		 *            <code>true</code> if the text is on ClipBoard, it only returns false if your api is less the 11
		 */
		public void textHasCopied(ClipboardTextView clipboardTextView, String stringCopied, boolean hasSucces);

	}

	private CopiedInterface delegate;

	/**
	 * @see TextView#TextView(Context, AttributeSet, int)
	 * @since {@link API#Version_3_0_0}
	 */
	public ClipboardTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * @see TextView#TextView(Context, AttributeSet)
	 * @since {@link API#Version_3_0_0}
	 */
	public ClipboardTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * @see TextView#TextView(Context)
	 * @since {@link API#Version_3_0_0}
	 */
	public ClipboardTextView(Context context) {
		super(context);
	}

	/**
	 * Copy the current text from {@link TextView#getText()} to clipbloard, need Android Api 11 if you have an api less then it, you can get the text on {@link CopiedInterface#textHasCopied(ClipboardTextView, String, boolean)} but not on clipboard
	 * 
	 * @since {@link API#Version_3_0_0}
	 */
	public void makeCopy() {
		if (android.os.Build.VERSION.SDK_INT >= 11) {
			ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
			ClipData clip = ClipData.newPlainText("label", getText());
			clipboard.setPrimaryClip(clip);
		}
		if (delegate != null)
			delegate.textHasCopied(this, getText().toString(), android.os.Build.VERSION.SDK_INT >= 11);
	}

	/**
	 * @param delegate
	 *            the {@link CopiedInterface} to get the callback
	 * @since {@link API#Version_3_0_0}
	 */
	public void setCopyListener(CopiedInterface delegate) {
		this.delegate = delegate;
		setOnClickListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		makeCopy();
		if (delegate != null)
			delegate.onClick(v);
	}

}
