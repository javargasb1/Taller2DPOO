package uniandes.dpoo.taller2.modelo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class pedido
{
	private static int numeroPedidos = 0;
	private int idPedido;
	private String nombreCliente;
	private String direccionCliente;
	private ArrayList<producto> ListaProductos;
	
	public pedido(String nombreCliente,String direccionCliente,ArrayList<producto> ListaProductos)
	{
		numeroPedidos++;
		this.idPedido = numeroPedidos;
		this.nombreCliente = nombreCliente;
		this.direccionCliente = direccionCliente;
		this.ListaProductos = ListaProductos;
	}
	public int getIdPedido()
	{
		return idPedido;
	}
	public void agregarProducto(producto nuevoItem)
	{
		ListaProductos.add(nuevoItem);
	}
	
	// Metodo creado para poder comparar los pedidos basandonos en los productos en el pedido.
	public  ArrayList<producto> getListaProductos()
	{
		return ListaProductos;
	}
	
	private int getPrecioNetoPedido()
	{
		int precioNeto = 0;
		for (producto n_producto: ListaProductos)
		{
			int precioProducto = n_producto.getPrecio();
					precioNeto += precioProducto;
		}
		return precioNeto;
		
	}
	private double getPrecioIVAPedido()
	{
		double precioIVA  = getPrecioNetoPedido() * 0.19;
		
		return precioIVA;
	}
	private double getPrecioTotalPedido()
	{
		double precioTotal = getPrecioNetoPedido() + getPrecioIVAPedido();
		
		return precioTotal;
	}
	public String generarTextoFactura()
	{
		String textoFactura = "Cliente: " + nombreCliente + " Dirección: " + direccionCliente + "\n" + "ID del pedido: " + idPedido + "\n";
		for (producto n_producto: ListaProductos)
		{
			String textoProducto = n_producto.generarTextoFactura();
			textoFactura = textoFactura.concat(textoProducto);
		}
		String precioNeto = Integer.toString(getPrecioNetoPedido());
		textoFactura = textoFactura.concat("Precio Neto   " + precioNeto + "\n");
		
		String precioIVA = Double.toString(getPrecioIVAPedido());
		textoFactura = textoFactura.concat("IVA   " + precioIVA + "\n");
		
		String precioTotal = Double.toString(getPrecioTotalPedido());
		textoFactura = textoFactura.concat("Precio Total   " + precioTotal + "\n");
		
		return textoFactura;
		
		
	}
	public void guardarFactura(File archivo) throws IOException
	{
		String textoFactura = generarTextoFactura();
		
		// Crea un FileWriter para escribir en el archivo
        FileWriter escritor = new FileWriter(archivo);

        // Crea un BufferedWriter para escribir de manera eficiente
        BufferedWriter bufferEscritura = new BufferedWriter(escritor);

        // Escribe el contenido en el archivo
        bufferEscritura.write(textoFactura);

        // Cierra el BufferedWriter (esto también cierra el FileWriter)
        bufferEscritura.close();
	}
	
}


