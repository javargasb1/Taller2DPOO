package uniandes.dpoo.taller2.procesamiento;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import uniandes.dpoo.taller2.modelo.bebida;
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
	private ArrayList<bebida> ListaBebidas = null;
	private static pedido Elpedido = null;
	private static ArrayList<pedido> historialPedidos = new ArrayList<pedido>();
	
	
	public restaurante(ArrayList<ingrediente> ListaIngredientes,ArrayList<productoMenu> ListaMenu, ArrayList<combo> ListaCombos,ArrayList<bebida> ListaBebidas)
	{	
		this.ListaIngredientes = ListaIngredientes;
		this.ListaMenu = ListaMenu;
		this.ListaCombos = ListaCombos;
		this.ListaBebidas = ListaBebidas;
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
		// verificar si alguien había hecho un pedido idéntico.
		int i = 0;
		for (pedido n_pedido:historialPedidos)
		{
			ArrayList<producto> Listan_pedido = n_pedido.getListaProductos();
			ArrayList<producto> Lista_Elpedido = Elpedido.getListaProductos();
			int igualdad = 0;
			if (Listan_pedido.size() == Lista_Elpedido.size())
			{
				int n = 0;
				while (n < Listan_pedido.size())
				{
					//Comparacion de dos objetos con equals
					producto elemento1 = Listan_pedido.get(n);
					producto elemento2 = Lista_Elpedido.get(n);
					String nombre1 = elemento1.getNombre();
					String nombre2 = elemento2.getNombre();
					if (nombre1.equals(nombre2))
					{
						igualdad++;
					}
					n++;
				}
			}
			
			if (igualdad == Lista_Elpedido.size())
			{
				System.out.println("Ya existe un pedido identico");
				System.out.println("Pedido anterior:\n");
				System.out.println(n_pedido.generarTextoFactura() + "\n");
				System.out.println("Pedido actual:\n");
				System.out.println(Elpedido.generarTextoFactura() + "\n");
				i = 1;
			}
		}
		if (i == 0)
			System.out.println("NO existe un pedido identico");
		
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
	public ArrayList<bebida> getBebidas()
	{
		return ListaBebidas;
	}
	public ArrayList<ingrediente> getIngrediente()
	{
		return ListaIngredientes;
	}
	
}