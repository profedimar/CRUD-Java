/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Grupo;
import config.Conexao;

/**
 *
 * @author edimar
 */
public class GrupoDAO {

    public Integer adicionar(Grupo g) {
        try {
            String sql = "INSERT INTO grupo (nome) VALUES (?)"; //alterar

            PreparedStatement pstmt = Conexao.getConexao()
                    .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); //não mexer

            pstmt.setString(1, g.getNome()); // alterar 1 refere-se a 1º interrogação

            pstmt.executeUpdate(); // não mexer

            Integer pk = null;// não mexer
            ResultSet rs = pstmt.getGeneratedKeys(); // não mexer
            if (rs.next()) {// não mexer
                pk = rs.getInt(1);// não mexer
            }// não mexer

            return pk;// não mexer

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Grupo> selecionaTodos() {
        String sql = "SELECT codigo, nome FROM grupo"; //alterar

        try {
            Statement stmt = Conexao.getConexao().createStatement(); //não mexer
            ResultSet rs = stmt.executeQuery(sql); //não mexer
            List<Grupo> lista = new ArrayList<>(); // não mexer

            while (rs.next()) {
                Grupo objeto = new Grupo(); //alterar

                objeto.setCodigo(rs.getInt("codigo")); //alterar
                objeto.setNome(rs.getString("nome"));  //alterar

                lista.add(objeto); //não mexer
            }
            stmt.close();
            return lista;

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Grupo selecionarObjetoPorId(Integer pk) {

        String sql = " SELECT codigo, nome FROM grupo WHERE codigo = ? "; //configurar

        try {
            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql); //não mexer
            pstmt.setInt(1, pk); //configurar

            ResultSet rs = pstmt.executeQuery(); //não mexer
            while (rs.next()) {
                Grupo objeto = new Grupo();
                objeto.setCodigo(rs.getInt("codigo"));
                objeto.setNome(rs.getString("nome"));
                return objeto;
            }
            return null;

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());            
        }
        return null;
    }

    public boolean excluir(Integer id) {
        try {
            String sql = " DELETE FROM grupo WHERE codigo = ? "; //alterar

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql); //não mexer
            pstmt.setInt(1, id); //configurar

            if (pstmt.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean alterar(Grupo g) {
        try {
            String sql = " UPDATE grupo "
                    + "    SET nome = ? "
                    + "  WHERE codigo = ? ";

            PreparedStatement pstmt = Conexao.getConexao().prepareStatement(sql);

            pstmt.setString(1, g.getNome());
            pstmt.setInt(2, g.getCodigo());

            if (pstmt.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static void main(String[] args) {
        Grupo g = new Grupo();
        g.setNome("Alimentícios");
        
        GrupoDAO dao = new GrupoDAO();
        dao.adicionar(g);
    }
}
