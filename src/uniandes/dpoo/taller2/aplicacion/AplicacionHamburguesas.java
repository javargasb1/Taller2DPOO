package uniandes.dpoo.taller2.aplicacion;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import uniandes.dpoo.taller2.modelo.combo;
import uniandes.dpoo.taller2.modelo.ingrediente;
import uniandes.dpoo.taller2.modelo.pedido;
import uniandes.dpoo.taller2.modelo.producto;
import uniandes.dpoo.taller2.modelo.productoAjustado;
import uniandes.dpoo.taller2.modelo.productoMenu;
import uniandes.dpoo.taller2.procesamiento.loaderRestaurante;
import uniandes.dpoo.taller2.procesamiento.restaurante;

public class AplicacionHamburguesas
{
	
	private static restaurante res = null;
	
	public void ejecutarAplicacion() throws IOException
	{
		System.out.println("Bienvenido a Restaurante Pepito\n");
		boolean continuar = true;
		while (continuar)
		{
			try
			{
				MostrarMenu();
				int opcion_seleccionada = Integer.parseInt(input("Por favor seleccione una opción"));
				if (opcion_seleccionada == 1)
					ejecutarIniciarAplicacion();
				else if (opcion_seleccionada == 2 && res != null)
					ejecutarAgregarElementoPedido();
				else if (opcion_seleccionada == 3 && res != null)
					EjecutaCerrarPedido();
				else if (opcion_seleccionada == 4 && res != null)
				{
					String textoFactura = EjecutarConsultaPedido();
					System.out.println(textoFactura);
				}
				else if (opcion_seleccionada == 5 && res != null)
				{
					System.out.println("Saliendo de la aplicación ...");
					continuar = false;
				}
				else
				{
					System.out.println("Por favor seleccione una opción válida.");
				}
			}
			catch (NumberFormatException e)
			{
				System.out.println("Debe seleccionar uno de los números de las opciones.");
			}
		}
	}
		




	public void MostrarMenu()
	{
		System.out.println("\nOpciones de la aplicación\n");
		System.out.println("1. Iniciar un pedido");
		System.out.println("2. Agregar un elemento a un pedido");
		System.out.println("3. Cerrar un pedido y guardar la factura");
		System.out.println("4. Consultar informacion de un pedido");
		System.out.println("5. Cerrar la aplicacion");
	}
	
	public void ejecutarIniciarAplicacion() throws FileNotFoundException, IOException
	{
		String nombreCliente = input("Por favor ingrese su nombre: ");
		nombreCliente = nombreCliente.toUpperCase();
		
		String direccionCliente = input("Por favor ingrese su direccion: ");
		String archivoIngredientes = "data/ingredientes.txt";
		String archivoMenu = "data/menu.txt";
		String archivoCombos = "data/combos.txt";
		
		res = loaderRestaurante.cargarInformacionRestaurante(archivoIngredientes,archivoMenu,archivoCombos);
		res.iniciarPedido(nombreCliente,direccionCliente);
	}
	private void ejecutarAgregarElementoPedido()
	{
		System.out.println("Que elemento desea agregar?\n");
		System.out.println("1. Combo");
		System.out.println("2. Producto del Menu");
		System.out.println("3. Cancelar");
	
		int opcion_seleccionada = Integer.parseInt(input("Por favor seleccione una opción"));
		producto elemento = null;
		if (opcion_seleccionada == 1)
		{
			ArrayList<combo> ListaCombos = mostrarCombos();
			System.out.println("Que elemento desea agregar?\n");
			int opcion_combo = Integer.parseInt(input("Por favor seleccione una opción"));
			elemento = ListaCombos.get(opcion_combo - 1);
			
			pedido Elpedido = res.getPedidoEnCurso();
			Elpedido.agregarProducto(elemento);
		}
		else if (opcion_seleccionada == 2)
		{
			ArrayList<productoMenu> ListaMenu = mostrarProductoMenu();
			System.out.println("Que elemento desea agregar?\n");
			int opcion_menu = Integer.parseInt(input("Por favor seleccione una opción"));
			productoMenu elemento1 = ListaMenu.get(opcion_menu - 1);
			
			System.out.println("Desea agregar o eliminar algun ingrediente?\n");
			System.out.println("1. Si\n");
			System.out.println("2. No\n");
			int opcion_ajustar = Integer.parseInt(input("Por favor seleccione una opción"));
			
			if (opcion_ajustar == 1)
			{
				boolean continuar = true;
				boolean continuar2 = false;
				ArrayList<ingrediente> ListaIngredientesAgregados = new ArrayList<ingrediente>();
				ArrayList<ingrediente> ListaIngredientesEliminados = new ArrayList<ingrediente>();
				
				while (continuar)
				{
					ArrayList<ingrediente> ListaIngredientes = mostrarIngredientes();

					int size_lista = ListaIngredientes.size();
					System.out.println(Integer.toString(size_lista + 1) + ". Ninguno");
					System.out.println("Que elemento desea agregar?\n");
					int opcion_ingrediente = Integer.parseInt(input("Por favor seleccione una opción"));
					if (opcion_ingrediente >= size_lista+1)
					{
						continuar2 = true;
					}
					else
					{
						ingrediente elemento_ingrediente = ListaIngredientes.get(opcion_ingrediente-1);
						ListaIngredientesAgregados.add(elemento_ingrediente);
						System.out.println("Se agrego " + elemento_ingrediente.getNombre()+"\n");
					}
					while (continuar2)
					{
						mostrarIngredientes();
						System.out.println(Integer.toString(size_lista + 1) + ". Ninguno");
						System.out.println("Que elemento desea eliminar? (no tiene costo)\n");
						int opcion_ingrediente2 = Integer.parseInt(input("Por favor seleccione una opción"));
						if (opcion_ingrediente2 >= size_lista+1)
						{
							continuar = false;
							continuar2 = false;
						}
						else
						{
							ingrediente elemento_ingrediente2 = ListaIngredientes.get(opcion_ingrediente2 - 1);
							ListaIngredientesEliminados.add(elemento_ingrediente2);
							System.out.println("Se elimino " + elemento_ingrediente2.getNombre()+ "\n");
						}
					}
				}
				
				productoAjustado elemento_ajustado = new productoAjustado(elemento1,ListaIngredientesAgregados,ListaIngredientesEliminados);
				pedido Elpedido = res.getPedidoEnCurso();
				Elpedido.agregarProducto(elemento_ajustado);
			}
			else
			{
				pedido Elpedido = res.getPedidoEnCurso();
				Elpedido.agregarProducto(elemento1);
			}
		}
				
	}
	
	private ArrayList<productoMenu> mostrarProductoMenu() {
		
		ArrayList<productoMenu> ListaMenu = res.getMenuBase();
		int i = 1;
		for (productoMenu n_producto: ListaMenu)
		{
			String factura = n_producto.generarTextoFactura();
			System.out.println(Integer.toString(i) + ". " + factura);
			i++;
		}
		return ListaMenu;
	}


	private ArrayList<combo> mostrarCombos()
	{
		ArrayList<combo> ListaCombos = res.getCombo();
		int i = 1;
		for (combo n_combo: ListaCombos)
		{
			String factura = n_combo.generarTextoFactura();
			System.out.println(Integer.toString(i) + ". " + factura);
			i++;
		}
		return ListaCombos;
	}
	
	private ArrayList<ingrediente> mostrarIngredientes()
	{
		ArrayList<ingrediente> ListaIngredientes = res.getIngrediente();
		int i = 1;
		for (ingrediente n_ingrediente: ListaIngredientes)
		{
			String factura = n_ingrediente.getNombre();
			factura += " " + n_ingrediente.getCostoAdicional();
			System.out.println(Integer.toString(i) + ". " + factura);
			i++;
		}
		return ListaIngredientes;
	}
	
	private void EjecutaCerrarPedido() throws IOException
	{
		res.cerrarYGuardarPedido();
	}
	
	private String EjecutarConsultaPedido()
	{
		int eleccion = Integer.parseInt(input("Desea consultar el pedido en curso (1) o uno anterior (2)?"));
		pedido Elpedido = null;
		if (eleccion == 1)
		{
			Elpedido = res.getPedidoEnCurso();
			String textoFactura = Elpedido.generarTextoFactura();
			return textoFactura;
		}
		else 
		{
			int idPedido = Integer.parseInt(input("Por favor ingrese ID del pedido: "));
			Elpedido = res.getPedidoID(idPedido);
			if (Elpedido != null)
			{
				String textoFactura = Elpedido.generarTextoFactura();
				return textoFactura;
			}
			else
				return ("No se encontró el pedido con ese ID :(");
		}
	}
	
	public String input(String mensaje)
	{
		try
		{
			System.out.print(mensaje + ": ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			return reader.readLine();
		}
		catch (IOException e)
		{
			System.out.println("Error leyendo de la consola");
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) throws IOException
	{
		AplicacionHamburguesas aplicacion = new AplicacionHamburguesas();
		aplicacion.ejecutarAplicacion();
	}
	
}