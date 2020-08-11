package guru.sfg.brewery.repositories.security;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import guru.sfg.brewery.domain.security.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
}
