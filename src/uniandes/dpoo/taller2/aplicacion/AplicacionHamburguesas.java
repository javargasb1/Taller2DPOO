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
				else if (opcion_seleccionada == 3)
					EjecutaCerrarPedido();
				else if (opcion_seleccionada == 4)
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
		
		System.out.println("Pedido empezado con exito...\n");
		System.out.println("Por favor agregue un elemento al pedido (opcion 2)\n");
	}
	private void ejecutarAgregarElementoPedido()
	{
		if (res != null)
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
				int size_lista_combos = ListaCombos.size();
				System.out.println(Integer.toString(size_lista_combos + 1) + ". Cancelar");
				System.out.println("Que elemento desea agregar?\n");
				int opcion_combo = Integer.parseInt(input("Por favor seleccione una opción"));
				
				if (opcion_combo <= size_lista_combos)
				{
					elemento = ListaCombos.get(opcion_combo - 1);
					pedido Elpedido = res.getPedidoEnCurso();
					Elpedido.agregarProducto(elemento);
					System.out.println("Se agrego " + elemento.generarTextoFactura());				}
				else if(opcion_combo == size_lista_combos + 1)
				{
					System.out.println("Accion cancelada...");
				}
				else
				{
					System.out.println("Opcion no valida, vuelva a intentarlo");
				}
			}
			
			else if (opcion_seleccionada == 2)
			{
				ArrayList<productoMenu> ListaMenu = mostrarProductoMenu();
				System.out.println("Que elemento desea agregar?\n");
				int opcion_menu = Integer.parseInt(input("Por favor seleccione una opción"));
				productoMenu elemento1 = ListaMenu.get(opcion_menu - 1);
				ArrayList<ingrediente> ListaIngredientesAgregados = null;
				ArrayList<ingrediente> ListaIngredientesEliminados = null;
				
				System.out.println("Desea agregar algun ingrediente?\n");
				System.out.println("1. Si\n");
				System.out.println("2. No\n");
				int opcion_ajustar = Integer.parseInt(input("Por favor seleccione una opción"));
				
				if (opcion_ajustar == 1)
				{  
					boolean continuar = true;
					
					while (continuar)
					{
						ArrayList<ingrediente> ListaIngredientes = mostrarIngredientes();
						ListaIngredientesAgregados = new ArrayList<ingrediente>();
						int size_lista = ListaIngredientes.size();
						System.out.println(Integer.toString(size_lista + 1) + ". Cancelar");
						System.out.println("Que elemento desea agregar?\n");
						int opcion_ingrediente = Integer.parseInt(input("Por favor seleccione una opción"));
						if (opcion_ingrediente >= size_lista+1)
						{
							continuar = false;
						}
						else
						{
							ingrediente elemento_ingrediente = ListaIngredientes.get(opcion_ingrediente-1);
							ListaIngredientesAgregados.add(elemento_ingrediente);
							System.out.println("Se agrego " + elemento_ingrediente.getNombre()+"\n");
							System.out.println("Desea agregar algo mas? \n");
						}
					}
				}
				
				
				System.out.println("Desea eliminar algun ingrediente?\n");
				System.out.println("1. Si\n");
				System.out.println("2. No\n");
				int opcion_ajustar2 = Integer.parseInt(input("Por favor seleccione una opción"));
				
				if (opcion_ajustar2 == 1)
				{
					boolean continuar2 = true;
					while (continuar2)
					{
						ArrayList<ingrediente> ListaIngredientes = mostrarIngredientes();
						ListaIngredientesEliminados = new ArrayList<ingrediente>();
						int size_lista = ListaIngredientes.size(); 
						System.out.println(Integer.toString(size_lista + 1) + ". Cancelar");
						System.out.println("Que elemento desea eliminar? (no tiene costo)\n");
						int opcion_ingrediente2 = Integer.parseInt(input("Por favor seleccione una opción"));
						if (opcion_ingrediente2 >= size_lista+1)
						{
							continuar2 = false;
						}
						else
						{
							ingrediente elemento_ingrediente2 = ListaIngredientes.get(opcion_ingrediente2 - 1);
							ListaIngredientesEliminados.add(elemento_ingrediente2);
							System.out.println("Se elimino " + elemento_ingrediente2.getNombre()+ "\n");
							System.out.println("Desea eliminar algun ingrediente mas?\n");
						}
					}
				}	
				
				productoAjustado elemento_ajustado = new productoAjustado(elemento1,ListaIngredientesAgregados,ListaIngredientesEliminados);
				pedido Elpedido = res.getPedidoEnCurso();
				Elpedido.agregarProducto(elemento_ajustado);
				System.out.println("Se agrego " + elemento_ajustado.generarTextoFactura() + "\n");
			}		
		}
		else
			System.out.println("No hay un pedido en curso");
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
		if (res != null)
		{
			pedido Elpedido = res.getPedidoEnCurso();
			int idPedido = Elpedido.getIdPedido();
			res.cerrarYGuardarPedido();
			System.out.println("Pedido cerrado con exito...\n Id del pedido: " + Integer.toString(idPedido) + "\n");
		}
		else
			System.out.println("No hay ningun pedido en curso\n");
	}
	
	private String EjecutarConsultaPedido()
	{
		int eleccion = Integer.parseInt(input("Desea consultar el pedido en curso (1) o uno anterior (2)?"));
		if (eleccion == 1 && res != null)
		{
			pedido Elpedido = res.getPedidoEnCurso();
			if (Elpedido != null)
			{
				String textoFactura = Elpedido.generarTextoFactura();
				return textoFactura;
			}
			else
				return ("No hay un pedido en curso");
		}
		
		else if (eleccion == 2 && res != null) 
		{
			int idPedido = Integer.parseInt(input("Por favor ingrese ID del pedido: "));
			pedido Elpedido = res.getPedidoID(idPedido);
			if (Elpedido != null)
			{
				String textoFactura = Elpedido.generarTextoFactura();
				return textoFactura;
			}
			else
				return ("No se encontró el pedido con ese ID :(");
		}
		
		return "No hay historial de pedidos";
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