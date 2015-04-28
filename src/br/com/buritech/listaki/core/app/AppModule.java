package br.com.buritech.listaki.core.app;

import br.com.buritech.listaki.model.bo.ProdutoBO;
import br.com.buritech.listaki.model.bo.SessaoO;
import br.com.buritech.listaki.model.bo.impl.ProdutoBOImpl;
import br.com.buritech.listaki.model.bo.impl.SessaoBOImpl;

import com.google.inject.Binder;
import com.google.inject.Module;

/**
 * Classe para configurar outros objetos a serem injetados
 * @author adrianaizel
 *
 */
public class AppModule implements Module {

	@Override
	public void configure(Binder binder) {
		
		//define a interface (bind) e a classe a ser injetada (to)
		binder.bind(SessaoO.class).to(SessaoBOImpl.class).asEagerSingleton();
		binder.bind(ProdutoBO.class).to(ProdutoBOImpl.class);
	}

}
