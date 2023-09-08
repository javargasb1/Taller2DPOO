package uniandes.dpoo.taller2.procesamiento;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import uniandes.dpoo.taller2.modelo.bebida;
import uniandes.dpoo.taller2.modelo.combo;
import uniandes.dpoo.taller2.modelo.ingrediente;
import uniandes.dpoo.taller2.modelo.productoMenu;

public class loaderRestaurante
{
	private static ArrayList<ingrediente> ListaIngredientes = null;
	private static ArrayList<productoMenu> ListaMenu = null;
	private static ArrayList<combo> ListaCombos = null;
	private static ArrayList<bebida> ListaBebidas = null;
	
	public static restaurante cargarInformacionRestaurante(String archivoIngredientes,String archivoMenu,String archivoCombos,String archivoBebidas) throws FileNotFoundException, IOException
	{
		cargarIngredientes(archivoIngredientes);
		cargarMenu(archivoMenu);
		cargarCombos(archivoCombos);
		cargarBebidas(archivoBebidas);
		restaurante res = new restaurante(ListaIngredientes,ListaMenu,ListaCombos,ListaBebidas);
		return res;
	}	
	private static  ArrayList<ingrediente> cargarIngredientes(String archivoIngredientes) throws FileNotFoundException, IOException
	{
		ListaIngredientes = new ArrayList<ingrediente>();
				
		BufferedReader br = new BufferedReader(new FileReader(archivoIngredientes));
		String linea = br.readLine();
		while (linea != null)
		{
			String[] partes = linea.split(";");
			String nombreIngrediente = partes[0];
			int costoAdicional = Integer.parseInt(partes[1]);
			
			ingrediente Elingrediente = new ingrediente(nombreIngrediente,costoAdicional);
			ListaIngredientes.add(Elingrediente);
			linea = br.readLine();
		}
		br.close();
		return ListaIngredientes;
	}
	private static ArrayList<productoMenu> cargarMenu(String archivoMenu)throws FileNotFoundException, IOException
	{
		ListaMenu = new ArrayList<productoMenu>();
		
		BufferedReader br = new BufferedReader(new FileReader(archivoMenu));
		String linea = br.readLine();
		while (linea != null)
		{
			String[] partes = linea.split(";");
			String nombreProductoMenu = partes[0];
			int costoBase = Integer.parseInt(partes[1]);
			
			productoMenu ElproductoMenu = new productoMenu(nombreProductoMenu,costoBase);
			ListaMenu.add(ElproductoMenu);
			linea = br.readLine();
		}
		br.close();
		return ListaMenu;
	}
	private static ArrayList<bebida> cargarBebidas(String archivoBebidas)throws FileNotFoundException, IOException
	{
		ListaBebidas = new ArrayList<bebida>();
		
		BufferedReader br = new BufferedReader(new FileReader(archivoBebidas));
		String linea = br.readLine();
		while (linea != null)
		{
			String[] partes = linea.split(";");
			String nombreBebida = partes[0];
			int costoBase = Integer.parseInt(partes[1]);
			
			bebida Labebida = new bebida(nombreBebida,costoBase);
			ListaBebidas.add(Labebida);
			linea = br.readLine();
		}
		br.close();
		return ListaBebidas;
	}	
	private static ArrayList<combo> cargarCombos(String archivoCombos)throws FileNotFoundException, IOException
	{
		ListaCombos = new ArrayList<combo>();
		
		BufferedReader br = new BufferedReader(new FileReader(archivoCombos));
		String linea = br.readLine();
		while (linea != null)
		{
			String[] partes = linea.split(";");
			String nombreCombo = partes[0];
			double descuento = Double.parseDouble(partes[1].replace("%", "")) /100;
			
			ArrayList<productoMenu> listaProductos = new ArrayList<productoMenu>();
			for (int i = 2; i < partes.length; i++) 
			{
				String nombreProducto = partes[i];
				for(productoMenu ElproductoMenu : ListaMenu)
				{
					String nombreProducto2 = ElproductoMenu.getNombre();
					if (nombreProducto2.equals(nombreProducto))
					{
						listaProductos.add(ElproductoMenu);
					}
				}
			}
			combo Elcombo = new combo(nombreCombo,descuento, listaProductos);
			ListaCombos.add(Elcombo);
			linea = br.readLine();
		}
		br.close();
		return ListaCombos;
	}
}