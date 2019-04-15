package br.edu.utfpr.dao;

import java.sql.Connection;
import java.sql.DriverManager;

import br.edu.utfpr.dto.ClienteDTO;
import br.edu.utfpr.dto.PaisDTO;
import br.edu.utfpr.excecao.NomeClienteMenor5CaracteresException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.extern.java.Log;

@Log
public class ClienteDAO {

    private Connection conn;
    // Responsável por criar a tabela Cliente no banco.
    public ClienteDAO() {

        try (
            
                Connection conn = DriverManager.getConnection("jdbc:derby:memory:database;create=true")) {

            log.info("Criando tabela cliente ...");
            conn.createStatement().executeUpdate(
            "CREATE TABLE cliente (" +
						"id int NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT id_cliente_pk PRIMARY KEY," +
						"nome varchar(255)," +
						"telefone varchar(30)," + 
						"idade int," + 
                        "limiteCredito double," +
                        "id_pais int)");

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
     public void adicionar(ClienteDTO cliente){
         
        String sql = "INSERT INTO cliente (id,nome,telefone,idade,limeteCredito,id_pais)VALUES(?,?,?,?,?,?)";
        try {
            
            try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
                pstmt.setString(1, toString(cliente.getId()));
                pstmt.setString(2, cliente.getNome());
          
                pstmt.setString(3, cliente.getTelefone());
                pstmt.setInt(4, cliente.getIdade());
                pstmt.setDouble(5, cliente.getLimiteCredito());
                pstmt.setObject(6, cliente.getPaisID());
     
                pstmt.execute();
            }
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void alterar(ClienteDTO cliente){
        try {
            String sql = "UPDATE tb_cliente SET nome=?, email=?, telefone=?, datanascimento=?"
                    + "WHERE id_cliente=?";
            PreparedStatement pstmt = this.conn.prepareStatement(sql);
             pstmt.setString(1, toString(cliente.getId()));
                pstmt.setString(2, cliente.getNome());
          
                pstmt.setString(3, cliente.getTelefone());
                pstmt.setInt(4, cliente.getIdade());
                pstmt.setDouble(5, cliente.getLimiteCredito());
                pstmt.setObject(6, cliente.getPaisID());
            pstmt.execute();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deletar(Integer id){
        try {
            try (PreparedStatement pstmt = this.conn.prepareStatement("DELETE FROM tb_cliente "
                    + "WHERE id_cliente=?")) {
                pstmt.setInt(1, id);
                pstmt.execute();
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    public List<ClienteDTO> listar() throws NomeClienteMenor5CaracteresException{
        try {
            List<ClienteDTO> clientes = new ArrayList<>();
            try (PreparedStatement pstmt = this.conn.prepareStatement("SELECT * FROM tb_cliente"); 
                    ResultSet rstst = pstmt.executeQuery()) {
                while(rstst.next()){
                    
                    ClienteDTO cliente;
                    cliente = ClienteDTO.ClienteDTOBuilder;
                    
                    cliente.setId(rstst.getInt("id_cliente"));
                    cliente.setNome(rstst.getString("nome"));
                    cliente.setTelefone(rstst.getString("telefone"));
                    cliente.setIdade((int) rstst.getLong("idade"));
                    cliente.setLimiteCredito(rstst.getDouble("limeteCredito"));
                    cliente.setPais((PaisDTO) rstst.getObject("id_pais"));
                    clientes.add(cliente);              
                }
            }
            conn.close();
            return clientes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String toString(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}