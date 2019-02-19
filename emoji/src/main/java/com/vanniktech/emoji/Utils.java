package com.vanniktech.emoji;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import com.vanniktech.emoji.emoji.Emoji;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;

final class Utils {
  static final String TAG = "Utils";

  static final int DONT_UPDATE_FLAG = -1;

  @NonNull static <T> T checkNotNull(@Nullable final T reference, final String message) {
    if (reference == null) {
      throw new IllegalArgumentException(message);
    }

    return reference;
  }

  static int dpToPx(@NonNull final Context context, final float dp) {
    return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
        context.getResources().getDisplayMetrics()) + 0.5f);
  }

  static int getOrientation(final Context context) {
    return context.getResources().getConfiguration().orientation;
  }

  static boolean shouldOverrideRegularCondition(@NonNull final Context context, final EditText editText) {
    if ((editText.getImeOptions() & EditorInfo.IME_FLAG_NO_EXTRACT_UI) == 0) {
      return getOrientation(context) == Configuration.ORIENTATION_LANDSCAPE;
    }

    return false;
  }

  @SuppressWarnings({"unchecked", "JavaReflectionMemberAccess"}) static int getInputMethodHeight(final Context context, final View rootView) {
    try {
      final InputMethodManager imm = (InputMethodManager) context.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
      final Class inputMethodManagerClass = imm.getClass();
      final Method visibleHeightMethod = inputMethodManagerClass.getDeclaredMethod("getInputMethodWindowVisibleHeight");
      visibleHeightMethod.setAccessible(true);
      return (int) visibleHeightMethod.invoke(imm);
    } catch (NoSuchMethodException exception) {
      Log.w(TAG, exception.getLocalizedMessage());
    } catch (IllegalAccessException exception) {
      Log.w(TAG, exception.getLocalizedMessage());
    } catch (InvocationTargetException exception) {
      Log.w(TAG, exception.getLocalizedMessage());
    }

    return alternativeInputMethodHeight(rootView);
  }

  @SuppressWarnings("JavaReflectionMemberAccess") @TargetApi(LOLLIPOP) static int getViewBottomInset(final View rootView) {
    try {
      final Field attachInfoField = View.class.getDeclaredField("mAttachInfo");
      attachInfoField.setAccessible(true);
      final Object attachInfo = attachInfoField.get(rootView);
      if (attachInfo != null) {
        final Field stableInsetsField = attachInfo.getClass().getDeclaredField("mStableInsets");
        stableInsetsField.setAccessible(true);
        return ((Rect) stableInsetsField.get(attachInfo)).bottom;
      }
    } catch (NoSuchFieldException noSuchFieldException) {
      Log.w(TAG, noSuchFieldException.getLocalizedMessage());
    } catch (IllegalAccessException illegalAccessException) {
      Log.w(TAG, illegalAccessException.getLocalizedMessage());
    }
    return 0;
  }

  static int alternativeInputMethodHeight(final View rootView) {
    int viewInset = 0;
    if (SDK_INT >= LOLLIPOP) {
      viewInset = getViewBottomInset(rootView);
    }

    final Rect rect = new Rect();
    rootView.getWindowVisibleDisplayFrame(rect);

    final int availableHeight = rootView.getHeight() - viewInset - rect.top;
    return availableHeight - (rect.bottom - rect.top);
  }

  static int getScreenWidth(@NonNull final Activity context) {
    return dpToPx(context, context.getResources().getConfiguration().screenWidthDp);
  }

  @NonNull static Point locationOnScreen(@NonNull final View view) {
    final int[] location = new int[2];
    view.getLocationOnScreen(location);
    return new Point(location[0], location[1]);
  }

  @NonNull static Rect windowVisibleDisplayFrame(@NonNull final Activity context) {
    final Rect result = new Rect();
    context.getWindow().getDecorView().getWindowVisibleDisplayFrame(result);
    return result;
  }

  static void backspace(@NonNull final EditText editText) {
    final KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
    editText.dispatchKeyEvent(event);
  }

  static List<Emoji> asListWithoutDuplicates(final Emoji[] emojis) {
    final List<Emoji> result = new ArrayList<>(emojis.length);

    for (final Emoji emoji : emojis) {
      if (!emoji.isDuplicate()) {
        result.add(emoji);
      }
    }

    return result;
  }

  static void input(@NonNull final EditText editText, @Nullable final Emoji emoji) {
    if (emoji != null) {
      final int start = editText.getSelectionStart();
      final int end = editText.getSelectionEnd();

      if (start < 0) {
        editText.append(emoji.getUnicode());
      } else {
        editText.getText().replace(Math.min(start, end), Math.max(start, end), emoji.getUnicode(), 0, emoji.getUnicode().length());
      }
    }
  }

  static Activity asActivity(@NonNull final Context context) {
    Context result = context;

    while (result instanceof ContextWrapper) {
      if (result instanceof Activity) {
        return (Activity) result;
      }

      result = ((ContextWrapper) result).getBaseContext();
    }

    throw new IllegalArgumentException("The passed Context is not an Activity.");
  }

  static void fixPopupLocation(@NonNull final PopupWindow popupWindow, @NonNull final Point desiredLocation) {
    popupWindow.getContentView().post(new Runnable() {
      @Override public void run() {
        final Point actualLocation = locationOnScreen(popupWindow.getContentView());

        if (!(actualLocation.x == desiredLocation.x && actualLocation.y == desiredLocation.y)) {
          final int differenceX = actualLocation.x - desiredLocation.x;
          final int differenceY = actualLocation.y - desiredLocation.y;

          final int fixedOffsetX;
          final int fixedOffsetY;

          if (actualLocation.x > desiredLocation.x) {
            fixedOffsetX = desiredLocation.x - differenceX;
          } else {
            fixedOffsetX = desiredLocation.x + differenceX;
          }

          if (actualLocation.y > desiredLocation.y) {
            fixedOffsetY = desiredLocation.y - differenceY;
          } else {
            fixedOffsetY = desiredLocation.y + differenceY;
          }

          popupWindow.update(fixedOffsetX, fixedOffsetY, DONT_UPDATE_FLAG, DONT_UPDATE_FLAG);
        }
      }
    });
  }

  @ColorInt static int resolveColor(final Context context, @AttrRes final int resource, @ColorRes final int fallback) {
    final TypedValue value = new TypedValue();
    context.getTheme().resolveAttribute(resource, value, true);
    final int resolvedColor;

    if (value.resourceId != 0) {
      resolvedColor = ContextCompat.getColor(context, value.resourceId);
    } else {
      resolvedColor = value.data;
    }

    if (resolvedColor != 0) {
      return resolvedColor;
    } else {
      return ContextCompat.getColor(context, fallback);
    }
  }

  private Utils() {
    throw new AssertionError("No instances.");
  }
}
