package example.android.app841;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class ActivityMain extends Activity {
private Spinner spnId;
    private TextView txvId;
    private EditText etxName,etxPrice;
    private Button btnInsert, btnUpdate, btnDelete;
    private List<DtoItem> listItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spnId = (Spinner) findViewById(R.id.amin_spnId);
        txvId = (TextView) findViewById(R.id.amin_txvId);
        etxName = (EditText) findViewById(R.id.amin_etxName);
        etxPrice = (EditText) findViewById(R.id.amin_etxPrice);
        btnInsert = (Button) findViewById(R.id.amin_btnInsert);
        btnUpdate = (Button) findViewById(R.id.amin_btnUpdate);
        btnDelete = (Button) findViewById(R.id.amin_btnDelete);

        listItem = DaoItem.findAll(getApplicationContext());

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.add(getText(R.string.amin_btnInsert).toString());
        for (DtoItem item : listItem) {
            adapter.add(String.valueOf(item.id));
        }
        spnId.setAdapter(adapter);
        spnId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    setButtonEnabled(true);
                    initData();
                } else {
                    setButtonEnabled(false);
                    txvId.setText(String.valueOf(listItem.get(position - 1).id));
                    etxName.setText(listItem.get(position - 1).name);
                    etxPrice.setText(String.valueOf((listItem.get(position - 1).price)));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DtoItem item = new DtoItem();
                item.name = etxName.getText().toString();
                if (etxPrice.getText().toString().length() == 0) {
                    item.price = 0;
                } else {
                    item.price = Long.valueOf(etxPrice.getText().toString());
                }
                item.id = DaoItem.insert(getApplicationContext(), item);
                initData();
                listItem.add(item);
                updateSpinner();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DtoItem item = new DtoItem();
                item.id = Long.valueOf(txvId.getText().toString());
                item.name = etxName.getText().toString();
                item.price = Long.valueOf(etxPrice.getText().toString());
                DaoItem.update(getApplicationContext(), item);
                listItem.set(spnId.getSelectedItemPosition() - 1, item);
                spnId.setSelection(0, true);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DaoItem.delete(getApplicationContext(), Long.valueOf(txvId.getText().toString()));

                listItem.remove(spnId.getSelectedItemPosition() - 1);
                updateSpinner();
                spnId.setSelection(0, true);
            }
        });
    }
    private void setButtonEnabled(boolean isInsert){
        btnInsert.setEnabled(isInsert);
        btnUpdate.setEnabled(!isInsert);
        btnDelete.setEnabled(!isInsert);
    }

    private void updateSpinner(){
        ArrayAdapter<String>adapter=(ArrayAdapter<String>)spnId.getAdapter();
        adapter.clear();
        adapter.add(getText(R.string.amin_btnInsert).toString());
        for(DtoItem item:listItem){
            adapter.add(String.valueOf(item.id));
        }
    }
    private void initData(){
        txvId.setText("");
        etxName.setText("");
        etxPrice.setText("0");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
