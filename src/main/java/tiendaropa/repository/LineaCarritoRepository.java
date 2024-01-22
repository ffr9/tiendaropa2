package tiendaropa.repository;

import org.springframework.data.repository.CrudRepository;
import tiendaropa.model.Carrito;
import tiendaropa.model.LineaCarrito;

import java.util.List;

public interface LineaCarritoRepository extends CrudRepository<LineaCarrito, Long> {
    List<LineaCarrito> findByCarrito(Carrito carrito);
    void deleteByCarrito(Carrito carrito);
}
