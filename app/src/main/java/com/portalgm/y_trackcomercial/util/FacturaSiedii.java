

    import android.content.Intent;
        import android.net.Uri;
        import android.os.Bundle;
        import android.os.Handler;
        import android.os.Looper;
        import android.util.Log;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.core.app.ActivityCompat;
        import org.jetbrains.annotations.Nullable;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.io.OutputStream;
        import java.io.Reader;

public class FacturaSiedii extends AppCompatActivity {
    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler(Looper.getMainLooper());
    }
    /**La funcion initInput() es la que ejecuta el proceso
     * */
    public void initInput() {
        mHandler.post(() -> Log.i("MensajeYtrack", "Solicitando datos ..."));
        String paquete = getPackageName();
        // PASO 1. SOLICITAR LA CREACION DE UN NUEVO ARCHIVO.
        Intent intent = new Intent();
        intent.setAction("py.com.sepsa.siediapp.intent.action.CREAR_ARCHIVO");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.putExtra("packageName", paquete);
        ActivityCompat.startActivityForResult(this, intent, 0x200, new Bundle());
    }

    /**
     * Realiza la llamada al intent FIRMAR_DTE
     *
     * @param target URI del archivo con el contenido TXT
     */
    private void initFirmar(Uri target) {
        // Intent para compartir.
        Intent intent = new Intent();
        intent.setAction("py.com.sepsa.siediapp.intent.action.FIRMAR_DTE");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(target, "text/csv");
        ActivityCompat.startActivityForResult(this, intent, 0x100, new Bundle());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // PASO 2. RELLENAR DATOS EN ARCHIVO GENERADO.
        if (requestCode == 0x200) {
            if (data == null) {
                mHandler.post(() -> Log.i("MensajeYtrack", "Error: NO DATA"));
                return;
            }

            Uri target = data.getData();
            if (target == null) {
                mHandler.post(() -> Log.i("MensajeYtrack", "Error: NO URI"));
                return;
            }

            // PASO 3: RELLENAR EL ARCHIVO.
            fillInput(target);

            // PASO 4. SOLICITAR PROCESAMIENTO DE DOCUMENTO ELECTRONICO.
            initFirmar(target);
            return;
        }

        // PASO 5. LEER DATOS LUEGO DE PROCESAMIENTO DE DOCUMENTO ELECTRONICO.
        if (requestCode == 0x100) {
            if (data == null) {
                mHandler.post(() -> Log.i("MensajeYtrack", "Error: NO DATA"));
                return;
            }

            if (data.getStringExtra("errors") != null) {
                mHandler.post(() -> Log.i("MensajeYtrack", data.getStringExtra("errors")));
                return;
            }

            if (data.getData() != null) {
                try (InputStream is = getContentResolver().openInputStream(data.getData())) {
                    Log.i("payload", data.getStringExtra("payload").toString());
                    char[] buffer = new char[4096];
                    StringBuilder out = new StringBuilder();
                    Reader in = new InputStreamReader(is);
                    for (int numRead; (numRead = in.read(buffer, 0, buffer.length)) > 0; ) {
                        out.append(buffer, 0, numRead);
                    }
                    mHandler.post(() -> Log.i("MensajeYtrack", out.toString()));

                } catch (Throwable thr) {
                    Log.e("test", "error", thr);
                    mHandler.post(() -> Log.i("MensajeYtrack", thr.getMessage()));
                }
            }
        }
    }

    /**
     * Realiza la escritura del archivo TXT en la URI
     *
     * @param target URI del archivo
     */
    private void fillInput(Uri target) {
        mHandler.post(() -> Log.i("MensajeYtrack", "Creando datos ..."));

        // Escribir datos.
        try (OutputStream os = getContentResolver().openOutputStream(target)) {
            try (InputStream is = getAssets().open("file.txt")) {
                byte[] buffer = new byte[4096];
                int count;
                while ((count = is.read(buffer)) != -1) {
                    os.write(buffer, 0, count);
                }
            }
        } catch (Throwable thr) {
            Log.e("test", "Error", thr);
            mHandler.post(() -> Log.i("MensajeYtrack", thr.getMessage()));
        }

        // Leer datoS (solo
        try {
            String data = read(target);
            mHandler.post(() -> Log.i("MensajeYtrack", data));
        } catch (Throwable thr) {
            Log.e("test", "ERROR", thr);
            mHandler.post(() -> Log.i("MensajeYtrack", thr.getMessage()));
        }
    }

    /**
     * Obtiene el contenido del URI en string
     *
     * @param target URI del archivo
     * @return Contenido del archivo en String
     * @throws IOException
     */
    private String read(Uri target) throws IOException {
        try (InputStream is = getContentResolver().openInputStream(target)) {
            char[] buffer = new char[4096];
            StringBuilder out = new StringBuilder();
            Reader in = new InputStreamReader(is);
            for (int numRead; (numRead = in.read(buffer, 0, buffer.length)) > 0; ) {
                out.append(buffer, 0, numRead);
            }
            return out.toString();
        }
    }

}
