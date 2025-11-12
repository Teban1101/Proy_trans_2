package uniandes.edu.co.proyecto.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.modelo.PuntoGeografico;
import uniandes.edu.co.proyecto.repositorio.PuntoGeograficoRepository;

@RestController
@RequestMapping("/puntosGeograficos")
public class PuntoGeograficoController {

    @Autowired
    private PuntoGeograficoRepository puntoGeograficoRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/insertar")
    public String insertarPunto(@RequestParam Integer id_punto, @RequestParam String nombre,
                                @RequestParam String direccion, @RequestParam Double latitud,
                                @RequestParam Double longitud, @RequestParam Integer id_ciudad) {
        try {
            puntoGeograficoRepository.insertarPunto(id_punto, nombre, direccion, latitud, longitud, id_ciudad);
            return "PuntoGeografico insertado correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al insertar PuntoGeografico: " + e.getMessage();
        }
    }

    // Accept JSON body for insertion (frontend may send JSON)
    @PostMapping(value = "/insertar", consumes = "application/json")
    public ResponseEntity<?> insertarPuntoJson(@RequestBody Map<String, Object> body) {
        try {
            // Parse id_punto safely (could be absent or too large); fall back to timestamp-based id
            Integer id_punto = null;
            Object idPObj = body.get("id_punto");
            if (idPObj != null) {
                try {
                    String s = idPObj.toString();
                    if (!s.isBlank()) {
                        id_punto = Integer.valueOf(s);
                    }
                } catch (Exception ex) {
                    // ignore and fallback
                }
            }
            if (id_punto == null) {
                id_punto = (int) (System.currentTimeMillis() / 1000);
            }

            String nombre = body.getOrDefault("nombre", "").toString();
            String direccion = body.getOrDefault("direccion", "").toString();
            Double latitud = null;
            Double longitud = null;
            Object latObj = body.get("latitud");
            if (latObj != null && !latObj.toString().isBlank()) {
                latitud = Double.valueOf(latObj.toString());
            }
            Object lonObj = body.get("longitud");
            if (lonObj != null && !lonObj.toString().isBlank()) {
                longitud = Double.valueOf(lonObj.toString());
            }

            Integer id_ciudad = null;
            Object idCObj = body.get("id_ciudad");
            if (idCObj != null) {
                String s = idCObj.toString().trim();
                if (!s.isBlank()) {
                    try { id_ciudad = Integer.valueOf(s); } catch (Exception ex) { id_ciudad = null; }
                }
            }

            if (nombre.isBlank() || direccion.isBlank() || latitud == null || longitud == null) {
                return ResponseEntity.badRequest().body(Map.of("ok", false, "message", "Faltan campos requeridos"));
            }

            // Decide whether the table actually contains ID_CIUDAD column by inspecting a sample row
            boolean hasIdCiudadColumn = false;
            try {
                List<Map<String,Object>> sample = jdbcTemplate.queryForList("SELECT * FROM PUNTO_GEOGRAFICO FETCH FIRST 1 ROWS ONLY");
                if (sample != null && !sample.isEmpty()) {
                    Map<String,Object> row = sample.get(0);
                    hasIdCiudadColumn = row.containsKey("ID_CIUDAD");
                }
            } catch (Exception e) {
                // ignore; assume column not present
                hasIdCiudadColumn = false;
            }

            // Build SQL depending on presence of column and provided id_ciudad
            try {
                if (hasIdCiudadColumn && id_ciudad != null) {
                    String sql = "INSERT INTO PUNTO_GEOGRAFICO (ID_PUNTO, NOMBRE, DIRECCION, LATITUD, LONGITUD, ID_CIUDAD) VALUES (?, ?, ?, ?, ?, ?)";
                    jdbcTemplate.update(sql, id_punto, nombre, direccion, latitud, longitud, id_ciudad);
                } else {
                    String sql = "INSERT INTO PUNTO_GEOGRAFICO (ID_PUNTO, NOMBRE, DIRECCION, LATITUD, LONGITUD) VALUES (?, ?, ?, ?, ?)";
                    jdbcTemplate.update(sql, id_punto, nombre, direccion, latitud, longitud);
                }
            } catch (org.springframework.jdbc.BadSqlGrammarException bsge) {
                // If the insert with ID_CIUDAD failed due to grammar, retry without that column
                try {
                    String sql = "INSERT INTO PUNTO_GEOGRAFICO (ID_PUNTO, NOMBRE, DIRECCION, LATITUD, LONGITUD) VALUES (?, ?, ?, ?, ?)";
                    jdbcTemplate.update(sql, id_punto, nombre, direccion, latitud, longitud);
                } catch (Exception inner) {
                    throw inner;
                }
            }

            return ResponseEntity.ok(Map.of("ok", true, "message", "Punto insertado", "id_punto", id_punto));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("ok", false, "error", e.getClass().getName(), "message", e.getMessage()));
        }
    }

    @PutMapping("/actualizar")
    public String actualizarPunto(@RequestParam Integer id_punto, @RequestParam String nombre,
                                  @RequestParam String direccion, @RequestParam Double latitud,
                                  @RequestParam Double longitud, @RequestParam Integer id_ciudad) {
        try {
            puntoGeograficoRepository.actualizarPunto(id_punto, nombre, direccion, latitud, longitud, id_ciudad);
            return "PuntoGeografico actualizado correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al actualizar PuntoGeografico: " + e.getMessage();
        }
    }

    @DeleteMapping("/eliminar/{id_punto}")
    public String eliminarPunto(@PathVariable Integer id_punto) {
        try {
            puntoGeograficoRepository.eliminarPunto(id_punto);
            return "PuntoGeografico eliminado correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al eliminar PuntoGeografico: " + e.getMessage();
        }
    }

    @GetMapping("/darPuntos")
    public List<PuntoGeografico> darPuntos() {
        try {
            return puntoGeograficoRepository.darPuntos();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/darPunto/{id_punto}")
    public PuntoGeografico darPunto(@PathVariable Integer id_punto) {
        try {
            return puntoGeograficoRepository.darPunto(id_punto);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Debug endpoint returning structured result or error
    @GetMapping("/debug")
    public ResponseEntity<?> debugDarPuntos() {
        try {
            List<PuntoGeografico> puntos = puntoGeograficoRepository.darPuntos();
            return ResponseEntity.ok(Map.of("ok", true, "count", puntos == null ? 0 : puntos.size(), "data", puntos));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(Map.of(
                "ok", false,
                "error", e.getClass().getName(),
                "message", e.getMessage()
            ));
        }
    }

    @GetMapping("/debugCount")
    public ResponseEntity<?> debugCount() {
        try {
            Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM PUNTO_GEOGRAFICO", Integer.class);
            List<Map<String,Object>> sample = jdbcTemplate.queryForList("SELECT * FROM PUNTO_GEOGRAFICO FETCH FIRST 5 ROWS ONLY");
            return ResponseEntity.ok(Map.of("ok", true, "count", count, "sample", sample));
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return ResponseEntity.ok(Map.of("ok", false, "error", dae.getClass().getName(), "message", dae.getMessage()));
        }
    }

    @GetMapping("/debugAll")
    public ResponseEntity<?> debugAll() {
        try {
            List<Map<String,Object>> rows = jdbcTemplate.queryForList("SELECT * FROM PUNTO_GEOGRAFICO ORDER BY ID_PUNTO");
            return ResponseEntity.ok(Map.of("ok", true, "count", rows.size(), "data", rows));
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            return ResponseEntity.ok(Map.of("ok", false, "error", dae.getClass().getName(), "message", dae.getMessage()));
        }
    }
}
