package com.movileHack.movileHack.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movileHack.movileHack.entitys.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	Optional<Usuario> findByEmail(String email);
}
