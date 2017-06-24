package estruturas;

public class ListaCircular<T> {
	public NodeDuplo<T> frente = null;
	public NodeDuplo<T> verso = null;
	public NodeDuplo<T> cursor = null;
	private int _tamanho = 0;
	
	public ListaCircular() {
		
	}
	
	public boolean estaVazia() {
		return tamanho() == 0;
	}
	
	public void inserir(T v) {
		NodeDuplo<T> novo = new NodeDuplo<T>(v);
		if (tamanho() == 0) {
			cursor = novo;
			frente = novo;
			frente.prev = novo;
			frente.next = novo;
		} else {
			verso.next = novo;
			novo.next = frente;
			novo.prev = verso;
		}
		verso = novo;
		_tamanho++;
	}
	
	public void inserir(T v, int indice) {
		if (tamanho() == 0 || indice >= tamanho()) {
			inserir(v);
		} else {
			NodeDuplo<T> novo = new NodeDuplo<T>(v);
			if(indice == 0){
				novo.prev = verso;
				novo.next = frente;
				frente.prev = novo;
				frente = novo;
			} else {
				NodeDuplo<T> atual = frente;
				for(int i = 1; i < indice; i++) {
					atual = atual.next;
				}
				novo.next = atual.next;
				novo.prev = atual;
				atual.next.prev = novo;
				atual.next = novo;
				
			}
			_tamanho++;
		}
	}
	
	public T retirar() {
		if (tamanho() == 0)
			throw new NullPointerException("Lista já está vazia");
		T retorno = frente.valor;
		
		frente = frente.next;
		frente.prev = verso;
		verso.next = frente;
		_tamanho--;
		if (tamanho() == 0) {
			verso = null;
			frente = null;
		}
		return retorno;
	}
	
	public T retirar(int indice) {
		if (indice == 0 )
			return retirar();
		if (indice >= tamanho())
			indice = tamanho()-1; //ou talvez lançar uma exceção?
		NodeDuplo<T> atual = frente;
		int i = 1;
		for(i = 1; i < indice; i++) {
			atual = atual.next;
		}
		NodeDuplo<T> retirado = atual.next;
		T retorno = retirado.valor;
		atual.next.next.prev = atual;
		atual.next = atual.next.next;
		if(i == tamanho()-1) {
			verso = verso.prev;
		}
		frente.prev = verso;
		verso.next = frente;
		_tamanho--;
		return retorno;
	}
	
	public T get(int indice) {
		NodeDuplo<T> atual = frente;
		for(int i = 0; i < indice; i++) {
			atual = atual.next;
		}
		return atual.valor;
	}
	
	public NodeDuplo<T> getCursor() {
		return cursor;
	}
	
	public T mostraAnterior() {
		NodeDuplo<T> ret = cursor.prev;
		return ret.valor;
	}
	
	public T mostraAtual() {
		return cursor.valor;
	}
	
	public T mostraProximo() {
		NodeDuplo<T> ret = cursor.next;
		return ret.valor;
	}	
	
	public void proximo() {
		cursor = cursor.next;
	}
	
	public int tamanho() {
		return _tamanho;
	}

	@Override
	public String toString() {
		String resp = "[";
		if (tamanho() > 0)
		{
			NodeDuplo<T> mostra = frente;
			for (int i = 0; i < tamanho()-1; i++) {
				resp += mostra.valor + ", ";
				mostra = mostra.next;
			}
			resp += mostra.valor;
		}
		return resp + "]";
	}
	
	public String cantos() {
		if(tamanho() == 0)
			return "[]";
		return "[" + frente.valor + ", " + verso.valor + "]";
	}
}
