package com.example.STL.Repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.STL.Model.RegistroAtividade;

public interface RegistroAtividadeRepository extends JpaRepository<RegistroAtividade, Long> {
	
	@Query("select t from RegistroAtividade t where t.dataHora between ?1 and ?2")
	List<RegistroAtividade> findByDataBetween(Date dataInicial, Date dataFinal);

}
