
package uniandes.dpoo.taller2.modelo;

import java.util.ArrayList;

public class productoAjustado implements producto
{
	private String nombre;
	private int precioAjustado;

	public productoAjustado(productoMenu base,ArrayList<ingrediente> ListaIngredientesAgregados, ArrayList<ingrediente> ListaIngredientesEliminados) 
	{
		String nombreAjustado = base.getNombre();
		int precioAjustado = base.getPrecio();
		
		if (ListaIngredientesAgregados != null)
		{
			nombreAjustado = nombreAjustado + " extra ";
			for (ingrediente n_ingrediente:ListaIngredientesAgregados)
			{
				precioAjustado += n_ingrediente.getCostoAdicional();
				nombreAjustado = nombreAjustado + ", " + n_ingrediente.getNombre();
			}
		}
		
		if (ListaIngredientesEliminados != null)
		{
			nombreAjustado = nombreAjustado + " sin ";
			for (ingrediente n_ingrediente:ListaIngredientesEliminados)
			{
				nombreAjustado = nombreAjustado + ", "+ n_ingrediente.getNombre();
			}
		}
		
		this.nombre = nombreAjustado;
		this.precioAjustado = precioAjustado;
		
	}

	@Override
	public int getPrecio() 
	{
		return precioAjustado;
	}

	@Override
	public String getNombre() 
	{
		return nombre;
	}

	@Override
	public String generarTextoFactura() 
	{
		return (nombre + "   " + Integer.toString(precioAjustado)+ "\n");
	}
}