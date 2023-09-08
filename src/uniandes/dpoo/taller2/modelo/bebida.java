package uniandes.dpoo.taller2.modelo;

public class bebida implements producto
{
	private String nombre;
	private int precioBase;
	
	public bebida(String nombre, int precioBase)
	{
		this.nombre = nombre;
		this.precioBase = precioBase;
	}
	
	@Override
	public int getPrecio() 
	{
		return precioBase;
	}
	
	@Override
	public String getNombre() 
	{
		return nombre;
	}
	
	@Override
	public String generarTextoFactura() 
	{
		return (nombre + "   " + Integer.toString(precioBase) + "\n");
	}
}