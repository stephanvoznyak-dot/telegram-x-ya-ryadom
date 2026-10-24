/*
 * Telegram X + «Я рядом»
 * Форма создания канала-объявления (YR1 + ChatLocation).
 */
package org.thunderdog.challegram.ui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import org.thunderdog.challegram.R;
import org.thunderdog.challegram.navigation.BackHeaderButton;
import org.thunderdog.challegram.navigation.ViewController;
import org.thunderdog.challegram.telegram.Tdlib;
import org.thunderdog.challegram.theme.ColorId;
import org.thunderdog.challegram.theme.Theme;
import org.thunderdog.challegram.tool.Screen;
import org.thunderdog.challegram.tool.UI;

import me.vkryl.android.widget.FrameLayoutFix;
import me.vkryl.core.StringUtils;

public class YaRyadomCreateController extends ViewController<Void> {

  private EditText titleInput, priceInput, roomsInput, areaInput, addressInput, descInput, latInput, lngInput;
  private TextView publishBtn;
  private boolean publishing;

  public YaRyadomCreateController (Context context, Tdlib tdlib) {
    super(context, tdlib);
  }

  @Override public int getId () { return R.id.controller_yaryadom; }
  @Override protected int getBackButton () { return BackHeaderButton.TYPE_BACK; }
  @Override public CharSequence getName () { return "Разместить"; }

  @Override
  protected View onCreateView (Context context) {
    LinearLayout root = new LinearLayout(context);
    root.setOrientation(LinearLayout.VERTICAL);
    root.setLayoutParams(FrameLayoutFix.newParams(
      ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    root.setBackgroundColor(Theme.backgroundColor());

    ScrollView scroll = new ScrollView(context);
    scroll.setLayoutParams(new LinearLayout.LayoutParams(
      ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f));

    LinearLayout form = new LinearLayout(context);
    form.setOrientation(LinearLayout.VERTICAL);
    int pad = Screen.dp(16f);
    form.setPadding(pad, pad, pad, pad);

    titleInput = addField(form, "Название *", "Квартира у метро");
    priceInput = addField(form, "Цена (₽/мес)", "35000");
    roomsInput = addField(form, "Комнат", "2");
    areaInput = addField(form, "Площадь (м²)", "54");
    addressInput = addField(form, "Адрес", "ул. Примерная, 1");
    descInput = addField(form, "Описание", "Светлая квартира, рядом парк");
    descInput.setMinLines(3);
    descInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);

    TextView geoLabel = new TextView(context);
    geoLabel.setText("Координаты (или оставьте пустым — возьмём GPS)");
    geoLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13f);
    geoLabel.setTextColor(Theme.getColor(ColorId.textLight));
    geoLabel.setPadding(0, Screen.dp(12f), 0, Screen.dp(4f));
    form.addView(geoLabel);

    LinearLayout coords = new LinearLayout(context);
    coords.setOrientation(LinearLayout.HORIZONTAL);
    latInput = makeCoord(context, "Широта");
    lngInput = makeCoord(context, "Долгота");
    coords.addView(latInput, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
    LinearLayout.LayoutParams lngLp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
    lngLp.leftMargin = Screen.dp(8f);
    coords.addView(lngInput, lngLp);
    form.addView(coords);

    prefillGps();

    scroll.addView(form);
    root.addView(scroll);

    publishBtn = new TextView(context);
    publishBtn.setText("Опубликовать");
    publishBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
    publishBtn.setTypeface(Typeface.DEFAULT_BOLD);
    publishBtn.setGravity(Gravity.CENTER);
    publishBtn.setTextColor(0xFFFFFFFF);
    publishBtn.setPadding(pad, Screen.dp(14f), pad, Screen.dp(14f));
    GradientDrawable bg = new GradientDrawable();
    bg.setCornerRadius(Screen.dp(12f));
    bg.setColor(0xFF0088CC);
    publishBtn.setBackground(bg);
    LinearLayout.LayoutParams btnLp = new LinearLayout.LayoutParams(
      ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    btnLp.setMargins(pad, pad, pad, pad);
    root.addView(publishBtn, btnLp);

    publishBtn.setOnClickListener(v -> publish());
    return root;
  }

  private EditText addField (LinearLayout form, String label, String hint) {
    TextView l = new TextView(context);
    l.setText(label);
    l.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13f);
    l.setTextColor(Theme.getColor(ColorId.textLight));
    l.setPadding(0, Screen.dp(10f), 0, Screen.dp(4f));
    form.addView(l);

    EditText e = new EditText(context);
    e.setHint(hint);
    e.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);
    e.setTextColor(Theme.getColor(ColorId.text));
    e.setHintTextColor(Theme.getColor(ColorId.textLight));
    e.setBackground(makeInputBg());
    e.setPadding(Screen.dp(12f), Screen.dp(10f), Screen.dp(12f), Screen.dp(10f));
    form.addView(e);
    return e;
  }

  private EditText makeCoord (Context context, String hint) {
    EditText e = new EditText(context);
    e.setHint(hint);
    e.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
    e.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f);
    e.setTextColor(Theme.getColor(ColorId.text));
    e.setHintTextColor(Theme.getColor(ColorId.textLight));
    e.setBackground(makeInputBg());
    e.setPadding(Screen.dp(10f), Screen.dp(10f), Screen.dp(10f), Screen.dp(10f));
    return e;
  }

  private GradientDrawable makeInputBg () {
    GradientDrawable bg = new GradientDrawable();
    bg.setCornerRadius(Screen.dp(10f));
    bg.setColor(Theme.getColor(ColorId.filling));
    return bg;
  }

  private void prefillGps () {
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
      && ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      return;
    }
    try {
      LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
      Location loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
      if (loc == null) loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
      if (loc != null) {
        latInput.setText(String.format(java.util.Locale.US, "%.6f", loc.getLatitude()));
        lngInput.setText(String.format(java.util.Locale.US, "%.6f", loc.getLongitude()));
      }
    } catch (Exception ignored) {}
  }

  private void publish () {
    if (publishing) return;
    String title = titleInput.getText().toString().trim();
    if (StringUtils.isEmpty(title)) {
      UI.showToast("Укажите название", Toast.LENGTH_SHORT);
      return;
    }

    YaRyadomFormat.ListingData d = new YaRyadomFormat.ListingData();
    d.title = title;
    d.price = priceInput.getText().toString().trim();
    d.rooms = roomsInput.getText().toString().trim();
    d.area = areaInput.getText().toString().trim();
    d.address = addressInput.getText().toString().trim();
    d.description = descInput.getText().toString().trim();

    try {
      String latS = latInput.getText().toString().trim();
      String lngS = lngInput.getText().toString().trim();
      if (!latS.isEmpty()) d.lat = Double.parseDouble(latS);
      if (!lngS.isEmpty()) d.lng = Double.parseDouble(lngS);
    } catch (Exception e) {
      UI.showToast("Некорректные координаты", Toast.LENGTH_SHORT);
      return;
    }

    publishing = true;
    publishBtn.setText("Публикация…");
    publishBtn.setEnabled(false);

    YaRyadomGeo.createListing(tdlib, d, chatId -> {
      publishing = false;
      publishBtn.setText("Опубликовать");
      publishBtn.setEnabled(true);
      if (chatId > 0) {
        UI.showToast("Объявление опубликовано", Toast.LENGTH_SHORT);
        navigateBack();
      }
    }, err -> {
      publishing = false;
      publishBtn.setText("Опубликовать");
      publishBtn.setEnabled(true);
      UI.showToast(err != null ? err : "Ошибка публикации", Toast.LENGTH_LONG);
    });
  }

  private TextView makeBtn (String text) {
    TextView b = new TextView(context);
    b.setText(text);
    b.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f);
    b.setTypeface(Typeface.DEFAULT_BOLD);
    b.setGravity(Gravity.CENTER);
    b.setPadding(Screen.dp(12f), Screen.dp(10f), Screen.dp(12f), Screen.dp(10f));
    GradientDrawable bg = new GradientDrawable();
    bg.setCornerRadius(Screen.dp(10f));
    bg.setColor(Theme.getColor(ColorId.filling));
    b.setBackground(bg);
    return b;
  }
}
