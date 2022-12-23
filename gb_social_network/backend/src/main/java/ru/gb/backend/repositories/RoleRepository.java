package ru.gb.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.backend.entity.Role;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    List<Role> findByNameIn(List<String> names);
}
