package nl.maccs.project.hackathon.repository;

import nl.maccs.project.hackathon.model.TitlesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Urfan
 */
@Repository
public interface TitleRepository extends JpaRepository<TitlesModel, Long> {

}
