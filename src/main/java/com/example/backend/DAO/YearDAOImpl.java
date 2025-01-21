package com.example.backend.DAO;

import com.example.backend.POJO.Year;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class YearDAOImpl implements YearDAO {

    private final JdbcTemplate jdbcTemplate;

    public YearDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Year theYear) {
        String sql = "INSERT INTO years (year) VALUES (?)";
        return jdbcTemplate.update(sql, theYear.getYear());
    }

    @Override
    public Optional<Year> findById(int year_id) {
        String sql = "SELECT * FROM years WHERE year_id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Year year = new Year();
            year.setYearId(rs.getInt("year_id"));
            year.setYear(rs.getInt("year"));
            return year;
        }, year_id));
    }

    @Override
    public List<Year> findAll() {
        String sql = "SELECT * FROM years";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Year year = new Year();
            year.setYearId(rs.getInt("year_id"));
            year.setYear(rs.getInt("year"));
            return year;
        });
    }

    // Update
    @Override
    public int update(Year year) {
        String sql = "UPDATE years SET year = ?, WHERE year_id = ?";
        return jdbcTemplate.update(sql, year.getYear(), year.getYearId());
    }

    // Delete
    @Override
    public int delete(int year_id) {
        String sql = "DELETE FROM years WHERE year_id = ?";
        return jdbcTemplate.update(sql, year_id);    }
}
