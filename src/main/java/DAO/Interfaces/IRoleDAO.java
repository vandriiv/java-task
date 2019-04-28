package DAO.Interfaces;

import Entities.Role;

public interface IRoleDAO {

    Role findByName(String name);

    Role findById(long id);
}
