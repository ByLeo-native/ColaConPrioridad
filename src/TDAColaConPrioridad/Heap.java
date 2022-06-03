package TDAColaConPrioridad;

import java.util.Comparator;

import Excepciones.EmptyPriorityQueueException;
import Excepciones.InvalidKeyException;

public class Heap <K,V> implements PriorityQueue<K,V> {
	
	protected Entrada<K,V> [] elems;
	protected Comparator<K> comp;
	protected int size;
	private final float factor = 0.7f;
	
	/**
	 * Constructor que cuenta con un limite de entradas.
	 * @param maxElems Cantidad maxima de entradas que dispondra la cola.
	 * @param comp Comparador que se utilizara para comparar entre las claves de las entradas.
	 */
	public Heap(int maxElems, Comparator<K> comp) {
		this.elems = (Entrada<K,V>[]) new Entrada[maxElems];
		this.comp = comp;
		this.size = 0;
	}
	
	public Heap(Comparator<K> comp) {
		this(20, comp);
	}
	
	/**
	 * Devuelve la cantidad de entradas almacenadas.
	 * @return cantidad de entradas.
	 */
	public int size() {
		return this.size;
	}
	
	/**
	 * Consulta si la cola esta vacia.
	 * @return Verdadero si no hay elementos almacenados y falso en caso contrario.
	 */
	public boolean isEmpty() {
		return this.size == 0;
	}
	
	/**
	 * Devuelve la entrada con menor prioridad.
	 * @return entrada con la clave de menor prioridad de la cola.
	 */
	public Entry<K,V> min() throws EmptyPriorityQueueException {
		if( this.isEmpty() ) {
			throw new EmptyPriorityQueueException("La cola está vacía.");
		} else {
			return this.elems[1];
		}
		
	}
	
	/**
	 * Inserta una entrada en la cola y la devuelve.
	 * @param k Clave de la entrada a insertar.
	 * @param value Valor de la entrada a insertar.
	 * @return entrada insertada en la cola.
	 */
	public Entry<K,V> insert(K key, V value) throws InvalidKeyException {
		this.checkKey(key);
		
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
		
		if( this.size == (this.elems.length - 1) ) {
			this.crecerArreglo();
		}
		
		return entrada;
	}
	
	/**
	 * Remueve y devuelve la entrada con menor prioridad.
	 * @return entrada de menor prioridad.
	 * @exception EmptyPriorityQueueException si la cola esta vacía.
	 */
	public Entry<K,V> removeMin() throws EmptyPriorityQueueException {
		Entry<K,V> entrada = this.min();
		
		if( this.size == 1) {
			this.elems[1] = null;
			this.size = 0;
		} else {
			//Paso la ultima entrada a la raiz, borro al final del arreglo y decemento el tamaño
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
	
	/**
	 * Transcribe las entradas almacenadas en un formato de arreglo.
	 * @return cadena de texto con las entradas almacenadas en forma de preOrden.
	 */
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
	
	/**
	 * Incrementa el tamaño del arreglo de entradas.
	 */
	private void crecerArreglo() {
		@SuppressWarnings("unchecked")
		Entrada<K,V> [] tmp = (Entrada<K,V>[]) new Entrada[elems.length+10];
		for( int i = 0; i < (this.size+1); i++ ) {
			tmp[i] = elems[i];
		}
		this.elems = tmp;
	}
	
	private void checkKey(K key) throws InvalidKeyException {
		if(key == null) {
			throw new InvalidKeyException("Clave invalida");
		}
	}
}
