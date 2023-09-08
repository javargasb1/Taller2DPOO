package uniandes.dpoo.taller2.procesamiento;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import uniandes.dpoo.taller2.modelo.combo;
import uniandes.dpoo.taller2.modelo.ingrediente;
import uniandes.dpoo.taller2.modelo.pedido;
import uniandes.dpoo.taller2.modelo.producto;
import uniandes.dpoo.taller2.modelo.productoMenu;

public class restaurante
{
	private ArrayList<ingrediente> ListaIngredientes = null;
	private ArrayList<productoMenu> ListaMenu = null;
	private ArrayList<combo> ListaCombos = null;
	private static pedido Elpedido = null;
	private static ArrayList<pedido> historialPedidos = new ArrayList<pedido>();
	
	
	public restaurante(ArrayList<ingrediente> ListaIngredientes,ArrayList<productoMenu> ListaMenu, ArrayList<combo> ListaCombos)
	{	
		this.ListaIngredientes = ListaIngredientes;
		this.ListaMenu = ListaMenu;
		this.ListaCombos = ListaCombos;
	}
	
	public void iniciarPedido(String nombreCliente, String direccionCliente)
	{
		ArrayList<producto> ListaProductos = new ArrayList<producto>();
		Elpedido = new pedido(nombreCliente,direccionCliente, ListaProductos);
	}
	public void cerrarYGuardarPedido() throws IOException
	{
		String nombreArchivo = "data/" + Integer.toString(Elpedido.getIdPedido()) + ".txt";
		File archivo = new File(nombreArchivo);
		Elpedido.guardarFactura(archivo);
		System.out.println("Factura guardada en: " + nombreArchivo);
		historialPedidos.add(Elpedido);
		Elpedido = null;
	}
	public pedido getPedidoEnCurso()
	{
		return Elpedido;
	}
	public pedido getPedidoID(int idPedido)
	{
		int sizeLista = historialPedidos.size();
		if (idPedido <= sizeLista)
		{
			return historialPedidos.get(idPedido - 1);
		}
		else
			return null;
	}
	public ArrayList<combo> getCombo()
	{
		return ListaCombos;
	}
	public ArrayList<productoMenu> getMenuBase()
	{
		return ListaMenu;
	}
	public ArrayList<ingrediente> getIngrediente()
	{
		return ListaIngredientes;
	}
	
}