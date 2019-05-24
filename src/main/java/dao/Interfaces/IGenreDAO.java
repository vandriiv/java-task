package dao.Interfaces;

import entities.Genre;
import exceptions.DAOException;

import java.util.List;

public interface IGenreDAO {

    List<Genre> getAll() throws DAOException;

    Genre findById(long id) throws DAOException;

    void update (Genre genre) throws DAOException;

    void create (Genre genre) throws DAOException;
}
