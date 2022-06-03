package TDAColaConPrioridad;

public class Entrada <K,V> implements Entry <K,V> {
	private K key;
	private V value;
	
	/**
	 * Constructor
	 * @param key clave de la entrada.
	 * @param value valor de la entrada.
	 */
	public Entrada(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	/**
	 * Consulta la clave de la entrada.
	 * @return clave de la entrada.
	 */
	public K getKey() {
		return this.key;
	}
	
	/**
	 * Consulta el valor de la entrada.
	 * @return valor de la entrada.
	 */
	public V getValue() {
		return this.value;
	}
	
	/**
	 * Actualiza la clave de la entrada.
	 * @param key Nueva clave de la entrada.
	 */
	public void setKey(K key) {
		this.key = key;
	}
	
	/**
	 * Actualiza el valor de la entrada.
	 * @param value Nueva valor de la entrada.
	 */
	public void setValue(V value) {
		this.value = value;
	}
	
	/**
	 * Devuelve una cadena de texto con la clave y valor de la entrada con un formato de par.
	 * @return cadena de texto de los atributos de la entrada.
	 */
	public String toString() {
		return "( "+this.getKey()+", "+this.getValue()+")";
	}
}
