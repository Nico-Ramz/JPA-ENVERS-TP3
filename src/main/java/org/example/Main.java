package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("example-unit");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        System.out.println("++++++++++++++++++++++++++++++++");

        try {
            entityManager.getTransaction().begin();

            Cliente cliente = Cliente.builder()
                    .nombre("Juan")
                    .apellido("Richardi")
                    .dni(20626474)
                    .build();

            Domicilio domicilio = Domicilio.builder()
                    .nombreCalle("Patricias Mendocinas")
                    .numero(2503)
                    .build();

            Factura factura = Factura.builder()
                    .fecha("15/01/24")
                    .numero(2589)
                    .total(750)
                    .build();

            DetalleFactura detalle1 = DetalleFactura.builder()
                    .cantidad(3)
                    .subtotal(150)
                    .build();

            Articulo articulo1 = Articulo.builder()
                    .cantidad(8)
                    .denominacion("Lapiz")
                    .precio(50)
                    .build();

            Articulo articulo2 = Articulo.builder()
                    .cantidad(10)
                    .denominacion("Shampoo")
                    .precio(300)
                    .build();

            DetalleFactura detalle2 = DetalleFactura.builder()
                    .cantidad(2)
                    .subtotal(600)
                    .build();

            Categoria Libreria = Categoria.builder()
                    .debominacion("Libreria")
                    .build();
            Categoria Limpieza = Categoria.builder()
                    .debominacion("Limpieza")
                    .build();

            cliente.setDomicilio(domicilio);

            factura.setCliente(cliente);
            factura.addDetalleFactura(detalle1);
            factura.addDetalleFactura(detalle2);


            articulo1.getCategorias().add(Libreria);

            articulo2.getCategorias().add(Limpieza);

            detalle2.setArticulo(articulo2);
            detalle1.setArticulo(articulo1);

            entityManager.persist(domicilio);
            entityManager.persist(cliente);
            entityManager.persist(detalle2);
            entityManager.persist(detalle1);
            entityManager.persist(articulo1);
            entityManager.persist(articulo2);
            entityManager.persist(Libreria);
            entityManager.persist(Limpieza);
            entityManager.persist(factura);


/*
            Factura factura =entityManager.find(Factura.class, 1L);
            factura.setNumero(85);

            entityManager.merge(factura);

            Factura factura =entityManager.find(Factura.class, 1L);
            factura.setNumero(85);

            entityManager.remove(factura);
*/



            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
            System.out.println("------------------");



        }
        entityManager.close();
        entityManagerFactory.close();
    }
}
