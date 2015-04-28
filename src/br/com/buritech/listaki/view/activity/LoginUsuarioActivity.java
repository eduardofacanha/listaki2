package br.com.buritech.listaki.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import br.com.buritech.listaki.core.view.activity.BaseActivity;
import br.com.buritech.listaki.model.dao.impl.UsuarioDAO;
import br.com.buritech.listaki.view.validation.SaveSharedPreference;
import br.com.buritech.listaki.view.validation.Validation;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/*Activity responsável por logar o usuário na app. Não está sendo feita nenhuma validação*/

@ContentView(R.layout.login_usuario)
public class LoginUsuarioActivity extends BaseActivity {

    @InjectView(R.id.box_titulo)
    private ImageView boxTitulo;

    @InjectView(R.id.box_login)
    private LinearLayout boxLogin;

    @InjectView(R.id.edtEmail)
    private EditText edtEmail;

    @InjectView(R.id.edtSenha)
    private EditText edtSenha;

    @InjectView(R.id.btnEntrar)
    private Button btnEntrar;

    @InjectView(R.id.btnCadastrar)
    private Button btnCadastrar;

    public static Activity loginUsuarioActivity;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginUsuarioActivity = this;

        if(SaveSharedPreference.getUserEmail(LoginUsuarioActivity.this).length() == 0) {
            if (savedInstanceState == null) {
                boxLogin.setVisibility(View.INVISIBLE);
                btnCadastrar.setVisibility(View.INVISIBLE);

                Animation animTranslate = AnimationUtils.loadAnimation(LoginUsuarioActivity.this, R.anim.translate_login);
                boxTitulo.startAnimation(animTranslate);
                animTranslate.setAnimationListener(new AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        boxLogin.setVisibility(View.VISIBLE);
                        btnCadastrar.setVisibility(View.VISIBLE);
                        Animation animFade = AnimationUtils.loadAnimation(LoginUsuarioActivity.this, R.anim.fade_login);
                        boxLogin.startAnimation(animFade);
                    }
                });
            }

            edtEmail.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) { edtEmail.setError(null);   }
                public void beforeTextChanged(CharSequence s, int start, int count, int after){}
                public void onTextChanged(CharSequence s, int start, int before, int count){}


            });

            edtSenha.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(edtSenha.hasFocus()){
                        Validation.isEmailAddress(edtEmail, true);
                    }
                }
            });

            edtSenha.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) { edtSenha.setError(null); }
                public void beforeTextChanged(CharSequence s, int start, int count, int after){}
                public void onTextChanged(CharSequence s, int start, int before, int count){}


            });

            edtSenha.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!edtSenha.hasFocus()){
                        if(!Validation.hasText(edtSenha)) {

                            edtSenha.setError("Preencha corretamente.");

                        }
                    }
                }
            });

            btnEntrar.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    UsuarioDAO dao = UsuarioDAO.getInstance();
                    if (Validation.isEmailAddress(edtEmail, true) && Validation.hasText(edtSenha)) {
                        if (dao.ConsultaSenha(edtEmail.getText().toString(), edtSenha
                                .getText().toString())) { // Faz a consulta no banco.

                            SaveSharedPreference.setUserEmail(LoginUsuarioActivity.this, edtEmail.getText().toString());

                            Intent intent = new Intent();
                            intent.setClass(LoginUsuarioActivity.this,
                                    ListaCarrinhoActivity.class);
                            startActivity(intent);
                            finish();

                        } else {

                            Toast.makeText(LoginUsuarioActivity.this,
                                    "Email e/ou senha inválidos", Toast.LENGTH_SHORT)
                                    .show();

                        }

                    }else{
                        Toast.makeText(LoginUsuarioActivity.this, "Preencha os campos corretamente!", Toast.LENGTH_LONG ).show();
                    }
                }
            });

            btnCadastrar.setOnClickListener(new OnClickListener() { // Chama a
                // Activity de
                // cadastro
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    Intent intent = new Intent();
                    intent.setClass(LoginUsuarioActivity.this,
                            CadastroUsuarioActivity.class);
                    startActivity(intent);


                }
            });

        }else{

            Intent intent = new Intent();
            intent.setClass(LoginUsuarioActivity.this,
                    ListaCarrinhoActivity.class);
            startActivity(intent);
            finish();


        }

    }

    private boolean checkValidation() { boolean ret = true; //Método responsável pela validação.

        if (!Validation.hasText(edtSenha)) ret = false; if
                (!Validation.isEmailAddress(edtEmail, true)) ret = false;

        return ret; }
}
