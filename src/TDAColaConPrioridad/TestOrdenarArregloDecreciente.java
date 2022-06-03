package TDAColaConPrioridad;

import java.util.Comparator;

import Excepciones.EmptyPriorityQueueException;
import Excepciones.InvalidKeyException;

public class TestOrdenarArregloDecreciente {
	public static void main (String [] args) {
		Comparator<Integer> comp = new DefaultComparator<Integer>();
		int [] a = new int[10];
		int cant = 0;
		a[cant++] = 6;
		a[cant++] = 4;
		a[cant++] = 9;
		a[cant++] = 1;
		a[cant++] = 2;
		a[cant++] = 5;
		a[cant++] = 3;
		a[cant++] = 0;
		a[cant++] = 7;
		a[cant++] = 8;
		System.out.println("Arreglo sin ordenar:");
		//Escribo el arreglo con el formato de arreglos
		String texto = "[ ";
		for( int i = 0; i < cant; i++) {
			if( i != cant-1) {
				texto += " "+a[i]+",";
			} else {
				texto += " "+a[i]+"]";
			}
		}
		System.out.println(texto);
		System.out.println();
		
		MaxHeap<Integer,Integer> cola = new MaxHeap<Integer,Integer>(20, comp);
		//Inserto cada elemento del arreglo al la cola con prioridad maxima
		for(int i = 0; i < cant; i++) {
			try {
				cola.insert(a[i], a[i]);
			} catch (InvalidKeyException e) {e.printStackTrace();}
		}
		//Datos de la cola
		System.out.println("Tamaño de la cola: "+cola.size());
		System.out.println();

		//Actualizo los valores del arreglo con un orden descendiente
		for(int i = 0; i < cant; i++) {
			try {
				a[i] = cola.removeMax().getValue();
			} catch (EmptyPriorityQueueException e) {e.printStackTrace();}
		}
		
		System.out.println("Arreglo luego de utilizar la cola:");
		//Escribo el arreglo con el formato de arreglos
		texto = "[ ";
		for( int i = 0; i < cant; i++) {
			if( i != cant-1) {
				texto += " "+a[i]+",";
			} else {
				texto += " "+a[i]+"]";
			}
		}
		System.out.println(texto);
	}
}
