package pl.blazejolesiak.demoBiblioteka.models.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.blazejolesiak.demoBiblioteka.models.UserModel;

import java.util.Optional;

@Repository
public interface IUserRepository extends CrudRepository<UserModel, Integer> {
Optional<UserModel> findByUsername(String username);
boolean existsByUsername(String username);

}
