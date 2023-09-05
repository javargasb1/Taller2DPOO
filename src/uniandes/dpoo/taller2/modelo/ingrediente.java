package uniandes.dpoo.taller2.modelo;

public class ingrediente
{
	
	private String nombre;
	private int CostoAdicional;
	
	// Constructor 
	public ingrediente(String nombre,int CostoAdicional)
	{
		this.nombre = nombre;
		this.CostoAdicional = CostoAdicional;
	}
	public String getNombre()
	{
		return nombre;
	}
	public int getCostoAdicional()
	{
		return CostoAdicional;
	}
}