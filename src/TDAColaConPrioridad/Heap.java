package TDAColaConPrioridad;

import java.util.Comparator;

import Excepciones.EmptyPriorityQueueException;
import Excepciones.InvalidKeyException;

public class Heap <K,V> implements PriorityQueue<K,V> {
	
	protected Entrada<K,V> [] elems;
	protected Comparator<K> comp;
	protected int size;
	private final float factor = 0.8f;
	
	
	public Heap(int maxElems, Comparator<K> comp) {
		this.elems = (Entrada<K,V>[]) new Entrada[maxElems];
		this.comp = comp;
		this.size = 0;
	}
	
	public int size() {
		return this.size;
	}
	
	public boolean isEmpty() {
		return this.size == 0;
	}
	
	public Entry<K,V> min() throws EmptyPriorityQueueException {
		if( this.isEmpty() ) {
			throw new EmptyPriorityQueueException("La cola est� vac�a.");
		} else {
			return this.elems[1];
		}
	}
	
	public Entry<K,V> insert(K key, V value) throws InvalidKeyException {
		Entrada<K,V> entrada = new Entrada<K,V>( key, value);
		elems[++size] = entrada;
		
		int i = size;
		boolean seEncontroUbicacionCorrecta = false;
		
		while( i > 1 && !seEncontroUbicacionCorrecta) {
			Entrada<K,V> elemActual = elems[i];
			Entrada<K,V> elemAncestro = elems[i/2];
			
			if( this.comp.compare(elemActual.getKey(), elemAncestro.getKey()) < 0) {
				//Intercambio posiciones
				Entrada<K,V> aux = elems[i]; //Entrada auxiliar para el intercambio
				elems[i] = elems[i/2];
				elems[i/2] = aux;
				
				i = i/2;
			} else {
				seEncontroUbicacionCorrecta = true;
			}
		}
		
		if( (i / this.elems.length) > this.factor ) {
			this.crecerArreglo();
		}
		
		return entrada;
	}
	
	public Entry<K,V> removeMin() throws EmptyPriorityQueueException {
		Entry<K,V> entrada = this.min();
		
		if( this.size == 1) {
			this.elems[1] = null;
			this.size = 0;
		} else {
			//Paso la ultima entrada a la raiz, borro al final del arreglo y decemento el tama�o
			this.elems[1] = this.elems[size]; 
			this.elems[size] = null;
			this.size--;
			
			int i = 1;
			boolean seEncontroUbicacionCorrecta = false;
			//En minimo se computara la posicion del minimo de los hijos de i
			int minimo = 1; 
			
			while( !seEncontroUbicacionCorrecta ) {
				int indexDescendienteIzquierdo = i*2;
				int indexDescendienteDerecho = i*2+1;
				boolean tieneDescendienteIzquierdo = indexDescendienteIzquierdo <= this.size;
				boolean tieneDescendienteDerecho = indexDescendienteDerecho <= this.size;
				
				if(!tieneDescendienteIzquierdo) {
					seEncontroUbicacionCorrecta = true;
				} else {
					
					if( tieneDescendienteDerecho ) {
						//Si el hijo izquierdo es menor al hijo derecho, entonces guardo en minimo el indice del hijo izquierdo
						if( comp.compare( this.elems[indexDescendienteIzquierdo].getKey(), elems[indexDescendienteDerecho].getKey()) < 0) {
							minimo = indexDescendienteIzquierdo;
						} else { //Caso contrario, guardo en minimo el indice del hijo derecho
							minimo = indexDescendienteDerecho;
						}
					//Caso contrario, ya que tiene hijo izquierdo y no hijo derecho, guardo en minimo el indice del hijo izquierdo
					} else {
						minimo = indexDescendienteIzquierdo;
					}
				}
				//Si el elemento actual es menor al minimo entre sus descendientes, entonces los intercambio
				if( comp.compare(elems[i].getKey(), elems[minimo].getKey()) > 0) {
					Entrada<K,V> aux = this.elems[i];
					this.elems[i] = this.elems[minimo];
					this.elems[minimo] = aux;
					
					i = minimo;
				} else {
					seEncontroUbicacionCorrecta = true;
				}
			}	
		}
		//Ya encontrada la ubicacion correcta, retorno la entrada
		return entrada;
	}
	
	public String toString() {
		String texto = "[ ";
		for( int i = 1; i <= size; i++) {
			if( i != size) {
				texto += " "+this.elems[i]+",";
			} else {
				texto += " "+this.elems[i]+"]";
			}
		}
		return texto;
	}
	
	private void crecerArreglo() {
		@SuppressWarnings("unchecked")
		Entrada<K,V> [] tmp = (Entrada<K,V>[]) new Entrada[elems.length+10];
		for( int i = 0; i < elems.length; i++ ) {
			tmp[i] = elems[i];
		}
		this.elems = tmp;
	}
	
}
