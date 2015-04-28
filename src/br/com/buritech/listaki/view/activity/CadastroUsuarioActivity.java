package br.com.buritech.listaki.view.activity;

import br.com.buritech.listaki.view.validation.SaveSharedPreference;
import br.com.buritech.listaki.view.validation.Validation;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import br.com.buritech.listaki.core.view.activity.BaseActivity;
import br.com.buritech.listaki.model.dao.impl.UsuarioDAO;
import br.com.buritech.listaki.model.entidade.Usuario;

/*Activity responsável pelo formulário de cadastro de novo usuário. Não está sendo feita nenhuma Validação*/

@ContentView(R.layout.cadastro_usuario)

public class CadastroUsuarioActivity extends BaseActivity {
	
	@InjectView(R.id.edtNomeCadastro)
	private EditText edtNomeCadastro;
	
	@InjectView(R.id.edtEmailCadastro)
	private EditText edtEmailCadastro;
	
	@InjectView(R.id.edtSenhaCadastro)
	private EditText edtSenhaCadastro;
	
	@InjectView(R.id.edtConfirmaSenhaCadastro)
	private EditText edtConfirmaSenhaCadastro;
	
	@InjectView(R.id.btnSalvar)
	private Button btnSalvar;
	
//	@InjectView(R.id.btnCancelar)
//	private TextView btnCancelar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		
		
		// TODO Auto-generated method stub
		
		
		
		
		super.onCreate(savedInstanceState);

        edtNomeCadastro.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                                                 //Aqui é feita a primeira verificação de campo (se contém texto).
                       edtNomeCadastro.setError(null);                           //Caso tenha algum texto, o focus do edtNomeCadastro será limpo.


            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }


        });

        edtNomeCadastro.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!edtNomeCadastro.hasFocus()){
                    if(!Validation.hasText(edtNomeCadastro)) {

                        edtNomeCadastro.setError("Preencha corretamente.");

                    }
                }
            }
        });

        edtEmailCadastro.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!edtEmailCadastro.hasFocus()) {
                      if(!Validation.isEmailAddress(edtEmailCadastro, true)){
                                  edtEmailCadastro.setError("Email inválido!");                          //Aqui verifica-se se o campo edtEmailCadastro está selecionado (Possui focus).
                      }
                                                         //Aqui fazemos uma nova verificação pois a de cima às vezes não faz corretamente (bug desconhecido).

                }
            }
        });

        edtEmailCadastro.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) { //Enquanto o usuário estiver digitando não aparecerá erro algum.
                edtEmailCadastro.setError(null);

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        edtSenhaCadastro.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!edtSenhaCadastro.hasFocus()) {   //Aqui verifica-se se o campo edtSenhaCadastro possui está selecionado (Possui focus).
                      if(!Validation.hasText(edtSenhaCadastro)){

                            edtSenhaCadastro.setError("Preencha corretamente.");
                                                  //Validation.isEmailAddress(edtEmailCadastro, true);      //Aqui é feita a validação do EMAIL, pois o campo está sem focus, logo o erro não
                      }
                                                     //Incomodorá o usuário, mas irá informá-lo de algo está errado.
                }
            }
        });

        edtSenhaCadastro.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                edtSenhaCadastro.setError(null);

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        edtConfirmaSenhaCadastro.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Validation.hasText(edtConfirmaSenhaCadastro);


            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });


        edtConfirmaSenhaCadastro.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getKeyCode() == event.KEYCODE_ENTER) {
                    edtSenhaCadastro.clearFocus();                    //Aqui foi verificado se o botão ENTER foi pressionado
                    edtConfirmaSenhaCadastro.clearFocus();           //Caso tenha sido pressionado, o focus será limpo e
                    edtConfirmaSenhaCadastro.requestFocus();        //O método "setOnFocusChangelistener" abaixo irá verificar se as senhas conferem
                }                                                  // e o focus será mantido no edtConfirmaSenha.

                return false;
            }
        });

        edtConfirmaSenhaCadastro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edtConfirmaSenhaCadastro.setError(null);


            }
        });

        edtConfirmaSenhaCadastro.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!edtConfirmaSenhaCadastro.hasFocus()) {

                    if (!edtSenhaCadastro.getText().toString().equals(edtConfirmaSenhaCadastro.getText().toString())) {
                        edtConfirmaSenhaCadastro.setError("As senhas não conferem.");
                    } else {
                        edtConfirmaSenhaCadastro.setError(null);

                    }

                }
            }
        });


		
		
		btnSalvar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
                // TODO Auto-generated method stub


                Usuario usuario = new Usuario();
                UsuarioDAO dao = UsuarioDAO.getInstance();


                if (Validation.isEmailAddress(edtEmailCadastro, true) && Validation.hasText(edtNomeCadastro) && Validation
                        .hasText(edtSenhaCadastro) && Validation.hasText(edtConfirmaSenhaCadastro)) {
                    if (edtSenhaCadastro.getText().toString().equals(edtConfirmaSenhaCadastro.getText().toString())) {
                        usuario.setNome(edtNomeCadastro.getText().toString());
                        usuario.setEmail(edtEmailCadastro.getText().toString());
                        usuario.setSenha(edtSenhaCadastro.getText().toString());
                        if (!dao.consultaIgual(usuario.getEmail())) {
                            if (dao.createOrUpdate(usuario)) {//Passagem do objeto Usuario preenchido para ser salvo no bd.

                                SaveSharedPreference.setUserEmail(CadastroUsuarioActivity.this, edtEmailCadastro.getText().toString());
                                Log.i("ID: ", usuario.getId().toString());

                                Toast.makeText(CadastroUsuarioActivity.this, "SEJA BEM VINDO AO ListAki!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent();
                                intent.setClass(CadastroUsuarioActivity.this, ListaCarrinhoActivity.class);
                                startActivity(intent);
                                finish();


                            } else {

                                Toast.makeText(CadastroUsuarioActivity.this, "Não foi possível criar o usuário.", Toast.LENGTH_LONG).show();

                            }

                        } else {

                            Toast.makeText(CadastroUsuarioActivity.this, "Email já cadastrado!", Toast.LENGTH_LONG).show();

                        }


                    } else {

                        Toast.makeText(CadastroUsuarioActivity.this, "As senhas não conferem!", Toast.LENGTH_LONG).show();

                    }
                }else{
                    Toast.makeText(CadastroUsuarioActivity.this, "Preencha os campos corretamente!", Toast.LENGTH_LONG).show();

                }
            }
		});
		
		
//		btnCancelar.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				
//				finish();
//				
//			}
//		});
		
	}
	
}

