package TDAColaConPrioridad;

import java.util.Comparator;

import Excepciones.EmptyPriorityQueueException;
import Excepciones.InvalidKeyException;

public class TestOrdenarArregloCreciente {
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
		String texto = "[ ";
		for( int i = 0; i < cant; i++) {
			if( i != cant-1) {
				texto += " "+a[i]+",";
			} else {
				texto += " "+a[i]+"]";
			}
		}
		System.out.println(texto);
		PriorityQueue<Integer,Integer> cola = new Heap<Integer,Integer>(20, comp);
		
		for(int i = 0; i < cant; i++) {
			try {
				cola.insert(a[i], a[i]);
			} catch (InvalidKeyException e) {e.printStackTrace();}
		}
		System.out.println("Tamaño de la cola: "+cola.size());
		
		System.out.println("Size: "+cant);
		for(int i = 0; i < cant; i++) {
			try {
				a[i] = cola.removeMin().getValue();
			} catch (EmptyPriorityQueueException e) {e.printStackTrace();}
		}
		
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
