package org.example;

import java.sql.*;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //Conectamos a la base de datos
        try (Connection conn = DriverManager.getConnection(
                DBConfig.getUrl(),
                DBConfig.getUser(),
                DBConfig.getPassword())) {
            System.out.println("Conectado con suceso!");
            int opcion;
//Hacemos un do while
            do {
                System.out.println("Que opcion quieres usar?");
                System.out.println("1. Mostrar los empleados");
                System.out.println("2. insertar un empleado");
                System.out.println("3. Actualizar un empleado");
                System.out.println("4. eliminar un empleado");
                System.out.println("5. Salir");

                opcion = sc.nextInt();
                sc.nextLine();
                //Creamos un menu switch que cada opcion llama a cada metodo usando la conexion que tenemos en la base de datos
                switch (opcion) {
                    case 1:
                        mostrarEmpleados(conn);
                        break;
                    case 2:
                        insertarEmpleados(conn,sc);
                        break;
                    case 3:
                        actualizarEmpleado(conn,sc);
                        break;
                    case 4:
                        eliminarEmpleados(conn,sc);
                        break;


                }
            } while (opcion != 5);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sc.close();

    }

    public static void mostrarEmpleados(Connection conn) {
        //En este método mostrará toda la información de los empleados
        String sql = "select * from empleado";
        try (Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("ID");
                String nombre = rs.getString("nombre");
                double salario = rs.getDouble("salario");
                System.out.println("ID: " + id + " nombre " + nombre + " salario: " + salario);
            }

        } catch (SQLException e) {
            System.out.println("Error de sql " + e.getMessage());
        }
    }

    //Este metodo sirve para insertarEmpleados en nuestra tabla de bases de datos
    public static void insertarEmpleados(Connection conn, Scanner sc)  {
        String sql2 = "INSERT INTO empleado (id,nombre, salario) VALUES (?, ?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql2)) {
            //Pedimos el id, el nombre y el salario, para luego añadirlo
            System.out.println("Ingrese el ID del empleado");
            int id = sc.nextInt();
            sc.nextLine();
            ps.setInt(1, id);
            System.out.println("Ingrese el nombre del empleado");
            String nombre2 = sc.nextLine();
            ps.setString(2, nombre2);
            System.out.println("Ingrese el salario del empleado");
            double salario2 = sc.nextDouble();
            sc.nextLine();
            ps.setDouble(3, salario2);
            ps.executeUpdate();
            System.out.println("Se ha añadido el empleado");
        }catch (SQLException e) {
            System.out.println("Error al insertar el empleado");
        }
    }


//Este metodo servira para actualizar el Empleado donde habra diferentes opciones dependiendo de lo que queramos actualizar
    public static void actualizarEmpleado(Connection conn,Scanner sc) throws SQLException {

        System.out.println("Que quieres actualizar del empleado?");
        String actualizar = sc.nextLine();
        if (actualizar.equalsIgnoreCase("nombre")) {

            String actualizarNombre = "UPDATE empleado SET nombre = ? WHERE id = ?";
            try(PreparedStatement ps2 = conn.prepareStatement(actualizarNombre)) {
                System.out.println("Ingrese el nombre del empleado");
                String nombreActualizar = sc.nextLine();
                ps2.setString(1, nombreActualizar);
                System.out.println("Dime el id del empleado a cual quieras actualizar");
                int id1 = sc.nextInt();
                sc.nextLine();
                ps2.setInt(2, id1);
                ps2.executeUpdate();
                System.out.println("Se ha actualizado el nombre del empleado");
            }catch (SQLException e) {
                System.out.println("Error al actualizar el empleado");
            }
        } else if (actualizar.equalsIgnoreCase("salario")) {
            String actualizarSalario = "UPDATE empleado SET salario = ? WHERE id = ?";
            try(PreparedStatement ps2 = conn.prepareStatement(actualizarSalario)) {
                System.out.println("Ingrese el salario del empleado");
                double salarioActualizar = sc.nextDouble();
                sc.nextLine();
                ps2.setDouble(1, salarioActualizar);
                System.out.println("Dime el id del empleado a cual quieras actualizar");
                int id2 = sc.nextInt();
                sc.nextLine();
                ps2.setInt(2, id2);
                ps2.executeUpdate();
            }catch (SQLException e) {
                System.out.println("Error al actualizar el salario del empleado");
            }
        } else if (actualizar.equalsIgnoreCase("id")) {
            String actualizarId = "UPDATE empleado SET id = ? WHERE id = ?";
            try(PreparedStatement ps2 = conn.prepareStatement(actualizarId)){
            System.out.println("Ingrese el ID del empleado");
            int idActualizar = sc.nextInt();
            sc.nextLine();
            ps2.setInt(1, idActualizar);
            System.out.println("Dime el id del empleado a cual quieras actualizar");
            int id3 = sc.nextInt();
            ps2.setInt(2, id3);

            ps2.executeUpdate();
            }catch (SQLException e) {
            System.out.println("Error al actualizar el ID del empleado");}
        }


    }
    //Metodo para eliminar empleados
    public static void eliminarEmpleados(Connection conn,Scanner sc)  {
        String eliminarSql = "DELETE FROM empleado WHERE id = ?";
        try(PreparedStatement ps2 = conn.prepareStatement(eliminarSql)){
        int filasEliminadas;
        System.out.println("Ingrese el ID del empleado");
        int idEliminar = sc.nextInt();
        sc.nextLine();
        //Ponemos la variable del ID y el índice para eliminarlo de nuestra base de datos
        ps2.setInt(1, idEliminar);
        filasEliminadas = ps2.executeUpdate();
        System.out.println("Se ha eliminado el empleado, estas han sido las filas afectadas " + filasEliminadas);
    }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}