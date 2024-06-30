package lk.ijse.cocothumbLayered.dao;

import java.sql.SQLException;
import java.util.List;

public interface CrudDAO<T> extends SuperDAO{
    public  boolean add(T entity) throws SQLException ;


    public  boolean update(T entity) throws SQLException ;

    public  T searchByNIC(String NIC) throws SQLException ;
    public  T searchById(String id) throws SQLException ;

    public  boolean delete(String id) throws SQLException ;


    public  List<T> getAll() throws SQLException ;

    public  List<String> getIds() throws SQLException ;


     //String currentId() throws SQLException ;
     public String generateNewID() throws SQLException;
    public boolean exist(String id) throws SQLException, ClassNotFoundException;
}
