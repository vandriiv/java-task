package DAO.Interfaces;

import Entities.Author;
import Exceptions.DAOException;

import java.util.List;

public interface IAuthorDAO {

    List<Author> findByFirstName(String firstName) throws DAOException;

    List<Author> findByLastName(String lastName) throws DAOException;

    List<Author> findByNamePart(String namePart) throws DAOException;

    Author findById(long id) throws DAOException;

    void create(Author author) throws DAOException;

    void update(Author author) throws DAOException;

    List<Author> getAll() throws DAOException;
}
