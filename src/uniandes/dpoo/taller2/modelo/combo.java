package uniandes.dpoo.taller2.modelo;

import java.util.ArrayList;

public class combo implements producto
{
	// ************************************************************************
	// Atributos
	// ************************************************************************
	
	private double descuento;
	private String nombreCombo;
	private ArrayList<productoMenu> ListaProductos;
	
	public combo(String nombre, double descuento,ArrayList<productoMenu> ListaProductos)
	{
		this.nombreCombo = nombre;
		this.descuento = descuento;
		this.ListaProductos = ListaProductos;
	}
	
	public void agregarItemACombo(productoMenu itemCombo)
	{
		ListaProductos.add(itemCombo);
	}
	
	public int getPrecio()
	{
		int precio = 0;
		int precioNeto = 0;
		for (producto n_producto: ListaProductos)
		{
			int precioProducto = n_producto.getPrecio();
					precioNeto += precioProducto;
		}
		
		precio = precioNeto - (int)(precioNeto*descuento);
				
		return precio;
	}
	public String generarTextoFactura()
	{
		String textoCombo = nombreCombo + "   " + Integer.toString(getPrecio()) + "\n";
		return textoCombo;
	}
	public String getNombre()
	{
		return nombreCombo;
	}
}