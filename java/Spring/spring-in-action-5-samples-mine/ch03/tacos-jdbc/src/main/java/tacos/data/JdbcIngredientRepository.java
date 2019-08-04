package tacos.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import tacos.Ingredient;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcIngredientRepository implements IngredientRepository {

    private JdbcTemplate jdbc;

    @Autowired
    public JdbcIngredientRepository(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }

    @Override
    Iterable<Ingredient> findAll(){
        return jdbc.query("select id, name, type from Ingredient",
                this.mapRowToIngredient);
    }

    Ingredient findOne(String id){
        return jdbc.queryForObject("select id, name, type from Ingredient",
                this.mapRowToIngredient, id);
    }

    Ingredient save(Ingredient ingredient){
        jdbc.updat(
                "insert int Ingredient (id, name, type) value (?, ?, ?)",
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getType()
        );
        return ingredient;
    }

    private Ingredient mapRowToIngredient(ResultSet rs, int rowNum) throws SQLException{
        return new Ingredient(
                rs.getString("id"),
                rs.getString("name"),
                Ingredient.Type.valueOf(rs.getString("type"))
        );
    }
}
